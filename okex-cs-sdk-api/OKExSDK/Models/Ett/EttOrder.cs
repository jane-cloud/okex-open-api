using System;
using System.Collections.Generic;
using System.Text;

namespace OKExSDK.Models.Ett
{
    public class EttOrder
    {
        /// <summary>
        /// [非必填]由您设置的订单ID来识别您的订单
        /// </summary>
        public string client_oid { get; set; }
        /// <summary>
        /// 订单类型(0：组合申购 1:用USDT申购 2:赎回USDT 3:立即赎回ett成分)
        /// </summary>
        public int type { get; set; }
        /// <summary>
        /// 申购/赎回币种
        /// </summary>
        public string quote_currency { get; set; }
        /// <summary>
        /// 申购金额，usdt申购必填参数
        /// </summary>
        public decimal? amount { get; set; }
        /// <summary>
        /// 赎回数量，赎回和组合申购必填参数
        /// </summary>
        public decimal? size { get; set; }
        /// <summary>
        /// 申购/赎回ett名称
        /// </summary>
        public string ett { get; set; }
    }
}
