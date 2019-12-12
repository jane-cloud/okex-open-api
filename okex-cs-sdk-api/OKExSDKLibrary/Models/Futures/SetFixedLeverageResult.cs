using System;
using System.Collections.Generic;
using System.Text;

namespace OKExSDK.Models.Futures
{
    public class SetFixedLeverageResult
    {
        /// <summary>
        /// 账户类型：逐仓 fixed
        /// </summary>
        public string margin_mode { get; set; }
        /// <summary>
        /// 合约ID，如BTC-USD-180213
        /// </summary>
        public string instrument_id { get; set; }
        /// <summary>
        /// 开仓方向，long(做多)或者short(做空)
        /// </summary>
        public string direction { get; set; }
        /// <summary>
        /// 已设定的杠杆倍数，10或20
        /// </summary>
        public int leverage { get; set; }
        /// <summary>
        /// 返回设定结果，成功或错误码
        /// </summary>
        public string result { get; set; }
    }
}
