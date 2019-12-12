using System;
using System.Collections.Generic;
using System.Text;

namespace OKExSDK.Models.Account
{
    public class WithdrawalFee
    {
        /// <summary>
        /// 币种
        /// </summary>
        public string currency { get; set; }
        /// <summary>
        /// 最小提币手续费数量
        /// </summary>
        public decimal min_fee { get; set; }
        /// <summary>
        /// 最大提币手续费数量
        /// </summary>
        public decimal max_fee { get; set; }
    }
}
