using System;
using System.Collections.Generic;
using System.Text;

namespace OKExSDK.Models.Margin
{
    public class MarginLedgerDetail
    {
        /// <summary>
        /// 交易的ID
        /// </summary>
        public long order_id { get; set; }
        /// <summary>
        /// 币对
        /// </summary>
        public string instrument_id { get; set; }
    }
}
