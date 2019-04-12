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
    private static final String FAILED_TO_PARSE_SHAPE = "Failed to parseShape shape binary to Geometry";

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
        tableName = "soils";
        point.setLatitude(-155.655135);
        point.setLongitude(19.883514);
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

}