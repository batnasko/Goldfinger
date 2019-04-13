package com.map.gis.services;


import com.goldfinger.gis.models.*;
import com.goldfinger.gis.repositories.contracts.MapRepository;
import com.goldfinger.gis.services.MapServiceImpl;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.ResourceAccessException;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Arrays;

@RunWith(MockitoJUnitRunner.class)
public class MapServiceTest {

    @Mock
    private MapRepository mapRepository;

    @InjectMocks
    private MapServiceImpl mapService;

    @Test
    public void getAllShapes_Should_ReturnAllShapes_When_DataTypeExists(){
        DataType dataType = new DataType();
        Mockito.when(mapRepository.getDataType(1)).thenReturn(dataType);
        Mockito.when(mapRepository.getAll(dataType.getTableName())).thenReturn(new ArrayList<>(Arrays.asList(new Shape(), new Shape())));

        Assert.assertEquals( 2,mapService.getAllShapes(1).size());
    }

    @Test(expected = ResourceAccessException.class)
    public void getAllShapes_Should_ReturnException_When_DataTypeDoesntExist(){
        Mockito.when(mapRepository.getDataType(1)).thenThrow(ResourceAccessException.class);

        mapService.getAllShapes(1);
    }

    @Test
    public void getAllDataTypes_Should_ReturnAllDataTypes_When_Called(){
        Mockito.when(mapRepository.getAllDataTypes()).thenReturn(new ArrayList<>(Arrays.asList(new DataType(), new DataType())));

        Assert.assertEquals(2,mapService.getAllDataTypes().size());
    }

    @Test
    public void getAllProperties_Should_ReturnAllProperties_When_DataTypeExists(){
        DataType dataType = new DataType();
        Mockito.when(mapRepository.getDataType(1)).thenReturn(dataType);
        Mockito.when(mapRepository.getDataProperties(dataType.getId())).thenReturn(new ArrayList<>(Arrays.asList("", "")));

        Assert.assertEquals( 2,mapService.getDataProperties(1).size());
    }

    @Test(expected = ResourceAccessException.class)
    public void getAllProperties_Should_ReturnException_When_DataTypeDoesntExist(){
        Mockito.when(mapRepository.getDataType(1)).thenThrow(ResourceAccessException.class);

        mapService.getDataProperties(1);
    }

    @Test(expected = ResourceAccessException.class)
    public void getShape_Should_ReturnException_When_DataTypeDoesntExist(){
        Mockito.when(mapRepository.getDataType(1)).thenThrow(ResourceAccessException.class);

        mapService.getShape(new Point(),1);
    }

    @Test
    public void getShape_Should_ReturnShape_When_ShapeExist(){
        DataType dataType = new DataType();
        Point point = new Point();
        Shape shape = new Shape();
        Mockito.when(mapRepository.getDataType(1)).thenReturn(dataType);
        Mockito.when(mapRepository.getShape(point,dataType.getTableName())).thenReturn(shape);

        Assert.assertEquals(shape,mapService.getShape(point,1));
    }

    @Test(expected = ResourceAccessException.class)
    public void getShape_Should_ReturnShape_When_ShapeDoesntExist(){
        DataType dataType = new DataType();
        Point point = new Point();
        Mockito.when(mapRepository.getDataType(1)).thenReturn(dataType);
        Mockito.when(mapRepository.getShape(point,dataType.getTableName())).thenThrow(ResourceAccessException.class);

        mapService.getShape(point,1);
    }
}
