
DROP SCHEMA IF EXISTS goldfingerauditability;
CREATE SCHEMA  goldfingerauditability;

USE goldfingerauditability;


CREATE TABLE logs(
	log_id INT NOT NULL AUTO_INCREMENT,
    log varchar(500) NOT NULL,
        FULLTEXT(log),
    PRIMARY KEY(log_id)
);

CREATE TABLE pairs(
	id INT NOT NULL AUTO_INCREMENT,
    log_id INT NOT NULL,
    key_ VARCHAR(50) NOT NULL,
    value_ VARCHAR(100) NOT NULL,
    PRIMARY KEY(id),
	FOREIGN KEY (log_id) REFERENCES logs(log_id),
    FULLTEXT(key_, value_)
)
engine InnoDB;

insert into logs(log) values ( "username Kaloqn msg Logged in ip 255.255.255.001 date 25-04-04"), 
								("username Rosen msg Logged in ip 255.255.255.005 date 01-06-07"), 
                                ("username Nasko msg Logged out ip 255.159.365.025 date 05-05-05"),
                                ("username Kaloqn msg Logged out ip 255.255.255.001 date 25-04-04");

insert into pairs(log_id, key_, value_) values(1, "username", "Kaloqn"), (1, "msg", "Logged in"), (1, "ip", "255.255.255.001"), (1, "date", "25-04-04"),
												(2, "username", "Rosen"), (2, "msg", "Logged in"), (2, "ip", "255.255.255.005"), (2, "date", "01-06-07"),
                                                (3, "username", "Nasko"), (3, "msg", "Logged Out"), (3, "ip", "255.159.365.025"), (3, "date", "05-05-05"),
                                                (4, "username", "Kaloqn"), (4, "msg", "Logged out"), (4, "ip", "255.255.255.001"), (4, "date", "25-04-04");


	select log_id from logs;
    
    SELECT log_id from logs;

select * from pairs;
select * from logs;