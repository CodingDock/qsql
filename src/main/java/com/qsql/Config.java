package com.qsql;

/**
 * Created by biezhi on 2017/2/15.
 */
public class Config {

    private String url;
    private String user;
    private String pass;
    private String db;

    /**
     * 是否显示数据库信息，默认显示
     */
    private boolean dbinfo = true;

    /**
     * 系统参数信息，默认显示
     */
    private boolean sysparam = true;

    /**
     * 优化器开关
     */
    private boolean optimizer_switch = true;

    /**
     * 是否显示执行计划，默认显示
     */
    private boolean explain = true;

    /**
     * 是否显示执行计划结果，默认显示
     */
    private boolean explain_msg = true;

    /**
     * sql语句是否格式化输出
     */
    private boolean sqlFmt = true;

    public Config() {
    }

    public Config(String url, String user, String pass) {
        this.url = url;
        this.user = user;
        this.pass = pass;
    }

    public Config(String url, String user, String pass, String db) {
        this.url = url;
        this.user = user;
        this.pass = pass;
        this.db = db;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public boolean isDbinfo() {
        return dbinfo;
    }

    public void setDbinfo(boolean dbinfo) {
        this.dbinfo = dbinfo;
    }

    public boolean isExplain() {
        return explain;
    }

    public void setExplain(boolean explain) {
        this.explain = explain;
    }

    public boolean isExplain_msg() {
        return explain_msg;
    }

    public void setExplain_msg(boolean explain_msg) {
        this.explain_msg = explain_msg;
    }

    public boolean isSqlFmt() {
        return sqlFmt;
    }

    public void setSqlFmt(boolean sqlFmt) {
        this.sqlFmt = sqlFmt;
    }

    public boolean isSysparam() {
        return sysparam;
    }

    public void setSysparam(boolean sysparam) {
        this.sysparam = sysparam;
    }

    public boolean isOptimizer_switch() {
        return optimizer_switch;
    }

    public void setOptimizer_switch(boolean optimizer_switch) {
        this.optimizer_switch = optimizer_switch;
    }
}
