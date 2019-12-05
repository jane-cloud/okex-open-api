# API参考

---
[TOC]
## AccountAPI

- 获取币种列表
```python
get_currencies(self)
```
- 钱包账户信息
```python
get_wallet(self)
```
- 单一币种账户信息
```python
get_currency(self, symbol)
```
- 提币
```python
coin_withdraw(self, currency, amount, destination, to_address, trade_pwd, fee)
```
- 提币手续费
```python
get_coin_fee(self, symbol='')
```
- 查询最近所有币种的提币记录
```python
get_coins_withdraw_record(self)
```
- 查询单个币种提币记录
```python
get_coin_withdraw_record(self, symbol)
```
- 账单流水查询
```python
get_ledger_record(self, froms, to, limit, currency='', ctype='')
```

- 获取充值地址
```python
get_top_up_address(self, symbol)
```
- 获取所有币种充值记录
```python
get_top_up_records(self)
```
- 获取单个币种充值记录
```python
get_top_up_record(self, symbol)
```
- 资金划转
```python
coin_transfer(self, currency, amount, account_from, account_to, sub_account='', instrument_id='')
```

## SpotAPI
- 币币账户信息
```python
get_account_info(self)
```
- 单一币种账户信息
```python
get_coin_account_info(self, symbol)
```
- 账单流水查询
```python
get_ledger(self, symbol)
```

- 下单
```python
take_order(self, otype, side, instrument_id, size, client_oid='', price='', funds=''):
```
- 批量下单
```python
take_batch_ordres(self, client_oid, instrument_id, side, ctype, size, price)
```

- 撤销指定订单
```python
revoke_order(self, oid, instrument_id)
```
- 批量撤销订单
```python
revoke_orders(self, instrument_id, order_ids)
```
- 获取订单列表
```python
get_orders_list(self, status, instrument_id, froms, to, limit)
```

- 获取所有未成交订单

```python
get_orders_pending(self, froms, to, limit)
```

- 获取订单信息
```python
get_order_info(self, oid, instrument_id)
```
- 获取成交明细
```python
get_fills(self, order_id, instrument_id, froms, to, limit)
```

- 获取币对信息
```python
get_coin_info(self)
```
- 获取深度数据
```python
get_depth(self, instrument_id, size, depth)
```
- 获取全部ticker信息
```python
get_ticker(self)
```
- 获取某个ticker信息
```python
get_specific_ticker(self, instrument_id)
```
- 获取成交数据
```python
get_deal(self, instrument_id, froms, to, limit)
```

- 获取K线数据
```python
get_kline(self, instrument_id, start, end, granularity)
```

## FutureAPI
- 合约持仓信息
```python
get_position(self)
```
- 单个合约持仓信息
```python
get_specific_position(self, instrument_id)
```
- 合约账户信息
```python
get_accounts(self)
```
- 某个币种合约账户信息
```python
get_coin_account(self, symbol)
```
- 获取合约币种杠杆倍数
```python
get_leverage(self, symbol)
```
- 设定合约币种杠杆倍数
```python
def set_leverage(self, symbol, instrument_id='', direction='', leverage=10)
```
- 账单流水查询
```python
get_ledger(self, symbol)
```
- 平掉所有仓位
```python
revoke_position(self, position_data)
```
- 下单
```python
take_order(self, client_oid, instrument_id, otype, price, size, match_price, leverage):
```
- 批量下单
```python
take_orders(self, instrument_id, order_data, leverage)
```
- 撤销指定订单
```python
revoke_order(self, order_id)
```
- 批量撤销订单
```python
revoke_orders(self, instrument_id)
```
- 获取订单列表
```python
get_order_list(self, status, froms, to, limit, instrument_id='')
```

- 获取订单信息
```python
get_order_info(self, order_id, instrument_id)
```
- 获取成交明细
```python
get_fills(self, order_id, instrument_id, froms, to, limit)
```

- 获取合约信息
```python
get_products(self)
```
- 获取深度数据
```python
get_depth(self, instrument_id, size)
```
- 获取全部ticker信息
```python
get_ticker(self)
```
- 获取某个ticker信息
```python
get_specific_ticker(self, instrument_id)
```
- 获取成交数据(新版)
```python
get_trades(self, instrument_id, froms, to, limit):
```

- 获取K线数据
```python
get_kline(self, instrument_id, granularity, start='', end='')
```
- 获取指数信息
```python
get_index(self, instrument_id)
```
- 获取法币汇率
```python
get_rate(self)
```
- 获取预估交割价
```python
get_estimated_price(self, instrument_id)
```
- 获取平台总持仓量
```python
get_holds(self, instrument_id):
```
- 获取当前限价
```python
get_limit(self, instrument_id)
```
- 获取爆仓单
```python
get_liquidation(self, instrument_id)
```

* 获取合约挂单冻结数量

```python
get_holds_amount(self, instrument_id)
```

## LeverAPI

- 币币杠杆账户信息
```python
get_account_info(self)
```
- 单一币对账户信息
```python
get_specific_account(self, instrument_id)
```
- 账单流水查询
```python
get_ledger_record(self, instrument_id, froms, to, limit)
```

- 杠杆配置信息
```python
get_config_info(self)
```
- 某个杠杆配置信息
```python
get_specific_config_info(self, instrument_id)
```
- 获取借币记录
```python
get_borrow_coin(self, status, froms, to, limit)
```

- 某账户借币记录
```python
get_specific_borrow_coin(self, instrument_id, status, froms, to, limit)
```

- 借币
```python
borrow_coin(self, instrument_id, currency, amount)
```
- 还币
```python
repayment_coin(self, borrow_id, instrument_id, currency, amount)
```
- 下单
```python
take_order(self, instrument_id, otype, side, size, client_oid='', price='', margin_trading='')
```
- 批量下单
```python
take_batch_ordres(self, client_oid, instrument_id, side, ctype, size, price)
```

- 撤销指定订单
```python
revoke_order(self, oid)
```
- 批量撤销订单
```python
revoke_orders(self, instrument_id)
```
- 获取订单列表
```python
get_order_list(self, status, froms, to, limit, instrument_id)
```

- 获取所有未成交订单
```python
get_order_pending(self, froms, to, limit):
```

- 获取订单信息
```python
get_order_info(self, oid)
```
* 获取成交明细

```python
def get_fills(self, order_id, instrument_id, froms, to, limit)
```

## ETTAPI

- 组合账户信息
```python
get_accounts(self)
```
- 单一币种账户信息
```python
get_account(self, symbol)
```
- 账单流水查询
```python
get_ledger(self, symbol)
```
- 下单
```python
take_order(self, otype, quoto_currency, amount, size, ett, client_oid='')
```
- 撤销指定订单
```python
revoke_order(self, order_id)
```
- 获取订单列表(新版本)
```python
get_order_list(self, status, ett, otype, froms, to, limit)
```

- 获取订单信息
```python
get_specific_order(self, order_id)
```
- 获取组合成分
```python
get_constituents(self, ett)
```
- 获取ETT清算历史定价
```python
get_define_price(self, ett)
```

## 永续合约API 

* 单个合约持仓信息

```
get_specific_position(self, instrument_id)
```

* 所有币种合约账户信息

```python
get_accounts(self)
```

* 单个币种合约账户信息

```python
get_coin_account(self, instrument_id)
```

* 获取某个合约的用户配置

```python
get_settings(self, instrument_id)
```

* 设定某个合约的杠杆

```python
set_leverage(self, instrument_id, leverage, side)
```

* 账单流水查询

```python
get_ledger(self, instrument_id, froms, to, limit)
```

* 下单

```python
take_order(self, client_oid, size, type, match_price, price, instrument_id)
```

* 批量下单

```python
take_orders(self, order_data, instrument_id)
```

* 撤单

```python
revoke_order(self, order_id, instrument_id)
```

* 批量撤单

```python
revoke_orders(self, ids, instrument_id)
```

* 获取所有订单列表

```python
get_order_list(self, status, instrument_id, froms, to, limit)
```

* 获取订单信息

```python
get_order_info(self, order_id, instrument_id)
```

* 获取成交明细

```python
get_fills(self, order_id, instrument_id, froms, to, limit)
```

* 获取合约信息

```python
get_instruments(self)
```

* 获取深度数据

```python
get_depth(self, instrument_id, size)
```

* 获取全部ticker信息

```python
get_ticker(self)
```

* 获取某个ticker信息

```python
get_specific_ticker(self, instrument_id)
```

* 获取成交数据

```python
get_trades(self, instrument_id, froms, to, limit)
```

* 获取K线数据

```python
get_kline(self, instrument_id, granularity, start='', end='')
```

* 获取指数信息

```python
get_index(self, instrument_id)
```

* 获取法币汇率

```python
get_rate(self)
```

* 获取平台总持仓量

```python
get_holds(self, instrument_id)
```

* 获取当前限价

```python
get_limit(self, instrument_id)
```

* 获取爆仓单

```python
get_liquidation(self, instrument_id)
```

* 获取合约挂单冻结数量

```python
get_holds_amount(self, instrument_id)
```

* 获取合约下一次结算时间

```python
get_funding_time(self, instrument_id)
```

* 获取合约标记价格

```python
get_mark_price(self, instrument_id)
```

* 获取合约历史资金费率

```python
get_historical_funding_rate(self, instrument_id, froms, to, limit)
```

## 永续合约websocket

由于使用到了websockets库，因此对python的版本有要求，Python ≥ 3.6。

* 订阅频道不需要登陆

```python
def subscribe_without_login(url, channels)
```

```python
# swap/ticker // 行情数据频道
# swap/candle60s // 1分钟k线数据频道
# swap/candle180s // 3分钟k线数据频道
# swap/candle300s // 5分钟k线数据频道
# swap/candle900s // 15分钟k线数据频道
# swap/candle1800s // 30分钟k线数据频道
# swap/candle3600s // 1小时k线数据频道
# swap/candle7200s // 2小时k线数据频道
# swap/candle14400s // 4小时k线数据频道
# swap/candle21600 // 6小时k线数据频道
# swap/candle43200s // 12小时k线数据频道
# swap/candle86400s // 1day k线数据频道
# swap/candle604800s // 1week k线数据频道
# swap/trade // 交易信息频道
# swap/funding_rate//资金费率频道
# swap/price_range//限价范围频道
# swap/depth //深度数据频道，首次200档，后续增量
# swap/depth5 //深度数据频道，每次返回前5档
# swap/mark_price// 标记价格频道
```

* 订阅需要登陆的频道

```python
def subscribe(url, api_key, passphrase, secret_key, channels)
```

```python
# swap/account //用户账户信息频道
# swap/position //用户持仓信息频道
# swap/order //用户交易数据频道
```

* 取消订阅

```python
def unsubscribe(url, api_key, passphrase, secret_key, channels)
```

关于python永续合约websocket的使用demo见websocket_example.py。