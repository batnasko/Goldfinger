DROP SCHEMA IF EXISTS goldfingergis;
CREATE SCHEMA goldfingergis;

USE  goldfingergis;


-- ogr2ogr -f MySQL MySQL:goldfingergis,host=localhost,port=3306,user=root,password=admin D:\shapefiles\earhquake\gdeqk.shp -nln earthquakes -update -overwrite -lco engine=MYISAM

-- ogr2ogr -f MySQL MySQL:goldfingergis,host=localhost,port=3306,user=root,password=admin D:\shapefiles\soil\DSMW.shp -nln soils -update -overwrite -lco engine=MYISAM

CREATE TABLE dataTypes
(
    id INT NOT NULL AUTO_INCREMENT,
    dataType varchar(80) NOT NULL,
	tableName varchar(80) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE dataProperties
(
    id INT NOT NULL AUTO_INCREMENT,
    dataType_id INT NOT NULL,
	property varchar(80) NOT NULL,
    PRIMARY KEY(id),
	FOREIGN KEY (dataType_id) REFERENCES dataTypes(id)
);
-- HAVE TO IMPLEMENT COLORS ????????????????????????????


INSERT INTO dataTypes(dataType, tableName) VALUES ("Soil Types", "soils"),
												  ("Earthquake Frequency", "earthquakes");
                                                  
INSERT INTO dataProperties(dataType_id, property) VALUES (1,"faosoil"),
														 (1,"domsoi"),
															(1,"sqkm"),
														  (1,"country"),
                                                          (2,"dn");

select * from soils;
select * from earthquakes;
select * from datatypes;
select * from dataProperties;

SELECT * FROM earthquakes WHERE CONTAINS(SHAPE,Point(129.53,72.7));