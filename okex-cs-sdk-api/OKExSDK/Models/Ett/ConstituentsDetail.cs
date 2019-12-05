using System;
using System.Collections.Generic;
using System.Text;

namespace OKExSDK.Models.Ett
{
    public class ConstituentsDetail
    {
        /// <summary>
        /// 每份ett包含币种成分数量
        /// </summary>
        public decimal amount { get; set; }
        /// <summary>
        /// ett包含币种成分
        /// </summary>
        public string currency { get; set; }
    }
}
