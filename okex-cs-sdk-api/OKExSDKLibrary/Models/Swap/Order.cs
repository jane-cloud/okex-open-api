using System;
using System.Collections.Generic;
using System.Text;

namespace OKExSDK.Models.Swap
{
    public class Order
    {
        /// <summary>
        /// 合约ID，如BTC-USD-180213
        /// </summary>
        public string instrument_id { get; set; }
        /// <summary>
        /// 数量
        /// </summary>
        public string size { get; set; }
        /// <summary>
        /// 委托时间
        /// </summary>
        public DateTime timestamp { get; set; }
        /// <summary>
        /// 成交数量
        /// </summary>
        public string filled_qty { get; set; }
        /// <summary>
        /// 手续费
        /// </summary>
        public decimal fee { get; set; }
        /// <summary>
        /// 订单ID
        /// </summary>
        public string order_id { get; set; }
        /// <summary>
        /// 订单价格
        /// </summary>
        public decimal price { get; set; }
        /// <summary>
        /// 平均价格
        /// </summary>
        public decimal price_avg { get; set; }
        /// <summary>
        /// 订单状态(-1.撤单成功；0:等待成交 1:部分成交 2:已完成）
        /// </summary>
        public string status { get; set; }
        /// <summary>
        /// 订单类型(1:开多 2:开空 3:开多 4:平空)
        /// </summary>
        public string type { get; set; }
        /// <summary>
        /// 合约面值
        /// </summary>
        public string contract_val { get; set; }
    }
}
