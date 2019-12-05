using System;
using System.Collections.Generic;
using System.Text;

namespace OKExSDK.Models.Account
{
    public class DepositAddress
    {
        /// <summary>
        /// 充值地址
        /// </summary>
        public string address { get; set; }
        /// <summary>
        /// 部分币种提币需要标签，若不需要则不返回此字段
        /// </summary>
        public string tag { get; set; }
        /// <summary>
        /// 部分币种提币需要此字段，若不需要则不返回此字段
        /// </summary>
        public string payment_id { get; set; }
        /// <summary>
        /// 币种，如btc
        /// </summary>
        public string currency { get; set; }
    }
}
