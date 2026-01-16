package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Dish {
    private int id;
    private String name;
    private DishtypeEnum DishType;
    private List<Ingredient> ingredients = new ArrayList<>();
    private Double price;


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

    public String getDishName(){
        if (name == null){
            System.out.println("Dish est null");
            return null;

        }else{
            return name;
        }
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

    public List<Ingredient> getIngredient() {
        return ingredients;
    }

    public Double getPrice() {
        return price;
    }

    public Double getDishCost (){
        return  ingredients.stream().mapToDouble(Ingredient::getPrice).sum();
    }
    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", DishType=" + DishType +
                ", Ingredients=" + ingredients.stream().map(Ingredient::getName).toList() +
                ", Price=" + price +
                '}';
    }


    double getGrossMargin(){
        if (price != null){
            return price - getDishCost();
        }else{
            throw new IllegalArgumentException("Le prix de votre plat ne possede pas de valeur souhaité !");
        }

    }

}
