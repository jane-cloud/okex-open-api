using System;
using System.Collections.Generic;
using System.Text;

namespace OKExSDK.Models.Futures
{
    public class OrderBatch
    {
        /// <summary>
        /// 合约ID，如BTC-USD-180213
        /// </summary>
        public string instrument_id { get; set; }
        /// <summary>
        /// 要设定的杠杆倍数，10或20
        /// </summary>
        public int leverage { get; set; }
        /// <summary>
        /// 订单列表
        /// </summary>
        public string orders_data { get; set; }
    }
}
