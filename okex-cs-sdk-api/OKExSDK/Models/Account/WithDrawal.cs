using System;
using System.Collections.Generic;
using System.Text;

namespace OKExSDK.Models.Account
{
    public class WithDrawal
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
        /// 提币到(2:OKCoin国际 3:OKEx 4:数字货币地址)
        /// </summary>
        public int destination { get; set; }
        /// <summary>
        /// 认证过的数字货币地址、邮箱或手机号
        /// </summary>
        public string to_address { get; set; }
        /// <summary>
        /// 交易密码
        /// </summary>
        public string trade_pwd { get; set; }
        /// <summary>
        /// 网络手续费≥0.提币到OKCoin国际或OKEx免手续费，请设置为0.提币到数字货币地址所需网络手续费可通过提币手续费接口查询
        /// </summary>
        public decimal fee { get; set; }
    }
}
