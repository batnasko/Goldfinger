package com.goldfinger.gis.repositories;

import com.goldfinger.gis.models.Point;
import com.goldfinger.gis.models.Shape;
import com.goldfinger.gis.repositories.contracts.MapRepository;
import com.goldfinger.gis.repositories.helpers.ShapeParser;
import com.vividsolutions.jts.io.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.ResourceAccessException;
import java.io.IOException;
import java.sql.*;

@Repository
@PropertySource("classpath:application.properties")
public class MapRepositoryImpl implements MapRepository {
    private static final String SHAPE_NOT_FOUND = "Shape not found";
    private static final String FAILED_TO_PARSE_SHAPE = "Failed to parse shape binary to Geometry";

    private String dbUrl, username, password;
    private ShapeParser shapeParser;

    @Autowired
    public MapRepositoryImpl(Environment environment) {
        dbUrl = environment.getProperty("database.url.jdbc");
        username = environment.getProperty("database.username");
        password = environment.getProperty("database.password");
        this.shapeParser = new ShapeParser();
    }

    @Override
    public Shape getShape(Point point, String tableName) {
        tableName = "soils";
        point.setLatitude(129.53);
        point.setLongitude(72.7);
        try (Connection connection = DriverManager.getConnection(dbUrl, username, password)) {
            String sql = "SELECT * FROM " + tableName + " WHERE CONTAINS(SHAPE,Point(" + point.getLatitude() + "," + point.getLongitude() + "));";
            ResultSet resultSet = connection.createStatement().executeQuery(sql);
            if (resultSet.next()){
                return shapeParser.parse(resultSet);
            }
            throw new ResourceAccessException(SHAPE_NOT_FOUND);
        } catch (SQLException e) {
            throw new ResourceAccessException(e.getMessage());
        } catch (ParseException | IOException e) {
            throw new IllegalArgumentException(FAILED_TO_PARSE_SHAPE);
        }
    }


}
