package com.goldfinger.gis.services;

import com.goldfinger.gis.models.*;
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
    private static final String DATATYPE_EXIST = "Shapefile name is already taken!";

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
    public DataType getDataType(int dataTypeId) {
        return mapRepository.getDataType(dataTypeId);
    }

    @Override
    public boolean changeProperty(DataProperties dataProperties) {
        return mapRepository.changeProperty(dataProperties.getDataTypeId(), dataProperties.getProperties(), dataProperties.getShow());
    }

    @Override
    public List<DataProperties> getDataProperties(int dataTypeId) throws ResourceAccessException {
        return mapRepository.getDataProperties(mapRepository.getDataType(dataTypeId).getId());
    }

    @Override
    public Shape getShape(Point point, int dataTypeId) throws ResourceAccessException, IllegalArgumentException {
        return mapRepository.getShape(point, mapRepository.getDataType(dataTypeId).getTableName());
    }

    @Override
    public boolean uploadFile(ShpFile shpFile) throws IOException {
        for (DataType dataType: mapRepository.getAllDataTypes()) {
            if (dataType.getTableName().equals(shpFile.getShpFileName().replace(" ", "").toLowerCase())){
                throw new IllegalArgumentException(DATATYPE_EXIST);
            }
        }
        byte[] shpBytes = uploadHelper.parseBase64(shpFile.getFile());
        String dirToSave = uploadHelper.createNewDirectory();
        String zipPath = uploadHelper.saveZip(shpBytes, dirToSave + uploadHelper.getZipSaveName());
        uploadHelper.unzip(zipPath, dirToSave);
        String shpFileName = uploadHelper.getShpFileName(dirToSave);
        uploadHelper.checkForDbf();
        String shpTableName = shpFile.getShpFileName().replace(" ", "").toLowerCase();
        uploadHelper.uploadShp(dirToSave + shpFileName, shpTableName);
        long dataTypeId = mapRepository.saveDataType(shpFile.getShpFileName(), shpTableName, "");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ignored) {

        }
        List<String> metaData = mapRepository.getDataTypeMetaDataColumns(shpTableName);
        for (String column : metaData) {
            mapRepository.saveNewColumnToDisplay(dataTypeId, column, false);
        }
        return true;
    }

    @Override
    public boolean changeDataType(DataType dataType) {
        return mapRepository.changeDataType(dataType.getId(), dataType.getRowToColor());
    }

}
