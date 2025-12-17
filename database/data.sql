INSERT INTO DISH (id_dish, name,dish_type) VALUES (1, 'Salade Fraîche', 'START')

INSERT INTO DISH (id_dish, name,dish_type) VALUES (2, 'Poulet grillé', 'MAIN'), 
(3,'Riz aux légumes', 'MAIN'),
(4,'Gâteaux aux chocolat', 'DESSERT'),
(5, 'Salade de fruits', 'DESSERT');

INSERT INTO INGREDIENT VALUES (1, 'Laitue', '800.00','VEGETABLE',1),
(2,'Tomate', 4500.00, 'VEGETABLE',1),
(3,'Poulet', 3000.00,'ANIMAL',2),
(4,'Chocolat', 3000.00,'OTHER', 4),
(5,'Beurre',2500.00, 'DAILY', 4);

SELECT * FROM INGREDIENT