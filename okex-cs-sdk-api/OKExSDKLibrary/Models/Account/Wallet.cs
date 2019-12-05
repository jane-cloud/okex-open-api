using System;
using System.Collections.Generic;
using System.Text;

namespace OKExSDK.Models.Account
{
    public class Wallet
    {
        /// <summary>
        /// 币种，如btc
        /// </summary>
        public string currency { get; set; }
        /// <summary>
        /// 余额
        /// </summary>
        public decimal balance { get; set; }
        /// <summary>
        /// 冻结(不可用)
        /// </summary>
        public decimal hold { get; set; }
        /// <summary>
        /// 可用于提现或资金划转的数量
        /// </summary>
        public decimal available { get; set; }
    }
}
