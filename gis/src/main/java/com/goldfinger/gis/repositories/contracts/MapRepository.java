package com.goldfinger.gis.repositories.contracts;

import com.goldfinger.gis.models.DataProperties;
import com.goldfinger.gis.models.DataType;
import com.goldfinger.gis.models.Point;
import com.goldfinger.gis.models.Shape;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


public interface MapRepository {
    List<Shape> getAll(String tableName);
    Shape getShape(Point point, String tableName);
    List<DataType> getAllDataTypes();
    DataType getDataType(int dataTypeId);
    List<DataProperties> getDataProperties(int dataTypeId);
    long saveDataType(String dataType, String tableName, String rowToColor);
    void saveNewColumnToDisplay(long dataTypeId, String column, boolean show);
    boolean changeProperty(int dataTypeId, String property, boolean show);
    boolean changeDataType(int dataTypeId, String rowToColor);
    List<String> getDataTypeMetaDataColumns(String tableName);
}
