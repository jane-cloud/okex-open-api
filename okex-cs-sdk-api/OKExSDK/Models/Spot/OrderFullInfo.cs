using System;
using System.Collections.Generic;
using System.Text;

namespace OKExSDK.Models.Spot
{
    public class OrderFullInfo
    {
        /// <summary>
        /// 订单ID
        /// </summary>
        public long order_id { get; set; }
        /// <summary>
        /// 价格
        /// </summary>
        public string price { get; set; }
        /// <summary>
        /// 交易货币数量
        /// </summary>
        public string size { get; set; }
        /// <summary>
        /// 买入金额，市价买入时返回
        /// </summary>
        public string notional { get; set; }
        /// <summary>
        /// 币对名称
        /// </summary>
        public string instrument_id { get; set; }
        /// <summary>
        /// limit,market(默认是limit)
        /// </summary>
        public string type { get; set; }
        /// <summary>
        /// buy or sell
        /// </summary>
        public string side { get; set; }
        /// <summary>
        /// 订单创建时间
        /// </summary>
        public DateTime timestamp { get; set; }
        /// <summary>
        /// 已成交数量
        /// </summary>
        public string filled_size { get; set; }
        /// <summary>
        /// 已成交金额
        /// </summary>
        public string filled_notional { get; set; }
        /// <summary>
        /// 订单状态
        /// </summary>
        public string status { get; set; }
    }
}
