package okex

import (
	"strings"
)

/*
币币杠杆账户信息
获取币币杠杆账户资产列表，查询各币种的余额、冻结和可用等信息。

限速规则：20次/2s
HTTP请求
GET /api/margin/v3/accounts
*/
func (client *Client) GetMarginAccounts() (*[]map[string]interface{}, error) {
	r := []map[string]interface{}{}

	if _, err := client.Request(GET, MARGIN_ACCOUNTS, nil, &r); err != nil {
		return nil, err
	}
	return &r, nil
}

/*
单一币对账户信息
获取币币杠杆某币对账户的余额、冻结和可用等信息。

限速规则：20次/2s
HTTP请求
GET /api/margin/v3/accounts/<instrument_id>
*/
func (client *Client) GetMarginAccountsByInstrument(instrumentId string) (*map[string]interface{}, error) {
	r := map[string]interface{}{}

	uri := GetInstrumentIdUri(MARGIN_ACCOUNTS_INSTRUMENT, instrumentId)
	if _, err := client.Request(GET, uri, nil, &r); err != nil {
		return nil, err
	}
	return &r, nil
}

/*
账单流水查询
列出杠杆帐户资产流水。帐户资产流水是指导致帐户余额增加或减少的行为。流水会分页，并且按时间倒序排序和存储，最新的排在最前面。请参阅分页部分以获取第一页之后的其他纪录。

限速规则：20次/2s
HTTP请求
GET /api/margin/v3/accounts/<instrument_id>/ledger
*/
func (client *Client) GetMarginAccountsLegerByInstrument(instrumentId string, optionalParams *map[string]string) (*[]map[string]interface{}, error) {
	r := []map[string]interface{}{}
	uri := GetInstrumentIdUri(MARGIN_ACCOUNTS_INSTRUMENT_LEDGER, instrumentId)
	if optionalParams != nil && len(*optionalParams) > 0 {
		uri = BuildParams(uri, *optionalParams)
	}

	if _, err := client.Request(GET, uri, nil, &r); err != nil {
		return nil, err
	}
	return &r, nil
}

/*
杠杆配置信息
获取币币杠杆账户的借币配置信息，包括当前最大可借、借币利率、最大杠杆倍数。

限速规则：20次/2s
HTTP请求
GET /api/margin/v3/accounts/availability
*/
func (client *Client) GetMarginAccountsAvailability() (*[]map[string]interface{}, error) {
	r := []map[string]interface{}{}

	if _, err := client.Request(GET, MARGIN_ACCOUNTS_AVAILABILITY, nil, &r); err != nil {
		return nil, err
	}
	return &r, nil
}

/*
某个杠杆配置信息
获取某个币币杠杆账户的借币配置信息，包括当前最大可借、借币利率、最大杠杆倍数。

限速规则：20次/2s
HTTP请求
GET /api/margin/v3/accounts/<instrument_id>/availability
*/
func (client *Client) GetMarginAccountsAvailabilityByInstrumentId(instrumentId string) (*[]map[string]interface{}, error) {
	r := []map[string]interface{}{}

	uri := GetInstrumentIdUri(MARGIN_ACCOUNTS_INSTRUMENT_AVAILABILITY, instrumentId)

	if _, err := client.Request(GET, uri, nil, &r); err != nil {
		return nil, err
	}
	return &r, nil
}

/*
获取借币记录
获取币币杠杆帐户的借币记录。这个请求支持分页，并且按时间倒序排序和存储，最新的排在最前面。请参阅分页部分以获取第一页之后的其他纪录。

限速规则：20次/2s
HTTP请求
GET /api/margin/v3/accounts/borrowed
*/
func (client *Client) GetMarginAccountsBorrowed(optionalParams *map[string]string) (*[]map[string]interface{}, error) {
	r := []map[string]interface{}{}

	uri := MARGIN_ACCOUNTS_BORROWED
	if optionalParams != nil {
		uri = BuildParams(uri, *optionalParams)
	}
	if _, err := client.Request(GET, uri, nil, &r); err != nil {
		return nil, err
	}
	return &r, nil
}

/*
某账户借币记录
获取币币杠杆帐户某币对的借币记录。这个请求支持分页，并且按时间倒序排序和存储，最新的排在最前面。请参阅分页部分以获取第一页之后的其他纪录。

限速规则：20次/2s
HTTP请求
GET /api/margin/v3/accounts/<instrument_id>/borrowed
*/
func (client *Client) GetMarginAccountsBorrowedByInstrumentId(instrumentId string, optionalParams *map[string]string) (*[]map[string]interface{}, error) {
	r := []map[string]interface{}{}

	uri := GetInstrumentIdUri(MARGIN_ACCOUNTS_INSTRUMENT_BORROWED, instrumentId)
	if optionalParams != nil && len(*optionalParams) > 0 {
		uri = BuildParams(uri, *optionalParams)
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
GET /api/margin/v3/orders
*/
func (client *Client) GetMarginOrders(instrumentId, state string, optionalParams *map[string]string) (*[]map[string]interface{}, error) {
	r := []map[string]interface{}{}
	fullParams := NewParams()
	fullParams["instrument_id"] = instrumentId
	fullParams["state"] = state

	if optionalParams != nil && len(*optionalParams) > 0 {
		for k, v := range *optionalParams {
			fullParams[k] = v
		}
	}

	uri := BuildParams(MARGIN_ORDERS, fullParams)

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
GET /api/margin/v3/orders/<order_id>
或者
GET /api/margin/v3/orders/<client_oid>
*/
func (client *Client) GetMarginOrdersById(instrumentId, orderOrClientId string) (*map[string]string, error) {

	r := map[string]string{}
	uri := strings.Replace(MARGIN_ORDERS_BY_ID, "{order_client_id}", orderOrClientId, -1)

	fullParams := NewParams()
	fullParams["instrument_id"] = instrumentId
	uri = BuildParams(uri, fullParams)

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
GET /api/margin/v3/orders_pending
*/
func (client *Client) GetMarginOrdersPending(instrumentId string, optionalParams *map[string]string) (*[]map[string]interface{}, error) {
	r := []map[string]interface{}{}

	fullParams := NewParams()
	fullParams["instrument_id"] = instrumentId

	if optionalParams != nil && len(*optionalParams) > 0 {
		for k, v := range *optionalParams {
			if v != "" && len(v) > 0 {
				fullParams[k] = v
			}
		}
	}

	uri := BuildParams(MARGIN_ORDERS_PENDING, fullParams)

	if _, err := client.Request(GET, uri, nil, &r); err != nil {
		return nil, err
	}
	return &r, nil
}

/*
获取成交明细
获取最近的成交明细列表。这个请求支持分页，并且按时间倒序排序和存储，最新的排在最前面。请参阅分页部分以获取第一页之后的其他纪录。

限速规则：20次/2s
HTTP请求
GET /api/margin/v3/fills
*/
func (client *Client) GetMarginFills(instrumentId, orderId string, optionalParams *map[string]string) (*[]map[string]interface{}, error) {
	r := []map[string]interface{}{}

	fullParams := NewParams()
	fullParams["instrument_id"] = instrumentId
	fullParams["order_id"] = orderId

	if optionalParams != nil && len(*optionalParams) > 0 {
		for k, v := range *optionalParams {
			if v != "" && len(v) > 0 {
				fullParams[k] = v
			}
		}
	}

	uri := BuildParams(MARGIN_FILLS, fullParams)

	if _, err := client.Request(GET, uri, nil, &r); err != nil {
		return nil, err
	}
	return &r, nil
}

/*
借币
在某个币币杠杆账户里进行借币。

限速规则：100次/2s
HTTP请求
POST /api/margin/v3/accounts/borrow
*/
func (client *Client) PostMarginAccountsBorrow(instrumentId, currency, amount string) (*map[string]interface{}, error) {
	r := map[string]interface{}{}

	bodyParams := NewParams()
	bodyParams["instrument_id"] = instrumentId
	bodyParams["currency"] = currency
	bodyParams["amount"] = amount

	if _, err := client.Request(POST, MARGIN_ACCOUNTS_BORROW, bodyParams, &r); err != nil {
		return nil, err
	}

	return &r, nil
}

/*
还币
在某个币币杠杆账户里进行还币。

限速规则：100次/2s
HTTP请求
POST /api/margin/v3/accounts/repayment
*/
func (client *Client) PostMarginAccountsRepayment(instrumentId, currency, amount string, optionalBorrowId *string) (*map[string]interface{}, error) {
	r := map[string]interface{}{}

	bodyParams := NewParams()
	bodyParams["instrument_id"] = instrumentId
	bodyParams["currency"] = currency
	bodyParams["amount"] = amount

	if optionalBorrowId != nil && len(*optionalBorrowId) > 0 {
		bodyParams["borrow_id"] = *optionalBorrowId
	}

	if _, err := client.Request(POST, MARGIN_ACCOUNTS_REPAYMENT, bodyParams, &r); err != nil {
		return nil, err
	}
	return &r, nil
}

/*
下单
OKEx API提供limit和market两种下单模式。只有当您的账户有足够的资金才能下单。一旦下单，您的账户资金将在订单生命周期内被冻结。被冻结的资金以及数量取决于订单指定的类型和参数。

限速规则：100次/2s
HTTP请求
POST /api/margin/v3/orders
*/
func (client *Client) PostMarginOrders(side, instrument_id, margin_trading string, optionalOrderInfo *map[string]string) (*map[string]interface{}, error) {
	r := map[string]interface{}{}

	postParams := NewParams()
	postParams["side"] = side
	postParams["instrument_id"] = instrument_id
	postParams["margin_trading"] = margin_trading

	if optionalOrderInfo != nil && len(*optionalOrderInfo) > 0 {
		postParams["client_oid"] = (*optionalOrderInfo)["client_oid"]
		postParams["type"] = (*optionalOrderInfo)["type"]
		postParams["order_type"] = (*optionalOrderInfo)["order_type"]

		if postParams["type"] == "limit" {
			postParams["price"] = (*optionalOrderInfo)["price"]
			postParams["size"] = (*optionalOrderInfo)["size"]

		} else if postParams["type"] == "market" {
			postParams["size"] = (*optionalOrderInfo)["size"]
			postParams["notional"] = (*optionalOrderInfo)["notional"]

		}
	}

	if _, err := client.Request(POST, MARGIN_ORDERS, postParams, &r); err != nil {
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
func (client *Client) PostMarginBatchOrders(orderInfos *[]map[string]string) (*map[string]interface{}, error) {
	r := map[string]interface{}{}
	if _, err := client.Request(POST, MARGIN_BATCH_ORDERS, orderInfos, &r); err != nil {
		return nil, err
	}
	return &r, nil
}

/*
撤销指定订单
撤销之前下的未完成订单。

限速规则：100次/2s
HTTP请求
POST /api/margin/v3/cancel_orders/<order_id>
或者
POST /api/margin/v3/cancel_orders/<client_oid>
*/
func (client *Client) PostMarginCancelOrdersById(instrumentId, orderOrClientId string) (*map[string]interface{}, error) {
	r := map[string]interface{}{}
	uri := strings.Replace(MARGIN_CANCEL_ORDERS_BY_ID, "{order_client_id}", orderOrClientId, -1)

	fullParams := NewParams()
	fullParams["instrument_id"] = instrumentId
	uri = BuildParams(uri, fullParams)

	if _, err := client.Request(POST, uri, fullParams, &r); err != nil {
		return nil, err
	}
	return &r, nil
}

/*
批量撤销订单
撤销指定的某一种或多种币对的所有未完成订单，每个币对可批量撤10个单。

限速规则：50次/2s
HTTP请求
POST /api/margin/v3/cancel_batch_orders
*/
func (client *Client) PostMarginCancelBatchOrders(orderInfos *[]map[string]string) (*map[string]interface{}, error) {
	r := map[string]interface{}{}

	if _, err := client.Request(POST, MARGIN_CANCEL_BATCH_ORDERS, *orderInfos, &r); err != nil {
		return nil, err
	}

	return &r, nil
}
