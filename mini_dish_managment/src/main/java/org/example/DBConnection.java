package org.example;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    Connection getConnection() {
        try{
            String url = System.getenv("DB_URL");
            String user = System.getenv("DB_USER");
            String password = System.getenv("DB_PASSWORD");
            return DriverManager.getConnection("jdbc:postgresql://localhost:5432/mini_dish_db", "mini_dish_db_manager","dish_managment_password");


        }catch(SQLException e){
            throw  new RuntimeException(e);

        }
    }

    public void closeConnection(){
        try {
            getConnection().close();
            System.out.println("Connection closed !");
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

}
