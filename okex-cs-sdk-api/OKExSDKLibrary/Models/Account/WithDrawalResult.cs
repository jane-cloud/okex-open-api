using System;
using System.Collections.Generic;
using System.Text;

namespace OKExSDK.Models.Account
{
    public class WithDrawalResult
    {
        /// <summary>
        /// 提币币种
        /// </summary>
        public string currency { get; set; }
        /// <summary>
        /// 提币数量
        /// </summary>
        public decimal amount { get; set; }
        /// <summary>
        /// 提币申请ID
        /// </summary>
        public int withdraw_id { get; set; }
        /// <summary>
        /// 提币申请结果。若是提现申请失败，将给出错误码提示
        /// </summary>
        public bool result { get; set; }
    }
}
