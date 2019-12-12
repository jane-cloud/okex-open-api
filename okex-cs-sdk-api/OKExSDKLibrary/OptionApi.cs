using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;

namespace OKExSDK
{
    public class OptionApi:SdkApi
    {
        private string Option_SEGMENT = "api/option/v3";
        public OptionApi(string apiKey,string secret,string passphrase) : base(apiKey, secret, passphrase)
        {

        }
       ///<summary>
       ///获取单个标的物账户信息
       ///</summary>
       public async Task<string> getOptionAccountsAsync(string underlying)
        {
            var url = $"{this.BASEURL}{this.Option_SEGMENT}/accounts/{underlying}";
            using (HttpClient client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, null)))
            {
                var res = await client.GetAsync(url);
                var contentStr = await res.Content.ReadAsStringAsync();
                return contentStr;
            }
        }
        /// <summary>
        /// 修改之前下的未完成订单，每个标的指数最多可批量修改10个单。
        /// </summary>
        /// <param name="underlying"></param>
        /// <param name="bodystr"></param>
        /// <returns></returns>
        public async Task<string> Amend_batch_orders(string underlying ,string bodystr)
        {
            var url = $"{this.BASEURL}{this.Option_SEGMENT}/amend_batch_orders/{underlying}";
            using (HttpClient client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, bodystr)))
            {
                var res = await client.PostAsync(url,new StringContent(bodystr,Encoding.UTF8, "application/json"));
                var contentStr = await res.Content.ReadAsStringAsync();
                return contentStr;
            }
        }
        /// <summary>
        /// 修改之前下的未完成订单。
        /// </summary>
        /// <param name="underlying"></param>
        /// <param name="bodystr"></param>
        /// <returns></returns>
        public async Task<string> Amend_order(string underlying ,string bodystr)
        {
            var url = $"{this.BASEURL}{this.Option_SEGMENT}/amend_order/{underlying}";
            using (HttpClient client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, bodystr)))
            {
                var res = await client.PostAsync(url, new StringContent(bodystr, Encoding.UTF8, "application/json"));
                var contentStr = await res.Content.ReadAsStringAsync();
                return contentStr;
            }
        }
        /// <summary>
        /// 获取期权合约的深度数据。
        /// </summary>
        /// <param name="instrument_id"></param>
        /// <returns></returns>
        public async Task<string> getOptionBook(string instrument_id,string size)
        {
            var url =string.IsNullOrWhiteSpace(size)? $"{this.BASEURL}{this.Option_SEGMENT}/instruments/{instrument_id}/book": $"{this.BASEURL}{this.Option_SEGMENT}/instruments/{instrument_id}/book?size={size}";
            using (HttpClient client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, null)))
            {
                var res = await client.GetAsync(url);
                var contentStr = await res.Content.ReadAsStringAsync();
                return contentStr;
            }
        }
        /// <summary>
        /// 批量撤销之前下的未完成订单，每个标的指数最多可批量撤10个单。
        /// </summary>
        /// <param name="underlying"></param>
        /// <param name="bodystr"></param>
        /// <returns></returns>
        public async Task<string> Cancel_Batch_Orders(string underlying, string bodystr)
        {
            var url = $"{this.BASEURL}{this.Option_SEGMENT}/cancel_batch_orders/{underlying}";
            using (HttpClient client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, bodystr)))
            {

                var res = await client.PostAsync(url, new StringContent(bodystr, Encoding.UTF8, "application/json"));
                var contentStr = await res.Content.ReadAsStringAsync();
                return contentStr;
            }
        }
        /// <summary>
        /// 撤销之前下的未完成订单。
        /// </summary>
        /// <param name="underlying"></param>
        /// <param name="order_id"></param>
        /// <returns></returns>
        public async Task<string> Cancel_Order(string underlying, string order_id)
        {
            //order_id填写order_id或者client_oid
            var url = $"{this.BASEURL}{this.Option_SEGMENT}/cancel_order/{underlying}/{order_id}";
            using (HttpClient client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, "")))
            {
                var res = await client.PostAsync(url, new StringContent("", Encoding.UTF8, "application/json"));
                var contentStr = await res.Content.ReadAsStringAsync();
                return contentStr;
            }
        }
        ///<summary>
        ///获取期权合约的成交记录。
        /// </summary>
        public async Task<string> getOptionDeal_data(string instrument_id,string from ,string to ,string limit)
        {
            var url = $"{this.BASEURL}{this.Option_SEGMENT}/instruments/{instrument_id}/trades";
            using (HttpClient client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, null)))
            {
                var queryParames = new Dictionary<string, string>();
                if (!string.IsNullOrWhiteSpace(from))
                {
                    queryParames.Add("after", from);
                }
                if (!string.IsNullOrWhiteSpace(to))
                {
                    queryParames.Add("before", to);
                }
                if (!string.IsNullOrWhiteSpace(limit))
                {
                    queryParames.Add("limit", limit);
                }
                var encodedContent = new FormUrlEncodedContent(queryParames);
                var paramsStr = await encodedContent.ReadAsStringAsync();
                var res =string.IsNullOrWhiteSpace(paramsStr)? await client.GetAsync(url) : await client.GetAsync($"{url}?{paramsStr}");
                var contentStr = await res.Content.ReadAsStringAsync();
                return contentStr;
            }
        }

        public async Task<string> getOptionFills(string underlying,string order_id,string instrument_id,string after,string  before, string limit)
        {
            var url = $"{this.BASEURL}{this.Option_SEGMENT}/fills/{underlying}";
            using (HttpClient client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, null)))
            {
                var queryParames = new Dictionary<string, string>();
                if (!string.IsNullOrWhiteSpace(order_id))
                {
                    queryParames.Add("order_id", order_id);
                }
                if (!string.IsNullOrWhiteSpace(instrument_id))
                {
                    queryParames.Add("instrument_id", instrument_id);
                }

                if (!string.IsNullOrWhiteSpace(after))
                {
                    queryParames.Add("after", after);
                }
                if (!string.IsNullOrWhiteSpace(before))
                {
                    queryParames.Add("before", before);
                }
                if (!string.IsNullOrWhiteSpace(limit))
                {
                    queryParames.Add("limit", limit);
                }
             
                var encodedContent = new FormUrlEncodedContent(queryParames);
                var paramsStr = await encodedContent.ReadAsStringAsync();
                var res =string.IsNullOrWhiteSpace(paramsStr)? await client.GetAsync(url) : await client.GetAsync($"{url}?{paramsStr}");
                var contentStr = await res.Content.ReadAsStringAsync();
                return contentStr;
            }
        }
        public async Task<string> getOptionInstrument_summary_byinis(string underlying, string instrument_id)
        {
            var url = $"{this.BASEURL}{this.Option_SEGMENT}/instruments/{underlying}/summary/{instrument_id}";
            using (HttpClient client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, null)))
            {
                var res = await client.GetAsync($"{url}");
                var contentStr = await res.Content.ReadAsStringAsync();
                return contentStr;
            }
        }
        public async Task<string> getOptionInstrument_summary (string underlying, string delivery)
        {

            var url =string.IsNullOrWhiteSpace(delivery)?$"{this.BASEURL}{this.Option_SEGMENT}/instruments/{underlying}/summary": $"{this.BASEURL}{this.Option_SEGMENT}/instruments/{underlying}/summary?delivery={delivery}";
            using (HttpClient client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, null)))
            {
                var res = await client.GetAsync(url);
                var contentStr = await res.Content.ReadAsStringAsync();
                return contentStr;
            }
        }
        public async Task<string> getOptionInstrument( string underlying,string delivery,string instrument_id)
        {
            var url = $"{this.BASEURL}{this.Option_SEGMENT}/instruments/{underlying}";
            using (HttpClient client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, null)))
            {
                var queryParams = new Dictionary<string, string>();
                if (!string.IsNullOrWhiteSpace(delivery))
                {
                    queryParams.Add("delivery", delivery);
                }
                if (!string.IsNullOrWhiteSpace(instrument_id))
                {
                    queryParams.Add("instrument_id", instrument_id);
                }
                var encodedContent = new FormUrlEncodedContent(queryParams);
                var paramsStr = await encodedContent.ReadAsStringAsync();
                var res = string.IsNullOrWhiteSpace(paramsStr) ? await client.GetAsync(url) : await client.GetAsync($"{url}?{paramsStr}");
                var contentStr = await res.Content.ReadAsStringAsync();
                return contentStr;
            }
        }
        public async Task<string> getOptionLedgert(string underlying,string after,string before,string limit)
        {
            var url = $"{this.BASEURL}{this.Option_SEGMENT}/instruments/{underlying}";
            using (HttpClient client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, null)))
            {
                var queryParames = new Dictionary<string, string>();
                if (!string.IsNullOrWhiteSpace(after))
                {
                    queryParames.Add("after", after);
                }
                if (!string.IsNullOrWhiteSpace(before))
                {
                    queryParames.Add("before", before);
                }
                if (!string.IsNullOrWhiteSpace(limit))
                {
                    queryParames.Add("limit", limit);
                }
                var encodedContent = new FormUrlEncodedContent(queryParames);
                var paramsStr = await encodedContent.ReadAsStringAsync();
                var res = string.IsNullOrWhiteSpace(paramsStr) ? await client.GetAsync(url) : await client.GetAsync($"{url}?{paramsStr}");
                var contentStr = await res.Content.ReadAsStringAsync();
                return contentStr;
            }
        }
        public async Task<string> getOptionLine(string instrument_id,string start,string end,string granularity)
        {
            var url = $"{this.BASEURL}{this.Option_SEGMENT}/instruments/{instrument_id}/candles";
            using (HttpClient client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, null)))
            {
                var queryParams = new Dictionary<string, string>();
                if (!string.IsNullOrWhiteSpace(start))
                {
                    queryParams.Add("start", start);
                }
                if (!string.IsNullOrWhiteSpace(end))
                {
                    queryParams.Add("end", end);
                }
                if (!string.IsNullOrWhiteSpace(granularity))
                {
                    queryParams.Add("granularity", granularity);
                }
                var encodedContent = new FormUrlEncodedContent(queryParams);
                var paramsStr = await encodedContent.ReadAsStringAsync();
                var res = string.IsNullOrWhiteSpace(paramsStr) ? await client.GetAsync(url) : await client.GetAsync($"{url}?{paramsStr}");
                var contentStr = await res.Content.ReadAsStringAsync();
                return contentStr;
            }
        }
        public async Task<string> getOrder_information(string underlying, string order_id)
        {
            //order_id也可填client_oid
            var url = $"{this.BASEURL}{this.Option_SEGMENT}/orders/{underlying}/{order_id}";
            using (HttpClient client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, null)))
            {
                var res = await client.GetAsync(url);
                var contentStr = await res.Content.ReadAsStringAsync();
                return contentStr;
            }
        }
        public async Task<string> getOrder_list(string underlying,string state,string instrument_id,string after,string before,string limit)
        {
            var url = $"{this.BASEURL}{this.Option_SEGMENT}/orders/{underlying}";
            using (HttpClient client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, null)))
            {
                var queryParams = new Dictionary<string, string>();
                queryParams.Add("state", state);
                if (!string.IsNullOrWhiteSpace(instrument_id))
                {
                    queryParams.Add("instrument_id", instrument_id);
                }
                if (!string.IsNullOrWhiteSpace(after))
                {
                    queryParams.Add("after", after);
                }
                if (!string.IsNullOrWhiteSpace(before))
                {
                    queryParams.Add("before", before);
                }
                if (!string.IsNullOrWhiteSpace(limit))
                {
                    queryParams.Add("limit", limit);
                }
                var encodedContent = new FormUrlEncodedContent(queryParams);
                var paramsStr = await encodedContent.ReadAsStringAsync();
                var res = string.IsNullOrWhiteSpace(paramsStr) ? await client.GetAsync(url) : await client.GetAsync($"{url}?{paramsStr}");
                var contentStr = await res.Content.ReadAsStringAsync();
                return contentStr;
            }
        }
        public async Task<string> getOrder(string bodystr)
        {

            var url = $"{this.BASEURL}{this.Option_SEGMENT}/order";
            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, bodystr)))
            {
                var res = await client.PostAsync(url, new StringContent(bodystr, Encoding.UTF8, "application/json"));
                var contentStr =await res.Content.ReadAsStringAsync();
                return contentStr;
            }
        } 
        public async Task<string> getOrders(string bodystr)
        {
            var url = $"{this.BASEURL}{this.Option_SEGMENT}/orders";
            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, bodystr)))
            {
                var res = await client.PostAsync(url, new StringContent(bodystr, Encoding.UTF8, "application/json"));
                var contentStr = await res.Content.ReadAsStringAsync();
                return contentStr;
            }
        }
        public async Task<string> getPosition(string underlying,string instrument_id)
        {
            var url = $"{this.BASEURL}{this.Option_SEGMENT}/{underlying}/position";
            using (HttpClient client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, null)))
            {
                var queryParams = new Dictionary<string, string>();
                if (!string.IsNullOrWhiteSpace(instrument_id))
                {
                    queryParams.Add("instrument_id", instrument_id);
                }
                var encodedContent = new FormUrlEncodedContent(queryParams);
                var paramsStr = await encodedContent.ReadAsStringAsync();
                var res = string.IsNullOrWhiteSpace(paramsStr) ? await client.GetAsync(url) : await client.GetAsync($"{url}?{paramsStr}");
                var contentStr = await res.Content.ReadAsStringAsync();
                return contentStr;
            }
        }
        public async Task<string> getTicker(string instrument_id)
        {
            var url = $"{this.BASEURL}{this.Option_SEGMENT}/instruments/{instrument_id}/ticker";
            using (HttpClient client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, null)))
            {
                var res = await client.GetAsync(url);
                var contentStr = await res.Content.ReadAsStringAsync();
                return contentStr;
            }
        }
        public async Task<string> getTrade_fee()
        {
            var url = $"{this.BASEURL}{this.Option_SEGMENT}/trade_fee";
            using (HttpClient client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, null)))
            {
                var res = await client.GetAsync(url);
                var contentStr = await res.Content.ReadAsStringAsync();
                return contentStr;
            }
        }
        public async Task<string> getUnderlying(string underlying)
        {
            var url = $"{this.BASEURL}{this.Option_SEGMENT}/underlying";
            using (HttpClient client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, null)))
            {
                var res = await client.GetAsync(url);
                var contentStr = await res.Content.ReadAsStringAsync();
                return contentStr;
            }
        }
    }

}
