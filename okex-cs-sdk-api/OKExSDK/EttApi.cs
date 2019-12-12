using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using OKExSDK.Models.Ett;
using System;
using System.Collections.Generic;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;

namespace OKExSDK
{
    public class EttApi : SdkApi
    {
        private string ETT_SEGMENT = "api/ett/v3";

        public EttApi(string apiKey, string secret, string passPhrase) : base(apiKey, secret, passPhrase) { }

        /// <summary>
        /// 组合账户信息
        /// </summary>
        /// <returns></returns>
        public async Task<JContainer> getAccountsAsync()
        {
            var url = $"{this.BASEURL}{this.ETT_SEGMENT}/accounts";
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
        /// 单一币种账户信息
        /// </summary>
        /// <param name="currency">币种或ett名称</param>
        /// <returns></returns>
        public async Task<JObject> getAccountByCurrencyAsync(string currency)
        {
            var url = $"{this.BASEURL}{this.ETT_SEGMENT}/accounts/{currency}";
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
        /// <param name="currency">币种或ett名称</param>
        /// <param name="from">请求此页码之后的分页内容（举例页码为：1，2，3，4，5。from 4 只返回第5页，to 4只返回第3页）</param>
        /// <param name="to">请求此页码之前的分页内容（举例页码为：1，2，3，4，5。from 4 只返回第5页，to 4只返回第3页）</param>
        /// <param name="limit">分页返回的结果集数量，默认为100，最大为100，按时间顺序排列，越早下单的在前面</param>
        /// <returns></returns>
        public async Task<JContainer> getLedgerAsync(string currency, int? from, int? to, int? limit)
        {
            var url = $"{this.BASEURL}{this.ETT_SEGMENT}/accounts/{currency}/ledger";
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
        /// <param name="order">订单信息</param>
        /// <returns></returns>
        public async Task<JObject> makeOrderAsync(EttOrder order)
        {
            var url = $"{this.BASEURL}{this.ETT_SEGMENT}/orders";
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
        /// <param name="order_id">服务器分配的订单ID</param>
        /// <returns></returns>
        public async Task<JObject> cancelOrderAsync(string order_id)
        {
            var url = $"{this.BASEURL}{this.ETT_SEGMENT}/orders/{order_id}";
            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, null)))
            {
                var res = await client.DeleteAsync(url);
                var contentStr = await res.Content.ReadAsStringAsync();
                return JObject.Parse(contentStr);
            }
        }

        /// <summary>
        /// 获取订单列表
        /// </summary>
        /// <param name="ett">[必填]列出指定ett的订单</param>
        /// <param name="type">[必填]（1:申购 2:赎回）</param>
        /// <param name="status">仅列出相应状态的订单列表。(0:所有状态 1:等待成交 2:已成交 3:已撤销)</param>
        /// <param name="from">请求此id之后(更新的数据)的分页内容</param>
        /// <param name="to">请求此id之前(更旧的数据)的分页内容</param>
        /// <param name="limit">分页返回的结果集数量，默认为100，最大为100</param>
        /// <returns></returns>
        public async Task<JContainer> getOrdersAsync(string ett, string type, string status, int? from, int? to, int? limit)
        {
            var url = $"{this.BASEURL}{this.ETT_SEGMENT}/orders";
            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, null)))
            {
                var queryParams = new Dictionary<string, string>();
                queryParams.Add("ett", ett);
                queryParams.Add("type", type);
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
        /// <param name="order_id">订单ID</param>
        /// <returns></returns>
        public async Task<JObject> getOrderByOrderIdAsync(string order_id)
        {
            var url = $"{this.BASEURL}{this.ETT_SEGMENT}/orders/{order_id}";
            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, null)))
            {
                var res = await client.GetAsync(url);
                var contentStr = await res.Content.ReadAsStringAsync();
                return JObject.Parse(contentStr);
            }
        }

        /// <summary>
        /// 获取组合成分
        /// </summary>
        /// <param name="ett"></param>
        /// <returns></returns>
        public async Task<JObject> getConstituentsAsync(string ett)
        {
            var url = $"{this.BASEURL}{this.ETT_SEGMENT}/constituents/{ett}";
            using (var client = new HttpClient())
            {
                var res = await client.GetAsync(url);
                var contentStr = await res.Content.ReadAsStringAsync();
                return JObject.Parse(contentStr);
            }
        }

        /// <summary>
        /// 获取ETT清算历史定价
        /// </summary>
        /// <param name="ett">基金名称</param>
        /// <returns></returns>
        public async Task<JContainer> getDefinePriceAsync(string ett)
        {
            var url = $"{this.BASEURL}{this.ETT_SEGMENT}/define-price/{ett}";
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
    }
}
