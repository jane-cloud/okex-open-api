using System;
using System.Collections.Generic;
using System.Text;

namespace OKExSDK.Models.Futures
{
    public class SetCrossedLeverageResult
    {
        /// <summary>
        /// 账户类型：全仓 crossed
        /// </summary>
        public string margin_mode { get; set; }
        /// <summary>
        /// 币种，如：btc
        /// </summary>
        public string currency { get; set; }
        /// <summary>
        /// 已设定的杠杆倍数，10或20
        /// </summary>
        public int leverage { get; set; }
        /// <summary>
        /// 返回设定结果，成功或错误码
        /// </summary>
        public string result { get; set; }
    }
}
