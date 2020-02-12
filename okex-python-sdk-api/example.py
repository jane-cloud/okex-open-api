import okex.account_api as account
import okex.futures_api as future
import okex.lever_api as lever
import okex.spot_api as spot
import okex.swap_api as swap
import okex.index_api as index
import okex.option_api as option
import json
import logging
import datetime

log_format = '%(asctime)s - %(levelname)s - %(message)s'
logging.basicConfig(filename='mylog-rest.json', filemode='a', format=log_format, level=logging.INFO)


def get_timestamp():
    now = datetime.datetime.now()
    t = now.isoformat("T", "milliseconds")
    return t + "Z"


time = get_timestamp()

if __name__ == '__main__':

    api_key = ""
    secret_key = ""
    passphrase = ""

# param use_server_time's value is False if is True will use server timestamp

# account api test
# 资金账户API
    accountAPI = account.AccountAPI(api_key, secret_key, passphrase, False)
    # 资金账户信息 （20次/2s）
    # result = accountAPI.get_wallet()
    # 单一币种账户信息 （20次/2s）
    # result = accountAPI.get_currency('usdt')
    # 资金划转  (1次/2s)（每个币种）
    # result = accountAPI.coin_transfer('ltc', '0.1', '3', '9', instrument_id='', to_instrument_id='')
    # 提币 （20次/2s）
    # result = accountAPI.coin_withdraw('XRP', 1, 4, '', "", 0.0005)
    # 账单流水查询 （可查询最近一个月）（20次/2s）
    # result = accountAPI.get_ledger_record()
    # 获取充值地址 （20次/2s）
    # result = accountAPI.get_top_up_address('usdt')
    # 获取账户资产估值 （1次/20s）
    # result = accountAPI.get_asset_valuation()
    # 获取子账户余额信息 （1次/20s）
    # result = accountAPI.get_sub_account('')
    # 查询所有币种的提币记录 （最近100条记录）（20次/2s）
    # result = accountAPI.get_coins_withdraw_record()
    # 查询单个币种提币记录 （20次/2s）
    # result = accountAPI.get_coin_withdraw_record('xrp')
    # 获取所有币种充值记录 （最近100条数据）（20次/2s）
    # result = accountAPI.get_top_up_records()
    # 获取单个币种充值记录 （最近100条数据）（20次/2s）
    # result = accountAPI.get_top_up_record('OKB')
    # 获取币种列表 （20次/2s）
    # result = accountAPI.get_currencies()
    # 提币手续费 （20次/2s）
    # result = accountAPI.get_coin_fee('usdt')

    # print(time + json.dumps(result))
    # logging.info("result:" + json.dumps(result))

# spot api test
# 币币API
    spotAPI = spot.SpotAPI(api_key, secret_key, passphrase, False)
    # 币币账户信息 （20次/2s）
    # result = spotAPI.get_account_info()
    # 单一币种账户信息 （20次/2s）
    # result = spotAPI.get_coin_account_info('usdt')
    # 账单流水查询 （最近3个月的数据）（20次/2s）
    # result = spotAPI.get_ledger_record('usdt')
    # 下单 （100次/2s）
    # result = spotAPI.take_order('xrp_usdt', 'buy', client_oid='', type='market', price='0.2717', order_type='0', notional='1', size='')

    # take orders
    # 批量下单 （每次只能下最多4个币对且每个币对可批量下10个单）（50次/2s）
    # params = [
    #   {"instrument_id": "XRP-USDT", "side": "buy", "type": "limit", "price": "0.2717", "size": "1"},
    #   {"instrument_id": "XRP-USDT", "side": "buy", "type": "market", "price": "2.5451", "notional": "1"}
    # ]
    # result = spotAPI.take_orders(params)
    # 撤消指定订单 （100次/2s）
    # result = spotAPI.revoke_order('XRP-USDT', order_id='4351604261981184')

    # revoke orders
    # 批量撤消订单 （每次只能下最多4个币对且每个币对可批量下10个单）（50次/2s）
    # params = [
    #     {'instrument_id': 'xrp-usdt', 'order_ids': ['4351631930908672','4351631930908673']}
    # ]
    # result = spotAPI.revoke_orders(params)
    # 获取订单列表 （最近3个月的订单信息）（20次/2s）
    # result = spotAPI.get_orders_list('XRP-USDT', '0')
    # 获取所有未成交订单 （20次/2s）
    # result = spotAPI.get_orders_pending('btc-usdt')
    # 获取订单信息 （最近3个月的订单信息）（已撤销的未成交单只保留2个小时）（20次/2s）
    # result = spotAPI.get_order_info('XRP-USDT', 4226325433617408)
    # 获取成交明细 （最近3个月的数据）（20次/2s）
    # result = spotAPI.get_fills('xrp-usdt')
    # 委托策略下单 （40次/2s）
    # result = spotAPI.take_order_algo('XRP-USDT', '1', '1', '1', 'buy', trigger_price='0.2347', algo_price='0.2348')
    # 委托策略撤单 （每次最多可撤6（冰山/时间）/10（计划/跟踪）个）（20 次/2s）
    # result = spotAPI.cancel_algos('XRP-USDT', ['484795'], '1')
    # 获取当前账户费率 （1次/10s）
    # result = spotAPI.get_trade_fee()
    # 获取委托单列表 （20次/2s）
    # result = spotAPI.get_order_algos('XRP-USDT', '1', status='1')
    # 公共-获取币对信息 （20次/2s）
    # result = spotAPI.get_coin_info()
    # 公共-获取深度数据 （20次/2s）
    # result = spotAPI.get_depth('BSV-USDT', depth='')
    # 公共-获取全部ticker信息 （50次/2s）
    # result = spotAPI.get_ticker()
    # 公共-获取某个ticker信息 （20次/2s）
    # result = spotAPI.get_specific_ticker('ETH-USDT')
    # 公共-获取成交数据 （最近60条数据）（20次/2s）
    # result = spotAPI.get_deal('XRP-USDT', limit='')
    # 公共-获取K线数据（最多可获取最近2000条）（20次/2s）
    # result = spotAPI.get_kline('XRP-USDT', 60)
    # print(len(result))

    # print(time + json.dumps(result))
    # logging.info("result:" + json.dumps(result))

# level api test
# 币币杠杆API
    levelAPI = lever.LeverAPI(api_key, secret_key, passphrase, False)
    # 币币杠杆账户信息 （20次/2s）
    # result = levelAPI.get_account_info()
    # 单一币对账户信息 （20次/2s）
    # result = levelAPI.get_specific_account('btc-USDT')
    # 账单流水查询 （最近3个月的数据）（20次/2s）
    # result = levelAPI.get_ledger_record('XRP-USDT')
    # 杠杆配置信息 （20次/2s）
    # result = levelAPI.get_config_info()
    # 某个杠杆配置信息 （20次/2s）
    # result = levelAPI.get_specific_config_info('XRP-USDT')
    # 获取借币记录 （20次/2s）
    # result = levelAPI.get_borrow_coin()
    # 某币对借币记录 （20次/2s）
    # result = levelAPI.get_specific_borrow_coin('BTC-USDT')
    # 借币 （100次/2s）
    # result = levelAPI.borrow_coin('BTC-USDT', 'btc', '0.1')
    # 还币 （100次/2s）
    # result = levelAPI.repayment_coin('BTC-USDT', 'btc', '0.1')
    # 下单 （100次/2s）
    # result = levelAPI.take_order('xrp-usdt', 'buy', '2', price='0.2806', size='2')

    # take orders
    # params = [
    #   {"instrument_id": "xrp-usdt", "side": "buy", "type": "market", "notional": "2", "margin_trading": "2"},
    #   {"instrument_id": "xrp-usdt", "side": "sell", 'price': '0.2806', "size": "5", "margin_trading": "2"}
    # ]
    # 批量下单 （每次只能下最多4个币对且每个币对可批量下10个单）（50次/2s）
    # result = levelAPI.take_orders(params)
    # 撤销指定订单 （100次/2s）
    # result = levelAPI.revoke_order('xrp-usdt', order_id='')

    # revoke orders
    # params = [
    #   {"instrument_id": "xrp-usdt", "order_ids": ['23464', '23465']},
    #   {"instrument_id": "xrp-usdt", "client_oids": ['243464', '234465']}
    # ]
    # 批量撤销订单 （每个币对可批量撤10个单）（50次/2s）
    # result = levelAPI.revoke_orders(params)

    # 获取订单列表 （最近100条订单信息）（20次/2s）
    # result = levelAPI.get_order_list('xrp-usdt', state='0')
    # 获取订单信息 （已撤销的未成交单只保留2个小时）（20次/2s）
    # result = levelAPI.get_order_info('xrp-usdt', client_oid='2244927451729920')
    # 获取所有未成交订单 （20次/2s）
    # result = levelAPI.get_order_pending('xrp-usdt')
    # 获取成交明细 （最近3个月的数据）（20次/2s）
    # result = levelAPI.get_fills('XRP-USDT')
    # 获取杠杆倍数 （5次/2s）
    # result = levelAPI.get_leverage('BTC-USDT')
    # 设置杠杆倍数 （5次/2s）
    # result = levelAPI.set_leverage('BTC-USDT', '7')
    # 公共-获取标记价格 （20次/2s）
    # result = levelAPI.get_mark_price('BTC-USDT')

    # print(time + json.dumps(result))
    # logging.info("result:" + json.dumps(result))

# future api test
# 交割合约API
    futureAPI = future.FutureAPI(api_key, secret_key, passphrase, False)
    # 所有合约持仓信息 （5次/2s）（根据userid限速）
    # result = futureAPI.get_position()
    # 单个合约持仓信息 （20次/2s）（根据underlying，分别限速）
    # result = futureAPI.get_specific_position('BTC-USD-200327')
    # 所有币种合约账户信息 （1次/10s）（根据userid限速）
    # result = futureAPI.get_accounts()
    # 单个币种合约账户信息（币本位保证金合约的传参值为BTC-USD，USDT保证金合约的传参值为BTC-USDT）（20次/2s）（根据underlying，分别限速）
    # result = futureAPI.get_coin_account('EOS-USD')
    # 获取合约币种杠杆倍数 （5次/2s）（根据underlying，分别限速）
    # result = futureAPI.get_leverage('xrp-usd')
    # 设定合约币种杠杆倍数 （5次/2s）（根据underlying，分别限速）
    # 全仓
    # result = futureAPI.set_leverage('XRP-USD', '20')
    # 逐仓
    # result = futureAPI.set_leverage('XRP-USD', '15', 'XRP-USD-200327', 'long')
    # 账单流水查询 （最近2天的数据）（5次/2s）（根据underlying，分别限速）
    # result = futureAPI.get_ledger('XRP-USD', type='1')
    # 下单 （40次/2s）（根据underlying，分别限速）
    # result = futureAPI.take_order('XRP-USD-200327', '1', '9450', '1', order_type='0', client_oid='', match_price='1')

    # take orders
    # 批量下单 （每个合约可批量下10个单）（20次/2s）（根据underlying，分别限速）
    # result = futureAPI.take_orders('XRP-USD-200327', [
    #           {"type": "1", "price": "0.2798", "size": "1"},
    #           {"type": "2", "price": "0.2931", "size": "1"}
    # ])
    # 撤销指定订单 （40次/2s）（根据underlying，分别限速）
    # result = futureAPI.revoke_order('BTC-USD-200327', '4335683159004161')
    # 批量撤销订单 （每次最多可撤10个单）（20 次/2s）（根据underlying，分别限速）
    # result = futureAPI.revoke_orders('XRP-USD-200327', order_ids=["4363690542012417", "4363690542077953"])
    # 获取订单列表 （最近3个月的数据，挂单可一直拿到）（20 次/2s）（根据underlying，分别限速）
    # result = futureAPI.get_order_list('XRP-USD-200327', '2')
    # 获取订单信息 （已撤销的未成交单只保留2个小时）（40次/2s）（根据underlying，分别限速）
    # result = futureAPI.get_order_info('BTC-USD-200327', '4335683159004161')
    # 获取成交明细 （最近7天的数据）（20 次/2s）（根据underlying，分别限速）
    # result = futureAPI.get_fills('XRP-USD-200327', after='4096402419785729')
    # 设置合约币种账户模式 （5次/2s）（根据underlying，分别限速）
    # result = futureAPI.set_margin_mode('LTC-USD', 'crossed')
    # 市价全平 （5次/2s）（根据underlying，分别限速）
    # result = futureAPI.close_position('XRP-USD-200327', 'long')
    # 撤销所有平仓挂单 （5次/2s）（根据underlying，分别限速）
    # result = futureAPI.cancel_all('BTC-USD-200327', 'long')
    # 获取合约挂单冻结数量 （5次/2s）（根据underlying，分别限速）
    # result = futureAPI.get_holds_amount('XRP-USD-200327')
    # 委托策略下单 （40次/2s）（根据underlying，分别限速）
    # result = futureAPI.take_order_algo('XRP-USD-200327', '1', '1', '1', trigger_price='0.2094', algo_price='0.2092')
    # 委托策略撤单 （每次最多可撤6（冰山/时间）/10（计划/跟踪）个）（20 次/2s）（根据underlying，分别限速）
    # result = futureAPI.cancel_algos('XRP-USD-200327', ['1907026'], '1')
    # 获取委托单列表 （20次/2s）（根据underlying，分别限速）
    # result = futureAPI.get_order_algos('XRP-USD-200327', '1', status='2')
    # 获取当前手续费费率 （1次/10s）
    # result = futureAPI.get_trade_fee()
    # 公共-获取合约信息 （20次/2s）（根据ip限速）
    # result = futureAPI.get_products()
    # 公共-获取深度数据 （20次/2s）（根据underlying，分别限速）
    # result = futureAPI.get_depth('BTC-USD-200327')
    # 公共-获取全部ticker信息 （20次/2s）（根据ip限速）
    # result = futureAPI.get_ticker()
    # 公共-获取某个ticker信息 （20次/2s）（根据underlying，分别限速）
    # result = futureAPI.get_specific_ticker('XRP-USD-200327')
    # 公共-获取成交数据 （最新300条成交列表）（20次/2s）（根据underlying，分别限速）
    # result = futureAPI.get_trades('BTC-USD-200327')
    # 公共-获取K线数据 （最多可获取最近1440条）（20次/2s）（根据underlying，分别限速）
    # result = futureAPI.get_kline('BTC-USD-200327', '86400')
    # print(len(result))
    # 公共-获取指数信息 （20次/2s）（根据ip限速）
    # result = futureAPI.get_index('XRP-USD-200327')
    # 公共-获取法币汇率 （20次/2s）（根据ip限速）
    # result = futureAPI.get_rate()
    # 公共-获取预估交割价 （交割预估价只有交割前一小时才有返回值）（20次/2s）（根据underlying，分别限速）
    # result = futureAPI.get_estimated_price('XRP-USD-200327')
    # 公共-获取平台总持仓量 （20次/2s）（根据underlying，分别限速）
    # result = futureAPI.get_holds('XRP-USD-200327')
    # 公共-获取当前限价 （20次/2s）（根据underlying，分别限速）
    # result = futureAPI.get_limit('XRP-USD-200327')
    # 公共-获取标记价格 （20次/2s）（根据underlying，分别限速）
    # result = futureAPI.get_mark_price('XRP-USD-200327')
    # 公共-获取强平单 （20次/2s）（根据underlying，分别限速）
    # result = futureAPI.get_liquidation('XRP-USD-200327', 1)

    # print(time + json.dumps(result))
    # logging.info("result:" + json.dumps(result))

# swap api test
# 永续合约API
    swapAPI = swap.SwapAPI(api_key, secret_key, passphrase, False)
    # 所有合约持仓信息 （1次/10s）
    # result = swapAPI.get_position()
    # 单个合约持仓信息 （20次/2s）
    # result = swapAPI.get_specific_position('XRP-USD-SWAP')
    # 所有币种合约账户信息 （当用户没有持仓时，保证金率为10000）（1次/10s）
    # result = swapAPI.get_accounts()
    # 单个币种合约账户信息 （当用户没有持仓时，保证金率为10000）（20次/2s）
    # result = swapAPI.get_coin_account('LTC-USD-SWAP')
    # 获取某个合约的用户配置 （5次/2s）
    # result = swapAPI.get_settings('XRP-USD-SWAP')
    # 设定某个合约的杠杆 （5次/2s）
    # result = swapAPI.set_leverage('XRP-USD-SWAP', 10, 3)
    # 账单流水查询 （流水会分页，每页100条数据，并且按照时间倒序排序和存储，最新的排在最前面）（可查询最近7天的数据）（5次/2s）
    # result = swapAPI.get_ledger('BTC-USD-SWAP')
    # 下单 （40次/2s）
    # result = swapAPI.take_order('EOS-USD-SWAP', '1', '4.206', '1', client_oid="", match_price='0')
    # 批量下单 （每个合约可批量下10个单）（20次/2s）
    # result = swapAPI.take_orders('XRP-USD-SWAP', [
    #         {"client_oid": "a1211", "type": "1", "price": "0.1911", "size": "1"},
    #         {"client_oid": "b2323", "type": "1", "price": "0.1912", "size": "1"}
    #     ])
    # 撤单 （40次/2s）
    # result = swapAPI.revoke_order('XRP-USD-SWAP', '413371152692895744')
    # 批量撤单 （每个币对可批量撤10个单）（20次/2s）
    # result = swapAPI.revoke_orders('XRP-USD-SWAP', client_oids=['a1211', 'b2323'])
    # 获取所有订单列表 （可查询最近7天20000条数据，挂单可一直拿到，支持分页，分页返回结果最大为100条）（20次/2s）
    # result = swapAPI.get_order_list('XRP-USD-SWAP', '0')
    # 获取订单信息 （只能查询最近3个月的已成交和已撤销订单信息，已撤销的未成交单只保留2个小时）（40次/2s）
    # result = swapAPI.get_order_info('BTC-USD-SWAP', '2')
    # 获取成交明细 （能查询最近7天的数据）（20次/2s）
    # result = swapAPI.get_fills('XRP-USD-SWAP')
    # 获取合约挂单冻结数量 （5次/2s）
    # result = swapAPI.get_holds_amount('XRP-USD-SWAP')
    # 委托策略下单 （40次/2s）
    # result = swapAPI.take_order_algo('XRP-USD-SWAP', '1', '1', '1', trigger_price='0.2640', algo_price='0.2641')
    # 委托策略撤单 （每次最多可撤6（冰山/时间）/10（计划/跟踪）个）（20 次/2s）
    # result = swapAPI.cancel_algos('XRP-USD-SWAP', ['367645536490315776'], '1')
    # 获取委托单列表 （20次/2s）
    # result = swapAPI.get_order_algos('XRP-USD-SWAP', '1', algo_id='', status='2')
    # 获取账户手续费费率 （母账户下的子账户的费率和母账户一致。每天凌晨0点更新一次）（1次/10s）
    # result = swapAPI.get_trade_fee()
    # 公共-获取合约信息 （20次/2s）
    # result = swapAPI.get_instruments()
    # 公共-获取深度数据 （20次/2s）
    # result = swapAPI.get_depth('BTC-USD-SWAP', '30')
    # 公共-获取全部ticker信息 （20次/2s）
    # result = swapAPI.get_ticker()
    # 公共-获取某个ticker信息 （20次/2s）
    # result = swapAPI.get_specific_ticker('BTC-USD-SWAP')
    # 公共-获取成交数据 （能查询最近300条数据）（20次/2s）
    # result = swapAPI.get_trades('XRP-USD-SWAP')
    # 公共-获取K线数据 （最多可获取最近1440条）（20次/2s）
    # result = swapAPI.get_kline('BTC-USDT-SWAP', '1800')
    # print(len(result))
    # 公共-获取指数信息 （20次/2s）
    # result = swapAPI.get_index('BTC-USD-SWAP')
    # 公共-获取法币汇率 （20次/2s）
    # result = swapAPI.get_rate()
    # 公共-获取平台总持仓量 （20次/2s）
    # result = swapAPI.get_holds('XRP-USD-SWAP')
    # 公共-获取当前限价 （20次/2s）
    # result = swapAPI.get_limit('XRP-USD-SWAP')
    # 公共-获取强平单 （20次/2s）
    # result = swapAPI.get_liquidation('XRP-USD-SWAP', '1')
    # 公共-获取合约资金费率 （20次/2s）
    # result = swapAPI.get_funding_time('XRP-USD-SWAP')
    # 公共-获取合约标记价格 （20次/2s）
    # result = swapAPI.get_mark_price('XRP-USD-SWAP')
    # 公共-获取合约历史资金费率 （能查询最近7天的数据）（20次/2s）
    # result = swapAPI.get_historical_funding_rate('XRP-USD-SWAP')

    # print(time + json.dumps(result))
    # logging.info("result:" + json.dumps(result))

# option api test
# 期权合约API
    optionAPI = option.OptionAPI(api_key, secret_key, passphrase, False)
    # 单个标的指数持仓信息 （20次/2s）
    # result = optionAPI.get_specific_position('BTC-USD')
    # 单个标的物账户信息 （20次/2s）
    # result = optionAPI.get_underlying_account('BTC-USD')
    # 下单 （40次/2s）
    # result = optionAPI.take_order('BTC-USD-200327-11000-C', 'buy', '0.0430', '1', match_price='0')
    # 批量下单 （每个标的指数最多可批量下10个单）（20次/2s）
    # result = optionAPI.take_orders('BTC-USD', [
    #         {"instrument_id": "BTC-USD-200327-11000-C", "side": "buy", "price": "0.1835", "size": "1", "order_type": "0", "match_price": "0"},
    #         {"instrument_id": "BTC-USD-200327-11000-C", "side": "buy", "price": "0.0126", "size": "1", "order_type": "0", "match_price": "0"}
    #     ])
    # 撤单 （40次/2s）
    # result = optionAPI.revoke_order('BTC-USD', order_id='127776879599992832')
    # 批量撤单 （每个标的指数最多可批量撤10个单）（20次/2s）
    # result = optionAPI.revoke_orders('BTC-USD', order_ids=["155801767097874432", "155803099442657280"])
    # 修改订单 （每个标的指数最多可批量修改10个单）（40次/2s）
    # result = optionAPI.amend_order('BTC-USD', order_id='155801767097874432', new_price='0.0311', new_size='5')
    # 批量修改订单 （20次/2s）
    # result = optionAPI.amend_batch_orders('BTC-USD', [
    #         {"order_id": "oktoption12", "new_size": "2"},
    #         {"client_oid": "oktoption14", "request_id": "okoptionBTCUSDmod002", "new_size": "1"}
    #     ])
    # 获取单个订单状态 （已撤销的未成交单只保留2个小时）（40次/2s）
    # result = optionAPI.get_order_info('BTC-USD', order_id='136849175983734784')
    # 获取订单列表 （可查询7天内数据）（20次/2s）
    # result = optionAPI.get_order_list('BTC-USD', '2')
    # 获取成交明细 （可查询7天内数据）（20次/2s）
    # result = optionAPI.get_fills('BTC-USD')
    # 获取账单流水 （可查询7天内数据）（5次/2s）
    # result = optionAPI.get_ledger('BTC-USD')
    # 获取手续费费率 （1次/10s）
    # result = optionAPI.get_trade_fee()
    # 公共-获取标的指数 （20次/2s）
    # result = optionAPI.get_index()
    # 公共-获取期权合约 （20次/2s）
    # result = optionAPI.get_instruments('BTC-USD')
    # 公共-获取期权合约详细定价 （20次/2s）
    # result = optionAPI.get_instruments_summary('BTC-USD')
    # 公共-获取单个期权合约详细定价 （20次/2s）
    # result = optionAPI.get_option_instruments_summary('BTC-USD', 'BTC-USD-200327-11000-C')
    # 公共-获取深度数据 （20次/2s）
    # result = optionAPI.get_depth('BTC-USD-200327-11000-C')
    # 公共-获取成交数据 （20次/2s）
    # result = optionAPI.get_trades('BTC-USD-200327-11000-C')
    # 公共-获取某个Ticker信息 （20次/2s）
    # result = optionAPI.get_specific_ticker('BTC-USD-200327-11000-C')
    # 公共-获取K线数据 （20次/2s）
    # result = optionAPI.get_kline('BTC-USD-200327-11000-C')

# index api test
# 指数API
    indexAPI = index.IndexAPI(api_key, secret_key, passphrase, False)
    # 公共-获取指数成分 （20次/2s）
    # result = indexAPI.get_index_constituents('BTC-USD')

    print(time + json.dumps(result))
    logging.info("result:" + json.dumps(result))
