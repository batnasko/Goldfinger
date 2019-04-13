DROP SCHEMA IF EXISTS goldfingerauditability;
CREATE SCHEMA  goldfingerauditability;

USE goldfingerauditability;

CREATE TABLE logs(
	id INT NOT NULL AUTO_INCREMENT,
    message VARCHAR(200) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE pairs(
	id INT NOT NULL AUTO_INCREMENT,
    log_id INT NOT NULL,
    key_ VARCHAR(50) NOT NULL,
    value_ VARCHAR(50) NOT NULL,
    PRIMARY KEY(id),
	FOREIGN KEY (log_id) REFERENCES logs(id)
);


SELECT * FROM logs;

SELECT * FROM pairs;