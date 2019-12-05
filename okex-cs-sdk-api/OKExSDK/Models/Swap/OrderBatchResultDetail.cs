using System;
using System.Collections.Generic;
using System.Text;

namespace OKExSDK.Models.Swap
{
    public class OrderBatchResultDetail
    {
        /// <summary>
        /// 订单ID，下单失败时，此字段值为-1
        /// </summary>
        public string order_id { get; set; }
        /// <summary>
        /// 由您设置的订单ID来识别您的订单
        /// </summary>
        public string client_oid { get; set; }
        /// <summary>
        /// 错误码，下单成功时为0，下单失败时会显示相应错误码
        /// </summary>
        public string error_code { get; set; }
        /// <summary>
        /// 错误信息，下单成功时为空，下单失败时会显示错误信息
        /// </summary>
        public string error_message { get; set; }
    }
}
