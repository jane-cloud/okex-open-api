using System;
using System.Collections.Generic;
using System.Text;

namespace OKExSDK.Models.Futures
{
    public class LeverageCrossed : Leverage
    {
        /// <summary>
        /// 币种，如：btc
        /// </summary>
        public string currency { get; set; }
        /// <summary>
        /// 杠杆倍数
        /// </summary>
        public int leverage { get; set; }
    }
}
