using System;
using System.Collections.Generic;
using System.Text;

namespace OKExSDK.Models.Margin
{
    public class BorrowResult
    {
        /// <summary>
        /// 借币记录ID
        /// </summary>
        public long borrow_id { get; set; }
        /// <summary>
        /// 结果
        /// </summary>
        public bool result { get; set; }
    }
}
