package com.goldfinger.gis.services.contracts;

import com.goldfinger.gis.models.DataType;
import com.goldfinger.gis.models.Shape;

import java.util.List;

public interface MapService {
    List<Shape> getAllShapes(int dataTypeId);
    List<DataType> getAllDataTypes();
    List<String> getDataProperties(int dataTypeId);
}
