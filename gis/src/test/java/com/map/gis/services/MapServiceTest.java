package com.map.gis.services;


import com.goldfinger.gis.models.*;
import com.goldfinger.gis.repositories.contracts.MapRepository;
import com.goldfinger.gis.services.MapServiceImpl;
import com.goldfinger.gis.services.helpers.UploadHelper;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.ResourceAccessException;

import java.io.IOException;
import java.util.*;

@RunWith(MockitoJUnitRunner.class)
public class MapServiceTest {

    @Mock
    private MapRepository mapRepository;
    @Mock
    private UploadHelper uploadHelper;

    @InjectMocks
    private MapServiceImpl mapService;

    @Test
    public void getAllShapes_Should_ReturnAllShapes_When_DataTypeExists() {
        DataType dataType = new DataType();
        Mockito.when(mapRepository.getDataType(1)).thenReturn(dataType);
        Mockito.when(mapRepository.getAll(dataType.getTableName())).thenReturn(new ArrayList<>(Arrays.asList(new Shape(), new Shape())));

        Assert.assertEquals(2, mapService.getAllShapes(1).size());
    }

    @Test(expected = ResourceAccessException.class)
    public void getAllShapes_Should_ReturnException_When_DataTypeDoesntExist() {
        Mockito.when(mapRepository.getDataType(1)).thenThrow(ResourceAccessException.class);

        mapService.getAllShapes(1);
    }

    @Test
    public void getAllDataTypes_Should_ReturnAllDataTypes_When_Called() {
        Mockito.when(mapRepository.getAllDataTypes()).thenReturn(new ArrayList<>(Arrays.asList(new DataType(), new DataType())));

        Assert.assertEquals(2, mapService.getAllDataTypes().size());
    }

    @Test
    public void getAllProperties_Should_ReturnAllProperties_When_DataTypeExists() {
        DataType dataType = new DataType();
        Mockito.when(mapRepository.getDataType(1)).thenReturn(dataType);
        Mockito.when(mapRepository.getDataProperties(dataType.getId())).thenReturn(new ArrayList<>(Arrays.asList(new DataProperties(), new DataProperties())));

        Assert.assertEquals(2, mapService.getDataProperties(1).size());
    }

    @Test(expected = ResourceAccessException.class)
    public void getAllProperties_Should_ReturnException_When_DataTypeDoesntExist() {
        Mockito.when(mapRepository.getDataType(1)).thenThrow(ResourceAccessException.class);

        mapService.getDataProperties(1);
    }

    @Test(expected = ResourceAccessException.class)
    public void getShape_Should_ReturnException_When_DataTypeDoesntExist() {
        Mockito.when(mapRepository.getDataType(1)).thenThrow(ResourceAccessException.class);

        mapService.getShape(new Point(), 1);
    }

    @Test
    public void getShape_Should_ReturnShape_When_ShapeExist() {
        DataType dataType = new DataType();
        Point point = new Point();
        Shape shape = new Shape();
        Mockito.when(mapRepository.getDataType(1)).thenReturn(dataType);
        Mockito.when(mapRepository.getShape(point, dataType.getTableName())).thenReturn(shape);

        Assert.assertEquals(shape, mapService.getShape(point, 1));
    }

    @Test(expected = ResourceAccessException.class)
    public void getShape_Should_ReturnShape_When_ShapeDoesntExist() {
        DataType dataType = new DataType();
        Point point = new Point();
        Mockito.when(mapRepository.getDataType(1)).thenReturn(dataType);
        Mockito.when(mapRepository.getShape(point, dataType.getTableName())).thenThrow(ResourceAccessException.class);

        mapService.getShape(point, 1);
    }

    @Test
    public void getDataType_Should_ReturnDataType_When_DataTypeExist() {
        DataType dataType = new DataType();
        Mockito.when(mapRepository.getDataType(1)).thenReturn(dataType);

        Assert.assertEquals(dataType, mapService.getDataType(1));
    }


    @Test
    public void changeDataProperty_Should_ReturnTrue_When_PropertyIsChanged() {
        DataProperties dataProperties = new DataProperties();
        dataProperties.setShow(true);
        dataProperties.setProperties("property");
        dataProperties.setDataTypeId(1);
        Mockito.when(mapRepository.changeProperty(dataProperties.getDataTypeId(), dataProperties.getProperties(), dataProperties.getShow())).thenReturn(true);

        Assert.assertTrue(mapService.changeProperty(dataProperties));
    }

    @Test
    public void changeDataType_Should_ReturnTrue_When_DataTypeIsChanged() {
        DataType dataType = new DataType();
        dataType.setId(1);
        dataType.setRowToColor("color");

        Mockito.when(mapRepository.changeDataType(dataType.getId(), dataType.getRowToColor())).thenReturn(true);

        Assert.assertTrue(mapService.changeDataType(dataType));
    }

    @Test(expected = IllegalArgumentException.class)
    public void uploadShp_Should_ReturnError_When_DbfDoesntExist() throws IOException {
        Mockito.doThrow(new IllegalArgumentException()).when(uploadHelper).checkForDbf();
        mapService.uploadFile(new ShpFile());
    }

    @Test(expected = IllegalArgumentException.class)
    public void uploadShp_Should_ReturnError_When_ShpExist() throws IOException {
        ShpFile shpFile = new ShpFile();
        shpFile.setShpFileName("");
        DataType dataType = new DataType();
        dataType.setTableName("");
        ArrayList<DataType> arrayList = new ArrayList<>();
        arrayList.add(dataType);

        Mockito.when(mapRepository.getAllDataTypes()).thenReturn(arrayList);

        mapService.uploadFile(shpFile);
    }

    @Test
    public void uploadShp_Should_ReturnTrue_When_ShpDoesntExist() throws IOException {
        ShpFile shpFile = new ShpFile();
        shpFile.setShpFileName("");
        DataType dataType = new DataType();
        dataType.setTableName("table");
        ArrayList<DataType> arrayList = new ArrayList<>();
        arrayList.add(dataType);

        Mockito.when(mapRepository.getAllDataTypes()).thenReturn(arrayList);

        Assert.assertTrue(mapService.uploadFile(shpFile));
    }
}
