using System;
using System.Collections.Generic;
using System.Text;

namespace OKExSDK.Models.Futures
{
    public class Ledger
    {
        /// <summary>
        /// 账单ID
        /// </summary>
        public string ledger_id { get; set; }
        /// <summary>
        /// 币种，如：btc
        /// </summary>
        public string currency { get; set; }
        /// <summary>
        /// 变动数量
        /// </summary>
        public decimal amount { get; set; }
        /// <summary>
        /// 流水来源
        /// </summary>
        public string type { get; set; }
        /// <summary>
        /// 账单创建时间
        /// </summary>
        public DateTime timestamp { get; set; }
        /// <summary>
        /// 如果类型是交易产生的，则该details字段将包含order_id和instrument_id //TODO transfer fee 内容
        /// </summary>
        public LedgerDetails details { get; set; }
    }
}
