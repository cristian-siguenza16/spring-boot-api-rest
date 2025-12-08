CREATE TABLE person (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nit VARCHAR(10) NOT NULL,
    name VARCHAR(60) NOT NULL,
    address VARCHAR(100),
    phone_number VARCHAR(16),
    
    CONSTRAINT PK_person PRIMARY KEY (id),
    
    CONSTRAINT UK_person_nit UNIQUE (nit)
);