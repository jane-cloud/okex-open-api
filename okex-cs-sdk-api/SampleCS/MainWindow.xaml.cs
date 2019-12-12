using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using OKExSDK;
using OKExSDK.Models;
using OKExSDK.Models.Account;
using OKExSDK.Models.Ett;
using OKExSDK.Models.Futures;
using OKExSDK.Models.Margin;
using OKExSDK.Models.Spot;
using System;
using System.Collections.Generic;
using System.IO;
using System.IO.Compression;
using System.Linq;
using System.Net.WebSockets;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;
using swap = OKExSDK.Models.Swap;

namespace SampleCS
{
    /// <summary>
    /// MainWindow.xaml 的交互逻辑
    /// </summary>
    public partial class MainWindow : Window
    {
        private GeneralApi generalApi;
        private AccountApi accountApi;
        private FuturesApi futureApi;
        private SpotApi spotApi;
        private MarginApi marginApi;
        private EttApi ettApi;
        private SwapApi swapApi;

        private string apiKey = "";
        private string secret = "";
        private string passPhrase = "";
        public MainWindow()
        {
            InitializeComponent();
            this.generalApi = new GeneralApi(this.apiKey, this.secret, this.passPhrase);
            this.futureApi = new FuturesApi(this.apiKey, this.secret, this.passPhrase);
            this.accountApi = new AccountApi(this.apiKey, this.secret, this.passPhrase);
            this.spotApi = new SpotApi(this.apiKey, this.secret, this.passPhrase);
            this.marginApi = new MarginApi(this.apiKey, this.secret, this.passPhrase);
            this.ettApi = new EttApi(this.apiKey, this.secret, this.passPhrase);
            this.swapApi = new SwapApi(this.apiKey, this.secret, this.passPhrase);
            this.DataContext = new MainViewModel();
        }
        private void btnSetKey(object sender, RoutedEventArgs e)
        {
            var keyinfo = ((MainViewModel)this.DataContext).KeyInfo;
            apiKey = keyinfo.api_key;
            secret = keyinfo.secret;
            passPhrase = keyinfo.passphrase;
            this.generalApi = new GeneralApi(apiKey, secret, passPhrase);
            this.accountApi = new AccountApi(apiKey, secret, passPhrase);
            this.futureApi = new FuturesApi(apiKey, secret, passPhrase);
            this.spotApi = new SpotApi(apiKey, secret, passPhrase);
            this.marginApi = new MarginApi(apiKey, secret, passPhrase);
            this.ettApi = new EttApi(apiKey, secret, passPhrase);
            this.swapApi = new SwapApi(apiKey, secret, passPhrase);
            MessageBox.Show("完成");
        }

        private async void btnSyncServerTimeClick(object sender, RoutedEventArgs e)
        {
            try
            {
                var result = await this.generalApi.syncTimeAsync();
                MessageBox.Show(JsonConvert.SerializeObject(result));
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetPositions(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.futureApi.getPositionsAsync();

                MessageBox.Show(resResult);

            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetPositionByInstrumentId(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.futureApi.getPositionByIdAsync("EOS-USD-191227");

                MessageBox.Show(resResult);

            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetFuturesAccounts(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.futureApi.getAccountsAsync();

                MessageBox.Show(resResult);

            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetOneCurencyAccounts(object sender, RoutedEventArgs e)
        {
            try
            {
                var currency = "btc-usd";
                var resResult = await this.futureApi.getAccountByCurrencyAsync(currency);
                JToken codeJToken;
                if (resResult.TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    //{"equity":"1.224","margin":"0","realized_pnl":"0","unrealized_pnl":"0","margin_ratio":"10000","margin_mode":"crossed","total_avail_balance":"1.224"}
                    var margin_mode = (string)resResult["margin_mode"];
                    if (margin_mode == "crossed")
                    {
                        //全仓
                        var accountInfo = resResult.ToObject<AccountCrossed>();
                        MessageBox.Show(currency + "：" + JsonConvert.SerializeObject(accountInfo));
                    }
                    else
                    {
                        //逐仓
                        var accountInfo = resResult.ToObject<AccountFixed>();
                        MessageBox.Show(currency + "：" + JsonConvert.SerializeObject(accountInfo));
                    }
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetLeverage(object sender, RoutedEventArgs e)
        {
            try
            {

                var resResult = await this.futureApi.getLeverageAsync("btc-usd");

                MessageBox.Show(resResult);

            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnSetCrossedLeverage(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.futureApi.setCrossedLeverageAsync("BTC-USDT", 20);
                JToken codeJToken;
                if (resResult.TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var result = resResult.ToObject<SetCrossedLeverageResult>();
                    MessageBox.Show(JsonConvert.SerializeObject(result));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnSetFixedLeverage(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.futureApi.setFixedLeverageAsync(this.currency.Text, int.Parse(this.leverage.Text), this.instrument_id.Text, this.direction.Text);
                JToken codeJToken;
                if (resResult.TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var result = resResult.ToObject<SetCrossedLeverageResult>();
                    MessageBox.Show(JsonConvert.SerializeObject(result));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetLedger(object sender, RoutedEventArgs e)
        {
            try
            {
                //[{"ledger_id":"1730792498235392","timestamp":"2018-11-02T08:03:17.0Z","amount":"-0.019","balance":"0","currency":"EOS","type":"match","details":{"order_id":0,"instrument_id":"EOS-USD-181228"}},{"ledger_id":"1730166161965057","timestamp":"2018-11-02T05:24:00.0Z","amount":"-0.007","balance":"0","currency":"EOS","type":"fee","details":{"order_id":1730166159284224,"instrument_id":"EOS-USD-181228"}},{"ledger_id":"1730166161965056","timestamp":"2018-11-02T05:24:00.0Z","amount":"-0.005","balance":"-13","currency":"EOS","type":"match","details":{"order_id":1730166159284224,"instrument_id":"EOS-USD-181228"}},{"ledger_id":"1730165947662337","timestamp":"2018-11-02T05:23:57.0Z","amount":"-0.007","balance":"0","currency":"EOS","type":"fee","details":{"order_id":1730165941482496,"instrument_id":"EOS-USD-181228"}},{"ledger_id":"1730165947662336","timestamp":"2018-11-02T05:23:57.0Z","amount":"0","balance":"13","currency":"EOS","type":"match","details":{"order_id":1730165941482496,"instrument_id":"EOS-USD-181228"}},{"ledger_id":"1691155227772928","timestamp":"2018-10-26T08:03:00.0Z","amount":"0.00052289","balance":"0","currency":"EOS","type":"match","details":{"order_id":0,"instrument_id":"EOS-USD-181228"}},{"ledger_id":"1689818088308738","timestamp":"2018-10-26T02:22:57.0Z","amount":"-0.00055597","balance":"0","currency":"EOS","type":"fee","details":{"order_id":1689818080547840,"instrument_id":"EOS-USD-181228"}},{"ledger_id":"1689818088308737","timestamp":"2018-10-26T02:22:57.0Z","amount":"0.004","balance":"-1","currency":"EOS","type":"match","details":{"order_id":1689818080547840,"instrument_id":"EOS-USD-181228"}},{"ledger_id":"1684987004485635","timestamp":"2018-10-25T05:54:21.0Z","amount":"-0.0005571","balance":"0","currency":"EOS","type":"fee","details":{"order_id":1684986992538624,"instrument_id":"EOS-USD-181228"}},{"ledger_id":"1684987004485634","timestamp":"2018-10-25T05:54:21.0Z","amount":"0","balance":"1","currency":"EOS","type":"match","details":{"order_id":1684986992538624,"instrument_id":"EOS-USD-181228"}},{"ledger_id":"1684986777075713","timestamp":"2018-10-25T05:54:17.0Z","amount":"-0.0005571","balance":"0","currency":"EOS","type":"fee","details":{"order_id":1684986768278528,"instrument_id":"EOS-USD-181228"}},{"ledger_id":"1684986777075712","timestamp":"2018-10-25T05:54:17.0Z","amount":"-0.001","balance":"-1","currency":"EOS","type":"match","details":{"order_id":1684986768278528,"instrument_id":"EOS-USD-181228"}},{"ledger_id":"1684129732001795","timestamp":"2018-10-25T02:16:20.0Z","amount":"-0.00055741","balance":"0","currency":"EOS","type":"fee","details":{"order_id":1684129718574080,"instrument_id":"EOS-USD-181228"}},{"ledger_id":"1684129732001794","timestamp":"2018-10-25T02:16:20.0Z","amount":"0","balance":"1","currency":"EOS","type":"match","details":{"order_id":1684129718574080,"instrument_id":"EOS-USD-181228"}},{"ledger_id":"1651521273235456","timestamp":"2018-10-19T08:03:34.0Z","amount":"-0.2","balance":"0","currency":"EOS","type":"match","details":{"order_id":0,"instrument_id":"EOS-USD-181228"}},{"ledger_id":"1651521271793668","timestamp":"2018-10-19T08:03:34.0Z","amount":"0","balance":"0","currency":"EOS","type":"match","details":{"order_id":0,"instrument_id":"EOS-USD-181019"}}]
                var resResult = await this.futureApi.getLedgersByCurrencyAsync("btc-usdt", null, null, 10);

                MessageBox.Show(resResult);

            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private Dictionary<string, string> orderTypes { get; set; } = new Dictionary<string, string>();

        private async void btnMakeOrder(object sender, RoutedEventArgs e)
        {
            try
            {
                var order = ((MainViewModel)this.DataContext).OrderSingle;
                var resResult = await this.futureApi.makeOrderAsync(order.instrument_id,
                    order.type,
                    order.price,
                    order.size,
                    order.leverage,
                    order.client_oid,
                    order.match_price == "True" ? "1" : "0");

                MessageBox.Show(resResult);

            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnMakeOrderBatch(object sender, RoutedEventArgs e)
        {
            try
            {
                var order = ((MainViewModel)this.DataContext).OrderBatch;
                var orderDetails = ((MainViewModel)this.DataContext).OrderDetails;
                orderDetails.ForEach(od =>
                {
                    od.match_price = od.match_price == "True" ? "1" : "0";
                });
                order.orders_data = JsonConvert.SerializeObject(orderDetails);
                var resResult = await this.futureApi.makeOrdersBatchAsync(order);

                MessageBox.Show(resResult);

            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnCancelOrder(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.futureApi.cancelOrderAsync(this.instrument_id_cancel.Text, this.order_id.Text);

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var cancelResult = resResult.ToObject<CancelOrderResult>();
                    MessageBox.Show(JsonConvert.SerializeObject(cancelResult));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnCancelOrderBatch(object sender, RoutedEventArgs e)
        {
            try
            {
                var orderIds = new List<long>();
                orderIds.Add(long.Parse(this.order_id.Text));
                var resResult = await this.futureApi.cancelOrderBatchAsync(this.instrument_id_cancel.Text, orderIds);

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var cancelResult = resResult.ToObject<CancelOrderBatchResult>();
                    MessageBox.Show(JsonConvert.SerializeObject(cancelResult));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetOrders(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.futureApi.getOrdersAsync("TRX-USD-191122", "2", null, null, 10);

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var ledgers = resResult.ToObject<OrderListResult>();
                    MessageBox.Show(JsonConvert.SerializeObject(ledgers));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetOrderById(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.futureApi.getOrderByIdAsync("TRX-USD-191122", "376549434978166784");


                MessageBox.Show(resResult);

            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetFills(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.futureApi.getFillsAsync("TRX-USD-191129", "376549434978166784", null, null, 10);
                if (resResult.Type == JTokenType.Object)
                {
                    JToken codeJToken;
                    if (((JObject)resResult).TryGetValue("code", out codeJToken))
                    {
                        var errorInfo = resResult.ToObject<ErrorResult>();
                        MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                    }
                }
                else
                {
                    var fills = resResult.ToObject<List<Fill>>();
                    MessageBox.Show(JsonConvert.SerializeObject(fills));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetInstruments(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.futureApi.getInstrumentsAsync();
                if (resResult.Type == JTokenType.Object)
                {
                    JToken codeJToken;
                    if (((JObject)resResult).TryGetValue("code", out codeJToken))
                    {
                        var errorInfo = resResult.ToObject<ErrorResult>();
                        MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                    }
                }
                else
                {
                    var instruments = resResult.ToObject<List<Instrument>>();
                    MessageBox.Show(JsonConvert.SerializeObject(instruments));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetBook(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.futureApi.getBookAsync("EOS-USD-191227", 20);
                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var book = resResult.ToObject<Book>();
                    MessageBox.Show(JsonConvert.SerializeObject(book));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetTickers(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.futureApi.getTickersAsync();
                if (resResult.Type == JTokenType.Object)
                {
                    JToken codeJToken;
                    if (((JObject)resResult).TryGetValue("code", out codeJToken))
                    {
                        var errorInfo = resResult.ToObject<ErrorResult>();
                        MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                    }
                }
                else
                {
                    var ticker = resResult.ToObject<List<Ticker>>();
                    MessageBox.Show(JsonConvert.SerializeObject(ticker));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetTickerById(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.futureApi.getTickerByInstrumentId("EOS-USD-191227");

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var ticker = resResult.ToObject<Ticker>();
                    MessageBox.Show(JsonConvert.SerializeObject(ticker));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetTrades(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.futureApi.getTradesAsync("BTC-USD-191129", 1, null, 10);
                if (resResult.Type == JTokenType.Object)
                {
                    JToken codeJToken;
                    if (((JObject)resResult).TryGetValue("code", out codeJToken))
                    {
                        var errorInfo = resResult.ToObject<ErrorResult>();
                        MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                    }
                }
                else
                {
                    var trades = resResult.ToObject<List<Trade>>();
                    MessageBox.Show(JsonConvert.SerializeObject(trades));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetKData(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.futureApi.getCandlesDataAsync("EOS-USD-191227", DateTime.UtcNow.AddMinutes(-1), DateTime.UtcNow, 60);
                MessageBox.Show(resResult);
                //if (resResult.Type == JTokenType.Object)
                //{
                //    JToken codeJToken;
                //    if (((JObject)resResult).TryGetValue("code", out codeJToken))
                //    {
                //        var errorInfo = resResult.ToObject<ErrorResult>();
                //        MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                //    }
                //}
                //else
                //{
                //    var candles = resResult.ToObject<List<List<decimal>>>();

                //    MessageBox.Show(JsonConvert.SerializeObject(candles));
                //}
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetIndex(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.futureApi.getIndexAsync("EOS-USD-191227");

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var index = resResult.ToObject<Index>();
                    MessageBox.Show(JsonConvert.SerializeObject(index));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetRate(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.futureApi.getRateAsync();

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var rate = resResult.ToObject<Rate>();
                    MessageBox.Show(JsonConvert.SerializeObject(rate));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetEstimatedPrice(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.futureApi.getEstimatedPriceAsync("EOS-USD-191227");

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var estimatedPrice = resResult.ToObject<EstimatedPrice>();
                    MessageBox.Show(JsonConvert.SerializeObject(estimatedPrice));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetOpenInteest(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.futureApi.getOpenInterestAsync("EOS-USD-191227");

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var openInterest = resResult.ToObject<OpenInterest>();
                    MessageBox.Show(JsonConvert.SerializeObject(openInterest));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnPriceLimit(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.futureApi.getPriceLimitAsync("EOS-USD-191227");

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var priceLimit = resResult.ToObject<PriceLimit>();
                    MessageBox.Show(JsonConvert.SerializeObject(priceLimit));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnLiquidation(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.futureApi.getLiquidationAsync("BTC-USD-191129", "0", 1, null, 10);
                if (resResult.Type == JTokenType.Object)
                {
                    JToken codeJToken;
                    if (((JObject)resResult).TryGetValue("code", out codeJToken))
                    {
                        var errorInfo = resResult.ToObject<ErrorResult>();
                        MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                    }
                }
                else
                {
                    var liquidations = resResult.ToObject<List<Liquidation>>();
                    MessageBox.Show(JsonConvert.SerializeObject(liquidations));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnHolds(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.futureApi.getHoldsAsync("EOS-USD-191227");

                MessageBox.Show(resResult);
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetCurrencies(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.accountApi.getCurrenciesAsync();
                if (resResult.Type == JTokenType.Object)
                {
                    JToken codeJToken;
                    if (((JObject)resResult).TryGetValue("code", out codeJToken))
                    {
                        var errorInfo = resResult.ToObject<ErrorResult>();
                        MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                    }
                }
                else
                {
                    var currencies = resResult.ToObject<List<Currency>>();
                    MessageBox.Show(JsonConvert.SerializeObject(currencies));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetWallet(object sender, RoutedEventArgs e)
        {
            //资金账户信息
            try
            {
                var resResult = await this.accountApi.getWalletInfoAsync();
                MessageBox.Show(resResult);
                //if (resResult.Type == JTokenType.Object)
                //{

                //    JToken codeJToken;
                //    if (((JObject)resResult).TryGetValue("code", out codeJToken))
                //    {
                //        var errorInfo = resResult.ToObject<ErrorResult>();
                //        MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                //    }
                //}
                //else
                //{
                //    var walletInfo = resResult.ToObject<List<Wallet>>();
                //    MessageBox.Show(JsonConvert.SerializeObject(walletInfo));
                //}
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetWalletByCurrency(object sender, RoutedEventArgs e)
        {
            //单一账户币种信息
            try
            {
                var resResult = await this.accountApi.getWalletInfoByCurrencyAsync("OKB");
                if (resResult.Type == JTokenType.Object)
                {
                    JToken codeJToken;
                    if (((JObject)resResult).TryGetValue("code", out codeJToken))
                    {
                        var errorInfo = resResult.ToObject<ErrorResult>();
                        MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                    }
                }
                else
                {
                    var walletInfo = resResult.ToObject<List<Wallet>>();
                    MessageBox.Show(JsonConvert.SerializeObject(walletInfo));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnMakeTransfer(object sender, RoutedEventArgs e)
        {
            //资金划转
            try
            {
                var resResult = await this.accountApi.makeTransferAsync(((MainViewModel)this.DataContext).Transfer);

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var transferResult = resResult.ToObject<TransferResult>();
                    MessageBox.Show(JsonConvert.SerializeObject(transferResult));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnMakeWithDrawal(object sender, RoutedEventArgs e)
        {
            //提币
            try
            {
                var resResult = await this.accountApi.makeWithDrawalAsync(((MainViewModel)this.DataContext).WithDrawal);

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var withdrawalResult = resResult.ToObject<WithDrawalResult>();
                    MessageBox.Show(JsonConvert.SerializeObject(withdrawalResult));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetWithdrawalFee(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.accountApi.getWithDrawalFeeAsync("eos");
                if (resResult.Type == JTokenType.Object)
                {
                    JToken codeJToken;
                    if (((JObject)resResult).TryGetValue("code", out codeJToken))
                    {
                        var errorInfo = resResult.ToObject<ErrorResult>();
                        MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                    }
                }
                else
                {
                    var fee = resResult.ToObject<List<WithdrawalFee>>();
                    MessageBox.Show(JsonConvert.SerializeObject(fee));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetWithdrawalHistory(object sender, RoutedEventArgs e)
        {
            //提币记录
            try
            {
                var resResult = await this.accountApi.getWithDrawalHistoryAsync();
                if (resResult.Type == JTokenType.Object)
                {
                    JToken codeJToken;
                    if (((JObject)resResult).TryGetValue("code", out codeJToken))
                    {
                        var errorInfo = resResult.ToObject<ErrorResult>();
                        MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                    }
                }
                else
                {
                    var history = resResult.ToObject<List<WithDrawalHistory>>();
                    MessageBox.Show(JsonConvert.SerializeObject(history));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetWithdrawalHistoryByCurrency(object sender, RoutedEventArgs e)
        {
            //单个币种提币记录
            try
            {
                var resResult = await this.accountApi.getWithDrawalHistoryByCurrencyAsync("eos");
                if (resResult.Type == JTokenType.Object)
                {
                    JToken codeJToken;
                    if (((JObject)resResult).TryGetValue("code", out codeJToken))
                    {
                        var errorInfo = resResult.ToObject<ErrorResult>();
                        MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                    }
                }
                else
                {
                    var history = resResult.ToObject<List<WithDrawalHistory>>();
                    MessageBox.Show(JsonConvert.SerializeObject(history));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetWalletLedger(object sender, RoutedEventArgs e)
        {
            //账单流水查询
            try
            {
                var resResult = await this.accountApi.getLedgerAsync("eos", "2", 1, null, 10);
                if (resResult.Type == JTokenType.Object)
                {
                    JToken codeJToken;
                    if (((JObject)resResult).TryGetValue("code", out codeJToken))
                    {
                        var errorInfo = resResult.ToObject<ErrorResult>();
                        MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                    }
                }
                else
                {
                    var walletLedger = resResult.ToObject<List<AccountLedger>>();
                    MessageBox.Show(JsonConvert.SerializeObject(walletLedger));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetDepositAddress(object sender, RoutedEventArgs e)
        {
            //获取充值地址
            try
            {
                var resResult = await this.accountApi.getDepositAddressAsync("eos");

                MessageBox.Show(resResult);
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetDepositHistory(object sender, RoutedEventArgs e)
        {
            //获取所有币种充值记录
            try
            {
                var resResult = await this.accountApi.getDepositHistoryAsync();
                if (resResult.Type == JTokenType.Object)
                {
                    JToken codeJToken;
                    if (((JObject)resResult).TryGetValue("code", out codeJToken))
                    {
                        var errorInfo = resResult.ToObject<ErrorResult>();
                        MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                    }
                }
                else
                {
                    var history = resResult.ToObject<List<DepositHistory>>();
                    MessageBox.Show(JsonConvert.SerializeObject(history));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetDepositHistoryByCurrency(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.accountApi.getDepositHistoryByCurrencyAsync("eth");
                if (resResult.Type == JTokenType.Object)
                {
                    JToken codeJToken;
                    if (((JObject)resResult).TryGetValue("code", out codeJToken))
                    {
                        var errorInfo = resResult.ToObject<ErrorResult>();
                        MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                    }
                }
                else
                {
                    var history = resResult.ToObject<List<DepositHistory>>();
                    MessageBox.Show(JsonConvert.SerializeObject(history));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetSpotAccount(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.spotApi.getSpotAccountsAsync();
                MessageBox.Show(resResult);
                //if (resResult.Type == JTokenType.Object)
                //{
                //    JToken codeJToken;
                //    if (((JObject)resResult).TryGetValue("code", out codeJToken))
                //    {
                //        var errorInfo = resResult.ToObject<ErrorResult>();
                //        MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                //    }
                //}
                //else
                //{
                //    var spotaccount = resResult.ToObject<List<SpotAccount>>();
                //    MessageBox.Show(JsonConvert.SerializeObject(spotaccount));
                //}
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetSpotAccountByCurrency(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.spotApi.getAccountByCurrencyAsync("eos");


                MessageBox.Show(resResult);
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetSpotLedger(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.spotApi.getSpotLedgerByCurrencyAsync("trx", null, null, 10, null);
                if (resResult.Type == JTokenType.Object)
                {
                    JToken codeJToken;
                    if (((JObject)resResult).TryGetValue("code", out codeJToken))
                    {
                        var errorInfo = resResult.ToObject<ErrorResult>();
                        MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                    }
                }
                else
                {
                    var walletLedger = resResult.ToObject<List<SpotLedger>>();
                    MessageBox.Show(JsonConvert.SerializeObject(walletLedger));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnMakeMarketOrder(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.spotApi.makeOrderAsync<SpotOrderMarket>(((MainViewModel)this.DataContext).SpotOrderMarket);

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var orderResult = resResult.ToObject<SpotOrderResult>();
                    MessageBox.Show(JsonConvert.SerializeObject(orderResult));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnMakeLimitOrder(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.spotApi.makeOrderAsync<SpotOrderLimit>(((MainViewModel)this.DataContext).SpotOrderLimit);

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var orderResult = resResult.ToObject<SpotOrderResult>();
                    MessageBox.Show(JsonConvert.SerializeObject(orderResult));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }
        private async void btnMakeMarketOrderBatch(object sender, RoutedEventArgs e)
        {
            try
            {
                var order = ((MainViewModel)this.DataContext).SpotOrderMarket;
                var resResult = await this.spotApi.makeOrderBatchAsync<SpotOrderMarket>(new List<SpotOrderMarket>() { order });

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var obj = resResult.Value<JObject>();
                    foreach (var property in obj)
                    {
                        MessageBox.Show(property.Key + ":" + JsonConvert.SerializeObject(property.Value.ToObject<List<SpotOrderResult>>()));
                    }
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }
        private async void btnMakeLimitOrderBatch(object sender, RoutedEventArgs e)
        {
            try
            {
                var order = ((MainViewModel)this.DataContext).SpotOrderLimit;
                var resResult = await this.spotApi.makeOrderBatchAsync<SpotOrderLimit>(new List<SpotOrderLimit>() { order });

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var obj = resResult.Value<JObject>();
                    foreach (var property in obj)
                    {
                        MessageBox.Show(property.Key + ":" + JsonConvert.SerializeObject(property.Value.ToObject<List<SpotOrderResult>>()));
                    }
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnCancelSpotOrder(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.spotApi.cancelOrderByOrderIdAsync(this.spot_order_id.Text, this.spot_instrument_id.Text, null);

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var orderResult = resResult.ToObject<SpotOrderResult>();
                    MessageBox.Show(JsonConvert.SerializeObject(orderResult));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnCancelSpotOrderBatch(object sender, RoutedEventArgs e)
        {
            try
            {
                var orders = new List<CancelOrderBatch>();
                var order = new CancelOrderBatch()
                {
                    instrument_id = this.spot_instrument_id.Text,
                    order_ids = new List<long>() { long.Parse(this.spot_order_id.Text) },
                };
                orders.Add(order);
                var resResult = await this.spotApi.cancelOrderBatchAsync(orders);

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var obj = resResult.Value<JObject>();
                    foreach (var property in obj)
                    {
                        MessageBox.Show(property.Key + ":" + JsonConvert.SerializeObject(property.Value.ToObject<List<SpotOrderResult>>()));
                    }
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetSpotOrders(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.spotApi.getOrdersAsync("BTC-USDT", "2", null, null, 10);
                if (resResult.Type == JTokenType.Object)
                {
                    JToken codeJToken;
                    if (((JObject)resResult).TryGetValue("code", out codeJToken))
                    {
                        var errorInfo = resResult.ToObject<ErrorResult>();
                        MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                    }
                }
                else
                {
                    var orders = resResult.ToObject<List<OrderFullInfo>>();
                    MessageBox.Show(JsonConvert.SerializeObject(orders));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetPendingOrders(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.spotApi.getPendingOrdersAsync("CAI-BTC", null, null, 10);
                if (resResult.Type == JTokenType.Object)
                {
                    JToken codeJToken;
                    if (((JObject)resResult).TryGetValue("code", out codeJToken))
                    {
                        var errorInfo = resResult.ToObject<ErrorResult>();
                        MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                    }
                }
                else
                {
                    var orders = resResult.ToObject<List<OrderFullInfo>>();
                    MessageBox.Show(JsonConvert.SerializeObject(orders));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetSpotOrderById(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.spotApi.getOrderByIdAsync(this.spotinstrument_id.Text, this.spotorder_id.Text);

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var fills = resResult.ToObject<OrderFullInfo>();
                    MessageBox.Show(JsonConvert.SerializeObject(fills));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }
        private async void btnGetSpotFills(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.spotApi.getFillsAsync(long.Parse(this.spotorder_id.Text), this.spotinstrument_id.Text, 1, null, 10);

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var orderResult = resResult.ToObject<List<SpotFill>>();
                    MessageBox.Show(JsonConvert.SerializeObject(orderResult));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetSpotInstruments(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.spotApi.getInstrumentsAsync();

                if (resResult.Type == JTokenType.Object)
                {
                    JToken codeJToken;
                    if (((JObject)resResult).TryGetValue("code", out codeJToken))
                    {
                        var errorInfo = resResult.ToObject<ErrorResult>();
                        MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                    }
                }
                else
                {
                    var instruments = resResult.ToObject<List<SpotInstrument>>();
                    MessageBox.Show(JsonConvert.SerializeObject(instruments.Take(10)));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetSpotBook(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.spotApi.getBookAsync("CAI-BTC", null, null);
                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var book = resResult.ToObject<SpotBook>();
                    MessageBox.Show(JsonConvert.SerializeObject(book));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetSpotTicker(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.spotApi.getTickerAsync();

                if (resResult.Type == JTokenType.Object)
                {
                    JToken codeJToken;
                    if (((JObject)resResult).TryGetValue("code", out codeJToken))
                    {
                        var errorInfo = resResult.ToObject<ErrorResult>();
                        MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                    }
                }
                else
                {
                    var tickers = resResult.ToObject<List<SpotTicker>>();
                    MessageBox.Show(JsonConvert.SerializeObject(tickers.Take(10)));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetSpotTickerByInstrument(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.spotApi.getTickerByInstrumentIdAsync("BTC-USDT");


                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var ticker = resResult.ToObject<SpotTicker>();
                    MessageBox.Show(JsonConvert.SerializeObject(ticker));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetSpotTrades(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.spotApi.getTradesAasync("CAI-BTC", null, null, 10);

                if (resResult.Type == JTokenType.Object)
                {
                    JToken codeJToken;
                    if (((JObject)resResult).TryGetValue("code", out codeJToken))
                    {
                        var errorInfo = resResult.ToObject<ErrorResult>();
                        MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                    }
                }
                else
                {
                    var trades = resResult.ToObject<List<SpotTrade>>();
                    MessageBox.Show(JsonConvert.SerializeObject(trades));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetSpotCandles(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.spotApi.getCandlesAsync("CAI-BTC", DateTime.UtcNow.AddHours(-1), DateTime.UtcNow, 60);


                MessageBox.Show(resResult);

            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetMarginAccount(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.marginApi.getAccountsAsync();


                MessageBox.Show(resResult);

            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetMarginAccountByInstrument(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.marginApi.getAccountsByInstrumentIdAsync("BTC_USDT");


                MessageBox.Show(resResult);
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetMarginLedger(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.marginApi.getLedgerAsync("BTC_USDT", null, null, null, 10);
                if (resResult.Type == JTokenType.Object)
                {
                    JToken codeJToken;
                    if (((JObject)resResult).TryGetValue("code", out codeJToken))
                    {
                        var errorInfo = resResult.ToObject<ErrorResult>();
                        MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                    }
                }
                else
                {
                    var marginLedger = resResult.ToObject<List<MarginLedger>>();
                    MessageBox.Show(JsonConvert.SerializeObject(marginLedger));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetAvailability(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.marginApi.getAvailabilityAsync();

                MessageBox.Show(resResult);
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetAvailable(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.marginApi.getAvailabilityByInstrumentId("BTC_USDT");

                MessageBox.Show(resResult);

            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetBorrowed(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.marginApi.getBorrowedAsync("0", null, null, 10);

                MessageBox.Show(resResult);

            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetBorrowByInstrument(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.marginApi.getBorrowedByInstrumentIdAsync("BTC_USDT", "0", null, null, 10);
                if (resResult.Type == JTokenType.Object)
                {
                    JToken codeJToken;
                    if (((JObject)resResult).TryGetValue("code", out codeJToken))
                    {
                        var errorInfo = resResult.ToObject<ErrorResult>();
                        MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                    }
                }
                else
                {
                    var borrows = resResult.ToObject<List<Borrowed>>();
                    MessageBox.Show(JsonConvert.SerializeObject(borrows));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnMakeBorrow(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.marginApi.makeBorrowAsync(this.borrow_instrument_id.Text, this.borrow_currency.Text, this.borrow_amount.Text);

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var borrowResult = resResult.ToObject<BorrowResult>();
                    MessageBox.Show(JsonConvert.SerializeObject(borrowResult));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnMakeRepayment(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.marginApi.makeRepaymentAsync(long.Parse(this.repay_borrow_id.Text), this.repay_instrument_id.Text, this.repay_currency.Text, this.repay_amount.Text);

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var borrowResult = resResult.ToObject<RepaymentResult>();
                    MessageBox.Show(JsonConvert.SerializeObject(borrowResult));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnMakeMarginMarketOrder(object sender, RoutedEventArgs e)
        {
            try
            {
                var order = ((MainViewModel)this.DataContext).MarginOrderMarket;
                order.order_type = "0";
                var resResult = await this.marginApi.makeOrderAsync<MarginOrderMarket>(order);


                MessageBox.Show(resResult);

            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnMakeMarginMarketOrderBatch(object sender, RoutedEventArgs e)
        {
            try
            {
                var order = ((MainViewModel)this.DataContext).MarginOrderMarket;
                order.order_type = "0";
                var resResult = await this.marginApi.makeOrderBatchAsync<MarginOrderMarket>(new List<MarginOrderMarket>() { order });


                MessageBox.Show(resResult);

            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnMakeMarginLimitOrder(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.marginApi.makeOrderAsync<MarginOrderLimit>(((MainViewModel)this.DataContext).MarginOrderLimit);

                MessageBox.Show(resResult);

            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnMakeMarginLimitOrderBatch(object sender, RoutedEventArgs e)
        {
            try
            {
                var order = ((MainViewModel)this.DataContext).MarginOrderLimit;
                order.order_type = "0";
                var resResult = await this.marginApi.makeOrderBatchAsync<MarginOrderLimit>(new List<MarginOrderLimit>() { order });

                MessageBox.Show(resResult);

            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnCancelMarginOrder(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.marginApi.cancelOrderByOrderIdAsync(this.margin_order_id.Text, this.margin_instrument_id.Text, null);

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var orderResult = resResult.ToObject<MarginOrderResult>();
                    MessageBox.Show(JsonConvert.SerializeObject(orderResult));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnCancelMarginOrderBatch(object sender, RoutedEventArgs e)
        {
            try
            {
                var orders = new List<MarginCancelOrderBatch>();
                var order = new MarginCancelOrderBatch()
                {
                    instrument_id = this.margin_instrument_id.Text,
                    order_ids = new List<long>() { long.Parse(this.margin_order_id.Text) },
                };
                orders.Add(order);
                var resResult = await this.marginApi.cancelOrderBatchAsync(orders);

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var obj = resResult.Value<JObject>();
                    foreach (var property in obj)
                    {
                        MessageBox.Show(property.Key + ":" + JsonConvert.SerializeObject(property.Value.ToObject<List<MarginOrderResult>>()));
                    }
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetMarginOrderById(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.marginApi.getOrderByIdAsync(this.margininstrument_id.Text, this.marginorder_id.Text);

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var fills = resResult.ToObject<MarginOrderFullInfo>();
                    MessageBox.Show(JsonConvert.SerializeObject(fills));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetMarginFills(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.marginApi.getFillsAsync(long.Parse(this.marginorder_id.Text), this.margininstrument_id.Text, 1, null, 10);

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var orderResult = resResult.ToObject<List<MarginFill>>();
                    MessageBox.Show(JsonConvert.SerializeObject(orderResult));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetMarginOrders(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.marginApi.getOrdersAsync("BTC-USDT", "2", null, null, 10);
                if (resResult.Type == JTokenType.Object)
                {
                    JToken codeJToken;
                    if (((JObject)resResult).TryGetValue("code", out codeJToken))
                    {
                        var errorInfo = resResult.ToObject<ErrorResult>();
                        MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                    }
                }
                else
                {
                    var orders = resResult.ToObject<List<MarginOrderFullInfo>>();
                    MessageBox.Show(JsonConvert.SerializeObject(orders));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetMarginPendingOrder(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.marginApi.getPendingOrdersAsync("CAI-BTC", null, null, 10);

                MessageBox.Show(resResult);

            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }
        private async void btnGetOrders_margin(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.marginApi.btnGetOrders("btc-usdt", "23458");

                MessageBox.Show(resResult);

            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }
        private async void btnGetEttAccount(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.ettApi.getAccountsAsync();
                if (resResult.Type == JTokenType.Object)
                {
                    JToken codeJToken;
                    if (((JObject)resResult).TryGetValue("code", out codeJToken))
                    {
                        var errorInfo = resResult.ToObject<ErrorResult>();
                        MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                    }
                }
                else
                {
                    var accounts = resResult.ToObject<List<EttAccount>>();
                    MessageBox.Show(JsonConvert.SerializeObject(accounts));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetEttAccountByCurrency(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.ettApi.getAccountByCurrencyAsync("eos");

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var accounts = resResult.ToObject<EttAccount>();
                    MessageBox.Show(JsonConvert.SerializeObject(accounts));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetEttLedger(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.ettApi.getLedgerAsync("eos", 1, null, 10);
                if (resResult.Type == JTokenType.Object)
                {
                    JToken codeJToken;
                    if (((JObject)resResult).TryGetValue("code", out codeJToken))
                    {
                        var errorInfo = resResult.ToObject<ErrorResult>();
                        MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                    }
                }
                else
                {
                    var ettLedger = resResult.ToObject<List<EttLedger>>();
                    MessageBox.Show(JsonConvert.SerializeObject(ettLedger));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnMakeEttOrder(object sender, RoutedEventArgs e)
        {
            try
            {
                var order = ((MainViewModel)this.DataContext).EttOrder;
                var resResult = await this.ettApi.makeOrderAsync(order);
                JToken codeJToken;
                if (resResult.TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var orderResult = resResult.ToObject<EttOrderResult>();
                    MessageBox.Show(JsonConvert.SerializeObject(orderResult));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetEttOrders(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.ettApi.getOrdersAsync("ok06ett", "1", "0", 1, null, 10);
                if (resResult.Type == JTokenType.Object)
                {
                    JToken codeJToken;
                    if (((JObject)resResult).TryGetValue("code", out codeJToken))
                    {
                        var errorInfo = resResult.ToObject<ErrorResult>();
                        MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                    }
                }
                else
                {
                    var ettLedger = resResult.ToObject<List<OrderFullInfo>>();
                    MessageBox.Show(JsonConvert.SerializeObject(ettLedger));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetPositionByInstrumentSwap(object sender, RoutedEventArgs e)
        {
            try
            {
                string instrumentId = "BTC-USD-SWAP";
                var resResult = await this.swapApi.getPositionByInstrumentAsync(instrumentId);
                MessageBox.Show(JsonConvert.SerializeObject(resResult));
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetSwapAccounts(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.swapApi.getAccountsAsync();

                MessageBox.Show(JsonConvert.SerializeObject(resResult));
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetOneAccountSwap(object sender, RoutedEventArgs e)
        {
            try
            {
                string instrumentId = "BTC-USD-SWAP";
                var resResult = await this.swapApi.getAccountsByInstrumentAsync(instrumentId);
                JToken codeJToken;
                if (resResult.TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    MessageBox.Show(JsonConvert.SerializeObject(resResult.ToObject<swap.AccountResult>()));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetSwapSettings(object sender, RoutedEventArgs e)
        {
            try
            {
                string instrumentId = "BTC-USD-SWAP";
                var resResult = await this.swapApi.getSettingsByInstrumentAsync(instrumentId);
                JToken codeJToken;
                if (resResult.TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    MessageBox.Show(JsonConvert.SerializeObject(resResult.ToObject<swap.Leverage>()));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnSetInstrumentLeverage(object sender, RoutedEventArgs e)
        {
            try
            {
                string instrumentId = "BTC-USD-SWAP";
                int leverage = 12;
                string side = "1";
                var resResult = await this.swapApi.setLeverageByInstrumentAsync(instrumentId, leverage, side);
                JToken codeJToken;
                if (resResult.TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    MessageBox.Show(JsonConvert.SerializeObject(resResult.ToObject<swap.Leverage>()));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetLedgerSwap(object sender, RoutedEventArgs e)
        {
            try
            {
                string instrument_id = "ETC-USD-SWAP";
                int after = 1;
                int before = 0;
                int limit = 10;
                int type = 0;
                var resResult = await this.swapApi.getLedgersByInstrumentAsync(instrument_id, null, null, limit, null);
                if (resResult.Type == JTokenType.Object)
                {
                    JToken codeJToken;
                    if (((JObject)resResult).TryGetValue("code", out codeJToken))
                    {
                        var errorInfo = resResult.ToObject<ErrorResult>();
                        MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                    }
                }
                else
                {
                    var ledgers = resResult.ToObject<List<swap.Ledger>>();
                    MessageBox.Show(JsonConvert.SerializeObject(ledgers));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnMakeOrderSwap(object sender, RoutedEventArgs e)
        {
            try
            {
                var order = ((MainViewModel)this.DataContext).SwapOrderSingle;
                order.client_oid = null;
                var resResult = await this.swapApi.makeOrderAsync(order.instrument_id,
                    order.type,
                    order.price,
                    order.size,
                    order.client_oid,
                    order.match_price == "True" ? "1" : "0");
                JToken codeJToken;
                if (resResult.TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var orderResult = resResult.ToObject<swap.OrderResultSingle>();
                    MessageBox.Show(JsonConvert.SerializeObject(orderResult));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnMakeOrderBatchSwap(object sender, RoutedEventArgs e)
        {
            try
            {
                var order = ((MainViewModel)this.DataContext).OrderBatchSwap;
                var orderDetails = ((MainViewModel)this.DataContext).OrderDetailsSwap;
                orderDetails.ForEach(od =>
                {
                    od.match_price = od.match_price == "True" ? "1" : "0";
                });

                order.order_data = JsonConvert.SerializeObject(orderDetails);
                var resResult = await this.swapApi.makeOrdersBatchAsync(order);

                MessageBox.Show(resResult);

            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnCancelOrderSwap(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.swapApi.cancelOrderAsync(this.instrument_id_cancel_swap.Text, this.order_id_swap.Text);

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var cancelResult = resResult.ToObject<swap.CancelOrderResult>();
                    MessageBox.Show(JsonConvert.SerializeObject(cancelResult));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnCancelOrderBatchSwap(object sender, RoutedEventArgs e)
        {
            try
            {
                var orderIds = new List<string>();
                orderIds.Add(this.order_id_swap.Text);
                var resResult = await this.swapApi.cancelOrderBatchAsync(this.instrument_id_cancel_swap.Text, orderIds);

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var cancelResult = resResult.ToObject<swap.CancelOrderBatchResult>();
                    MessageBox.Show(JsonConvert.SerializeObject(cancelResult));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetOrdersSwap(object sender, RoutedEventArgs e)
        {
            try
            {
                string instrumentId = "ETC-USD-SWAP";
                int after = 10;
                int before = 0;
                int limit = 10;
                string state = "2";
                var resResult = await this.swapApi.getOrdersAsync(instrumentId, "2", null, null, limit);

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var ledgers = resResult.ToObject<swap.OrderListResult>();
                    MessageBox.Show(JsonConvert.SerializeObject(ledgers));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }
        private async void btnGetOrder_algoSpot(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.spotApi.getOrder_algoAsync("BTC-USDT", 1, 2, null, null, null, null);

                MessageBox.Show(resResult);
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }
        private async void btnGetOrder_algoSwap(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.swapApi.getOrder_algoAsync("ETC-USD-SWAP", 1, 2, null, null, null, null);

                MessageBox.Show(resResult);
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }
        private async void btnTrade_fee(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.swapApi.getTrade_fee();

                MessageBox.Show(resResult);
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }
        private async void btnorder_algo_swap(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.swapApi.order_algo("BTC-USD-SWAP", "1", "1", "1", "432.11", "341.99");

                MessageBox.Show(resResult);
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }
        private async void btncancel_algos_swap(object sender, RoutedEventArgs e)
        {
            try
            {
                List<string> algo_ids = new List<string>();
                algo_ids.Add("1");
                string str_algoIds = JsonConvert.SerializeObject(algo_ids);
                var resResult = await this.swapApi.cancel_algos("ETC-USD-191206", str_algoIds, "1");

                MessageBox.Show(resResult);
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetOrderByIdSwap(object sender, RoutedEventArgs e)
        {
            try
            {
                string instrumentId = "ETC-USD-SWAP";
                string orderId = "376549434978166784";//orderid或clientid
                var resResult = await this.swapApi.getOrderByIdAsync(instrumentId, orderId);

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var ledgers = resResult.ToObject<swap.Order>();
                    MessageBox.Show(JsonConvert.SerializeObject(ledgers));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetFillsSwap(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.swapApi.getFillsAsync("ETC-USD-SWAP", "376549434978166784", null, null, 10);
                if (resResult.Type == JTokenType.Object)
                {
                    JToken codeJToken;
                    if (((JObject)resResult).TryGetValue("code", out codeJToken))
                    {
                        var errorInfo = resResult.ToObject<ErrorResult>();
                        MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                    }
                }
                else
                {
                    var fills = resResult.ToObject<List<swap.Fill>>();
                    MessageBox.Show(JsonConvert.SerializeObject(fills));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetInstrumentsSwap(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.swapApi.getInstrumentsAsync();
                if (resResult.Type == JTokenType.Object)
                {
                    JToken codeJToken;
                    if (((JObject)resResult).TryGetValue("code", out codeJToken))
                    {
                        var errorInfo = resResult.ToObject<ErrorResult>();
                        MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                    }
                }
                else
                {
                    var instruments = resResult.ToObject<List<swap.Instrument>>();
                    MessageBox.Show(JsonConvert.SerializeObject(instruments));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetDepthSwap(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.swapApi.getBookAsync("BTC-USD-SWAP", null, 20);
                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var depth = resResult.ToObject<swap.Depth>();
                    MessageBox.Show(JsonConvert.SerializeObject(depth));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetTickersSwap(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.swapApi.getTickersAsync();
                if (resResult.Type == JTokenType.Object)
                {
                    JToken codeJToken;
                    if (((JObject)resResult).TryGetValue("code", out codeJToken))
                    {
                        var errorInfo = resResult.ToObject<ErrorResult>();
                        MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                    }
                }
                else
                {
                    var ticker = resResult.ToObject<List<swap.Ticker>>();
                    MessageBox.Show(JsonConvert.SerializeObject(ticker));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetTickerByIdSwap(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.swapApi.getTickerByInstrumentId("BTC-USD-SWAP");

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var ticker = resResult.ToObject<swap.Ticker>();
                    MessageBox.Show(JsonConvert.SerializeObject(ticker));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetTradesSwap(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.swapApi.getTradesAsync("ETC-USD-SWAP", null, null, 10);
                if (resResult.Type == JTokenType.Object)
                {
                    JToken codeJToken;
                    if (((JObject)resResult).TryGetValue("code", out codeJToken))
                    {
                        var errorInfo = resResult.ToObject<ErrorResult>();
                        MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                    }
                }
                else
                {
                    var trades = resResult.ToObject<List<swap.Trade>>();
                    MessageBox.Show(JsonConvert.SerializeObject(trades));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetKDataSwap(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.swapApi.getCandlesDataAsync("BTC-USD-SWAP", DateTime.UtcNow.AddMinutes(-1), DateTime.UtcNow, 60);

                MessageBox.Show(resResult);
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetIndexSwap(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.swapApi.getIndexAsync("BTC-USD-SWAP");

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var index = resResult.ToObject<swap.Index>();
                    MessageBox.Show(JsonConvert.SerializeObject(index));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetRateSwap(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.swapApi.getRateAsync();

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var rate = resResult.ToObject<swap.Rate>();
                    MessageBox.Show(JsonConvert.SerializeObject(rate));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetOpenInteestSwap(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.swapApi.getOpenInterestAsync("BTC-USD-SWAP");

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var openInterest = resResult.ToObject<swap.OpenInterest>();
                    MessageBox.Show(JsonConvert.SerializeObject(openInterest));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnPriceLimitSwap(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.swapApi.getPriceLimitAsync("BTC-USD-SWAP");

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var priceLimit = resResult.ToObject<swap.PriceLimit>();
                    MessageBox.Show(JsonConvert.SerializeObject(priceLimit));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnLiquidationSwap(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.swapApi.getLiquidationAsync("BTC-USD-SWAP", "0", 1, null, 10);

                MessageBox.Show(resResult);
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnHoldsSwap(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.swapApi.getHoldsAsync("BTC-USD-SWAP");

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var holds = resResult.ToObject<swap.Hold>();
                    MessageBox.Show(JsonConvert.SerializeObject(holds));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetFundingTimeSwap(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.swapApi.getFundingTimeAsync("BTC-USD-SWAP");

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var holds = resResult.ToObject<swap.FundingTime>();
                    MessageBox.Show(JsonConvert.SerializeObject(holds));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btnGetMarkPriceSwap(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.swapApi.getMarkPriceAsync("BTC-USD-SWAP");

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var holds = resResult.ToObject<swap.MarkPrice>();
                    MessageBox.Show(JsonConvert.SerializeObject(holds));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }
        private async void btnGetGenaralSwap(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.swapApi.getGeneralFundingRateAsync("BTC-USD-SWAP");
                MessageBox.Show(resResult);
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }
        private async void btnGetHistoricalSwap(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.swapApi.getHistoricalFundingRateAsync("BTC-USD-SWAP", 10);
                if (resResult.Type == JTokenType.Object)
                {
                    JToken codeJToken;
                    if (((JObject)resResult).TryGetValue("code", out codeJToken))
                    {
                        var errorInfo = resResult.ToObject<ErrorResult>();
                        MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                    }
                }
                else
                {
                    var liquidations = resResult.ToObject<List<swap.HistoricalFundingRate>>();
                    MessageBox.Show(JsonConvert.SerializeObject(liquidations));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        // 创建Websocketor对象
        private WebSocketor websocketor = new WebSocketor();
        /// <summary>
        /// WebSocket消息推送侦听
        /// </summary>
        /// <param name="msg">WebSocket消息</param>
        private void handleWebsocketMessage(string msg)
        {
            this.Dispatcher.BeginInvoke(new Action(() =>
            {
                this.msgBox.AppendText(msg + Environment.NewLine);//换行标识
                File.AppendAllText("D:\\b.txt", msg + "\n\n\n");
            }));
        }

        private async void btnConnect(object sender, RoutedEventArgs e)
        {
            websocketor.WebSocketPush -= this.handleWebsocketMessage;
            websocketor.WebSocketPush += this.handleWebsocketMessage;
            await websocketor.ConnectAsync();
            MessageBox.Show("连接成功");
        }

        //private async void btnSubscribe(object sender, RoutedEventArgs e)
        //{
        //    await websocketor.Subscribe(new List<String>() { "futures/depth:BTC-USDT-191227" });
        //}
        private async void btnSubscribe(object sender, RoutedEventArgs e)
        {
            string channel = "", contract = "", currencyType = "", candle = "", contractID = "";
            Button btn = (Button)sender;
            string btn_type = btn.Content.ToString();
            switch (btn_type)
            {
                case "订阅(spot)":
                    channel = cleanTag(this.channel_spot.Text);
                    contract = cleanTag(this.contract_spot.Text);
                    currencyType = cleanTag(this.currency_spot.Text);
                    candle = cleanTag(this.candle_spot.Text);
                    contractID = this.contract_spot.Text;
                    break;
                case "订阅(futures)":
                    channel = cleanTag(this.channel_futures.Text);
                    contract = cleanTag(this.contract_futures.Text);
                    candle = cleanTag(this.candle_futures.Text);
                    contractID = this.contract_futures.Text;
                    break;
                case "订阅(swap)":
                    channel = cleanTag(this.channel_swap.Text);
                    contract = cleanTag(this.contract_swap.Text);
                    candle = cleanTag(this.candle_swap.Text);
                    contractID = this.contract_swap.Text;
                    break;
                case "订阅(index)":
                    channel = cleanTag(this.channel_index.Text);
                    candle = cleanTag(this.candle_index.Text);
                    contractID = this.contract_index.Text;
                    break;
                default:
                    break;
            }
            if (!string.IsNullOrWhiteSpace(candle) && candle != "请选择时间粒度")
            {
                if (!string.IsNullOrWhiteSpace(contract) && contract != "请选择合约类型")
                {
                    await websocketor.Subscribe(new List<string>() { $"{channel}{candle}s:{contract}" });
                }
                else if (!string.IsNullOrWhiteSpace(currencyType) && currencyType != "请选择币种类型")
                {
                    await websocketor.Subscribe(new List<string>() { $"{channel}{candle}s:{currencyType}" });
                }
                else if (!string.IsNullOrWhiteSpace(contractID))
                {
                    await websocketor.Subscribe(new List<string>() { $"{channel}{candle}s:{contractID}" });
                }
            }
            else if (!string.IsNullOrWhiteSpace(contract) && contract != "请选择合约类型")
            {
                await websocketor.Subscribe(new List<string>() { $"{channel}:{contract}" });
            }
            else if (!string.IsNullOrWhiteSpace(currencyType) && currencyType != "请选择币种类型")
            {
                await websocketor.Subscribe(new List<string>() { $"{channel}:{currencyType}" });
            }
            else if (!string.IsNullOrWhiteSpace(contractID))
            {
                await websocketor.Subscribe(new List<string>() { $"{channel}:{contractID}" });
            }
        }
        private string cleanTag(string content)
        {
            int sign = content.IndexOf("(");
            return sign > 0 ? content.Substring(0, sign) : content;
        }

        //private async void btnUnSubscribe(object sender, RoutedEventArgs e)
        //{
        //    await websocketor.UnSubscribe(new List<String>() { "swap/depth:BTC-USD-SWAP", "swap/candle60s:BTC-USD-SWAP" });
        //}
        private async void btnUnSubscribe(object sender, RoutedEventArgs e)
        {
            string channel = "", contract = "", currencyType = "", candle = "", contractID = "";
            Button btn = (Button)sender;
            string btn_type = btn.Content.ToString();
            switch (btn_type)
            {
                case "取消订阅(spot)":
                    channel = cleanTag(this.channel_spot.Text);
                    contract = cleanTag(this.contract_spot.Text);
                    currencyType = cleanTag(this.currency_spot.Text);
                    candle = cleanTag(this.candle_spot.Text);
                    contractID = this.contract_spot.Text;
                    break;
                case "取消订阅(futures)":
                    channel = cleanTag(this.channel_futures.Text);
                    contract = cleanTag(this.contract_futures.Text);
                    candle = cleanTag(this.candle_futures.Text);
                    contractID = this.contract_futures.Text;
                    break;
                case "取消订阅(swap)":
                    channel = cleanTag(this.channel_swap.Text);
                    contract = cleanTag(this.contract_swap.Text);
                    candle = cleanTag(this.candle_swap.Text);
                    contractID = this.contract_swap.Text;
                    break;
                case "取消订阅(index)":
                    channel = cleanTag(this.channel_index.Text);
                    candle = cleanTag(this.candle_index.Text);
                    contractID = this.contract_index.Text;
                    break;
                default:
                    break;
            }
            if (!string.IsNullOrWhiteSpace(candle) && candle != "请选择时间粒度")
            {
                if (!string.IsNullOrWhiteSpace(contract) && contract != "请选择合约类型")
                {
                    await websocketor.UnSubscribe(new List<string>() { $"{channel}{candle}s:{contract}" });
                }
                else if (!string.IsNullOrWhiteSpace(currencyType) && currencyType != "请选择币种类型")
                {
                    await websocketor.UnSubscribe(new List<string>() { $"{channel}{candle}s:{currencyType}" });
                }
                else if (!string.IsNullOrWhiteSpace(contractID))
                {
                    await websocketor.UnSubscribe(new List<string>() { $"{channel}{candle}s:{contractID}" });
                }
            }
            else if (!string.IsNullOrWhiteSpace(contract) && contract != "请选择合约类型")
            {
                await websocketor.UnSubscribe(new List<string>() { $"{channel}:{contract}" });
            }
            else if (!string.IsNullOrWhiteSpace(currencyType) && currencyType != "请选择币种类型")
            {
                await websocketor.UnSubscribe(new List<string>() { $"{channel}:{currencyType}" });
            }
            else if (!string.IsNullOrWhiteSpace(contractID))
            {
                await websocketor.UnSubscribe(new List<string>() { $"{channel}:{contractID}" });
            }
        }

        private async void btnLogin(object sender, RoutedEventArgs e)
        {
            await websocketor.LoginAsync(this.apiKey, this.secret, this.passPhrase);
        }

        private async void btnSubscribeLogin(object sender, RoutedEventArgs e)
        {
            await websocketor.Subscribe(new List<String>() { "futures/account:BTC" });
        }

        private void btnDispose(object sender, RoutedEventArgs e)
        {
            websocketor.Dispose();
        }

        private async void btnProgress(object sender, RoutedEventArgs e)
        {
            // 取消事件侦听
            websocketor.WebSocketPush -= this.handleWebsocketMessage;
            // 添加事件侦听
            websocketor.WebSocketPush += this.handleWebsocketMessage;
            try
            {
                // 建立WebSocket连接
                await websocketor.ConnectAsync();
                // 订阅无需登录的Channel
                await websocketor.Subscribe(new List<String>() { "swap/ticker:BTC-USD-SWAP", "swap/candle60s:BTC-USD-SWAP" });
                // 登录
                await websocketor.LoginAsync(this.apiKey, this.secret, this.passPhrase);
                // 等待登录
                await Task.Delay(500);
                // 订阅需要登录的Channel
                await websocketor.Subscribe(new List<String>() { "futures/account:BTC" });
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private void TextBox_TextChanged(object sender, TextChangedEventArgs e)
        {

        }

        private async void btnSetMargin_mode(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.futureApi.setMargin_modeAsync("btc-usd", "crossed");

                MessageBox.Show(resResult);

            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }
        private async void btnclose_position(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.futureApi.close_position("BTC-USD-191129", "short");

                MessageBox.Show(resResult);

            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }
        private async void cancel_all(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.futureApi.cancel_all("BTC-USD-191129", "short");
                MessageBox.Show(resResult);

            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }
        private async void order_algo(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.futureApi.order_algo("BTC-USD-191129", "1", 2, null, null, null, null);
                MessageBox.Show(resResult);

            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }
        private async void mark_price(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.futureApi.mark_price("BTC-USD-191129");


                MessageBox.Show(resResult);

            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }
        private async void btnOrder_algoSpot(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.spotApi.btnOrder_algoSpot("BTC-USDT", "1", "1", "0.05", "sell", "8000", "7500");


                MessageBox.Show(resResult);

            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private async void btncancel_batch_algosSpot(object sender, RoutedEventArgs e)
        {
            try
            {
                List<string> algo_ids = new List<string>();
                algo_ids.Add("1600593327162368");
                string algoids = JsonConvert.SerializeObject(algo_ids);
                var resResult = await this.spotApi.cancel_batch_algosSpot("BTC-USDT", algoids, "1");


                MessageBox.Show(resResult);

            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }
        private async void order_algo_futures(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.futureApi.order_algo("ETC-USD-191206", "1", "1", "1", "432.11", "341.99");

                MessageBox.Show(resResult);
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }
        private async void cancel_algos_futures(object sender, RoutedEventArgs e)
        {
            try
            {
                List<string> algo_ids = new List<string>();
                algo_ids.Add("1");
                string str_algoIds = JsonConvert.SerializeObject(algo_ids);
                var resResult = await this.futureApi.cancel_algos("ETC-USD-191206", str_algoIds, "1");

                MessageBox.Show(resResult);
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }
    }
}
