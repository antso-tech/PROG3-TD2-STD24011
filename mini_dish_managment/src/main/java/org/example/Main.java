package org.example;

import java.sql.SQLException;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        DBConnection dbConnection = new DBConnection();
        DataRetriever data = new DataRetriever();
        dbConnection.getConnection();
        data.findDishById(1);
        System.out.println("---return---");
        data.findDishById(999);
        System.out.println("test 1");
        data.findIngredients(2,2);
        System.out.println("test 2");
        data.findIngredients(2,3);
        System.out.println("test 3");
        data.findDishsByIngredientName("eur");
        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(7);
        ingredient1.setName("Lait");
        ingredient1.setCategory(CategoryEnum.DAIRY);
        ingredient1.setPrice(14.00);
        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(8);
        ingredient2.setName("Oignon");
        ingredient2.setPrice(15.00);
        ingredient2.setCategory(CategoryEnum.VEGETABLE);
        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId(9);
        ingredient3.setName("Farine");
        ingredient3.setPrice(12.00);
        ingredient3.setCategory(CategoryEnum.OTHER);
        List<Ingredient> ingredientList = List.of(ingredient2,ingredient3);
        data.createIngredients(ingredientList);

        dbConnection.closeConnection();

    }
}