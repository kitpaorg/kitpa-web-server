package com.kitpa.kitpaserver.dto;

public class Response <T>{
    public final static String GENERAL_SUCCESS = "G-9200";
    private T data;
    private String bizCode;

    public Response(T data) {
        this.data = data;
    }

    public static <T>Response<T> success(T data) {
        Response<T> objectResponse = new Response<>(data);
        objectResponse.bizCode = GENERAL_SUCCESS;
        return objectResponse;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }
}
