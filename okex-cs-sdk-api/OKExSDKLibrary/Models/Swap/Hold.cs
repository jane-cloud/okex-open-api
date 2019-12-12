using System;
using System.Collections.Generic;
using System.Text;

namespace OKExSDK.Models.Swap
{
    public class Hold
    {
        /// <summary>
        /// 合约ID，如BTC-USD-180213
        /// </summary>
        public string instrument_id { get; set; }
        /// <summary>
        /// 冻结数量
        /// </summary>
        public string amount { get; set; }
        /// <summary>
        /// 查询时间
        /// </summary>
        public DateTime timestamp { get; set; }
    }
}
