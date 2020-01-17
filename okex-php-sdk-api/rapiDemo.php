<?php
/**
 * Created by PhpStorm.
 * User: hengliu
 * Date: 2019/5/7
 * Time: 2:59 PM
 */

require './vendor/autoload.php';

require './Config.php';

use okv3\AccountApi;
use okv3\Config;
use okv3\FuturesApi;
use okv3\MarginApi;
use okv3\OptionsApi;
use okv3\SpotApi;
use okv3\SwapApi;
use Workerman\Connection\AsyncTcpConnection;
use Workerman\Worker;

/**
 * 资金账户
 */
$obj = new AccountApi(Config::$config);
$coin = "XMR";
// 资金账户信息，多个币种
//$res = $obj -> getWalletInfo();
// 单一币种账户信息
//$res = $obj -> getSpecialWalletInfo($coin);
// 资金划转
//$res = $obj -> transfer($coin,"0.1","6","1","","");
// 提币
//$res = $obj -> withdrawal($coin,"1","4","eostoliuheng:OKEx","123456","0.1");
// 账单流水
//$res = $obj -> getLeger("EOS");
// 获取充值地址
//$res = $obj -> getDepositAddress($coin);
// 查询所有币种的提币记录
//$res = $obj -> getWithdrawalHistory();
// 查询单个币种的提币记录
//$res = $obj -> getCoinWithdrawalHistory($coin);
// 获取所有币种的充值记录
//$res = $obj -> getDepositHistory();
// 查询单个币种的充值记录
//$res = $obj -> getCoinDepositHistory($coin);
// 获取币种列表
//$res = $obj -> getCurrencies();
// 提币手续费
//$res = $obj -> getWithdrawalFee($coin);

/**
 * 币币
 */
$instrumentId = "EOS-USDT";
$currency = "EOS";
$obj = new SpotApi(Config::$config);
// 币币账户信息
//$res = $obj -> getAccountInfo();
// 单一币种账户信息
//$res = $obj -> getCoinAccountInfo($currency);
// 账单流水查询
//$res = $obj -> getLedgerRecord($currency);
// 下单
//$res = $obj -> takeOrder($instrumentId,"buy","0.1","2");
// 撤销指定订单
//$res = $obj -> revokeOrder($instrumentId,"3452612358987776");
// 获取订单列表
//$res = $obj -> getOrdersList($instrumentId,"2","","",1);
// 获取订单信息
//$res = $obj -> getOrderInfo($instrumentId,"3271189018971137");
// 获取成交明细
$res = $obj -> getFills($instrumentId,"3230072570268672");
// 获取币对信息
//$res = $obj -> getCoinInfo();
// 获取深度数据
//$res = $obj -> getDepth($instrumentId,1);
// 获取全部ticker信息
//$res = $obj -> getTicker();
// 获取某个ticker信息
//$res = $obj -> getSpecificTicker($instrumentId);
// 获取成交数据
//$res = $obj -> getDeal($instrumentId);
// 获取K线
//$res = $obj -> getKine($instrumentId,3600);
// 策略委托下单-止盈止损- mode为1是币币， mode为1是币币杠杆，
//$res = $obj -> takeAlgoOrderStop($instrumentId,"1","1", "1", "buy", "1","1");
// 委托策略撤单-止盈止损
//$res = $obj -> revokeAlgoOrders($instrumentId,["401671"], "1");
// 获取委托单列表-止盈止损
//$res = $obj -> getAlgoList($instrumentId, "1", '','401671','','','');

// 币币杠杆账户信息
$instrumentId = "EOS-USDT";
$currency = "EOS";
$obj = new MarginApi(Config::$config);
//$res = $obj -> getAccountInfo();
// 单一币种账户信息
//$res = $obj -> getCoinAccountInfo($instrumentId);
// 账单流水查询
//$res = $obj -> getLedgerRecord($instrumentId);
// 杠杆配置信息
//$res = $obj -> getMarginConf();
// 某个杠杆配置信息
//$res = $obj -> getMarginSpecialConf($instrumentId);
// 获取借币记录
//$res = $obj -> getBorrowedRecord();
// 某账户借币记录
//$res = $obj -> getSpecialBorrowedRecord($instrumentId,0);
// 借币
//$res = $obj -> borrowCoin($instrumentId, $currency, 0.1);
// 还币
//$res = $obj -> returnCoin($instrumentId, $currency, 0.1, "");
// 下单
//$res = $obj -> takeOrder($instrumentId,"sell","0.1","2","10");
// 撤销指定订单
//$res = $obj -> revokeOrder($instrumentId,"3292706588398592");
// 获取订单列表
//$res = $obj -> getOrdersList($instrumentId,"-1","","",1);
// 获取订单信息
//$res = $obj -> getOrderInfo($instrumentId,"3292706588398592");
// 获取成交明细
//$res = $obj -> getFills($instrumentId,"3292706588398592");


// 交割合约-Ticker
$instrumentId = "EOS-USD-191227";
$coin = "EOS";
$obj = new FuturesApi(Config::$config);
// 合约持仓信息
//$res = $obj->getPosition();
// 单个合约持仓信息
//$res = $obj->getSpecificPosition($instrumentId);
// 所有币种合约账户信息
//$res = $obj->getAccounts();
// 单个币种合约账户信息
//$res = $obj->getCoinAccounts($coin);
// 获取合约币种杠杆倍数
//$res = $obj->getLeverage($coin);
// 设定合约币种杠杆倍数
//$res = $obj->setLeverage($coin, 10);
// 账单流水查询
//$res = $obj->getLedger($coin);
// 下单
//$res = $obj->takeOrder("abc",$instrumentId,"1","4.2", "1","0","10","");
// 撤销指定订单
//$res = $obj->revokeOrder($instrumentId,"3298735645477888");
// 获取订单列表
//$res = $obj->getOrderList(-1,$instrumentId);
// 获取订单信息
//$res = $obj->getOrderInfo("3298735645477888",$instrumentId);
// 获取成交明细
//$res = $obj->getFills("3298735645477888",$instrumentId);
// 获取合约信息
//$res = $obj->getProducts();
// 获取深度
//$res = $obj->getDepth($instrumentId,1);
// 公共-获取全部ticker信息
//$res = $obj->getTicker();
// 公共-获取某个ticker信息
//$res = $obj->getSpecificTicker($instrumentId);
// 公共-获取成交数据
//$res = $obj->getTrades($instrumentId);
// 公共-获取K线数据
//$res = $obj->getKline($instrumentId,"60");
// 公共-获取指数信息
//$res = $obj->getIndex($instrumentId);
// 公共-获取法币汇率
//$res = $obj->getRate();
// 公共-获取预估交割价
//$res = $obj->getEstimatedPrice($instrumentId);
// 公共-获取平台总持仓量
//$res = $obj->getHolds($instrumentId);
// 公共-获取当前限价
//$res = $obj->getLimit($instrumentId);
// 公共-获取合约标记价格
//$res = $obj->getMarkPrice($instrumentId);
// 公共-获取强平单
//$res = $obj->getLiquidation($instrumentId,1);
// 公共-获取合约挂单冻结数量
//$res = $obj->getHoldsAmount($instrumentId);

// 策略委托下单-止盈止损- mode为1是币币， mode为1是币币杠杆，
//$res = $obj -> takeAlgoOrderStop($instrumentId,"1","1", "1", "1", "1");
// 委托策略撤单-止盈止损
//$res = $obj -> revokeAlgoOrders($instrumentId,["3121188"], "1");
// 获取委托单列表-止盈止损
//$res = $obj -> getAlgoList($instrumentId, "1", '',"3121188",'','','');

//$res = $obj -> setMarginMode($coin, "crossed");
//$res = $obj -> closePosition($instrumentId, "long");
//$res = $obj -> cancelAll($instrumentId, "long");


// 永续合约
$instrumentId = "EOS-USD-SWAP";
$currency = "EOS";
$obj = new SwapApi(Config::$config);
// 合约持仓信息
//$res = $obj->getPosition();
// 单个合约持仓信息
//$res = $obj->getSpecificPosition($instrumentId);
// 所有币种合约账户信息
//$res = $obj->getAccounts();
// 单个币种合约账户信息
//$res = $obj->getCoinAccounts($instrumentId);
// 获取合约币种杠杆倍数
//$res = $obj->getSettings($instrumentId);
// 设定合约币种杠杆倍数
//$res = $obj->setLeverage($instrumentId, 3,10);
// 账单流水查询
//$res = $obj->getLedger($instrumentId);
// 下单
//$res = $obj->takeOrder("abc",$instrumentId,"1","4.2", "1","0","10","");
// 撤销指定订单
//$res = $obj->revokeOrder($instrumentId,"294683725542936576");
// 获取订单列表
//$res = $obj->getOrderList(-1,$instrumentId);
// 获取订单信息
//$res = $obj->getOrderInfo("296764659235508224",$instrumentId);
// 获取成交明细
//$res = $obj->getFills("294683725542936576",$instrumentId);
// 获取合约信息
//$res = $obj->getProducts();
// 获取深度
//$res = $obj->getDepth($instrumentId,1);
// 公共-获取全部ticker信息
//$res = $obj->getTicker();
// 公共-获取某个ticker信息
//$res = $obj->getSpecificTicker($instrumentId);
// 公共-获取成交数据
//$res = $obj->getTrades($instrumentId);
// 公共-获取K线数据
//$res = $obj->getKline($instrumentId,"60");
// 公共-获取指数信息
//$res = $obj->getIndex($instrumentId);
// 公共-获取法币汇率
//$res = $obj->getRate();
// 公共-获取平台总持仓量
//$res = $obj->getHolds($instrumentId);
// 公共-获取当前限价
//$res = $obj->getLimit($instrumentId);
// 公共-获取强平单
//$res = $obj->getLiquidation($instrumentId,1);
// 公共-获取合约挂单冻结数量
//$res = $obj->getHoldsAmount($instrumentId);
// 公共-获取合约下一次结算时间
//$res = $obj->getFundingTime($instrumentId);
// 公共-获取合约标记价格
//$res = $obj->getMarkPrice($instrumentId);
// 公共-获取合约标记价格
//$res = $obj->getMarkPrice($instrumentId);
// 公共-获取合约历史资金费率
//$res = $obj->getHistoricalFundingRate($instrumentId);

// 策略委托下单-止盈止损- mode为1是币币， mode为1是币币杠杆，
//$res = $obj -> takeAlgoOrderStop($instrumentId,"1","1", "1", "1", "1");
// 委托策略撤单-止盈止损
//$res = $obj -> revokeAlgoOrders($instrumentId,["375065465116119040"], "1");
// 获取委托单列表-止盈止损
//$res = $obj -> getAlgoList($instrumentId, "1", '',"375065465116119040",'','','');
//$res = $obj -> getTradeFee();


// 期权
$instrumentId = "TBTC-USD-191213-7500-C";
$underlying = "TBTC-USD";
$obj = new OptionsApi(Config::$config);
// 单个标的指数持仓信息
//$res = $obj -> getSpecificPosition($underlying);
// 单个标的物账户信息
//$res = $obj -> getSpecificAccounts($underlying);
// 下单
//$res = $obj -> takeOrder('',$instrumentId,'buy','0.001','1','','');
// 撤单
//$res = $obj -> revokeOrder($underlying,'125240402932457472');
// 修改订单
//$res = $obj -> amendOrder($underlying,'125245684311965696','','3','');
// 获取单个订单状态
//$res = $obj -> getOrderInfo('125245684311965696', $underlying);
// 获取订单列表
//$res = $obj -> getOrderList('2', $underlying);
// 获取成交明细
//$res = $obj -> getFills('125249091227729920', $underlying);
// 获取账单流水
//$res = $obj -> getLedger($underlying);
// 获取手续费费率
//$res = $obj -> getRateFee();
// 公共-获取标的指数
//$res = $obj -> getIndex();
// 公共-获取期权合约
//$res = $obj -> getInstruments($underlying);
// 公共-获取期权合约详细定价
//$res = $obj -> getInstrumentsSummary($underlying);
// 公共-获取单个期权合约详细定价
//$res = $obj -> getSpecificInstrumentSummary($underlying, $instrumentId);
// 获取深度
//$res = $obj->getDepth($instrumentId,1);
// 公共-获取全部ticker信息
//$res = $obj->getTicker();
// 公共-获取成交数据
//$res = $obj->getTrades($instrumentId);
// 公共-获取某个ticker信息
//$res = $obj->getSpecificTicker($instrumentId);
// 公共-获取K线数据
//$res = $obj->getKline($instrumentId,"60");

// 指数
//$res = $obj->getHistoricalFundingRate($instrumentId);

print_r($res);
