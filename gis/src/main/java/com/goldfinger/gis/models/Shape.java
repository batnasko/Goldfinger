package com.goldfinger.gis.models;

import com.vividsolutions.jts.geom.Geometry;
import java.util.Map;

public class Shape {
    private Geometry geometry;
    private Map<String, String> properties;

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }
}
