using System;
using System.Collections.Generic;
using System.Text;

namespace OKExSDK.Models.Futures
{
    public class Account
    {
        /// <summary>
        /// 币种，如：btc
        /// </summary>
        public string currency { get; set; }
        /// <summary>
        /// 账户类型：全仓 crossed 逐仓 fixed
        /// </summary>
        public string margin_mode { get; set; }
        /// <summary>
        /// 账户权益
        /// </summary>
        public string equity { get; set; }
        /// <summary>
        /// 账户余额
        /// </summary>
        public decimal total_avail_balance { get; set; }
        /// <summary>
        /// 已实现盈亏
        /// </summary>
        public decimal realized_pnl { get; set; }
        /// <summary>
        /// 未实现盈亏
        /// </summary>
        public decimal unrealized_pnl { get; set; }
    }
}
