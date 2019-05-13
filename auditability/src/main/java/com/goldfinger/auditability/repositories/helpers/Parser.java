package com.goldfinger.auditability.repositories.helpers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parser {

    public List<Map<String, String>> logs(ResultSet resultSet) throws SQLException {
        long curLogId = -1;
        List<Map<String, String>> logs = new ArrayList<>();
        Map<String, String> curLog = new HashMap<>();
        while (resultSet.next()) {
            if (resultSet.getLong("log_id") != curLogId) {
                curLogId = resultSet.getLong("log_id");
                curLog = new HashMap<>();
                logs.add(curLog);
            }
            curLog.put(resultSet.getString("key_"), resultSet.getString("value_"));
        }
        return logs;
    }

    public String list(List<Integer> list) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int item : list) {
            stringBuilder.append(item).append(",");
        }
        if (stringBuilder.length() > 0) {
            stringBuilder.setLength(stringBuilder.length() - 1);
        }
        return stringBuilder.toString();
    }
}
