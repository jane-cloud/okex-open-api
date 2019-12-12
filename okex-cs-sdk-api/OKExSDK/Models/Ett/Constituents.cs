using System;
using System.Collections.Generic;
using System.Text;

namespace OKExSDK.Models.Ett
{
    public class Constituents
    {
        /// <summary>
        /// ett美元净值
        /// </summary>
        public decimal net_value { get; set; }
        /// <summary>
        /// ett名称
        /// </summary>
        public string ett { get; set; }
        /// <summary>
        /// 包含币种成分信息
        /// </summary>
        public List<ConstituentsDetail> constituents { get; set; }

    }
}
