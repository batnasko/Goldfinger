package com.goldfinger.gis.controllers;


import com.goldfinger.gis.models.*;
import com.goldfinger.gis.services.contracts.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;


@RestController
@RequestMapping("/map")
public class MapController {
    private MapService mapService;

    @Autowired
    public MapController(MapService mapService) {
        this.mapService = mapService;
    }

    @GetMapping("/datatype")
    public List<DataType> getAllDataTypes() {
        try {
            return mapService.getAllDataTypes();
        } catch (ResourceAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/shape/{dataTypeId}")
    public List<Shape> getAllShapes(@PathVariable int dataTypeId) {
        try {
            return mapService.getAllShapes(dataTypeId);
        } catch (ResourceAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/shape/{dataTypeId}")
    public Shape getShape(@PathVariable int dataTypeId, @RequestBody Point point) {
        try {
            return mapService.getShape(point, dataTypeId);
        } catch (ResourceAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/datatype/{dataTypeId}/property")
    public List<String> getDataProperties(@PathVariable int dataTypeId) {
        try {
            return mapService.getDataProperties(dataTypeId);
        } catch (ResourceAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

}
