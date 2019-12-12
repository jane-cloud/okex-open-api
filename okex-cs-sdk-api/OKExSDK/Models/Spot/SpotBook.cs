using System;
using System.Collections.Generic;
using System.Text;

namespace OKExSDK.Models.Spot
{
    public class SpotBook
    {
        public List<List<string>> bids { get; set; }
        public List<List<string>> asks { get; set; }
    }
}
