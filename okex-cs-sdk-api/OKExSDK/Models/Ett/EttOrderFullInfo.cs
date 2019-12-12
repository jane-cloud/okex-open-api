using System;
using System.Collections.Generic;
using System.Text;

namespace OKExSDK.Models.Ett
{
    public class EttOrderFullInfo
    {
        /// <summary>
        /// 订单ID
        /// </summary>
        public string order_id { get; set; }
        /// <summary>
        /// 价格
        /// </summary>
        public string price { get; set; }
        /// <summary>
        /// 订单类型(0：组合申购 1:用USDT申购 2:赎回USDT 3:立即赎回ett成分)
        /// </summary>
        public int type { get; set; }
        /// <summary>
        /// 申购/赎回币种
        /// </summary>
        public string quote_currency { get; set; }
        /// <summary>
        /// 申购/赎回币种总量
        /// </summary>
        public string amount { get; set; }
        /// <summary>
        /// ett数量
        /// </summary>
        public string size { get; set; }
        /// <summary>
        /// ett名称
        /// </summary>
        public string ett { get; set; }
        /// <summary>
        /// 订单创建时间
        /// </summary>
        public DateTime created_at { get; set; }
        /// <summary>
        /// 订单状态
        /// </summary>
        public string status { get; set; }
    }
}
