using System;
using System.Collections.Generic;
using System.Text;

namespace OKExSDK.Models.Futures
{
    public class PositionFixed : Position
    {
        /// <summary>
        /// 多仓保证金
        /// </summary>
        public decimal long_margin { get; set; }
        /// <summary>
        /// 多仓强平价格
        /// </summary>
        public decimal long_liqui_price { get; set; }
        /// <summary>
        /// 多仓收益率
        /// </summary>
        public double long_pnl_ratio { get; set; }
        /// <summary>
        /// 多仓杠杆倍数
        /// </summary>
        public int long_leverage { get; set; }
        /// <summary>
        /// 空仓保证金
        /// </summary>
        public decimal short_margin { get; set; }
        /// <summary>
        /// 空仓强平价格
        /// </summary>
        public decimal short_liqui_price { get; set; }
        /// <summary>
        /// 空仓收益率
        /// </summary>
        public double short_pnl_ratio { get; set; }
        /// <summary>
        /// 空仓杠杆倍数
        /// </summary>
        public int short_leverage { get; set; }
    }
}
