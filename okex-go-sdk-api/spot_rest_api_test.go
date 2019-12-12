package okex

import (
	"fmt"
	"github.com/stretchr/testify/assert"
	"github.com/stretchr/testify/require"
	"testing"
)

func TestGetSpotAccounts(t *testing.T) {
	c := NewTestClient()
	ac, err := c.GetSpotAccounts()

	fmt.Printf("%+v, %+v", ac, err)

	assert.True(t, err == nil)
	jstr, _ := Struct2JsonString(ac)
	println(jstr)
}

func TestGetSpotAccountsCurrency(t *testing.T) {
	c := NewTestClient()
	ac, err := c.GetSpotAccountsCurrency("BTC")
	assert.True(t, err == nil)
	jstr, _ := Struct2JsonString(ac)
	println(jstr)
}

func TestGetSpotAccountsCurrencyLeger(t *testing.T) {
	c := NewTestClient()
	ac, err := c.GetSpotAccountsCurrencyLeger("btc", nil)
	assert.True(t, err == nil)
	jstr, _ := Struct2JsonString(ac)
	println(jstr)

	options := map[string]string{}
	options["from"] = "1"
	options["to"] = "2"
	options["limit"] = "100"

	ac2, err2 := c.GetSpotAccountsCurrencyLeger("btc", &options)
	assert.True(t, ac2 != nil && err2 == nil)
}

func getFirstValidSpotInstrument() map[string]string {
	c := NewTestClient()
	ac, _ := c.GetSpotInstruments()
	return (*ac)[0]
}

func TestGetSpotInstruments(t *testing.T) {
	c := NewTestClient()
	ac, err := c.GetSpotInstruments()
	assert.True(t, err == nil)
	jstr, _ := Struct2JsonString(ac)
	println(jstr)
}

func TestGetSpotInstrumentBook(t *testing.T) {
	c := NewTestClient()
	ac, err := c.GetSpotInstrumentBook("LTC-USDT", nil)
	assert.True(t, err == nil)
	jstr, _ := Struct2JsonString(ac)
	println(jstr)
}

func TestGetSpotInstrumentsTicker(t *testing.T) {
	c := NewTestClient()
	ac, err := c.GetSpotInstrumentsTicker()
	assert.True(t, err == nil)
	jstr, _ := Struct2JsonString(ac)
	println(jstr)
}

func TestGetSpotInstrumentTicker(t *testing.T) {
	c := NewTestClient()
	ac, err := c.GetSpotInstrumentTicker("LTC-USDT")
	assert.True(t, err == nil)
	jstr, _ := Struct2JsonString(ac)
	println(jstr)
}

func TestGetSpotInstrumentTrade(t *testing.T) {
	c := NewTestClient()
	ac, err := c.GetSpotInstrumentTrade("BTC-USDT", nil)
	assert.True(t, err == nil)
	jstr, _ := Struct2JsonString(ac)
	println(jstr)

	options := map[string]string{}
	options["from"] = "1"
	options["to"] = "2"
	options["limit"] = "100"

	ac2, err := c.GetSpotInstrumentTrade("BTC-USDT", &options)
	assert.True(t, err == nil)
	jstr, _ = Struct2JsonString(ac2)
	println(jstr)
}

func TestGetSpotInstrumentCandles(t *testing.T) {
	c := NewTestClient()
	ac, err := c.GetSpotInstrumentCandles("BTC-USDT", nil)
	assert.True(t, err == nil)
	jstr, _ := Struct2JsonString(ac)
	println(jstr)
}

func TestPostSpotOrders(t *testing.T) {
	c := NewTestClient()

	optionals := NewParams()

	// case1: Fail to sell a spot limit order
	optionals["type"] = "limit"
	optionals["price"] = "100"
	optionals["size"] = "100000"

	r, err := c.PostSpotOrders("sell", "btc-usdt", &optionals)
	require.True(t, r != nil, r)
	require.True(t, (*r)["error_code"] == "33017", r)
	require.True(t, err == nil, err)
	jstr, _ := Struct2JsonString(r)
	println(jstr)

	// Case2: Success to buy or sell a spot limit order
	accounts, err := c.GetSpotAccounts()
	var acc map[string]string
	for _, ac := range *accounts {
		if ac["currency"] == "ETC" {
			acc = ac
			break
		}
	}

	currency := acc["currency"]
	instId := currency + "-USDT"
	optionals["type"] = "limit"
	optionals["price"] = "0.01"
	optionals["size"] = "0.01"

	r, err = c.PostSpotOrders("buy", instId, &optionals)
	fmt.Printf("%+v %+v \n", r, err)

	// Case3: Cancel posted order.
	r, err = c.PostSpotCancelOrders(instId, "fake_order_id")
	fmt.Printf("%+v %+v \n", r, err)

}

func TestClient_PostSpotBatchOrders(t *testing.T) {
	c := NewTestClient()

	orderInfos := []map[string]string{
		map[string]string{"client_oid": "w20180728w", "instrument_id": "btc-usdt", "side": "sell", "type": "limit", "size": "0.001", "price": "10001", "margin_trading ": "1"},
		map[string]string{"client_oid": "r20180728r", "instrument_id": "btc-usdt", "side": "sell", "type": "limit", " size ": "0.001", "notional": "10002", "margin_trading ": "1"},
	}

	r, err := c.PostSpotBatchOrders(&orderInfos)
	assert.True(t, r != nil && err == nil)
	jstr, _ := Struct2JsonString(r)
	println(jstr)
}

func TestClient_PostSpotCancelBatchOrders(t *testing.T) {
	c := NewTestClient()

	orderInfos := []map[string]interface{}{
		map[string]interface{}{"instrument_id": "btc-usdt", "client_oids": []string{"16ee593327162368"}},
		map[string]interface{}{"instrument_id": "ltc-usdt", "client_oids": []string{"16ee5933271623682"}},
	}

	r, err := c.PostSpotCancelBatchOrders(&orderInfos)
	require.True(t, r == nil, r)
	require.True(t, err != nil, err)
}

func TestGetSpotOrders(t *testing.T) {
	c := NewTestClient()
	acc := getFirstValidSpotInstrument()
	currency := acc["currency"]

	r, e := c.GetSpotOrders("filled", currency, nil)
	fmt.Printf("%+v %+v \n", r, e)

	sf, e := c.GetSpotFills("fake_order_id", acc["instrument_id"], nil)
	fmt.Printf("%+v %+v \n", sf, e)

	ib, e := c.GetSpotInstrumentBook(acc["instrument_id"], nil)
	fmt.Printf("%+v %+v \n", ib, e)

	//orders, err := c.GetSpotOrders("filled", currency, nil)
	//require.True(t, err == nil, err)
	//jstr, _ := Struct2JsonString(orders)
	//println(jstr)
	//
	//// Fore. 20190305. TODO: {"message":"System error"} returned by following request.
	//// Url: http://coinmainweb.new.docker.okex.com/api/spot/v3/fills?instrument_id=BTC-USDT&order_id=2365709152770048
	//if orders != nil {
	//	pr, err:= LoadPagingResult(orders)
	//	filledOrderId := pr.ResultItems[0]["order_id"]
	//	instId := pr.ResultItems[0]["instrument_id"]
	//
	//	sf, err := c.GetSpotFills(filledOrderId, instId, nil)
	//	require.True(t, sf != nil && err == nil, sf, err)
	//}
}

func TestGetSpotOrdersPending(t *testing.T) {
	c := NewTestClient()
	acc := getFirstValidSpotInstrument()
	instId := acc["instrument_id"]
	ac, err := c.GetSpotOrdersPending(instId, nil)
	assert.True(t, err == nil)
	jstr, _ := Struct2JsonString(ac)
	println(jstr)

	options := NewParams()
	options["instrument_id"] = instId
	ac, err = c.GetSpotOrdersPending(instId, &options)
	assert.True(t, err == nil)
	jstr, _ = Struct2JsonString(ac)
	println(jstr)

	testOrderId := "a_random_id"
	so, err := c.GetSpotOrdersById(instId, testOrderId)
	fmt.Printf("%+v %+v \n", so, err)
}
