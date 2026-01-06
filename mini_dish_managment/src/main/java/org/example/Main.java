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
        Dish dish1 = new Dish();
        Dish dish2 = new Dish();
        Dish dishCreate = new Dish();
        Dish dishCreate2 = new Dish();
        Dish dishCreate3 = new Dish();

        System.out.println("Find dish by id :");
        data.findDishById(1);
    //    data.findDishById(999);

        System.out.println("Find Ingredients :");
        data.findIngredients(2,2);
        data.findIngredients(3,5);

        System.out.println("Find dish by Ingredient name: ");
        data.findDishsByIngredientName("eur");
        Ingredient ingredient1 = new Ingredient();

        System.out.println("Find dish by criteria : ");
        data.findIngredientsByCriteria(null, CategoryEnum.VEGETABLE, null, 1, 10);
        data.findIngredientsByCriteria(null, null, "Sal", 1, 10);

        ingredient1.setId(7);
        ingredient1.setName("Lait");
        ingredient1.setCategory(CategoryEnum.DAIRY);
        ingredient1.setPrice(14.00);
        dishCreate3.setId(4);
        ingredient1.setDish(dishCreate3);
        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(8);
        ingredient2.setName("Oignon");
        ingredient2.setPrice(15.00);
        ingredient2.setCategory(CategoryEnum.VEGETABLE);
        dishCreate.setId(1);
        ingredient2.setDish(dishCreate);
        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId(9);
        ingredient3.setName("Farine");
        ingredient3.setPrice(12.00);
        ingredient3.setCategory(CategoryEnum.OTHER);
        dishCreate2.setId(4);
        ingredient3.setDish(dishCreate2);
        List<Ingredient> ingredientList = List.of(ingredient1);
        List<Ingredient> ingredientList2 = List.of(ingredient2);

        System.out.println("Create Ingredient : ");
        data.createIngredients(ingredientList);
        data.createIngredients(ingredientList2);

        System.out.println("Save dish : ");
        dish1.setId(1);
        dish1.setName("Salade Fraîche");
        dish1.setDishType(DishtypeEnum.STARTER);
        dish1.setIngredients(ingredientList2);
        dish2.setId(6);
        dish2.setName("Salade de légumes");
        dish2.setDishType(DishtypeEnum.STARTER);
        dish2.setIngredients(ingredientList2);
        data.saveDish(dish2);



    }
}