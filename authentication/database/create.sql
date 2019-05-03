DROP SCHEMA IF EXISTS goldfingerauthentication;
CREATE SCHEMA goldfingerauthentication;

USE  goldfingerauthentication;

CREATE TABLE users
(
    id INT NOT NULL AUTO_INCREMENT,
    username varchar(80) NOT NULL,
	firstName varchar(80) NOT NULL,
    lastName varchar(80) NOT NULL,
    password varchar(80) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE roles
(
    id INT NOT NULL AUTO_INCREMENT,
    role varchar(80) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE userRole
(
    id INT NOT NULL AUTO_INCREMENT,
    userId int NOT NULL,
	roleId int NOT NULL,
    PRIMARY KEY(id),
    foreign key(userId) references users(id),
    foreign key(roleId) references roles(id)
    
);

insert into roles(role) values("ROLE_ADMIN"), ("ROLE_USER");
insert into users(username, firstName, lastName, password) values("admin", "admin", "admin", "admin");
insert into users(username, firstName, lastName, password) values("user", "user", "user", "user");
insert into userRole(userId, roleId) values(1, 1), (1,2), (2,2); 