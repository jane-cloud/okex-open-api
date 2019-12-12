## OKCoin OKEX V3 Open Api使用说明

### 1.基于.NET Standard，支持上传至NuGet

###  2.依赖Json.NET序列化/反序列化

### 3.Api简单使用

```c#
private async void btnGetPositions(object sender, RoutedEventArgs e)
{
    //创建合约API对象，传入Api Key，Secret Key, PassPhrase
    var futureApi = new FuturesApi(this.apiKey, this.secret, this.passPhrase);
    try
    {
        //调用获取合约账户所有的持仓信息方法
        var resResult = await futureApi.getPositions();
		JToken codeJToken;
        if (resResult.TryGetValue("code", out codeJToken))
        {
            var errorInfo = resResult.ToObject<ErrorResult>();
            MessageBox.Show("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
        }
        else
        {
            var result = (bool)resResult["result"];
            if (result)
            {
                var margin_mode = (string)resResult["margin_mode"];
                // 全仓
                if (margin_mode == "crossed")
                {
                    var obj = resResult.ToObject<PositionResult<PositionCrossed>>();
                    MessageBox.Show(JsonConvert.SerializeObject(resResult));
                }
                else if (margin_mode == "fixed")
                {
                    // 逐仓
                    var obj = resResult.ToObject<PositionResult<PositionFixed>>();
                    MessageBox.Show(JsonConvert.SerializeObject(resResult));
                }
            }
            else
            {
                MessageBox.Show("错误代码：" + (string)resResult["code"] + ",错误消息：" + (string)resResult["message"]);
            }
        }
    }
    catch (Exception ex)
    {
        MessageBox.Show(ex.Message);
    }
}
```

### 4.WebSocket简单使用

```C#
// 创建Websocketor对象
private WebSocketor websocketor = new WebSocketor();

/// <summary>
/// WebSocket消息推送侦听
/// </summary>
/// <param name="msg">WebSocket消息</param>
private void handleWebsocketMessage(string msg)
{
    // msg: 远端服务推送的消息，如：
    // {"event":"subscribe","channel":"swap/ticker:BTC-USD-SWAP"}
    // {"table":"swap/ticker","data":[{"best_ask":"3620.8","best_bid":"3620.7","high_24h":"3635.1","instrument_id":"BTC-USD-SWAP","last":"3620.7","low_24h":"3580","timestamp":"2019-01-19T03:43:29.567Z","volume_24h":"600655"}]}
    this.Dispatcher.BeginInvoke(new Action(() =>
                                           {
                                               this.msgBox.AppendText(msg + Environment.NewLine);
                                           }));
}

private async void btnProgress(object sender, RoutedEventArgs e)
{
    // 取消事件侦听
    websocketor.WebSocketPush -= this.handleWebsocketMessage;
    // 添加事件侦听
    websocketor.WebSocketPush += this.handleWebsocketMessage;
    try
    {
        // 建立WebSocket连接
        await websocketor.ConnectAsync();
        // 订阅无需登录的Channel
        await websocketor.Subscribe(new List<String>() { "swap/ticker:BTC-USD-SWAP", "swap/candle60s:BTC-USD-SWAP" });
        // 登录
        await websocketor.LoginAsync(this.apiKey, this.secret, this.passPhrase);
        // 等待登录
        await Task.Delay(500);
        // 订阅需要登录的Channel
        await websocketor.Subscribe(new List<String>() { "futures/account:BTC" });
    }
    catch (Exception ex)
    {
    }
}
```

### 5.接口列表

#### a)钱包API对应AccountApi

##### 获取币种列表

```C#
public async Task<JContainer> getCurrenciesAsync()
```

##### 钱包账户信息

```C#
public async Task<JContainer> getWalletInfoAsync()
```

##### 单一币种账户信息

```C#
/// <param name="currency">币种，如：btc</param>
public async Task<JContainer> getWalletInfoByCurrencyAsync(string currency)
```

##### 资金划转

```C#
/// <param name="transfer">划转信息</param>
public async Task<JObject> makeTransferAsync(Transfer transfer)
```

##### 提币

```C#
/// <param name="withDraw">提币信息</param>
public async Task<JObject> makeWithDrawalAsync(WithDrawal withDraw)
```

##### 提币手续费

```C#
/// <param name="currency">币种</param>
public async Task<JContainer> getWithDrawalFeeAsync(string currency)
```

##### 查询最近所有币种的提币记录

```C#
public async Task<JContainer> getWithDrawalHistoryAsync()
```

##### 查询单个币种提币记录

```C#
/// <param name="currency">币种</param>
public async Task<JContainer> getWithDrawalHistoryByCurrencyAsync(string currency)
```

##### 账单流水查询

```C#
/// <param name="currency">币种，如btc</param>
/// <param name="type">填写相应数字：1:充值2:提现13:撤销提现18:转入合约账户19:合约账户转出20:转入子账户21:子账户转出28:领取29:转入指数交易区30:指数交易区转出 31:转入点对点账户32:点对点账户转出 33:转入币币杠杆账户 34:币币杠杆账户转出 37:转入币币账户 38:币币账户转出</param>
/// <param name="from">请求此页码之后的分页内容</param>
/// <param name="to">请求此页码之前的分页内容</param>
/// <param name="limit">分页返回的结果集数量，默认为100，最大为100，按时间升序排列</param>
public async Task<JContainer> getLedgerAsync(string currency, string type, int? from, int? to, int? limit)
```

##### 获取充值地址

```C#
/// <param name="currency">币种</param>
public async Task<JContainer> getDepositAddressAsync(string currency)
```

##### 获取所有币种充值记录

```C#
public async Task<JContainer> getDepositHistoryAsync()
```

##### 获取单个币种充值记录

```C#
/// <param name="currency">币种</param>
public async Task<JContainer> getDepositHistoryByCurrencyAsync(string currency)
```

#### b)币币API对应SpotApi

##### 获取币币账户信息

```C#
public async Task<JContainer> getSpotAccountsAsync()
```

##### 获取币币账户单个币种的余额、冻结和可用等信息

```C#
/// <param name="currency">币种</param>
public async Task<JObject> getAccountByCurrencyAsync(string currency)
```

##### 账单流水查询

```C#
/// <param name="currency">币种，如btc</param>
/// <param name="from">请求此页码之后的分页内容</param>
/// <param name="to">请求此页码之前的分页内容</param>
/// <param name="limit">分页返回的结果集数量，默认为100，最大为100</param>
public async Task<JContainer> getSpotLedgerByCurrencyAsync(string currency, int? from, int? to, int? limit)
```

##### 下单

```C#
/// <typeparam name="T">订单类型：市价：SpotOrderMarket 限价：SpotOrderLimit</typeparam>
/// <param name="order">订单信息</param>
public async Task<JObject> makeOrderAsync<T>(T order)
```

##### 批量下单

```C#
/// <typeparam name="T">订单类型：市价：SpotOrderMarket 限价：SpotOrderLimit</typeparam>
/// <param name="orders">订单列表</param>
public async Task<JContainer> makeOrderBatchAsync<T>(List<T> orders)
```

##### 撤销指定订单

```C#
/// <param name="orderId">订单ID</param>
/// <param name="instrument_id">提供此参数则撤销指定币对的相应订单</param>
/// <param name="client_oid">由您设置的订单ID来识别您的订单</param>
public async Task<JObject> cancelOrderByOrderIdAsync(string order_id, string instrument_id, string client_oid)
```

##### 批量撤销订单

```C#
/// <param name="orders">订单列表</param>
public async Task<JContainer> cancelOrderBatchAsync(List<CancelOrderBatch> orders)
```

##### 获取订单列表

```C#
/// <param name="instrument_id">[必填]列出指定币对的订单</param>
/// <param name="status">[必填]仅列出相应状态的订单列表。(all:所有状态 open:未成交 part_filled:部分成交 canceling:撤销中 filled:已成交 cancelled:已撤销 failure:下单失败 ordering:下单中)</param>
/// <param name="from">请求此id之后(更新的数据)的分页内容</param>
/// <param name="to">请求此id之前(更旧的数据)的分页内容</param>
/// <param name="limit">分页返回的结果集数量，默认为100，最大为100</param>
public async Task<JContainer> getOrdersAsync(string instrument_id, string status, int? from, int? to, int? limit)
```

##### 获取所有未成交订单

```C#
/// <param name="instrument_id">[必填]列出指定币对的订单</param>
/// <param name="from">请求此id之后(更新的数据)的分页内容</param>
/// <param name="to">请求此id之前(更旧的数据)的分页内容</param>
/// <param name="limit">分页返回的结果集数量，默认为100，最大为100</param>
public async Task<JContainer> getPendingOrdersAsync(string instrument_id, int? from, int? to, int? limit)
```

##### 获取订单信息

```C#
/// <param name="instrument_id">币对</param>
/// <param name="order_id">订单ID</param>
public async Task<JObject> getOrderByIdAsync(string instrument_id, string order_id)
```

##### 获取成交明细

```C#
/// <param name="order_id">[必填]仅限此order_id的资金明细列表</param>
/// <param name="instrument_id">[必填]仅限此instrument_id的资金明细表</param>
/// <param name="from">请求此页码之后的分页内容</param>
/// <param name="to">请求此页码之前的分页内容</param>
/// <param name="limit">分页返回的结果集数量，默认为100，最大为100</param>
public async Task<JContainer> getFillsAsync(long order_id, string instrument_id, int? from, int? to, int? limit)
```

##### 获取币对信息

```C#
public async Task<JContainer> getInstrumentsAsync()
```

##### 获取深度数据

```C#
/// <param name="instrument_id">币对名称</param>
/// <param name="size">[非必填]返回深度档位数量，最多返回200</param>
/// <param name="depth">[非必填]按价格合并深度，默认为tick_size的值(获取币对信息)</param>
public async Task<JContainer> getBookAsync(string instrument_id, string size, string depth)
```

##### 获取全部ticker信息

```C#
public async Task<JContainer> getTickerAsync()
```

##### 获取某个ticker信息

```C#
/// <param name="instrument_id">币对</param>
public async Task<JObject> getTickerByInstrumentIdAsync(string instrument_id)
```

##### 获取成交数据

```C#
/// <param name="instrument_id">[必填]列出指定币对的订单</param>
/// <param name="from">请求此id之后(更新的数据)的分页内容</param>
/// <param name="to">请求此id之前(更旧的数据)的分页内容</param>
/// <param name="limit">分页返回的结果集数量，默认为100，最大为100</param>
public async Task<JContainer> getTradesAasync(string instrument_id, int? from, int? to, int? limit)
```

##### 获取K线数据

```C#
/// <param name="instrument_id">合约ID，如BTC-USD-180213</param>
/// <param name="start">开始时间</param>
/// <param name="end">结束时间</param>
/// <param name="granularity">时间粒度，以秒为单位，必须为60的倍数</param>
public async Task<JContainer> getCandlesAsync(string instrument_id, DateTime? start, DateTime? end, int? granularity)
```

#### c)币币杠杆API对应MarginApi

##### 获取币币杠杆账户资产列表

```C#
public async Task<JContainer> getAccountsAsync()
```

##### 单一币对账户信息

```C#
/// <param name="instrument_id">币对</param>
public async Task<JObject> getAccountsByInstrumentIdAsync(string instrument_id)
```

##### 账单流水查询

```C#
/// <param name="instrument_id">币对</param>
/// <param name="type">	[非必填]1:转入 2:转出 3:借款 4:还款 5:计息 6:穿仓亏损 7:买入 8:卖出 若不传此参数则默认返回所有类型</param>
/// <param name="from">请求此页码之后的分页内容</param>
/// <param name="to">请求此页码之前的分页内容</param>
/// <param name="limit">分页返回的结果集数量，默认为100，最大为100</param>
public async Task<JContainer> getLedgerAsync(string instrument_id, string type, int? from, int? to, int? limit)
```

##### 杠杆配置信息

```C#
public async Task<JContainer> getAvailabilityAsync()
```

##### 某个杠杆配置信息

```C#
/// <param name="instrument_id">币对</param>
public async Task<JContainer> getAvailabilityByInstrumentId(string instrument_id)
```

##### 获取借币记录

```C#
/// <param name="status">状态(0:未还清 1:已还清)</param>
/// <param name="from">请求此页码之后的分页内容</param>
/// <param name="to">请求此页码之前的分页内容</param>
/// <param name="limit">分页返回的结果集数量，默认为100，最大为100</param>
public async Task<JContainer> getBorrowedAsync(string status, int? from, int? to, int? limit)
```

##### 某账户借币记录

```C#
/// <param name="instrument_id">币对</param>
/// <param name="status">状态(0:未还清 1:已还清)</param>
/// <param name="from">请求此页码之后的分页内容</param>
/// <param name="to">请求此页码之前的分页内容</param>
/// <param name="limit">分页返回的结果集数量，默认为100，最大为100</param>
/// <returns></returns>
public async Task<JContainer> getBorrowedByInstrumentIdAsync(string instrument_id, string status, int? from, int? to, int? limit)
```

##### 借币

```C#
/// <param name="instrument_id">杠杆币对名称</param>
/// <param name="currency">币种</param>
/// <param name="amount">借币数量</param>
public async Task<JObject> makeBorrowAsync(string instrument_id, string currency, string amount)
```

##### 还币

```C#
/// <param name="borrow_id">借币记录ID</param>
/// <param name="instrument_id">杠杆币对名称</param>
/// <param name="currency">币种</param>
/// <param name="amount">借币数量</param>
public async Task<JObject> makeRepaymentAsync(long borrow_id, string instrument_id, string currency, string amount)
```

##### 下单

```C#
/// <typeparam name="T">订单类型：市价:MarginOrderMarket 限价:MarginOrderLimit</typeparam>
/// <param name="order">订单信息</param>
public async Task<JObject> makeOrderAsync<T>(T order)
```

##### 批量下单

```C#
/// <typeparam name="T">订单类型：市价:MarginOrderMarket 限价:MarginOrderLimit</typeparam>
/// <param name="orders">订单列表</param>
public async Task<JContainer> makeOrderBatchAsync<T>(List<T> orders)
```

##### 撤销指定订单

```C#
/// <param name="orderId">订单ID</param>
/// <param name="instrument_id">提供此参数则撤销指定币对的相应订单</param>
/// <param name="client_oid">由您设置的订单ID来识别您的订单</param>
public async Task<JObject> cancelOrderByOrderIdAsync(string order_id, string instrument_id, string client_oid)
```

##### 批量撤销订单

```C#
/// <param name="orders">订单列表</param>
public async Task<JContainer> cancelOrderBatchAsync(List<MarginCancelOrderBatch> orders)
```

##### 获取订单列表

```C#
/// <param name="instrument_id">[必填]列出指定币对的订单</param>
/// <param name="status">[必填]仅列出相应状态的订单列表。(all:所有状态 open:未成交 part_filled:部分成交 canceling:撤销中 filled:已成交 cancelled:已撤销 failure:下单失败 ordering:下单中)</param>
/// <param name="from">请求此id之后(更新的数据)的分页内容</param>
/// <param name="to">请求此id之前(更旧的数据)的分页内容</param>
/// <param name="limit">分页返回的结果集数量，默认为100，最大为100</param>
public async Task<JContainer> getOrdersAsync(string instrument_id, string status, int? from, int? to, int? limit)
```

##### 获取订单信息

```C#
/// <param name="instrument_id">币对</param>
/// <param name="order_id">订单ID</param>
public async Task<JObject> getOrderByIdAsync(string instrument_id, string order_id)
```

##### 获取所有未成交订单

```C#
/// <param name="instrument_id">[必填]列出指定币对的订单</param>
/// <param name="from">请求此id之后(更新的数据)的分页内容</param>
/// <param name="to">请求此id之前(更旧的数据)的分页内容</param>
/// <param name="limit">分页返回的结果集数量，默认为100，最大为100</param>
public async Task<JContainer> getPendingOrdersAsync(string instrument_id, int? from, int? to, int? limit)
```

##### 获取成交明细

```C#
/// <param name="order_id">[必填]仅限此order_id的资金明细列表</param>
/// <param name="instrument_id">[必填]仅限此instrument_id的资金明细表</param>
/// <param name="from">请求此页码之后的分页内容</param>
/// <param name="to">请求此页码之前的分页内容</param>
/// <param name="limit">分页返回的结果集数量，默认为100，最大为100</param>
public async Task<JContainer> getFillsAsync(long order_id, string instrument_id, int? from, int? to, int? limit)
```

#### d)合约API对应FuturesApi

##### 合约持仓信息

```C#
public async Task<JObject> getPositionsAsync()
```

##### 单个合约持仓信息

```C#
/// <param name="instrument_id">合约ID</param>
public async Task<JObject> getPositionByIdAsync(string instrument_id)
```

##### 所有币种合约账户信息

```C#
public async Task<JObject> getAccountsAsync()
```

##### 获取单个币种的合约账户信息

```C#
/// <param name="currency">币种，如：btc</param>
public async Task<JObject> getAccountByCurrencyAsync(string currency)
```

##### 获取合约账户币种杠杆倍数

```C#
/// <param name="currency">币种，如：btc</param>
public async Task<JObject> getLeverageAsync(string currency)
```

##### 全仓设定合约币种杠杆倍数

```C#
/// <param name="currency">币种，如：btc</param>
/// <param name="leverage">要设定的杠杆倍数，10或20</param>
public async Task<JObject> setCrossedLeverageAsync(string currency, int leverage)
```

##### 逐仓设定合约币种杠杆倍数

```C#
/// <param name="currency">币种，如：btc</param>
/// <param name="leverage">要设定的杠杆倍数，10或20</param>
/// <param name="instrument_id">合约ID，如BTC-USD-180213</param>
/// <param name="direction">开仓方向，long(做多)或者short(做空)</param>
public async Task<JObject> setFixedLeverageAsync(string currency, int leverage, string instrument_id, string direction)
```

##### 账单流水查询

```C#
/// <param name="currency">币种</param>
/// <param name="from">分页游标开始</param>
/// <param name="to">分页游标截至</param>
/// <param name="limit">分页数据数量，默认100</param>
public async Task<JContainer> getLedgersByCurrencyAsync(string currency, int? from, int? to, int? limit)
```

##### 下单

```C#
/// <param name="instrument_id">合约ID，如BTC-USD-180213</param>
/// <param name="type">1:开多2:开空3:平多4:平空</param>
/// <param name="price">每张合约的价格</param>
/// <param name="size">买入或卖出合约的数量（以张计数）</param>
/// <param name="leverage">要设定的杠杆倍数，10或20</param>
/// <param name="client_oid">由您设置的订单ID来识别您的订单</param>
/// <param name="match_price">是否以对手价下单(0:不是 1:是)，默认为0，当取值为1时。price字段无效
public async Task<JObject> makeOrderAsync(string instrument_id, string type, decimal price, int size, int leverage, string client_oid, string match_price)
```

##### 批量下单

```C#
/// <param name="order">订单信息</param>
public async Task<JObject> makeOrdersBatchAsync(OrderBatch order)
```

##### 撤销指定订单

```C#
/// <param name="instrument_id">合约ID，如BTC-USD-180213</param>
/// <param name="order_id">服务器分配订单ID</param>
public async Task<JObject> cancelOrderAsync(string instrument_id, string order_id)
```

##### 批量撤销订单

```C#
/// <param name="instrument_id">撤销指定合约品种的订单</param>
/// <param name="orderIds">撤销订单ID列表</param>
public async Task<JObject> cancelOrderBatchAsync(string instrument_id, List<long> orderIds)
```

##### 获取订单列表

```C#
/// <param name="instrument_id">合约ID，如BTC-USD-180213</param>
/// <param name="status">订单状态(-1.撤单成功；0:等待成交 1:部分成交 2:全部成交 6：未完成（等待成交+部分成交）7：已完成（撤单成功+全部成交）</param>
/// <param name="from">分页游标开始</param>
/// <param name="to">分页游标截至</param>
/// <param name="limit">分页数据数量，默认100</param>
public async Task<JObject> getOrdersAsync(string instrument_id, string status, int? from, int? to, int? limit)
```

##### 通过订单ID获取单个订单信息

```C#
/// <param name="instrument_id">合约ID，如BTC-USD-180213</param>
/// <param name="order_id">订单ID</param>
public async Task<JObject> getOrderByIdAsync(string instrument_id, string order_id)
```

##### 获取成交明细

```C#
/// <param name="instrument_id">合约ID，如BTC-USD-180213</param>
/// <param name="order_id">订单ID</param>
/// <param name="from">分页游标开始</param>
/// <param name="to">分页游标截至</param>
/// <param name="limit">分页数据数量，默认100</param>
public async Task<JContainer> getFillsAsync(string instrument_id, string order_id, int? from, int? to, int? limit)
```

##### 获取合约信息

```C#
public async Task<JContainer> getInstrumentsAsync()
```

##### 获取深度数据

```C#
/// <param name="instrument_id">合约ID，如BTC-USD-180213</param>
/// <param name="size">返回深度数量，最大值可传200，即买卖深度共400条</param>
public async Task<JObject> getBookAsync(string instrument_id, int? size)
```

##### 获取全部ticker信息

```C#
public async Task<JContainer> getTickersAsync()
```

##### 获取某个ticker信息

```C#
/// <param name="instrument_id">合约ID，如BTC-USD-180213</param>
public async Task<JObject> getTickerByInstrumentId(string instrument_id)
```

##### 获取成交数据

```C#
/// <param name="instrument_id">合约ID，如BTC-USD-180213</param>
/// <param name="from">分页游标开始</param>
/// <param name="to">分页游标截至</param>
/// <param name="limit">分页数据数量，默认100</param>
public async Task<JContainer> getTradesAsync(string instrument_id, int? from, int? to, int? limit)
```

##### 获取K线数据

```C#
/// <param name="instrument_id">合约ID，如BTC-USD-180213</param>
/// <param name="start">开始时间</param>
/// <param name="end">结束时间</param>
/// <param name="granularity">时间粒度，以秒为单位，必须为60的倍数</param>
public async Task<JContainer> getCandlesDataAsync(string instrument_id, DateTime? start, DateTime? end, int? granularity)
```

##### 获取指数信息

```C#
/// <param name="instrument_id">合约ID，如BTC-USD-180213</param>
public async Task<JObject> getIndexAsync(string instrument_id)
```

##### 获取法币汇率

```C#
public async Task<JObject> getRateAsync()
```

##### 获取预估交割价

```C#
/// <param name="instrument_id">合约ID，如BTC-USD-180213</param>
public async Task<JObject> getEstimatedPriceAsync(string instrument_id)
```

##### 获取平台总持仓量

```C#
/// <param name="instrument_id">合约ID，如BTC-USD-180213</param>
public async Task<JObject> getOpenInterestAsync(string instrument_id)
```

##### 获取当前限价

```C#
/// <param name="instrument_id">合约ID，如BTC-USD-180213</param>
public async Task<JObject> getPriceLimitAsync(string instrument_id)
```

##### 获取爆仓单

```C#
/// <param name="instrument_id">合约ID，如BTC-USD-180213</param>
/// <param name="status">状态(0:最近7天数据（包括第7天） 1:7天前数据)</param>
/// <param name="from">分页游标开始</param>
/// <param name="to">分页游标截至</param>
/// <param name="limit">分页数据数量，默认100</param>
public async Task<JContainer> getLiquidationAsync(string instrument_id, string status, int? from, int? to, int? limit)
```

##### 获取合约挂单冻结数量

```C#
/// <param name="instrument_id">合约ID，如BTC-USD-180213</param>
public async Task<JObject> getHoldsAsync(string instrument_id)
```

#### e) ETT API对应EttApi

##### 组合账户信息

```C#
public async Task<JContainer> getAccountsAsync()
```

##### 单一币种账户信息

```C#
/// <param name="currency">币种或ett名称</param>
public async Task<JObject> getAccountByCurrencyAsync(string currency)
```

##### 账单流水查询

```C#
/// <param name="currency">币种或ett名称</param>
/// <param name="from">请求此页码之后的分页内容</param>
/// <param name="to">请求此页码之前的分页内容</param>
/// <param name="limit">分页返回的结果集数量，默认为100，最大为100</param>
public async Task<JContainer> getLedgerAsync(string currency, int? from, int? to, int? limit)
```

##### 下单

```C#
/// <param name="order">订单信息</param>
public async Task<JObject> makeOrderAsync(EttOrder order)
```

##### 撤销指定订单

```C#
/// <param name="order_id">服务器分配的订单ID</param>
public async Task<JObject> cancelOrderAsync(string order_id)
```

##### 获取订单列表

```C#
/// <param name="ett">[必填]列出指定ett的订单</param>
/// <param name="type">[必填]（1:申购 2:赎回）</param>
/// <param name="status">仅列出相应状态的订单列表。(0:所有状态 1:等待成交 2:已成交 3:已撤销)
/// <param name="from">请求此id之后(更新的数据)的分页内容</param>
/// <param name="to">请求此id之前(更旧的数据)的分页内容</param>
/// <param name="limit">分页返回的结果集数量，默认为100，最大为100</param>
public async Task<JContainer> getOrdersAsync(string ett, string type, string status, int? from, int? to, int? limit)
```

##### 获取订单信息

```C#
/// <param name="order_id">订单ID</param>
public async Task<JObject> getOrderByOrderIdAsync(string order_id)
```

##### 获取组合成分

```C#
/// <param name="ett"></param>
public async Task<JObject> getConstituentsAsync(string ett)
```

##### 获取ETT清算历史定价

```C#
/// <param name="ett">基金名称</param>
public async Task<JContainer> getDefinePriceAsync(string ett)
```

#### f)  永续合约API

##### 单个合约持仓信息

``` C#
/// <param name="instrument_id">合约名称，如BTC-USD-SWAP</param>
public async Task<JObject> getPositionByInstrumentAsync(string instrument_id)
```

##### 所有币种合约账户信息
``` C#
public async Task<JObject> getAccountsAsync()
```

##### 单个币种合约账户信息
``` C#
/// <param name="instrument_id">合约名称，如BTC-USD-SWAP</param>
public async Task<JObject> getAccountsByInstrumentAsync(string instrument_id)
```

##### 获取某个合约的用户配置

```C#
/// <param name="instrument_id">合约名称，如BTC-USD-SWAP</param>
public async Task<JObject> getSettingsByInstrumentAsync(string instrument_id)
```

##### 设定某个合约的杠杆

```C#
/// <param name="instrument_id">合约名称，如BTC-USD-SWAP</param>
/// <param name="leverage">新杠杆倍数，可填写1-40之间的整数</param>
/// <param name="side">方向:1.LONG 2.SHORT 3.CROSS</param>
public async Task<JObject> setLeverageByInstrumentAsync(string instrument_id, int leverage, string side)
```

##### 账单流水查询

```C#
/// <param name="instrument_id">合约名称，如BTC-USD-SWAP</param>
/// <param name="from">分页游标开始</param>
/// <param name="to">分页游标截至</param>
/// <param name="limit">分页数据数量，默认100</param>
public async Task<JContainer> getLedgersByInstrumentAsync(string instrument_id, int? from, int? to, int? limit)
```

##### 下单

```C#
/// <param name="instrument_id">合约名称，如BTC-USD-SWAP</param>
/// <param name="type">1:开多2:开空3:平多4:平空</param>
/// <param name="price">委托价格</param>
/// <param name="size">下单数量</param>
/// <param name="client_oid">由您设置的订单ID来识别您的订单</param>
/// <param name="match_price">是否以对手价下单(0:不是 1:是)</param>
public async Task<JObject> makeOrderAsync(string instrument_id, string type, decimal price, int size, string client_oid, string match_price)
```

##### 批量下单

```C#
/// <param name="order">订单信息</param>
public async Task<JObject> makeOrdersBatchAsync(OrderBatch order)
```

##### 撤单

```C#
/// <param name="instrument_id">合约名称，如BTC-USD-SWAP</param>
/// <param name="order_id">订单id</param>
public async Task<JObject> cancelOrderAsync(string instrument_id, string order_id)
```

##### 批量撤单

```C#
/// <param name="instrument_id">合约名称，如BTC-USD-SWAP</param>
/// <param name="orderIds">订单id列表</param>
public async Task<JObject> cancelOrderBatchAsync(string instrument_id, List<string> orderIds)
```

##### 获取所有订单列表

```C#
/// <param name="instrument_id">合约名称，如BTC-USD-SWAP</param>
/// <param name="status">订单状态(-2:失败 -1:撤单成功 0:等待成交 1:部分成交 2:完全成交)
/// <param name="from">分页游标开始</param>
/// <param name="to">分页游标截至</param>
/// <param name="limit">分页数据数量，默认100</param>
public async Task<JObject> getOrdersAsync(string instrument_id, string status, int? from, int? to, int? limit)
```

##### 通过订单ID获取单个订单信息

```C#
/// <param name="instrument_id">合约名称，如BTC-USD-SWAP</param>
/// <param name="order_id">订单ID</param>
public async Task<JObject> getOrderByIdAsync(string instrument_id, string order_id)
```

##### 获取成交明细

```C#
/// <param name="instrument_id">合约名称，如BTC-USD-SWAP</param>
/// <param name="order_id">订单ID</param>
/// <param name="from">分页游标开始</param>
/// <param name="to">分页游标截至</param>
/// <param name="limit">分页数据数量，默认100</param>
public async Task<JContainer> getFillsAsync(string instrument_id, string order_id, int? from, int? to, int? limit)
```

##### 获取合约信息

```C#
public async Task<JContainer> getInstrumentsAsync()
```

##### 获取深度数据

```C#
/// <param name="instrument_id">合约名称，如BTC-USD-SWAP</param>
/// <param name="size">返回深度数量，最大值可传200，即买卖深度共400条</param>
public async Task<JObject> getBookAsync(string instrument_id, int? size)
```

##### 获取全部ticker信息

```C#
public async Task<JContainer> getTickersAsync()
```

##### 获取某个ticker信息

```C#
/// <param name="instrument_id">合约名称，如BTC-USD-SWAP</param>
public async Task<JObject> getTickerByInstrumentId(string instrument_id)
```

##### 获取成交数据

```C#
/// <param name="instrument_id">合约ID，如BTC-USD-180213</param>
/// <param name="from">分页游标开始</param>
/// <param name="to">分页游标截至</param>
/// <param name="limit">分页数据数量，默认100</param>
public async Task<JContainer> getTradesAsync(string instrument_id, int? from, int? to, int? limit)
```

##### 获取K线数据

```C#
/// <param name="instrument_id">合约名称，如BTC-USD-SWAP</param>
/// <param name="start">开始时间</param>
/// <param name="end">结束时间</param>
/// <param name="granularity">时间粒度，以秒为单位，必须为60的倍数</param>
public async Task<JContainer> getCandlesDataAsync(string instrument_id, DateTime? start, DateTime? end, int? granularity)
```

##### 获取指数信息

```C#
/// <param name="instrument_id">合约名称，如BTC-USD-SWAP</param>
public async Task<JObject> getIndexAsync(string instrument_id)
```

##### 获取法币汇率

```C#
public async Task<JObject> getRateAsync()
```

##### 获取平台总持仓量

```C#
/// <param name="instrument_id">合约名称，如BTC-USD-SWAP</param>
public async Task<JObject> getOpenInterestAsync(string instrument_id)
```

##### 获取当前限价

```C#
/// <param name="instrument_id">合约名称，如BTC-USD-SWAP</param>
public async Task<JObject> getPriceLimitAsync(string instrument_id)
```

##### 获取爆仓单

```C#
/// <param name="instrument_id">合约名称，如BTC-USD-SWAP</param>
/// <param name="status">状态(0:最近7天的未成交 1:最近7天的已成交)</param>
/// <param name="from">分页游标开始</param>
/// <param name="to">分页游标截至</param>
/// <param name="limit">分页数据数量，默认100</param>
public async Task<JContainer> getLiquidationAsync(string instrument_id, string status, int? from, int? to, int? limit)
```

##### 获取合约挂单冻结数量

```C#
/// <param name="instrument_id">合约名称，如BTC-USD-SWAP</param>
public async Task<JObject> getHoldsAsync(string instrument_id)
```

##### 获取合约下一次结算时间

```C#
/// <param name="instrument_id">合约名称，如BTC-USD-SWAP</param>
public async Task<JObject> getFundingTimeAsync(string instrument_id)
```

##### 获取合约标记价格

```C#
/// <param name="instrument_id">合约名称，如BTC-USD-SWAP</param>
public async Task<JObject> getMarkPriceAsync(string instrument_id)
```

##### 获取合约历史资金费率

```C#
/// <param name="instrument_id">合约名称，如BTC-USD-SWAP</param>
/// <param name="from">分页游标开始</param>
/// <param name="to">分页游标截至</param>
/// <param name="limit">分页数据数量，默认100</param>
public async Task<JContainer> getHistoricalFundingRateAsync(string instrument_id, int? from, int? to, int? limit)
```

