package com.goldfinger.gis.repositories.helpers;

import com.goldfinger.gis.models.*;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.*;
import java.io.*;
import java.sql.*;
import java.util.*;

public class Parser {


    public Shape parseShape(ResultSet resultSet) throws IOException, ParseException, SQLException {
        Shape shape = new Shape();
        shape.setGeometry(parseGeometry(resultSet.getBinaryStream("SHAPE")));

        ResultSetMetaData resultSetColumns = resultSet.getMetaData();

        Map<String, String> properties = new HashMap<>();
        for (int i = 1; i <= resultSetColumns.getColumnCount(); i++) {
            if (resultSetColumns.getColumnName(i).equals("SHAPE")) {
                continue;
            }
            properties.put(resultSetColumns.getColumnName(i), resultSet.getString(i));
        }
        shape.setProperties(properties);

        return shape;
    }

    public DataType dataType(ResultSet resultSet)throws SQLException{
        DataType dataType = new DataType();

        dataType.setDataType(resultSet.getString("dataType"));
        dataType.setId(resultSet.getInt("id"));
        dataType.setTableName(resultSet.getString("tableName"));
        dataType.setRowToColor(resultSet.getString("row_to_color"));

        return dataType;
    }


    private Geometry parseGeometry(InputStream inputStream) throws IOException, ParseException {

        Geometry dbGeometry = null;

        if (inputStream != null) {

            //convert the stream to a byte[] array
            //so it can be passed to the WKBReader
            byte[] buffer = new byte[255];

            int bytesRead;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }

            byte[] geometryAsBytes = baos.toByteArray();

            if (geometryAsBytes.length < 5) {
                throw new IllegalArgumentException("Invalid geometry inputStream - less than five bytes");
            }

            //first four bytes of the geometry are the SRID,
            //followed by the actual WKB.  Determine the SRID
            //here
            byte[] sridBytes = new byte[4];
            System.arraycopy(geometryAsBytes, 0, sridBytes, 0, 4);
            boolean bigEndian = (geometryAsBytes[4] == 0x00);

            int srid = 0;
            if (bigEndian) {
                for (byte sridByte : sridBytes) {
                    srid = (srid << 8) + (sridByte & 0xff);
                }
            } else {
                for (int i = 0; i < sridBytes.length; i++) {
                    srid += (sridBytes[i] & 0xff) << (8 * i);
                }
            }

            //use the JTS WKBReader for WKB parsing
            WKBReader wkbReader = new WKBReader();

            //copy the byte array, removing the first four
            //SRID bytes
            byte[] wkb = new byte[geometryAsBytes.length - 4];
            System.arraycopy(geometryAsBytes, 4, wkb, 0, wkb.length);
            dbGeometry = wkbReader.read(wkb);
            dbGeometry.setSRID(srid);
        }

        return dbGeometry;
    }
}
