package okex

import "strings"

/*
币币账户信息
获取币币账户资产列表(仅展示拥有资金的币对)，查询各币种的余额、冻结和可用等信息。

限速规则：20次/2s
HTTP请求
GET /api/spot/v3/accounts

*/
func (client *Client) GetSpotAccounts() (*[]map[string]string, error) {
	r := []map[string]string{}

	if _, err := client.Request(GET, SPOT_ACCOUNTS, nil, &r); err != nil {
		return nil, err
	}
	return &r, nil
}

/*
单一币种账户信息
获取币币账户单个币种的余额、冻结和可用等信息。

限速规则：20次/2s
HTTP请求
GET /api/spot/v3/accounts/<currency>
*/
func (client *Client) GetSpotAccountsCurrency(currency string) (*map[string]interface{}, error) {
	r := map[string]interface{}{}
	uri := GetCurrencyUri(SPOT_ACCOUNTS_CURRENCY, currency)

	if _, err := client.Request(GET, uri, nil, &r); err != nil {
		return nil, err
	}
	return &r, nil
}

/*
账单流水查询
列出账户资产流水。账户资产流水是指导致账户余额增加或减少的行为。流水会分页，并且按时间倒序排序和存储，最新的排在最前面。请参阅分页部分以获取第一页之后的其他记录。

限速规则：20次/2s
HTTP请求
GET /api/spot/v3/accounts/<currency>/ledger
*/
func (client *Client) GetSpotAccountsCurrencyLeger(currency string, optionalParams *map[string]string) (*[]map[string]interface{}, error) {
	r := []map[string]interface{}{}

	baseUri := GetCurrencyUri(SPOT_ACCOUNTS_CURRENCY_LEDGER, currency)
	uri := baseUri
	if optionalParams != nil && len(*optionalParams) > 0 {
		uri = BuildParams(baseUri, *optionalParams)
	}

	if _, err := client.Request(GET, uri, nil, &r); err != nil {
		return nil, err
	}
	return &r, nil
}

/*
获取订单列表
列出您当前所有的订单信息。这个请求支持分页，并且按时间倒序排序和存储，最新的排在最前面。请参阅分页部分以获取第一页之后的其他纪录。

限速规则：20次/2s
HTTP请求
GET /api/spot/v3/orders
*/
func (client *Client) GetSpotOrders(status, instrument_id string, options *map[string]string) (*[]interface{}, error) {
	r := []interface{}{}

	fullOptions := NewParams()
	fullOptions["instrument_id"] = instrument_id
	fullOptions["status"] = status
	if options != nil && len(*options) > 0 {
		fullOptions["before"] = (*options)["before"]
		fullOptions["after"] = (*options)["after"]
		fullOptions["limit"] = (*options)["limit"]
	}

	uri := BuildParams(SPOT_ORDERS, fullOptions)

	if _, err := client.Request(GET, uri, nil, &r); err != nil {
		return nil, err
	}
	return &r, nil
}

/*
获取所有未成交订单
列出您当前所有的订单信息。这个请求支持分页，并且按时间倒序排序和存储，最新的排在最前面。请参阅分页部分以获取第一页之后的其他纪录。

限速规则：20次/2s
HTTP请求
GET /api/spot/v3/orders_pending
*/
func (client *Client) GetSpotOrdersPending(instrumentId string, options *map[string]string) (*[]interface{}, error) {
	r := []interface{}{}

	fullOptions := NewParams()
	fullOptions["instrument_id"] = instrumentId

	if options != nil && len(*options) > 0 {

		for k, v := range *options {
			if v != "" && len(v) > 0 {
				fullOptions[k] = v
			}
		}
	}

	uri := BuildParams(SPOT_ORDERS_PENDING, fullOptions)
	if _, err := client.Request(GET, uri, nil, &r); err != nil {
		return nil, err
	}

	return &r, nil
}

/*
获取订单信息
通过订单ID获取单个订单信息。

限速规则：20次/2s

HTTP请求
GET /api/spot/v3/orders/<order_id>
或者
GET /api/spot/v3/orders/<client_oid>
*/
func (client *Client) GetSpotOrdersById(instrumentId, orderOrClientId string) (*map[string]string, error) {
	r := map[string]string{}
	uri := strings.Replace(SPOT_ORDERS_BY_ID, "{order_client_id}", orderOrClientId, -1)
	options := NewParams()
	options["instrument_id"] = instrumentId
	uri = BuildParams(uri, options)

	if _, err := client.Request(GET, uri, nil, &r); err != nil {
		return nil, err
	}
	return &r, nil
}

/*
获取成交明细
获取最近的成交明细表。这个请求支持分页，并且按时间倒序排序和存储，最新的排在最前面。请参阅分页部分以获取第一页之后的其他记录。

限速规则：20次/2s
HTTP请求
GET /api/spot/v3/fills
*/
func (client *Client) GetSpotFills(order_id, instrument_id string, options *map[string]string) (*[]map[string]interface{}, error) {
	r := []map[string]interface{}{}

	fullOptions := NewParams()
	fullOptions["instrument_id"] = instrument_id
	fullOptions["order_id"] = order_id
	if options != nil && len(*options) > 0 {
		fullOptions["before"] = (*options)["before"]
		fullOptions["after"] = (*options)["after"]
		fullOptions["limit"] = (*options)["limit"]
	}

	uri := BuildParams(SPOT_FILLS, fullOptions)

	if _, err := client.Request(GET, uri, nil, &r); err != nil {
		return nil, err
	}
	return &r, nil
}

/*
获取币对信息
用于获取行情数据，这组公开接口提供了行情数据的快照，无需认证即可调用。

获取交易币对的列表，查询各币对的交易限制和价格步长等信息。

限速规则：20次/2s
HTTP请求
GET /api/spot/v3/instruments
*/
func (client *Client) GetSpotInstruments() (*[]map[string]string, error) {
	r := []map[string]string{}

	if _, err := client.Request(GET, SPOT_INSTRUMENTS, nil, &r); err != nil {
		return nil, err
	}
	return &r, nil
}

/*
获取深度数据
获取币对的深度列表。这个请求不支持分页，一个请求返回整个深度列表。

限速规则：20次/2s
HTTP请求
GET /api/spot/v3/instruments/<instrument_id>/book
*/
func (client *Client) GetSpotInstrumentBook(instrumentId string, optionalParams *map[string]string) (*map[string]interface{}, error) {
	r := map[string]interface{}{}
	uri := GetInstrumentIdUri(SPOT_INSTRUMENT_BOOK, instrumentId)
	if optionalParams != nil && len(*optionalParams) > 0 {
		optionals := NewParams()
		optionals["size"] = (*optionalParams)["size"]
		optionals["depth"] = (*optionalParams)["depth"]
		uri = BuildParams(uri, optionals)
	}

	if _, err := client.Request(GET, uri, nil, &r); err != nil {
		return nil, err
	}
	return &r, nil
}

/*
获取全部ticker信息
获取平台全部币对的最新成交价、买一价、卖一价和24小时交易量的快照信息。

限速规则：50次/2s
HTTP请求
GET /api/spot/v3/instruments/ticker
*/
func (client *Client) GetSpotInstrumentsTicker() (*[]map[string]interface{}, error) {
	r := []map[string]interface{}{}

	if _, err := client.Request(GET, SPOT_INSTRUMENTS_TICKER, nil, &r); err != nil {
		return nil, err
	}
	return &r, nil
}

/*
获取某个ticker信息
获取币对的最新成交价、买一价、卖一价和24小时交易量的快照信息。

限速规则：20次/2s
HTTP请求
GET /api/spot/v3/instruments/<instrument-id>/ticker
*/
func (client *Client) GetSpotInstrumentTicker(instrument_id string) (*map[string]interface{}, error) {
	r := map[string]interface{}{}

	uri := GetInstrumentIdUri(SPOT_INSTRUMENT_TICKER, instrument_id)
	if _, err := client.Request(GET, uri, nil, &r); err != nil {
		return nil, err
	}
	return &r, nil
}

/*
获取成交数据
获取币对最新的60条成交列表。这个请求支持分页，并且按时间倒序排序和存储，最新的排在最前面。请参阅分页部分以获取第一页之后的其他纪录。

限速规则：20次/2s
HTTP请求
GET /api/spot/v3/instruments/<instrument_id>/trades
*/
func (client *Client) GetSpotInstrumentTrade(instrument_id string, options *map[string]string) (*[]map[string]interface{}, error) {
	r := []map[string]interface{}{}

	uri := GetInstrumentIdUri(SPOT_INSTRUMENT_TRADES, instrument_id)
	fullOptions := NewParams()
	if options != nil && len(*options) > 0 {
		fullOptions["from"] = (*options)["from"]
		fullOptions["to"] = (*options)["to"]
		fullOptions["limit"] = (*options)["limit"]
		uri = BuildParams(uri, fullOptions)
	}

	if _, err := client.Request(GET, uri, nil, &r); err != nil {
		return nil, err
	}
	return &r, nil
}

/*
获取成交数据
获取币对最新的60条成交列表。这个请求支持分页，并且按时间倒序排序和存储，最新的排在最前面。请参阅分页部分以获取第一页之后的其他纪录。

限速规则：20次/2s
HTTP请求
GET /api/spot/v3/instruments/<instrument_id>/candles
*/
func (client *Client) GetSpotInstrumentCandles(instrument_id string, options *map[string]string) (*[]interface{}, error) {
	r := []interface{}{}

	uri := GetInstrumentIdUri(SPOT_INSTRUMENT_CANDLES, instrument_id)
	fullOptions := NewParams()
	if options != nil && len(*options) > 0 {
		fullOptions["start"] = (*options)["start"]
		fullOptions["end"] = (*options)["end"]
		fullOptions["granularity"] = (*options)["granularity"]
		uri = BuildParams(uri, fullOptions)
	}

	if _, err := client.Request(GET, uri, nil, &r); err != nil {
		return nil, err
	}
	return &r, nil
}

/*
下单
OKEx币币交易提供限价单和市价单两种下单模式(更多下单模式将会在后期支持)。只有当您的账户有足够的资金才能下单。

一旦下单，您的账户资金将在订单生命周期内被冻结。被冻结的资金以及数量取决于订单指定的类型和参数。

限速规则：100次/2s
HTTP请求
POST /api/spot/v3/orders
*/
func (client *Client) PostSpotOrders(side, instrument_id string, optionalOrderInfo *map[string]string) (result *map[string]interface{}, err error) {

	r := map[string]interface{}{}
	postParams := NewParams()
	postParams["side"] = side
	postParams["instrument_id"] = instrument_id

	if optionalOrderInfo != nil && len(*optionalOrderInfo) > 0 {

		for k, v := range *optionalOrderInfo {
			postParams[k] = v
		}

		if postParams["type"] == "limit" {
			postParams["price"] = (*optionalOrderInfo)["price"]
			postParams["size"] = (*optionalOrderInfo)["size"]

		} else if postParams["type"] == "market" {
			postParams["size"] = (*optionalOrderInfo)["size"]
			postParams["notional"] = (*optionalOrderInfo)["notional"]

		}
	}

	if _, err := client.Request(POST, SPOT_ORDERS, postParams, &r); err != nil {
		return nil, err
	}

	return &r, nil
}

/*
批量下单
下指定币对的多个订单（每次只能下最多4个币对且每个币对可批量下10个单）。

限速规则：50次/2s
HTTP请求
POST /api/spot/v3/batch_orders
*/
func (client *Client) PostSpotBatchOrders(orderInfos *[]map[string]string) (*map[string]interface{}, error) {
	r := map[string]interface{}{}
	if _, err := client.Request(POST, SPOT_BATCH_ORDERS, orderInfos, &r); err != nil {
		return nil, err
	}
	return &r, nil
}

/*
撤销指定订单
撤销之前下的未完成订单。

限速规则：100次/2s
HTTP请求
POST /api/spot/v3/cancel_orders/<order_id>
或者
POST /api/spot/v3/cancel_orders/<client_oid>
*/
func (client *Client) PostSpotCancelOrders(instrumentId, orderOrClientId string) (*map[string]interface{}, error) {
	r := map[string]interface{}{}

	uri := strings.Replace(SPOT_CANCEL_ORDERS_BY_ID, "{order_client_id}", orderOrClientId, -1)
	options := NewParams()
	options["instrument_id"] = instrumentId

	if _, err := client.Request(POST, uri, options, &r); err != nil {
		return nil, err
	}
	return &r, nil

}

/*
批量撤销订单
撤销指定的某一种或多种币对的所有未完成订单，每个币对可批量撤10个单。

限速规则：50次/2s
HTTP请求
POST /api/spot/v3/cancel_batch_orders
*/
func (client *Client) PostSpotCancelBatchOrders(orderInfos *[]map[string]interface{}) (*map[string]interface{}, error) {
	r := map[string]interface{}{}
	if _, err := client.Request(POST, SPOT_CANCEL_BATCH_ORDERS, orderInfos, &r); err != nil {
		return nil, err
	}
	return &r, nil
}
