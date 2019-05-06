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
    row_to_color varchar(80) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE dataProperties
(
    id INT NOT NULL AUTO_INCREMENT,
    dataType_id INT NOT NULL,
	property varchar(80) NOT NULL,
    showProperty TINYINT(1) NOT NULL,
    PRIMARY KEY(id),
	FOREIGN KEY (dataType_id) REFERENCES dataTypes(id)
);

                                        