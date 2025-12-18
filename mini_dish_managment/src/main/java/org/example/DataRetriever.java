package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DataRetriever {
 /*
    Dish findDishById(Integer id){
        Connection connection = dbConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("""
    select id,name, dish_type from dish where id= ?;
""");
        preparedStatement.setInt(1,id);
        ResultSet rs = preparedStatement.executeQuery();
        il faut convertir un string en enum
        if (rs.next()) {
            dbConnection.closeConnection(connection);
            return new Dish();
        }
        dbConnection.

    } */
}

// find ingredientbydiishid
// key word : rollback, setAutoCommit
