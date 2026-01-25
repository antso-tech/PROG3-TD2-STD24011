package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DataRetriever {
DBConnection dbConnection = new DBConnection();
Connection connection;

    List<DishIngredients> findDishIngredientbyDishId (int id){
        List<DishIngredients> dishIngredients = new ArrayList<>();
        try {
            connection = dbConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("""
           SELECT di.id, i.name, i.price, i.category, di.quantity_required, di.unit, i.id as idIngredient\s
           ,d.id as dishId, d.name as dishName, d.price as dishPrice, d.dishType
           FROM INGREDIENT i  FULL JOIN dishIngredient di ON i.id = di.id JOIN dish d on d.id = di.id\s
           WHERE di.id_dish = ?;
""");
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Ingredient ingredient = new Ingredient();
                Dish dish = new Dish();
                DishIngredients dishIngredient = new DishIngredients();
                int idIngredientDish = resultSet.getInt("id");
                int idIngredient = resultSet.getInt("idIngredient");
                String ingredientName = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                CategoryEnum category = CategoryEnum.valueOf(resultSet.getString("category"));
                Double quantity = resultSet.getDouble("quantity_required");
                UnitType unit = UnitType.valueOf(resultSet.getString("unit"));
                int dishId = resultSet.getInt("dishId");
                String dishName = resultSet.getString("dishName");
                double dishPrice = resultSet.getDouble("dishPrice");
                DishtypeEnum dishType = DishtypeEnum.valueOf(resultSet.getString("dishType"));

                ingredient.setId(idIngredient);
                ingredient.setName(ingredientName);
                ingredient.setPrice(price);
                ingredient.setCategory(category);

                dish.setId(dishId);
                dish.setName(dishName);
                dish.setPrice(dishPrice);
                dish.setDishType(dishType);

                dishIngredient.setDish(dish);
                dishIngredient.setIngredient(ingredient);
                dishIngredient.setQuantity(quantity);
                dishIngredient.setUnit(unit);
                dishIngredient.setId(idIngredientDish);

                dishIngredients.add(dishIngredient);

            }


            System.out.println(dishIngredients);
            return dishIngredients;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void findDishById(int id){
        List<DishIngredients> dishIngredients;
        Dish dish;
        try {
            connection = dbConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("""
            SELECT d.id as idDish, d.name as dishName, d.dishType, d.price\s
               ,i.name as ingredientName
            FROM dish d\s
                INNER join dishIngredient dt on d.id = dt.id\s
                INNER JOIN INGREDIENT i on dt.id = i.id\s
            where d.id = ?
""");

            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();


            while (rs.next()){
                    dish = new Dish();
                    Ingredient ingredient = new Ingredient();
                    int idDish = rs.getInt("idDish");
                    String dishName = rs.getString("dishName");
                    String ingredientName = rs.getString("ingredientName");
                    DishtypeEnum type = DishtypeEnum.valueOf(rs.getString("dishType"));


                    dish.setId(idDish);
                    dish.setName(dishName);
                    dish.setDishType(type);
                    dish.setPrice(rs.getObject("price") == null ? null : rs.getDouble("price"));
                    ingredient.setName(ingredientName);


                System.out.println(dish);

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


            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(ingredients);
        return ingredients;
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
         int dishId;
         try (PreparedStatement ps = conn.prepareStatement(updateDishQuery)) {

             ps.setString(1, dishToSave.getName());
             ps.setString(2, dishToSave.getDishType().name());
             if (dishToSave.getPrice() != null) {

                 ps.setDouble(3, dishToSave.getPrice());
             } else {
                 ps.setNull(3, Types.DOUBLE);

             }
             try(ResultSet rs = ps.executeQuery()){
                 rs.next();
                 dishId = rs.getInt("id");

             }

         }
         List<DishIngredients> dishIngredients = new ArrayList<>();
         attachDishIngredient(conn, dishId,dishIngredients );
         detachDishIngredient(conn, dishId,dishIngredients);

         conn.commit();
         conn.close();

     } catch (SQLException e) {

         try {
             conn.rollback();
         } catch (SQLException ex) {
             throw new RuntimeException("Roll backError :" + ex);
         }

         throw new RuntimeException("Failed to save dish : " + e);
     }
 }

    private void detachDishIngredient(Connection conn, int dish, List<DishIngredients> dishIngredients)
            throws SQLException {
        if (dishIngredients.isEmpty() || dishIngredients == null) {
            String detachIngredientSQL = """
               DELETE FROM DishIngredient WHERE id_dish = ? AND id_ingredient = ? ;
                """;

            PreparedStatement ps = conn.prepareStatement(detachIngredientSQL);
            for (DishIngredients dishIngredient : dishIngredients){

                ps.setInt(1, dish);
                ps.setInt(2, dishIngredient.getIngredient().getId());

                try (ResultSet rs = ps.executeQuery()){
                    rs.next();

                }
            }
        }
    }

    public void attachDishIngredient(Connection conn, int dishId, List<DishIngredients> dishIngredients)
            throws SQLException{
        if (dishIngredients.isEmpty() || dishIngredients == null){
            String attachDishIngredient = """
                INSERT INTO DishIngredient (id_dish, id_ingredient, quantity_require, unit)\s
                VALUES (?,?,?,?)
               """;

            PreparedStatement ps = conn.prepareStatement(attachDishIngredient);
            for (DishIngredients dishIngredient : dishIngredients){
                ps.setInt(1, dishId);
                ps.setInt(2, dishIngredient.getIngredient().getId());
                ps.setDouble(3, dishIngredient.getQuantity());
                ps.setObject(4, dishIngredient.getUnit().name());

                try(ResultSet rs = ps.executeQuery()) {
                    rs.next();
                    System.out.println("Votre code fonctionne !");

                }
            }
        }

    }


    List<Dish> findDishsByIngredientName(String IngredientName){
        List<Dish> dishes = new ArrayList<>();
        try{
            Dish dish = new Dish();

            List<Ingredient> ingredients = new ArrayList<>();
            PreparedStatement ps = connection.prepareStatement("""
        SELECT d.id as dishId, d.name as dishName, d.dishType, d.price, i.name as ingredientName\s
        from DISH d\s
            INNER JOIN DishIngredient di on d.id = di.id\s
            INNER JOIN INGREDIENT i on i.id = di.id
        WHERE i.name ilike ?
            \s""");

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
            connection.close();


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
            SELECT i.id as ingredientId, i.name as ingredientName, i.category,
                   i.price as ingredientPrice, d.name as dishName\s
            FROM INGREDIENT i\s
                LEFT JOIN dishIngredient dt\s
                    ON dt.id = i.id\s
                LEFT JOIN DISH d ON dt.id = d.id \s
            WHERE i.name ilike ?\s
            OR i.category::text ilike\s
            ? OR d.name ilike\s
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
            connection.close();

        }catch(SQLException e) {
            throw  new RuntimeException(e);

        }
        System.out.println(ingredients);
        return ingredients;
    }



    void createDish(Dish dishParameters){
        Dish dish = new Dish();
        try{
            String createIngredientQuery = """
                INSERT INTO DISH (id_dish, name, dish_type, price)\s
                VALUES (?,?,?::dish_type,?)\s
                RETURNING id_dish, name, dish_type, price""";

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
            connection.close();

        } catch (SQLException e) {

            throw new RuntimeException(e);
        }

    }

    public Ingredient saveIngredient(Ingredient toSave){
        Connection conn = null;
        try{
            conn = new DBConnection().getConnection();
            conn.setAutoCommit(false);
            Ingredient ingredient = new Ingredient();

            String saveIngredientSQL = """
                INSERT INTO INGREDIENT (id , name, price, category) VALUES  (?,?, ?, ?::ingredient_category)
                ON CONFLICT (id)
                DO NOTHING
               """;

            PreparedStatement ps = connection.prepareStatement(saveIngredientSQL);
            ps.setInt(1, toSave.getId());
            ps.setString(2, toSave.getName());
            ps.setDouble(3, toSave.getPrice());
            ps.setString(4, toSave.getCategory().name());

            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                System.out.println("Votre plat a été mis à jour !");
            }

            conn.commit();
            conn.close();
            return ingredient;
        }catch (SQLException e){
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }

    }

    public Order findOrderByReference(String reference) {
        throw  new RuntimeException("Not implemented");
    }
}
