package com.qsql.utils;

import java.sql.Statement;

/**
 * Created by biezhi on 2017/2/15.
 */
public class DBRes {

    private Statement statement;
    private Object data;

    public Statement getStatement() {
        return statement;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
