using System;
using System.Collections.Generic;
using System.Text;

namespace OKExSDK.Models.Ett
{
    public class EttLedger
    {
        /// <summary>
        /// 账单ID
        /// </summary>
        public long ledger_id { get; set; }
        /// <summary>
        /// 币种或ett名称
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
        /// 流水来源
        /// </summary>
        public string type { get; set; }
        /// <summary>
        /// 账单创建时间
        /// </summary>
        public DateTime created_at { get; set; }
        /// <summary>
        /// 如果类型是交易产生的，则该details字段将包含该交易的关联信息
        /// </summary>
        public EttLedgerDetail details { get; set; }
    }
}
