-- Active: 1760942016873@@localhost@5432@mini_dish_db
CREATE DATABASE mini_dish_db

CREATE USER mini_dish_db_manager WITH PASSWORD 'dish_managment_password';

GRANT CONNECT ON DATABASE mini_dish_db to mini_dish_db_manager;

GRANT CREATE ON SCHEMA PUBLIC TO mini_dish_db_manager

GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA PUBLIC TO mini_dish_db_manager

GRANT USAGE ON SCHEMA public TO mini_dish_db_manager;

GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO mini_dish_db_manager

ALTER DEFAULT PRIVILEGES IN SCHEMA public 
GRANT ALL PRIVILEGES ON TABLES TO mini_dish_db_manager;