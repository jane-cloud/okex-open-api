using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace OKExSDK.Models.Swap
{
    public class OrderBatch
    {
        /// <summary>
        /// 合约ID，如BTC-USD-180213
        /// </summary>
        public string instrument_id { get; set; }
        /// <summary>
        /// 订单列表
        /// </summary>
        public string order_data { get; set; }

    }
}
