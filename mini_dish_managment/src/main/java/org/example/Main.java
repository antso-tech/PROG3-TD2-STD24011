package org.example;

public class Main {
    public static void main(String[] args) {
        DataRetriever data = new DataRetriever();
        data.findDishById(4);
        data.findIngredientbyDishId(4);

    }
}
