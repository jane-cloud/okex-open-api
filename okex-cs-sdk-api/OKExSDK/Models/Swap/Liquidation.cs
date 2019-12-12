using System;
using System.Collections.Generic;
using System.Text;

namespace OKExSDK.Models.Swap
{
    public class Liquidation
    {
        /// <summary>
        /// 合约ID，如BTC-USD-180213
        /// </summary>
        public string instrument_id { get; set; }
        /// <summary>
        /// 数量
        /// </summary>
        public int size { get; set; }
        /// <summary>
        /// 爆仓单的委托时间
        /// </summary>
        public DateTime created_at { get; set; }
        /// <summary>
        /// 穿仓用户亏损
        /// </summary>
        public decimal loss { get; set; }
        /// <summary>
        /// 订单价格
        /// </summary>
        public decimal price { get; set; }
        /// <summary>
        /// 订单类型(1:开多 2:开空 3:平多 4:平空)
        /// </summary>
        public int type { get; set; }
    }
}
