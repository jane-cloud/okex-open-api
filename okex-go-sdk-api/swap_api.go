package okex

/*
 OKEX Swap Api
 @author Lingting Fu
 @date 2018-12-27
 @version 1.0.0
*/

import (
	"errors"
	"strings"
)

/*
获取某个合约的持仓信息
GET /api/swap/v3/<instrument_id>/position
*/
func (client *Client) GetSwapPositionByInstrument(instrumentId string) (map[string]interface{}, error) {

	sp := map[string]interface{}{}
	if _, err := client.Request(GET, GetInstrumentIdUri(SWAP_INSTRUMENT_POSITION, instrumentId), nil, &sp); err != nil {
		return nil, err
	}
	return sp, nil
}

/*
所有合约持仓信息
获取所有合约的持仓信息
限速规则：1次/10s
GET /api/swap/v3/position
*/
func (client *Client) GetSwapPositions() ([]map[string]interface{}, error) {

	sp := []map[string]interface{}{}
	if _, err := client.Request(GET, SWAP_POSITION, nil, &sp); err != nil {
		return nil, err
	}
	return sp, nil
}

func (client *Client) getSwapAccounts(uri string) (*SwapAccounts, error) {
	sa := SwapAccounts{}
	if _, err := client.Request(GET, uri, nil, &sa); err != nil {
		return nil, err
	}
	return &sa, nil
}

/*
获取所有币种合约的账户信息
HTTP请求
GET /api/swap/v3/accounts
*/
func (client *Client) GetSwapAccounts() (*SwapAccounts, error) {
	return client.getSwapAccounts(SWAP_ACCOUNTS)
}

/*
单个币种合约账户信息
HTTP请求
GET /api/swap/v3/<instrument_id>/accounts
*/
func (client *Client) GetSwapAccount(instrumentId string) (*SwapAccount, error) {

	sa := SwapAccount{}
	uri := GetInstrumentIdUri(SWAP_INSTRUMENT_ACCOUNT, instrumentId)
	if _, err := client.Request(GET, uri, nil, &sa); err != nil {
		return nil, err
	}
	return &sa, nil
}

/*
获取某个合约的杠杆倍数，持仓模式

HTTP请求
GET /api/swap/v3/accounts/<instrument_id>/settings
*/
func (client *Client) GetSwapAccountsSettingsByInstrument(instrumentId string) (*SwapAccountsSetting, error) {
	as := SwapAccountsSetting{}
	if _, err := client.Request(GET, GetInstrumentIdUri(SWAP_ACCOUNTS_SETTINGS, instrumentId), nil, &as); err != nil {
		return nil, err
	}
	return &as, nil
}

/*
设定某个合约的杠杆倍数

HTTP请求
POST /api/swap/v3/accounts/<instrument_id>/leverage
*/
func (client *Client) PostSwapAccountsLeverage(instrumentId string, leverage string, side string) (*SwapAccountsSetting, error) {
	params := make(map[string]string)
	params["leverage"] = leverage
	params["side"] = side
	as := SwapAccountsSetting{}
	if _, err := client.Request(POST, GetInstrumentIdUri(SWAP_ACCOUNTS_LEVERAGE, instrumentId), params, &as); err != nil {
		return nil, err
	}
	return &as, nil
}

/*
账单流水查询
列出账户资产流水，账户资产流水是指导致账户余额增加或减少的行为。流水会分页，每页100条数据，并且按照时间倒序排序和存储，最新的排在最前面。

HTTP请求
GET /api/swap/v3/accounts/<instrument_id>/ledger
*/
func (client *Client) GetSwapAccountLedger(instrumentId string, optionalParams map[string]string) ([]map[string]string, error) {
	baseUri := GetInstrumentIdUri(SWAP_ACCOUNTS_LEDGER, instrumentId)
	uri := baseUri
	if optionalParams != nil {
		uri = BuildParams(baseUri, optionalParams)
	}
	ll := []map[string]string{}
	if _, err := client.Request(GET, uri, nil, &ll); err != nil {
		return nil, err
	}
	return ll, nil
}

/*
API交易提供限价单下单模式，只有当您的账户有足够的资金才能下单。一旦下单，您的账户资金将在订单生命周期内被冻结，被冻结的资金以及数量取决于订单指定的类型和参数。

HTTP请求
POST /api/swap/v3/order
*/
func (client *Client) PostSwapOrder(instrumentId string, order *BasePlaceOrderInfo) (*SwapOrderResult, error) {
	or := SwapOrderResult{}
	info := PlaceOrderInfo{*order, instrumentId}
	if _, err := client.Request(POST, SWAP_ORDER, info, &or); err != nil {
		return nil, err
	}
	return &or, nil
}

/*
批量进行下单请求。

HTTP请求
POST /api/swap/v3/orders
*/
func (client *Client) PostSwapOrders(instrumentId string, orders []*BasePlaceOrderInfo) (*SwapOrdersResult, error) {
	sor := SwapOrdersResult{}
	orderData := PlaceOrdersInfo{InstrumentId: instrumentId, OrderData: orders}
	if _, err := client.Request(POST, SWAP_ORDERS, orderData, &sor); err != nil {
		return nil, err
	}
	return &sor, nil
}

/*
撤销之前下的未完成订单。

HTTP请求
POST /api/swap/v3/cancel_order/<instrument_id>/<order_id>
*/
func (client *Client) PostSwapCancelOrder(instrumentId string, orderId string) (*SwapCancelOrderResult, error) {
	uri := "/api/swap/v3/cancel_order/" + instrumentId + "/" + orderId
	or := SwapCancelOrderResult{}
	if _, err := client.Request(POST, uri, nil, &or); err != nil {
		return nil, err
	}
	return &or, nil

}

/*
批量撤销之前下的未完成订单。

HTTP请求
POST /api/swap/v3/cancel_batch_orders/<instrument_id>
*/
func (client *Client) PostSwapBatchCancelOrders(instrumentId string, orderIds []string) (*SwapCancelOrderResult, error) {
	uri := GetInstrumentIdUri(SWAP_CANCEL_BATCH_ORDERS, instrumentId)
	or := SwapCancelOrderResult{}

	params := map[string]interface{}{}
	params["ids"] = orderIds

	if _, err := client.Request(POST, uri, params, &or); err != nil {
		return nil, err
	}
	return &or, nil
}

/*
列出您当前所有的订单信息。

HTTP请求
GET /api/swap/v3/orders/<instrument_id>

请求示例
GET /api/swap/v3/orders/BTC-USD-SWAP?status=2&from=4&limit=30
*/
func (client *Client) GetSwapOrderByInstrumentId(instrumentId, state string, paramMap map[string]string) (*map[string]interface{}, error) {
	if paramMap["status"] == "" || len(instrumentId) == 0 {
		return nil, errors.New("Request Parameter's not correct, instrument_id and status is required.")
	}

	fullParams := NewParams()
	fullParams["instrument_id"] = instrumentId
	fullParams["state"] = state

	if paramMap != nil && len(paramMap) > 0 {
		for k, v := range paramMap {
			if len(v) > 0 {
				fullParams[k] = v
			}
		}
	}

	baseUri := GetInstrumentIdUri(SWAP_INSTRUMENT_ORDER_LIST, instrumentId)
	uri := BuildParams(baseUri, fullParams)

	soi := map[string]interface{}{}

	if _, err := client.Request(GET, uri, nil, &soi); err != nil {
		return nil, err
	}
	return &soi, nil
}

/*
通过订单id获取单个订单信息。

HTTP请求
GET /api/swap/v3/orders/<instrument_id>/<order_id>

请求示例
GET /api/swap/v3/orders/BTC-USD-SWAP/64-2a-26132f931-3
*/
func (client *Client) GetSwapOrderByOrderId(instrumentId string, orderId string) (*BaseOrderInfo, error) {
	return client.GetSwapOrderById(instrumentId, orderId)
}

/*
获取订单信息
通过订单id获取单个订单信息。

限速规则：40次/2s
HTTP请求
GET /api/swap/v3/orders/<instrument_id>/<order_id>
or
GET /api/swap/v3/orders/<instrument_id>/<client_oid>
*/
func (client *Client) GetSwapOrderById(instrumentId, orderOrClientId string) (*BaseOrderInfo, error) {

	orderInfo := BaseOrderInfo{}
	baseUri := GetInstrumentIdUri(SWAP_INSTRUMENT_ORDER_BY_ID, instrumentId)
	uri := strings.Replace(baseUri, "{order_client_id}", orderOrClientId, -1)

	if _, err := client.Request(GET, uri, nil, &orderInfo); err != nil {
		return nil, err
	}

	return &orderInfo, nil
}

/*
获取最近的成交明细列表。

HTTP请求
GET /api/swap/v3/fills

请求示例
GET /api/swap/v3/fills?order_id=64-2b-16122f931-3&instrument_id=BTC-USD-SWAP&from=1&limit=50(返回BTC-USD-SWAP中order_id为64-2b-16122f931-3的订单中第1页前50笔成交信息)
*/
func (client *Client) GetSwapFills(instrumentId string, orderId string, options map[string]string) ([]interface{}, error) {
	m := make(map[string]string)
	m["instrument_id"] = instrumentId
	m["order_id"] = orderId

	for k, v := range options {
		if v != "" && len(v) > 0 {
			m[k] = v
		}
	}

	uri := BuildParams(SWAP_FILLS, m)
	sfi := []interface{}{}

	if _, err := client.Request(GET, uri, nil, &sfi); err != nil {
		return nil, err
	}

	return sfi, nil
}

/*
获取可用合约的列表，这组公开接口提供了行情数据的快照，无需认证即可调用。 获取可用合约的列表，查询各合约的交易限制和价格步长等信息。

HTTP请求
GET /api/swap/v3/instruments
*/
func (client *Client) GetSwapInstruments() (*SwapInstrumentList, error) {
	sil := SwapInstrumentList{}
	if _, err := client.Request(GET, SWAP_INSTRUMENTS, nil, &sil); err != nil {
		return nil, err
	}

	return &sil, nil
}

/*
获取合约的深度列表。

HTTP请求
GET /api/swap/v3/instruments/<instrument_id>/depth

请求示例
GET /api/swap/v3/instruments/<instrument_id>/depth?size=50
*/
func (client *Client) GetSwapDepthByInstrumentId(instrumentId string, optionalSize string) (interface{}, error) {
	sid := SwapInstrumentDepth{}
	baseUri := GetInstrumentIdUri(SWAP_INSTRUMENT_DEPTH, instrumentId)
	if optionalSize != "" {
		baseUri = baseUri + "?size=" + optionalSize
	}

	if _, err := client.Request(GET, baseUri, nil, &sid); err != nil {
		return nil, err
	}

	return sid, nil
}

/*
获取平台全部合约的最新成交价、买一价、卖一价和24交易量。

HTTP请求
GET /api/swap/v3/instruments/ticker
*/
func (client *Client) GetSwapInstrumentsTicker() (*SwapTickerList, error) {
	stl := SwapTickerList{}
	if _, err := client.Request(GET, SWAP_INSTRUMENTS_TICKER, nil, &stl); err != nil {
		return nil, err
	}

	return &stl, nil
}

/*
获取合约的最新成交价、买一价、卖一价和24交易量。

HTTP请求
GET /api/swap/v3/instruments/<instrument_id>/ticker
*/
func (client *Client) GetSwapTickerByInstrument(instrumentId string) (*BaseTickerInfo, error) {
	bti := BaseTickerInfo{}
	uri := GetInstrumentIdUri(SWAP_INSTRUMENT_TICKER, instrumentId)
	if _, err := client.Request(GET, uri, nil, &bti); err != nil {
		return nil, err
	}

	bti.InstrumentId = instrumentId
	return &bti, nil
}

/*
获取合约的成交记录。

HTTP请求
GET /api/swap/v3/instruments/<instrument_id>/trades

请求示例
GET /api/swap/v3/instruments/BTC-USD-SWAP/trades?from=1&limit=50
*/
func (client *Client) GetSwapTradesByInstrument(instrumentId string, optionalParams map[string]string) (*SwapTradeList, error) {
	stl := SwapTradeList{}
	baseUri := GetInstrumentIdUri(SWAP_INSTRUMENT_TRADES, instrumentId)
	uri := BuildParams(baseUri, optionalParams)
	if _, err := client.Request(GET, uri, nil, &stl); err != nil {
		return nil, err
	}
	return &stl, nil
}

/*
获取合约的K线数据。

HTTP请求
GET /api/swap/v3/instruments/<instrument_id>/candles

请求示例
GET /api/swap/v3/instruments/BTC-USD-SWAP/candles?start=2018-10-26T02:31:00.000Z&end=2018-10-26T02:55:00.000Z&granularity=60(查询BTC-USD-SWAP的2018年10月26日02点31分到2018年10月26日02点55分的1分钟K线数据)
*/
func (client *Client) GetSwapCandlesByInstrument(instrumentId string, optionalParams map[string]string) (*SwapCandleList, error) {
	scl := SwapCandleList{}
	baseUri := GetInstrumentIdUri(SWAP_INSTRUMENT_CANDLES, instrumentId)
	uri := baseUri
	if len(optionalParams) > 0 {
		uri = BuildParams(baseUri, optionalParams)
	}
	if _, err := client.Request(GET, uri, nil, &scl); err != nil {
		return nil, err
	}
	return &scl, nil

}

/*
获取币种指数。

HTTP请求
GET /api/swap/v3/instruments/<instrument_id>/index

请求示例
GET /api/swap/v3/instruments/BTC-USD-SWAP/index
*/
func (client *Client) GetSwapIndexByInstrument(instrumentId string) (*SwapIndexInfo, error) {
	sii := SwapIndexInfo{}
	if _, err := client.Request(GET, GetInstrumentIdUri(SWAP_INSTRUMENT_INDEX, instrumentId), nil, &sii); err != nil {
		return nil, err
	}
	return &sii, nil
}

/*
获取合约整个平台的总持仓量。

HTTP请求
GET /api/swap/v3/instruments/<instrument_id>/open_interest
*/
func (client *Client) GetSwapOpenInterestByInstrument(instrumentId string) (*SwapOpenInterest, error) {
	sii := SwapOpenInterest{}
	if _, err := client.Request(GET, GetInstrumentIdUri(SWAP_INSTRUMENT_OPEN_INTEREST, instrumentId), nil, &sii); err != nil {
		return nil, err
	}
	return &sii, nil
}

/*
获取合约当前开仓的最高买价和最低卖价。

HTTP请求
GET /api/swap/v3/instruments/<instrument_id>/price_limit
*/
func (client *Client) GetSwapPriceLimitByInstrument(instrumentId string) (*SwapPriceLimit, error) {
	sii := SwapPriceLimit{}
	if _, err := client.Request(GET, GetInstrumentIdUri(SWAP_INSTRUMENT_PRICE_LIMIT, instrumentId), nil, &sii); err != nil {
		return nil, err
	}
	return &sii, nil
}

/*
获取合约爆仓单。

HTTP请求
GET /api/swap/v3/instruments/<instrument_id>/liquidation

请求示例
GET /api/swap/v3/instruments/BTC-USD-SWAP/liquidation?status=0&from=1&limit=50
*/
func (client *Client) GetSwapLiquidationByInstrument(instrumentId string, status string, optionalParams map[string]string) (*SwapLiquidationList, error) {
	scl := SwapLiquidationList{}
	baseUri := GetInstrumentIdUri(SWAP_INSTRUMENT_LIQUIDATION, instrumentId)
	uri := baseUri
	if optionalParams != nil {
		optionalParams["status"] = status
		uri = BuildParams(baseUri, optionalParams)
	} else {
		oParams := map[string]string{}
		oParams["status"] = status
		uri = BuildParams(baseUri, oParams)
	}
	if _, err := client.Request(GET, uri, nil, &scl); err != nil {
		return nil, err
	}
	return &scl, nil
}

/*
获取合约挂单冻结数量。

HTTP请求
GET /api/swap/v3/accounts/<instrument_id>/holds
*/
func (client *Client) GetSwapAccountsHoldsByInstrument(instrumentId string) (*SwapAccountHolds, error) {
	r := SwapAccountHolds{}
	if _, err := client.Request(GET, GetInstrumentIdUri(SWAP_ACCOUNTS_HOLDS, instrumentId), nil, &r); err != nil {
		return nil, err
	}
	return &r, nil
}

/*
获取合约下一次的结算时间。

HTTP请求
GET /api/swap/v3/instruments/<instrument_id>/funding_time
*/
func (client *Client) GetSwapFundingTimeByInstrument(instrumentId string) (*SwapFundingTime, error) {
	r := SwapFundingTime{}
	if _, err := client.Request(GET, GetInstrumentIdUri(SWAP_INSTRUMENT_FUNDING_TIME, instrumentId), nil, &r); err != nil {
		return nil, err
	}
	return &r, nil
}

/*
获取合约标记价格。

HTTP请求
GET /api/swap/v3/instruments/<instrument_id>/mark_price
*/
func (client *Client) GetSwapMarkPriceByInstrument(instrumentId string) (*SwapMarkPrice, error) {
	r := SwapMarkPrice{}
	if _, err := client.Request(GET, GetInstrumentIdUri(SWAP_INSTRUMENT_MARK_PRICE, instrumentId), nil, &r); err != nil {
		return nil, err
	}
	return &r, nil
}

/*
获取合约历史资金费率。

HTTP请求
GET /api/swap/v3/instruments/<instrument_id>/historical_funding_rate

请求示例
GET /api/swap/v3/instruments/BTC-USD-SWAP/historical_funding_rate?from=1&limit=50
*/
func (client *Client) GetSwapHistoricalFundingRateByInstrument(instrumentId string, optionalParams map[string]string) (*SwapHistoricalFundingRateList, error) {
	r := SwapHistoricalFundingRateList{}
	baseUri := GetInstrumentIdUri(SWAP_INSTRUMENT_HISTORICAL_FUNDING_RATE, instrumentId)
	uri := baseUri
	if optionalParams != nil {
		uri = BuildParams(baseUri, optionalParams)
	}

	if _, err := client.Request(GET, uri, nil, &r); err != nil {
		return nil, err
	}
	return &r, nil
}

/*
获取法币汇率。

HTTP请求
GET /api/swap/v3/rate
*/
func (client *Client) GetSwapRate() (*SwapRate, error) {
	sr := SwapRate{}
	if _, err := client.Request(GET, SWAP_RATE, nil, &sr); err != nil {
		return nil, err
	}
	return &sr, nil
}
