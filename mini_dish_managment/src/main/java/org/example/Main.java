package org.example;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        DBConnection dbConnection = new DBConnection();
        DataRetriever data = new DataRetriever();
        dbConnection.getConnection();
        Dish saladeFraiche = new Dish();
        Dish saladeLegume = new Dish();
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
        Ingredient lait = new Ingredient();

        System.out.println("Find dish by criteria : ");
        data.findIngredientsByCriteria(null, CategoryEnum.VEGETABLE, null, 1, 10);
        data.findIngredientsByCriteria(null, null, "Sal", 1, 10);

        lait.setId(7);
        lait.setName("Lait");
        lait.setCategory(CategoryEnum.DAIRY);
        lait.setPrice(14.00);
        dishCreate3.setId(4);
        lait.setDish(dishCreate3);
        Ingredient oignon = new Ingredient();
        oignon.setId(8);
        oignon.setName("Oignon");
        oignon.setPrice(15.00);
        oignon.setCategory(CategoryEnum.VEGETABLE);
        dishCreate.setId(1);
        oignon.setDish(dishCreate);
        Ingredient farine = new Ingredient();
        farine.setId(9);
        farine.setName("Farine");
        farine.setPrice(12.00);
        farine.setCategory(CategoryEnum.OTHER);
        dishCreate2.setId(4);
        farine.setDish(dishCreate2);
        List<Ingredient> ingredientList = List.of(lait);
        List<Ingredient> ingredientList2 = List.of(oignon);

        System.out.println("Create Ingredient : ");
        data.createIngredients(ingredientList);
        data.createIngredients(ingredientList2);

        System.out.println("Save dish : ");
        saladeFraiche.setId(1);
        saladeFraiche.setName("Salade Fraîche");
        saladeFraiche.setDishType(DishtypeEnum.STARTER);
        saladeFraiche.setIngredients(ingredientList2);
        saladeLegume.setId(6);
        saladeLegume.setName("Salade de légumes");
        saladeLegume.setDishType(DishtypeEnum.STARTER);
        saladeLegume.setIngredients(ingredientList2);
        data.saveDish(saladeLegume);
        Ingredient fromage = new Ingredient();
        fromage.setId(13);
        fromage.setName("fromage");
        fromage.setPrice(10.18);
        fromage.setCategory(CategoryEnum.DAIRY);
        fromage.setDish(saladeFraiche);
        Ingredient oignon2 = new Ingredient();
        oignon2.setId(8);
        oignon2.setName("Oignon");
        oignon2.setCategory(CategoryEnum.VEGETABLE);
        oignon2.setDish(saladeFraiche);
        Ingredient tomate = new Ingredient();
        tomate.setId(2);
        tomate.setName("Tomate");
        tomate.setCategory(CategoryEnum.VEGETABLE);
        tomate.setDish(saladeFraiche);
        oignon2.setDish(saladeFraiche);
        Ingredient laitue = new Ingredient();
        laitue.setId(1);
        laitue.setName("Laitue");
        laitue.setCategory(CategoryEnum.VEGETABLE);
        laitue.setDish(saladeFraiche);
        List<Ingredient> saladeFraicheList = List.of(fromage, oignon2, tomate,laitue);
        saladeFraiche.setIngredients(saladeFraicheList);
        data.saveDish(saladeFraiche);


    }
}