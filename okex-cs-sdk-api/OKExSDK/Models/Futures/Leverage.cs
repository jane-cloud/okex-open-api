using System;
using System.Collections.Generic;
using System.Text;

namespace OKExSDK.Models.Futures
{
    public class Leverage
    {
        /// <summary>
        /// 账户类型：全仓 crossed 逐仓 fixed
        /// </summary>
        public string margin_mode { get; set; }

    }
}
