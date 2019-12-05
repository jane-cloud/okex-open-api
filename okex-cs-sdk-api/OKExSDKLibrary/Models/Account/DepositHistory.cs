using System;
using System.Collections.Generic;
using System.Text;

namespace OKExSDK.Models.Account
{
    public class DepositHistory
    {
        /// <summary>
        /// 币种名称，如：btc
        /// </summary>
        public string currency { get; set; }
        /// <summary>
        /// 充值数量
        /// </summary>
        public decimal amount { get; set; }
        /// <summary>
        /// 此笔充值到账地址
        /// </summary>
        public string to { get; set; }
        /// <summary>
        /// 区块转账哈希记录
        /// </summary>
        public string txid { get; set; }
        /// <summary>
        /// 充值到账时间
        /// </summary>
        public DateTime timestamp { get; set; }
        /// <summary>
        /// 提现状态（0:等待确认;1:确认到账;2:充值成功；）
        /// </summary>
        public int status { get; set; }
    }
}
