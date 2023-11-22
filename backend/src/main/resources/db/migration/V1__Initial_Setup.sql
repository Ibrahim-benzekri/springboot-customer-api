create sequence customer_id_seq
start with 1
increment by 1;

CREATE TABLE customer(
    id int PRIMARY KEY DEFAULT nextval('customer_id_seq'),
    name TEXT NOT NULL ,
    email TEXT NOT NULL ,
    age INT NOT NULL
)