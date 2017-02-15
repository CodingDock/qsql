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
│                                                 parameter │ value                                                    │
├───────────────────────────────────────────────────────────┼──────────────────────────────────────────────────────────┤
│                                         binlog_cache_size │ 32768                                                    │
│                                   bulk_insert_buffer_size │ 8388608                                                  │
│                                          have_query_cache │ YES                                                      │
│                                       interactive_timeout │ 28800                                                    │
│                                          join_buffer_size │ 262144                                                   │
│                                           key_buffer_size │ 8388608                                                  │
│                                   key_cache_age_threshold │ 300                                                      │
│                                      key_cache_block_size │ 1024                                                     │
│                                  key_cache_division_limit │ 100                                                      │
│                                               large_pages │ OFF                                                      │
│                                          locked_in_memory │ OFF                                                      │
│                                           long_query_time │ 10.000000                                                │
│                                        max_allowed_packet │ 4194304                                                  │
│                                     max_binlog_cache_size │ 18446744073709547520                                     │
│                                           max_binlog_size │ 1073741824                                               │
│                                           max_connections │ 151                                                      │
│                                        max_connect_errors │ 100                                                      │
│                                             max_join_size │ 18446744073709551615                                     │
│                                  max_length_for_sort_data │ 1024                                                     │
│                                         max_seeks_for_key │ 18446744073709551615                                     │
│                                           max_sort_length │ 1024                                                     │
│                                            max_tmp_tables │ 32                                                       │
│                                      max_user_connections │ 0                                                        │
│                                     optimizer_prune_level │ 1                                                        │
│                                    optimizer_search_depth │ 62                                                       │
│                                          query_cache_size │ 1048576                                                  │
│                                          query_cache_type │ OFF                                                      │
│                                       query_prealloc_size │ 8192                                                     │
│                                    range_alloc_block_size │ 4096                                                     │
│                                          read_buffer_size │ 131072                                                   │
│                                      read_rnd_buffer_size │ 262144                                                   │
│                                          sort_buffer_size │ 262144                                                   │
│                                                  sql_mode │ ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,N │
│                                                           │ O_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_US │
│                                                           │ ER,NO_ENGINE_SUBSTITUTION                                │
│                                         thread_cache_size │ 9                                                        │
│                                            tmp_table_size │ 16777216                                                 │
│                                              wait_timeout │ 28800                                                    │
└───────────────────────────────────────────────────────────┴──────────────────────────────────────────────────────────┘
┌──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┐
│                                                   Optimizer Switch                                                   │
├───────────────────────────────────────────────────────────┬──────────────────────────────────────────────────────────┤
│                                               switch_name │ value                                                    │
├───────────────────────────────────────────────────────────┼──────────────────────────────────────────────────────────┤
│                                               index_merge │ on                                                       │
│                                         index_merge_union │ on                                                       │
│                                    index_merge_sort_union │ on                                                       │
│                                  index_merge_intersection │ on                                                       │
│                                 engine_condition_pushdown │ on                                                       │
│                                  index_condition_pushdown │ on                                                       │
│                                                       mrr │ on                                                       │
│                                            mrr_cost_based │ on                                                       │
│                                         block_nested_loop │ on                                                       │
│                                        batched_key_access │ off                                                      │
│                                           materialization │ on                                                       │
│                                                  semijoin │ on                                                       │
│                                                 loosescan │ on                                                       │
│                                                firstmatch │ on                                                       │
│                                          duplicateweedout │ on                                                       │
│                       subquery_materialization_cost_based │ on                                                       │
│                                      use_index_extensions │ on                                                       │
│                                   condition_fanout_filter │ on                                                       │
│                                             derived_merge │ on                                                       │
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
┌──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┐
│ explain report                                                                                                                                                                                                           │
├────────────────┬────────────────┬────────────────┬────────────────┬────────────────┬────────────────┬────────────────┬────────────────┬────────────────┬────────────────┬────────────────┬───────────────────────────────┤
│ id             │ select_type    │ table          │ partitions     │ type           │ possible_keys  │ key            │ key_len        │ ref            │ rows           │ filtered       │ Extra                          │
├────────────────┼────────────────┼────────────────┼────────────────┼────────────────┼────────────────┼────────────────┼────────────────┼────────────────┼────────────────┼────────────────┼───────────────────────────────┤
│ 1              │ SIMPLE         │ t_user         │                │ ALL            │ PRIMARY        │                │                │                │ 3              │ 100.0          │                                │
│ 1              │ SIMPLE         │ t_topic        │                │ ALL            │                │                │                │                │ 21             │ 4.76           │ Using where;                   │
│                │                │                │                │                │                │                │                │                │                │                │ FirstMatch(t_user);            │
│                │                │                │                │                │                │                │                │                │                │                │ Using join buffer (Block       │
│                │                │                │                │                │                │                │                │                │                │                │ Nested Loop)                   │
└────────────────┴────────────────┴────────────────┴────────────────┴────────────────┴────────────────┴────────────────┴────────────────┴────────────────┴────────────────┴────────────────┴───────────────────────────────┘
┌──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┐
│ explain message report                                                                                                                                                                                                   │
├────────────────┬────────────────┬────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┤
│ level          │ code           │ message                                                                                                                                                                                 │
├────────────────┼────────────────┼────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┤
│ Warning        │ 1681           │ 'EXTENDED' is deprecated and will be removed in a future release.                                                                                                                       │
│ Note           │ 1003           │ /* select#1 */ select `annal`.`t_user`.`username` AS `username`,`annal`.`t_user`.`password` AS `password`,`annal`.`t_user`.`nickname` AS `nickname`,`annal`.`t_user`.`email`            │
│                │                │ AS `email`,`annal`.`t_user`.`avatar` AS `avatar`,`annal`.`t_user`.`signature` AS `signature`,`annal`.`t_user`.`followers` AS `followers`,`annal`.`t_user`.`following` AS                │
│                │                │ `following`,`annal`.`t_user`.`topics` AS `topics`,`annal`.`t_user`.`privated` AS `privated`,`annal`.`t_user`.`state` AS `state`,`annal`.`t_user`.`created` AS                           │
│                │                │ `created`,`annal`.`t_user`.`logined` AS `logined` from `annal`.`t_user` semi join (`annal`.`t_topic`) where ((`annal`.`t_topic`.`stars` > 0) and                                        │
│                │                │ (`annal`.`t_user`.`username` = `annal`.`t_topic`.`username`))                                                                                                                           │
└────────────────┴────────────────┴────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┘

 :: optimizer sql ::

  select
    `annal`.`t_user`.`username`
  AS
    `username`,`annal`.`t_user`.`password`
  AS
    `password`,`annal`.`t_user`.`nickname`
  AS
    `nickname`,`annal`.`t_user`.`email`
  AS
    `email`,`annal`.`t_user`.`avatar`
  AS
    `avatar`,`annal`.`t_user`.`signature`
  AS
    `signature`,`annal`.`t_user`.`followers`
  AS
    `followers`,`annal`.`t_user`.`following`
  AS
    `following`,`annal`.`t_user`.`topics`
  AS
    `topics`,`annal`.`t_user`.`privated`
  AS
    `privated`,`annal`.`t_user`.`state`
  AS
    `state`,`annal`.`t_user`.`created`
  AS
    `created`,`annal`.`t_user`.`logined`
  AS
    `logined`
  from
    `annal`.`t_user` semi
  join
    (
      `annal`.`t_topic`
    )
  where
    (
      (
        `annal`.`t_topic`.`stars` > 0
      )
      and (
        `annal`.`t_user`.`username` = `annal`.`t_topic`.`username`
      )
    )

analyze finished.
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