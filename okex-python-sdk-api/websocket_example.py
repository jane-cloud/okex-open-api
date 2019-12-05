import asyncio
import websockets
import json
import requests
import dateutil.parser as dp
import hmac
import base64
import zlib
import logging
import datetime

log_format = '%(asctime)s - %(levelname)s - %(message)s'
logging.basicConfig(filename='mylog-ws.json', filemode='w', format=log_format, level=logging.INFO)

# logging.warning('warn message')
# logging.info('info message')
# logging.debug('debug message')
# logging.error('error message')
# logging.critical('critical message')


def get_timestamp():
    now = datetime.datetime.now()
    t = now.isoformat("T", "milliseconds")
    return t + "Z"


def get_server_time():
    url = "https://www.okex.com/api/general/v3/time"
    response = requests.get(url)
    if response.status_code == 200:
        return response.json()['iso']
    else:
        return ""


def server_timestamp():
    server_time = get_server_time()
    parsed_t = dp.parse(server_time)
    timestamp = parsed_t.timestamp()
    return timestamp


def login_params(timestamp, api_key, passphrase, secret_key):
    message = timestamp + 'GET' + '/users/self/verify'

    mac = hmac.new(bytes(secret_key, encoding='utf8'), bytes(message, encoding='utf-8'), digestmod='sha256')
    d = mac.digest()
    sign = base64.b64encode(d)

    login_param = {"op": "login", "args": [api_key, passphrase, timestamp, sign.decode("utf-8")]}
    login_str = json.dumps(login_param)
    return login_str


def inflate(data):
    decompress = zlib.decompressobj(
            -zlib.MAX_WBITS  # see above
    )
    inflated = decompress.decompress(data)
    inflated += decompress.flush()
    return inflated


def partial(res, timestamp):
    data_obj = res['data'][0]
    bids = data_obj['bids']
    asks = data_obj['asks']
    print(timestamp + '全量数据bids为：' + str(bids))
    logging.info('partial bids:' + str(bids))
    print('档数为：' + str(len(bids)))
    print(timestamp + '全量数据asks为：' + str(asks))
    logging.info('partial asks:' + str(asks))
    print('档数为：' + str(len(asks)))
    return bids, asks


def update_bids(res, bids_p, timestamp):
    # 获取增量bids数据
    bids_u = res['data'][0]['bids']
    print(timestamp + '增量数据bids为：' + str(bids_u))
    print('档数为：' + str(len(bids_u)))
    # bids合并
    for i in bids_u:
        bid_price = i[0]
        for j in bids_p:
            if bid_price == j[0]:
                if i[1] == '0':
                    bids_p.remove(j)
                    break
                else:
                    del j[1]
                    j.insert(1, i[1])
                    break
        else:
            if i[1] != "0":
                bids_p.append(i)
    else:
        bids_p.sort(key=lambda price: sort_num(price[0]), reverse=True)
        print(timestamp + '合并后的bids为：' + str(bids_p) + '，档数为：' + str(len(bids_p)))
        logging.info('combine bids:' + str(bids_p))
    return bids_p


def update_asks(res, asks_p, timestamp):
    # 获取增量asks数据
    asks_u = res['data'][0]['asks']
    print(timestamp + '增量数据asks为：' + str(asks_u))
    print('档数为：' + str(len(asks_u)))
    # asks合并
    for i in asks_u:
        ask_price = i[0]
        for j in asks_p:
            if ask_price == j[0]:
                if i[1] == '0':
                    asks_p.remove(j)
                    break
                else:
                    del j[1]
                    j.insert(1, i[1])
                    break
        else:
            if i[1] != "0":
                asks_p.append(i)
    else:
        asks_p.sort(key=lambda price: sort_num(price[0]))
        print(timestamp + '合并后的asks为：' + str(asks_p) + '，档数为：' + str(len(asks_p)))
        logging.info('combine asks:' + str(asks_p))
    return asks_p


def sort_num(n):
    if n.isdigit():
        return int(n)
    else:
        return float(n)


def check(bids, asks):
    if len(bids) >= 25 and len(asks) >= 25:
        bids_l = []
        asks_l = []
        for i in range(1, 26):
            bids_l.append(bids[i - 1])
            asks_l.append(asks[i - 1])
        bid_l = []
        ask_l = []
        for j in bids_l:
            str_bid = ':'.join(j[0 : 2])
            bid_l.append(str_bid)
        for k in asks_l:
            str_ask = ':'.join(k[0 : 2])
            ask_l.append(str_ask)
        num = ''
        for m in range(len(bid_l)):
            num += bid_l[m] + ':' + ask_l[m] + ':'
        new_num = num[:-1]
        int_checksum = zlib.crc32(new_num.encode())
        fina = change(int_checksum)
        return fina

    else:
        logging.error("depth data < 25 is error")
        print('深度数据少于25档')


def change(num_old):
    num = pow(2, 31) - 1
    if num_old > num:
        out = num_old - num * 2 - 2
    else:
        out = num_old
    return out


async def ping(url):
    while True:
        try:
            async with websockets.connect(url) as ws:
                await ws.send('ping')
                logging.info('ping')

                res_1 = await ws.recv()
                res = inflate(res_1).decode('utf-8')
                time = get_timestamp()
                print(time + res)
                logging.info(res)
                await asyncio.sleep(25)

        except Exception as e:
            logging.error(e)
            print('connect ws error')
            async with websockets.connect(url) as ws:
                await ws.send('ping')
                logging.info('ping')

                res_1 = await ws.recv()
                res = inflate(res_1).decode('utf-8')
                time = get_timestamp()
                print(time + res)
                logging.info(res)
                await asyncio.sleep(25)


# subscribe channels un_need login
async def subscribe_without_login(url, channels):
    async with websockets.connect(url) as ws:
        sub_param = {"op": "subscribe", "args": channels}
        sub_str = json.dumps(sub_param)
        await ws.send(sub_str)
        logging.info(f"send: {sub_str}")

        while True:
            try:
                res_1 = await ws.recv()
            except websockets.exceptions.ConnectionClosedError as e:
                logging.error(e)
                print("连接异常关闭，正在重连订阅……")
                async with websockets.connect(url) as ws:
                    await ws.send(sub_str)
                    logging.info(f"send: {sub_str}")
                    await asyncio.sleep(5)
                    continue
            except websockets.exceptions.ConnectionClosedOK as e:
                logging.error(e)
                async with websockets.connect(url) as ws:
                    await ws.send(sub_str)
                    logging.info(f"send: {sub_str}")
                    await asyncio.sleep(5)
                    continue
            await asyncio.sleep(2)

            res = inflate(res_1).decode('utf-8')
            logging.info(f"recv: {res}")
            timestamp = get_timestamp()
            print(timestamp + res)
            res = eval(res)
            if 'event' in res:
                continue

            for i in res:
                if 'depth' in res[i] and 'depth5' not in res[i]:
                    # 订阅频道是深度频道
                    if res['action'] == 'partial':
                        # 获取首次全量深度数据
                        bids_p, asks_p = partial(res, timestamp)

                        # 校验checksum
                        checksum = res['data'][0]['checksum']
                        print(timestamp + '推送数据的checksum为：' + str(checksum))
                        logging.info('get checksum:' + str(checksum))
                        check_num = check(bids_p, asks_p)
                        print(timestamp + '校验后的checksum为：' + str(check_num))
                        logging.info('calculate checksum:' + str(check_num))
                        if check_num == checksum:
                            print("校验结果为：True")
                            logging.info('checksum: True')
                        else:
                            print(timestamp + "校验结果为：False，正在重新订阅……")
                            logging.error('checksum: False')
                            # 取消订阅
                            await unsubscribe_without_login(url, channels, timestamp)

                            # 发送订阅
                            async with websockets.connect(url) as ws:
                                sub_param = {"op": "subscribe", "args": channels}
                                sub_str = json.dumps(sub_param)
                                await ws.send(sub_str)
                                print(timestamp + f"send: {sub_str}")
                                logging.info(f"send: {sub_str}")

                    elif res['action'] == 'update':
                        # 获取合并后数据
                        bids_p = update_bids(res, bids_p, timestamp)
                        asks_p = update_asks(res, asks_p, timestamp)

                        # 校验checksum
                        checksum = res['data'][0]['checksum']
                        print(timestamp + '推送数据的checksum为：' + str(checksum))
                        logging.info('get checksum:' + str(checksum))
                        check_num = check(bids_p, asks_p)
                        print(timestamp + '校验后的checksum为：' + str(check_num))
                        logging.info('calculate checksum:' + str(check_num))
                        if check_num == checksum:
                            print("校验结果为：True")
                            logging.info('checksum: True')
                        else:
                            print(timestamp + "校验结果为：False，正在重新订阅……")
                            logging.error('checksum: False')

                            # 取消订阅
                            await unsubscribe_without_login(url, channels, timestamp)
                            # 发送订阅
                            async with websockets.connect(url) as ws:
                                sub_param = {"op": "subscribe", "args": channels}
                                sub_str = json.dumps(sub_param)
                                await ws.send(sub_str)
                                print(timestamp + f"send: {sub_str}")
                                logging.info(f"send: {sub_str}")


# subscribe channels need login
async def subscribe(url, api_key, passphrase, secret_key, channels):
    async with websockets.connect(url) as ws:
        # login
        timestamp = str(server_timestamp())
        login_str = login_params(timestamp, api_key, passphrase, secret_key)
        await ws.send(login_str)
        time = get_timestamp()
        print(time + f"send: {login_str}")
        logging.info(f"send: {login_str}")
        res_1 = await ws.recv()
        res = inflate(res_1).decode('utf-8')
        time = get_timestamp()
        print(time + res)
        logging.info(f"recv: {res}")
        await asyncio.sleep(1)

        # subscribe
        sub_param = {"op": "subscribe", "args": channels}
        sub_str = json.dumps(sub_param)
        await ws.send(sub_str)
        time = get_timestamp()
        print(time + f"send: {sub_str}")
        logging.info(f"send: {sub_str}")

        while True:
            try:
                res_1 = await ws.recv()
            except websockets.exceptions.ConnectionClosedError as e:
                logging.error(e)
                print("连接异常关闭，正在重连登录订阅……")
                async with websockets.connect(url) as ws:
                    # 重新登录
                    timestamp_new = str(server_timestamp())
                    login_str_new = login_params(timestamp_new, api_key, passphrase, secret_key)
                    await ws.send(login_str_new)
                    time = get_timestamp()
                    print(time + login_str_new)
                    logging.info(f"send: {login_str_new}")
                    res_1 = await ws.recv()
                    res = inflate(res_1).decode('utf-8')
                    time = get_timestamp()
                    print(time + res)
                    logging.info(f"recv: {res}")
                    await asyncio.sleep(1)

                    # 重新订阅
                    sub_param = {"op": "subscribe", "args": channels}
                    sub_str = json.dumps(sub_param)
                    await ws.send(sub_str)
                    time = get_timestamp()
                    print(time + f"send: {sub_str}")
                    logging.info(f"send: {sub_str}")

                    await asyncio.sleep(5)
                    continue
            except websockets.exceptions.ConnectionClosedOK as e:
                logging.error(e)
                async with websockets.connect(url) as ws:
                    # 重新登录
                    timestamp_new = str(server_timestamp())
                    login_str_new = login_params(timestamp_new, api_key, passphrase, secret_key)
                    await ws.send(login_str_new)
                    time = get_timestamp()
                    print(time + login_str_new)
                    logging.info(f"send: {login_str_new}")
                    res_1 = await ws.recv()
                    res = inflate(res_1).decode('utf-8')
                    time = get_timestamp()
                    print(time + res)
                    logging.info(f"recv: {res}")
                    await asyncio.sleep(1)

                    # 重新订阅
                    sub_param = {"op": "subscribe", "args": channels}
                    sub_str = json.dumps(sub_param)
                    await ws.send(sub_str)
                    time = get_timestamp()
                    print(time + f"send: {sub_str}")
                    logging.info(f"send: {sub_str}")

                    await asyncio.sleep(5)
                    continue
            await asyncio.sleep(2)

            res = inflate(res_1).decode('utf-8')
            time = get_timestamp()
            print(time + res)
            logging.info(f"recv: {res}")


# unsubscribe channels
async def unsubscribe(url, api_key, passphrase, secret_key, channels):
    async with websockets.connect(url) as ws:
        # login
        timestamp = str(server_timestamp())
        login_str = login_params(str(timestamp), api_key, passphrase, secret_key)
        await ws.send(login_str)
        time = get_timestamp()
        print(time + f"send: {login_str}")
        logging.info(f"send: {login_str}")

        res_1 = await ws.recv()
        res = inflate(res_1).decode('utf-8')
        time = get_timestamp()
        print(time + res)
        logging.info(f"recv: {res}")
        await asyncio.sleep(1)

        # unsubscribe
        sub_param = {"op": "unsubscribe", "args": channels}
        sub_str = json.dumps(sub_param)
        await ws.send(sub_str)
        time = get_timestamp()
        print(time + f"send: {sub_str}")
        logging.info(f"send: {sub_str}")

        res_1 = await ws.recv()
        res = inflate(res_1).decode('utf-8')
        time = get_timestamp()
        print(time + res)
        logging.info(f"recv: {res}")


# unsubscribe channels
async def unsubscribe_without_login(url, channels, timestamp):
    async with websockets.connect(url) as ws:
        # unsubscribe
        sub_param = {"op": "unsubscribe", "args": channels}
        sub_str = json.dumps(sub_param)
        await ws.send(sub_str)
        print(timestamp + f"send: {sub_str}")
        logging.info(f"send: {sub_str}")

        res_1 = await ws.recv()
        res = inflate(res_1).decode('utf-8')
        print(timestamp + f"recv: {res}")
        logging.info(f"recv: {res}")


api_key = ""
seceret_key = ""
passphrase = ""

url = 'wss://real.okex.com:8443/ws/v3'

# 现货
# 用户币币账户频道
# channels = ["spot/account:XRP"]
# 用户杠杆账户频道
# channels = ["spot/margin_account:BTC-USDT"]
# 用户交易频道
# channels = ["spot/order:XRP-USDT"]
# 公共-Ticker频道
# channels = ["spot/ticker:ABL-BTC"]
# 公共-K线频道
# channels = ["spot/candle60s:BTC-USDT"]
# 公共-交易频道
# channels = ["spot/trade:ETH-USDT"]
# 公共-5档深度频道
# channels = [["spot/depth5:ETH-USDT"]]
# 公共-200档深度频道
# channels = ["spot/depth:EOS-BTC"]

# 交割合约
# 用户持仓频道
# channels = ["futures/position:EOS-USD-191227"]
# 用户账户频道
# channels = ["futures/account:BTC-USDT"]
# 用户交易频道
# channels = ["futures/order:TRX-USD-191213"]
# 公共-全量合约信息频道
# channels = ["futures/instruments"]
# 公共-Ticker频道
# channels = ["futures/ticker:ETC-USD-191227"]
# 公共-K线频道
channels = ["futures/candle60s:EOS-USD-191227"]
# 公共-交易频道
# channels = ["futures/trade:BTC-USD-191227"]
# 公共-预估交割价频道
# channels = ["futures/estimated_price:BTC-USD-191227"]
# 公共-限价频道
# channels = ["futures/price_range:BTC-USD-191227"]
# 公共-5档深度频道
# channels = ["futures/depth5:BTC-USD-191129"]
# 公共-400档深度频道
# channels = ["futures/depth:LTC-USD-191213"]
# 公共-全量深度频道
# channels = ["futures/depth_l2_tbt:BTC-USD-191227"]
# 公共-标记价格频道
# channels = ["futures/mark_price:BTC-USD-191227"]

# 永续合约
# 用户持仓频道
# channels = ["swap/position:XRP-USD-SWAP"]
# 用户账户频道
# channels = ["swap/account:BTC-USD-SWAP"]
# 用户交易频道
# channels = ["swap/order:XRP-USD-SWAP"]
# 用户委托策略频道
# channels = ["swap/order_algo:LTC-USDT-SWAP"]
# 公共-Ticker频道
# channels = ["swap/ticker:BTC-USD-SWAP"]
# 公共-K线频道
# channels = ["swap/candle180s:BTC-USD-SWAP"]
# 公共-交易频道
# channels = ["swap/trade:BTC-USD-SWAP"]
# 公共-资金费率频道
# channels = ["swap/funding_rate:BTC-USD-SWAP"]
# 公共-限价频道
# channels = ["swap/price_range:BTC-USD-SWAP"]
# 公共-5档深度频道
# channels = ["swap/depth5:BTC-USD-SWAP"]
# 公共-200档深度频道
# channels = ["swap/depth:BTC-USD-SWAP"]
# 公共-标记价格频道
# channels = ["swap/mark_price:BTC-USD-SWAP"]

# ws公共指数频道
# 指数行情
# channels = ["index/ticker:BTC-USD"]
# 指数K线
# channels = ["index/candle60s:BTC-USD"]

# swap/candle60s // 1分钟k线数据频道
# swap/candle180s // 3分钟k线数据频道
# swap/candle300s // 5分钟k线数据频道
# swap/candle900s // 15分钟k线数据频道
# swap/candle1800s // 30分钟k线数据频道
# swap/candle3600s // 1小时k线数据频道
# swap/candle7200s // 2小时k线数据频道
# swap/candle14400s // 4小时k线数据频道
# swap/candle21600s // 6小时k线数据频道
# swap/candle43200s // 12小时k线数据频道
# swap/candle86400s // 1day k线数据频道
# swap/candle604800s // 1week k线数据频道


#公共数据 不需要登录（行情，K线，交易数据，资金费率，限价范围，深度数据，标记价格）
tasks = [subscribe_without_login(url, channels), ping(url)]

#个人数据 需要登录（用户账户，用户交易，用户持仓）
# tasks = [subscribe(url, api_key, passphrase, seceret_key, channels), ping(url)]


loop = asyncio.get_event_loop()
loop.run_until_complete(asyncio.wait(tasks))
# loop.run_until_complete(subscribe(url, api_key, passphrase, seceret_key, channels))
# loop.run_until_complete(subscribe_without_login(url, channels))

loop.close()
