using OKExSDK.Models.Account;
using OKExSDK.Models.Ett;
using OKExSDK.Models.Futures;
using OKExSDK.Models.Margin;
using OKExSDK.Models.Spot;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using swap = OKExSDK.Models.Swap;

namespace SampleCS
{
    public class MainViewModel : INotifyPropertyChanged
    {
        public MainViewModel()
        {
            this.ordertypes = new Dictionary<string, string>();
            this.strategy_type = new Dictionary<string, string>();
            this.ordertypes.Add("开多", "1");
            this.ordertypes.Add("开空", "2");
            this.ordertypes.Add("平多", "3");
            this.ordertypes.Add("平空", "4");
            this.transferTypes.Add("子账户", "0");
            this.transferTypes.Add("币币", "1");
            this.transferTypes.Add("合约", "3");
            this.transferTypes.Add("C2C", "4");
            this.transferTypes.Add("币币杠杆", "5");
            this.transferTypes.Add("钱包", "6");
            this.transferTypes.Add("ETT", "7");
            this.strategy_type.Add("普通委托", "0");
            this.strategy_type.Add("只做Maker", "1");
            this.strategy_type.Add("全部成交或立即取消", "2");
            this.strategy_type.Add("立即成交并取消剩余", "3");

            this.destinationTypes.Add("OKCoin国际", "2");
            this.destinationTypes.Add("OKEx", "3");
            this.destinationTypes.Add("数字货币地址", "4");

            this.EttOrderTypes.Add("组合申购", "0");
            this.EttOrderTypes.Add("用USDT申购", "1");
            this.EttOrderTypes.Add("赎回USDT", "2");
            this.EttOrderTypes.Add("立即赎回ett成分", "3");

        }

        public Dictionary<string, string> transferTypes { get; set; } = new Dictionary<string, string>();
        public Dictionary<string, string> destinationTypes { get; set; } = new Dictionary<string, string>();
        public Transfer Transfer { get; set; } = new Transfer();
        public WithDrawal WithDrawal { get; set; } = new WithDrawal();
        public KeyInfo KeyInfo { get; set; } = new KeyInfo();
        public SpotOrderMarket SpotOrderMarket { get; set; } = new SpotOrderMarket()
        {
            type = "market",
        };
        public SpotOrderLimit SpotOrderLimit { get; set; } = new SpotOrderLimit()
        {
            type = "limit",
        };
        public MarginOrderMarket MarginOrderMarket { get; set; } = new MarginOrderMarket()
        {
            type = "market"
        };
        public MarginOrderLimit MarginOrderLimit { get; set; } = new MarginOrderLimit()
        {
            type = "limit"
        };

        public EttOrder EttOrder { get; set; } = new EttOrder();
        public Dictionary<string, string> EttOrderTypes { get; set; } = new Dictionary<string, string>();

        public swap.OrderSingle SwapOrderSingle { get; set; } = new swap.OrderSingle();
        private OrderSingle orderSingle = new OrderSingle();

        public OrderSingle OrderSingle
        {
            get { return orderSingle; }
            set
            {
                if (OrderSingle != value)
                {
                    OrderSingle = value;
                    RaisePropertyChanged("OrderSingle");
                }
            }
        }

        public List<swap.OrderBatchDetail> OrderDetailsSwap { get; set; } = new List<swap.OrderBatchDetail>()
        {
            new swap.OrderBatchDetail()
        };
        private List<OrderBatchDetail> orderdetails = new List<OrderBatchDetail>() {
            new OrderBatchDetail()
        };

        public List<OrderBatchDetail> OrderDetails
        {
            get { return orderdetails; }
            set { orderdetails = value; }
        }

        public swap.OrderBatch OrderBatchSwap { get; set; } = new swap.OrderBatch();

        private OrderBatch orderBatch = new OrderBatch();

        public OrderBatch OrderBatch
        {
            get { return orderBatch; }
            set { orderBatch = value; }
        }

        private Dictionary<string, string> ordertypes;
        private Dictionary<string, string> strategy_type;
        public Dictionary<string, string> OrderTypes
        {
            get { return ordertypes; }
            set { ordertypes = value; }
        }
        /// <summary>
        /// 策略类型
        /// </summary>
        public Dictionary<string, string> StrategyTypes
        {
            get { return strategy_type; }
            set { ordertypes = value; }
        }
        public event PropertyChangedEventHandler PropertyChanged;

        private void RaisePropertyChanged(string propertyName)
        {
            PropertyChangedEventHandler handler = PropertyChanged;
            if (handler != null)
            {
                handler(this, new PropertyChangedEventArgs(propertyName));
            }
        }
    }
}
