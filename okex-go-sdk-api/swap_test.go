package okex

/*
 OKEX Swap Rest Api. Unit test
 @author Lingting Fu
 @date 2018-12-27
 @version 1.0.0
*/

import (
	"fmt"
	"github.com/stretchr/testify/assert"
	"github.com/stretchr/testify/require"
	"os"
	"reflect"
	"testing"
)

func TestGetSwapInstrumentPosition(t *testing.T) {
	c := NewTestClient()
	sp, err := c.GetSwapPositionByInstrument("BTC-USD-SWAP")
	assert.True(t, err == nil)
	jstr, _ := Struct2JsonString(sp)
	println(jstr)
}

func TestGetSwapPositions(t *testing.T) {
	c := NewTestClient()
	sp, err := c.GetSwapPositions()
	simpleAssertTrue(sp, err, t, false)
}

func TestGetSwapAccount(t *testing.T) {
	c := NewTestClient()
	sa, err := c.GetSwapAccount("BTC-USD-SWAP")
	simpleAssertTrue(sa, err, t, false)
}

type JsonTestType struct {
	Asks [][]interface{} `json:"asks"`
}

func TestJson(t *testing.T) {
	a := string(`{"asks" : [["411.5","9",4,3]]}`)
	i := JsonTestType{}
	JsonString2Struct(a, &i)
	simpleAssertTrue(i, nil, t, false)

	str, _ := Struct2JsonString(i)
	println(str)
}

func TestMap(t *testing.T) {
	m := map[string]string{}
	m["a"] = "1999"

	r := m["b"]
	assert.True(t, r == "" && len(r) == 0)

	r2 := m["a"]
	assert.True(t, r2 == "1999")
}

func TestClient_PublicAPI(t *testing.T) {
	c := NewTestClient()
	instrumentId := "BTC-USD-SWAP"
	histList, err := c.GetSwapHistoricalFundingRateByInstrument(instrumentId, nil)
	fmt.Printf("%+v", err)
	assert.True(t, histList != nil && err == nil)
	fmt.Printf("%+v", histList)

	r, err := c.GetSwapMarkPriceByInstrument(instrumentId)
	fmt.Printf("Result: %+v, Error: %+v", r, err)
	assert.True(t, r != nil && err == nil)

	r1, err := c.GetSwapFundingTimeByInstrument(instrumentId)
	fmt.Printf("Result: %+v, Error: %+v", r1, err)
	assert.True(t, r1 != nil && err == nil)

	r2, err := c.GetSwapAccountsHoldsByInstrument(instrumentId)
	fmt.Printf("Result: %+v, Error: %+v", r2, err)
	assert.True(t, r2 != nil && err == nil)

	r3, err := c.GetSwapLiquidationByInstrument(instrumentId, "1", nil)
	fmt.Printf("Result: %+v, Error: %+v", r3, err)
	assert.True(t, r3 != nil && err == nil)

	optionalParams := map[string]string{}
	optionalParams["from"] = "1"
	optionalParams["to"] = "5"
	optionalParams["limit"] = "50"

	r4, err := c.GetSwapLiquidationByInstrument(instrumentId, "0", optionalParams)
	fmt.Printf("Result: %+v, Error: %+v", r4, err)
	assert.True(t, r4 != nil && err == nil)

	r5, err := c.GetSwapPriceLimitByInstrument(instrumentId)
	fmt.Printf("Result: %+v, Error: %+v", r5, err)
	assert.True(t, r5 != nil && err == nil)

	r6, err := c.GetSwapOpenInterestByInstrument(instrumentId)
	fmt.Printf("Result: %+v, Error: %+v", r6, err)
	assert.True(t, r6 != nil && err == nil)

	r7, err := c.GetSwapIndexByInstrument(instrumentId)
	fmt.Printf("Result: %+v, Error: %+v", r7, err)
	assert.True(t, r7 != nil && err == nil)

	//lingting.fu. 20190225. No Kline in test enviroment, contact LiWei to solve env problem.
	//r8, err := c.GetSwapCandlesByInstrument(instrumentId, nil)
	//fmt.Printf("Result: %+v, Error: %+v", r8, err)
	//assert.True(t, r8 != nil && err == nil)

	r9, err := c.GetSwapTradesByInstrument(instrumentId, nil)
	fmt.Printf("Result: %+v, Error: %+v", r9, err)
	assert.True(t, r9 != nil && err == nil)

	r10, err := c.GetSwapTickerByInstrument(instrumentId)
	fmt.Printf("Result: %+v, Error: %+v", r10, err)
	assert.True(t, r10 != nil && err == nil)

	r11, err := c.GetSwapInstruments()
	fmt.Printf("Result: %+v, Error: %+v", r11, err)
	assert.True(t, r11 != nil && err == nil)

	r12, err := c.GetSwapRate()
	fmt.Printf("Result: %+v, Error: %+v", r12, err)
	assert.True(t, r12 != nil && err == nil)

	r13, err := c.GetSwapInstrumentsTicker()
	simpleAssertTrue(r13, err, t, false)

	r14, err := c.GetSwapDepthByInstrumentId(instrumentId, "1")
	simpleAssertTrue(r14, err, t, false)

}

func simpleAssertTrue(result interface{}, err error, t *testing.T, doprint bool) bool {
	if doprint {
		fmt.Fprintf(os.Stderr, "Result: %+v, Error: %+v", result, err)
	}
	require.True(t, result != nil, result)
	require.True(t, err == nil, err)
	return result != nil && err == nil
}

func TestClient_PrivateAPI(t *testing.T) {

	c := NewTestClient()
	instrumentId := "BTC-USD-SWAP"

	// Fore. 20190225. CleanUp history test order before new test start.
	cleanUpOrders(c, instrumentId)

	r1, err := c.GetSwapPositionByInstrument(instrumentId)
	simpleAssertTrue(r1, err, t, false)

	r2, err := c.GetSwapAccounts()
	simpleAssertTrue(r2, err, t, false)

	r3, err := c.GetSwapAccountsSettingsByInstrument(instrumentId)
	simpleAssertTrue(r3, err, t, false)

	r4, err := c.PostSwapAccountsLeverage(instrumentId, "20", "3")
	simpleAssertTrue(r4, err, t, false)

	r5, err := c.PostSwapAccountsLeverage(instrumentId, "50", "3")
	simpleAssertTrue(r5, err, t, false)
	//require.True(t, int(r5.Code) > 30000, r5)

	r6, err := c.GetSwapAccountLedger(instrumentId, nil)
	simpleAssertTrue(r6, err, t, false)

	order := BasePlaceOrderInfo{}
	order.Size = "1"
	order.Type = "1"
	order.MatchPrice = "1"
	order.Price = "100"
	r7, err := c.PostSwapOrder(instrumentId, &order)
	fmt.Printf("%+v, %+v\n", r7, err)
	//simpleAssertTrue(r7, err, t, false)
	order2 := BasePlaceOrderInfo{}
	order2.Size = "1"
	order2.Type = "1"
	order2.MatchPrice = "1"
	order2.Price = "200"
	r8, err := c.PostSwapOrders(instrumentId, []*BasePlaceOrderInfo{&order, &order2})
	fmt.Printf("%+v, %+v\n", r8, err)
	//simpleAssertTrue(r8, err, t, false)

	r81, err := c.GetSwapOrderByOrderId(instrumentId, r8.OrderInfo[0].OrderId)
	fmt.Printf("%+v, %+v\n", r81, err)
	//simpleAssertTrue(r81, err, t, false)

	orderId := r8.OrderInfo[0].OrderId
	r9, err := c.PostSwapCancelOrder(instrumentId, orderId)
	fmt.Printf("%+v, %+v\n", r9, err)
	//simpleAssertTrue(r9, err, t, false)

	ids := []string{r8.OrderInfo[0].OrderId, r8.OrderInfo[1].OrderId}
	r10, err := c.PostSwapBatchCancelOrders(instrumentId, ids)
	fmt.Printf("%+v, %+v\n", r10, err)
	//simpleAssertTrue(r10, err, t, false)

	params := map[string]string{}
	params["status"] = "1"
	params["from"] = "1"
	params["to"] = "4"
	params["limit"] = "100"
	r11, err := c.GetSwapOrderByInstrumentId(instrumentId, "7", params)
	fmt.Printf("%+v, %+v\n", r11, err)
	//simpleAssertTrue(r11, err, t, false)

	r12, err := c.GetSwapFills(instrumentId, orderId, nil)
	fmt.Printf("%+v, %+v\n", r12, err)
	//simpleAssertTrue(r12, err, t, false)
}

func cleanUpOrders(c *Client, instrumentId string) {
	params := NewParams()
	currentPage := 1
	params["status"] = "6"
	params["limit"] = "100"
	params["from"] = Int2String(currentPage)

	// Fore. 20190826
	//orders := []string{}
	//
	//rNotDealed, _ := c.GetSwapOrderByInstrumentId(instrumentId, "7", params)
	//for rNotDealed != nil && len(*rNotDealed) > 0 {
	//	for i := 0; i < len(*rNotDealed); i++ {
	//		if rNotDealed.OrderInfo[i].OrderId != "" && len(rNotDealed.OrderInfo[i].OrderId) > 0 {
	//			orders = append(orders, rNotDealed.OrderInfo[i].OrderId)
	//		}
	//	}
	//
	//	delta := 10
	//	for i := 0; i < len(orders); i = i + delta {
	//		upper := i + delta
	//		if upper > len(orders)-1 {
	//			upper = len(orders) - 1
	//		}
	//		c.PostSwapBatchCancelOrders(instrumentId, orders[i:upper])
	//		time.Sleep(time.Millisecond * 200)
	//		println(i, i+delta)
	//	}
	//
	//	currentPage += 1
	//	params["from"] = Int2String(currentPage)
	//
	//	rNotDealed, _ = c.GetSwapOrderByInstrumentId(instrumentId, "7", params)
	//}
}

func TestClient_Err(t *testing.T) {

	c := NewTestClient()
	c.Config.Endpoint = "http://192.168.80.113:930/"

	r1, err := c.GetSwapRate()
	assert.True(t, r1 == nil && err != nil)
}

func TestClient_SwapHistoricalFundingRate(t *testing.T) {
	s := `[
  {
    "instrument_id": "BTC-USD-SWAP",
    "funding_rate": "0.00250000",
    "realized_rate": "0.00249999",
    "interest_rate": "0.00000000",
    "funding_time": "2018-12-17T12:40:26.000Z"
  }
]`
	r := SwapHistoricalFundingRateList{}
	JsonString2Struct(s, &r)
	assert.True(t, r != nil)
	assert.True(t, len(r) >= 1)
}

func TestClient_reflect(t *testing.T) {
	i := BasePlaceOrderInfo{}
	tp := reflect.TypeOf(i)
	fmt.Println(tp)
}
