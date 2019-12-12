package com.okcoin.commons.okex.open.api.bean.swap.param;

import java.util.LinkedList;
import java.util.List;

public class PpCancelOrderVO {
    private List<String> Orderids = new LinkedList<>();
    private List<String> Clientoids = new LinkedList<>();

    public PpCancelOrderVO(List<String> orderids, List<String> clientoids) {
        Orderids = orderids;
        Clientoids = clientoids;
    }

    public PpCancelOrderVO() {
    }

    public List<String> getOrderids() {
        return Orderids;
    }

    public void setOrderids(List<String> orderids) {
        Orderids = orderids;
    }

    public List<String> getClientoids() {
        return Clientoids;
    }

    public void setClientoids(List<String> clientoids) {
        Clientoids = clientoids;
    }

    @Override
    public String toString() {
        return "PpCancelOrderVO{" +
                "Orderids=" + Orderids +
                ", Clientoids=" + Clientoids +
                '}';
    }
}
