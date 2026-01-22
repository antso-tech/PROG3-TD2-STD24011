-- Active: 1760942016873@@localhost@5432@mini_dish_db

CREATE TYPE dish_category as ENUM ('VEGETABLE', 'ANIMAL', 'MARINE', 'DAILY', 'OTHER');

CREATE TYPE dish_type as Enum ('STARTER', 'MAIN', 'DESSERT');

CREATE TABLE DISH (
    id_dish int PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    dish_type dish_type

)
;

CREATE TABLE INGREDIENT (
    id int PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price NUMERIC(10,2) NOT NULL,
    category dish_category NOT NULL,
    id_dish int,
    CONSTRAINT fk_dish FOREIGN KEY (id_dish) REFERENCES DISH(id_dish) 

);



