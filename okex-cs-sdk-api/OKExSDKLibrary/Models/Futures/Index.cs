using System;
using System.Collections.Generic;
using System.Text;

namespace OKExSDK.Models.Futures
{
    public class Index
    {
        /// <summary>
        /// 指数币对，如BTC-USD
        /// </summary>
        public string instrument_id { get; set; }
        /// <summary>
        /// 指数价格
        /// </summary>
        public decimal index { get; set; }
        /// <summary>
        /// 系统时间戳
        /// </summary>
        public DateTime timestamp { get; set; }
    }
}
