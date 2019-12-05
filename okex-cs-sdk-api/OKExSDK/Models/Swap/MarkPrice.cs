using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace OKExSDK.Models.Swap
{
    public class MarkPrice
    {
        /// <summary>
        /// 合约名称，如BTC-USD-SWAP
        /// </summary>
        public string instrument_id { get; set; }
        /// <summary>
        /// 标记价格
        /// </summary>
        public decimal mark_price { get; set; }
        /// <summary>
        /// 系统时间戳
        /// </summary>
        public DateTime timestamp { get; set; }
    }
}
