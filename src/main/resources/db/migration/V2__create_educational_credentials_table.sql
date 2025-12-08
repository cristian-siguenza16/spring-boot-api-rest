CREATE TABLE educational_credentials (
    id BIGINT NOT NULL AUTO_INCREMENT,
    person_id BIGINT NOT NULL,
    type VARCHAR(10) NOT NULL,
    organization VARCHAR(60) NOT NULL,
    acquired_credential VARCHAR(100) NOT NULL,
    year INT,
    
    CONSTRAINT PK_educational_credentials PRIMARY KEY (id),
    
    CONSTRAINT FK_educational_credentials_person 
        FOREIGN KEY (person_id) 
        REFERENCES person(id)
        ON DELETE CASCADE
);