using System;
using System.Collections.Generic;
using System.Text;

namespace OKExSDK.Models.Swap
{
    public class Account
    {
        /// <summary>
        /// 账户权益
        /// </summary>
        public string equity { get; set; }
        /// <summary>
        /// 账户余额
        /// </summary>
        public string total_avail_balance { get; set; }
        /// <summary>
        /// 已用保证金
        /// </summary>
        public decimal margin { get; set; }
        /// <summary>
        /// 已实现盈亏
        /// </summary>
        public decimal realized_pnl { get; set; }
        /// <summary>
        /// 未实现盈亏
        /// </summary>
        public decimal unrealized_pnl { get; set; }
        /// <summary>
        /// 保证金率
        /// </summary>
        public double margin_ratio { get; set; }
        /// <summary>
        /// 合约ID，如BTC-USD-180213
        /// </summary>
        public string instrument_id { get; set; }
        /// <summary>
        /// 开仓冻结保证金
        /// </summary>
        public string margin_frozen { get; set; }
        /// <summary>
        /// 账户类型：全仓 crossed 逐仓 fixed
        /// </summary>
        public string margin_mode { get; set; }
        /// <summary>
        /// 创建时间
        /// </summary>
        public DateTime timestamp { get; set; }
    }
}
