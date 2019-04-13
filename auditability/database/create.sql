DROP SCHEMA IF EXISTS goldfingerauditability;
CREATE SCHEMA  goldfingerauditability;

USE goldfingerauditability;

CREATE TABLE logs(
	id INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(80) NOT NULL,
    ip VARCHAR(30) NOT NULL,
    event_time TIMESTAMP NOT NULL,
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