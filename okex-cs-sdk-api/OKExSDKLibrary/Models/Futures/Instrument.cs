using System;
using System.Collections.Generic;
using System.Text;

namespace OKExSDK.Models.Futures
{
    public class Instrument
    {
        /// <summary>
        /// 合约ID，如BTC-USD-180213
        /// </summary>
        public string instrument_id { get; set; }
        /// <summary>
        /// 交易货币币种，如：btc-usd中的btc
        /// </summary>
        public string underlying_index { get; set; }
        /// <summary>
        /// 计价货币币种，如：btc-usd中的usd
        /// </summary>
        public string quote_currency { get; set; }
        /// <summary>
        /// 合约面值(美元)
        /// </summary>
        public decimal contract_val { get; set; }
        /// <summary>
        /// 上线日期
        /// </summary>
        public DateTime listing { get; set; }
        /// <summary>
        /// 交割日期
        /// </summary>
        public DateTime delivery { get; set; }
        /// <summary>
        /// 下单价格精度
        /// </summary>
        public decimal tick_size { get; set; }
        /// <summary>
        /// 下单数量精度
        /// </summary>
        public decimal trade_increment { get; set; }
    }
}
