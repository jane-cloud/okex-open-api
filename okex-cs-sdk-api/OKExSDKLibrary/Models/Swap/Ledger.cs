using System;
using System.Collections.Generic;
using System.Text;

namespace OKExSDK.Models.Swap
{
    public class Ledger
    {
        /// <summary>
        /// 账单ID
        /// </summary>
        public string ledger_id { get; set; }
        /// <summary>
        /// 变动金额
        /// </summary>
        public decimal amount { get; set; }
        /// <summary>
        /// 账单类型
        /// </summary>
        public string type { get; set; }
        /// <summary>
        /// 手续费
        /// </summary>
        public decimal fee { get; set; }
        /// <summary>
        /// 账单创建时间
        /// </summary>
        public DateTime timestamp { get; set; }
        /// <summary>
        /// 合约名称
        /// </summary>
        public string instrument_id { get; set; }
    }
}
