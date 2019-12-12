package com.okcoin.commons.okex.open.api.bean.futures.result;

/**
 * Contract currency  <br/>
 * Created by Tony Tian on 2018/2/26 21:33. <br/>
 */
public class Currencies {
    /**
     * symbol
     */
    private String id;
    /**
     * currency name
     */
    private String name;
    /**
     * Minimum transaction quantity
     */
    private String min_size;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMin_size() {
        return min_size;
    }

    public void setMin_size(String min_size) {
        this.min_size = min_size;
    }
}
