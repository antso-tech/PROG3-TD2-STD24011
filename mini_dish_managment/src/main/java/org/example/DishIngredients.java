package org.example;

import java.util.Objects;

public class DishIngredients {
    int id;
    Dish dish;
    Ingredient ingredient;
    Double quantity;
    UnitType unit;

    public DishIngredients(){

    }


    public DishIngredients(int id, Dish dish, Ingredient ingredient, Double quantity, UnitType unit) {
        this.dish = dish;
        this.ingredient = ingredient;
        this.quantity = quantity;
        this.unit = unit;
        this.id = id;
    }





    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public UnitType getUnit() {
        return unit;
    }

    public void setUnit(UnitType unit) {
        this.unit = unit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DishIngredients that = (DishIngredients) o;
        return id == that.id && Objects.equals(dish, that.dish) && Objects.equals(ingredient, that.ingredient) && Objects.equals(quantity, that.quantity) && unit == that.unit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dish, ingredient, quantity, unit);
    }

    @Override
    public String toString() {
        return "DishIngredients{" +
                "id=" + id +
                ", dish=" + dish +
                ", ingredient=" + ingredient +
                ", quantity=" + quantity +
                ", unit=" + unit +
                '}';
    }
}
