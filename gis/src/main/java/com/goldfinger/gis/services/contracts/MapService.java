package com.goldfinger.gis.services.contracts;

import com.goldfinger.gis.models.Shape;

import java.util.List;

public interface MapService {
    List<Shape> getAllShapes(int dataTypeId);
}
