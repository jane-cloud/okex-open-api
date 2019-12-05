using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using OKExSDK.Models.Futures;
using OKExSDK.Models.General;
using System;
using System.Collections.Generic;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;

namespace OKExSDK
{
    public class FuturesApi : SdkApi
    {
        private string FUTURES_SEGMENT = "api/futures/v3";

        /// <summary>
        /// FuturesApi构造函数
        /// </summary>
        /// <param name="apiKey">API Key</param>
        /// <param name="secret">Secret</param>
        /// <param name="passPhrase">Passphrase</param>
        public FuturesApi(string apiKey, string secret, string passPhrase) : base(apiKey, secret, passPhrase) { }

        /// <summary>
        /// 获取合约账户所有的持仓信息。
        /// </summary>
        /// <returns>账户所有持仓信息</returns>
        public async Task<JObject> getPositionsAsync()
        {
            var url = $"{this.BASEURL}{this.FUTURES_SEGMENT}/position";

            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, null)))
            {
                var res = await client.GetAsync(url);
                var contentStr = await res.Content.ReadAsStringAsync();

                return JObject.Parse(contentStr);
            }
        }

        /// <summary>
        /// 获取某个合约的持仓信息
        /// </summary>
        /// <param name="instrument_id">合约ID</param>
        /// <returns>该合约全部持仓</returns>
        public async Task<JObject> getPositionByIdAsync(string instrument_id)
        {
            var url = $"{this.BASEURL}{this.FUTURES_SEGMENT}/{instrument_id}/position";
            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, null)))
            {
                var res = await client.GetAsync(url);
                var contentStr = await res.Content.ReadAsStringAsync();
                return JObject.Parse(contentStr);
            }
        }

        /// <summary>
        /// 所有币种合约账户信息
        /// </summary>
        /// <returns>合约账户信息</returns>
        public async Task<JObject> getAccountsAsync()
        {
            var url = $"{this.BASEURL}{this.FUTURES_SEGMENT}/accounts";
            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, null)))
            {
                var res = await client.GetAsync(url);
                var contentStr = await res.Content.ReadAsStringAsync();
                return JObject.Parse(contentStr);
            }
        }

        /// <summary>
        /// 获取单个币种的合约账户信息
        /// </summary>
        /// <param name="currency">币种，如：btc</param>
        /// <returns>该币种的合约账户信息</returns>
        public async Task<JObject> getAccountByCurrencyAsync(string currency)
        {
            var url = $"{this.BASEURL}{this.FUTURES_SEGMENT}/accounts/{currency}";
            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, null)))
            {
                var res = await client.GetAsync(url);
                var contentStr = await res.Content.ReadAsStringAsync();
                return JObject.Parse(contentStr);
            }
        }

        /// <summary>
        /// 获取合约账户币种杠杆倍数
        /// </summary>
        /// <param name="currency">币种，如：btc</param>
        /// <returns></returns>
        public async Task<JObject> getLeverageAsync(string currency)
        {
            var url = $"{this.BASEURL}{this.FUTURES_SEGMENT}/accounts/{currency}/leverage";
            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, null)))
            {
                var res = await client.GetAsync(url);
                var contentStr = await res.Content.ReadAsStringAsync();
                return JObject.Parse(contentStr);
            }
        }

        /// <summary>
        /// 全仓设定合约币种杠杆倍数
        /// </summary>
        /// <param name="currency">币种，如：btc</param>
        /// <param name="leverage">要设定的杠杆倍数，10或20</param>
        /// <returns></returns>
        public async Task<JObject> setCrossedLeverageAsync(string currency, int leverage)
        {
            var url = $"{this.BASEURL}{this.FUTURES_SEGMENT}/accounts/{currency}/leverage";
            var body = new { leverage = leverage };
            var bodyStr = JsonConvert.SerializeObject(body);
            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, bodyStr)))
            {
                var res = await client.PostAsync(url, new StringContent(bodyStr, Encoding.UTF8, "application/json"));
                var contentStr = await res.Content.ReadAsStringAsync();
                return JObject.Parse(contentStr);
            }
        }

        /// <summary>
        /// 逐仓设定合约币种杠杆倍数
        /// </summary>
        /// <param name="currency">币种，如：btc</param>
        /// <param name="leverage">要设定的杠杆倍数，10或20</param>
        /// <param name="instrument_id">合约ID，如BTC-USD-180213</param>
        /// <param name="direction">开仓方向，long(做多)或者short(做空)</param>
        /// <returns></returns>
        public async Task<JObject> setFixedLeverageAsync(string currency, int leverage, string instrument_id, string direction)
        {
            var url = $"{this.BASEURL}{this.FUTURES_SEGMENT}/accounts/{currency}/leverage";
            var body = new { instrument_id = instrument_id, direction = direction, leverage = leverage };
            var bodyStr = JsonConvert.SerializeObject(body);
            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, bodyStr)))
            {
                var res = await client.PostAsync(url, new StringContent(bodyStr, Encoding.UTF8, "application/json"));
                var contentStr = await res.Content.ReadAsStringAsync();
                return JObject.Parse(contentStr);
            }
        }

        /// <summary>
        /// 账单流水查询
        /// </summary>
        /// <param name="currency">币种</param>
        /// <param name="from">分页游标开始</param>
        /// <param name="to">分页游标截至</param>
        /// <param name="limit">分页数据数量，默认100</param>
        /// <returns></returns>
        public async Task<JContainer> getLedgersByCurrencyAsync(string currency, int? from, int? to, int? limit)
        {
            var url = $"{this.BASEURL}{this.FUTURES_SEGMENT}/accounts/{currency}/ledger";
            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, null)))
            {
                var queryParams = new Dictionary<string, string>();
                if (from.HasValue)
                {
                    queryParams.Add("from", from.Value.ToString());
                }
                if (to.HasValue)
                {
                    queryParams.Add("to", to.Value.ToString());
                }
                if (limit.HasValue)
                {
                    queryParams.Add("limit", limit.Value.ToString());
                }
                var encodedContent = new FormUrlEncodedContent(queryParams);
                var paramsStr = await encodedContent.ReadAsStringAsync();
                var res = await client.GetAsync($"{url}?{paramsStr}");
                var contentStr = await res.Content.ReadAsStringAsync();
                if (contentStr[0] == '[')
                {
                    return JArray.Parse(contentStr);
                }
                return JObject.Parse(contentStr);
            }
        }

        /// <summary>
        /// 下单
        /// </summary>
        /// <param name="instrument_id">合约ID，如BTC-USD-180213</param>
        /// <param name="type">1:开多2:开空3:平多4:平空</param>
        /// <param name="price">每张合约的价格</param>
        /// <param name="size">买入或卖出合约的数量（以张计数）</param>
        /// <param name="leverage">要设定的杠杆倍数，10或20</param>
        /// <param name="client_oid">由您设置的订单ID来识别您的订单</param>
        /// <param name="match_price">是否以对手价下单(0:不是 1:是)，默认为0，当取值为1时。price字段无效</param>
        /// <returns></returns>
        public async Task<JObject> makeOrderAsync(string instrument_id, string type, decimal price, int size, int leverage, string client_oid, string match_price)
        {
            var url = $"{this.BASEURL}{this.FUTURES_SEGMENT}/order";
            var body = new
            {
                instrument_id = instrument_id,
                type = type,
                price = price,
                size = size,
                leverage = leverage,
                client_oid = client_oid,
                match_price = match_price
            };
            var bodyStr = JsonConvert.SerializeObject(body);
            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, bodyStr)))
            {
                var res = await client.PostAsync(url, new StringContent(bodyStr, Encoding.UTF8, "application/json"));
                var contentStr = await res.Content.ReadAsStringAsync();
                return JObject.Parse(contentStr);
            }
        }

        /// <summary>
        /// 批量下单
        /// </summary>
        /// <param name="order">订单信息</param>
        /// <returns></returns>
        public async Task<JObject> makeOrdersBatchAsync(OrderBatch order)
        {
            var url = $"{this.BASEURL}{this.FUTURES_SEGMENT}/orders";
            var bodyStr = JsonConvert.SerializeObject(order);
            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, bodyStr)))
            {
                var res = await client.PostAsync(url, new StringContent(bodyStr, Encoding.UTF8, "application/json"));
                var contentStr = await res.Content.ReadAsStringAsync();
                return JObject.Parse(contentStr);
            }
        }

        /// <summary>
        /// 撤销指定订单
        /// </summary>
        /// <param name="instrument_id">合约ID，如BTC-USD-180213</param>
        /// <param name="order_id">服务器分配订单ID</param>
        /// <returns></returns>
        public async Task<JObject> cancelOrderAsync(string instrument_id, string order_id)
        {
            var url = $"{this.BASEURL}{this.FUTURES_SEGMENT}/cancel_order/{instrument_id}/{order_id}";
            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, "")))
            {
                var res = await client.PostAsync(url, new StringContent("", Encoding.UTF8, "application/json"));
                var contentStr = await res.Content.ReadAsStringAsync();
                return JObject.Parse(contentStr);
            }
        }

        /// <summary>
        /// 批量撤销订单
        /// </summary>
        /// <param name="instrument_id">撤销指定合约品种的订单</param>
        /// <param name="orderIds">撤销订单ID列表</param>
        /// <returns></returns>
        public async Task<JObject> cancelOrderBatchAsync(string instrument_id, List<long> orderIds)
        {
            var url = $"{this.BASEURL}{this.FUTURES_SEGMENT}/cancel_batch_orders/{instrument_id}";
            var body = new { order_ids = orderIds };
            var bodyStr = JsonConvert.SerializeObject(body);
            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, bodyStr)))
            {
                var res = await client.PostAsync(url, new StringContent(bodyStr, Encoding.UTF8, "application/json"));
                var contentStr = await res.Content.ReadAsStringAsync();
                return JObject.Parse(contentStr);
            }
        }

        /// <summary>
        /// 获取订单列表
        /// </summary>
        /// <param name="instrument_id">合约ID，如BTC-USD-180213</param>
        /// <param name="status">订单状态(-1.撤单成功；0:等待成交 1:部分成交 2:全部成交 6：未完成（等待成交+部分成交）7：已完成（撤单成功+全部成交）</param>
        /// <param name="from">分页游标开始</param>
        /// <param name="to">分页游标截至</param>
        /// <param name="limit">分页数据数量，默认100</param>
        /// <returns></returns>
        public async Task<JObject> getOrdersAsync(string instrument_id, string status, int? from, int? to, int? limit)
        {
            var url = $"{this.BASEURL}{this.FUTURES_SEGMENT}/orders/{instrument_id}";
            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, null)))
            {
                var queryParams = new Dictionary<string, string>();
                queryParams.Add("status", status);
                if (from.HasValue)
                {
                    queryParams.Add("from", from.Value.ToString());
                }
                if (to.HasValue)
                {
                    queryParams.Add("to", to.Value.ToString());
                }
                if (limit.HasValue)
                {
                    queryParams.Add("limit", limit.Value.ToString());
                }
                var encodedContent = new FormUrlEncodedContent(queryParams);
                var paramsStr = await encodedContent.ReadAsStringAsync();
                var res = await client.GetAsync($"{url}?{paramsStr}");
                var contentStr = await res.Content.ReadAsStringAsync();
                return JObject.Parse(contentStr);
            }
        }

        /// <summary>
        /// 通过订单ID获取单个订单信息
        /// </summary>
        /// <param name="instrument_id">合约ID，如BTC-USD-180213</param>
        /// <param name="order_id">订单ID</param>
        /// <returns></returns>
        public async Task<JObject> getOrderByIdAsync(string instrument_id, string order_id)
        {
            var url = $"{this.BASEURL}{this.FUTURES_SEGMENT}/orders/{instrument_id}/{order_id}";
            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, null)))
            {
                var res = await client.GetAsync(url);
                var contentStr = await res.Content.ReadAsStringAsync();
                return JObject.Parse(contentStr);
            }
        }

        /// <summary>
        /// 获取成交明细
        /// </summary>
        /// <param name="instrument_id">合约ID，如BTC-USD-180213</param>
        /// <param name="order_id">订单ID</param>
        /// <param name="from">分页游标开始</param>
        /// <param name="to">分页游标截至</param>
        /// <param name="limit">分页数据数量，默认100</param>
        /// <returns></returns>
        public async Task<JContainer> getFillsAsync(string instrument_id, string order_id, int? from, int? to, int? limit)
        {
            var url = $"{this.BASEURL}{this.FUTURES_SEGMENT}/fills";
            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, null)))
            {
                var queryParams = new Dictionary<string, string>();
                queryParams.Add("instrument_id", instrument_id);
                queryParams.Add("order_id", order_id);
                if (from.HasValue)
                {
                    queryParams.Add("from", from.Value.ToString());
                }
                if (to.HasValue)
                {
                    queryParams.Add("to", to.Value.ToString());
                }
                if (limit.HasValue)
                {
                    queryParams.Add("limit", limit.Value.ToString());
                }
                var encodedContent = new FormUrlEncodedContent(queryParams);
                var paramsStr = await encodedContent.ReadAsStringAsync();
                var res = await client.GetAsync($"{url}?{paramsStr}");
                var contentStr = await res.Content.ReadAsStringAsync();
                if (contentStr[0] == '[')
                {
                    return JArray.Parse(contentStr);
                }
                return JObject.Parse(contentStr);
            }
        }

        /// <summary>
        /// 获取合约信息
        /// </summary>
        /// <returns></returns>
        public async Task<JContainer> getInstrumentsAsync()
        {
            var url = $"{this.BASEURL}{this.FUTURES_SEGMENT}/instruments";
            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, null)))
            {
                var res = await client.GetAsync(url);
                var contentStr = await res.Content.ReadAsStringAsync();
                if (contentStr[0] == '[')
                {
                    return JArray.Parse(contentStr);
                }
                return JObject.Parse(contentStr);
            }
        }
        /// <summary>
        /// 获取深度数据
        /// </summary>
        /// <param name="instrument_id">合约ID，如BTC-USD-180213</param>
        /// <param name="size">返回深度数量，最大值可传200，即买卖深度共400条</param>
        /// <returns></returns>
        public async Task<JObject> getBookAsync(string instrument_id, int? size)
        {
            var url = $"{this.BASEURL}{this.FUTURES_SEGMENT}/instruments/{instrument_id}/book";

            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, null)))
            {
                var queryParams = new Dictionary<string, string>();
                if (size.HasValue)
                {
                    queryParams.Add("size", size.Value.ToString());
                }
                var encodedContent = new FormUrlEncodedContent(queryParams);
                var paramsStr = await encodedContent.ReadAsStringAsync();
                var res = await client.GetAsync($"{url}?{paramsStr}");
                var contentStr = await res.Content.ReadAsStringAsync();
                return JObject.Parse(contentStr);
            }
        }

        /// <summary>
        /// 获取全部ticker信息
        /// </summary>
        /// <returns></returns>
        public async Task<JContainer> getTickersAsync()
        {
            var url = $"{this.BASEURL}{this.FUTURES_SEGMENT}/instruments/ticker";
            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, null)))
            {
                var res = await client.GetAsync(url);
                var contentStr = await res.Content.ReadAsStringAsync();
                if (contentStr[0] == '[')
                {
                    return JArray.Parse(contentStr);
                }
                return JObject.Parse(contentStr);
            }
        }

        /// <summary>
        /// 获取某个ticker信息
        /// </summary>
        /// <param name="instrument_id">合约ID，如BTC-USD-180213</param>
        /// <returns></returns>
        public async Task<JObject> getTickerByInstrumentId(string instrument_id)
        {
            var url = $"{this.BASEURL}{this.FUTURES_SEGMENT}/instruments/{instrument_id}/ticker";
            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, null)))
            {
                var res = await client.GetAsync(url);
                var contentStr = await res.Content.ReadAsStringAsync();
                return JObject.Parse(contentStr);
            }
        }

        /// <summary>
        /// 获取成交数据
        /// </summary>
        /// <param name="instrument_id">合约ID，如BTC-USD-180213</param>
        /// <param name="from">分页游标开始</param>
        /// <param name="to">分页游标截至</param>
        /// <param name="limit">分页数据数量，默认100</param>
        /// <returns></returns>
        public async Task<JContainer> getTradesAsync(string instrument_id, int? from, int? to, int? limit)
        {
            var url = $"{this.BASEURL}{this.FUTURES_SEGMENT}/instruments/{instrument_id}/trades";
            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, null)))
            {
                var queryParams = new Dictionary<string, string>();
                if (from.HasValue)
                {
                    queryParams.Add("from", from.Value.ToString());
                }
                if (to.HasValue)
                {
                    queryParams.Add("to", to.Value.ToString());
                }
                if (limit.HasValue)
                {
                    queryParams.Add("limit", limit.Value.ToString());
                }
                var encodedContent = new FormUrlEncodedContent(queryParams);
                var paramsStr = await encodedContent.ReadAsStringAsync();
                var res = await client.GetAsync($"{url}?{paramsStr}");
                var contentStr = await res.Content.ReadAsStringAsync();
                if (contentStr[0] == '[')
                {
                    return JArray.Parse(contentStr);
                }
                return JObject.Parse(contentStr);
            }
        }

        /// <summary>
        /// 获取K线数据
        /// </summary>
        /// <param name="instrument_id">合约ID，如BTC-USD-180213</param>
        /// <param name="start">开始时间</param>
        /// <param name="end">结束时间</param>
        /// <param name="granularity">时间粒度，以秒为单位，必须为60的倍数</param>
        /// <returns></returns>
        public async Task<JContainer> getCandlesDataAsync(string instrument_id, DateTime? start, DateTime? end, int? granularity)
        {
            var url = $"{this.BASEURL}{this.FUTURES_SEGMENT}/instruments/{instrument_id}/candles";
            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, null)))
            {
                var queryParams = new Dictionary<string, string>();
                if (start.HasValue)
                {
                    queryParams.Add("start", TimeZoneInfo.ConvertTimeToUtc(start.Value).ToString("yyyy-MM-ddTHH:mm:ss.fffZ"));
                }
                if (end.HasValue)
                {
                    queryParams.Add("end", TimeZoneInfo.ConvertTimeToUtc(end.Value).ToString("yyyy-MM-ddTHH:mm:ss.fffZ"));
                }
                if (granularity.HasValue)
                {
                    queryParams.Add("granularity", granularity.Value.ToString());
                }
                var encodedContent = new FormUrlEncodedContent(queryParams);
                var paramsStr = await encodedContent.ReadAsStringAsync();
                var res = await client.GetAsync($"{url}?{paramsStr}");
                var contentStr = await res.Content.ReadAsStringAsync();
                if (contentStr[0] == '[')
                {
                    return JArray.Parse(contentStr);
                }
                return JObject.Parse(contentStr);
            }
        }

        /// <summary>
        /// 获取指数信息
        /// </summary>
        /// <param name="instrument_id">合约ID，如BTC-USD-180213</param>
        /// <returns></returns>
        public async Task<JObject> getIndexAsync(string instrument_id)
        {
            var url = $"{this.BASEURL}{this.FUTURES_SEGMENT}/instruments/{instrument_id}/index";
            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, null)))
            {
                var res = await client.GetAsync(url);
                var contentStr = await res.Content.ReadAsStringAsync();
                return JObject.Parse(contentStr);
            }
        }

        /// <summary>
        /// 获取法币汇率
        /// </summary>
        /// <returns></returns>
        public async Task<JObject> getRateAsync()
        {
            var url = $"{this.BASEURL}{this.FUTURES_SEGMENT}/rate";
            using (var client = new HttpClient())
            {
                var res = await client.GetAsync(url);
                var contentStr = await res.Content.ReadAsStringAsync();
                return JObject.Parse(contentStr);
            }
        }

        /// <summary>
        /// 获取预估交割价
        /// </summary>
        /// <param name="instrument_id">合约ID，如BTC-USD-180213</param>
        /// <returns></returns>
        public async Task<JObject> getEstimatedPriceAsync(string instrument_id)
        {
            var url = $"{this.BASEURL}{this.FUTURES_SEGMENT}/instruments/{instrument_id}/estimated_price";
            using (var client = new HttpClient())
            {
                var res = await client.GetAsync(url);
                var contentStr = await res.Content.ReadAsStringAsync();
                return JObject.Parse(contentStr);
            }
        }

        /// <summary>
        /// 获取平台总持仓量
        /// </summary>
        /// <param name="instrument_id">合约ID，如BTC-USD-180213</param>
        /// <returns></returns>
        public async Task<JObject> getOpenInterestAsync(string instrument_id)
        {
            var url = $"{this.BASEURL}{this.FUTURES_SEGMENT}/instruments/{instrument_id}/open_interest";
            using (var client = new HttpClient())
            {
                var res = await client.GetAsync(url);
                var contentStr = await res.Content.ReadAsStringAsync();
                return JObject.Parse(contentStr);
            }
        }

        /// <summary>
        /// 获取当前限价
        /// </summary>
        /// <param name="instrument_id">合约ID，如BTC-USD-180213</param>
        /// <returns></returns>
        public async Task<JObject> getPriceLimitAsync(string instrument_id)
        {
            var url = $"{this.BASEURL}{this.FUTURES_SEGMENT}/instruments/{instrument_id}/price_limit";
            using (var client = new HttpClient())
            {
                var res = await client.GetAsync(url);
                var contentStr = await res.Content.ReadAsStringAsync();
                return JObject.Parse(contentStr);
            }
        }

        /// <summary>
        /// 获取爆仓单
        /// </summary>
        /// <param name="instrument_id">合约ID，如BTC-USD-180213</param>
        /// <param name="status">状态(0:最近7天数据（包括第7天） 1:7天前数据)</param>
        /// <param name="from">分页游标开始</param>
        /// <param name="to">分页游标截至</param>
        /// <param name="limit">分页数据数量，默认100</param>
        /// <returns></returns>
        public async Task<JContainer> getLiquidationAsync(string instrument_id, string status, int? from, int? to, int? limit)
        {
            var url = $"{this.BASEURL}{this.FUTURES_SEGMENT}/instruments/{instrument_id}/liquidation";
            using (var client = new HttpClient())
            {
                var queryParams = new Dictionary<string, string>();
                queryParams.Add("status", status);
                if (from.HasValue)
                {
                    queryParams.Add("from", from.Value.ToString());
                }
                if (to.HasValue)
                {
                    queryParams.Add("to", to.Value.ToString());
                }
                if (limit.HasValue)
                {
                    queryParams.Add("limit", limit.Value.ToString());
                }
                var encodedContent = new FormUrlEncodedContent(queryParams);
                var paramsStr = await encodedContent.ReadAsStringAsync();
                var res = await client.GetAsync($"{url}?{paramsStr}");
                var contentStr = await res.Content.ReadAsStringAsync();
                if (contentStr[0] == '[')
                {
                    return JArray.Parse(contentStr);
                }
                return JObject.Parse(contentStr);
            }
        }

        /// <summary>
        /// 获取合约挂单冻结数量
        /// </summary>
        /// <param name="instrument_id">合约ID，如BTC-USD-180213</param>
        /// <returns></returns>
        public async Task<JObject> getHoldsAsync(string instrument_id)
        {
            var url = $"{this.BASEURL}{this.FUTURES_SEGMENT}/accounts/{instrument_id}/holds";
            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, null)))
            {
                var res = await client.GetAsync(url);
                var contentStr = await res.Content.ReadAsStringAsync();
                return JObject.Parse(contentStr);
            }
        }
    }
}
