using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace OKExSDK.Models.Swap
{
    public class FundingTime
    {
        /// <summary>
        /// 合约名称，如BTC-USD-SWAP
        /// </summary>
        public string instrument_id { get; set; }
        /// <summary>
        /// 下一次的结算时间
        /// </summary>
        public string funding_time { get; set; }
    }
}
