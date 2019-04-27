package com.goldfinger.gis.services.contracts;

import com.goldfinger.gis.models.DataType;
import com.goldfinger.gis.models.Point;
import com.goldfinger.gis.models.Shape;
import com.goldfinger.gis.models.ShpFile;

import java.io.IOException;
import java.util.List;

public interface MapService {
    List<Shape> getAllShapes(int dataTypeId);
    List<DataType> getAllDataTypes();
    List<String> getDataProperties(int dataTypeId);
    Shape getShape(Point point, int dataTypeId);
    boolean uploadFile(ShpFile shpFile) throws IOException;
}
