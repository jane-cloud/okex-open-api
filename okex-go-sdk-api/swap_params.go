package okex

/*
 OKEX swap api parameter's definition
 @author Lingting Fu
 @date 2018-12-27
 @version 1.0.0
*/

type BasePlaceOrderInfo struct {
	ClientOid  string `json:"client_oid"`
	Price      string `json:"price"`
	MatchPrice string `json:"match_price"`
	Type       string `json:"type"`
	Size       string `json:"size"`
}

type PlaceOrderInfo struct {
	BasePlaceOrderInfo
	InstrumentId string `json:"instrument_id"`
}

type PlaceOrdersInfo struct {
	InstrumentId string                `json:"instrument_id"`
	OrderData    []*BasePlaceOrderInfo `json:"order_data"`
}
