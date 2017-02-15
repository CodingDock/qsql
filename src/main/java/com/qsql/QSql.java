package com.qsql;

import com.qsql.fmt.SQLFormatter;
import com.qsql.model.Explain;
import com.qsql.model.ExplainMsg;
import com.qsql.model.SysParam;
import com.qsql.utils.DBRes;
import com.qsql.utils.DBUtils;
import com.qsql.utils.StringUtils;
import de.vandermeer.asciitable.v2.RenderedTable;
import de.vandermeer.asciitable.v2.V2_AsciiTable;
import de.vandermeer.asciitable.v2.render.V2_AsciiTableRenderer;
import de.vandermeer.asciitable.v2.render.WidthAbsoluteEven;
import de.vandermeer.asciitable.v2.themes.V2_E_TableThemes;
import org.omg.CORBA.PRIVATE_MEMBER;

import java.util.List;
import java.util.Map;

/**
 * QSQL核心类
 * <p>
 * Created by biezhi on 2017/2/15.
 */
public class QSql {

    private Config config;

    private V2_AsciiTableRenderer rend = new V2_AsciiTableRenderer();

    private int width = 120;

    private final String SYS_PARAMS = "'BINLOG_CACHE_SIZE'," +
            "'BULK_INSERT_BUFFER_SIZE'," +
            "'HAVE_PARTITION_ENGINE'," +
            "'HAVE_QUERY_CACHE'," +
            "'INTERACTIVE_TIMEOUT'," +
            "'JOIN_BUFFER_SIZE'," +
            "'KEY_BUFFER_SIZE'," +
            "'KEY_CACHE_AGE_THRESHOLD'," +
            "'KEY_CACHE_BLOCK_SIZE'," +
            "'KEY_CACHE_DIVISION_LIMIT'," +
            "'LARGE_PAGES'," +
            "'LOCKED_IN_MEMORY'," +
            "'LONG_QUERY_TIME'," +
            "'MAX_ALLOWED_PACKET'," +
            "'MAX_BINLOG_CACHE_SIZE'," +
            "'MAX_BINLOG_SIZE'," +
            "'MAX_CONNECT_ERRORS'," +
            "'MAX_CONNECTIONS'," +
            "'MAX_JOIN_SIZE'," +
            "'MAX_LENGTH_FOR_SORT_DATA'," +
            "'MAX_SEEKS_FOR_KEY'," +
            "'MAX_SORT_LENGTH'," +
            "'MAX_TMP_TABLES'," +
            "'MAX_USER_CONNECTIONS'," +
            "'OPTIMIZER_PRUNE_LEVEL'," +
            "'OPTIMIZER_SEARCH_DEPTH'," +
            "'QUERY_CACHE_SIZE'," +
            "'QUERY_CACHE_TYPE'," +
            "'QUERY_PREALLOC_SIZE'," +
            "'RANGE_ALLOC_BLOCK_SIZE'," +
            "'READ_BUFFER_SIZE'," +
            "'READ_RND_BUFFER_SIZE'," +
            "'SORT_BUFFER_SIZE'," +
            "'SQL_MODE'," +
            "'TABLE_CACHE'," +
            "'THREAD_CACHE_SIZE'," +
            "'TMP_TABLE_SIZE'," +
            "'WAIT_TIMEOUT'";

    public QSql(Config config) {
        this.config = config;
        try {
            DBUtils.openConnection(config);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化操作，在这里初始化数据库连接
     */
    private void init() {
        rend.setTheme(V2_E_TableThemes.UTF_LIGHT.get());
        rend.setWidth(new WidthAbsoluteEven(width));

        V2_AsciiTable initTab = new V2_AsciiTable();
        initTab.addRule();
        initTab.addRow("QSQL v1.0 (by biezhi)").setAlignment(new char[]{'c'});
        initTab.addRule();
        RenderedTable rt = rend.render(initTab);
        System.out.print(rt);
    }

    /**
     * 数据库基本信息
     */
    private void dbinfo() {
        String db_name = DBUtils.queryObject("select database()", String.class);
        String mysql_version = DBUtils.queryObject("select @@version", String.class);

        V2_AsciiTable dbinfoTab = new V2_AsciiTable();
        dbinfoTab.addRule();
        dbinfoTab.addRow(null, null, null, "Basic Infomation").setAlignment(new char[]{'c', 'c', 'c', 'c'});
        dbinfoTab.addRule();
        dbinfoTab.addRow("host", "user_name", "db_name", "db_version").setAlignment(new char[]{'c', 'c', 'c', 'c'});
        dbinfoTab.addRule();
        dbinfoTab.addRow("127.0.0.1", config.getUser(), db_name, mysql_version).setAlignment(new char[]{'c', 'c', 'c', 'c'});
        dbinfoTab.addRule();

        RenderedTable rt = rend.render(dbinfoTab);
        System.out.print(rt);
    }

    /**
     * 系统参数信息
     */
    private void sys_param() {
        V2_AsciiTable dbinfoTab = new V2_AsciiTable();
        dbinfoTab.addRule();
        dbinfoTab.addRow(null, "System Paramter").setAlignment(new char[]{'c', 'c'});
        dbinfoTab.addRule();
        dbinfoTab.addRow("parameter", "value").setAlignment(new char[]{'r', 'l'});
        dbinfoTab.addRule();

        // 开启global_status，mysql5.7默认关闭
        DBUtils.execute("set global show_compatibility_56=on");

        String sql = "select lower(variable_name) as variable_name, variable_value from INFORMATION_SCHEMA.GLOBAL_VARIABLES where upper(variable_name) in (" + SYS_PARAMS + ") order by variable_name";
        List<SysParam> list = DBUtils.queryBeanList(sql, SysParam.class);
        for (SysParam sysParam : list) {
            dbinfoTab.addRow(sysParam.getVariable_name(), sysParam.getVariable_value()).setAlignment(new char[]{'r', 'l'});
        }
        dbinfoTab.addRule();
        RenderedTable rt = rend.render(dbinfoTab);
        System.out.print(rt);
    }

    /**
     * 优化器开关
     */
    private void optimizer_switch() {

        V2_AsciiTable dbinfoTab = new V2_AsciiTable();
        dbinfoTab.addRule();
        dbinfoTab.addRow(null, "Optimizer Switch").setAlignment(new char[]{'c', 'c'});
        dbinfoTab.addRule();

        dbinfoTab.addRow("switch_name", "value").setAlignment(new char[]{'r', 'l'});
        dbinfoTab.addRule();

        String sql = "select variable_value from INFORMATION_SCHEMA.GLOBAL_VARIABLES where upper(variable_name)='OPTIMIZER_SWITCH'";
        String value = DBUtils.queryObject(sql, String.class);
        String[] arr = value.split(",");
        for (String kv : arr) {
            String[] os = kv.split("=");
            dbinfoTab.addRow(os[0], os[1]).setAlignment(new char[]{'r', 'l'});
        }
        dbinfoTab.addRule();
        RenderedTable rt = rend.render(dbinfoTab);
        System.out.print(rt);
    }

    /**
     * 开始分析
     *
     * @param sql
     * @return
     */
    private QRes optimizer(String sql) {

        QRes qRes = new QRes();
        qRes.setOriginalSql(sql);

        System.out.println("\r\n >>>> original sql <<<< \r\n");
        StringUtils.ps('-', width);
        System.out.println(new SQLFormatter(sql).format());
        StringUtils.ps('-', width);

        String q = "explain extended " + sql;
        DBRes dbRes = DBUtils.queryNoCloseList(null, q, Explain.class);
        List<Explain> list = null != dbRes.getData() ? (List<Explain>) dbRes.getData() : null;
        if (null != list && !list.isEmpty() && config.isExplain()) {
            V2_AsciiTable expTab = new V2_AsciiTable();
            expTab.addRule();
            expTab.addRow(null, null, null, null, null, null, null, null, null, null, null, null, "explain report");
            expTab.addRule();
            expTab.addRow("id", "select_type", "table", "partitions", "type",
                    "possible_keys", "key", "key_len", "ref", "rows", "filtered", null, "Extra");
            expTab.addRule();

            for (Explain explain : list) {
                expTab.addRow(explain.getId(), explain.getSelect_type(), explain.getTable(),
                        explain.getPartitions(), explain.getType(), explain.getPossible_keys(),
                        explain.getKey(), explain.getKey_len(), explain.getRef(), explain.getRows(),
                        explain.getFiltered(), null, explain.getExtra().trim().replaceAll("; ", "; \r\n"));
            }
            expTab.addRule();
            rend.setWidth(new WidthAbsoluteEven(220));
            RenderedTable ert = rend.render(expTab);
            System.out.print(ert);
        }

        q = "show warnings";
        dbRes = DBUtils.queryNoCloseList(dbRes.getStatement(), q, ExplainMsg.class);
        List<ExplainMsg> listMap = null != dbRes.getData() ? (List<ExplainMsg>) dbRes.getData() : null;
        if (null != listMap && !listMap.isEmpty()) {
            V2_AsciiTable expMsgTab = new V2_AsciiTable();
            expMsgTab.addRule();
            expMsgTab.addRow(null, null, null, null, null, null, null, null, null, null, null, null, "explain message report");
            expMsgTab.addRule();
            expMsgTab.addRow("level", "code", null, null, null, null, null, null, null, null, null, null, "message");
            expMsgTab.addRule();

            for (ExplainMsg explainMsg : listMap) {
                expMsgTab.addRow(explainMsg.getLevel(), explainMsg.getCode(), null, null, null, null, null, null, null, null, null, null, explainMsg.getMessage());
                if (explainMsg.getCode() == 1003) {
                    qRes.setOptimizerSql(explainMsg.getMessage().replace("/* select#1 */ ", ""));
                }
            }
            if (config.isExplain_msg()) {
                expMsgTab.addRule();
                RenderedTable ert = rend.render(expMsgTab);
                System.out.print(ert);
            }
        }
        System.out.println();
        if(qRes.getOptimizerSql().length() > 0){
            System.out.println(" :: optimizer sql ::\r\n");
            System.out.println(qRes.getOptimizerFmtSql());
        }
        System.out.println();
        System.out.println("analyze finished.\r\n");
        DBUtils.close(null, dbRes.getStatement());
        DBUtils.closeConnection();

        return qRes;
    }

    /**
     * 统计数据
     */
    private void statistics(){

    }

    public void analyze(String sql) {
        this.init();
        if (config.isDbinfo()) {
            this.dbinfo();
        }
        if (config.isSysparam()) {
            this.sys_param();
        }
        if (config.isOptimizer_switch()) {
            this.optimizer_switch();
        }
        this.optimizer(sql);
    }

}