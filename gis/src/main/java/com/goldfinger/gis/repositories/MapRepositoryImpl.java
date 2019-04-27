package com.goldfinger.gis.repositories;

import com.goldfinger.gis.models.*;
import com.goldfinger.gis.repositories.contracts.MapRepository;
import com.goldfinger.gis.repositories.helpers.Parser;
import com.vividsolutions.jts.io.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.ResourceAccessException;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@PropertySource("classpath:application.properties")
public class MapRepositoryImpl implements MapRepository {

    private static final String SHAPE_NOT_FOUND = "Shape not found";
    private static final String DATA_TYPE_NOT_FOUND = "Data type not found";
    private static final String FAILED_TO_PARSE_SHAPE = "Failed to parseShape shape binary to Geometry";
    private static final String COULDNT_INSERT_DATATYPE = "Couldn't insert new datatype";

    private String dbUrl, username, password;
    private Parser parser;

    @Autowired
    public MapRepositoryImpl(Environment environment) {
        dbUrl = environment.getProperty("database.url.jdbc");
        username = environment.getProperty("database.username");
        password = environment.getProperty("database.password");
        this.parser = new Parser();
    }

    @Override
    public List<Shape> getAll(String tableName) {
        String sql = "SELECT * FROM " + tableName + ";";
        try (
                Connection connection = DriverManager.getConnection(dbUrl, username, password);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)
        ) {
            List<Shape> shapes = new ArrayList<>();
            while (resultSet.next()) {
                shapes.add(parser.parseShape(resultSet));
            }
            return shapes;
        } catch (SQLException e) {
            throw new ResourceAccessException(e.getMessage());
        } catch (ParseException | IOException e) {
            throw new IllegalArgumentException(FAILED_TO_PARSE_SHAPE);
        }
    }

    @Override
    public Shape getShape(Point point, String tableName) {
        String sql = "SELECT * FROM " + tableName + " WHERE CONTAINS(SHAPE,Point(" + point.getLatitude() + "," + point.getLongitude() + "));";
        try (
                Connection connection = DriverManager.getConnection(dbUrl, username, password);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)
        ) {
            if (resultSet.next()) {
                return parser.parseShape(resultSet);
            }
            throw new ResourceAccessException(SHAPE_NOT_FOUND);
        } catch (SQLException e) {
            throw new ResourceAccessException(e.getMessage());
        } catch (ParseException | IOException e) {
            throw new IllegalArgumentException(FAILED_TO_PARSE_SHAPE);
        }
    }

    @Override
    public List<DataType> getAllDataTypes() {
        String sql = "SELECT * FROM dataTypes;";
        try (
                Connection connection = DriverManager.getConnection(dbUrl, username, password);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)
        ) {
            List<DataType> dataTypes = new ArrayList<>();
            while (resultSet.next()) {
                dataTypes.add(parser.dataType(resultSet));
            }
            return dataTypes;
        } catch (SQLException e) {
            throw new ResourceAccessException(e.getMessage());
        }
    }

    @Override
    public DataType getDataType(int dataTypeId) {
        String sql = "SELECT * FROM dataTypes WHERE id = " + dataTypeId + ";";
        try (
                Connection connection = DriverManager.getConnection(dbUrl, username, password);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)
        ) {
            if (resultSet.next()) {
                return parser.dataType(resultSet);
            }
            throw new ResourceAccessException(DATA_TYPE_NOT_FOUND);
        } catch (SQLException e) {
            throw new ResourceAccessException(e.getMessage());
        }
    }

    @Override
    public List<String> getDataProperties(int dataTypeId) {
        String sql = "SELECT PROPERTY FROM dataproperties WHERE dataType_id = " + dataTypeId;
        try (
                Connection connection = DriverManager.getConnection(dbUrl, username, password);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)
        ) {
            List<String> dataProperties = new ArrayList<>();
            while (resultSet.next()) {
                dataProperties.add(resultSet.getString("property"));
            }
            return dataProperties;
        } catch (SQLException e) {
            throw new ResourceAccessException(e.getMessage());
        }
    }

    @Override
    public long saveDataType(String dataType, String tableName, String rowToColor) {
        String sql = "INSERT INTO datatypes (dataType,tableName,row_to_color) VALUES('" + dataType + "','" + tableName + "','" + rowToColor + "') ;";
        try (
                Connection connection = DriverManager.getConnection(dbUrl, username, password);
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ResultSet resultSet = getGeneratedKeys(statement)
        ) {
            if (resultSet.next()) {
                return resultSet.getLong(1);
            }
            throw new ResourceAccessException(COULDNT_INSERT_DATATYPE);
        } catch (SQLException e) {
            throw new ResourceAccessException(e.getMessage());
        }
    }

    @Override
    public void saveNewColumnToDisplay(long dataTypeId, String column) {
        String sql = "INSERT INTO dataproperties(dataType_id, property) VALUES ('" + dataTypeId + "','" + column + "');";
        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             Statement statement = connection.createStatement();
        ) {
            statement.execute(sql);
        } catch (SQLException e) {
            throw new ResourceAccessException(e.getMessage());
        }
    }

    private ResultSet getGeneratedKeys(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.execute();
        return preparedStatement.getGeneratedKeys();
    }

}