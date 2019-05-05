package com.goldfinger.auditability.repositories;

import com.goldfinger.auditability.models.Filter;
import com.goldfinger.auditability.repositories.contracts.AuditabilityRepository;
import com.goldfinger.auditability.repositories.helpers.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.ResourceAccessException;

import java.sql.*;
import java.util.*;


@Repository
@PropertySource("classpath:application.properties")
public class AuditabilityRepositoryImpl implements AuditabilityRepository {
    private static final String COULDNT_INSERT_LOG_ID = "Couldn't insert new log id";
    private static final String COULDNT_INSERT_WORD = "Couldn't insert new word";
    private static final String LOG_NOT_FOUND = "Log not found";

    private String dbUrl, username, password;
    private Parser parser;

    @Autowired
    public AuditabilityRepositoryImpl(Environment environment) {
        dbUrl = environment.getProperty("database.url.jdbc");
        username = environment.getProperty("database.username");
        password = environment.getProperty("database.password");
        parser = new Parser();
    }

    @Override
    public long addNewLog(String log) {
        String sql = "INSERT INTO logs(log) VALUES('" + log + "') ;";
        return executeQueryAndReturnGeneratedId(sql, COULDNT_INSERT_LOG_ID);
    }

    @Override
    public Map<String, String> getLog(long logId) {
        String sql = "SELECT * FROM pairs WHERE log_id = " + logId + ";";
        try (
                Connection connection = DriverManager.getConnection(dbUrl, username, password);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)
        ) {
            if (resultSet.next()) {
                return parser.log(resultSet);
            }
            throw new ResourceAccessException(LOG_NOT_FOUND);
        } catch (SQLException e) {
            throw new ResourceAccessException(e.getMessage());
        }
    }

    @Override
    public boolean addKeyValuePair(long logId, String key, String value) {
        String sql = "INSERT INTO pairs (log_id,key_,value_) VALUES (" + logId + ", '" + key + "','" + value + "') ;";
        try (
                Connection connection = DriverManager.getConnection(dbUrl, username, password);
                Statement statement = connection.createStatement()
        ) {
            statement.execute(sql);
            return true;
        } catch (SQLException e) {
            throw new ResourceAccessException(e.getMessage());
        }
    }

    @Override
    public long getWordId(String word) {
        String sql = "SELECT id FROM words where word ='" + word + "';";
        try (
                Connection connection = DriverManager.getConnection(dbUrl, username, password);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)
        ) {
            if (resultSet.next()) {
                return resultSet.getLong("id");
            }
            throw new IllegalArgumentException();
        } catch (SQLException e) {
            throw new ResourceAccessException(e.getMessage());
        }
    }

    @Override
    public long addWord(String word) {
        String sql = "INSERT INTO words(word) VALUES('" + word + "');";
        return executeQueryAndReturnGeneratedId(sql, COULDNT_INSERT_WORD);
    }

    @Override
    public boolean addWordLogRelation(long wordId, long logId) {
        String sql = "INSERT INTO word_log(word_id, log_id) VALUES('" + wordId + "','" + logId + "');";
        try (
                Connection connection = DriverManager.getConnection(dbUrl, username, password);
                Statement statement = connection.createStatement()
        ) {
            return statement.execute(sql);
        } catch (SQLException e) {
            throw new ResourceAccessException(e.getMessage());
        }
    }

    @Override
    public List<Integer> getAllLogs(Filter filter) {
        String sql = "SELECT distinct log_id from pairs";
        sql = addOrderToQuery(sql, filter);
        return executeSearchQuery(sql);
    }

    @Override
    public Set<Integer> wordOccurrences(String wordsToSearch) {
        String sql = "SELECT distinct log_id from word_log join words on word_id = words.id where word = '" + wordsToSearch + "';";
        try (
                Connection connection = DriverManager.getConnection(dbUrl, username, password);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)
        ) {
            Set<Integer> logs = new HashSet<>();
            while (resultSet.next()) {
                logs.add(resultSet.getInt("log_id"));
            }
            return logs;
        } catch (SQLException e) {
            throw new ResourceAccessException(e.getMessage());
        }
    }

    @Override
    public List<Integer> searchPhrase(String phrase, Filter filter) {
        String sql = "SELECT distinct log_id from pairs WHERE log_id IN (SELECT log_id from pairs where value_ like '%" + phrase + "%')";
        sql = addOrderToQuery(sql, filter);
        return executeSearchQuery(sql);
    }

    @Override
    public List<Integer> searchWordInPair(String key, String value, Filter filter) {
        String sql = "SELECT distinct log_id from pairs WHERE log_id IN (SELECT log_id from pairs where key_ = '" + key + "' AND value_ like '%" + value + "%')";
        sql = addOrderToQuery(sql, filter);
        return executeSearchQuery(sql);
    }

    @Override
    public List<Integer> searchExactTextInPairs(String key, String value, Filter filter) {
        String sql = "SELECT distinct log_id from pairs WHERE log_id IN (SELECT log_id from pairs where key_ = '" + key + "' AND value_ = '" + value + "')";
        sql = addOrderToQuery(sql, filter);
        return executeSearchQuery(sql);
    }

    @Override
    public List<Integer> fullTextSearch(String search) {
        String sql = "select log_id from logs where match(log) AGAINST('" + search + "' in boolean mode);";
        return executeSearchQuery(sql);
    }

    private String addOrderToQuery(String sql, Filter filter) {
        if (filter != null) {
            sql += " and key_ = '" + filter.getSortBy() + "' ORDER BY value_ " + filter.getOrder() + ";";
        }
        return sql;
    }

    private List<Integer> executeSearchQuery(String sql) {
        try (
                Connection connection = DriverManager.getConnection(dbUrl, username, password);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)
        ) {
            List<Integer> logs = new ArrayList<>();
            while (resultSet.next()) {
                logs.add(resultSet.getInt("log_id"));
            }
            return logs;
        } catch (SQLException e) {
            throw new ResourceAccessException(e.getMessage());
        }
    }

    private long executeQueryAndReturnGeneratedId(String sql, String errorMsg) {
        try (
                Connection connection = DriverManager.getConnection(dbUrl, username, password);
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ResultSet resultSet = getGeneratedKeys(statement)
        ) {
            if (resultSet.next()) {
                return resultSet.getLong(1);
            }
            throw new ResourceAccessException(errorMsg);
        } catch (SQLException e) {
            throw new ResourceAccessException(e.getMessage());
        }
    }

    private ResultSet getGeneratedKeys(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.execute();
        return preparedStatement.getGeneratedKeys();
    }
}
