using System;
using System.Collections.Generic;
using System.Text;

namespace OKExSDK.Models.Account
{
    public class WithDrawalHistory
    {
        /// <summary>
        /// 币种
        /// </summary>
        public string currency { get; set; }
        /// <summary>
        /// 数量
        /// </summary>
        public decimal amount { get; set; }
        /// <summary>
        /// 提币申请时间
        /// </summary>
        public DateTime timestamp { get; set; }
        /// <summary>
        /// 提币地址(如果收币地址是OKEx平台地址，则此处将显示用户账户)
        /// </summary>
        public string from { get; set; }
        /// <summary>
        /// 收币地址
        /// </summary>
        public string to { get; set; }
        /// <summary>
        /// 部分币种提币需要标签，若不需要则不返回此字段
        /// </summary>
        public string tag { get; set; }
        /// <summary>
        /// 部分币种提币需要此字段，若不需要则不返回此字段
        /// </summary>
        public string payment_id { get; set; }
        /// <summary>
        /// 提币哈希记录(内部转账将不返回此字段)
        /// </summary>
        public string txid { get; set; }
        /// <summary>
        /// 提币手续费
        /// </summary>
        public string fee { get; set; }
        /// <summary>
        /// 提现状态（-3:撤销中;-2:已撤销;-1:失败;0:等待提现;1:提现中;2:已汇出;3:邮箱确认;4:人工审核中5:等待身份认证）
        /// </summary>
        public int status { get; set; }
    }
}
