using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using OKExSDK.Models.Spot;
using System;
using System.Collections.Generic;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;

namespace OKExSDK
{
    public class SpotApi : SdkApi
    {
        private string SPOT_SEGMENT = "api/spot/v3";

        public SpotApi(string apiKey, string secret, string passPhrase) : base(apiKey, secret, passPhrase) { }

        /// <summary>
        /// 获取币币账户信息
        /// </summary>
        /// <returns></returns>
        public async Task<JContainer> getSpotAccountsAsync()
        {
            var url = $"{this.BASEURL}{this.SPOT_SEGMENT}/accounts";

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
        /// 获取币币账户单个币种的余额、冻结和可用等信息
        /// </summary>
        /// <param name="currency">币种</param>
        /// <returns></returns>
        public async Task<JObject> getAccountByCurrencyAsync(string currency)
        {
            var url = $"{this.BASEURL}{this.SPOT_SEGMENT}/accounts/{currency}";

            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, null)))
            {
                var res = await client.GetAsync(url);

                var contentStr = await res.Content.ReadAsStringAsync();
                return JObject.Parse(contentStr);
            }
        }

        /// <summary>
        /// 账单流水查询
        /// </summary>
        /// <param name="currency">币种，如btc</param>
        /// <param name="from">请求此页码之后的分页内容（举例页码为：1，2，3，4，5。from 4 只返回第5页，to 4只返回第3页）</param>
        /// <param name="to">请求此页码之前的分页内容（举例页码为：1，2，3，4，5。from 4 只返回第5页，to 4只返回第3页）</param>
        /// <param name="limit">分页返回的结果集数量，默认为100，最大为100</param>
        /// <returns></returns>
        public async Task<JContainer> getSpotLedgerByCurrencyAsync(string currency, int? from, int? to, int? limit)
        {
            var url = $"{this.BASEURL}{this.SPOT_SEGMENT}/accounts/{currency}/ledger";
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
        /// <typeparam name="T">订单类型：市价：SpotOrderMarket 限价：SpotOrderLimit</typeparam>
        /// <param name="order">订单信息</param>
        /// <returns></returns>
        public async Task<JObject> makeOrderAsync<T>(T order)
        {
            var url = $"{this.BASEURL}{this.SPOT_SEGMENT}/orders";
            var bodyStr = JsonConvert.SerializeObject(order);
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
        /// <typeparam name="T">订单类型：市价：SpotOrderMarket 限价：SpotOrderLimit</typeparam>
        /// <param name="orders">订单列表</param>
        /// <returns></returns>
        public async Task<JContainer> makeOrderBatchAsync<T>(List<T> orders)
        {
            var url = $"{this.BASEURL}{this.SPOT_SEGMENT}/batch_orders";
            var bodyStr = JsonConvert.SerializeObject(orders);
            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, bodyStr)))
            {
                var res = await client.PostAsync(url, new StringContent(bodyStr, Encoding.UTF8, "application/json"));
                var contentStr = await res.Content.ReadAsStringAsync();
                if (contentStr[0] == '[')
                {
                    return JArray.Parse(contentStr);
                }
                return JObject.Parse(contentStr);
            }
        }

        /// <summary>
        /// 撤销指定订单
        /// </summary>
        /// <param name="orderId">订单ID</param>
        /// <param name="instrument_id">提供此参数则撤销指定币对的相应订单，如果不提供此参数则返回错误码</param>
        /// <param name="client_oid">由您设置的订单ID来识别您的订单</param>
        /// <returns></returns>
        public async Task<JObject> cancelOrderByOrderIdAsync(string order_id, string instrument_id, string client_oid)
        {
            var url = $"{this.BASEURL}{this.SPOT_SEGMENT}/cancel_orders/{order_id}";
            var body = new
            {
                instrument_id = instrument_id,
                client_oid = client_oid
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
        /// 批量撤销订单
        /// </summary>
        /// <param name="orders">订单列表</param>
        /// <returns></returns>
        public async Task<JContainer> cancelOrderBatchAsync(List<CancelOrderBatch> orders)
        {
            var url = $"{this.BASEURL}{this.SPOT_SEGMENT}/cancel_batch_orders";
            var bodyStr = JsonConvert.SerializeObject(orders);
            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, bodyStr)))
            {
                var res = await client.PostAsync(url, new StringContent(bodyStr, Encoding.UTF8, "application/json"));
                var contentStr = await res.Content.ReadAsStringAsync();
                if (contentStr[0] == '[')
                {
                    return JArray.Parse(contentStr);
                }
                return JObject.Parse(contentStr);
            }
        }

        /// <summary>
        /// 获取订单列表
        /// </summary>
        /// <param name="instrument_id">[必填]列出指定币对的订单</param>
        /// <param name="status">[必填]仅列出相应状态的订单列表。(all:所有状态 open:未成交 part_filled:部分成交 canceling:撤销中 filled:已成交 cancelled:已撤销 failure:下单失败 ordering:下单中)</param>
        /// <param name="from">请求此id之后(更新的数据)的分页内容</param>
        /// <param name="to">请求此id之前(更旧的数据)的分页内容</param>
        /// <param name="limit">分页返回的结果集数量，默认为100，最大为100</param>
        /// <returns></returns>
        public async Task<JContainer> getOrdersAsync(string instrument_id, string status, int? from, int? to, int? limit)
        {
            var url = $"{this.BASEURL}{this.SPOT_SEGMENT}/orders";
            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, null)))
            {
                var queryParams = new Dictionary<string, string>();
                queryParams.Add("instrument_id", instrument_id);
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
        /// 获取所有未成交订单
        /// </summary>
        /// <param name="instrument_id">[必填]列出指定币对的订单</param>
        /// <param name="from">请求此id之后(更新的数据)的分页内容</param>
        /// <param name="to">请求此id之前(更旧的数据)的分页内容</param>
        /// <param name="limit">分页返回的结果集数量，默认为100，最大为100</param>
        /// <returns></returns>
        public async Task<JContainer> getPendingOrdersAsync(string instrument_id, int? from, int? to, int? limit)
        {
            var url = $"{this.BASEURL}{this.SPOT_SEGMENT}/orders_pending";
            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, null)))
            {
                var queryParams = new Dictionary<string, string>();
                queryParams.Add("instrument_id", instrument_id);

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
        /// 获取订单信息
        /// </summary>
        /// <param name="instrument_id">币对</param>
        /// <param name="order_id">订单ID</param>
        /// <returns></returns>
        public async Task<JObject> getOrderByIdAsync(string instrument_id, string order_id)
        {
            var url = $"{this.BASEURL}{this.SPOT_SEGMENT}/orders/{order_id}";
            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, null)))
            {
                var queryParams = new Dictionary<string, string>();
                queryParams.Add("instrument_id", instrument_id);
                var encodedContent = new FormUrlEncodedContent(queryParams);
                var paramsStr = await encodedContent.ReadAsStringAsync();
                var res = await client.GetAsync($"{url}?{paramsStr}");

                var contentStr = await res.Content.ReadAsStringAsync();
                return JObject.Parse(contentStr);
            }
        }

        /// <summary>
        /// 获取成交明细
        /// </summary>
        /// <param name="order_id">[必填]仅限此order_id的资金明细列表</param>
        /// <param name="instrument_id">[必填]仅限此instrument_id的资金明细表</param>
        /// <param name="from">请求此页码之后的分页内容（举例页码为：1，2，3，4，5。from 4 只返回第5页，to 4只返回第3页）</param>
        /// <param name="to">请求此页码之前的分页内容（举例页码为：1，2，3，4，5。from 4 只返回第5页，to 4只返回第3页）</param>
        /// <param name="limit">分页返回的结果集数量，默认为100，最大为100，按时间顺序排列，越早下单的在前面</param>
        /// <returns></returns>
        public async Task<JContainer> getFillsAsync(long order_id, string instrument_id, int? from, int? to, int? limit)
        {
            var url = $"{this.BASEURL}{this.SPOT_SEGMENT}/fills";
            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, null)))
            {
                var queryParams = new Dictionary<string, string>();
                queryParams.Add("order_id", order_id.ToString());
                queryParams.Add("instrument_id", instrument_id);
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
        /// 获取币对信息
        /// </summary>
        /// <returns></returns>
        public async Task<JContainer> getInstrumentsAsync()
        {
            var url = $"{this.BASEURL}{this.SPOT_SEGMENT}/instruments";
            using (var client = new HttpClient())
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
        /// <param name="instrument_id">币对名称</param>
        /// <param name="size">[非必填]返回深度档位数量，最多返回200</param>
        /// <param name="depth">[非必填]按价格合并深度，默认为tick_size的值(获取币对信息)</param>
        /// <returns></returns>
        public async Task<JContainer> getBookAsync(string instrument_id, string size, string depth)
        {
            var url = $"{this.BASEURL}{this.SPOT_SEGMENT}/instruments/{instrument_id}/book";
            using (var client = new HttpClient())
            {
                var queryParams = new Dictionary<string, string>();
                if (!string.IsNullOrEmpty(size))
                {
                    queryParams.Add("size", size);
                }
                if (!string.IsNullOrEmpty(depth))
                {
                    queryParams.Add("depth", depth);
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
        /// 获取全部ticker信息
        /// </summary>
        /// <returns></returns>
        public async Task<JContainer> getTickerAsync()
        {
            var url = $"{this.BASEURL}{this.SPOT_SEGMENT}/instruments/ticker";
            using (var client = new HttpClient())
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
        /// <param name="instrument_id">币对</param>
        /// <returns></returns>
        public async Task<JObject> getTickerByInstrumentIdAsync(string instrument_id)
        {
            var url = $"{this.BASEURL}{this.SPOT_SEGMENT}/instruments/{instrument_id}/ticker";
            using (var client = new HttpClient())
            {
                var res = await client.GetAsync(url);
                var contentStr = await res.Content.ReadAsStringAsync();
                return JObject.Parse(contentStr);
            }
        }

        /// <summary>
        /// 获取成交数据
        /// </summary>
        /// <param name="instrument_id">[必填]列出指定币对的订单</param>
        /// <param name="from">请求此id之后(更新的数据)的分页内容</param>
        /// <param name="to">请求此id之前(更旧的数据)的分页内容</param>
        /// <param name="limit">分页返回的结果集数量，默认为100，最大为100</param>
        /// <returns></returns>
        public async Task<JContainer> getTradesAasync(string instrument_id, int? from, int? to, int? limit)
        {
            var url = $"{this.BASEURL}{this.SPOT_SEGMENT}/instruments/{instrument_id}/trades";
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
        public async Task<JContainer> getCandlesAsync(string instrument_id, DateTime? start, DateTime? end, int? granularity)
        {
            var url = $"{this.BASEURL}{this.SPOT_SEGMENT}/instruments/{instrument_id}/candles";
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
    }
}
