create table working_hour_mgmt.location
(
    id varchar(255) not null primary key
);

create table working_hour_mgmt.effort
(
    id varchar(255) not null primary key,
    date datetime(6) not null ,
    hours int not null ,
    billable boolean not null,
    note varchar(255),
    location_id varchar(255) not null ,
    FOREIGN KEY (location_id) REFERENCES working_hour_mgmt.location(id)
);

