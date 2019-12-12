using System;
using System.Collections.Generic;
using System.Text;

namespace OKExSDK.Models.Account
{
    public class Transfer
    {
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
        /// 子账号登录名，from或to指定为0时，sub_account为必填项
        /// </summary>
        public string sub_account { get; set; }
        /// <summary>
        /// 杠杆币对，如：eos-usdt，仅限已开通杠杆的币对
        /// </summary>
        public string instrument_id { get; set; }
    }
}
