package com.goldfinger.gis.services;

import com.goldfinger.gis.models.DataType;
import com.goldfinger.gis.models.Point;
import com.goldfinger.gis.models.Shape;
import com.goldfinger.gis.repositories.contracts.MapRepository;
import com.goldfinger.gis.services.contracts.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;

@Service
public class MapServiceImpl implements MapService {

    private MapRepository mapRepository;

    @Autowired
    public MapServiceImpl(MapRepository mapRepository){
        this.mapRepository = mapRepository;
    }

    @Override
    public List<Shape> getAllShapes(int dataTypeId)throws ResourceAccessException {
        return mapRepository.getAll(mapRepository.getDataType(dataTypeId).getTableName());
    }

    @Override
    public List<DataType> getAllDataTypes()throws ResourceAccessException {
        return mapRepository.getAllDataTypes();
    }

    @Override
    public List<String> getDataProperties(int dataTypeId) {
        return mapRepository.getDataProperties(dataTypeId);
    }

    @Override
    public Shape getShape(Point point, int dataTypeId)throws ResourceAccessException, IllegalArgumentException {
        return mapRepository.getShape(point, mapRepository.getDataType(dataTypeId).getTableName());
    }

}
