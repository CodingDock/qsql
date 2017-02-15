package com.qsql.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IResultSetCall<T> {

    T invoke(ResultSet rs) throws SQLException;

}