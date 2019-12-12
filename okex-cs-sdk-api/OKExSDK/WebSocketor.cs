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
                await ws.SendAsync(new ArraySegment<byte>(buffer), WebSocketMessageType.Text, true, CancellationToken.None);
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
                      byte[] buffer = new byte[1024];
                      var result = await ws.ReceiveAsync(new ArraySegment<byte>(buffer), CancellationToken.None);
                      if (result.MessageType == WebSocketMessageType.Binary)
                      {
                          closeCheckTimer.Interval = 31000;
                          var resultStr = Decompress(buffer);
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

        private string Decompress(byte[] baseBytes)
        {
            using (var decompressedStream = new MemoryStream())
            using (var compressedStream = new MemoryStream(baseBytes))
            using (var deflateStream = new DeflateStream(compressedStream, CompressionMode.Decompress))
            {
                deflateStream.CopyTo(decompressedStream);
                decompressedStream.Position = 0;
                using (var streamReader = new StreamReader(decompressedStream))
                {
                    return streamReader.ReadToEnd();
                }
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
                await Task.Delay(500);//µÈ´ýµÇÂ¼
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
