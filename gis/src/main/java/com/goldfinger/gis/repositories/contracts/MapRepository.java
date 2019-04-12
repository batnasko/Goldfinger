package com.goldfinger.gis.repositories.contracts;

import com.goldfinger.gis.models.DataType;
import com.goldfinger.gis.models.Point;
import com.goldfinger.gis.models.Shape;

import java.util.List;


public interface MapRepository {
    List<Shape> getAll(String tableName);
    Shape getShape(Point point, String tableName);
    List<DataType> getAllDataTypes();
}
