using System;
using System.Collections.Generic;
using System.Text;

namespace OKExSDK.Models.Futures
{
    public class CancelOrderBatchResult
    {
        /// <summary>
        /// 撤销指定合约品种的订单
        /// </summary>
        public string instrument_id { get; set; }
        /// <summary>
        /// 调用接口返回结果
        /// </summary>
        public string result { get; set; }
        /// <summary>
        /// 撤销指定的订单ID
        /// </summary>
        public List<string> order_ids { get; set; }
    }
}
