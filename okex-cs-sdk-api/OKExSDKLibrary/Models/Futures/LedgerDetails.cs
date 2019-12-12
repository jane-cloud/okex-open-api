using System;
using System.Collections.Generic;
using System.Text;

namespace OKExSDK.Models.Futures
{
    public class LedgerDetails
    {
        /// <summary>
        /// 订单ID
        /// </summary>
        public long order_id { get; set; }
        /// <summary>
        /// 合约ID，如BTC-USD-180213
        /// </summary>
        public string instrument_id { get; set; }
    }
}
