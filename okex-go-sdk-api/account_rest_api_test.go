package okex

import (
	"fmt"
	"github.com/stretchr/testify/assert"
	"testing"
)

func TestGetAccountCurrencies(t *testing.T) {
	c := NewTestClient()
	ac, err := c.GetAccountCurrencies()

	fmt.Printf("%+v, %+v", ac, err)

	assert.True(t, err == nil)
	jstr, _ := Struct2JsonString(ac)
	println(jstr)
}

func TestGetAccountWallet(t *testing.T) {
	c := NewTestClient()
	ac, err := c.GetAccountWallet()
	assert.True(t, err == nil)
	jstr, _ := Struct2JsonString(ac)
	println(jstr)
}

func TestGetAccountWithdrawalFeeByCurrency(t *testing.T) {
	c := NewTestClient()
	currency := "btc"
	ac, err := c.GetAccountWithdrawalFeeByCurrency(&currency)
	assert.True(t, err == nil)
	jstr, _ := Struct2JsonString(ac)
	println(jstr)

	ac, err = c.GetAccountWithdrawalFeeByCurrency(nil)
	assert.True(t, err == nil)
	jstr, _ = Struct2JsonString(ac)
	println(jstr)
}

func TestGetAccountWithdrawalHistory(t *testing.T) {
	c := NewTestClient()
	ac, err := c.GetAccountWithdrawalHistory()
	assert.True(t, err == nil)
	jstr, _ := Struct2JsonString(ac)
	println(jstr)
}

func TestClient_GetAccountWithdrawalHistoryByCurrency(t *testing.T) {
	c := NewTestClient()
	ac, err := c.GetAccountWithdrawalHistoryByCurrency("btc")
	assert.True(t, err == nil)
	jstr, _ := Struct2JsonString(ac)
	println(jstr)
}

func TestGetAccountDepositAddress(t *testing.T) {
	c := NewTestClient()
	ac, err := c.GetAccountDepositAddress("btc")
	assert.True(t, err == nil)
	jstr, _ := Struct2JsonString(ac)
	println(jstr)
}

func TestGetAccountDepositHistory(t *testing.T) {
	c := NewTestClient()
	ac, err := c.GetAccountDepositHistory()
	assert.True(t, err == nil)
	jstr, _ := Struct2JsonString(ac)
	println(jstr)
}

func TestGetAccountDepositHistoryByCurrency(t *testing.T) {
	c := NewTestClient()
	ac, err := c.GetAccountDepositHistoryByCurrency("btc")
	assert.True(t, err == nil)
	jstr, _ := Struct2JsonString(ac)
	println(jstr)
}

func TestGetAccountLeger(t *testing.T) {
	c := NewTestClient()
	ac, err := c.GetAccountLeger(nil)
	assert.True(t, err == nil)
	jstr, _ := Struct2JsonString(ac)
	println(jstr)

	optionals := NewParams()
	optionals["type"] = "37"

	ac, err = c.GetAccountLeger(&optionals)
	assert.True(t, err == nil)
	jstr, _ = Struct2JsonString(ac)
	println(jstr)

}

func TestGetAccountWalletByCurrency(t *testing.T) {
	c := NewTestClient()
	ac, err := c.GetAccountWalletByCurrency("btc")
	assert.True(t, err == nil)
	jstr, _ := Struct2JsonString(ac)
	println(jstr)
}

func TestPostAccountWithdrawal(t *testing.T) {
	c := NewTestClient()
	ac, err := c.PostAccountWithdrawal("btc", "17DKe3kkkkiiiiTvAKKi2vMPbm1Bz3CMKw", "123456",
		"4", "1", "0.0005")
	assert.True(t, ac != nil && err == nil)
	jstr, _ := Struct2JsonString(ac)
	println(jstr)

}

func TestPostAccountTransfer(t *testing.T) {
	c := NewTestClient()
	options := NewParams()
	options["instrument_id"] = "okb-btc"
	ac, err := c.PostAccountTransfer("okb", "6", "5", "0.0001", &options)
	assert.True(t, ac != nil && err == nil)
	jstr, _ := Struct2JsonString(ac)
	println(jstr)

}
