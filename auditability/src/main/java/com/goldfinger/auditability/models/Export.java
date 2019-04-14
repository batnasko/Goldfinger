package com.goldfinger.auditability.models;

public class Export  {
    private SearchFilter searchFilter;
    private String[] columnsToExport;

    public SearchFilter getSearchFilter() {
        return searchFilter;
    }

    public void setSearchFilter(SearchFilter searchFilter) {
        this.searchFilter = searchFilter;
    }

    public String[] getColumnsToExport() {
        return columnsToExport;
    }

    public void setColumnsToExport(String[] columnsToExport) {
        this.columnsToExport = columnsToExport;
    }
}
