using System;
using System.Collections.Generic;
using System.Text;

namespace OKExSDK.Models.Account
{
    public class AccountLedger
    {
        /// <summary>
        /// 账单ID
        /// </summary>
        public int ledger_id { get; set; }
        /// <summary>
        /// 币种
        /// </summary>
        public string currency { get; set; }
        /// <summary>
        /// 余额
        /// </summary>
        public decimal balance { get; set; }
        /// <summary>
        /// 变动数量
        /// </summary>
        public decimal amount { get; set; }
        /// <summary>
        /// 账单类型
        /// </summary>
        public string typename { get; set; }
        /// <summary>
        /// 手续费
        /// </summary>
        public decimal fee { get; set; }
        /// <summary>
        /// 账单创建时间
        /// </summary>
        public decimal timestamp { get; set; }
    }
}
