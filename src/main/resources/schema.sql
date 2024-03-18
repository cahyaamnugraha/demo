--CREATE DATABASE IF NOT EXISTS demo;

create table if not exists role (
	id int(11) NOT NULL auto_increment NOT NULL,
	name varchar(100) NOT NULL,
	CONSTRAINT user_pk PRIMARY KEY (id),
	CONSTRAINT user_unique UNIQUE KEY (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
