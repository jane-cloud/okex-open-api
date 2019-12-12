package okex

import (
	"fmt"
	"github.com/stretchr/testify/assert"
	"github.com/stretchr/testify/require"
	"testing"
)

const (
	InstrumentId = "BTC-USD-181228"
	currency     = "BTC"
)

/*
 OKEX general api's testing
*/
func TestGetServerTime(t *testing.T) {
	serverTime, err := NewTestClient().GetServerTime()
	if err != nil {
		t.Error(err)
	}
	FmtPrintln("OKEX's server time: ", serverTime)
}

func TestGetFuturesExchangeRate(t *testing.T) {
	exchangeRate, err := NewTestClient().GetFuturesExchangeRate()
	if err != nil {
		t.Error(err)
	}
	FmtPrintln("Current exchange rate: ", exchangeRate)
}

/*
 Futures market api's testing
*/
func TestGetFuturesInstruments(t *testing.T) {
	Instruments, err := NewTestClient().GetFuturesInstruments()
	if err != nil {
		t.Error(err)
	}
	FmtPrintln("Futures Instruments: ", Instruments)
}

func TestGetFuturesInstrumentsCurrencies(t *testing.T) {
	currencies, err := NewTestClient().GetFuturesInstrumentCurrencies()
	if err != nil {
		t.Error(err)
	}
	FmtPrintln("Futures Instrument currencies: ", currencies)
}

func TestGetFuturesInstrumentBook(t *testing.T) {
	insId := getValidFutureInstrumentId()
	book, err := NewTestClient().GetFuturesInstrumentBook(insId, nil)
	if err != nil {
		t.Error(err)
	}
	FmtPrintln("Futures Instrument book: ", book)
}

func TestGetFuturesInstrumentBook2(t *testing.T) {
	params := NewParams()
	params["size"] = "10"
	params["depth"] = "0.1"
	insId := getValidFutureInstrumentId()
	r, err := NewTestClient().GetFuturesInstrumentBook(insId, nil)

	simpleAssertTrue(r, err, t, false)
}

func TestGetFuturesInstrumentAllTicker(t *testing.T) {
	tickers, err := NewTestClient().GetFuturesInstrumentAllTicker()
	if err != nil {
		t.Error(err)
	}
	FmtPrintln("Futures Instrument all ticker: ", tickers)
}

func getFirstValidInstrumentId() string {
	tickers, _ := NewTestClient().GetFuturesInstrumentAllTicker()
	t := tickers[0]
	return t.InstrumentId
}

func TestGetFuturesInstrumentTicker(t *testing.T) {
	tId := getFirstValidInstrumentId()
	ticker, err := NewTestClient().GetFuturesInstrumentTicker(tId)
	if err != nil {
		t.Error(err)
	}
	FmtPrintln("Futures Instrument ticker: ", ticker)
}

func TestGetFuturesInstrumentTrades(t *testing.T) {
	instId := getFirstValidInstrumentId()
	trades, err := NewTestClient().GetFuturesInstrumentTrades(instId, nil)
	if err != nil {
		t.Error(err)
	}
	FmtPrintln("Futures Instrument trades: ", trades)
}

func TestGetFuturesInstrumentCandles(t *testing.T) {
	//start := "2018-06-20T02:31:00Z"
	//end := "2018-06-20T02:55:00Z"
	granularity := CANDLES_1MIN

	optional := map[string]string{}
	//optional["start"] = start
	//optional["end"] = end
	optional["granularity"] = Int2String(granularity)

	insId := getValidFutureInstrumentId()

	candles, err := NewTestClient().GetFuturesInstrumentCandles(insId, optional)
	if err != nil {
		t.Error(err)
	}
	fmt.Println("Futures Instrument candles:")
	for i, outLen := 0, len(candles); i < outLen; i++ {
		candle := candles[i]
		for j, inLen := 0, 7; j < inLen; j++ {
			if j == 0 {
				fmt.Print("timestamp:")
				fmt.Print(candle[j])
			} else if j == 1 {
				fmt.Print(" open:")
				fmt.Print(candle[j])
			} else if j == 2 {
				fmt.Print(" high:")
				fmt.Print(candle[j])
			} else if j == 3 {
				fmt.Print(" low:")
				fmt.Print(candle[j])
			} else if j == 4 {
				fmt.Print(" close:")
				fmt.Print(candle[j])
			} else if j == 5 {
				fmt.Print(" volume:")
				fmt.Print(candle[j])
			} else if j == 6 {
				fmt.Print(" currency_volume:")
				fmt.Println(candle[j])
			}
		}
	}
}

func TestGetFuturesInstrumentIndex(t *testing.T) {
	instId := getFirstValidInstrumentId()
	index, err := NewTestClient().GetFuturesInstrumentIndex(instId)
	if err != nil {
		t.Error(err)
	}
	FmtPrintln("Futures Instrument index: ", index)
}

func TestGetFuturesInstrumentEstimatedPrice(t *testing.T) {
	instId := getFirstValidInstrumentId()
	estimatedPrice, err := NewTestClient().GetFuturesInstrumentEstimatedPrice(instId)
	if err != nil {
		t.Error(err)
	}
	FmtPrintln("Futures Instrument estimated price: ", estimatedPrice)
}

func TestGetFuturesInstrumentOpenInterest(t *testing.T) {

	instId := getFirstValidInstrumentId()
	priceLimit, err := NewTestClient().GetFuturesInstrumentOpenInterest(instId)
	if err != nil {
		t.Error(err)
	}
	FmtPrintln("Futures Instrument open interest: ", priceLimit)
}

func TestGetFuturesInstrumentPriceLimit(t *testing.T) {

	instId := getFirstValidInstrumentId()
	priceLimit, err := NewTestClient().GetFuturesInstrumentPriceLimit(instId)
	if err != nil {
		t.Error(err)
	}
	FmtPrintln("Futures Instrument price limit: ", priceLimit)
}

func TestGetFuturesInstrumentLiquidation(t *testing.T) {
	InstrumentIdx := getFirstValidInstrumentId()
	status, from, to, limit := 1, 1, 0, 5
	liquidation, err := NewTestClient().GetFuturesInstrumentLiquidation(InstrumentIdx, status, from, to, limit)
	if err != nil {
		t.Error(err)
	}
	FmtPrintln("Futures Instrument liquidation: ", liquidation)
}

/*
 Futures trade api's testing
*/
func TestGetFuturesPositions(t *testing.T) {
	positions, err := NewTestClient().GetFuturesPositions()
	if err != nil {
		t.Error(err)
	}

	fmt.Printf("postions: %+v\n", positions)
	require.True(t, positions != nil, positions)
	require.True(t, err == nil, err)

	//if *position["m"] == "crossed" {
	//	FmtPrintln("Futures crossed position: ", position)
	//} else if position.MarginMode == "fixed" {
	//	FmtPrintln("Futures fixed position: ", position)
	//} else {
	//	FmtPrintln("Futures position failed: ", position)
	//}
}

func TestGetFuturesInstrumentPosition(t *testing.T) {

	instId := getFirstValidInstrumentId()

	positions, err := NewTestClient().GetFuturesInstrumentPosition(instId)
	fmt.Printf("postions: %+v\n", positions)
	require.True(t, positions != nil, positions)
	require.True(t, err == nil, err)

	//if position.MarginMode == "crossed" {
	//	FmtPrintln("Futures crossed position: ", position)
	//}
	//if position.MarginMode == "fixed" {
	//	FmtPrintln("Futures fixed position: ", position)
	//} else {
	//	FmtPrintln("Futures position failed: ", position)
	//}
}

func TestGetFuturesAccounts(t *testing.T) {
	account, err := NewTestClient().GetFuturesAccounts()

	require.True(t, account != nil, account)
	require.True(t, err == nil, err)

	//if account.MarginMode == "crossed" {
	//	FmtPrintln("Futures crossed account: ", account)
	//} else if account.MarginMode == "fixed" {
	//	FmtPrintln("Futures fixed account: ", account)
	//} else {
	//	FmtPrintln("Futures account failed: ", account)
	//}
}

func TestGetFuturesAccountsByCurrency(t *testing.T) {
	currencyAccounts, err := NewTestClient().GetFuturesAccountsByCurrency(currency)
	if err != nil {
		t.Error(err)
	}
	FmtPrintln("Futures currency accounts: ", currencyAccounts)
}

func TestGetFuturesAccountsLedgerByCurrency(t *testing.T) {
	optionalParams := map[string]string{}
	//optionalParams["before"] = "100000"
	//optionalParams["after"] = "0"
	optionalParams["limit"] = "10"

	ledger, err := NewTestClient().GetFuturesAccountsLedgerByCurrency(currency, optionalParams)
	FmtPrintln("Futures currency ledger: ", ledger)
	require.True(t, ledger != nil, ledger)
	require.True(t, err == nil, err)
}

func TestGetFuturesAccountsHoldsByInstrumentId(t *testing.T) {

	instId := getValidFutureInstrumentId()
	holds, err := NewTestClient().GetFuturesAccountsHoldsByInstrumentId(instId)
	if err != nil {
		t.Error(err)
	}
	FmtPrintln("Futures currency holds: ", holds)
}

func TestFuturesOrder(t *testing.T) {
	clientOid := "od12345678"
	instId := getValidFutureInstrumentId()
	result, err := NewTestClient().GetFuturesOrder(instId, clientOid)
	if err != nil {
		t.Error(err)
	}
	FmtPrintln("Futures new order: ", result)
}

//func TestFuturesOrders(t *testing.T) {
//	var batchNewOrder FuturesBatchNewOrderParams
//	batchNewOrder.InstrumentId = InstrumentId
//	batchNewOrder.Leverage = "20"
//	var ordersData [5]FuturesBatchNewOrderItem
//	for i, loop := 1, 6; i < loop; i++ {
//		var item FuturesBatchNewOrderItem
//		item.ClientOid = "od" + IntToString(12345670+i)
//		item.Type = IntToString(OPEN_SHORT)
//		item.Price = IntToString(100000 + i)
//		item.Size = "1"
//		item.MatchPrice = "0"
//		ordersData[i-1] = item
//	}
//	json, err := Struct2JsonString(ordersData)
//	if err != nil {
//		t.Error(err)
//	}
//	batchNewOrder.OrdersData = json
//	result, err := NewTestClient().FuturesOrders(batchNewOrder)
//	if err != nil {
//		t.Error(err)
//	}
//	FmtPrintln("Futures new orders: ", result)
//}

func TestGetFuturesOrders(t *testing.T) {
	state := "6"
	optionals := map[string]string{}
	//optionals["before"] = "100000"
	//optionals["after"] = "0"
	optionals["limit"] = "10"

	instId := getValidFutureInstrumentId()
	orderList, err := NewTestClient().GetFuturesOrders(instId, state, optionals)
	if err != nil {
		t.Error(err)
	}
	FmtPrintln("Futures Instrument order list: ", orderList)
	require.True(t, orderList != nil, orderList)
	require.True(t, err == nil, err)

}

func TestGetFuturesOrder(t *testing.T) {
	orderId := "1713584667466752"
	instId := getValidFutureInstrumentId()
	order, err := NewTestClient().GetFuturesOrder(instId, orderId)
	if err != nil {
		t.Error(err)
	}
	FmtPrintln("Futures Instrument order: ", order)
}

func TestBatchCancelFuturesInstrumentOrders(t *testing.T) {
	var orderIds [3]int64
	orderIds[0] = 1713484060138496
	orderIds[1] = 1713484060990464
	orderIds[2] = 1713484061907968
	json, err := Struct2JsonString(orderIds)
	if err != nil {
		t.Error(err)
	}

	instId := getValidFutureInstrumentId()
	result, err := NewTestClient().BatchCancelFuturesInstrumentOrders(instId, json)
	if err != nil {
		t.Error(err)
	}
	FmtPrintln("Futures Instrument batch cancel order: ", result)
}

func TestCancelFuturesInstrumentOrder(t *testing.T) {
	orderId := "1713484063611904"
	instId := getValidFutureInstrumentId()

	result, err := NewTestClient().CancelFuturesInstrumentOrder(instId, orderId)
	require.True(t, err == nil, err)
	FmtPrintln("Futures Instrument cancel order: ", result)
}

func TestGetFuturesFills(t *testing.T) {
	orderId := "1713584667466752"
	from, to, limit := "1", "0", "5"
	optionals := map[string]string{}
	optionals["from"] = from
	optionals["to"] = to
	optionals["limit"] = limit

	instId := getValidFutureInstrumentId()

	result, err := NewTestClient().GetFuturesFills(instId, orderId, optionals)
	if err != nil {
		t.Error(err)
	}
	FmtPrintln("Futures Instrument fills: ", result)
}

func getValidFutureInstrumentId() string {
	c := NewTestClient()
	insList, err := c.GetFuturesInstruments()
	if err == nil {
		return insList[0].InstrumentId
	}

	return InstrumentId
}

func TestGetInstrumentMarkPrice(t *testing.T) {
	insId := getValidFutureInstrumentId()
	r, e := NewTestClient().GetInstrumentMarkPrice(insId)
	simpleAssertTrue(r, e, t, false)
	assert.True(t, r.Code == 0)
}

func TestFuturesAccountsLeverage(t *testing.T) {
	c := NewTestClient()
	r, e := c.GetFuturesAccountsLeverage(currency)
	//assert.True(t, r["code"] == nil)
	simpleAssertTrue(r, e, t, true)

	// PostFuturesAccountsLeverage. 设定合约账户币种杠杆倍数，注意当前仓位有持仓或者挂单禁止切换杠杆。
	// lingting.fu. 20190225. Cleanup your test env yourself before running test case.
	//
	// The following 2 cases might fail because of
	// 		a. Not satisfying position or order requirements.
	//		b. Invalid Authority
	// Post C1. Full Position
	//
	// lingting.fu 20190826. 全仓服务端不再支持
	//r, e = c.PostFuturesAccountsLeverage(currency, "10", nil)
	//simpleAssertTrue(r, e, t, false)

	// Post C2. One Position
	params := NewParams()
	params["instrument_id"] = getValidFutureInstrumentId()
	params["direction"] = "long"
	r, e = c.PostFuturesAccountsLeverage(currency, "10", params)
	simpleAssertTrue(r, e, t, false)
}

// lingting.fu@okcoin.com
// The following requests might not success becoz of risk and considered as "Bad Request"
func TestPostFuturesAPI(t *testing.T) {
	c := NewTestClient()
	r, _ := c.PostFutureAccountsLiquiMode("btc", "tier")
	fmt.Printf("%+v \n", r)

	r, _ = c.PostFutureAccountsMarginMode("btc", "crossed")
	fmt.Printf("%+v \n", r)

	validInstId := getValidFutureInstrumentId()
	r, _ = c.PostFuturesOrder(validInstId, "1", "1.1", "1", nil)
	fmt.Printf("%+v \n", r)

	orderDatas := []map[string]string{
		map[string]string{"order_type": "0", "price": "5", "size": "2", "type": "1", "match_price": "1"},
		map[string]string{"order_type": "0", "price": "2", "size": "3", "type": "1", "match_price": "1"},
	}

	r, _ = c.PostFuturesOrders(validInstId, orderDatas, "20", nil)
	fmt.Printf("%+v \n", r)
}
