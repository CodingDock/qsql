# qsql

`QSQL` 是一个帮助你分析 mysql 查询语句并优化的小工具，原理是使用 `explain` 执行计划。

## 使用

```java
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

```

##  展示

```bash
/Library/Java/JavaVirtualMachines/jdk1.8.0_101.jdk/Contents/Home/bin/java -agentlib:jdwp=transport=dt_socket,address=127.0.0.1:50058,suspend=y,server=n -ea -Didea.junit.sm_runner -Dfile.encoding=UTF-8 -classpath "/Applications/IntelliJ IDEA.app/Contents/lib/idea_rt.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/junit/lib/junit-rt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_101.jdk/Contents/Home/jre/lib/charsets.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_101.jdk/Contents/Home/jre/lib/deploy.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_101.jdk/Contents/Home/jre/lib/ext/cldrdata.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_101.jdk/Contents/Home/jre/lib/ext/dnsns.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_101.jdk/Contents/Home/jre/lib/ext/jaccess.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_101.jdk/Contents/Home/jre/lib/ext/jfxrt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_101.jdk/Contents/Home/jre/lib/ext/localedata.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_101.jdk/Contents/Home/jre/lib/ext/nashorn.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_101.jdk/Contents/Home/jre/lib/ext/sunec.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_101.jdk/Contents/Home/jre/lib/ext/sunjce_provider.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_101.jdk/Contents/Home/jre/lib/ext/sunpkcs11.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_101.jdk/Contents/Home/jre/lib/ext/zipfs.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_101.jdk/Contents/Home/jre/lib/javaws.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_101.jdk/Contents/Home/jre/lib/jce.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_101.jdk/Contents/Home/jre/lib/jfr.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_101.jdk/Contents/Home/jre/lib/jfxswt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_101.jdk/Contents/Home/jre/lib/jsse.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_101.jdk/Contents/Home/jre/lib/management-agent.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_101.jdk/Contents/Home/jre/lib/plugin.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_101.jdk/Contents/Home/jre/lib/resources.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_101.jdk/Contents/Home/jre/lib/rt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_101.jdk/Contents/Home/lib/ant-javafx.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_101.jdk/Contents/Home/lib/dt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_101.jdk/Contents/Home/lib/javafx-mx.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_101.jdk/Contents/Home/lib/jconsole.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_101.jdk/Contents/Home/lib/packager.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_101.jdk/Contents/Home/lib/sa-jdi.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_101.jdk/Contents/Home/lib/tools.jar:/Users/biezhi/workspace/projects/java/qsql/target/test-classes:/Users/biezhi/workspace/projects/java/qsql/target/classes:/usr/local/apache-maven-3.3.9/repo/mysql/mysql-connector-java/5.1.40/mysql-connector-java-5.1.40.jar:/usr/local/apache-maven-3.3.9/repo/de/vandermeer/asciitable/0.2.5/asciitable-0.2.5.jar:/usr/local/apache-maven-3.3.9/repo/de/vandermeer/asciilist/0.0.3/asciilist-0.0.3.jar:/usr/local/apache-maven-3.3.9/repo/org/apache/commons/commons-lang3/3.4/commons-lang3-3.4.jar:/usr/local/apache-maven-3.3.9/repo/junit/junit/4.12/junit-4.12.jar:/usr/local/apache-maven-3.3.9/repo/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar" com.intellij.rt.execution.junit.JUnitStarter -ideVersion5 com.qsql.QSqlTest
Connected to the target VM, address: '127.0.0.1:50058', transport: 'socket'
Wed Feb 15 22:56:09 CST 2017 WARN: Establishing SSL connection without server's identity verification is not recommended. According to MySQL 5.5.45+, 5.6.26+ and 5.7.6+ requirements SSL connection must be established by default if explicit option isn't set. For compliance with existing applications not using SSL the verifyServerCertificate property is set to 'false'. You need either to explicitly disable SSL by setting useSSL=false, or set useSSL=true and provide truststore for server certificate verification.
┌──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┐
│                                                QSQL v1.0 (by biezhi)                                                 │
└──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┘
┌──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┐
│                                                   Basic Infomation                                                   │
├─────────────────────────────┬─────────────────────────────┬─────────────────────────────┬────────────────────────────┤
│            host             │          user_name          │           db_name           │         db_version         │
├─────────────────────────────┼─────────────────────────────┼─────────────────────────────┼────────────────────────────┤
│          127.0.0.1          │            root             │            annal            │           5.7.15           │
└─────────────────────────────┴─────────────────────────────┴─────────────────────────────┴────────────────────────────┘
┌──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┐
│                                                   System Paramter                                                    │
├───────────────────────────────────────────────────────────┬──────────────────────────────────────────────────────────┤
│                           host                            │                        user_name                         │
└───────────────────────────────────────────────────────────┴──────────────────────────────────────────────────────────┘

 >>>> original sql <<<<

------------------------------------------------------------------------------------------------------------------------
  select
    *
  from
    t_user
  where
    username

  in (
    select
      username from
        t_topic
      where
        stars > 0
    )
------------------------------------------------------------------------------------------------------------------------

 >>>> explain report <<<<

┌────────────────┬────────────────┬────────────────┬────────────────┬────────────────┬────────────────┬────────────────┬────────────────┬────────────────┬────────────────┬────────────────┬───────────────────────────────┐
│ id             │ select_type    │ table          │ partitions     │ type           │ possible_keys  │ key            │ key_len        │ ref            │ rows           │ filtered       │ Extra                          │
├────────────────┼────────────────┼────────────────┼────────────────┼────────────────┼────────────────┼────────────────┼────────────────┼────────────────┼────────────────┼────────────────┼───────────────────────────────┤
│ 1              │ SIMPLE         │ t_user         │                │ ALL            │ PRIMARY        │                │                │                │ 3              │ 100.0          │                                │
│ 1              │ SIMPLE         │ t_topic        │                │ ALL            │                │                │                │                │ 21             │ 4.76           │ Using where;                   │
│                │                │                │                │                │                │                │                │                │                │                │ FirstMatch(t_user);            │
│                │                │                │                │                │                │                │                │                │                │                │ Using join buffer (Block       │
│                │                │                │                │                │                │                │                │                │                │                │ Nested Loop)                   │
└────────────────┴────────────────┴────────────────┴────────────────┴────────────────┴────────────────┴────────────────┴────────────────┴────────────────┴────────────────┴────────────────┴───────────────────────────────┘

 >>>> explain message report <<<<

┌────────────────┬────────────────┬────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┐
│ level          │ code           │ message                                                                                                                                                                                 │
├────────────────┼────────────────┼────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┤
│ Warning        │ 1681           │ 'EXTENDED' is deprecated and will be removed in a future release.                                                                                                                       │
│ Note           │ 1003           │ /* select#1 */ select `annal`.`t_user`.`username` AS `username`,`annal`.`t_user`.`password` AS `password`,`annal`.`t_user`.`nickname` AS `nickname`,`annal`.`t_user`.`email`            │
│                │                │ AS `email`,`annal`.`t_user`.`avatar` AS `avatar`,`annal`.`t_user`.`signature` AS `signature`,`annal`.`t_user`.`followers` AS `followers`,`annal`.`t_user`.`following` AS                │
│                │                │ `following`,`annal`.`t_user`.`topics` AS `topics`,`annal`.`t_user`.`privated` AS `privated`,`annal`.`t_user`.`state` AS `state`,`annal`.`t_user`.`created` AS                           │
│                │                │ `created`,`annal`.`t_user`.`logined` AS `logined` from `annal`.`t_user` semi join (`annal`.`t_topic`) where ((`annal`.`t_topic`.`stars` > 0) and                                        │
│                │                │ (`annal`.`t_user`.`username` = `annal`.`t_topic`.`username`))                                                                                                                           │
└────────────────┴────────────────┴────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┘

analyze finished.

Disconnected from the target VM, address: '127.0.0.1:50058', transport: 'socket'
优化后语句:

select `annal`.`t_user`.`username` AS `username`,`annal`.`t_user`.`password` AS `password`,`annal`.`t_user`.`nickname` AS `nickname`,`annal`.`t_user`.`email` AS `email`,`annal`.`t_user`.`avatar` AS `avatar`,`annal`.`t_user`.`signature` AS `signature`,`annal`.`t_user`.`followers` AS `followers`,`annal`.`t_user`.`following` AS `following`,`annal`.`t_user`.`topics` AS `topics`,`annal`.`t_user`.`privated` AS `privated`,`annal`.`t_user`.`state` AS `state`,`annal`.`t_user`.`created` AS `created`,`annal`.`t_user`.`logined` AS `logined` from `annal`.`t_user` semi join (`annal`.`t_topic`) where ((`annal`.`t_topic`.`stars` > 0) and (`annal`.`t_user`.`username` = `annal`.`t_topic`.`username`))

Process finished with exit code 0

```

## 术语

- id: SELECT 查询的标识符. 每个 SELECT 都会自动分配一个唯一的标识符.
- select_type: SELECT 查询的类型.
- table: 查询的是哪个表
- partitions: 匹配的分区
- type: join 类型
- possible_keys: 此次查询中可能选用的索引
- key: 此次查询中确切使用到的索引.
- ref: 哪个字段或常数与 key 一起被使用
- rows: 显示此查询一共扫描了多少行. 这个是一个估计值.
- filtered: 表示此查询条件所过滤的数据的百分比
- extra: 额外的信息

### select_type

`select_type` 表示了查询的类型, 它的常用取值有:

* `SIMPLE`, 表示此查询不包含 `UNION` 查询或子查询
* `PRIMARY`, 表示此查询是最外层的查询
* `UNION`, 表示此查询是 `UNION` 的第二或随后的查询
* `DEPENDENT UNION`, `UNION` 中的第二个或后面的查询语句, 取决于外面的查询
* `UNION RESULT`, `UNION` 的结果
* `SUBQUERY`, 子查询中的第一个 `SELECT`
* `DEPENDENT SUBQUERY`: 子查询中的第一个 `SELECT`, 取决于外面的查询. 即子查询依赖于外层查询的结果.