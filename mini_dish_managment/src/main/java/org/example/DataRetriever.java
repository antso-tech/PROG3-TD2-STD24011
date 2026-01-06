package org.example;

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
            SELECT id_dish, name, dish_type FROM DISH WHERE id_dish = ?;
""");

            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                    if (dish == null){
                    dish = new Dish();
                    int idDish = rs.getInt("id_dish");
                    String dishName = rs.getString("name");
                    DishtypeEnum category = DishtypeEnum.valueOf(rs.getString("dish_type"));

                    dish.setId(idDish);
                    dish.setIngredients(ingredientList);
                    dish.setName(dishName);
                    dish.setDishType(category);

                    String ingredientListQuery = "SELECT name FROM INGREDIENT WHERE id_dish = ?";
                    PreparedStatement ps = connection.prepareStatement(ingredientListQuery);
                    ps.setInt(1,id);
                    List<Ingredient> ingredients = new ArrayList<>();

                    ResultSet ingredientRs = ps.executeQuery();
                    while (ingredientRs.next()){
                        Ingredient ingredient = new Ingredient();
                        String ingredientName = ingredientRs.getString("name");
                        ingredient.setName(ingredientName);
                        ingredients.add(ingredient);

                    }

                    dish.setIngredients(ingredients);
                }
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
            String createIngredientQuery = "INSERT INTO INGREDIENT (id, name, price, category,id_dish) VALUES ( ?, ?, ?, ?::dish_category,?) RETURNING *";
            Boolean autoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);

            for (int i = 0; i < newIngredients.size(); i++) {
                try{
                    PreparedStatement ps = connection.prepareStatement(createIngredientQuery);
                    ps.setInt(1, newIngredients.get(i).getId());
                    ps.setString(2, newIngredients.get(i).getName());
                    ps.setDouble(3, newIngredients.get(i).getPrice());
                    ps.setObject(4, newIngredients.get(i).getCategory().name());
                    ps.setInt(5,newIngredients.get(i).getDish().getId());

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
        Dish dish = new Dish();

        try{
            boolean checkDish = false;
            try{
                String checkDishQuery = "SELECT COUNT(*) as number from DISH WHERE id_dish = ?";
                PreparedStatement ps = connection.prepareStatement(checkDishQuery);
                ps.setInt(1, dishToSave.getId());
                ResultSet rs = ps.executeQuery();

                while(rs.next()){
                    int number = rs.getInt("number");

                    if(number > 0){
                        checkDish = true;
                    }

                }
            }catch(SQLException e) {
                throw new RuntimeException(e);
            }
            if(!checkDish){
                createDish(dishToSave);

            }else{
                updateDish(dishToSave);

            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return dish;
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
                dish.setIngredients(ingredients);

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



    void createDish(Dish dishParameters){
        Dish dish = new Dish();
        try{
            String createIngredientQuery = "INSERT INTO DISH (id_dish, name, dish_type) VALUES (?,?,?::dish_type) RETURNING *";
            Boolean commitAuto = connection.getAutoCommit();
            connection.setAutoCommit(false);

            PreparedStatement ps = connection.prepareStatement(createIngredientQuery);

            ps.setInt(1,dishParameters.getId());
            ps.setString(2, dishParameters.getName());
            ps.setObject(3,dishParameters.getDishType().name());

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int idDish = rs.getInt("id_dish");
                String dishName = rs.getString("name");
                DishtypeEnum dishType =  DishtypeEnum.valueOf(rs.getString("dish_type"));

                dish.setId(idDish);
                dish.setName(dishName);
                dish.setDishType(dishType);
            }
            connection.commit();
            connection.setAutoCommit(commitAuto);
            System.out.println("Vous avec créez un plat");
            System.out.println(dish);

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }

    }

    void updateDish(Dish dishParameters){
        String updateDishQuery = "UPDATE DISH set id_dish = ?, name = ?, dish_type=?::dish_type WHERE id_dish = ?";
        try {
            Boolean commitAuto = connection.getAutoCommit();
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(updateDishQuery);

            ps.setInt(1, dishParameters.getId());
            ps.setString(2, dishParameters.getName());
            ps.setObject(3, dishParameters.getDishType().name());
            ps.setInt(4,dishParameters.getId());

            int UpdateRows = ps.executeUpdate();
            System.out.println(UpdateRows);

            connection.commit();
            connection.setAutoCommit(commitAuto);

            if (UpdateRows > 0){
                System.out.println("Votre plat a été mis à jour !");

            }else{
                System.out.println("Erreur !");
            }

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }

}
// find ingredientbydiishid
// key word : rollback, setAutoCommit
