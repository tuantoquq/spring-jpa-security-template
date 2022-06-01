package com.openway.springtemplateapollo.constants;

import org.springframework.http.HttpStatus;

public class StatusApiConstants {
    public static final StatusApiConstants SUCCESS;
    public static final StatusApiConstants OTHER_ERROR;
    public static final StatusApiConstants INVALID_PARAM;
    public static final StatusApiConstants TIMEOUT;
    private StatusApiConstants.ResponseClass responseClass;
    private int errorCode;
    private int httpStatusCode;
    private String responseText;

    public StatusApiConstants(ResponseClass responseClass, int errorCode, int httpStatusCode, String responseText) {
        this.responseClass = responseClass;
        this.errorCode = errorCode;
        this.httpStatusCode = httpStatusCode;
        this.responseText = responseText;
    }

    public ResponseClass getResponseClass() {
        return responseClass;
    }

    public void setResponseClass(ResponseClass responseClass) {
        this.responseClass = responseClass;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public String getResponseText() {
        return responseText;
    }

    public void setResponseText(String responseText) {
        this.responseText = responseText;
    }

    static {
        SUCCESS = new StatusApiConstants(StatusApiConstants.ResponseClass.INFORMATION, 0, HttpStatus.OK.value(),"Success");
        OTHER_ERROR = new StatusApiConstants(ResponseClass.ERROR, 1, HttpStatus.OK.value(),"Other Error");
        INVALID_PARAM = new StatusApiConstants(StatusApiConstants.ResponseClass.ERROR, 4, HttpStatus.BAD_REQUEST.value(),"Invalid Parameters or Values");
        TIMEOUT = new StatusApiConstants(StatusApiConstants.ResponseClass.ERROR, 5, HttpStatus.GATEWAY_TIMEOUT.value(),"Time-out at vendor system");
    }

    public static enum ResponseClass {
        INFORMATION("Information", "I"),
        ERROR("Error", "E"),
        WARNING("Warning", "W");

        private final String name;
        private final String asChar;

        private ResponseClass(String name, String asChar) {
            this.name = name;
            this.asChar = asChar;
        }

        public String getName() {
            return this.name;
        }

        public String getAsChar() {
            return this.asChar;
        }
    }
}
