DROP SCHEMA IF EXISTS goldfingerauditability;
CREATE SCHEMA  goldfingerauditability;

USE goldfingerauditability;

CREATE TABLE logs(
	id INT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY(id)
);

CREATE TABLE pairs(
	id INT NOT NULL AUTO_INCREMENT,
    log_id INT NOT NULL,
    key_ VARCHAR(50) NOT NULL,
    value_ VARCHAR(100) NOT NULL,
    PRIMARY KEY(id),
	FOREIGN KEY (log_id) REFERENCES logs(id)
);

CREATE TABLE words(
	id INT NOT NULL AUTO_INCREMENT,
    word VARCHAR(50) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE word_log(
	id INT NOT NULL AUTO_INCREMENT,
    word_id INT NOT NULL,
    log_id INT NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY (word_id) REFERENCES words(id),
	FOREIGN KEY (log_id) REFERENCES logs(id)
);


SELECT * FROM logs;

SELECT * FROM pairs;

insert into logs values();