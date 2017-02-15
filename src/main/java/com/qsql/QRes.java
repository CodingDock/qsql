package com.qsql;

import com.qsql.fmt.SQLFormatter;

import java.io.Serializable;

/**
 * 优化后分析结果
 *
 * Created by biezhi on 2017/2/15.
 */
public class QRes implements Serializable {

    /**
     * 原始sql语句
     */
    private String originalSql;

    /**
     * 优化后sql
     */
    private String optimizerSql;

    /**
     * 优化后sql格式化输出
     */
    private String optimizerFmtSql;

    public QRes() {

    }

    public String getOriginalSql() {
        return originalSql;
    }

    public void setOriginalSql(String originalSql) {
        this.originalSql = originalSql;
    }

    public String getOptimizerSql() {
        return optimizerSql;
    }

    public void setOptimizerSql(String optimizerSql) {
        this.optimizerSql = optimizerSql;
    }

    public String getOptimizerFmtSql() {
        return new SQLFormatter(optimizerSql).format();
    }
}
