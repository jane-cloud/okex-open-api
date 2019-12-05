using System;
using System.Collections.Generic;
using System.Text;

namespace OKExSDK.Models.Swap
{
    public class Leverage
    {
        /// <summary>
        /// 账户类型：全仓 crossed 逐仓 fixed
        /// </summary>
        public string margin_mode { get; set; }
        /// <summary>
        /// 合约ID，如BTC-USD-180213
        /// </summary>
        public string instrument_id { get; set; }
        /// <summary>
        /// 多头杠杆倍数
        /// </summary>
        public string long_leverage { get; set; }
        /// <summary>
        /// 空头杠杆倍数
        /// </summary>
        public string short_leverage { get; set; }
    }
}
