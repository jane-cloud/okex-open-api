using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace OKExSDK.Models.Swap
{
    public class HistoricalFundingRate
    {
        /// <summary>
        /// 合约名称，如BTC-USD-SWAP
        /// </summary>
        public string instrument_id { get; set; }
        /// <summary>
        /// 资金费率
        /// </summary>
        public decimal funding_rate { get; set; }
        /// <summary>
        /// 实际资金费率
        /// </summary>
        public decimal realized_rate { get; set; }
        /// <summary>
        /// 利率
        /// </summary>
        public decimal interest_rate { get; set; }
        /// <summary>
        /// 结算时间
        /// </summary>
        public DateTime funding_time { get; set; }
    }
}
