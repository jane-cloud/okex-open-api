using System;
using System.Collections.Generic;
using System.Text;

namespace OKExSDK.Models.Swap
{
    public class CancelOrderResult
    {
        /// <summary>
        /// 订单ID
        /// </summary>
        public string order_id { get; set; }
        /// <summary>
        /// 撤单申请结果
        /// </summary>
        public string result { get; set; } 
    }
}
