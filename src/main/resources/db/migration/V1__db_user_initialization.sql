create table user (
                      id integer not null AUTO_INCREMENT,
                      email varchar(255) UNIQUE,
                      first_name varchar(255) NOT NULL,
                      last_name varchar(255) NOT NULL,
                      password varchar (255) NOT NULL,
                      role varchar(255),
                      primary key (id)
);

insert into user (email, first_name, last_name, password, role)
values
    ("admin", "admin", "admin", "$2a$10$Il7twPvztg1rns8oQqa1E.V7xKohWXaiXTjJ06Ubalm//nZsGm6aq", "ADMIN");

