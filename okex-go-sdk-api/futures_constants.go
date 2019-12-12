package okex

/*
 Futures constants
 @author Tony Tian
 @date 2018-03-18
 @version 1.0.0
*/

const (
	/*
	 currencies
	*/
	BTC = 0
	LTC = 1
	ETH = 2
	ETC = 4
	BCH = 5
	XRP = 15
	EOS = 20
	BTG = 10
	/*
		transaction type
	*/
	OPEN_LONG   = 1
	OPEN_SHORT  = 2
	CLOSE_LONG  = 3
	CLOSE_SHORT = 4

	/*
	 margin mode
	*/
	CROSS = 1
	FIXED = 2

	/*
	 candles bin size
	*/
	CANDLES_1MIN   = 60
	CANDLES_3MIN   = 180
	CANDLES_5MIN   = 300
	CANDLES_15MIN  = 900
	CANDLES_30MIN  = 1800
	CANDLES_1HOUR  = 3600
	CANDLES_2HOUR  = 7200
	CANDLES_4HOUR  = 14400
	CANDLES_6HOUR  = 21600
	CANDLES_12HOUR = 43200
	CANDLES_1DAY   = 86400
	CANDLES_1WEEK  = 604800
)
