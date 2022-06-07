package com.stdio.esm.model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class EsmResponse {

    public static final String SUCCESS = "success";
    public static final String ERROR = "error";
    public static final String STATUS_KEY = "status";
    public static final String MESSAGE_KEY = "message";
    public static final String RESPONSE_DATA_KEY = "responseData";

    private String status;
    private String message;
    private Object responseData;

    public Map<String, Object> getResponse() {
        Map<String, Object> response = new HashMap<>();
        response.put(STATUS_KEY, this.status);
        response.put(MESSAGE_KEY, this.message);
        response.put(RESPONSE_DATA_KEY, this.responseData);
        return response;
    }

    public void setResponse(String status, String message, Object responseData) {
        this.status = status;
        this.message = message;
        this.responseData = responseData;
    }
}
