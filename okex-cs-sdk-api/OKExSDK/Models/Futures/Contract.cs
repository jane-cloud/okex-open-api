using System;
using System.Collections.Generic;
using System.Text;

namespace OKExSDK.Models.Futures
{
    public class Contract
    {
        /// <summary>
        /// 逐仓可用余额
        /// </summary>
        public string available_qty { get; set; }
        /// <summary>
        /// 逐仓账户余额
        /// </summary>
        public string fixed_balance { get; set; }
        /// <summary>
        /// 合约ID，如BTC-USD-180213
        /// </summary>
        public string instrument_id { get; set; }
        /// <summary>
        /// 挂单冻结保证金
        /// </summary>
        public decimal margin_for_unfilled { get; set; }
        /// <summary>
        /// 冻结的保证金(成交以后仓位所需的)
        /// </summary>
        public string margin_frozen { get; set; }
        /// <summary>
        /// 已实现盈亏
        /// </summary>
        public string realized_pnl { get; set; }
        /// <summary>
        /// 未实现盈亏
        /// </summary>
        public string unrealized_pnl { get; set; }
    }
}
