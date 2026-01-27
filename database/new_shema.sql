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

SELECT * FROM INGREDIENT join on ;
SELECT * from ingredient i inner join stockMovement s on s.id = i.id where s.id = 1;

SELECT * from ingredient;

ALTER TABLE ingredient ADD CONSTRAINT unique_id UNIQUE (id);
DELETE FROM INGREDIENT WHERE id = 17;
ALTER TABLE ingredient DROP CONSTRAINT name_unique;
INSERT INTO ingredient (name, price, category) VALUES

INSERT INTO INGREDIENT (name, price, category) VALUES 
('Laitue', 800.00,'VEGETABLE'),
('Tomate', 4500.00, 'VEGETABLE'),
('Poulet', 3000.00,'ANIMAL'),
('Chocolat', 3000.00,'OTHER'),
('Beurre',2500.00, 'DAIRY');

SELECT * FROM STOCKMOVEMENT;
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

INSERT INTO ORDERS (reference, creation_datetime) VALUES
('ORD00001', '2024-01-15 10:30:00'),
('ORD00002', '2024-01-15 12:15:00'),
('ORD00003', '2024-01-16 14:45:00'),
('ORD00004', '2024-01-16 18:20:00'),
('ORD00005', '2024-01-17 09:00:00');




CREATE TABLE ORDERS(
    id serial primary key,
    reference varchar(255),
    creation_datetime TIMESTAMP
);

CREATE TABLE DISHORDER(
    id serial PRIMARY KEY,
    id_dish int,
    id_order int, 
    CONSTRAINT fk_dish FOREIGN KEY (id_dish) REFERENCES DISH(id),
    CONSTRAINT fk_order FOREIGN KEY (id_order) REFERENCES ORDERS(id),
    quantity numeric(4,2)
);

INSERT INTO DISHORDER (id_dish, id_order, quantity) VALUES
(5, 15, 2.50),
(25, 35, 1.75),
(105, 215, 3.00),
(45, 95, 2.25),
(155, 305, 4.50),
(75, 125, 1.50),
(205, 255, 2.75),
(35, 45, 3.25),
(185, 195, 2.00),
(65, 85, 1.25);

SELECT * from DISHORDER;

SELECT * from orders;

SELECT * from dish;
SELECT * from ingredient;

SELECT id, name, dishType, price  FROM dish where id = 1;

SELECT o.id, o.reference, o.creation_datetime, d.quantity from ORDERS o left join DISHORDER d on d.id_order = o.id WHERE REFERENCE = 'ORD00003';




