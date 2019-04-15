package com.goldfinger.auditability.repositories.helpers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Parser {
    public Map<String, String> log(ResultSet resultSet) throws SQLException {
        Map<String, String> log = new HashMap<>();
        log.put("log_id", resultSet.getString("log_id"));
        do {
            log.put(resultSet.getString("key_"), resultSet.getString("value_"));
        } while (resultSet.next());
        return log;
    }
}
