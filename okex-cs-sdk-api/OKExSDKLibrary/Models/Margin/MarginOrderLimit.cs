using System;
using System.Collections.Generic;
using System.Text;

namespace OKExSDK.Models.Margin
{
    public class MarginOrderLimit : MarginOrder
    {
        /// <summary>
        /// 价格
        /// </summary>
        public string price { get; set; }
    }
}
