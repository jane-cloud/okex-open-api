using System;
using System.Collections.Generic;
using System.Text;

namespace OKExSDK.Models.Futures
{
    public class AccountFixed
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
        /// 账户类型：逐仓 fixed
        /// </summary>
        public string margin_mode { get; set; }
        /// <summary>
        /// 合约列表
        /// </summary>
        public List<Contract> contracts { get; set; }
    }
}
