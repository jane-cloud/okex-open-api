using System;
using System.Collections.Generic;
using System.Text;

namespace OKExSDK.Models.Account
{
    public class TransferResult
    {
        /// <summary>
        /// 划转ID
        /// </summary>
        public int transfer_id { get; set; }
        /// <summary>
        /// 币种
        /// </summary>
        public string currency { get; set; }
        /// <summary>
        /// 划转数量
        /// </summary>
        public decimal amount { get; set; }
        /// <summary>
        /// 转出账户(0:子账户 1:币币 3:合约 4:C2C 5:币币杠杆 6:钱包 7:ETT)
        /// </summary>
        public int from { get; set; }
        /// <summary>
        /// 转入账户(0:子账户 1:币币 3:合约 4:C2C 5:币币杠杆 6:钱包 7:ETT)
        /// </summary>
        public int to { get; set; }
        /// <summary>
        /// 划转结果。若是划转失败，将给出错误码提示
        /// </summary>
        public bool result { get; set; }
    }
}
