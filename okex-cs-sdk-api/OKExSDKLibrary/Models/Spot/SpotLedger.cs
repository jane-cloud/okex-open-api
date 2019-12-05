using System;
using System.Collections.Generic;
using System.Text;

namespace OKExSDK.Models.Spot
{
    public class SpotLedger
    {
        /// <summary>
        /// 账单ID
        /// </summary>
        public long ledger_id { get; set; }
        /// <summary>
        /// 币种
        /// </summary>
        public string currency { get; set; }
        /// <summary>
        /// 余额
        /// </summary>
        public string balance { get; set; }
        /// <summary>
        /// 变动数量
        /// </summary>
        public string amount { get; set; }
        /// <summary>
        /// 流水来源
        /// </summary>
        public string type { get; set; }
        /// <summary>
        /// 账单创建时间
        /// </summary>
        public DateTime timestamp { get; set; }
        /// <summary>
        /// 如果type是trade或者fee，则会有该details字段将包含order,instrument信息
        /// </summary>
        public SpotLedgerDetail details { get; set; }
    }
}
