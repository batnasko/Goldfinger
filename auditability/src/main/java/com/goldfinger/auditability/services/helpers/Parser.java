package com.goldfinger.auditability.services.helpers;


import java.util.*;

public class Parser {
    public String logsToCSV(List<Map<String, String>> logs, String[] columnsToExport) {
        StringBuilder csv = new StringBuilder();

        for (String column : columnsToExport) {
            csv.append("\"").append(column).append("\",");
        }
        csv.setLength(csv.length() - 1);
        csv.append(System.lineSeparator());

        for (Map<String, String> log : logs) {
            for (String column: columnsToExport) {
                if (log.containsKey(column)){
                    csv.append("\"").append(log.get(column)).append("\"");
                }
                csv.append(",");
            }
            csv.setLength(csv.length() - 1);
            csv.append(System.lineSeparator());
        }
        return csv.toString();
    }
}
