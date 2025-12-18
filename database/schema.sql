
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

SELECT d.id_dish, d.name, d.dish_type, i.name from DISH d LEFT JOIN INGREDIENT i on i.id_dish = d.id_dish WHERE d.id_dish = 1;



