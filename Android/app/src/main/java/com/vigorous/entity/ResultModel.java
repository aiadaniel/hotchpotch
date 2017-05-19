package com.vigorous.entity;

/**
 * Created by lxm.
 */

public class ResultModel {
    private int code;

    private String message;

    private String content;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getContent() {
        return content;
    }

    public ResultModel(int code, String message) {
        this.code = code;
        this.message = message;
        this.content = "";
    }

    public ResultModel(int code, String message, String content) {
        this.code = code;
        this.message = message;
        this.content = content;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
