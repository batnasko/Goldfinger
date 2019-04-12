package com.goldfinger.gis.controllers;


import com.goldfinger.gis.models.DataType;
import com.goldfinger.gis.models.Shape;
import com.goldfinger.gis.services.contracts.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;


@RestController
@RequestMapping("/map")
public class MapController {
    private MapService mapService;

    @Autowired
    public MapController(MapService mapService){
        this.mapService = mapService;
    }

    @GetMapping("/{dataTypeId}")
    public List<Shape> getAllShapes(@PathVariable int dataTypeId){
        try {
            return mapService.getAllShapes(dataTypeId);
        }
        catch (Exception e){
            throw new NotImplementedException();
        }
    }

    @GetMapping("/datatypes")
    public List<DataType> getAllDataTypes(){
        try {
            return mapService.getAllDataTypes();
        }catch (Exception e){
            throw new NotImplementedException();
        }
    }

}
