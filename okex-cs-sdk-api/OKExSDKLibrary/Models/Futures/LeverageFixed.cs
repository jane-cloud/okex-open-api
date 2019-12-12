using System;
using System.Collections.Generic;
using System.Text;

namespace OKExSDK.Models.Futures
{
    public class LeverageFixed : Leverage
    {
        /// <summary>
        /// 合约ID，如BTC-USD-180213
        /// </summary>
        public string instrument_id { get; set; }
        /// <summary>
        /// 多头杠杆倍数
        /// </summary>
        public int long_leverage { get; set; }
        /// <summary>
        /// 空头杠杆倍数
        /// </summary>
        public int short_leverage { get; set; }
    }
}
