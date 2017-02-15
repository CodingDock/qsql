package com.qsql.model;

import java.io.Serializable;

/**
 * Created by biezhi on 2017/2/15.
 */
public class ExplainMsg implements Serializable {

    private String Message = "";
    private String Level = "";
    private int Code;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    public String getLevel() {
        return Level;
    }

    public void setLevel(String level) {
        Level = level;
    }

    @Override
    public String toString() {
        return "ExplainMsg [" +
                "Message='" + Message + '\'' +
                ", Code=" + Code +
                ", Level='" + Level + '\'' +
                ']';
    }
}
