package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Dish {
    private int id;
    private String name;
    private DishtypeEnum DishType;
    private List<Ingredient> ingredients = new ArrayList<>();



    public void setId(int id) {
        this.id = id;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Dish dish = (Dish) o;
        return id == dish.id && Objects.equals(name, dish.name) && DishType == dish.DishType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, DishType);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public DishtypeEnum getDishType() {
        return DishType;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setDishType(DishtypeEnum dishType) {
        DishType = dishType;
    }


    public void addIngredient(Ingredient ingredient){
        this.ingredients.add(ingredient);
    }

    public List<Ingredient> getIngredient() {
        return ingredients;
    }


    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", DishType=" + DishType +
                ", Ingredients=" + ingredients.stream().map(Ingredient::getName).toList() +
                '}';
    }

}
