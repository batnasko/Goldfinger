package com.goldfinger.gis.repositories.helpers;

import com.goldfinger.gis.models.Shape;
import com.vividsolutions.jts.io.ParseException;
import org.springframework.web.client.ResourceAccessException;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ShapeParser {
    private GeometryParser geometryParser;

    public ShapeParser() {
        geometryParser = new GeometryParser();
    }

    public Shape parse(ResultSet resultSet) throws IOException, ParseException, SQLException {
        Shape shape = new Shape();
        shape.setId(resultSet.getInt("OGR_FID"));
        shape.setGeometry(geometryParser.parse(resultSet.getBinaryStream("SHAPE")));

        ResultSetMetaData resultSetColumns = resultSet.getMetaData();

        Map<String, String> properties = new HashMap<>();
        for (int i = 1; i <= resultSetColumns.getColumnCount(); i++) {
            if (resultSetColumns.getColumnName(i).equals("ORG_FID") || resultSetColumns.getColumnName(i).equals("SHAPE")) {
                continue;
            }
            properties.put(resultSetColumns.getColumnName(i), resultSet.getString(i));
        }
        shape.setProperties(properties);

        return shape;
    }
}
