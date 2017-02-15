package com.qsql;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by biezhi on 2017/2/15.
 */
public class QSqlTest {

    private QSql qSql;

    @Before
    public void before(){
        String url = "jdbc:mysql://127.0.0.1:3306/annal";
        String user = "root";
        String pass = "123456";
        Config config = new Config(url, user, pass);
        qSql = new QSql(config);
    }

    private final String SQL1 = "select * from t_user where username in (select username from t_topic where stars > 0)";

    @Test
    public void test1(){
        qSql.analyze(SQL1);
    }

}
