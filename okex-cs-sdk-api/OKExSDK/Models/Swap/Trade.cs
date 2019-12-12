using System;
using System.Collections.Generic;
using System.Text;

namespace OKExSDK.Models.Swap
{
    public class Trade
    {
        /// <summary>
        /// 成交ID
        /// </summary>
        public string trade_id { get; set; }
        /// <summary>
        /// 成交价格
        /// </summary>
        public decimal price { get; set; }
        /// <summary>
        /// 成交数量
        /// </summary>
        public int size { get; set; }
        /// <summary>
        /// 成交时间
        /// </summary>
        public DateTime timestamp { get; set; }
        /// <summary>
        /// 成交方向
        /// </summary>
        public string side { get; set; }
    }
}
