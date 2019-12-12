using System;
using System.Collections.Generic;
using System.Text;

namespace OKExSDK.Models.Futures
{
    public class Position
    {
        /// <summary>
        /// 账户类型：全仓 crossed
        /// </summary>
        public string margin_mode { get; set; }
        /// <summary>
        /// 多仓数量
        /// </summary>
        public int long_qty { get; set; }
        /// <summary>
        /// 多仓可平仓数量
        /// </summary>
        public int long_avail_qty { get; set; }
        /// <summary>
        /// 开仓平均价
        /// </summary>
        public decimal long_avg_cost { get; set; }
        /// <summary>
        /// 多仓结算基准价
        /// </summary>
        public decimal long_settlement_price { get; set; }
        /// <summary>
        /// 已实现盈余
        /// </summary>
        public decimal realized_pnl { get; set; }
        /// <summary>
        /// 空仓数量
        /// </summary>
        public int short_qty { get; set; }
        /// <summary>
        /// 空仓可平仓数量
        /// </summary>
        public int short_avail_qty { get; set; }
        /// <summary>
        /// 开仓平均价
        /// </summary>
        public decimal short_avg_cost { get; set; }
        /// <summary>
        /// 空仓结算基准价
        /// </summary>
        public string short_settlement_price { get; set; }
        /// <summary>
        /// 合约ID，如BTC-USD-180213
        /// </summary>
        public string instrument_id { get; set; }
        /// <summary>
        /// 创建时间
        /// </summary>
        public DateTime created_at { get; set; }
        /// <summary>
        /// 更新时间
        /// </summary>
        public DateTime updated_at { get; set; }
    }
}
