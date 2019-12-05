package com.okcoin.commons.okex.open.api.bean.other;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SpotOrderBookItem implements OrderBookItem<String> {
    private final String price;
    private final String size;
    private final String numOrder;

    public SpotOrderBookItem(String price, String size, String numOrder) {
        this.price = price;
        this.size = size;
        this.numOrder = numOrder;
    }

    @Override
    public String toString() {
        return "[\"" + price.toString() + "\",\"" + size + "\",\"" + numOrder + "\"]";
    }

    @Override
    public String getPrice() {
        return price;
    }

    @Override
    public String getSize() {
        return size;
    }

    public String getNumOrder() {
        return numOrder;
    }
}
