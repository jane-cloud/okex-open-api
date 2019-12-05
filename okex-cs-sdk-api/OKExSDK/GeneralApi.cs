using Newtonsoft.Json;
using OKExSDK.Models.General;
using System;
using System.Collections.Generic;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;

namespace OKExSDK
{
    public class GeneralApi : SdkApi
    {
        private string GENERAL_SEGMENT = "api/general/v3";

        public GeneralApi(string apiKey, string secret, string passPhrase) : base(apiKey, secret, passPhrase) { }
        public async Task<ServerTime> syncTimeAsync()
        {
            var url = $"{this.BASEURL}{this.GENERAL_SEGMENT}/time";
            using (var httpClient = new HttpClient())
            {
                var res = await httpClient.GetAsync(url);
                var contentStr = await res.Content.ReadAsStringAsync();
                return JsonConvert.DeserializeObject<ServerTime>(contentStr);
            }
        }
    }
}
