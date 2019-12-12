package com.okcoin.commons.okex.open.api.bean.futures.param;

import java.util.List;

/**
 * Close Position
 *
 * @author wc.j
 * @version 1.0.0
 * @date 2018/10/19 16:54
 */
public class CancelOrders {

    List<String> order_ids;
    List<String> client_oids;

    public List<String> getOrder_ids() {
        return order_ids;
    }

    public void setOrder_ids(List<String> order_ids) {
        this.order_ids = order_ids;
    }

    public List<String> getClient_oids() {
        return client_oids;
    }

    public void setClient_oids(List<String> client_oids) {
        this.client_oids = client_oids;
    }
}
