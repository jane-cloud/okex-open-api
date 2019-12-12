package okex

import (
	"net/http"
	"strings"
)

/*
 OKEX futures contract api
 @author Tony Tian
 @date 2018-03-17
 @version 1.0.0
*/

/*
 =============================== Futures market api ===============================
*/
/*
 The exchange rate of legal tender pairs
*/
func (client *Client) GetFuturesExchangeRate() (ExchangeRate, error) {
	var exchangeRate ExchangeRate
	_, err := client.Request(GET, FUTURES_RATE, nil, &exchangeRate)
	return exchangeRate, err
}

/*
  Get all of futures contract list
*/
func (client *Client) GetFuturesInstruments() ([]FuturesInstrumentsResult, error) {
	var Instruments []FuturesInstrumentsResult
	_, err := client.Request(GET, FUTURES_INSTRUMENTS, nil, &Instruments)
	return Instruments, err
}

/*
 Get the futures contract currencies
*/
func (client *Client) GetFuturesInstrumentCurrencies() ([]FuturesInstrumentCurrenciesResult, error) {
	var currencies []FuturesInstrumentCurrenciesResult
	_, err := client.Request(GET, FUTURES_CURRENCIES, nil, &currencies)
	return currencies, err
}

/*
	获取深度数据
	获取币对的深度列表。这个请求不支持分页，一个请求返回整个深度列表。

	限速规则：20次/2s
	HTTP请求
	GET /api/spot/v3/instruments/<instrument_id>/book

	签名请求示例
	2018-09-12T07:57:09.130ZGET/api/spot/v3/instruments/LTC-USDT/book?size=10&depth=0.001

*/
func (client *Client) GetFuturesInstrumentBook(InstrumentId string, optionalParams map[string]string) (FuturesInstrumentBookResult, error) {
	var book FuturesInstrumentBookResult
	params := NewParams()
	if optionalParams != nil && len(optionalParams) > 0 {
		params["size"] = optionalParams["size"]
		params["depth"] = optionalParams["depth"]
	}
	requestPath := BuildParams(GetInstrumentIdUri(FUTURES_INSTRUMENT_BOOK, InstrumentId), params)
	_, err := client.Request(GET, requestPath, nil, &book)
	return book, err
}

/*
 Get the futures contract Instrument all ticker
*/
func (client *Client) GetFuturesInstrumentAllTicker() ([]FuturesInstrumentTickerResult, error) {
	var tickers []FuturesInstrumentTickerResult
	_, err := client.Request(GET, FUTURES_TICKERS, nil, &tickers)
	return tickers, err
}

/*
 Get the futures contract Instrument ticker
*/
func (client *Client) GetFuturesInstrumentTicker(InstrumentId string) (FuturesInstrumentTickerResult, error) {
	var ticker FuturesInstrumentTickerResult
	_, err := client.Request(GET, GetInstrumentIdUri(FUTURES_INSTRUMENT_TICKER, InstrumentId), nil, &ticker)
	return ticker, err
}

/*
 Get the futures contract Instrument candles
 granularity: @see  file: futures_constants.go
*/
func (client *Client) GetFuturesInstrumentCandles(InstrumentId string, optionalParams map[string]string) ([][]string, error) {
	var candles [][]string
	params := NewParams()

	if optionalParams != nil && len(optionalParams) > 0 {
		params["start"] = optionalParams["start"]
		params["end"] = optionalParams["end"]
		params["granularity"] = optionalParams["granularity"]
	}
	requestPath := BuildParams(GetInstrumentIdUri(FUTURES_INSTRUMENT_CANDLES, InstrumentId), params)
	_, err := client.Request(GET, requestPath, nil, &candles)
	return candles, err
}

/*
 Get the futures contract Instrument index
*/
func (client *Client) GetFuturesInstrumentIndex(InstrumentId string) (FuturesInstrumentIndexResult, error) {
	var index FuturesInstrumentIndexResult
	_, err := client.Request(GET, GetInstrumentIdUri(FUTURES_INSTRUMENT_INDEX, InstrumentId), nil, &index)
	return index, err
}

/*
 Get the futures contract Instrument estimated price
*/
func (client *Client) GetFuturesInstrumentEstimatedPrice(InstrumentId string) (FuturesInstrumentEstimatedPriceResult, error) {
	var estimatedPrice FuturesInstrumentEstimatedPriceResult
	_, err := client.Request(GET, GetInstrumentIdUri(FUTURES_INSTRUMENT_ESTIMATED_PRICE, InstrumentId), nil, &estimatedPrice)
	return estimatedPrice, err
}

/*
 Get the futures contract Instrument holds
*/
func (client *Client) GetFuturesInstrumentOpenInterest(InstrumentId string) (FuturesInstrumentOpenInterestResult, error) {
	var openInterest FuturesInstrumentOpenInterestResult
	_, err := client.Request(GET, GetInstrumentIdUri(FUTURES_INSTRUMENT_OPEN_INTEREST, InstrumentId), nil, &openInterest)
	return openInterest, err
}

/*
 Get the futures contract Instrument limit price
*/
func (client *Client) GetFuturesInstrumentPriceLimit(InstrumentId string) (FuturesInstrumentPriceLimitResult, error) {
	var priceLimit FuturesInstrumentPriceLimitResult
	_, err := client.Request(GET, GetInstrumentIdUri(FUTURES_INSTRUMENT_PRICE_LIMIT, InstrumentId), nil, &priceLimit)
	return priceLimit, err
}

/*
 Get the futures contract liquidation
*/
func (client *Client) GetFuturesInstrumentLiquidation(InstrumentId string, status, from, to, limit int) (FuturesInstrumentLiquidationListResult, error) {
	var liquidation []FuturesInstrumentLiquidationResult
	params := NewParams()
	params["status"] = Int2String(status)
	params["from"] = Int2String(from)
	params["to"] = Int2String(to)
	params["limit"] = Int2String(limit)
	requestPath := BuildParams(GetInstrumentIdUri(FUTURES_INSTRUMENT_LIQUIDATION, InstrumentId), params)
	response, err := client.Request(GET, requestPath, nil, &liquidation)
	var list FuturesInstrumentLiquidationListResult
	page := parsePage(response)
	list.Page = page
	list.LiquidationList = liquidation
	return list, err
}

/*
 =============================== Futures trade api ===============================
*/

/*
单个合约持仓信息
获取某个合约的持仓信息。

限速规则：20次/2s
HTTP请求
GET /api/futures/v3/<instrument_id>/position

请求示例
GET/api/futures/v3/ BTC-USD-180309 /position
*/
func (client *Client) GetFuturesInstrumentPosition(InstrumentId string) (*map[string]interface{}, error) {
	r := map[string]interface{}{}
	_, err := client.Request(GET, GetInstrumentIdUri(FUTURES_INSTRUMENT_POSITION, InstrumentId), nil, &r)
	if err != nil {
		return nil, err
	} else {
		return &r, nil
	}
}

/*
 Get the futures contract currency account @see file : futures_constants.go
 return struct: FuturesCurrencyAccounts
*/
func (client *Client) GetFuturesAccountsByCurrency(currency string) (FuturesCurrencyAccount, error) {
	response, err := client.Request(GET, GetCurrencyUri(FUTURES_ACCOUNT_CURRENCY_INFO, currency), nil, nil)
	return parseCurrencyAccounts(response, err)
}

/*
 Get the futures contract Instrument holds
*/
func (client *Client) GetFuturesAccountsHoldsByInstrumentId(InstrumentId string) (FuturesAccountsHolds, error) {
	var holds FuturesAccountsHolds
	_, err := client.Request(GET, GetInstrumentIdUri(FUTURES_ACCOUNT_INSTRUMENT_HOLDS, InstrumentId), nil, &holds)
	return holds, err
}

/*

下单
OKEx合约交易提供了限价单下单模式。只有当您的账户有足够的资金才能下单。一旦下单，您的账户资金将在订单生命周期内被冻结。被冻结的资金以及数量取决于订单指定的类型和参数。

限速规则：40次/2s
HTTP请求
POST /api/futures/v3/order

请求示例
POST/api/futures/v3/order
{"client_oid": “12233456”,"order_type”:”1”,"instrument_id":"BTC-USD-180213","type":"1","price":"432.11","size":"2","match_price":"0","leverage":"10"}

*/
func (client *Client) PostFuturesOrder(instrumentId, oType, price, size string, optionalParams map[string]string) (*map[string]interface{}, error) {
	r := map[string]interface{}{}

	params := NewParams()
	params["instrument_id"] = instrumentId
	params["type"] = oType
	params["price"] = price
	params["size"] = size

	if optionalParams != nil && len(optionalParams) > 0 {
		for k, v := range optionalParams {
			params[k] = v
		}
	}

	_, err := client.Request(POST, FUTURES_ORDER, params, &r)
	return &r, err
}

/*
获取订单信息
通过订单ID获取单个订单信息。已撤销的未成交单只保留2个小时。

限速规则：40次/2s
HTTP请求
GET /api/futures/v3/orders/<instrument_id>/<order_id>
or
GET /api/futures/v3/orders/<instrument_id>/<client_oid>
请求示例
GET/api/futures/v3/orders/BTC-USD-180213/888845120785408
or
GET/api/futures/v3/orders/BTC-USD-180213/888845120785408ee
*/
func (client *Client) GetFuturesOrder(InstrumentId string, orderid_or_clientoId string) (map[string]string, error) {
	var getOrderResult map[string]string
	_, err := client.Request(GET, GetInstrumentIdOrdersUri(FUTURES_INSTRUMENT_ORDER_INFO, InstrumentId, orderid_or_clientoId), nil, &getOrderResult)
	return getOrderResult, err
}

/*
 Batch Cancel the orders
*/
func (client *Client) BatchCancelFuturesInstrumentOrders(InstrumentId, orderIds string) (FuturesBatchCancelInstrumentOrdersResult, error) {
	var cancelInstrumentOrdersResult FuturesBatchCancelInstrumentOrdersResult
	params := NewParams()
	params["order_ids"] = orderIds
	_, err := client.Request(POST, GetInstrumentIdUri(FUTURES_INSTRUMENT_ORDER_BATCH_CANCEL, InstrumentId), params, &cancelInstrumentOrdersResult)
	return cancelInstrumentOrdersResult, err
}

/*
 撤销指定订单
撤销之前下的未完成订单。
限速规则：40次/2s
HTTP请求
POST /api/futures/v3/cancel_order/<instrument_id>/<order_id>
or
POST /api/futures/v3/cancel_order/<instrument_id>/<client_oid>

请求示例
POST /api/futures/v3/cancel_order/BTC-USD-180309/1407616797780992
or
POST /api/futures/v3/cancel_order/BTC-USD-180309/1407616797780992ee
*/
func (client *Client) CancelFuturesInstrumentOrder(InstrumentId string, orderid_or_clientoId string) (map[string]interface{}, error) {
	var cancelInstrumentOrderResult map[string]interface{}
	_, err := client.Request(POST, GetInstrumentIdOrdersUri(FUTURES_INSTRUMENT_ORDER_CANCEL, InstrumentId, orderid_or_clientoId), nil,
		&cancelInstrumentOrderResult)
	return cancelInstrumentOrderResult, err
}

/*
获取标记价格
获取合约标记价格。此接口为公共接口，不需要身份验证。

请求示例
GET/api/futures/v3/instruments/BTC-USD-180309/mark_price
*/
func (c *Client) GetInstrumentMarkPrice(instrumentId string) (*FuturesMarkdown, error) {
	uri := GetInstrumentIdUri(FUTURES_INSTRUMENT_MARK_PRICE, instrumentId)
	r := FuturesMarkdown{}
	_, err := c.Request(GET, uri, nil, &r)
	return &r, err
}

//func parsePositions(response *http.Response, err error) (FuturesPosition, error) {
//	var position FuturesPosition
//	if err != nil {
//		return position, err
//	}
//	var result Result
//	result.Result = false
//	jsonString := GetResponseDataJsonString(response)
//	if strings.Contains(jsonString, "\"margin_mode\":\"fixed\"") {
//		var fixedPosition FuturesFixedPosition
//		err = JsonString2Struct(jsonString, &fixedPosition)
//		if err != nil {
//			return position, err
//		} else {
//			position.Result = fixedPosition.Result
//			position.MarginMode = fixedPosition.MarginMode
//			position.FixedPosition = fixedPosition.FixedPosition
//		}
//	} else if strings.Contains(jsonString, "\"margin_mode\":\"crossed\"") {
//		var crossPosition FuturesCrossPosition
//		err = JsonString2Struct(jsonString, &crossPosition)
//		if err != nil {
//			return position, err
//		} else {
//			position.Result = crossPosition.Result
//			position.MarginMode = crossPosition.MarginMode
//			position.CrossPosition = crossPosition.CrossPosition
//		}
//	} else if strings.Contains(jsonString, "\"code\":") {
//		JsonString2Struct(jsonString, &position)
//		position.Result = result
//	} else {
//		position.Result = result
//	}
//
//	return position, nil
//}
//
//func parseAccounts(response *http.Response, err error) (FuturesAccount, error) {
//	var account FuturesAccount
//	if err != nil {
//		return account, err
//	}
//	var result Result
//	result.Result = false
//	jsonString := GetResponseDataJsonString(response)
//	if strings.Contains(jsonString, "\"contracts\"") {
//		var fixedAccount FuturesFixedAccountInfo
//		err = JsonString2Struct(jsonString, &fixedAccount)
//		if err != nil {
//			return account, err
//		} else {
//			account.Result = fixedAccount.Result
//			account.FixedAccount = fixedAccount.Info
//			account.MarginMode = "fixed"
//		}
//	} else if strings.Contains(jsonString, "\"realized_pnl\"") {
//		var crossAccount FuturesCrossAccountInfo
//		err = JsonString2Struct(jsonString, &crossAccount)
//		if err != nil {
//			return account, err
//		} else {
//			account.Result = crossAccount.Result
//			account.MarginMode = "crossed"
//			account.CrossAccount = crossAccount.Info
//		}
//	} else if strings.Contains(jsonString, "\"code\":") {
//		JsonString2Struct(jsonString, &account)
//		account.Result = result
//	} else {
//		account.Result = result
//	}
//	return account, nil
//}

func parseCurrencyAccounts(response *http.Response, err error) (FuturesCurrencyAccount, error) {
	var currencyAccount FuturesCurrencyAccount
	if err != nil {
		return currencyAccount, err
	}
	jsonString := GetResponseDataJsonString(response)
	var result Result
	result.Result = true
	if strings.Contains(jsonString, "\"margin_mode\":\"fixed\"") {
		var fixedAccount FuturesFixedAccount
		err = JsonString2Struct(jsonString, &fixedAccount)
		if err != nil {
			return currencyAccount, err
		} else {
			currencyAccount.Result = result
			currencyAccount.MarginMode = fixedAccount.MarginMode
			currencyAccount.FixedAccount = fixedAccount
		}
	} else if strings.Contains(jsonString, "\"margin_mode\":\"crossed\"") {
		var crossAccount FuturesCrossAccount
		err = JsonString2Struct(jsonString, &crossAccount)
		if err != nil {
			return currencyAccount, err
		} else {
			currencyAccount.Result = result
			currencyAccount.MarginMode = crossAccount.MarginMode
			currencyAccount.CrossAccount = crossAccount
		}
	} else if strings.Contains(jsonString, "\"code\":") {
		result.Result = true
		JsonString2Struct(jsonString, &currencyAccount)
		currencyAccount.Result = result
	} else {
		result.Result = true
		currencyAccount.Result = result
	}
	return currencyAccount, nil
}

func parsePage(response *http.Response) PageResult {
	var page PageResult
	jsonString := GetResponsePageJsonString(response)
	JsonString2Struct(jsonString, &page)
	return page
}

/*
设定合约币种杠杆倍数
设定合约账户币种杠杆倍数，注意当前仓位有持仓或者挂单禁止切换杠杆。

HTTP请求
POST /api/futures/v3/accounts/<currency>/leverage

请求示例
POST/api/futures/v3/accounts/btc/leverage{"leverage":"10"}（全仓示例）
POST/api/futures/v3/accounts/btc/leverage{"instrument_id":"BTC-USD-180213","direction":"long","leverage":"10"}（逐仓示例）

*/
func (c *Client) PostFuturesAccountsLeverage(currency string, leverage string, optionalParams map[string]string) (map[string]interface{}, error) {
	uri := GetCurrencyUri(FUTURES_ACCOUNT_CURRENCY_LEVERAGE, currency)
	params := NewParams()
	params["leverage"] = leverage

	if optionalParams != nil && len(optionalParams) > 0 {
		params["instrument_id"] = optionalParams["instrument_id"]
		params["direction"] = optionalParams["direction"]
	}

	r := new(map[string]interface{})
	_, err := c.Request(POST, uri, params, r)

	return *r, err
}

/*
获取合约账户币种杠杆倍数

限速规则：5次/2s
HTTP请求
GET /api/futures/v3/accounts/<currency>/leverage

请求示例
GET/api/futures/v3/accounts/btc/leverage
*/
func (c *Client) GetFuturesAccountsLeverage(currency string) (map[string]interface{}, error) {
	uri := GetCurrencyUri(FUTURES_ACCOUNT_CURRENCY_LEVERAGE, currency)
	r := new(map[string]interface{})
	_, err := c.Request(GET, uri, nil, r)
	return *r, err
}

/*
设置合约币种强平模式
设置合约币种强平模式，注意当前仓位有挂单禁止切换账户模式。
限速规则：5次/2s

HTTP请求
POST /api/futures/v3/accounts/liqui_mode

请求示例
POST /api/futures/v3/accounts/liqui_mode {"currency":"btc","liqui_mode":"tier"}
*/
func (client *Client) PostFutureAccountsLiquiMode(
	currency string, liqui_mode string) (*map[string]interface{}, error) {

	r := map[string]interface{}{}

	transferInfo := map[string]interface{}{}
	transferInfo["liqui_mode"] = liqui_mode
	transferInfo["currency"] = currency

	if _, err := client.Request(POST, FUTURES_ACCOUNTS_LIQUI_MODE, transferInfo, &r); err != nil {
		return nil, err
	}

	return &r, nil
}

/*
交割合约增加：设置合约币种账户模式接口
影响的业务线：交割合约
影响的具体接口：POST /api/futures/v3/accounts/margin_mode
设置合约币种账户模式，注意当前仓位有持仓或者挂单禁止切换账户模式。

限速规则：5次/2s

请求示例
POST /api/futures/v3/accounts/margin_mode{"currency":"btc","margin_mode":"crossed"}

*/
func (client *Client) PostFutureAccountsMarginMode(
	currency string, margin_mode string) (*map[string]interface{}, error) {

	r := map[string]interface{}{}

	transferInfo := map[string]interface{}{}
	transferInfo["margin_mode"] = margin_mode
	transferInfo["currency"] = currency

	if _, err := client.Request(POST, FUTURES_ACCOUNTS_MARGIN_MODE, transferInfo, &r); err != nil {
		return nil, err
	}

	return &r, nil
}

/*
所有币种合约账户信息
获取合约账户所有币种的账户信息。请求此接口，会在数据库遍历所有币对下的账户数据，有大量的性能消耗,请求频率较低。建议用户传币种获取账户信息信息。

限速规则：1次/10s
HTTP请求
GET /api/futures/v3/accounts

请求示例
GET/api/futures/v3/accounts
*/
func (client *Client) GetFuturesAccounts() (*map[string]interface{}, error) {

	r := map[string]interface{}{}
	if _, err := client.Request(GET, FUTURES_ACCOUNTS, nil, &r); err != nil {
		return nil, err
	}

	return &r, nil
}

/*
获取成交明细
获取最近的成交明细列表，本接口能查询最近7天的数据。

限速规则：20 次/2s
HTTP请求
GET /api/futures/v3/fills

请求示例
GET/api/futures/v3/fills?order_id=123123&instrument_id=BTC-USD-180309&after=2517062044057601&limit=50
*/
func (client *Client) GetFuturesFills(InstrumentId string, orderId string, optionalParams map[string]string) ([]FuturesFillResult, error) {
	var fillsResult []FuturesFillResult
	params := NewParams()
	params["order_id"] = orderId
	params["instrument_id"] = InstrumentId

	if optionalParams != nil && len(optionalParams) > 0 {
		params["after"] = optionalParams["after"]
		params["before"] = optionalParams["before"]
		params["limit"] = optionalParams["limit"]
	}

	requestPath := BuildParams(FUTURES_FILLS, params)
	_, err := client.Request(GET, requestPath, nil, &fillsResult)
	return fillsResult, err
}

/*
批量下单
批量进行合约下单操作。每个币对可批量下10个单。

限速规则：20次/2s
HTTP请求
POST /api/futures/v3/orders

请求示例
POST/api/futures/v3/orders
{
	"instrument_id":"ETH-USD-181228",
	"leverage":20,
	"orders_data":[
			{"order_type”:”1”,"client_oid":"f379a96206fa4b778e1554c6dc969687","type":"1","price":"180.0","size":"1","match_price":"0"}
	]
}
*/
func (client *Client) PostFuturesOrders(instrumentId string, orderData []map[string]string, leverage string, optionalParams map[string]string) (*map[string]interface{}, error) {
	var batchNewOrderResult map[string]interface{}
	params := map[string]interface{}{}
	params["orders_data"] = orderData
	params["instrument_id"] = instrumentId
	params["leverage"] = leverage

	if optionalParams != nil && len(optionalParams) > 0 {
		for k, v := range optionalParams {
			if v != "" && len(v) > 0 {
				params[k] = v
			}
		}
	}

	_, err := client.Request(POST, FUTURES_ORDERS, params, &batchNewOrderResult)
	return &batchNewOrderResult, err
}

/*
合约持仓信息
获取合约账户所有币种的持仓信息。请求此接口，会在数据库遍历所有币对下的持仓数据，有大量的性能消耗,请求频率较低。建议用户传币种获取持仓信息。

限速规则：5次/2s
HTTP请求
GET /api/futures/v3/position

请求示例
GET/api/futures/v3/position
*/
func (client *Client) GetFuturesPositions() (*map[string]interface{}, error) {

	result := map[string]interface{}{}

	_, err := client.Request(GET, FUTURES_POSITION, nil, &result)
	if err != nil {
		return nil, err
	} else {
		return &result, err
	}
}

/*
账单流水查询
列出帐户资产流水。帐户资产流水是指导致帐户余额增加或减少的行为。本接口能查询最近2天的数据。

限速规则：5次/2s
HTTP请求
GET /api/futures/v3/accounts/<currency>/ledger

请求示例
GET/api/futures/v3/accounts/eos/ledger?after=2510946217009854&limit=3
*/
func (client *Client) GetFuturesAccountsLedgerByCurrency(currency string, optionalParams map[string]string) ([]map[string]interface{}, error) {
	var ledger []map[string]interface{}

	var params map[string]string = nil
	if optionalParams != nil && len(optionalParams) > 0 {
		params = NewParams()
		for k, v := range optionalParams {
			if len(v) > 0 {
				params[k] = v
			}
		}
	}

	requestPath := BuildParams(GetCurrencyUri(FUTURES_ACCOUNT_CURRENCY_LEDGER, currency), params)
	_, err := client.Request(GET, requestPath, nil, &ledger)
	return ledger, err
}

/*
公共-获取成交数据
获取合约最新的300条成交列表。

限速规则：20次/2s
HTTP请求
GET /api/futures/v3/instruments/<instrument_id>/trades

请求示例
GET/api/futures/v3/instruments/BTC-USD-180309/trades?after=2517062044057601&limit=2
*/
func (client *Client) GetFuturesInstrumentTrades(InstrumentId string, optionalParams map[string]string) ([]interface{}, error) {
	var trades []interface{}

	params := NewParams()

	if optionalParams != nil && len(optionalParams) > 0 {
		for k, v := range optionalParams {
			if len(v) > 0 {
				params[k] = v
			}
		}
	}

	uri := BuildParams(GetInstrumentIdUri(FUTURES_INSTRUMENT_TRADES, InstrumentId), params)
	_, err := client.Request(GET, uri, nil, &trades)
	if err != nil {
		return nil, err
	} else {
		return trades, err
	}
}

/*
 获取订单列表
列出您当前所有的订单信息（after的优先级高于before，当同时传after和before参数时，系统返回after参数的请求值）。本接口能查询最近7天的数据。

限速规则：20次/2s
HTTP请求
GET /api/futures/v3/orders/<instrument_id>

请求示例
GET/api/futures/v3/orders/BTC-USD-190628?state=2&after=2517062044057601&limit=2
*/
func (client *Client) GetFuturesOrders(InstrumentId, state string, optionalParams map[string]string) (map[string]interface{}, error) {
	var ordersResult map[string]interface{}
	params := NewParams()
	params["state"] = state

	if optionalParams != nil && len(optionalParams) > 0 {
		for k, v := range optionalParams {
			if len(v) > 0 {
				params[k] = v
			}
		}
	}

	requestPath := BuildParams(GetInstrumentIdUri(FUTURES_INSTRUMENT_ORDER_LIST, InstrumentId), params)
	_, err := client.Request(GET, requestPath, nil, &ordersResult)
	return ordersResult, err
}
