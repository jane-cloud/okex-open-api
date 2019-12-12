using Ionic.Crc;
using Newtonsoft.Json;
using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.IO;
using System.IO.Compression;
using System.Linq;
using System.Net.WebSockets;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace OKExSDK
{
    public class WebSocketor : IDisposable
    {
        string url = "wss://real.okex.com:10442/ws/v3";
        ClientWebSocket ws = null;
        CancellationTokenSource cts = new CancellationTokenSource();
        public event WebSocketPushHandler WebSocketPush;
        public delegate void WebSocketPushHandler(string message);
        public string asks_start = "asks\":[[";
        public string asks_end = "]],\"bids";
        public string bids_start = "bids\":[[";
        public string bids_end = "]],\"timestamp";
        public static SortedDictionary<double, string[]> baseAsks = new SortedDictionary<double, string[]>();
        public static SortedDictionary<double, string[]> baseBids = new SortedDictionary<double, string[]>();
        private bool isLogin = false;
        private string apiKey;
        private string secret;
        private string phrase;
        private HashSet<string> channels = new HashSet<string>();
        private ConcurrentQueue<PendingChannel> pendingChannels = new ConcurrentQueue<PendingChannel>();
        private System.Timers.Timer pendingTimer;

        private System.Timers.Timer closeCheckTimer = new System.Timers.Timer();

        private int retryNum = 0;
        public int retryLimit { get; set; } = 20;

        public WebSocketor()
        {
            ws = new ClientWebSocket();
            closeCheckTimer.Interval = 31000;
            closeCheckTimer.Elapsed += async (s, e) =>
            {
                await rebootAsync();
            };
        }
        public void test()
        {
            receive();
        }
        public async Task ConnectAsync()
        {
            await ws.ConnectAsync(new Uri(url), cts.Token);
            closeCheckTimer.Interval = 31000;
            closeCheckTimer.Start();
            receive();
        }

        public async Task LoginAsync(string apiKey, string secret, string phrase)
        {
            this.apiKey = apiKey;
            this.secret = secret;
            this.phrase = phrase;
            isLogin = true;
            if (ws.State == WebSocketState.Open)
            {
                var sign = Encryptor.MakeSign(apiKey, secret, phrase);
                byte[] buff = Encoding.UTF8.GetBytes(sign);
                await ws.SendAsync(new ArraySegment<byte>(buff), WebSocketMessageType.Text, true, CancellationToken.None);
                closeCheckTimer.Interval = 31000;
            }
            else if (ws.State == WebSocketState.CloseReceived || ws.State == WebSocketState.Closed || ws.State == WebSocketState.Aborted)
            {
                await rebootAsync();
            }
        }

        public async Task Subscribe(List<string> args)
        {
            if (ws.State == WebSocketState.Open)
            {
                args.ForEach(channel =>
                {
                    channels.Add(channel);
                });
                var message = new
                {
                    op = "subscribe",
                    args
                };
                var messageStr = JsonConvert.SerializeObject(message);
                byte[] buffer = Encoding.UTF8.GetBytes(messageStr);
                await ws.SendAsync(new ArraySegment<byte>(buffer), WebSocketMessageType.Text, true, CancellationToken.None);
                closeCheckTimer.Interval = 31000;
            }
            else if (ws.State == WebSocketState.CloseReceived || ws.State == WebSocketState.Closed || ws.State == WebSocketState.Aborted)
            {
                args.ForEach(channel =>


                {
                    channels.Add(channel);
                });
                await rebootAsync();
            }
            else
            {
                pendingChannels.Enqueue(new PendingChannel { action = "subscribe", args = args });
                setPendingTimer();
            }
        }
        public async Task UnSubscribe(List<string> args)
        {
            foreach (var channel in args)
            {
                channels.Remove(channel);
            }
            if (ws.State == WebSocketState.Open)
            {
                var message = new
                {
                    op = "unsubscribe",
                    args
                };
                var messageStr = JsonConvert.SerializeObject(message);
                byte[] buffer = Encoding.UTF8.GetBytes(messageStr);
                await ws.SendAsync(new ArraySegment<byte>(buffer), WebSocketMessageType.Binary, true, CancellationToken.None);
                closeCheckTimer.Interval = 31000;
            }
            else if (ws.State == WebSocketState.CloseReceived || ws.State == WebSocketState.Closed || ws.State == WebSocketState.Aborted)
            {
                await rebootAsync();
            }
            else
            {
                pendingChannels.Enqueue(new PendingChannel { action = "unsubscribe", args = args });
                setPendingTimer();
            }
        }

        public void Dispose()
        {
            if (!cts.Token.CanBeCanceled)
            {
                cts.Cancel();
            }
            if (ws != null)
            {
                ws.Dispose();
                ws = null;
            }
            channels = null;
            pendingChannels = null;
            if (pendingTimer != null)
            {
                pendingTimer.Stop();
                pendingTimer.Dispose();
            }
            if (closeCheckTimer != null)
            {
                closeCheckTimer.Stop();
                closeCheckTimer.Dispose();
            }
        }

        private void receive()
        {
            Task.Factory.StartNew(
              async () =>
              {
                  while (ws.State == WebSocketState.Open)
                  {
                      byte[] buffer = new byte[102400000];
                      var result = await ws.ReceiveAsync(new ArraySegment<byte>(buffer), CancellationToken.None);
                      if (result.MessageType == WebSocketMessageType.Binary)
                      {
                          closeCheckTimer.Interval = 31000;
                          var resultStr = Decompress(buffer);
                          //If is depth
                          try
                          {
                              if (resultStr.IndexOf("event") < 0 && !(resultStr.IndexOf("depth5") > 0)&& resultStr.IndexOf("depth") > 0)
                              {
                                  int index_asksStart = resultStr.IndexOf(asks_start);
                                  int index_asksEnd = resultStr.IndexOf(asks_end);
                                  int index_bidsStart = resultStr.IndexOf(bids_start);
                                  int index_bidsEnd = resultStr.IndexOf(bids_end);
                                  string beforeAsks = "", asks = "", bids = "", afterBids = "";
                                  if (index_asksStart > 0)
                                  {
                                      beforeAsks = resultStr.Substring(0, index_asksStart + 8);
                                      asks = resultStr.Substring(index_asksStart + 8, index_asksEnd - (index_asksStart + 8));
                                  }
                                  if (index_bidsStart > 0 && index_bidsEnd > 0)
                                  {
                                      bids = resultStr.Substring(index_bidsStart + 8, index_bidsEnd - index_bidsStart - 8);
                                      afterBids = resultStr.Substring(index_bidsEnd);
                                  }
                                  List<string> list_askContents = asks.Split(new[] { "],[" }, StringSplitOptions.None).ToList();
                                  List<string> list_bidContents = bids.Split(new[] { "],[" }, StringSplitOptions.None).ToList();
                                  combineIncrementalData(list_askContents, "asks");
                                  combineIncrementalData(list_bidContents, "bids");
                                  if (list_askContents.Count != 200)
                                  {
                                      var obj = buildStr_checksum_asks_bids();
                                      int crc32 = getCRC32(obj.Item1);
                                      resultStr = "增量数据：" + resultStr + "\n" + "crc32值：" + crc32.ToString() + "\n" + "checksum值：" + obj.Item1 + "\n" + "合并后的" + beforeAsks + obj.Item2 + "],\"bids\":[" + obj.Item3 + afterBids + "\n";
                                  }
                              }
                          }
                          catch (Exception e)
                          {
                              resultStr = e.Message + e.StackTrace;
                          }

                          WebSocketPush.Invoke(resultStr);
                          continue;
                      }

                      if (result.MessageType == WebSocketMessageType.Close)
                      {
                          try
                          {
                              await ws.CloseOutputAsync(WebSocketCloseStatus.Empty, null, cts.Token);
                          }
                          catch (Exception)
                          {
                              break;
                          }
                          break;
                      }
                  }
              }, cts.Token, TaskCreationOptions.LongRunning,
                 TaskScheduler.Default);
        }
        public int getCRC32(string value)
        {
            if (string.IsNullOrWhiteSpace(value)) return 0;
            byte[] array = Encoding.ASCII.GetBytes(value);
            MemoryStream stream = new MemoryStream(array);
            return new CRC32().GetCrc32(stream);
        }
        public Tuple<string, string, string> buildStr_checksum_asks_bids()
        {
            List<string> list_checkSumStr = new List<string>(new string[50]);
            List<string> list_askContents = new List<string>();
            List<string> list_bidContents = new List<string>();
            int i = 0;
            foreach (var bid in baseBids.Reverse())
            {
                if (i < 50)
                {
                    list_checkSumStr[i] = bid.Value[0] + ":" + bid.Value[1];
                    i += 2;
                }

                list_bidContents.Add("[" + String.Join(",", bid.Value) + "]");
            }
            i = 1;
            foreach (var ask in baseAsks)
            {
                if (i < 50)
                {
                    list_checkSumStr[i] = ask.Value[0] + ":" + ask.Value[1];
                    i += 2;
                }
                list_askContents.Add("[" + String.Join(",", ask.Value) + "]");
            }
            List<string> removeNull_list_checksum = new List<string>();

            for (int index = 0; index < 50; index++)
            {
                if (list_checkSumStr[index] != null)
                {
                    removeNull_list_checksum.Add(list_checkSumStr[index]);
                }
            }
            string checksumStr = String.Join(":", removeNull_list_checksum).Replace("\"", "");
            string asks = string.Join(",", list_askContents);
            string bids = string.Join(",", list_bidContents);
            Tuple<string, string, string> obj = new Tuple<string, string, string>(checksumStr, asks, bids);
            return obj;
        }
        public void combineIncrementalData(List<string> incrementalData, string type)
        {
            if (type == "bids")
            {
                if (baseBids.Count == 0)
                {
                    foreach (var content in incrementalData)
                    {
                        string[] detail = content.Split(',');
                        try
                        {
                            if (!string.IsNullOrWhiteSpace(detail[0].Replace("\"", "")))
                            {
                                baseBids.Add(Convert.ToDouble(detail[0].Replace("\"", "")), detail);
                            }

                        }
                        catch (Exception e)
                        {
                            File.AppendAllText(@"D:\\error.txt", "CreateDate" + DateTime.Now.ToString() + "detail[0]:" + detail[0].Replace("\"", "") + "\n" + e.Message + "\n" + e.StackTrace + "\n");
                        }
                    }

                }
                else
                {
                    if (baseBids.Count > 0)
                    {
                        foreach (var content in incrementalData)
                        {
                            string[] detail = content.Split(',');
                            if (string.IsNullOrWhiteSpace(detail[0]))
                            {
                                continue;
                            }
                            try
                            {
                                if (baseBids.Keys.Contains(Convert.ToDouble(detail[0].Replace("\"", ""))))
                                {
                                    if (detail[1].Replace("\"", "") == "0")
                                    {
                                        if (baseBids.Keys.Contains(Convert.ToDouble(detail[0].Replace("\"", ""))))
                                        {
                                            baseBids.Remove(Convert.ToDouble(detail[0].Replace("\"", "")));
                                        }
                                    }
                                    else
                                    {
                                        baseBids[Convert.ToDouble(detail[0].Replace("\"", ""))] = detail;
                                    }
                                }
                                else
                                {
                                    baseBids.Add(Convert.ToDouble(detail[0].Replace("\"", "")), detail);
                                }
                            }
                            catch (Exception e)
                            {
                                File.AppendAllText(@"D:\\error.txt", "CreateDate" + DateTime.Now.ToString() + "detail[0]:" + detail[0].Replace("\"", "") + "\n" + e.Message + "\n" + e.StackTrace + "\n");
                            }
                        }
                    }
                }
            }
            if (type == "asks")
            {
                if (baseAsks.Count == 0)
                {
                    foreach (var content in incrementalData)
                    {
                        string[] detail = content.Split(',');
                        if (string.IsNullOrWhiteSpace(detail[0]))
                        {
                            continue;
                        }
                        try
                        {
                            baseAsks.Add(Convert.ToDouble(detail[0].Replace("\"", "")), detail);
                        }
                        catch (Exception e)
                        {
                            File.AppendAllText(@"D:\\error.txt", "CreateDate" + DateTime.Now.ToString() + "detail[0]:" + detail[0].Replace("\"", "") + "\n" + e.Message + "\n" + e.StackTrace + "\n");
                        }
                    }

                }
                else
                {
                    if (baseAsks.Count > 0)
                    {
                        foreach (var content in incrementalData)
                        {
                            string[] detail = content.Split(',');
                            if (string.IsNullOrWhiteSpace(detail[0]))
                            {
                                continue;
                            }
                            try
                            {
                                if (baseAsks.Keys.Contains(Convert.ToDouble(detail[0].Replace("\"", ""))))
                                {
                                    if (detail[1].Replace("\"", "") == "0")
                                    {
                                        if (baseAsks.Keys.Contains(Convert.ToDouble(detail[0].Replace("\"", ""))))
                                        {
                                            baseAsks.Remove(Convert.ToDouble(detail[0].Replace("\"", "")));
                                        }
                                    }
                                    else
                                    {
                                        baseAsks[Convert.ToDouble(detail[0].Replace("\"", ""))] = detail;
                                    }
                                }
                                else
                                {
                                    baseAsks.Add(Convert.ToDouble(detail[0].Replace("\"", "")), detail);
                                }

                            }
                            catch (Exception e)
                            {
                                File.AppendAllText(@"D:\\error.txt", "CreateDate" + DateTime.Now.ToString() + "detail[0]:" + detail[0].Replace("\"", "") + "\n" + e.Message + "\n" + e.StackTrace + "\n");
                            }
                        }
                    }
                }
            }
        }
        private string Decompress(byte[] baseBytes)
        {
            try
            {
                using (var decompressedStream = new MemoryStream())
                using (var compressedStream = new MemoryStream(baseBytes))
                using (var deflateStream = new DeflateStream(compressedStream, CompressionMode.Decompress, true))
                {
                    deflateStream.CopyTo(decompressedStream);
                    decompressedStream.Position = 0;
                    using (var streamReader = new StreamReader(decompressedStream,Encoding.UTF8))
                    {
                        return streamReader.ReadToEnd();
                    }
                }
            }
            catch (Exception ex)
            {
                return ex.Message;
            }

        }
    
        private void setPendingTimer()
        {
            if (pendingTimer == null)
            {
                pendingTimer = new System.Timers.Timer(10000);
                pendingTimer.Elapsed += async (s, e) => { await retryPending(); };
                pendingTimer.Start();
            }
            else
            {
                pendingTimer.Start();
            }
        }
        private async Task retryPending()
        {
            while (pendingChannels.Count > 0)
            {
                retryNum++;
                if (retryNum > retryLimit)
                {
                    break;
                }
                PendingChannel channel;

                if (pendingChannels.TryDequeue(out channel))
                {
                    switch (channel.action)
                    {
                        case "subscribe":
                            await Subscribe(channel.args);
                            break;
                        case "unsubscribe":
                            await UnSubscribe(channel.args);
                            break;
                    }
                }
            }
            retryNum = 0;
            pendingTimer.Stop();
        }

        private async Task rebootAsync()
        {
            if (ws.State != WebSocketState.Aborted || ws.State != WebSocketState.Closed)
            {
                await ws.CloseOutputAsync(WebSocketCloseStatus.Empty, null, cts.Token);
            }
            if (cts.Token.CanBeCanceled)
            {
                cts.Cancel();
                cts = new CancellationTokenSource();
            }
            ws.Dispose();
            ws = null;
            ws = new ClientWebSocket();
            if (pendingTimer != null)
            {
                pendingTimer.Stop();
            }

            await ConnectAsync();
            if (isLogin)
            {
                await LoginAsync(apiKey, secret, phrase);
                await Task.Delay(500);//等待登录
            }
            if (channels.Count > 0)
            {
                await Subscribe(channels.ToList<string>());
            }
            setPendingTimer();
        }

        private class PendingChannel
        {
            public string action { get; set; }
            public List<string> args { get; set; }
        }
    }
}
