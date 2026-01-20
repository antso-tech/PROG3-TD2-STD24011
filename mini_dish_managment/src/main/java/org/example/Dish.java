package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Dish {
    private int id;
    private String name;
    private DishtypeEnum dishType;
    private Double price;
    private List<DishIngredients> ingredients;

    public Dish(){

    }

    public Dish(int id, String name, DishtypeEnum dishType, Double price) {
        this.id = id;
        this.name = name;
        this.dishType = dishType;
        this.price = price;
        this.ingredients = new ArrayList<>();
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Dish dish = (Dish) o;
        return id == dish.id && Objects.equals(name, dish.name) && dishType == dish.dishType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, dishType);
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


    public double getMarginGross(){
        if (getPrice() == null){
            throw new RuntimeException("vous devez ajouter un nouveau plat ");
        }

        return getPrice() - getDishCost();
    }

    public Double getDishCost(){
        double totalPrice = 0;
        for (int i = 0; i < ingredients.size(); i++) {
            Double quantity = ingredients.get(i).getQuantity();
            if (getIngredients().isEmpty() || getPrice() == null){
                throw  new RuntimeException("Le prix du plat est vide ");
            }else{
                totalPrice += ingredients.get(i).getIngredient().getPrice() * quantity;
            }

        }
        return totalPrice;

    }

    public DishtypeEnum getDishType() {
        return dishType;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setDishType(DishtypeEnum dishType) {
        this.dishType = dishType;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<DishIngredients> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<DishIngredients> ingredients) {
        this.ingredients = ingredients;
    }


    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", DishType=" + dishType +
                ", Price=" + price +
                ", Ingredients=" + ingredients +
                '}';
    }

}
