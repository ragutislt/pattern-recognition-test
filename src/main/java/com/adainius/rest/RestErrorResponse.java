package com.adainius.rest;

public class RestErrorResponse {
    private String error;

    private RestErrorResponse() {
    }

    public RestErrorResponse(String error) {
        this.error = error;
    }

    public String getError() {
        return this.error;
    }
}