package com.okcoin.commons.okex.open.api.test;

import com.okcoin.commons.okex.open.api.bean.futures.result.ServerTime;
import com.okcoin.commons.okex.open.api.config.APIConfiguration;
import com.okcoin.commons.okex.open.api.service.GeneralAPIService;
import com.okcoin.commons.okex.open.api.service.futures.impl.GeneralAPIServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Type2Test {
    @Autowired
    private GeneralAPIService generalAPIService;

    @GetMapping("/server-time")
    public ServerTime getServerTime() {
        return generalAPIService.getServerTime();
    }

    @Bean
    public APIConfiguration okexApiConfig() {
        APIConfiguration config = new APIConfiguration();
        config.setEndpoint("");
        config.setApiKey("");
        config.setSecretKey("");
        config.setPassphrase("");
        config.setPrint(false);
        return config;
    }

    @Bean
    public GeneralAPIService generalAPIService(APIConfiguration config) {
        return new GeneralAPIServiceImpl(config);
    }
}
