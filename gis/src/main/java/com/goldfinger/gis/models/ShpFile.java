package com.goldfinger.gis.models;

public class ShpFile {
    private String file;
    private String shpFileName;
    private String columnsToShow;
    private String columnToColor;

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getShpFileName() {
        return shpFileName;
    }

    public void setShpFileName(String shpFileName) {
        this.shpFileName = shpFileName;
    }

    public String getColumnsToShow() {
        return columnsToShow;
    }

    public void setColumnsToShow(String columnsToShow) {
        this.columnsToShow = columnsToShow;
    }

    public String getColumnToColor() {
        return columnToColor;
    }

    public void setColumnToColor(String columnToColor) {
        this.columnToColor = columnToColor;
    }
}
