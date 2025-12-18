package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataRetriever {
DBConnection dbConnection = new DBConnection();
    Dish findDishById(){
        Dish dishById = new Dish();
        try {
            Connection connection = dbConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("""
            SELECT id_dish, name, dish_type 
            from DISH
            """);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()){
                int id_dish = rs.getInt("id_dish");
                String name = rs.getString("name");
                DishtypeEnum category = DishtypeEnum.valueOf(rs.getString("dish_type"));
                System.out.println("id : " + id_dish + ", name : " + name + ", type : " + category);
            }

        }catch (SQLException e){
            throw new RuntimeException("Erreur au niveau du serveur : " + e);

        }
        return null;
    }
}

// find ingredientbydiishid
// key word : rollback, setAutoCommit
