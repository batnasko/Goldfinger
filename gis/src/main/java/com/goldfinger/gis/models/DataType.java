package com.goldfinger.gis.models;

public class DataType {

    private int id;
    private String dataType;
    private String tableName;
    private String rowToColor;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getRowToColor() {
        return rowToColor;
    }

    public void setRowToColor(String rowToColor) {
        this.rowToColor = rowToColor;
    }
}
