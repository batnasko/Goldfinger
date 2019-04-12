package com.goldfinger.gis.controllers;

import com.goldfinger.gis.models.Shape;
import com.goldfinger.gis.repositories.contracts.MapRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/map")
public class MapController {
    private MapRepository mapRepository;

    public MapController(MapRepository mapRepository){
        this.mapRepository = mapRepository;
    }

    @GetMapping
    public List<Shape> test(){
         return mapRepository.getAll( "soils");
    }
}
