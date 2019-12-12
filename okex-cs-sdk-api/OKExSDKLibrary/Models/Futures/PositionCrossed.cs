using System;
using System.Collections.Generic;
using System.Text;

namespace OKExSDK.Models.Futures
{
    public class PositionCrossed : Position
    {
        /// <summary>
        /// 预估爆仓价
        /// </summary>
        public decimal liquidation_price { get; set; }
        /// <summary>
        /// 杠杆倍数
        /// </summary>
        public int leverage { get; set; }
    }
}
