package com.goldfinger.auditability.models;

public class Export  {
    private String search;
    private String[] columnsToExport;

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String[] getColumnsToExport() {
        return columnsToExport;
    }

    public void setColumnsToExport(String[] columnsToExport) {
        this.columnsToExport = columnsToExport;
    }
}
