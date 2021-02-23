package com.example.shoppingCart.exceptions;

import java.util.Date;

public class ErrorMessage {
    private Date timeStamp;
    private String errorMessage;
    private String message;

    public ErrorMessage() {
    }

    public ErrorMessage(Date timeStamp, String errorMessage, String message) {
        this.timeStamp = timeStamp;
        this.errorMessage = errorMessage;
        this.message = message;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
