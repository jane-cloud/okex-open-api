using System;
using System.Collections.Generic;
using System.Text;

namespace OKExSDK.Models.Futures
{
    public class CancelOrderResult
    {
        /// <summary>
        /// 订单ID
        /// </summary>
        public long order_id { get; set; }
        /// <summary>
        /// 撤单申请结果
        /// </summary>
        public string result { get; set; }
        /// <summary>
        /// 系统时间戳
        /// </summary>
        public DateTime timestamp { get; set; }    
    }
}
