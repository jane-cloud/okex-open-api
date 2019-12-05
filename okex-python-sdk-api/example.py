import okex.account_api as account
import okex.futures_api as future
import okex.lever_api as lever
import okex.spot_api as spot
import okex.swap_api as swap
import okex.index_api as index
import json
import logging
import datetime

log_format = '%(asctime)s - %(levelname)s - %(message)s'
logging.basicConfig(filename='mylog-rest.json', filemode='a', format=log_format, level=logging.INFO)

# logging.warning('warn message')
# logging.info('info message')
# logging.debug('debug message')
# logging.error('error message')
# logging.critical('critical message')


def get_timestamp():
    now = datetime.datetime.now()
    t = now.isoformat("T", "milliseconds")
    return t + "Z"


time = get_timestamp()

if __name__ == '__main__':

    api_key = ""
    seceret_key = ""
    passphrase = ""

    # 资金账户API
    # account api test
    # param use_server_time's value is False if is True will use server timestamp
    accountAPI = account.AccountAPI(api_key, seceret_key, passphrase, True)
    # 获取资金账户信息 （20次/2s）
    # result = accountAPI.get_wallet()
    # 获取单一币种账户信息 （20次/2s）
    # result = accountAPI.get_currency('xrp')
    # 资金划转  1次/2s（每个币种）
    # result = accountAPI.coin_transfer('btc', 0.01, '1', '3')
    # 提币 （20次/2s）
    # result = accountAPI.coin_withdraw('XRP', 1, 4, '17DKe3kkkkiiiiTvAKKi2vMPbm1Bz3CMKw', "123456", 0.0005)
    # 账单流水查询 （可查询最近一个月）（20次/2s）
    # result = accountAPI.get_ledger_record('okb')
    # 获取充值地址 （20次/2s）
    # result = accountAPI.get_top_up_address('xrp')
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
    # 查询提币手续费 （20次/2s）
    # result = accountAPI.get_coin_fee('EOS')

    # print(time + json.dumps(result))
    # logging.info("result:" + json.dumps(result))

# 币币API
    # spot api test
    spotAPI = spot.SpotAPI(api_key, seceret_key, passphrase, True)
    # 获取币币账户信息 （20次/2s）
    # result = spotAPI.get_account_info()
    # 获取单一币种账户信息 （20次/2s）
    # result = spotAPI.get_coin_account_info('okb')
    # 账单流水查询 最近3个月 （最近3个月的数据）（20次/2s）
    # result = spotAPI.get_ledger_record('XRP')
    # 下单 （100次/2s）
    # result = spotAPI.take_order('xrp-usdt', 'buy', client_oid='', type='limit', price='0.2266', size='1')

    # take orders
    # params = [
    #   {"instrument_id": "XRP-USDT", "side": "sell", "type": "market", "size": "1"},
    #   {"instrument_id": "XRP-USDT", "side": "buy", "type": "market", "notional": "0.3"}
    # ]
    # 批量下单 （每次只能下最多4个币对且每个币对可批量下10个单）（50次/2s）
    # result = spotAPI.take_orders(params)
    # 撤消指定订单 （100次/2s）
    # result = spotAPI.revoke_order('XRP-USDT', order_id='3926884573645824')

    # revoke orders
    # 批量撤消订单 （每次只能下最多4个币对且每个币对可批量下10个单）（50次/2s）
    # params = [
    #     {'instrument_id': 'xrp-usdt', 'order_ids': ['3956994307262464']
    #      }
    # ]
    # result = spotAPI.revoke_orders(params)
    # 获取订单列表 （最近3个月的订单信息）（20次/2s）
    # result = spotAPI.get_orders_list('XRP-USDT', '0')
    # 获取所有未成交订单 （20次/2s）
    # result = spotAPI.get_orders_pending('XRP-USDT')
    # 获取订单信息 （最近3个月的订单信息）（已撤销的未成交单只保留2个小时）（20次/2s）
    # result = spotAPI.get_order_info('XRP-USDT', 3937857243189248)
    # 获取成交明细 （最近3个月的数据）（20次/2s）
    # result = spotAPI.get_fills('btc-usdt')
    # 委托策略下单 （40次/2s）
    # result = spotAPI.take_order_algo('XRP-USDT', '1', '1', '1', 'buy', trigger_price='0.2893', algo_price='0.2894')
    # 委托策略撤单 （每次最多可撤6（冰山/时间）/10（计划/跟踪）个）（20 次/2s）
    # result = spotAPI.cancel_algos('XRP-USDT', ['377553'], '1')
    # 获取委托单列表 （20次/2s）
    # result = spotAPI.get_order_algos('XRP-USDT', '1', status='3')
    # 公共-获取币对信息 （20次/2s）
    # result = spotAPI.get_coin_info()
    # 公共-获取深度数据 （20次/2s）
    # result = spotAPI.get_depth('XRP-USDT')
    # 公共-获取全部ticker信息 （50次/2s）
    # result = spotAPI.get_ticker()
    # 公共-获取某个ticker信息 （20次/2s）
    # result = spotAPI.get_specific_ticker('ETH-USDT')
    # 公共-获取成交数据 （最近60条数据）（20次/2s）
    # result = spotAPI.get_deal('XRP-USDT')
    # 公共-获取K线数据（最多可获取最近2000条）（20次/2s）
    # result = spotAPI.get_kline('XRP-USDT', 60)
    # print(len(result))

    # print(time + json.dumps(result))
    # logging.info("result:" + json.dumps(result))

# 币币杠杆API
    # level api test
    levelAPI = lever.LeverAPI(api_key, seceret_key, passphrase, True)
    # 币币杠杆账户信息 （20次/2s）
    # result = levelAPI.get_account_info()
    # 单一币对账户信息 （20次/2s）
    # result = levelAPI.get_specific_account('XRP-USDT')
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
    # result = levelAPI.borrow_coin('BTC-USDT', 'usdt', '0.1')
    # 还币 （100次/2s）
    # result = levelAPI.repayment_coin('BTC-USDT', 'usdt', '0.1')
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

    # print(time + json.dumps(result))
    # logging.info("result:" + json.dumps(result))

# 交割合约API
    # future api test
    futureAPI = future.FutureAPI(api_key, seceret_key, passphrase, True)
    # 所有合约持仓信息 （5次/2s）（根据userid限速）
    # result = futureAPI.get_position()
    # 单个合约持仓信息 （20次/2s）（根据underlying，分别限速）
    # result = futureAPI.get_specific_position('XRP-USD-191227')
    # 所有币种合约账户信息 （1次/10s）（根据userid限速）
    # result = futureAPI.get_accounts()
    # 单个币种合约账户信息（币本位保证金合约的传参值为BTC-USD，USDT保证金合约的传参值为BTC-USDT）（20次/2s）（根据underlying，分别限速）
    # result = futureAPI.get_coin_account('XRP-USD')
    # 获取合约币种杠杆倍数 （5次/2s）（根据underlying，分别限速）
    # result = futureAPI.get_leverage('BTC-USDT')
    # 设定合约币种杠杆倍数 （5次/2s）（根据underlying，分别限速）
    # 全仓
    # result = futureAPI.set_leverage('XRP-USD', '10')
    # 逐仓
    # result = futureAPI.set_leverage('XRP-USD', '10', 'XRP-USD-191227', 'long')
    # 账单流水查询 （最近2天的数据）（5次/2s）（根据underlying，分别限速）
    # result = futureAPI.get_ledger('XRP-USD')
    # 下单 （40次/2s）（根据underlying，分别限速）
    # result = futureAPI.take_order('XRP-USD-191227', '2', '0.2219', '1', match_price='0')

    # take orders
    # 批量下单 （每个合约可批量下10个单）（20次/2s）（根据underlying，分别限速）
    # orders = [
    #           {"type": "1", "price": "0.2750", "size": "1"},
    #           {"type": "2", "price": "0.2760", "size": "1"}
    #           ]
    # orders_data = json.dumps(orders)
    # result = futureAPI.take_orders('XRP-USD-191227', orders_data=orders_data)
    # 撤销指定订单 （40次/2s）（根据underlying，分别限速）
    # result = futureAPI.revoke_order('XRP-USD-191227', '3933278497446913')
    # 批量撤销订单 （每次最多可撤10个单）（20 次/2s）（根据underlying，分别限速）
    # result = futureAPI.revoke_orders('XRP-USD-191227', order_ids=["3853889302246401", "3853889302246403"])
    # 获取订单列表 （最近7天的数据）（20 次/2s）（根据underlying，分别限速）
    # result = futureAPI.get_order_list('XRP-USD-191227', '0')
    # 获取订单信息 （已撤销的未成交单只保留2个小时）（40次/2s）（根据underlying，分别限速）
    # result = futureAPI.get_order_info('XRP-USD-191227', '3926536385152001')
    # 获取成交明细 （最近7天的数据）（20 次/2s）（根据underlying，分别限速）
    # result = futureAPI.get_fills('XRP-USD-191227')
    # 设置合约币种账户模式 （5次/2s）（根据underlying，分别限速）
    # result = futureAPI.set_margin_mode('XRP-USD', 'crossed')
    # 市价全平 （5次/2s）（根据underlying，分别限速）
    # result = futureAPI.close_position('XRP-USD-191227', 'long')
    # 撤销所有平仓挂单 （5次/2s）（根据underlying，分别限速）
    # result = futureAPI.cancel_all('XRP-USD-191227', 'long')
    # 获取合约挂单冻结数量 （5次/2s）（根据underlying，分别限速）
    # result = futureAPI.get_holds_amount('XRP-USD-191227')
    # 委托策略下单 （40次/2s）（根据underlying，分别限速）
    # result = futureAPI.take_order_algo('XRP-USD-191227', '3', '1', '1', trigger_price='0.2094', algo_price='0.2092')
    # 委托策略撤单 （每次最多可撤6（冰山/时间）/10（计划/跟踪）个）（20 次/2s）（根据underlying，分别限速）
    # result = futureAPI.cancel_algos('XRP-USD-191227', ['1907026'], '1')
    # 获取委托单列表 （20次/2s）（根据underlying，分别限速）
    # result = futureAPI.get_order_algos('XRP-USD-191227', '1', status='1')
    # 公共-获取合约信息 （20次/2s）（根据ip限速）
    # result = futureAPI.get_products()
    # 公共-获取深度数据 （20次/2s）（根据underlying，分别限速）
    # result = futureAPI.get_depth('XRP-USD-191227')
    # 公共-获取全部ticker信息 （20次/2s）（根据ip限速）
    # result = futureAPI.get_ticker()
    # 公共-获取某个ticker信息 （20次/2s）（根据underlying，分别限速）
    # result = futureAPI.get_specific_ticker('XRP-USD-191227')
    # 公共-获取成交数据 （最新300条成交列表）（20次/2s）（根据underlying，分别限速）
    # result = futureAPI.get_trades('XRP-USD-191227')
    # 公共-获取K线数据 （最多可获取最近1440条）（20次/2s）（根据underlying，分别限速）
    # result = futureAPI.get_kline('BTC-USD-191227', '60')
    # print(len(result))
    # 公共-获取指数信息 （20次/2s）（根据ip限速）
    # result = futureAPI.get_index('XRP-USD-191227')
    # 公共-获取法币汇率 （20次/2s）（根据ip限速）
    # result = futureAPI.get_rate()
    # 公共-获取预估交割价 （交割预估价只有交割前一小时才有返回值）（20次/2s）（根据underlying，分别限速）
    # result = futureAPI.get_estimated_price('XRP-USD-191227')
    # 公共-获取平台总持仓量 （20次/2s）（根据underlying，分别限速）
    # result = futureAPI.get_holds('XRP-USD-191227')
    # 公共-获取当前限价 （20次/2s）（根据underlying，分别限速）
    # result = futureAPI.get_limit('XRP-USD-191227')
    # 公共-获取标记价格 （20次/2s）（根据underlying，分别限速）
    # result = futureAPI.get_mark_price('XRP-USD-191227')
    # 公共-获取强平单 （20次/2s）（根据underlying，分别限速）
    # result = futureAPI.get_liquidation('XRP-USD-191227', 1)

    # print(time + json.dumps(result))
    # logging.info("result:" + json.dumps(result))

# 永续合约API
    # swap api test
    swapAPI = swap.SwapAPI(api_key, seceret_key, passphrase, True)
    # 所有合约持仓信息 （1次/10s）
    # result = swapAPI.get_position()
    # 单个合约持仓信息 （20次/2s）
    # result = swapAPI.get_specific_position('XRP-USD-SWAP')
    # 所有币种合约账户信息 （当用户没有持仓时，保证金率为10000）（1次/10s）
    # result = swapAPI.get_accounts()
    # 单个币种合约账户信息 （当用户没有持仓时，保证金率为10000）（20次/2s）
    # result = swapAPI.get_coin_account('XRP-USD-SWAP')
    # 获取某个合约的用户配置 （5次/2s）
    # result = swapAPI.get_settings('XRP-USD-SWAP')
    # 设定某个合约的杠杆 （5次/2s）
    # result = swapAPI.set_leverage('XRP-USD-SWAP', 10, 1)
    # 账单流水查询 （流水会分页，每页100条数据，并且按照时间倒序排序和存储，最新的排在最前面）（可查询最近7天的数据）（5次/2s）
    # result = swapAPI.get_ledger('XRP-USD-SWAP')
    # 下单 （40次/2s）
    # result = swapAPI.take_order('XRP-USD-SWAP', '3', '1', '0.2608', match_price='1')
    # 批量下单 （每个合约可批量下10个单）（20次/2s）
    # result = swapAPI.take_orders('XRP-USD-SWAP', [
    #         {"type": "1", "price": "0.2600", "size": "1"},
    #         {"type": "2", "price": "0.2608", "size": "1"}
    #     ])
    # 撤单 （40次/2s）
    # result = swapAPI.revoke_order('XRP-USD-SWAP', '369874940524453888')
    # 批量撤单 （每个币对可批量撤10个单）（20次/2s）
    # result = swapAPI.revoke_orders('XRP-USD-SWAP', ids=["3698749403525888", "369874940532842496"])
    # 获取所有订单列表 （可查询最近7天20000条数据，支持分页，分页返回结果最大为100条）（20次/2s）
    # result = swapAPI.get_order_list('XRP-USD-SWAP', '0')
    # 获取订单信息 （只能查询最近3个月的已成交和已撤销订单信息，已撤销的未成交单只保留2个小时）（40次/2s）
    # result = swapAPI.get_order_info('XRP-USD-SWAP', '369856427972321280')
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
    # result = swapAPI.get_depth('BTC-USD-SWAP', '3', '1')
    # 公共-获取全部ticker信息 （20次/2s）
    # result = swapAPI.get_ticker()
    # 公共-获取某个ticker信息 （20次/2s）
    # result = swapAPI.get_specific_ticker('XRP-USD-SWAP')
    # 公共-获取成交数据 （能查询最近300条数据）（20次/2s）
    # result = swapAPI.get_trades('XRP-USD-SWAP')
    # 公共-获取K线数据 （最多可获取最近1440条）（20次/2s）
    # result = swapAPI.get_kline('XRP-USD-SWAP', '2019-09-29T07:59:45.977Z', '2019-09-29T16:59:45.977Z', 60)
    # 公共-获取指数信息 （20次/2s）
    # result = swapAPI.get_index('XRP-USD-SWAP')
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

# 指数API
    # index api test
    indexAPI = index.IndexAPI(api_key, seceret_key, passphrase, True)
    # 公共-获取指数成分 （20次/2s）
    # result = indexAPI.get_index_constituents('BTC-USD')


    print(time + json.dumps(result))
    logging.info("result:" + json.dumps(result))
