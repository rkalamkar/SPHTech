package com.ngo.sphtech.Pojo;

import java.io.Serializable;

public class ApiResponse implements Serializable {
    String help;
    boolean success;
    ApiResult result;

    public String getHelp() {
        return help;
    }

    public ApiResult getResult() {
        return result;
    }

    public void setResult(ApiResult result) {
        this.result = result;
    }

    public void setHelp(String help) {
        this.help = help;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

}
