package com.goldfinger.gis.services;

import com.goldfinger.gis.models.DataType;
import com.goldfinger.gis.models.Shape;
import com.goldfinger.gis.repositories.contracts.MapRepository;
import com.goldfinger.gis.services.contracts.MapService;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

@Service
public class MapServiceImpl implements MapService {

    private MapRepository mapRepository;

    public MapServiceImpl(MapRepository mapRepository){
        this.mapRepository = mapRepository;
    }

    @Override
    public List<Shape> getAllShapes(int dataTypeId) {
        throw new NotImplementedException();
    }

    @Override
    public List<DataType> getAllDataTypes() {
        return mapRepository.getAllDataTypes();
    }

    @Override
    public List<String> getDataProperties(int dataTypeId) {
        return mapRepository.getDataProperties(dataTypeId);
    }

}
