using System;
using System.Collections.Generic;
using System.Text;

namespace OKExSDK.Models.Spot
{
    public class SpotCandle
    {
        /// <summary>
        /// 开始时间
        /// </summary>
        public DateTime time { get; set; }
        /// <summary>
        /// 最低价格
        /// </summary>
        public string low { get; set; }
        /// <summary>
        /// 最高价格
        /// </summary>
        public string high { get; set; }
        /// <summary>
        /// 开盘价格
        /// </summary>
        public string open { get; set; }
        /// <summary>
        /// 收盘价格
        /// </summary>
        public string close { get; set; }
        /// <summary>
        /// 交易量
        /// </summary>
        public string volume { get; set; }
    }
}
