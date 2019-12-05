using System;
using System.Collections.Generic;
using System.Text;

namespace OKExSDK.Models.Spot
{
    public class SpotFill
    {
        /// <summary>
        /// 账单ID
        /// </summary>
        public long ledger_id { get; set; }
        /// <summary>
        /// 币对名称
        /// </summary>
        public string instrument_id { get; set; }
        /// <summary>
        /// 价格
        /// </summary>
        public string price { get; set; }
        /// <summary>
        /// 数量
        /// </summary>
        public string size { get; set; }
        /// <summary>
        /// 订单ID
        /// </summary>
        public long order_id { get; set; }
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
        public string fee { get; set; }
        /// <summary>
        /// 订单方向(buy or sell)
        /// </summary>
        public string side { get; set; }
    }
}
