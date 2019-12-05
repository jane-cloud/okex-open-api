using System;
using System.Collections.Generic;
using System.Text;

namespace OKExSDK.Models.Swap
{
    public class Position
    {
        /// <summary>
        /// 预估爆仓价
        /// </summary>
        public decimal liquidation_price { get; set; }
        /// <summary>
        /// 持仓数量
        /// </summary>
        public string position { get; set; }
        /// <summary>
        /// 可平数量
        /// </summary>
        public string avail_position { get; set; }
        /// <summary>
        /// 保证金
        /// </summary>
        public string margin { get; set; }
        /// <summary>
        /// 开仓平均价
        /// </summary>
        public string avg_cost { get; set; }
        /// <summary>
        /// 结算基准价
        /// </summary>
        public string settlement_price { get; set; }
        /// <summary>
        /// 合约ID，如BTC-USD-180213
        /// </summary>
        public string instrument_id { get; set; }
        /// <summary>
        /// 杠杆倍数
        /// </summary>
        public int leverage { get; set; }
        /// <summary>
        /// 已实现盈亏
        /// </summary>
        public decimal realized_pnl { get; set; }
        /// <summary>
        /// 方向
        /// </summary>
        public string side { get; set; }
        /// <summary>
        /// 创建时间
        /// </summary>
        public DateTime timestamp { get; set; }
    }
}
