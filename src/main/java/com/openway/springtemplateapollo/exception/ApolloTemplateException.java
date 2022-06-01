package com.openway.springtemplateapollo.exception;

public class ApolloTemplateException extends Exception{
    private int code;
    public ApolloTemplateException(String message, int code){
        super(message);
        this.code = code;
    }
    public void setCode(int code){
        this.code = code;
    }
    public int getCode(){
        return this.code;
    }
}
