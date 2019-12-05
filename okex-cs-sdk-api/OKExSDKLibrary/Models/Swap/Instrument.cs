using System;
using System.Collections.Generic;
using System.Text;

namespace OKExSDK.Models.Swap
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
        /// 保证金币种，如BTC-USD-SWAP中BTC
        /// </summary>
        public string coin { get; set; }
        /// <summary>
        /// 合约面值
        /// </summary>
        public decimal contract_val { get; set; }
        /// <summary>
        /// 创建时间
        /// </summary>
        public DateTime listing { get; set; }
        /// <summary>
        /// 结算时间
        /// </summary>
        public DateTime delivery { get; set; }
        /// <summary>
        /// 下单价格精度
        /// </summary>
        public decimal tick_size { get; set; }
        /// <summary>
        /// 数量精度
        /// </summary>
        public decimal size_increment { get; set; }
    }
}
