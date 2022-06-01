package com.openway.springtemplateapollo.builder;

import com.google.gson.annotations.Expose;

public class Response {
    @Expose
    private int code;
    @Expose
    private int status;
    @Expose
    private String message;
    @Expose
    private Object data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    private Response(Builder builder){
        this.code = builder.code;
        this.status = builder.status;;
        this.message = builder.message;;
        this.data = builder.data;
    }

    public static final class Builder{
        //require params
        private int status;
        private int code;

        //optional params
        private String message = "response from server";
        private Object data = new Object();

        public Builder(int status, int code){
            this.code = code;
            this.status = status;
        }

        public Builder buildMessage(String message){
            this.message = message;
            return this;
        }
        public Builder buildData(Object data){
            this.data = data;
            return this;
        }
        public Response build(){
            return new Response(this);
        }
    }
}
