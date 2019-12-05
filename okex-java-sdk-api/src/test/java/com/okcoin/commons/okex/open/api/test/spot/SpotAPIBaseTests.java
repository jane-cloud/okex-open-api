package com.okcoin.commons.okex.open.api.test.spot;

import com.okcoin.commons.okex.open.api.config.APIConfiguration;
import com.okcoin.commons.okex.open.api.enums.I18nEnum;
import com.okcoin.commons.okex.open.api.test.BaseTests;

public class SpotAPIBaseTests extends BaseTests {

    public APIConfiguration config() {
        final APIConfiguration config = new APIConfiguration();

        config.setEndpoint("https://www.okex.com/");

        config.setApiKey("1398f8ef-bc21-4421-8a12-ddd5d0ce8218");
        // secretKey，api注册成功后页面上有
        config.setSecretKey("F7AE2609A16B4E078C8F771679CD06CB");
        config.setPassphrase("ctyxz425409");

        config.setPrint(true);
        config.setI18n(I18nEnum.SIMPLIFIED_CHINESE);

        return config;
    }

}
