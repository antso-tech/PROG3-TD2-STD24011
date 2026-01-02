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
    category dish_category,
    id_dish int,
    CONSTRAINT fk_dish FOREIGN KEY (id_dish) REFERENCES DISH(id_dish) 

);

SELECT d.id_dish, d.name as dishName, d.dish_type, i.name as ingredientName from DISH d 
                LEFT JOIN INGREDIENT i on i.id_dish = d.id_dish WHERE i.id_dish = 1 ;

SELECT * from INGREDIENT ;

DELETE from INGREDIENT WHERE id = 7

INSERT INTO INGREDIENT (id, name, price, category) VALUES (6,'Fromage', 1200.0, 'DAIRY');

SELECT i.id_dish, d.name as dishName, d.dish_type, i.name AS ingredientName FROM DISH d LEFT JOIN INGREDIENT i ON i.id_dish = d.id_dish WHERE i.name ilike '%eur%';

SELECT COUNT(*) FROM DISH WHERE id_dish = 7 ;
SELECT COUNT(*) FROM INGREDIENT WHERE NAME = ""; 


SELECT i.id, i.name as ingredientName, i.price, i.category, d.name as dishName FROM DISH d LEFT JOIN INGREDIENT i on i.id_dish = d.id_dish WHERE i.name ilike '%i%' OR i.category::text ilike '%bl%' OR d.name ilike '';

SELECT * from INGREDIENT;
