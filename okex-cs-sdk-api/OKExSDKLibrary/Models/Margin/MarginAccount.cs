using System;
using System.Collections.Generic;
using System.Text;

namespace OKExSDK.Models.Margin
{
    public class MarginAccount
    {
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
        /// 风险率
        /// </summary>
        //public string risk_rate { get; set; }
        /// <summary>
        /// 爆仓价
        /// </summary>
        //public string liquidation_price { get; set; }
        /// <summary>
        /// 已借币 （已借未还的部分）
        /// </summary>
        public string borrowed { get; set; }
        /// <summary>
        /// 利息 （未还的利息）
        /// </summary>
        public string lending_fee { get; set; }
    }
}
