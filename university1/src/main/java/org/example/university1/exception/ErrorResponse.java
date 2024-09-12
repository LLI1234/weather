package org.example.university1.exception;

import java.util.Date;

public class ErrorResponse {
    private String message;
    private Date date;
    private int code;

    public ErrorResponse(String message, Date date, int code) {
        this.message = message;
        this.date = date;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
