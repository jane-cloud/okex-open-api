using System;
using System.Collections.Generic;
using System.Text;

namespace OKExSDK.Models.Swap
{
    public class Ticker
    {
        /// <summary>
        /// 合约ID，如BTC-USD-180213
        /// </summary>
        public string instrument_id { get; set; }
        /// <summary>
        /// 最新成交价
        /// </summary>
        public decimal last { get; set; }
        /// <summary>
        /// 24小时最高价
        /// </summary>
        public decimal high_24h { get; set; }
        /// <summary>
        /// 24小时最低价
        /// </summary>
        public decimal low_24h { get; set; }
        /// <summary>
        /// 24小时成交量，按张数统计
        /// </summary>
        public int volume_24h { get; set; }
        /// <summary>
        /// 系统时间戳
        /// </summary>
        public DateTime timestamp { get; set; }
    }
}
