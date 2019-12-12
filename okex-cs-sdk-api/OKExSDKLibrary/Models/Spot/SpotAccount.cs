using System;
using System.Collections.Generic;
using System.Text;

namespace OKExSDK.Models.Spot
{
    public class SpotAccount
    {
        /// <summary>
        /// 币种
        /// </summary>
        public string currency { get; set; }
        /// <summary>
        /// 余额
        /// </summary>
        public string balance { get; set; }
        /// <summary>
        /// 冻结(不可用)
        /// </summary>
        public string hold { get; set; }
        /// <summary>
        /// 可用于交易或资金划转的数量
        /// </summary>
        public string available { get; set; }
        /// <summary>
        /// 账户ID
        /// </summary>
        public long id { get; set; }
    }
}
