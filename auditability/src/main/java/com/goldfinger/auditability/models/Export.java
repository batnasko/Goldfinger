package com.goldfinger.auditability.models;

public class Export extends SearchFilter {
    private String[] columnsToExport;

    public String[] getColumnsToExport() {
        return columnsToExport;
    }

    public void setColumnsToExport(String[] columnsToExport) {
        this.columnsToExport = columnsToExport;
    }
}
