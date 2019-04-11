package com.goldfinger.gis.repositories;

import com.goldfinger.gis.models.Point;
import com.goldfinger.gis.models.Shape;
import com.goldfinger.gis.repositories.contracts.MapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.ResourceAccessException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
@PropertySource("classpath:application.properties")
public class MapRepositoryImpl implements MapRepository {

    private String dbUrl, username, password;

    @Autowired
    public MapRepositoryImpl(Environment environment) {
        dbUrl = environment.getProperty("database.url.jdbc");
        username = environment.getProperty("database.username");
        password = environment.getProperty("database.password");
    }

    @Override
    public Shape getShape(Point point, String tableName) {
        try (Connection connection = DriverManager.getConnection(dbUrl, username, password)) {
            String sql = "SELECT * FROM " + tableName + " WHERE CONTAINS(SHAPE,Point(" + point.getLatitude() + "," + point.getLongitude() + "));";
            
            ResultSet resultSet = connection.createStatement().executeQuery(sql);
            throw new NotImplementedException();
        } catch (SQLException e) {
            throw new ResourceAccessException(e.getMessage());
        }
    }
}
