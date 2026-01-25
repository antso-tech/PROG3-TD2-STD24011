CREATE TYPE  unit_type AS ENUM ('PCS', 'KG', 'L');
CREATE TYPE mouvement_type AS ENUM ('IN', 'OUT');

CREATE TABLE DISH (
    id serial primary key,
    name VARCHAR(255),
    dishType dish_type,
    price NUMERIC(10,2)
);

CREATE TABLE INGREDIENT(
    id serial PRIMARY KEY,
    name VARCHAR(255),
    price NUMERIC(10,2),
    category ingredient_category
);

CREATE TABLE DishIngredient(
    id SERIAL PRIMARY KEY,
    id_dish int,
    id_ingredient int,
    quantity_required NUMERIC(4,2),
    unit unit_type,
    CONSTRAINT fk_dish FOREIGN KEY (id_dish) REFERENCES DISH(id),
    CONSTRAINT fk_ingredient FOREIGN KEY (id_ingredient) REFERENCES INGREDIENT(id)

);

INSERT INTO DishIngredient (id_dish, id_ingredient, quantity_required, unit) VALUES 
(1,1,0.20,'KG'), (1,2,0.15,'KG'),(2,3,1.00,'KG'),
(4,4,0.30,'KG'),(4,5,0.20,'KG');


INSERT INTO DISH (name,dishType, price) VALUES 
('Salade Fraîche', 'STARTER', 3500.00),
('Poulet grillé', 'MAIN', 12000.00), 
('Riz aux légumes', 'MAIN', NULL),
('Gâteaux aux chocolat', 'DESSERT', 8000.00),
('Salade de fruits', 'DESSERT', NULL);

SELECT * FROM INGREDIENT;

INSERT INTO INGREDIENT (name, price, category) VALUES 
('Laitue', 800.00,'VEGETABLE'),
('Tomate', 4500.00, 'VEGETABLE'),
('Poulet', 3000.00,'ANIMAL'),
('Chocolat', 3000.00,'OTHER'),
('Beurre',2500.00, 'DAIRY');


CREATE TABLE StockMovement(
    id SERIAL PRIMARY KEY,
    id_ingredient int,
    quantity NUMERIC(5,2),
    type mouvement_type,
    unit unit_type,
    creation_datetime TIMESTAMP,
    CONSTRAINT fk_ingredient FOREIGN KEY (id_ingredient) REFERENCES INGREDIENT(id)
);


(1, 5.00, 'IN', 'KG', '2024-01-05 08:00:00'),
(1, 0.20, 'OUT', 'KG', '2024-01-06 12:00:00'),
(2, 4.00, 'IN', 'KG', '2024-01-05 08:00:00'),
(2, 0.15, 'OUT', 'KG', '2024-01-06 12:00:00'),
(3, 10.00, 'IN', 'KG', '2024-01-04 09:00:00'),
(3, 1.00, 'OUT', 'KG', '2024-01-06 13:00:00'),
(4, 3.00, 'IN', 'KG', '2024-01-05 10:00:00'),
(4, 0.30, 'OUT', 'KG', '2024-01-06 14:00:00'),
(5, 2.50, 'IN', 'KG', '2024-01-05 10:00:00'),
(5, 0.20, 'OUT', 'KG', '2024-01-06 14:00:00');


CREATE TABLE ORDERS(
    id serial primary key,
    reference varchar(255),
    creation_datetime TIMESTAMP
);

CREATE TABLE DISHORDER(
    id serial PRIMARY KEY,
    id_dish int,
    id_ingredient int, 
    CONSTRAINT fk_dish FOREIGN KEY (id_dish) REFERENCES DISH(id),
    CONSTRAINT fk_ingredient FOREIGN KEY (id_ingredient) REFERENCES DISH(id),
    quantity numeric(4,2)
)

SELECT id, name, dishType, price  FROM dish where id = 1;


;




