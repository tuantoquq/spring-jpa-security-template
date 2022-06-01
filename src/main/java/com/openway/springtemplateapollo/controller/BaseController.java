package com.openway.springtemplateapollo.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.openway.springtemplateapollo.builder.Response;
import com.openway.springtemplateapollo.constants.StatusApiConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public abstract class BaseController {
    protected static final Logger requestLogger = LogManager.getLogger("RequestLog");
    protected static final Logger eLogger = LogManager.getLogger("ErrorLog");

    protected Gson gson;

    public BaseController(){
        GsonBuilder gsonBuilder =new GsonBuilder();
        gson = gsonBuilder
                .excludeFieldsWithoutExposeAnnotation()
                .serializeNulls()
                .create();
    }

    protected String getRequestParams(HttpServletRequest request){
        StringBuilder sb = new StringBuilder();
        request.getParameterMap().keySet().stream().sorted().forEach(
                s ->{
                    String p = request.getParameter(s);
                    if(sb.length() > 0) sb.append("&");
                    sb.append(s).append("=").append(p);
                }
        );
        return sb.toString();
    }

    protected String buildFailureResponse(int code, String message){
        Response failureResponse = new Response.Builder(StatusApiConstants.OTHER_ERROR.getErrorCode(), code)
                .buildMessage(message)
                .build();
        return gson.toJson(failureResponse);
    }
    protected String buildFailureResponse(StatusApiConstants statusApiConstants){
        Response failureResponse = new Response.Builder(statusApiConstants.getErrorCode(), statusApiConstants.getHttpStatusCode())
                .buildMessage(statusApiConstants.getResponseText())
                .build();
        return gson.toJson(failureResponse);
    }
}
