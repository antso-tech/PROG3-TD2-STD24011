package org.example;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        DataRetriever data = new DataRetriever();
        data.findDishById(4);
        data.findDishIngredientbyDishId(4);
        Dish saladeFraiche = new Dish(1, "Salade Fraîche",DishtypeEnum.STARTER,3500.0);
        Dish pouletGrille = new Dish(2, "Poulet grillé", DishtypeEnum.MAIN,12000.00);
        Dish gateauxChocolat = new Dish(4,"Gâteaux aux chocolat", DishtypeEnum.DESSERT,8000.00);
        Dish saladeDeFruits = new Dish(5, "Salade de fruits", DishtypeEnum.DESSERT, null);
        Dish rizLegumes = new Dish(35,"Riz aux légume" , DishtypeEnum.MAIN,  1500.00);
        Ingredient tomate = new Ingredient(2,"Tomate", 600.00, CategoryEnum.VEGETABLE);
        Ingredient laitue = new Ingredient( 1 , "Laitue" ,  800.00, CategoryEnum.VEGETABLE);
        Ingredient poulet = new Ingredient(3 ,"Poulet", 3000.00, CategoryEnum.ANIMAL);
        Ingredient chocolat = new Ingredient(4, "Chocolat", 3000.00, CategoryEnum.OTHER);
        Ingredient beurre = new Ingredient( 5, "Beurre", 2500.00, CategoryEnum.DAIRY);
        Ingredient boeuf = new Ingredient(6, "Boeuf", 134.00, CategoryEnum.ANIMAL);
        DishIngredients dishIngredients1 = new DishIngredients( 1 ,saladeFraiche, laitue, 0.20, UnitType.KG);
        DishIngredients dishIngredients2 = new DishIngredients(2,saladeFraiche, tomate, 0.15, UnitType.KG);
        DishIngredients dishIngredients3 = new DishIngredients(3, pouletGrille,poulet,1.00 , UnitType.KG);
        DishIngredients dishIngredients4 = new DishIngredients(4, gateauxChocolat, chocolat, 0.30, UnitType.KG);
        DishIngredients dishIngredients5 = new DishIngredients(5,gateauxChocolat, beurre, 0.20, UnitType.KG);
        System.out.println(saladeFraiche.getDishCost());
        System.out.println(saladeFraiche.getMarginGross());
        pouletGrille.setDishIngredients(List.of(dishIngredients3));
        System.out.println(pouletGrille.getDishCost());
        System.out.println(pouletGrille.getMarginGross());
        gateauxChocolat.setDishIngredients(List.of(dishIngredients4, dishIngredients5));
        System.out.println(gateauxChocolat.getDishCost());
        System.out.println(gateauxChocolat.getMarginGross());
        Ingredient oignon  = new Ingredient(6, "Oignon",500.00, CategoryEnum.VEGETABLE);
        Ingredient fromage = new Ingredient(7, "Fromage",1200.00,CategoryEnum.DAIRY);
        DishIngredients dishIngredient6 = new DishIngredients(6, saladeFraiche,oignon,0.15, UnitType.KG);
        DishIngredients dishIngredient7 = new DishIngredients(7, saladeFraiche, fromage, 0.18, UnitType.KG);
        saladeFraiche.setDishIngredients(List.of(dishIngredients1, dishIngredients2, dishIngredient6, dishIngredient7));
        data.saveDish(saladeFraiche);
        data.saveIngredient(boeuf);



    }
}
