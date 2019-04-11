package com.goldfinger.gis.repositories.contracts;

import com.goldfinger.gis.models.Point;
import com.goldfinger.gis.models.Shape;


public interface MapRepository {
    Shape getShape(Point point, String tableName);
}
