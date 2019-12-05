using System;
using System.Collections.Generic;
using System.Text;

namespace OKExSDK.Models.Margin
{
    public class MarginAvailable
    {
        /// <summary>
        /// 当前最大可借
        /// </summary>
        public string available { get; set; }
        /// <summary>
        /// 借币利率
        /// </summary>
        public string rate { get; set; }
        /// <summary>
        /// 最大杠杆倍数
        /// </summary>
        public string leverage { get; set; }
    }
}
