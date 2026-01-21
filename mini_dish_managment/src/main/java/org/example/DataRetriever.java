package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DataRetriever {
DBConnection dbConnection = new DBConnection();
Connection connection;

    List<DishIngredients> findIngredientbyDishId (int id){
        List<DishIngredients> ingredients = new ArrayList<>();
        try {
            connection = dbConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("""
           SELECT di.id, i.name, i.price, i.category, di.quantity_required, di.unit, d.name, i.id as idIngredient 
           FROM INGREDIENT i  FULL JOIN dishIngredient di ON i.id = di.id JOIN dish d on d.id = di.id 
           WHERE di.id_dish = ?;
""");
            preparedStatement.setInt(1,id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                Ingredient ingredient = new Ingredient();
                DishIngredients dishIngredient = new DishIngredients();
                int idIngredientDish = resultSet.getInt("id");
                int idIngredient = resultSet.getInt("idIngredient");
                String ingredientName = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                CategoryEnum category = CategoryEnum.valueOf(resultSet.getString("category"));
                Double quantity = resultSet.getDouble("quantity_required");
                UnitType unit = UnitType.valueOf(resultSet.getString("unit"));

                ingredient.setId(idIngredient);
                ingredient.setName(ingredientName);
                ingredient.setPrice(price);
                ingredient.setCategory(category);

                dishIngredient.setIngredient(ingredient);
                dishIngredient.setQuantity(quantity);
                dishIngredient.setUnit(unit);
                dishIngredient.setId(idIngredientDish);

                System.out.println(dishIngredient);

            }

            return ingredients;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void findDishById(int id){
        Dish dish;
        try {
            connection = dbConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("""
            SELECT d.id as idDish, d.name as dishName, d.dishType, d.price ,i.name as ingredientName 
            FROM dish d 
                left join dishIngredient dt on d.id = dt.id 
                LEFT JOIN INGREDIENT i on dt.id = d.id 
            where d.id = ?
""");

            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()){
                    dish = new Dish();
                    int idDish = rs.getInt("idDish");
                    String dishName = rs.getString("dishName");
                    DishtypeEnum category = DishtypeEnum.valueOf(rs.getString("dishType"));

                    dish.setId(idDish);
                    dish.setName(dishName);
                    dish.setDishType(category);
                    dish.setPrice(rs.getObject("price") == null ? null : rs.getDouble("price"));



                System.out.println(dish);

            }else {
                throw new RuntimeException("Erreur : l'Id n'existe pas ");

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
            String checkIngredient = "SELECT id from ingredient where id = ?";
            String createIngredientQuery = "INSERT INTO INGREDIENT (id, name, price, category,id_dish) VALUES ( ?, ?, ?, ?::dish_category,?) RETURNING *";
            connection.setAutoCommit(false);


            for (int i = 0; i < newIngredients.size(); i++) {
                PreparedStatement checkStatement = connection.prepareStatement(checkIngredient);
                checkStatement.setInt(1,newIngredients.get(i).getId());
                ResultSet rsCheck = checkStatement.executeQuery();

                if (rsCheck.next()){
                    System.out.println("ingredient déjà existant");
                    continue;
                }
                try {
                    PreparedStatement ps = connection.prepareStatement(createIngredientQuery);
                    ps.setInt(1, newIngredients.get(i).getId());
                    ps.setString(2, newIngredients.get(i).getName());
                    ps.setDouble(3, newIngredients.get(i).getPrice());
                    ps.setObject(4, newIngredients.get(i).getCategory().name());


                    ResultSet rs = ps.executeQuery();
                    Ingredient ingredient = new Ingredient();


                    while (rs.next()) {
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

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (IllegalArgumentException e) {
                    throw new RuntimeException(e);
                }
            }
            connection.commit();

        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
        return newIngredientData;
    }

 void saveDish(Dish dishToSave) {
     Connection conn = null;

     String updateDishQuery = """
         INSERT INTO DISH (name, dishType, price) VALUES (?,?::dish_type,?) ON CONFLICT (name) DO 
         UPDATE set name = EXCLUDED.name,dishType = EXCLUDED.dishType, price = EXCLUDED.price 
         RETURNING id
         """;
     try {
         conn = new DBConnection().getConnection();
         conn.setAutoCommit(false);
         System.out.println("Auto-commit désactivé");

         Integer dishId;
         try (PreparedStatement ps = conn.prepareStatement(updateDishQuery)) {
             System.out.println("Requête préparée: " + ps.toString());

             System.out.println("DEBUG - Executing UPSERT for: " + dishToSave.getName());
             ps.setString(1, dishToSave.getName());
             ps.setString(2, dishToSave.getDishType().name());
             if (dishToSave.getPrice() != null) {
                 System.out.println("Paramètres: name=" + dishToSave.getName()
                         + ", type=" + dishToSave.getDishType().name()
                         + ", price=" + dishToSave.getPrice());
                 ps.setDouble(3, dishToSave.getPrice());
             } else {
                 ps.setNull(3, Types.DOUBLE);
                 System.out.println("Paramètres: name=" + dishToSave.getName()
                         + ", type=" + dishToSave.getDishType().name()
                         + ", price=NULL");
             }
             System.out.println("Exécution de la requête...");
             try (ResultSet rs = ps.executeQuery()) {
                 System.out.println("Requête exécutée");
                 rs.next();
                 dishId = rs.getInt("id");

             }catch(SQLException e){
                 conn.rollback();
                 throw e;

             }

         }
         List<DishIngredients> dishIngredients = new ArrayList<>();
         attachIngredient(conn, dishId, dishIngredients );
         detachIngredient(conn, dishId, dishIngredients);

         conn.commit();

     } catch (SQLException e) {
         System.err.println("ERROR - No result from UPSERT");
         if (conn != null) {
             try {
                 conn.rollback();
             } catch (SQLException ex) {
                 throw new RuntimeException("RolllbackError :" + ex);
             }

         }
         throw new RuntimeException("Failed to save dish : " + e);
     }
     return ;
 }

    private void detachIngredient(Connection conn, Integer id, List<DishIngredients> dishIngredients) throws SQLException {
            if(dishIngredients == null || dishIngredients.isEmpty()){
                try(PreparedStatement ps = conn.prepareStatement("""
                UPDATE INGREDIENT SET id = null where id = ?
            """)){
                    ps.setInt(1, id);
                    ps.executeUpdate();


                } return;
            }
            String baseSQL = """
                    UPDATE dishIngredient SET id_dish = NULL WHERE id_dish = ? AND id NOT IN (%s)
                    """;

            String inClause = dishIngredients.stream()
                    .map(i -> "?")
                    .collect(Collectors.joining(","));

            String sql = String.format(baseSQL, inClause);

            try (PreparedStatement ps = conn.prepareStatement(sql)){
                ps.setInt(1, id);
                int index = 2;
                for(DishIngredients ingredient : dishIngredients){
                    ps.setInt(index++, ingredient.getIngredient().getId());
                }
                ps.executeUpdate();

            }
    }

    public void attachIngredient(Connection conn, Integer id, List<DishIngredients> dishIngredients) throws SQLException{
        if (dishIngredients == null || dishIngredients.isEmpty()){
            return;

        }
        String attachSQL = """
                UPDATE dishIngredient set id_dish = ? WHERE id = ?
                """;

        try(PreparedStatement ps = conn.prepareStatement(attachSQL)) {
            for (DishIngredients dishIngredient: dishIngredients){
                ps.setInt(1, id);
                ps.setInt(2, dishIngredient.getIngredient().getId());
                ps.addBatch();

            }
            ps.executeBatch();

        }

    }


    List<Dish> findDishsByIngredientName(String IngredientName){
        List<Dish> dishes = new ArrayList<>();
        try{
            Dish dish = new Dish();

            List<Ingredient> ingredients = new ArrayList<>();
            PreparedStatement ps = connection.prepareStatement("""
        SELECT d.id as dishId, d.name as dishName, d.dishType, d.price, i.name as ingredientName 
        from DISH d 
            INNER JOIN DishIngredient di on d.id = di.id 
            INNER JOIN INGREDIENT i on i.id = di.id
        WHERE i.name ilike ?
             """);

            ps.setString(1, "%" + IngredientName + "%");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){

                int idDish = rs.getInt("dishId");
                String dishName = rs.getString("dishName");
                DishtypeEnum dishType =  DishtypeEnum.valueOf(rs.getString("dishType"));
                Double price = rs.getDouble("price");
                String ingredientName = rs.getString("ingredientName");

                Ingredient ingredient = new Ingredient();
                ingredient.setName(ingredientName);
                ingredients.add(ingredient);
                dish.setId(idDish);
                dish.setName(dishName);
                dish.setPrice(price);

                dish.setDishType(dishType);


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
            int offset = (page - 1) * size;
            PreparedStatement ps = connection.prepareStatement("""
            SELECT i.id as ingredientId, i.name as ingredientName, i.category, i.price as ingredientPrice, d.name as dishName 
            FROM INGREDIENT i 
                LEFT JOIN dishIngredient dt 
                    ON dt.id = i.id 
                LEFT JOIN DISH d ON dt.id = d.id  
            WHERE i.name ilike ? 
            OR i.category::text ilike 
            ? OR d.name ilike 
            ? LIMIT ? OFFSET ?
""");

            ps.setString(1, '%' + ingredientName + '%');
            ps.setString(2, String.valueOf(category));
            ps.setString(3, '%' + dishName + '%');
            ps.setInt(4, page);
            ps.setInt(5, offset);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                Ingredient ingredient = new Ingredient();
                int idIngredient = rs.getInt("ingredientId");
                String nameIngredient = rs.getString("ingredientName");
                Long price = rs.getLong("ingredientPrice");
                CategoryEnum categoryIngredient = CategoryEnum.valueOf(rs.getString("category"));

                ingredient.setId(idIngredient);
                ingredient.setName(nameIngredient);
                ingredient.setPrice(price);
                ingredient.setCategory(categoryIngredient);
                ingredients.add(ingredient);

            }



        }catch(SQLException e) {
            throw  new RuntimeException(e);

        }
        System.out.println(ingredients);
        return ingredients;
    }



    void createDish(Dish dishParameters){
        Dish dish = new Dish();
        try{
            String createIngredientQuery = "INSERT INTO DISH (id_dish, name, dish_type, price) VALUES (?,?,?::dish_type,?) RETURNING id_dish, name, dish_type, price";

            PreparedStatement ps = connection.prepareStatement(createIngredientQuery);

            ps.setInt(1,dishParameters.getId());
            ps.setString(2, dishParameters.getName());
            ps.setObject(3,dishParameters.getDishType().name());
            ps.setDouble(4,dishParameters.getPrice());

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int idDish = rs.getInt("id_dish");
                String dishName = rs.getString("name");
                DishtypeEnum dishType =  DishtypeEnum.valueOf(rs.getString("dish_type"));
                Double price = rs.getDouble("price");

                dish.setId(idDish);
                dish.setName(dishName);
                dish.setDishType(dishType);
                dish.setPrice(price);

            }

            System.out.println("Vous avec créez un plat");
            System.out.println(dish);

        } catch (SQLException e) {

            throw new RuntimeException(e);
        }

    }
}
