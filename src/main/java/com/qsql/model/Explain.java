package com.qsql.model;

import java.io.Serializable;

/**
 * Created by biezhi on 2017/2/15.
 */
public class Explain implements Serializable {

    private int id;
    private String select_type = "";
    private String table = "";
    private String partitions = "";
    private String type = "";
    private String possible_keys = "";
    private String key = "";
    private String key_len = "";
    private String ref = "";
    private String rows = "";
    private String filtered = "";
    private String Extra = "";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSelect_type() {
        return select_type;
    }

    public void setSelect_type(String select_type) {
        this.select_type = select_type;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getPartitions() {
        return partitions;
    }

    public void setPartitions(String partitions) {
        this.partitions = partitions;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPossible_keys() {
        return possible_keys;
    }

    public void setPossible_keys(String possible_keys) {
        this.possible_keys = possible_keys;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey_len() {
        return key_len;
    }

    public void setKey_len(String key_len) {
        this.key_len = key_len;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getRows() {
        return rows;
    }

    public void setRows(String rows) {
        this.rows = rows;
    }

    public String getFiltered() {
        return filtered;
    }

    public void setFiltered(String filtered) {
        this.filtered = filtered;
    }

    public String getExtra() {
        return Extra;
    }

    public void setExtra(String extra) {
        Extra = extra;
    }

    @Override
    public String toString() {
        return "Explain [" +
                "id=" + id +
                ", select_type='" + select_type + '\'' +
                ", table='" + table + '\'' +
                ", partitions='" + partitions + '\'' +
                ", type='" + type + '\'' +
                ", possible_keys='" + possible_keys + '\'' +
                ", key='" + key + '\'' +
                ", key_len='" + key_len + '\'' +
                ", ref='" + ref + '\'' +
                ", rows='" + rows + '\'' +
                ", filtered='" + filtered + '\'' +
                ", Extra='" + Extra + '\'' +
                ']';
    }
}
