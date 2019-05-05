package com.goldfinger.gis.services.contracts;

import com.goldfinger.gis.models.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.List;

public interface MapService {
    List<Shape> getAllShapes(int dataTypeId);
    List<DataType> getAllDataTypes();
    DataType getDataType(int dataTypeId);
    boolean changeProperty( DataProperties dataProperties);
    List<DataProperties> getDataProperties(int dataTypeId);
    Shape getShape(Point point, int dataTypeId);
    boolean uploadFile(ShpFile shpFile) throws IOException;
    boolean changeDataType(DataType dataType);
}
