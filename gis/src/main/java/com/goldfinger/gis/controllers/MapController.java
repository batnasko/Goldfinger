package com.goldfinger.gis.controllers;

import com.goldfinger.gis.models.Point;
import com.goldfinger.gis.repositories.contracts.MapRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/map")
public class MapController {
    private MapRepository mapRepository;

    public MapController(MapRepository mapRepository){
        this.mapRepository = mapRepository;
    }

    @GetMapping
    public void test(){
         mapRepository.getShape(new Point(), "Adf");
    }
}
