using System;
using System.Collections.Generic;
using System.Text;

namespace OKExSDK.Models.Margin
{
    public class Borrowed
    {
        /// <summary>
        /// 借币记录ID
        /// </summary>
        public long borrow_id { get; set; }
        /// <summary>
        /// 杠杆币对名称
        /// </summary>
        public string instrument_id { get; set; }
        /// <summary>
        /// 币种
        /// </summary>
        public string currency { get; set; }
        /// <summary>
        /// 借币时间
        /// </summary>
        public DateTime created_at { get; set; }
        /// <summary>
        /// 借币总数量
        /// </summary>
        public string amount { get; set; }
        /// <summary>
        /// 利息总数量
        /// </summary>
        public string interest { get; set; }
        /// <summary>
        /// 已还借币数量
        /// </summary>
        public string returned_amount { get; set; }
        /// <summary>
        /// 已还利息数量
        /// </summary>
        public string paid_interest { get; set; }
        /// <summary>
        /// 最后一次计息时间
        /// </summary>
        public DateTime last_interest_time { get; set; }
    }
}
