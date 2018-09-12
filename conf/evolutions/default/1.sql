CREATE TABLE  user (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    user_name varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    PRIMARY KEY (id)
);

# --- !Downs

DROP TABLE User;