package com.goldfinger.gis.services;

import com.goldfinger.gis.models.DataType;
import com.goldfinger.gis.models.Point;
import com.goldfinger.gis.models.Shape;
import com.goldfinger.gis.models.ShpFile;
import com.goldfinger.gis.repositories.contracts.MapRepository;
import com.goldfinger.gis.services.contracts.MapService;
import com.goldfinger.gis.services.helpers.UploadHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.io.IOException;
import java.util.List;

@Service
public class MapServiceImpl implements MapService {

    private MapRepository mapRepository;

    private UploadHelper uploadHelper;

    @Autowired
    public MapServiceImpl(MapRepository mapRepository, UploadHelper uploadHelper) {
        this.mapRepository = mapRepository;
        this.uploadHelper = uploadHelper;
    }

    @Override
    public List<Shape> getAllShapes(int dataTypeId) throws ResourceAccessException {
        return mapRepository.getAll(mapRepository.getDataType(dataTypeId).getTableName());
    }

    @Override
    public List<DataType> getAllDataTypes() throws ResourceAccessException {
        return mapRepository.getAllDataTypes();
    }

    @Override
    public List<String> getDataProperties(int dataTypeId) throws ResourceAccessException {
        return mapRepository.getDataProperties(mapRepository.getDataType(dataTypeId).getId());
    }

    @Override
    public Shape getShape(Point point, int dataTypeId) throws ResourceAccessException, IllegalArgumentException {
        return mapRepository.getShape(point, mapRepository.getDataType(dataTypeId).getTableName());
    }

    @Override
    public boolean uploadFile(ShpFile shpFile) throws IOException {
        byte[] shpBytes = uploadHelper.parseBase64(shpFile.getFile());
        String dirToSave = uploadHelper.createNewDirectory();
        String zipPath = uploadHelper.saveZip(shpBytes, dirToSave + uploadHelper.getZipSaveName());
        uploadHelper.unzip(zipPath, dirToSave);
        String shpFileName = uploadHelper.getShpFileName(dirToSave);
        String shpTableName = shpFile.getShpFileName().replace(" ","");
        uploadHelper.uploadShp(dirToSave+shpFileName, shpTableName);
        long dataTypeId = mapRepository.saveDataType(shpFile.getShpFileName(),shpTableName,shpFile.getColumnToColor());
        for (String column: shpFile.getColumnsToShow().split(" ")) {
            mapRepository.saveNewColumnToDisplay(dataTypeId,column);
        }
        return true;
    }

}
