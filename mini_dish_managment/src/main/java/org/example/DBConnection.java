package org.example;


import org.w3c.dom.ls.LSOutput;
import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    Connection getConnection() {
        try{
            Dotenv dotenv = Dotenv.load();
            String url = dotenv.get("DB_URL");
            String user = dotenv.get("DB_USER");
            String password = dotenv.get("DB_PASSWORD");

            return  DriverManager.getConnection(url, user,password);


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
