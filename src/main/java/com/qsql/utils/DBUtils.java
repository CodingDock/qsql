package com.qsql.utils;

import com.qsql.Config;
import com.qsql.model.Explain;

import java.beans.*;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.*;
import java.sql.Statement;
import java.util.*;
import java.util.Date;

/**
 * DBUtils，数据库访问工具类<br/>
 *
 * @preserve all
 */
public class DBUtils {

    private static Connection con = null;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {

        }
    }

    public static Connection openConnection() throws SQLException, ClassNotFoundException, IOException {
        if (null == con || con.isClosed()) {
            Properties p = new Properties();
            p.load(DBUtils.class.getResourceAsStream("/config-db.properties"));
            Class.forName(p.getProperty("db_driver"));
            con = DriverManager.getConnection(p.getProperty("db_url"), p.getProperty("db_username"),
                    p.getProperty("db_password"));
        }
        return con;
    }

    public static Connection openConnection(Config config) throws SQLException {
        con = DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPass());
        return con;
    }

    public static void closeConnection() {
        try {
            if (null != con)
                con.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con = null;
        }
    }

    public static List<Map<String, Object>> queryMapList(String sql) {
        List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
        Statement preStmt = null;
        ResultSet rs = null;
        try {
            preStmt = con.createStatement();
            rs = preStmt.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            while (null != rs && rs.next()) {
                Map<String, Object> map = new HashMap<String, Object>();
                for (int i = 0; i < columnCount; i++) {
                    String name = rsmd.getColumnName(i + 1);
                    Object value = rs.getObject(name);
                    map.put(name, value);
                }
                lists.add(map);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            close(rs, preStmt);
        }
        return lists;
    }

    public static List<Map<String, Object>> queryMapList(String sql, Object... params) {
        List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
        PreparedStatement preStmt = null;
        ResultSet rs = null;
        try {
            preStmt = con.prepareStatement(sql);
            for (int i = 0; i < params.length; i++)
                preStmt.setObject(i + 1, params[i]);// 下标从1开始
            rs = preStmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            while (null != rs && rs.next()) {
                Map<String, Object> map = new HashMap<String, Object>();
                for (int i = 0; i < columnCount; i++) {
                    String name = rsmd.getColumnName(i + 1);
                    Object value = rs.getObject(name);
                    map.put(name, value);
                }
                lists.add(map);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            close(rs, preStmt);
        }
        return lists;
    }

    public static <T> List<T> queryBeanList(String sql, Class<T> beanClass) {
        List<T> lists = new ArrayList<T>();
        Statement stmt = null;
        ResultSet rs = null;
        Field[] fields = null;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            fields = beanClass.getDeclaredFields();
            for (Field f : fields)
                f.setAccessible(true);
            while (null != rs && rs.next()) {
                T t = beanClass.newInstance();
                for (Field f : fields) {
                    String name = f.getName();
                    try {
                        Object value = rs.getObject(name);
                        setValue(t, f, value);
                    } catch (Exception e) {
                    }
                }
                lists.add(t);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            close(rs, stmt);
        }
        return lists;
    }

    public static <T> DBRes queryNoCloseList(Statement stmt, String sql, Class<T> beanClass) {
        DBRes dbRes = new DBRes();
        List<T> lists = new ArrayList<T>();
        ResultSet rs = null;
        Field[] fields = null;
        try {
            if (null == stmt) {
                stmt = con.createStatement();
            }
            rs = stmt.executeQuery(sql);
            fields = beanClass.getDeclaredFields();
            for (Field f : fields)
                f.setAccessible(true);
            while (null != rs && rs.next()) {
                T t = beanClass.newInstance();
                for (Field f : fields) {
                    String name = f.getName();
                    try {
                        Object value = rs.getObject(name);
                        setValue(t, f, value);
                    } catch (Exception e) {
                    }
                }
                lists.add(t);
            }
            dbRes.setData(lists);
            dbRes.setStatement(stmt);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return dbRes;
    }

    public static <T> List<T> queryBeanList(String sql, Class<T> beanClass, Object... params) {
        List<T> lists = new ArrayList<T>();
        PreparedStatement preStmt = null;
        ResultSet rs = null;
        Field[] fields = null;
        try {
            preStmt = con.prepareStatement(sql);
            for (int i = 0; i < params.length; i++)
                preStmt.setObject(i + 1, params[i]);// 下标从1开始
            rs = preStmt.executeQuery();
            fields = beanClass.getDeclaredFields();
            for (Field f : fields)
                f.setAccessible(true);
            while (null != rs && rs.next()) {
                T t = beanClass.newInstance();
                for (Field f : fields) {
                    String name = f.getName();
                    try {
                        Object value = rs.getObject(name);
                        setValue(t, f, value);
                    } catch (Exception e) {
                    }
                }
                lists.add(t);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            close(rs, preStmt);
        }
        return lists;
    }

    public static <T> List<T> queryBeanList(String sql, IResultSetCall<T> qdi) throws SQLException {
        List<T> lists = new ArrayList<T>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            while (null != rs && rs.next())
                lists.add(qdi.invoke(rs));
        } finally {
            close(rs, stmt);
        }
        return lists;
    }

    public static <T> List<T> queryBeanList(String sql, IResultSetCall<T> qdi, Object... params)
            throws SQLException {
        List<T> lists = new ArrayList<T>();
        PreparedStatement preStmt = null;
        ResultSet rs = null;
        try {
            preStmt = con.prepareStatement(sql);
            for (int i = 0; i < params.length; i++)
                preStmt.setObject(i + 1, params[i]);
            rs = preStmt.executeQuery();
            while (null != rs && rs.next())
                lists.add(qdi.invoke(rs));
        } finally {
            close(rs, preStmt);
        }
        return lists;
    }

    public static <T> T queryBean(String sql, Class<T> beanClass) {
        List<T> lists = queryBeanList(sql, beanClass);
        if (lists.size() != 1)
            throw new RuntimeException("SqlError：期待一行返回值，却返回了太多行！");
        return lists.get(0);
    }

    public static <T> T queryBean(String sql, Class<T> beanClass, Object... params) {
        List<T> lists = queryBeanList(sql, beanClass, params);
        if (lists.size() != 1)
            throw new RuntimeException("SqlError：期待一行返回值，却返回了太多行！");
        return lists.get(0);
    }

    public static <T> List<T> queryObjectList(String sql, Class<T> objClass) {
        List<T> lists = new ArrayList<T>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            label:
            while (null != rs && rs.next()) {
                Constructor<?>[] constor = objClass.getConstructors();
                for (Constructor<?> c : constor) {
                    Object value = rs.getObject(1);
                    try {
                        lists.add((T) c.newInstance(value));
                        continue label;
                    } catch (Exception e) {
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(rs, stmt);
        }
        return lists;
    }


    public static <T> List<T> queryObjectList(String sql, Class<T> objClass, Object... params) {
        List<T> lists = new ArrayList<T>();
        PreparedStatement preStmt = null;
        ResultSet rs = null;
        try {
            preStmt = con.prepareStatement(sql);
            for (int i = 0; i < params.length; i++)
                preStmt.setObject(i + 1, params[i]);
            rs = preStmt.executeQuery();
            label:
            while (null != rs && rs.next()) {
                Constructor<?>[] constor = objClass.getConstructors();
                for (Constructor<?> c : constor) {
                    String value = rs.getObject(1).toString();
                    try {
                        T t = (T) c.newInstance(value);
                        lists.add(t);
                        continue label;
                    } catch (Exception e) {
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            close(rs, preStmt);
        }
        return lists;
    }

    public static <T> T queryObject(String sql, Class<T> objClass) {
        List<T> lists = queryObjectList(sql, objClass);
        if (lists.size() != 1)
            throw new RuntimeException("SqlError：期待一行返回值，却返回了太多行！");
        return lists.get(0);
    }

    public static <T> T queryObject(String sql, Class<T> objClass, Object... params) {
        List<T> lists = queryObjectList(sql, objClass, params);
        if (lists.size() != 1)
            throw new RuntimeException("SqlError：期待一行返回值，却返回了太多行！");
        return lists.get(0);
    }

    public static int execute(String sql) {
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            return stmt.executeUpdate(sql);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            close(null, stmt);
        }
    }

    public static int execute(String sql, Object... params) {
        PreparedStatement preStmt = null;
        try {
            preStmt = con.prepareStatement(sql);
            for (int i = 0; i < params.length; i++)
                preStmt.setObject(i + 1, params[i]);// 下标从1开始
            return preStmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            close(null, preStmt);
        }
    }

    public static int[] executeAsBatch(List<String> sqlList) {
        return executeAsBatch(sqlList.toArray(new String[]{}));
    }

    public static int[] executeAsBatch(String[] sqlArray) {
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            for (String sql : sqlArray) {
                stmt.addBatch(sql);
            }
            return stmt.executeBatch();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            close(null, stmt);
        }
    }

    public static int[] executeAsBatch(String sql, Object[][] params) {
        PreparedStatement preStmt = null;
        try {
            preStmt = con.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                Object[] rowParams = params[i];
                for (int k = 0; k < rowParams.length; k++) {
                    Object obj = rowParams[k];
                    preStmt.setObject(k + 1, obj);
                }
                preStmt.addBatch();
            }
            return preStmt.executeBatch();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            close(null, preStmt);
        }
    }

    private static <T> void setValue(T t, Field f, Object value) throws IllegalAccessException {
        // TODO 以数据库类型为准绳，还是以java数据类型为准绳？还是混合两种方式？
        if (null == value)
            return;
        String v = value.toString();
        String n = f.getType().getName();
        if ("java.lang.Byte".equals(n) || "byte".equals(n)) {
            f.set(t, Byte.parseByte(v));
        } else if ("java.lang.Short".equals(n) || "short".equals(n)) {
            f.set(t, Short.parseShort(v));
        } else if ("java.lang.Integer".equals(n) || "int".equals(n)) {
            f.set(t, Integer.parseInt(v));
        } else if ("java.lang.Long".equals(n) || "long".equals(n)) {
            f.set(t, Long.parseLong(v));
        } else if ("java.lang.Float".equals(n) || "float".equals(n)) {
            f.set(t, Float.parseFloat(v));
        } else if ("java.lang.Double".equals(n) || "double".equals(n)) {
            f.set(t, Double.parseDouble(v));
        } else if ("java.lang.String".equals(n)) {
            f.set(t, value.toString());
        } else if ("java.lang.Character".equals(n) || "char".equals(n)) {
            f.set(t, (Character) value);
        } else if ("java.lang.Date".equals(n)) {
            f.set(t, new Date(((java.sql.Date) value).getTime()));
        } else if ("java.lang.Timer".equals(n)) {
            f.set(t, new Time(((java.sql.Time) value).getTime()));
        } else if ("java.sql.Timestamp".equals(n)) {
            f.set(t, (java.sql.Timestamp) value);
        } else {
            System.out.println("SqlError：暂时不支持此数据类型，请使用其他类型代替此类型！");
        }
    }

    public static void executeProcedure(Connection con, String procedureName, Object... params) {
        CallableStatement proc = null;
        try {
            proc = con.prepareCall(procedureName);
            for (int i = 0; i < params.length; i++) {
                proc.setObject(i + 1, params[i]);
            }
            proc.execute();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            close(null, proc);
        }
    }

    public static <T> List<List<T>> listLimit(List<T> lists, int pageSize) {
        List<List<T>> llists = new ArrayList<List<T>>();
        for (int i = 0; i < lists.size(); i = i + pageSize) {
            try {
                List<T> list = lists.subList(i, i + pageSize);
                llists.add(list);
            } catch (IndexOutOfBoundsException e) {
                List<T> list = lists.subList(i, i + (lists.size() % pageSize));
                llists.add(list);
            }
        }
        return llists;
    }

    public static void close(ResultSet rs, Statement statement) {
        try {
            if (null != rs) {
                rs.close();
            }
            if (null != statement) {
                statement.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}