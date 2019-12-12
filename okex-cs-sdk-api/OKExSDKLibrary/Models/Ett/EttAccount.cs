using System;
using System.Collections.Generic;
using System.Text;

namespace OKExSDK.Models.Ett
{
    public class EttAccount
    {
        /// <summary>
        /// 币种或ett名称
        /// </summary>
        public string currency { get; set; }
        /// <summary>
        /// 余额
        /// </summary>
        public decimal balance { get; set; }
        /// <summary>
        /// 冻结(不可用)
        /// </summary>
        public decimal holds { get; set; }
        /// <summary>
        /// 可用于交易或资金划转的数量
        /// </summary>
        public decimal available { get; set; }
    }
}
