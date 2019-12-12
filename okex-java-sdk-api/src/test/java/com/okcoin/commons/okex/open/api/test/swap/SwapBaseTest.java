package com.okcoin.commons.okex.open.api.test.swap;

import com.okcoin.commons.okex.open.api.config.APIConfiguration;
import com.okcoin.commons.okex.open.api.enums.I18nEnum;
import com.okcoin.commons.okex.open.api.test.BaseTests;

public class SwapBaseTest extends BaseTests {
    public APIConfiguration config() {
        final APIConfiguration config = new APIConfiguration();

        config.setEndpoint("https://www.okex.com/");
        config.setApiKey("");
        // secretKey，api注册成功后页面上有
        config.setSecretKey("");
        config.setPassphrase("");
        config.setPrint(true);
        config.setI18n(I18nEnum.SIMPLIFIED_CHINESE);

        return config;
    }

    int from = 0;
    int to = 0;
    int limit = 20;

    String instrument_id = "BTC-USD-SWAP";
}
