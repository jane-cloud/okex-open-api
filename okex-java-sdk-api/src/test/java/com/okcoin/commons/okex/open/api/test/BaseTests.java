package com.okcoin.commons.okex.open.api.test;

import com.alibaba.fastjson.JSON;
import com.okcoin.commons.okex.open.api.config.APIConfiguration;
import org.slf4j.Logger;

import org.apache.commons.compress.compressors.deflate64.Deflate64CompressorInputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Junit base Tests
 *
 * @author Tony Tian
 * @version 1.0.0
 * @date 2018/3/12 14:48
 */
public class BaseTests {

    public APIConfiguration config;

    public void toResultString(Logger log, String flag, Object object) {
        StringBuilder su = new StringBuilder();
        su.append("\n").append("=====>").append(flag).append(":\n").append(JSON.toJSONString(object));
        log.info(su.toString());
    }
}
