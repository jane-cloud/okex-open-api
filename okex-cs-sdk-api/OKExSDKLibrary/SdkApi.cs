using System;
using System.Collections.Generic;
using System.Text;

namespace OKExSDK
{
    public abstract class SdkApi
    {
        protected string BASEURL = "https://www.okex.me/";

        protected string _apiKey;
        protected string _secret;
        protected string _passPhrase;

        public SdkApi(string apiKey, string secret, string passPhrase)
        {
            this._apiKey = apiKey;
            this._secret = secret;
            this._passPhrase = passPhrase;
        }
    }
}
