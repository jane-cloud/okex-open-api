using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using OKExSDK.Models.Account;
using System;
using System.Collections.Generic;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;

namespace OKExSDK
{
    public class AccountApi : SdkApi
    {
        private string ACCOUNT_SEGMENT = "api/account/v3";
        public AccountApi(string apiKey, string secret, string passPhrase) : base(apiKey, secret, passPhrase) { }

        /// <summary>
        /// 获取币种列表
        /// </summary>
        /// <returns>币种列表</returns>
        public async Task<JContainer> getCurrenciesAsync()
        {
            var url = $"{this.BASEURL}{this.ACCOUNT_SEGMENT}/currencies";

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
        /// 钱包账户信息
        /// </summary>
        /// <returns>钱包列表</returns>
        public async Task<JContainer> getWalletInfoAsync()
        {
            var url = $"{this.BASEURL}{this.ACCOUNT_SEGMENT}/wallet";
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
        /// <param name="currency">币种，如：btc</param>
        /// <returns></returns>
        public async Task<JContainer> getWalletInfoByCurrencyAsync(string currency)
        {
            var url = $"{this.BASEURL}{this.ACCOUNT_SEGMENT}/wallet/{currency}";
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
        /// 资金划转
        /// </summary>
        /// <param name="transfer">划转信息</param>
        /// <returns></returns>
        public async Task<JObject> makeTransferAsync(Transfer transfer)
        {
            var url = $"{this.BASEURL}{this.ACCOUNT_SEGMENT}/transfer";
            var bodyStr = JsonConvert.SerializeObject(transfer);
            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, bodyStr)))
            {
                var res = await client.PostAsync(url, new StringContent(bodyStr, Encoding.UTF8, "application/json"));
                var contentStr = await res.Content.ReadAsStringAsync();
                return JObject.Parse(contentStr);
            }
        }

        /// <summary>
        /// 提币
        /// </summary>
        /// <param name="withDraw">提币信息</param>
        /// <returns></returns>
        public async Task<JObject> makeWithDrawalAsync(WithDrawal withDraw)
        {
            var url = $"{this.BASEURL}{this.ACCOUNT_SEGMENT}/withdrawal";
            var bodyStr = JsonConvert.SerializeObject(withDraw);
            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, bodyStr)))
            {
                var res = await client.PostAsync(url, new StringContent(bodyStr, Encoding.UTF8, "application/json"));
                var contentStr = await res.Content.ReadAsStringAsync();
                return JObject.Parse(contentStr);
            }
        }

        /// <summary>
        /// 提币手续费
        /// </summary>
        /// <param name="currency">币种</param>
        /// <returns></returns>
        public async Task<JContainer> getWithDrawalFeeAsync(string currency)
        {
            var url = $"{this.BASEURL}{this.ACCOUNT_SEGMENT}/withdrawal/fee";
            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, null)))
            {
                var queryParams = new Dictionary<string, string>();
                if (!string.IsNullOrEmpty(currency))
                {
                    queryParams.Add("currency", currency);
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
        /// 查询最近所有币种的提币记录
        /// </summary>
        /// <returns></returns>
        public async Task<JContainer> getWithDrawalHistoryAsync()
        {
            var url = $"{this.BASEURL}{this.ACCOUNT_SEGMENT}/withdrawal/history";
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
        /// 查询单个币种提币记录
        /// </summary>
        /// <param name="currency">币种</param>
        /// <returns></returns>
        public async Task<JContainer> getWithDrawalHistoryByCurrencyAsync(string currency)
        {
            var url = $"{this.BASEURL}{this.ACCOUNT_SEGMENT}/withdrawal/history/{currency}";
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
        /// 账单流水查询
        /// </summary>
        /// <param name="currency">币种，如btc</param>
        /// <param name="type">填写相应数字：1:充值2:提现13:撤销提现18:转入合约账户19:合约账户转出20:转入子账户21:子账户转出28:领取29:转入指数交易区30:指数交易区转出 31:转入点对点账户32:点对点账户转出 33:转入币币杠杆账户 34:币币杠杆账户转出 37:转入币币账户 38:币币账户转出</param>
        /// <param name="from">请求此页码之后的分页内容（举例页码为：1，2，3，4，5。from 4 只返回第5页，to 4只返回第3页）</param>
        /// <param name="to">请求此页码之前的分页内容（举例页码为：1，2，3，4，5。from 4 只返回第5页，to 4只返回第3页）</param>
        /// <param name="limit">分页返回的结果集数量，默认为100，最大为100，按时间顺序排列，越早下单的在前面</param>
        /// <returns></returns>
        public async Task<JContainer> getLedgerAsync(string currency, string type, int? from, int? to, int? limit)
        {
            var url = $"{this.BASEURL}{this.ACCOUNT_SEGMENT}/ledger";
            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, null)))
            {
                var queryParams = new Dictionary<string, string>();
                if (!string.IsNullOrEmpty(currency))
                {
                    queryParams.Add("currency", currency);
                }
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
        /// 获取充值地址
        /// </summary>
        /// <param name="currency">币种</param>
        /// <returns></returns>
        public async Task<JContainer> getDepositAddressAsync(string currency)
        {
            var url = $"{this.BASEURL}{this.ACCOUNT_SEGMENT}/deposit/address";
            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, null)))
            {
                var queryParams = new Dictionary<string, string>();
                if (!string.IsNullOrEmpty(currency))
                {
                    queryParams.Add("currency", currency);
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
        /// 获取所有币种充值记录
        /// </summary>
        /// <returns></returns>
        public async Task<JContainer> getDepositHistoryAsync()
        {
            var url = $"{this.BASEURL}{this.ACCOUNT_SEGMENT}/deposit/history";
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
        /// 获取单个币种充值记录
        /// </summary>
        /// <param name="currency">币种</param>
        /// <returns></returns>
        public async Task<JContainer> getDepositHistoryByCurrencyAsync(string currency)
        {
            var url = $"{this.BASEURL}{this.ACCOUNT_SEGMENT}/deposit/history/{currency}";
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

    }
}
