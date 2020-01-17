<?php
/**
 * Created by PhpStorm.
 * User: hengliu
 * Date: 2019/5/10
 * Time: 8:22 PM
 */

//namespace okv3;

require './vendor/autoload.php';
require './Config.php';

use okv3\Config;
use okv3\Websocket;

$obj = new Websocket(Config::$config);

$callbackTrade = function ($data) use ($obj) {
    $dataArr = json_decode($data, true);
    $ntime = $obj->getTimestamp();
    $otime = $obj -> oldTime;
//    print_r($dataArr);

    if (!empty($dataArr["table"])){
        $key = substr($dataArr["data"][0]["timestamp"], 0,-8);
//        print_r($key."\n");

        // 第一次，分钟线的初始化
        if (empty($obj->tradeVolumn[$key]))
        {
            print_r($ntime." tradeVolumn ".json_encode($obj->tradeVolumn));
            print_r("\n");

            $obj->tradeVolumn[$key] = $dataArr["data"][0]["qty"];
        // 分钟线成交量的累加
        }else{
            $obj->tradeVolumn[$key] += $dataArr["data"][0]["qty"];
        }



    }

    print_r($ntime." ".$data);
    print_r("\n");
};

// 回调函数
$callback = function ($data) use ($obj){

    $ntime = $obj->getTimestamp();
    $otime = $obj -> oldTime;

    print_r($ntime." ".$data);
    print_r("\n");
};

$callbackTime = function ($data) use ($obj){
    $dataArr = json_decode($data, true);

    $ntime = $obj->getTimestamp();
//    $key = $dataArr
//    print_r($dataArr["op"]);
//    die();
    if (!empty($dataArr["table"]))
        $key = $dataArr["table"];
    elseif(!empty($dataArr["op"]))
        $key = $dataArr["op"];
    else
        $key = "else";

    // 上一次时间
    if (!empty($key) && !empty($obj -> oldTime[$key]))
        $otime = $obj -> oldTime[$key];
    else
        $otime = $ntime;

    // 上次与本次推送时间差，
    $lastDiff = $obj->dateToTimestamp($ntime)-$obj->dateToTimestamp($otime);
//    print_r($ntime." ($diff) ".$data);
//    print_r("\n");

    // 本次，本地时间戳与推送数据时间戳的差
    if (!empty($dataArr["data"][0]["timestamp"])){
        $timestamp = $dataArr["data"][0]["timestamp"];
//    if (!empty($dataArr["data"][0]["last_fill_time"])){
//        $timestamp = $dataArr["data"][0]["last_fill_time"];
        @$diff = $obj->dateToTimestamp($ntime)-$obj->dateToTimestamp($timestamp);
        print_r($ntime." ($lastDiff)"." ($diff) ".$data);
        print_r("\n");
    }else{
        print_r($obj->getTimestamp()." ".$data);
        print_r("\n");
    }

//    if (!empty($key) && !empty($obj -> oldTime[$key]))
    $obj -> oldTime[$key] = $ntime;


};

/**
 * spot
 */
$instrumentId = "TRX-USDK";
$instrumentId = "OKB-ETH";
$instrumentId = "TRIO-USDT";
$instrumentId = "XMR-ETH";
$instrumentId = "BTC-USDT";
$coin = "EOS";
//$obj->subscribe($callbackTime,"spot/ticker:$instrumentId");
//$obj->subscribe($callback,"spot/candle60s:$instrumentId");
//$obj->subscribe($callbackTime,"spot/depth:$instrumentId");
//$obj->subscribe($callbackTime,"spot/depth5:$instrumentId");
//$obj->subscribe($callbackTime,"spot/trade:$instrumentId");
//$obj->subscribe($callback);
//$obj->subscribe($callbackTime,"spot/order:$instrumentId");
//$obj->subscribe($callback,"spot/account:$coin");

//$obj->subscribe($callback,["spot/order:$instrumentId","futures/order:$instrumentId","swap/order:$instrumentId"]);

/**
 * margin
 */
//$obj->subscribe($callback,"spot/margin_account:EOS-USDT");

/**
 * futures
 */
//$instrumentId= "ETH-USD-190816";
$instrumentId= "EOS-USD-191227";
$coin = "EOS";
//$obj->subscribe($callbackTime,"futures/ticker:$instrumentId");
//$obj->subscribe($callback,"futures/candle1800s:$instrumentId");
//$obj->subscribe($callbackTime,"futures/candle60s:$instrumentId");
//$obj->subscribe($callback,"futures/candle900s:$instrumentId");
//$obj->subscribe($callbackTime,"futures/depth:$instrumentId");
//$obj->subscribe($callbackTime,"futures/depth_l2_tbt:$instrumentId");
//$obj->subscribe($callbackTime,"futures/depth5:$instrumentId");
//$obj->subscribe($callbackTrade,"futures/trade:$instrumentId");
//$obj->subscribe($callback,["futures/account:EOS"]);
//$obj->subscribe($callbackTime,"futures/order:$instrumentId");

//$obj->subscribe($callbackTime,"futures/position:$instrumentId");
//$obj->subscribe($callback,"futures/estimated_price:$instrumentId");
//$obj->subscribe($callbackTime,"futures/price_range:$instrumentId");
//$obj->subscribe($callback,"futures/mark_price:$instrumentId");
//$obj->subscribe($callback,"futures/mark_price:$instrumentId");
//$obj->subscribe($callback,["futures/instruments"]);
//$obj->subscribe($callback,"futures/depth_l2_tbt:$instrumentId");
//$obj->subscribe($callback,"futures/order_algo:$instrumentId");

//$obj->subscribe($callbackTime,["futures/position:$instrumentId","spot/order:EOS-USDT","swap/order:EOS-USD-SWAP"]);
//$obj->subscribe($callbackTime,['futures/position:BTC-USD-190927', 'futures/position:LTC-USD-190927', 'futures/position:ETH-USD-190927', 'futures/position:ETC-USD-190927', 'futures/position:XRP-USD-190927', 'futures/position:EOS-USD-190927', 'futures/position:BCH-USD-190927', 'futures/position:BSV-USD-190927', 'futures/position:TRX-USD-190927']);


/**
 * swap
 */
$instrumentId= "EOS-USD-SWAP";
$coin = "EOS";
//$obj->subscribe($callbackTime,"swap/ticker:$instrumentId");
//$obj->subscribe($callback,"swap/candle60s:$instrumentId");
//$obj->subscribe($callbackTime,"swap/depth:$instrumentId");
//$obj->subscribe($callbackTime,"swap/depth5:$instrumentId");
//$obj->subscribe($callbackTime,"swap/depth:$instrumentId");
//$obj->subscribe($callback,"swap/trade:$instrumentId");
//$obj->subscribe($callback,"swap/account:$instrumentId");
//$obj->subscribe($callbackTime,"swap/order:$instrumentId");
//$obj->subscribe($callbackTime,"swap/order_algo:$instrumentId");
//$obj->subscribe($callbackTime,["swap/account:$instrumentId","swap/order:$instrumentId"]);

//$obj->subscribe($callback,"swap/position:$instrumentId");
//$obj->subscribe($callbackTime,"swap/funding_rate:$instrumentId");
//$obj->subscribe($callbackTime,"swap/price_range:$instrumentId");
//$obj->subscribe($callbackTime,["swap/mark_price:$instrumentId"]);
//$obj->subscribe($callbackTime,["swap/account:$instrumentId", "swap/order:$instrumentId"]);

/**
 * index
 */
//$obj->subscribe($callback,"index/ticker:BTC-USD");
//$obj->subscribe($callback,"index/candle60s:EOS-USD");

//$obj->subscribe($callback,"swap/position:$instrumentId");
//$obj->subscribe($callbackTime,"swap/funding_rate:$instrumentId");
//$obj->subscribe($callbackTime,"swap/price_range:$instrumentId");
//$obj->subscribe($callbackTime,["swap/mark_price:$instrumentId"]);
//$obj->subscribe($callbackTime,["swap/account:$instrumentId", "swap/order:$instrumentId"]);


/**
 * option
 */
$instrumentId= "BTC-USD-191227-12500-C";
$underlying = "TBTC-USD";
$coin = "BTC";
//$obj->subscribe($callbackTime,"option/instruments:$underlying");
//$obj->subscribe($callback,"option/candle60s:$instrumentId");
//$obj->subscribe($callback,"option/summary:$instrumentId");      // 期权详细定价频道
//$obj->subscribe($callbackTime,"option/trade:$instrumentId");
//$obj->subscribe($callbackTime,"option/depth5:$instrumentId");
//$obj->subscribe($callbackTime,"option/depth:$instrumentId");
//$obj->subscribe($callback,"option/account:$instrumentId");
//$obj->subscribe($callbackTime,"option/position:$instrumentId");
//$obj->subscribe($callbackTime,"option/order:$instrumentId");
//$obj->subscribe($callbackTime,["swap/account:$instrumentId","swap/order:$instrumentId"]);
