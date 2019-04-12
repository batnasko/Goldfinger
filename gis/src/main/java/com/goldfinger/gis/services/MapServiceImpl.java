package com.goldfinger.gis.services;

import com.goldfinger.gis.repositories.contracts.MapRepository;
import com.goldfinger.gis.services.contracts.MapService;
import org.springframework.stereotype.Service;

@Service
public class MapServiceImpl implements MapService {

    private MapRepository mapRepository;

    public MapServiceImpl(MapRepository mapRepository){
        this.mapRepository = mapRepository;
    }
}
