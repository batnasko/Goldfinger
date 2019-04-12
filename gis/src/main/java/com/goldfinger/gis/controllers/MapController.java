package com.goldfinger.gis.controllers;


import com.goldfinger.gis.services.contracts.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/map")
public class MapController {
    private MapService mapService;

    @Autowired
    public MapController(MapService mapService){
        this.mapService = mapService;
    }


}
