package org.example;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    Connection connection = null;
    Connection getConnection() {
        try{
            String url = System.getenv("DB_URL");
            String user = System.getenv("DB_USER");
            String password = System.getenv("DB_PASSWORD");
            connection = DriverManager.getConnection(url, user,password);


        }catch(SQLException e){
            throw  new RuntimeException(e);

        }
     return connection;
    }

}
