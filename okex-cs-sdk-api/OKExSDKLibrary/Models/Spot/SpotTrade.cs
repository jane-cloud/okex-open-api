using System;
using System.Collections.Generic;
using System.Text;

namespace OKExSDK.Models.Spot
{
    public class SpotTrade
    {
        /// <summary>
        /// 成交ID
        /// </summary>
        public long trade_id { get; set; }
        /// <summary>
        /// 成交价格
        /// </summary>
        public string price { get; set; }
        /// <summary>
        /// 成交数量
        /// </summary>
        public string size { get; set; }
        /// <summary>
        /// 成交方向
        /// </summary>
        public string side { get; set; }
        /// <summary>
        /// 成交时间
        /// </summary>
        public DateTime timestamp { get; set; }
    }
}
