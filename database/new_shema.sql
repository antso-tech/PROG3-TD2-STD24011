CREATE TYPE  unit_type AS ENUM ('PCS', 'KG', 'L');

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

SELECT d.id as dishId, d.name as dishName, d.dishType, d.price, i.name as ingredientName from DISH d LEFT JOIN DishIngredient di on d.id = di.id LEFT JOIN INGREDIENT i on i.id = di.id WHERE i.name ilike '%tom%';

SELECT i.id as ingredientId, i.name as ingredientName, i.category, i.price, d.name as dishName FROM INGREDIENT i LEFT JOIN dishIngredient dt ON dt.id = i.id LEFT JOIN DISH d ON dt.id = d.id  WHERE i.name ilike null OR i.category::text ilike NULL OR d.name ilike '%Sal%' LIMIT 3 OFFSET 2;
SELECT d.id as idDish, d.name as dishName, d.dishType, d.price ,i.name as ingredientName FROM dish d left join dishIngredient dt on d.id = dt.id LEFT JOIN INGREDIENT i on dt.id = d.id;

SELECT id, name, price, category from ingredient;

INSERT INTO DISH (name,dishType, price) VALUES 
('Salade Fraîche', 'STARTER', 3500.00),
('Poulet grillé', 'MAIN', 12000.00), 
('Riz aux légumes', 'MAIN', NULL),
('Gâteaux aux chocolat', 'DESSERT', 8000.00),
('Salade de fruits', 'DESSERT', NULL);

INSERT INTO INGREDIENT (name, price, category) VALUES 
('Laitue', '800.00','VEGETABLE'),
('Tomate', 4500.00, 'VEGETABLE'),
('Poulet', 3000.00,'ANIMAL'),
('Chocolat', 3000.00,'OTHER'),
('Beurre',2500.00, 'DAIRY');

SELECT * FROM INGREDIENT;
select * from DISH;
SELECT * from DISHINGREDIENT;


SELECT i.id, i.name, i.price, i.category, di.quantity_required, di.unit, d.name FROM INGREDIENT i  FULL JOIN dishIngredient di ON i.id = di.id JOIN dish d on d.id = di.id WHERE di.id_dish = 1;

