package com.okcoin.commons.okex.open.api.bean.futures;

/**
 * Http Result
 *
 * @author Tony Tian
 * @version 1.0.0
 * @date 17/03/2018 11:36
 */
public class HttpResult {

    private int code;
    private String message;
    private int errorCode;
    private String errorMessage;
    private String order_id;
    private Boolean result;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }



    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }


    @Override
    public String toString() {
        return "\t\tResponse Body:{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", errorCode=" + errorCode +
                ", errorMessage='" + errorMessage + '\'' +
                ", order_id='" + order_id + '\'' +
                ", result=" + result +
                '}';
    }
}