using System;
using System.Collections.Generic;
using System.Text;

namespace OKExSDK.Models.Spot
{
    public class SpotTicker
    {
        /// <summary>
        /// 币对名称
        /// </summary>
        public string instrument_id { get; set; }
        /// <summary>
        /// 最新成交价
        /// </summary>
        public string last { get; set; }
        /// <summary>
        /// 买一价
        /// </summary>
        public string best_bid { get; set; }
        /// <summary>
        /// 卖一价
        /// </summary>
        public string best_ask { get; set; }
        /// <summary>
        /// 24小时开盘价
        /// </summary>
        public string open_24h { get; set; }
        /// <summary>
        /// 24小时最高价
        /// </summary>
        public string high_24h { get; set; }
        /// <summary>
        /// 24小时最低价
        /// </summary>
        public string low_24h { get; set; }
        /// <summary>
        /// 24小时成交量，按交易货币统计
        /// </summary>
        public string base_volume_24h { get; set; }
        /// <summary>
        /// 24小时成交量，按计价货币统计
        /// </summary>
        public string quote_volume_24h { get; set; }
        /// <summary>
        /// 系统时间戳
        /// </summary>
        public DateTime timestamp { get; set; }
    }
}
