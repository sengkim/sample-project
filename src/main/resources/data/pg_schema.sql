CREATE TABLE user_tb (
	id serial PRIMARY KEY,
	email varchar (50) NOT NULL,
	password varchar (50) NOT NULL,
	firstname varchar (50),
	lastname varchar (50)
	);