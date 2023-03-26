CREATE TABLE REQUEST_DATA(ID BIGINT PRIMARY KEY AUTO_INCREMENT,
                  INCOMING_MESSAGE VARCHAR(7999),
                  TRANSLATED_MESSAGE VARCHAR(7999),
                  SOURCE_LANGUAGE VARCHAR(10),
                  TARGET_LANGUAGE VARCHAR(10),
                  REQUEST_TIME VARCHAR(50),
                  REQUEST_IP VARCHAR(50)
                 );

CREATE TABLE REQUEST_WORD(ID BIGINT,
                ID_REQUEST BIGINT,
                INCOMING_WORD VARCHAR(255),
                TRANSLATED_WORD VARCHAR(255),
                FOREIGN KEY (ID_REQUEST) REFERENCES REQUEST_DATA(ID),
                PRIMARY KEY (ID, ID_REQUEST)
);
