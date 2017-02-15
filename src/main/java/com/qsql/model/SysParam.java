package com.qsql.model;

import java.io.Serializable;

/**
 * Created by biezhi on 2017/2/15.
 */
public class SysParam implements Serializable {

    private String variable_name;
    private String variable_value;

    public String getVariable_name() {
        return variable_name;
    }

    public void setVariable_name(String variable_name) {
        this.variable_name = variable_name;
    }

    public String getVariable_value() {
        return variable_value;
    }

    public void setVariable_value(String variable_value) {
        this.variable_value = variable_value;
    }
}
