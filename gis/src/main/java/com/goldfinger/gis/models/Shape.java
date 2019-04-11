package com.goldfinger.gis.models;

import com.vividsolutions.jts.geom.Geometry;
import java.util.HashMap;

public class Shape {
    private int id;
    private Geometry geometry;
    private HashMap<String, String> properties;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public HashMap<String, String> getProperties() {
        return properties;
    }

    public void setProperties(HashMap<String, String> properties) {
        this.properties = properties;
    }
}
