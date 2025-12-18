package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DataRetriever {
DBConnection dbConnection = new DBConnection();
    Dish findDishById(int id){
        Dish dishById = new Dish();
        Ingredient ingredient = new Ingredient();
        try {
            Connection connection = dbConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("""
            SELECT d.id_dish, d.name, d.dish_type, i.name from DISH d 
                LEFT JOIN INGREDIENT i on i.id_dish = d.id_dish 
                WHERE d.id_dish = 1;
            """);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()){
                int id_dish = rs.getInt("id_dish");
                String name = rs.getString("name");
                DishtypeEnum category = DishtypeEnum.valueOf(rs.getString("dish_type"));



                dishById.setId(id_dish);
                dishById.setName(name);
                dishById.setDishType(category);

            }

        }catch (SQLException e){
            throw new RuntimeException("Erreur au niveau du serveur : " + e);

        }
        System.out.println(dishById);
        return dishById;
    }

    List<Ingredient> findIngredients(int page, int size){
        throw new RuntimeException("Not implemented");

    }

    List<Ingredient> createIngredients(List<Ingredient> newIngredients){
        throw new RuntimeException("Not implemented");
    }

    Dish saveDish(Dish dishToSave){
        throw new RuntimeException("Not implemented");
    }

    List<Dish> findDishsByIngredientName(String IngredientName){
        throw new RuntimeException("Not implemented");
    }

    List<Ingredient>
    findIngredientsByCriteria(String ingredientName,
                              CategoryEnum category, String dishName, int page, int size){
        throw new RuntimeException("Not implemented");
    }

}


// find ingredientbydiishid
// key word : rollback, setAutoCommit
