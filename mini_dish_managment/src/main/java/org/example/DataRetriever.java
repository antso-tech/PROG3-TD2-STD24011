package org.example;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataRetriever {
DBConnection dbConnection = new DBConnection();
Connection connection;

    void findDishById(int id){
        Dish dish = null;
        List<Ingredient> ingredientList = new ArrayList<>();
        try {
            connection = dbConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("""
            SELECT d.id_dish, d.name as dishName, d.dish_type, i.name as ingredientName from DISH d 
                LEFT JOIN INGREDIENT i on i.id_dish = d.id_dish 
                WHERE i.id_dish = ?;
            """);


            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                    if (dish == null){
                    dish = new Dish();
                    int idDish = rs.getInt("id_dish");
                    String dishName = rs.getString("dishName");
                    DishtypeEnum category = DishtypeEnum.valueOf(rs.getString("dish_type"));
                    String ingredientName = rs.getString("ingredientName");
                    if(!rs.wasNull()){
                        Ingredient newIngredient = new Ingredient();
                        newIngredient.setName(ingredientName);

                        System.out.println(newIngredient);
                        ingredientList.add(newIngredient);

                    }
                    dish.setId(idDish);
                    dish.setIngredient(ingredientList);
                    dish.setName(dishName);
                    dish.setDishType(category);
                    dish.setIngredient(ingredientList);

                }
                // recuperer ingredient name
                //tester si pas null ingredient name
                System.out.println(dish);
                return;
            }


        }catch (SQLException e){
            throw new RuntimeException("Erreur au niveau du serveur : " + e);
        }
    }

    List<Ingredient> findIngredients(int page, int size){
        List<Ingredient> ingredients = new ArrayList<>();
        try{
            int offset = (page - 1) * size;
            PreparedStatement ps = connection.prepareStatement("SELECT id, name, price, category from INGREDIENT LIMIT ? OFFSET ?");
            ps.setInt(1,page);
            ps.setInt(2,offset);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Ingredient ingredient = new Ingredient();
                int idIngredient = rs.getInt("id");
                String nameIngredient = rs.getString("name");
                Long priceIngredient = rs.getLong("price");
                CategoryEnum category = CategoryEnum.valueOf(rs.getString("category"));

                ingredient.setId(idIngredient);
                ingredient.setName(nameIngredient);
                ingredient.setPrice(priceIngredient);
                ingredient.setCategory(category);

                ingredients.add(ingredient);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(ingredients);
        return ingredients;
    }

    List<Ingredient> createIngredients(List<Ingredient> newIngredients){
        List<Ingredient> newIngredientData = new ArrayList<>();
        try{
            String createIngredientQuery = "INSERT INTO INGREDIENT (id, name, price, category) VALUES ( ?, ?, ?, ?::dish_category) RETURNING *";
            Boolean autoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);

            for (int i = 0; i < newIngredients.size(); i++) {
                try{
                    PreparedStatement ps = connection.prepareStatement(createIngredientQuery);
                    ps.setInt(1, newIngredients.get(i).getId());
                    ps.setString(2, newIngredients.get(i).getName());
                    ps.setDouble(3, newIngredients.get(i).getPrice());
                    ps.setObject(4, newIngredients.get(i).getCategory().name());

                    ResultSet rs = ps.executeQuery();
                    Ingredient ingredient = new Ingredient();


                    while (rs.next()){
                        int idIngredient = rs.getInt("id");
                        String name = rs.getString("name");
                        Double price = rs.getDouble("price");
                        CategoryEnum category = CategoryEnum.valueOf(rs.getString("category"));

                        ingredient.setId(idIngredient);
                        ingredient.setName(name);
                        ingredient.setPrice(price);
                        ingredient.setCategory(category);

                    }

                    newIngredientData.add(ingredient);
                }catch (SQLIntegrityConstraintViolationException e){
                    System.out.println("Element Existant : " + e);


                }

            }
            connection.commit();
            connection.setAutoCommit(autoCommit);

        }catch(SQLException e){
            try {
                connection.rollback();
            } catch(SQLException ex){
                throw new RuntimeException(ex);
            }

            throw new RuntimeException("Erreur serveur : " + e.getMessage());


        }
        System.out.println(newIngredientData);
        return newIngredientData;
        
    }

    Dish saveDish(Dish dishToSave){
        throw new RuntimeException("Not implemented");
    }

    List<Dish> findDishsByIngredientName(String IngredientName){
        List<Dish> dishes = new ArrayList<>();
        try{
            Dish dish = new Dish();

            List<Ingredient> ingredients = new ArrayList<>();
            PreparedStatement ps = connection.prepareStatement("SELECT i.id_dish, d.name as dishName, " +
                    "d.dish_type, i.name AS ingredientName FROM DISH d LEFT JOIN INGREDIENT i ON " +
                    "i.id_dish = d.id_dish WHERE i.name ILIKE ? ");

            ps.setString(1, "%" + IngredientName + "%");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){

                int idDish = rs.getInt("id_dish");
                String dishName = rs.getString("dishName");
                DishtypeEnum dishType =  DishtypeEnum.valueOf(rs.getString("dish_type"));
                String ingredientName = rs.getString("ingredientName");

                Ingredient ingredient = new Ingredient();
                ingredient.setName(ingredientName);
                ingredients.add(ingredient);
                dish.setId(idDish);
                dish.setName(dishName);

                dish.setDishType(dishType);
                System.out.println(ingredients);
                dish.setIngredient(ingredients);

            }
            dishes.add(dish);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println(dishes);
        return dishes;

    }

    List<Ingredient>
    findIngredientsByCriteria(String ingredientName,
                              CategoryEnum category, String dishName, int page, int size){
        List<Ingredient> ingredients = new ArrayList<>();

        try{
            Ingredient ingredient = new Ingredient();
            int offset = (page - 1) * size;
            PreparedStatement ps = connection.prepareStatement("SELECT i.id, i.name as ingredientName," +
                    " i.price, i.category, d.name as dishName FROM DISH d LEFT JOIN INGREDIENT i on" +
                    " i.id_dish = d.id_dish WHERE i.name ilike ? OR i.category::text " +
                    "ilike ? OR d.name ilike ? LIMIT ? OFFSET ?");

            ps.setString(1, ingredientName);
            ps.setString(2, String.valueOf(category));
            ps.setString(3, dishName);
            ps.setInt(4, page);
            ps.setInt(5, offset);

            ResultSet rs = ps.executeQuery();

            Dish dish = new Dish();
            while (rs.next()){
                int idIngredient = rs.getInt("id");
                String nameIngredient = rs.getString("ingredientName");
                Long price = rs.getLong("price");
                CategoryEnum categoryIngredient = CategoryEnum.valueOf(rs.getString("category"));

                String nameDish = rs.getString("dishName");

                dish.setName(nameDish);

                ingredient.setId(idIngredient);
                ingredient.setName(nameIngredient);
                ingredient.setPrice(price);
                ingredient.setCategory(categoryIngredient);
                ingredient.setDish(dish);

            }
            System.out.println("---All-Dish----");
            System.out.println(dish);

            ingredients.add(ingredient);

        }catch(SQLException e) {
            throw  new RuntimeException(e);

        }
        System.out.println(ingredients);
        return ingredients;
    }

    List<Ingredient> addIngredientList() {
        Ingredient ingredient = new Ingredient();
        List<Ingredient> ingredientsList = new ArrayList<>();
        try {
            Connection connection = dbConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("""
            SELECT id, name, price, category, id_dish FROM INGREDIENT
""");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int idIngredient = rs.getInt("id");
                String name = rs.getString("name");
                Long price = rs.getLong("price");
                CategoryEnum category = CategoryEnum.valueOf(rs.getString("category"));
                int idDish = rs.getInt("id_dish");

                ingredient.setId(idIngredient);
                ingredient.setName(name);
                ingredient.setPrice(price);
                ingredient.setCategory(category);


            }

        } catch (SQLException e) {
            throw new RuntimeException(e);

        }

        return ingredientsList;
    }


}


// find ingredientbydiishid
// key word : rollback, setAutoCommit
