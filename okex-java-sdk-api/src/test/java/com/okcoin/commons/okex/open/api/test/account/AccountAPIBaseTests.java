package com.okcoin.commons.okex.open.api.test.account;

import com.okcoin.commons.okex.open.api.config.APIConfiguration;
import com.okcoin.commons.okex.open.api.enums.I18nEnum;
import com.okcoin.commons.okex.open.api.test.BaseTests;

/**
 * Account api basetests
 *
 * @author hucj
 * @version 1.0.0
 * @date 2018/7/04 18:23
 */
public class AccountAPIBaseTests extends BaseTests {

    public APIConfiguration config() {
        APIConfiguration config = new APIConfiguration();

        config.setEndpoint("https://www.okex.com");
        // apiKey，api注册成功后页面上有
        config.setApiKey("");
        // secretKey，api注册成功后页面上有
        config.setSecretKey("");
        config.setPassphrase("");
        //是否打印配置信息
        config.setPrint(true);

        config.setI18n(I18nEnum.SIMPLIFIED_CHINESE);

        return config;
    }


}
