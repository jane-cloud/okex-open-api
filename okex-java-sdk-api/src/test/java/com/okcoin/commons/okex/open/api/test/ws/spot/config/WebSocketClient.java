package com.okcoin.commons.okex.open.api.test.ws.spot.config;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.okcoin.commons.okex.open.api.bean.other.OrderBookItem;
import com.okcoin.commons.okex.open.api.bean.other.SpotOrderBook;
import com.okcoin.commons.okex.open.api.bean.other.SpotOrderBookDiff;
import com.okcoin.commons.okex.open.api.bean.other.SpotOrderBookItem;
import com.okcoin.commons.okex.open.api.enums.CharsetEnum;
import com.okcoin.commons.okex.open.api.utils.DateUtils;
import lombok.Data;
import net.sf.json.JSONObject;
import okhttp3.*;
import okio.ByteString;
import org.apache.commons.compress.compressors.deflate64.Deflate64CompressorInputStream;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Test;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


/**
 * webSocket client
 *
 * @author oker
 * @create 2019-06-12 15:57
 **/
public class WebSocketClient {
    private static WebSocket webSocket = null;
    private static Boolean isLogin = false;
    private static Boolean isConnect = false;
    private final static ObjectReader objectReader = new ObjectMapper().readerFor(OrderBookData.class);
    private final static ObjectReader objectReader2 = new ObjectMapper().readerFor(SpotOrderBook.class);
    private final static HashFunction crc32 = Hashing.crc32();
    private static Map<String,Optional<SpotOrderBook>> bookMap = new HashMap<>();

    @Test
    public void test(){
        String tmp = "{\"instrument_id\":\"OKB-USDT\",\"asks\":[[\"2.486\",\"30.1749\",\"3\"],[\"2.4861\",\"1200\",\"1\"],[\"2.4862\",\"348.6485\",\"1\"],[\"2.4863\",\"1736.2497\",\"1\"],[\"2.4871\",\"818.9294\",\"1\"],[\"2.4873\",\"818.9293\",\"1\"],[\"2.4874\",\"751.5645\",\"1\"],[\"2.488\",\"225.5386\",\"1\"],[\"2.489\",\"10000\",\"1\"],[\"2.4895\",\"999.9999\",\"1\"],[\"2.4896\",\"2.721\",\"1\"],[\"2.4897\",\"47.9396\",\"2\"],[\"2.49\",\"31505\",\"3\"],[\"2.4909\",\"100.5051\",\"1\"],[\"2.4912\",\"2.3563\",\"1\"],[\"2.492\",\"80.4158\",\"1\"],[\"2.4925\",\"100.3363\",\"1\"],[\"2.4929\",\"361.8427\",\"1\"],[\"2.4938\",\"49.5936\",\"2\"],[\"2.4947\",\"143.7977\",\"3\"],[\"2.4977\",\"201.9219\",\"1\"],[\"2.4979\",\"47.6167\",\"2\"],[\"2.4987\",\"33\",\"1\"],[\"2.499\",\"210.8506\",\"1\"],[\"2.4996\",\"2.1803\",\"1\"],[\"2.4998\",\"125\",\"1\"],[\"2.4999\",\"664.3956\",\"2\"],[\"2.5\",\"10556.2757\",\"16\"],[\"2.5007\",\"950.6813\",\"3\"],[\"2.5011\",\"2.721\",\"1\"],[\"2.5014\",\"996.6875\",\"1\"],[\"2.5015\",\"1.6296\",\"1\"],[\"2.5017\",\"50\",\"1\"],[\"2.5018\",\"5.222\",\"1\"],[\"2.502\",\"170.9396\",\"2\"],[\"2.503\",\"5000\",\"1\"],[\"2.505\",\"337.311\",\"1\"],[\"2.5052\",\"74.5939\",\"1\"],[\"2.5061\",\"45.9396\",\"1\"],[\"2.5063\",\"75.428\",\"1\"],[\"2.5065\",\"44.984\",\"34\"],[\"2.507\",\"500\",\"1\"],[\"2.5088\",\"99.9\",\"1\"],[\"2.5089\",\"10.099\",\"1\"],[\"2.509\",\"500.0021\",\"1\"],[\"2.5095\",\"23.9177\",\"2\"],[\"2.51\",\"5242.471\",\"7\"],[\"2.5102\",\"60.2959\",\"4\"],[\"2.5105\",\"33.8609\",\"1\"],[\"2.5114\",\"120.7535\",\"1\"],[\"2.5115\",\"33\",\"1\"],[\"2.5122\",\"100.6389\",\"1\"],[\"2.5123\",\"101.2694\",\"1\"],[\"2.5126\",\"2.721\",\"1\"],[\"2.513\",\"500\",\"1\"],[\"2.5132\",\"15.916\",\"2\"],[\"2.5139\",\"2.1803\",\"1\"],[\"2.5142\",\"45.9396\",\"1\"],[\"2.5168\",\"8.982\",\"1\"],[\"2.5169\",\"1150.5861\",\"1\"],[\"2.5172\",\"4.475\",\"1\"],[\"2.5179\",\"3047\",\"1\"],[\"2.5183\",\"47.6167\",\"2\"],[\"2.5188\",\"82.2658\",\"1\"],[\"2.5196\",\"202.1163\",\"2\"],[\"2.5199\",\"1043.0682\",\"2\"],[\"2.52\",\"405.4199\",\"4\"],[\"2.5204\",\"2\",\"1\"],[\"2.521\",\"1200\",\"1\"],[\"2.5216\",\"50\",\"1\"],[\"2.5222\",\"164.803\",\"1\"],[\"2.5224\",\"45.9396\",\"1\"],[\"2.5241\",\"2.721\",\"1\"],[\"2.5243\",\"38.196\",\"2\"],[\"2.5244\",\"23.9177\",\"2\"],[\"2.525\",\"55.9147\",\"1\"],[\"2.5261\",\"1.6296\",\"1\"],[\"2.5265\",\"45.9396\",\"1\"],[\"2.5268\",\"74.5939\",\"1\"],[\"2.5283\",\"2.1803\",\"1\"],[\"2.5289\",\"5000\",\"1\"],[\"2.5291\",\"2.3563\",\"1\"],[\"2.53\",\"95.4906\",\"20\"],[\"2.5306\",\"47.9396\",\"2\"],[\"2.5317\",\"9.45\",\"1\"],[\"2.5322\",\"5.1383\",\"1\"],[\"2.5323\",\"4.743\",\"1\"],[\"2.5346\",\"45.9396\",\"1\"],[\"2.5356\",\"2.721\",\"1\"],[\"2.5357\",\"707.616\",\"1\"],[\"2.5359\",\"33.1891\",\"1\"],[\"2.5371\",\"33\",\"1\"],[\"2.5379\",\"162.6906\",\"1\"],[\"2.5384\",\"14840\",\"1\"],[\"2.5386\",\"2.3563\",\"1\"],[\"2.5387\",\"47.6167\",\"2\"],[\"2.5393\",\"23.9177\",\"2\"],[\"2.5395\",\"11.326\",\"1\"],[\"2.54\",\"1556.0059\",\"6\"],[\"2.5408\",\"2\",\"1\"],[\"2.5413\",\"185.0325\",\"1\"],[\"2.5426\",\"2.1803\",\"1\"],[\"2.5428\",\"45.9396\",\"1\"],[\"2.5432\",\"495.2914\",\"1\"],[\"2.5436\",\"35.37\",\"30\"],[\"2.5441\",\"200\",\"1\"],[\"2.5466\",\"11.017\",\"1\"],[\"2.5469\",\"45.9396\",\"1\"],[\"2.5471\",\"2.721\",\"1\"],[\"2.5481\",\"2.3563\",\"1\"],[\"2.5484\",\"74.5939\",\"1\"],[\"2.5488\",\"31.779\",\"27\"],[\"2.5489\",\"200\",\"1\"],[\"2.549\",\"12409.5428\",\"1\"],[\"2.5499\",\"264.1815\",\"1\"],[\"2.55\",\"7066.3874\",\"28\"],[\"2.5508\",\"1.6296\",\"1\"],[\"2.551\",\"57.9396\",\"3\"],[\"2.5517\",\"4.475\",\"1\"],[\"2.5542\",\"23.9177\",\"2\"],[\"2.5543\",\"7.752\",\"1\"],[\"2.555\",\"5000\",\"1\"],[\"2.5551\",\"45.9396\",\"1\"],[\"2.5555\",\"1504.995\",\"4\"],[\"2.5569\",\"2.1803\",\"1\"],[\"2.5576\",\"2.3563\",\"1\"],[\"2.5586\",\"2.721\",\"1\"],[\"2.5591\",\"47.6167\",\"2\"],[\"2.5594\",\"26.056\",\"1\"],[\"2.56\",\"6334.9397\",\"9\"],[\"2.5612\",\"2\",\"1\"],[\"2.5625\",\"12.885\",\"2\"],[\"2.5632\",\"45.9396\",\"1\"],[\"2.5633\",\"50\",\"1\"],[\"2.566\",\"500\",\"1\"],[\"2.5671\",\"2.3563\",\"1\"],[\"2.5673\",\"45.9396\",\"1\"],[\"2.5691\",\"23.9177\",\"2\"],[\"2.5699\",\"74.5939\",\"1\"],[\"2.57\",\"55.0857\",\"3\"],[\"2.5701\",\"10.413\",\"2\"],[\"2.5712\",\"2.1803\",\"1\"],[\"2.5714\",\"755.5556\",\"3\"],[\"2.5717\",\"1000\",\"1\"],[\"2.5748\",\"78.5268\",\"1\"],[\"2.5754\",\"1.6296\",\"1\"],[\"2.5755\",\"45.9396\",\"1\"],[\"2.5766\",\"2.3563\",\"1\"],[\"2.577\",\"500\",\"1\"],[\"2.5775\",\"6.517\",\"1\"],[\"2.5788\",\"1\",\"1\"],[\"2.5795\",\"47.6167\",\"2\"],[\"2.5798\",\"74.76\",\"60\"],[\"2.58\",\"14186.6321\",\"10\"],[\"2.5813\",\"23.24\",\"20\"],[\"2.5816\",\"4.721\",\"2\"],[\"2.5836\",\"45.9396\",\"1\"],[\"2.584\",\"23.9177\",\"2\"],[\"2.5855\",\"2.1803\",\"1\"],[\"2.5858\",\"1000\",\"1\"],[\"2.5861\",\"2.3563\",\"1\"],[\"2.5862\",\"4.475\",\"1\"],[\"2.5877\",\"45.9396\",\"1\"],[\"2.588\",\"200.1053\",\"1\"],[\"2.5886\",\"12.738\",\"11\"],[\"2.5888\",\"104\",\"1\"],[\"2.5889\",\"202.8962\",\"1\"],[\"2.5896\",\"9.264\",\"8\"],[\"2.5898\",\"6009.652\",\"5\"],[\"2.59\",\"1764.9812\",\"4\"],[\"2.591\",\"100.916\",\"1\"],[\"2.5915\",\"74.5939\",\"1\"],[\"2.5918\",\"57.9396\",\"3\"],[\"2.5931\",\"2.721\",\"1\"],[\"2.5956\",\"2.3563\",\"1\"],[\"2.5959\",\"45.9396\",\"1\"],[\"2.5989\",\"23.9177\",\"2\"],[\"2.5996\",\"7975.9923\",\"2\"],[\"2.5998\",\"8109.7843\",\"81\"],[\"2.5999\",\"45.9396\",\"1\"],[\"2.6\",\"51581.7338\",\"56\"],[\"2.6013\",\"14.991\",\"3\"],[\"2.602\",\"2\",\"1\"],[\"2.604\",\"70.1649\",\"2\"],[\"2.6045\",\"2.721\",\"1\"],[\"2.6051\",\"2.3563\",\"1\"],[\"2.6062\",\"38.427\",\"1\"],[\"2.6071\",\"726.9223\",\"3\"],[\"2.6081\",\"45.9396\",\"1\"],[\"2.61\",\"5\",\"1\"],[\"2.6122\",\"47.9396\",\"2\"],[\"2.6131\",\"74.5939\",\"1\"],[\"2.6141\",\"2.1803\",\"1\"],[\"2.6145\",\"2.3563\",\"1\"],[\"2.6149\",\"42.3946\",\"1\"],[\"2.615\",\"500\",\"1\"],[\"2.616\",\"2.721\",\"1\"],[\"2.6163\",\"45.9396\",\"1\"],[\"2.6198\",\"40.076\",\"25\"]],\"bids\":[[\"2.4841\",\"545.92\",\"2\"],[\"2.4832\",\"20.124\",\"1\"],[\"2.4827\",\"199.9999\",\"1\"],[\"2.4826\",\"100.3411\",\"1\"],[\"2.4824\",\"442.1163\",\"2\"],[\"2.4823\",\"420.2953\",\"1\"],[\"2.4822\",\"497.8931\",\"1\"],[\"2.4816\",\"45.9396\",\"1\"],[\"2.4797\",\"11.659\",\"1\"],[\"2.478\",\"286.3428\",\"1\"],[\"2.4776\",\"199.7492\",\"1\"],[\"2.4775\",\"45.9396\",\"1\"],[\"2.4764\",\"1800\",\"1\"],[\"2.4744\",\"946.0168\",\"1\"],[\"2.474\",\"127.3242\",\"1\"],[\"2.4734\",\"45.9396\",\"1\"],[\"2.473\",\"33\",\"1\"],[\"2.4726\",\"6.781\",\"1\"],[\"2.4722\",\"2.3563\",\"1\"],[\"2.471\",\"2.1803\",\"1\"],[\"2.47\",\"5\",\"1\"],[\"2.4699\",\"52.6337\",\"1\"],[\"2.4693\",\"184.2521\",\"3\"],[\"2.4692\",\"485.9856\",\"1\"],[\"2.469\",\"3048\",\"1\"],[\"2.4671\",\"1.0201\",\"1\"],[\"2.4666\",\"2.721\",\"1\"],[\"2.4661\",\"17.1391\",\"1\"],[\"2.4653\",\"1565.8357\",\"2\"],[\"2.4651\",\"7.834\",\"1\"],[\"2.4649\",\"23.9177\",\"2\"],[\"2.4631\",\"121.7977\",\"1\"],[\"2.463\",\"1001\",\"1\"],[\"2.4629\",\"3042.5609\",\"1\"],[\"2.4628\",\"2000\",\"1\"],[\"2.4627\",\"2.3563\",\"1\"],[\"2.4621\",\"74.5939\",\"1\"],[\"2.4615\",\"655.2008\",\"1\"],[\"2.4612\",\"45.9396\",\"1\"],[\"2.4604\",\"34.5456\",\"1\"],[\"2.4602\",\"87.7285\",\"2\"],[\"2.46\",\"548.398\",\"3\"],[\"2.4591\",\"2\",\"1\"],[\"2.4581\",\"9.809\",\"1\"],[\"2.4571\",\"47.6167\",\"2\"],[\"2.4567\",\"2.1803\",\"1\"],[\"2.4564\",\"192.0206\",\"1\"],[\"2.4559\",\"2542.1491\",\"1\"],[\"2.4552\",\"750.448\",\"1\"],[\"2.4551\",\"2.721\",\"1\"],[\"2.4532\",\"3.3764\",\"2\"],[\"2.4531\",\"121.0667\",\"1\"],[\"2.453\",\"177.986\",\"2\"],[\"2.4523\",\"1.6296\",\"1\"],[\"2.4504\",\"4.127\",\"1\"],[\"2.4501\",\"2002\",\"2\"],[\"2.45\",\"5223.7608\",\"10\"],[\"2.4491\",\"17.1391\",\"1\"],[\"2.4489\",\"47.9396\",\"2\"],[\"2.4486\",\"14841\",\"1\"],[\"2.4482\",\"4.475\",\"1\"],[\"2.448\",\"3767.1432\",\"1\"],[\"2.4475\",\"920.4857\",\"1\"],[\"2.4474\",\"33\",\"1\"],[\"2.445\",\"1000\",\"1\"],[\"2.4448\",\"45.9396\",\"1\"],[\"2.4444\",\"20\",\"1\"],[\"2.4437\",\"2.3563\",\"1\"],[\"2.4436\",\"2.721\",\"1\"],[\"2.4433\",\"10.676\",\"1\"],[\"2.4424\",\"2.1803\",\"1\"],[\"2.4408\",\"45.9396\",\"1\"],[\"2.4405\",\"74.5939\",\"1\"],[\"2.4401\",\"1992.2873\",\"2\"],[\"2.44\",\"1592.427\",\"4\"],[\"2.4393\",\"1.0201\",\"1\"],[\"2.4391\",\"59.4821\",\"1\"],[\"2.4387\",\"2\",\"1\"],[\"2.438\",\"24\",\"1\"],[\"2.4375\",\"1.3047\",\"1\"],[\"2.4374\",\"20\",\"1\"],[\"2.4372\",\"46.2415\",\"1\"],[\"2.4367\",\"47.6167\",\"2\"],[\"2.4365\",\"164.1699\",\"1\"],[\"2.4359\",\"7.858\",\"1\"],[\"2.4357\",\"35.2449\",\"1\"],[\"2.4351\",\"23.9177\",\"2\"],[\"2.4346\",\"33\",\"1\"],[\"2.4342\",\"2.3563\",\"1\"],[\"2.4336\",\"12.4296\",\"1\"],[\"2.4326\",\"45.9396\",\"1\"],[\"2.4322\",\"17.1391\",\"1\"],[\"2.4321\",\"2.721\",\"1\"],[\"2.432\",\"14823\",\"1\"],[\"2.431\",\"557.1299\",\"1\"],[\"2.43\",\"7472.753\",\"11\"],[\"2.4285\",\"765.5556\",\"4\"],[\"2.4282\",\"8.929\",\"1\"],[\"2.4281\",\"2.1803\",\"1\"],[\"2.4277\",\"15577.8055\",\"1\"],[\"2.4276\",\"1.6296\",\"1\"],[\"2.4255\",\"1.0201\",\"1\"],[\"2.4247\",\"2.3563\",\"1\"],[\"2.4244\",\"45.9396\",\"1\"],[\"2.4217\",\"33\",\"1\"],[\"2.4215\",\"9.443\",\"1\"],[\"2.4209\",\"1237.22\",\"1\"],[\"2.4208\",\"26.5206\",\"1\"],[\"2.4206\",\"2.721\",\"1\"],[\"2.4204\",\"45.9396\",\"1\"],[\"2.4202\",\"23.9177\",\"2\"],[\"2.4201\",\"1000\",\"1\"],[\"2.42\",\"713.6692\",\"7\"],[\"2.4196\",\"15338\",\"1\"],[\"2.4189\",\"74.5939\",\"1\"],[\"2.4183\",\"2\",\"1\"],[\"2.4177\",\"15642.2377\",\"1\"],[\"2.4173\",\"12.4296\",\"1\"],[\"2.4166\",\"53.404\",\"1\"],[\"2.4163\",\"47.6167\",\"2\"],[\"2.4158\",\"41.3941\",\"1\"],[\"2.4153\",\"2.3563\",\"1\"],[\"2.4152\",\"17.1391\",\"1\"],[\"2.415\",\"3000\",\"1\"],[\"2.4143\",\"8.89\",\"1\"],[\"2.4139\",\"9.9407\",\"1\"],[\"2.4138\",\"2.1803\",\"1\"],[\"2.4137\",\"4.475\",\"1\"],[\"2.4122\",\"45.9396\",\"1\"],[\"2.4116\",\"1.0201\",\"1\"],[\"2.4113\",\"35.9576\",\"1\"],[\"2.411\",\"2954.9559\",\"1\"],[\"2.4103\",\"200\",\"1\"],[\"2.4101\",\"1000\",\"1\"],[\"2.41\",\"11606.7046\",\"12\"],[\"2.4091\",\"2.721\",\"1\"],[\"2.4089\",\"33\",\"1\"],[\"2.4081\",\"47.9396\",\"2\"],[\"2.4077\",\"15707.2053\",\"1\"],[\"2.4072\",\"5886\",\"1\"],[\"2.4062\",\"1.3047\",\"1\"],[\"2.4058\",\"2.3563\",\"1\"],[\"2.4056\",\"2935.186\",\"1\"],[\"2.4054\",\"23.9177\",\"2\"],[\"2.405\",\"760\",\"2\"],[\"2.4045\",\"9.9407\",\"1\"],[\"2.404\",\"45.9396\",\"1\"],[\"2.4033\",\"1209.526\",\"1\"],[\"2.403\",\"86.9397\",\"2\"],[\"2.402\",\"5013\",\"1\"],[\"2.4013\",\"58.8277\",\"1\"],[\"2.401\",\"22864.4845\",\"8\"],[\"2.4005\",\"20\",\"1\"],[\"2.4001\",\"14383.7869\",\"6\"],[\"2.4\",\"52875.3512\",\"44\"],[\"2.3997\",\"70\",\"1\"],[\"2.3995\",\"2.1803\",\"1\"],[\"2.3994\",\"2.4458\",\"1\"],[\"2.3993\",\"4.134\",\"1\"],[\"2.3983\",\"17.1391\",\"1\"],[\"2.3979\",\"2\",\"1\"],[\"2.3977\",\"15776.4559\",\"3\"],[\"2.3973\",\"74.5939\",\"1\"],[\"2.3972\",\"193.5021\",\"1\"],[\"2.3963\",\"2.3563\",\"1\"],[\"2.3961\",\"33\",\"1\"],[\"2.3959\",\"1.6771\",\"1\"],[\"2.3951\",\"9.9407\",\"1\"],[\"2.395\",\"3210\",\"1\"],[\"2.3947\",\"6070\",\"1\"],[\"2.3944\",\"37.515\",\"1\"],[\"2.3937\",\"202.5521\",\"1\"],[\"2.3928\",\"707.616\",\"1\"],[\"2.3924\",\"11.837\",\"1\"],[\"2.3923\",\"1456.3413\",\"1\"],[\"2.3913\",\"12.4092\",\"1\"],[\"2.3905\",\"23.9177\",\"2\"],[\"2.39\",\"21938.3284\",\"8\"],[\"2.3888\",\"105.3724\",\"2\"],[\"2.3877\",\"15850.773\",\"3\"],[\"2.3875\",\"2.4458\",\"1\"],[\"2.3872\",\"145.7345\",\"1\"],[\"2.3871\",\"36.6853\",\"1\"],[\"2.3868\",\"2.3563\",\"1\"],[\"2.3862\",\"2.721\",\"1\"],[\"2.3858\",\"12.5743\",\"1\"],[\"2.3857\",\"9.9407\",\"1\"],[\"2.3853\",\"7.098\",\"1\"],[\"2.3852\",\"2.1803\",\"1\"],[\"2.3846\",\"12.4296\",\"1\"],[\"2.3839\",\"1.0201\",\"1\"],[\"2.3835\",\"1461.7181\",\"1\"],[\"2.3833\",\"33\",\"1\"],[\"2.3823\",\"2922\",\"1\"],[\"2.382\",\"2831\",\"1\"],[\"2.3813\",\"17.1391\",\"1\"],[\"2.38\",\"10937.4242\",\"8\"],[\"2.3793\",\"4.475\",\"1\"],[\"2.3788\",\"151.9253\",\"1\"],[\"2.3775\",\"2\",\"1\"]],\"timestamp\":\"2019-07-22T05:52:05.084Z\",\"checksum\":-1819012977}";
        //测试计算checksum值
        Optional<SpotOrderBook> book = parse(tmp);
        String str = getStr(book.get().getAsks(), book.get().getBids());
        System.out.println(str);
        int checksum = checksum(book.get().getAsks(), book.get().getBids());
        System.out.println(checksum);
    }

    /**
     * 解压函数
     * Decompression function
     *
     * @param bytes
     * @return
     */
    private static String uncompress(final byte[] bytes) {
        try (final ByteArrayOutputStream out = new ByteArrayOutputStream();
             final ByteArrayInputStream in = new ByteArrayInputStream(bytes);
             final Deflate64CompressorInputStream zin = new Deflate64CompressorInputStream(in)) {
            final byte[] buffer = new byte[1024];
            int offset;
            while (-1 != (offset = zin.read(buffer))) {
                out.write(buffer, 0, offset);
            }
            return out.toString();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 与服务器建立连接，参数为服务器的URL
     * connect server
     *
     * @param url
     */
    public void connection(final String url) {

        final OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
        final Request request = new Request.Builder()
                .url(url)
                .build();
        webSocket = client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(final WebSocket webSocket, final Response response) {
                isConnect = true;
                System.out.println(DateFormatUtils.format(new Date(), DateUtils.TIME_STYLE_S4) + " Connected to the server success!");

                //连接成功后，设置定时器，每隔25，自动向服务器发送心跳，保持与服务器连接
                final Runnable runnable = new Runnable() {
                    String time = new Date().toString();
                    @Override
                    public void run() {
                        // task to run goes here
                        sendMessage("ping");
                    }
                };
                final ScheduledExecutorService service = Executors
                        .newSingleThreadScheduledExecutor();
                // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
                service.scheduleAtFixedRate(runnable, 25, 25, TimeUnit.SECONDS);
            }

            @Override
            public void onMessage(final WebSocket webSocket, final String s) {
                //System.out.println(DateFormatUtils.format(new Date(), DateUtils.TIME_STYLE_S4) + " Receive--------: " + s);
                if (null != s && s.contains("login")) {
                    if (s.endsWith("true}")) {
                        isLogin = true;
                    }
                }
            }

            @Override
            public void onClosing(WebSocket webSocket, final int code, final String reason) {
                System.out.println(DateFormatUtils.format(new Date(), DateUtils.TIME_STYLE_S4) + " Connection is disconnected !!！");
                webSocket.close(1000, "Long time not to send and receive messages! ");
                webSocket = null;
                isConnect = false;
            }

            @Override
            public void onClosed(final WebSocket webSocket, final int code, final String reason) {
                System.out.println("Connection has been disconnected.");
                isConnect = false;
            }

            @Override
            public void onFailure(final WebSocket webSocket, final Throwable t, final Response response) {
                t.printStackTrace();
                System.out.println("Connection failed!");
                isConnect = false;
            }

            /**
             * 深度合并
             * @param webSocket
             * @param bytes
             */
            @Override
            public void onMessage(final WebSocket webSocket, final ByteString bytes) {
                //解压返回的数据
                final String s = WebSocketClient.uncompress(bytes.toByteArray());
                //判断是否是深度接口
                if (s.contains("\"table\":\"spot/depth\",")) {//是深度接口
                    if (s.contains("partial")) {//是第一次的200档，记录下第一次的200档
                        //System.out.println(DateFormatUtils.format(new Date(), DateUtils.TIME_STYLE_S4) + " Receive: " + s);
                        JSONObject rst = JSONObject.fromObject(s);
                        net.sf.json.JSONArray dataArr = net.sf.json.JSONArray.fromObject(rst.get("data"));
                        JSONObject data = JSONObject.fromObject(dataArr.get(0));
                        String dataStr = data.toString();
                        Optional<SpotOrderBook> oldBook = parse(dataStr);
                        String instrumentId = data.get("instrument_id").toString();
                        bookMap.put(instrumentId,oldBook);
                    } else if (s.contains("\"action\":\"update\",")) {//是后续的增量，则需要进行深度合并
                        //System.out.println(DateFormatUtils.format(new Date(), DateUtils.TIME_STYLE_S4) + " Receive: " + s);
                        JSONObject rst = JSONObject.fromObject(s);
                        net.sf.json.JSONArray dataArr = net.sf.json.JSONArray.fromObject(rst.get("data"));
                        JSONObject data = JSONObject.fromObject(dataArr.get(0));
                        String dataStr = data.toString();
                        String instrumentId = data.get("instrument_id").toString();
                        Optional<SpotOrderBook> oldBook = bookMap.get(instrumentId);
                        Optional<SpotOrderBook> newBook = parse(dataStr);
                        //获取增量的ask
                        List<SpotOrderBookItem> askList = newBook.get().getAsks();
                        //获取增量的bid
                        List<SpotOrderBookItem> bidList = newBook.get().getBids();
                        System.out.println("oldBook.get--------------"+oldBook.get());
                        //oldBook.get()拿到全量的数据
                        SpotOrderBookDiff bookdiff = oldBook.get().diff(newBook.get());
                        System.out.println("名称："+instrumentId+",深度合并成功！checknum值为：" + bookdiff.getChecksum() + ",合并后的数据为：" + bookdiff.toString());
                        String str = getStr(bookdiff.getAsks(), bookdiff.getBids());
                        System.out.println("名称："+instrumentId+",拆分要校验的字符串：" + str);
                        int checksum = checksum(bookdiff.getAsks(), bookdiff.getBids());
                        System.out.println("名称："+instrumentId+",校验的checksum:" + checksum);
                        boolean flag = checksum==bookdiff.getChecksum()?true:false;
                        if(flag){
                            System.out.println("名称："+instrumentId+",深度校验结果为："+flag);
                            oldBook = parse(bookdiff.toString());
                            bookMap.put(instrumentId,oldBook);
                        }else{
                            System.out.println("名称："+instrumentId+",深度校验结果为："+flag+"数据有误！请重连！");
                            //获取订阅的频道和币对
                            String channel = rst.get("table").toString();
                            String unSubStr = "{\"op\": \"unsubscribe\", \"args\":[\"" + channel+":"+instrumentId + "\"]}";
                            System.out.println(DateFormatUtils.format(new Date(), DateUtils.TIME_STYLE_S4) + " Send: " + unSubStr);
                            webSocket.send(unSubStr);
                            String subStr = "{\"op\": \"subscribe\", \"args\":[\"" + channel+":"+instrumentId + "\"]}";
                            System.out.println(DateFormatUtils.format(new Date(), DateUtils.TIME_STYLE_S4) + " Send: " + subStr);
                            webSocket.send(subStr);
                            System.out.println("名称："+instrumentId+",正在重新订阅！");
                        }
                    }
                } else {//不是深度接口
                    System.out.println(DateFormatUtils.format(new Date(), DateUtils.TIME_STYLE_S4) + " Receive: " + s);
                }
                if (null != s && s.contains("login")) {
                    if (s.endsWith("true}")) {
                        isLogin = true;
                    }
                }
            }
        });
    }

    /**
     * 获得sign
     * @param message
     * @param secret
     * @return
     */
    private String sha256_HMAC(final String message, final String secret) {
        String hash = "";
        try {
            final Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            final SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(CharsetEnum.UTF_8.charset()), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            final byte[] bytes = sha256_HMAC.doFinal(message.getBytes(CharsetEnum.UTF_8.charset()));
            hash = Base64.getEncoder().encodeToString(bytes);
        } catch (final Exception e) {
            System.out.println(DateFormatUtils.format(new Date(), DateUtils.TIME_STYLE_S4) + "Error HmacSHA256 ===========" + e.getMessage());
        }
        return hash;
    }

    /**
     * list转换成json
     * @param list
     * @return
     */
    private String listToJson(final List<String> list) {
        final JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(list);
        return jsonArray.toJSONString();
    }

    /**
     * 登录
     * login
     *
     * @param apiKey
     * @param passPhrase
     * @param secretKey
     */
    public void login(final String apiKey, final String passPhrase, final String secretKey) {
        final String timestamp = (Double.parseDouble(DateUtils.getEpochTime()) + 28800) + "";
        final String message = timestamp + "GET" + "/users/self/verify";
        final String sign = sha256_HMAC(message, secretKey);
        final String str = "{\"op\"" + ":" + "\"login\"" + "," + "\"args\"" + ":" + "[" + "\"" + apiKey + "\"" + "," + "\"" + passPhrase + "\"" + "," + "\"" + timestamp + "\"" + "," + "\"" + sign + "\"" + "]}";
        sendMessage(str);
    }


    /**
     * 订阅，参数为频道组成的集合
     * Bulk Subscription
     *
     * @param list
     */
    public void subscribe(final List<String> list) {
        final String s = listToJson(list);
        final String str = "{\"op\": \"subscribe\", \"args\":" + s + "}";
        sendMessage(str);
    }

    /**
     * 取消订阅，参数为频道组成的集合
     * unsubscribe
     *
     * @param list
     */
    public void unsubscribe(final List<String> list) {
        final String s = listToJson(list);
        final String str = "{\"op\": \"unsubscribe\", \"args\":" + s + "}";
        sendMessage(str);
    }

    private void sendMessage(final String str) {
        if (null != webSocket) {
            try {
                Thread.sleep(1000);
            } catch (final Exception e) {
                e.printStackTrace();
            }
            System.out.println(DateFormatUtils.format(new Date(), DateUtils.TIME_STYLE_S4) + " Send: " + str);
            if (isConnect) {
                webSocket.send(str);
                return;
            }
        }
        System.out.println(DateFormatUtils.format(new Date(), DateUtils.TIME_STYLE_S4) + " Please establish a connection before operation !!!");
    }

    /**
     * 断开连接
     * Close Connection
     */
    public void closeConnection() {
        if (null != webSocket) {
            webSocket.close(1000, "User close connect !!!");
        } else {
            System.out.println(DateFormatUtils.format(new Date(), DateUtils.TIME_STYLE_S4) + " Please establish a connection before operation !!!");
        }
        isConnect = false;
    }

    public boolean getIsLogin() {
        return isLogin;
    }

    public boolean getIsConnect() {
        return isConnect;
    }

    public static <T extends OrderBookItem> int checksum(List<T> asks, List<T> bids) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < 25; i++) {
            if (i < bids.size()) {
                s.append(bids.get(i).getPrice().toString());
                s.append(":");
                s.append(bids.get(i).getSize());
                s.append(":");
            }
            if (i < asks.size()) {
                s.append(asks.get(i).getPrice().toString());
                s.append(":");
                s.append(asks.get(i).getSize());
                s.append(":");
            }
        }
        final String str;
        if (s.length() > 0) {
            str = s.substring(0, s.length() - 1);
        } else {
            str = "";
        }

        return crc32.hashString(str, StandardCharsets.UTF_8).asInt();
    }

    private static <T extends OrderBookItem> String getStr(List<T> asks, List<T> bids) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < 25; i++) {
            if (i < bids.size()) {
                s.append(bids.get(i).getPrice().toString());
                s.append(":");
                s.append(bids.get(i).getSize());
                s.append(":");
            }
            if (i < asks.size()) {
                s.append(asks.get(i).getPrice().toString());
                s.append(":");
                s.append(asks.get(i).getSize());
                s.append(":");
            }
        }
        final String str;
        if (s.length() > 0) {
            str = s.substring(0, s.length() - 1);
        } else {
            str = "";
        }
        return str;
    }

    public static Optional<SpotOrderBook> parse(String json) {

        try {
            OrderBookData data = objectReader.readValue(json);
            List<SpotOrderBookItem> asks =
                    data.getAsks().stream().map(x -> new SpotOrderBookItem(new String(x.get(0)), x.get(1), x.get(2)))
                            .collect(Collectors.toList());

            List<SpotOrderBookItem> bids =
                    data.getBids().stream().map(x -> new SpotOrderBookItem(new String(x.get(0)), x.get(1), x.get(2)))
                            .collect(Collectors.toList());

            return Optional.of(new SpotOrderBook(data.getInstrument_id(), asks, bids, data.getTimestamp(),data.getChecksum()));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Data
    static class OrderBookData {
        private String instrument_id;
        private List<List<String>> asks;
        private List<List<String>> bids;
        private String timestamp;
        private int checksum;
    }
}
