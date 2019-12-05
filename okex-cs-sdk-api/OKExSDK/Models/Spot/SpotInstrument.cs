using System;
using System.Collections.Generic;
using System.Text;

namespace OKExSDK.Models.Spot
{
    public class SpotInstrument
    {
        /// <summary>
        /// 币对名称
        /// </summary>
        public string instrument_id { get; set; }
        /// <summary>
        /// 交易货币币种
        /// </summary>
        public string base_currency { get; set; }
        /// <summary>
        /// 计价货币币种
        /// </summary>
        public string quote_currency { get; set; }
        /// <summary>
        /// 最小交易数量
        /// </summary>
        public string min_size { get; set; }
        /// <summary>
        /// 交易货币数量精度
        /// </summary>
        public string size_increment { get; set; }
        /// <summary>
        /// 交易价格精度
        /// </summary>
        public string tick_size { get; set; }
    }
}
