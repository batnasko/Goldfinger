package com.goldfinger.auditability.repositories;

import com.goldfinger.auditability.repositories.contracts.AuditabilityRepository;
import com.goldfinger.auditability.repositories.helpers.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.ResourceAccessException;

import java.sql.*;
import java.util.Map;


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
    public long addNewLog() {
        String sql = "INSERT INTO logs VALUES() ;";
        try (
                Connection connection = DriverManager.getConnection(dbUrl, username, password);
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ResultSet resultSet = getGeneratedKeys(statement)
        ) {
            if (resultSet.next()) {
                return resultSet.getLong(1);
            }
            throw new ResourceAccessException(COULDNT_INSERT_LOG_ID);
        } catch (SQLException e) {
            throw new ResourceAccessException(e.getMessage());
        }

    }

    @Override
    public Map<String, String> getLog(long logId) {
        String sql = "SELECT * FROM pairs WHERE log_id = " + logId + ";";
        try (
                Connection connection = DriverManager.getConnection(dbUrl, username, password);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
        ) {
            if (resultSet.next()){
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
            if (resultSet.next()){
                return resultSet.getLong("id");
            }
            throw new IllegalArgumentException();
        } catch (SQLException e) {
            throw new ResourceAccessException(e.getMessage());
        }
    }

    @Override
    public long addWord(String word) {
        String sql = "INSERT INTO words(word) VALUES('"+word+"');";
        try (
                Connection connection = DriverManager.getConnection(dbUrl, username, password);
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ResultSet resultSet = getGeneratedKeys(statement)
        ) {
            if (resultSet.next()) {
                return resultSet.getLong(1);
            }
            throw new ResourceAccessException(COULDNT_INSERT_WORD);
        } catch (SQLException e) {
            throw new ResourceAccessException(e.getMessage());
        }
    }

    @Override
    public boolean addWordLogRelation(long wordId, long logId) {
        String sql = "INSERT INTO word_log(word_id, log_id) VALUES('"+wordId+"','"+logId+"');";
        try (
                Connection connection = DriverManager.getConnection(dbUrl, username, password);
                Statement statement = connection.createStatement()
        ) {
           return statement.execute(sql);
        } catch (SQLException e) {
            throw new ResourceAccessException(e.getMessage());
        }
    }


    private ResultSet getGeneratedKeys(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.execute();
        return preparedStatement.getGeneratedKeys();
    }
}
