using System;
using System.Collections.Generic;
using System.Text;

namespace OKExSDK.Models.Swap
{
    public class Rate
    {
        /// <summary>
        /// 合约ID，如BTC-USD-180213
        /// </summary>
        public string instrument_id { get; set; }
        /// <summary>
        /// 汇率
        /// </summary>
        public decimal rate { get; set; }
        /// <summary>
        /// 系统时间戳
        /// </summary>
        public DateTime timestamp { get; set; }
    }
}
