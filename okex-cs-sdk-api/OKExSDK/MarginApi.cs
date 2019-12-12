using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using OKExSDK.Models.Margin;
using System;
using System.Collections.Generic;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;

namespace OKExSDK
{
    public class MarginApi : SdkApi
    {
        private string MARGIN_SEGMENT = "api/margin/v3";

        public MarginApi(string apiKey, string secret, string passPhrase) : base(apiKey, secret, passPhrase) { }

        /// <summary>
        /// 获取币币杠杆账户资产列表
        /// </summary>
        /// <returns></returns>
        public async Task<JContainer> getAccountsAsync()
        {
            var url = $"{this.BASEURL}{this.MARGIN_SEGMENT}/accounts";
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
        /// 单一币对账户信息
        /// </summary>
        /// <param name="instrument_id">币对</param>
        /// <returns></returns>
        public async Task<JObject> getAccountsByInstrumentIdAsync(string instrument_id)
        {
            var url = $"{this.BASEURL}{this.MARGIN_SEGMENT}/accounts/{instrument_id}";
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
        /// <param name="instrument_id">币对</param>
        /// <param name="type">	[非必填]1:转入 2:转出 3:借款 4:还款 5:计息 6:穿仓亏损 7:买入 8:卖出 若不传此参数则默认返回所有类型</param>
        /// <param name="from">请求此页码之后的分页内容（举例页码为：1，2，3，4，5。from 4 只返回第5页，to 4只返回第3页）</param>
        /// <param name="to">请求此页码之前的分页内容（举例页码为：1，2，3，4，5。from 4 只返回第5页，to 4只返回第3页）</param>
        /// <param name="limit">分页返回的结果集数量，默认为100，最大为100，按时间顺序排列，越早下单的在前面</param>
        /// <returns></returns>
        public async Task<JContainer> getLedgerAsync(string instrument_id, string type, int? from, int? to, int? limit)
        {
            var url = $"{this.BASEURL}{this.MARGIN_SEGMENT}/accounts/{instrument_id}/ledger";
            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, null)))
            {
                var queryParams = new Dictionary<string, string>();
                if (!string.IsNullOrEmpty(type))
                {
                    queryParams.Add("type", type);
                }
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
        /// 杠杆配置信息
        /// </summary>
        /// <returns></returns>
        public async Task<JContainer> getAvailabilityAsync()
        {
            var url = $"{this.BASEURL}{this.MARGIN_SEGMENT}/accounts/availability";
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
        /// 某个杠杆配置信息
        /// </summary>
        /// <param name="instrument_id">币对</param>
        /// <returns></returns>
        public async Task<JContainer> getAvailabilityByInstrumentId(string instrument_id)
        {
            var url = $"{this.BASEURL}{this.MARGIN_SEGMENT}/accounts/{instrument_id}/availability";
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
        /// 获取借币记录
        /// </summary>
        /// <param name="status">状态(0:未还清 1:已还清)</param>
        /// <param name="from">请求此页码之后的分页内容（举例页码为：1，2，3，4，5。from 4 只返回第5页，to 4只返回第3页）</param>
        /// <param name="to">请求此页码之前的分页内容（举例页码为：1，2，3，4，5。from 4 只返回第5页，to 4只返回第3页）</param>
        /// <param name="limit">分页返回的结果集数量，默认为100，最大为100，按时间顺序排列，越早下单的在前面</param>
        /// <returns></returns>
        public async Task<JContainer> getBorrowedAsync(string status, int? from, int? to, int? limit)
        {
            var url = $"{this.BASEURL}{this.MARGIN_SEGMENT}/accounts/borrowed";
            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, null)))
            {
                var queryParams = new Dictionary<string, string>();
                if (!string.IsNullOrEmpty(status))
                {
                    queryParams.Add("status", status);
                }
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
        /// 某账户借币记录
        /// </summary>
        /// <param name="instrument_id">币对</param>
        /// <param name="status">状态(0:未还清 1:已还清)</param>
        /// <param name="from">请求此页码之后的分页内容（举例页码为：1，2，3，4，5。from 4 只返回第5页，to 4只返回第3页）</param>
        /// <param name="to">请求此页码之前的分页内容（举例页码为：1，2，3，4，5。from 4 只返回第5页，to 4只返回第3页）</param>
        /// <param name="limit">分页返回的结果集数量，默认为100，最大为100，按时间顺序排列，越早下单的在前面</param>
        /// <returns></returns>
        public async Task<JContainer> getBorrowedByInstrumentIdAsync(string instrument_id, string status, int? from, int? to, int? limit)
        {
            var url = $"{this.BASEURL}{this.MARGIN_SEGMENT}/accounts/{instrument_id}/borrowed";
            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, null)))
            {
                var queryParams = new Dictionary<string, string>();
                if (!string.IsNullOrEmpty(status))
                {
                    queryParams.Add("status", status);
                }
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
        /// 借币
        /// </summary>
        /// <param name="instrument_id">杠杆币对名称</param>
        /// <param name="currency">币种</param>
        /// <param name="amount">借币数量</param>
        /// <returns></returns>
        public async Task<JObject> makeBorrowAsync(string instrument_id, string currency, string amount)
        {
            var url = $"{this.BASEURL}{this.MARGIN_SEGMENT}/accounts/borrow";
            var body = new Borrow()
            {
                instrument_id = instrument_id,
                currency = currency,
                amount = amount
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
        /// 还币
        /// </summary>
        /// <param name="borrow_id">借币记录ID</param>
        /// <param name="instrument_id">杠杆币对名称</param>
        /// <param name="currency">币种</param>
        /// <param name="amount">借币数量</param>
        /// <returns></returns>
        public async Task<JObject> makeRepaymentAsync(long borrow_id, string instrument_id, string currency, string amount)
        {
            var url = $"{this.BASEURL}{this.MARGIN_SEGMENT}/accounts/repayment";
            var body = new Repayment()
            {
                borrow_id = borrow_id,
                instrument_id = instrument_id,
                currency = currency,
                amount = amount
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
        /// 下单
        /// </summary>
        /// <typeparam name="T">订单类型：市价：MarginOrderMarket 限价：MarginOrderLimit</typeparam>
        /// <param name="order">订单信息</param>
        /// <returns></returns>
        public async Task<JObject> makeOrderAsync<T>(T order)
        {
            var url = $"{this.BASEURL}{this.MARGIN_SEGMENT}/orders";
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
        /// <typeparam name="T">订单类型：市价：List<MarginOrderMarket> 限价：List<MarginOrderLimit></typeparam>
        /// <param name="orders">订单列表</param>
        /// <returns></returns>
        public async Task<JContainer> makeOrderBatchAsync<T>(List<T> orders)
        {
            var url = $"{this.BASEURL}{this.MARGIN_SEGMENT}/batch_orders";
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
            var url = $"{this.BASEURL}{this.MARGIN_SEGMENT}/cancel_orders/{order_id}";
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
        public async Task<JContainer> cancelOrderBatchAsync(List<MarginCancelOrderBatch> orders)
        {
            var url = $"{this.BASEURL}{this.MARGIN_SEGMENT}/cancel_batch_orders";
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
            var url = $"{this.BASEURL}{this.MARGIN_SEGMENT}/orders";
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
        /// 获取订单信息
        /// </summary>
        /// <param name="instrument_id">币对</param>
        /// <param name="order_id">订单ID</param>
        /// <returns></returns>
        public async Task<JObject> getOrderByIdAsync(string instrument_id, string order_id)
        {
            var url = $"{this.BASEURL}{this.MARGIN_SEGMENT}/orders/{order_id}";
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
        /// 获取所有未成交订单
        /// </summary>
        /// <param name="instrument_id">[必填]列出指定币对的订单</param>
        /// <param name="from">请求此id之后(更新的数据)的分页内容</param>
        /// <param name="to">请求此id之前(更旧的数据)的分页内容</param>
        /// <param name="limit">分页返回的结果集数量，默认为100，最大为100</param>
        /// <returns></returns>
        public async Task<JContainer> getPendingOrdersAsync(string instrument_id, int? from, int? to, int? limit)
        {
            var url = $"{this.BASEURL}{this.MARGIN_SEGMENT}/orders_pending";
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
            var url = $"{this.BASEURL}{this.MARGIN_SEGMENT}/fills";
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
    }
}
