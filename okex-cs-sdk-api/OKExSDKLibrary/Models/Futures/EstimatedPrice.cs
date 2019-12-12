using System;
using System.Collections.Generic;
using System.Text;

namespace OKExSDK.Models.Futures
{
    public class EstimatedPrice
    {
        /// <summary>
        /// 合约ID，如BTC-USD-180213
        /// </summary>
        public string instrument_id { get; set; }
        /// <summary>
        /// 预估交割价
        /// </summary>
        public decimal settlement_price { get; set; }
        /// <summary>
        /// 系统时间戳
        /// </summary>
        public DateTime timestamp { get; set; }
    }
}
