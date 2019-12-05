using System;
using System.Collections.Generic;
using System.Text;

namespace OKExSDK.Models.Futures
{
    public class Fill
    {
        /// <summary>
        /// 账单ID
        /// </summary>
        public long trade_id { get; set; }
        /// <summary>
        /// 合约ID，如BTC-USD-180213
        /// </summary>
        public string instrument_id { get; set; }
        /// <summary>
        /// 价格
        /// </summary>
        public decimal price { get; set; }
        /// <summary>
        /// 数量
        /// </summary>
        public string order_qty { get; set; }
        /// <summary>
        /// 订单ID
        /// </summary>
        public string order_id { get; set; }
        /// <summary>
        /// 订单创建时间
        /// </summary>
        public DateTime timestamp { get; set; }
        /// <summary>
        /// 流动性方向(T or M)
        /// </summary>
        public string exec_type { get; set; }
        /// <summary>
        /// 手续费
        /// </summary>
        public decimal fee { get; set; }
        /// <summary>
        /// 订单方向(buy or sell)
        /// </summary>
        public string side { get; set; }
    }
}
