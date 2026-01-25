package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Dish {
    private int id;
    private String name;
    private DishtypeEnum dishType;
    private Double price;
    private List<DishIngredients> dishIngredients;

    public Dish(){

    }

    public Dish(int id, String name, DishtypeEnum dishType, Double price) {
        this.id = id;
        this.name = name;
        this.dishType = dishType;
        this.price = price;
        this.dishIngredients = new ArrayList<>();
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
        System.out.println("La marge de " + getName());
        if (getPrice() == null){
            throw new RuntimeException("Le prix du plat est null ");
        }

        return getPrice() - getDishCost();
    }

    public Double getDishCost(){
        System.out.println("Le coût de " + getName());
        double totalPrice = 0;
        for (int i = 0; i < dishIngredients.size(); i++) {
            Double quantity = dishIngredients.get(i).getQuantity();
            if (getPrice() == null){
                totalPrice = 00.0;

            }else{
                totalPrice += dishIngredients.get(i).getIngredient().getPrice() * quantity;
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

    public List<DishIngredients> getDishIngredients() {
        return dishIngredients;
    }

    public void setDishIngredients(List<DishIngredients> dishIngredients) {
        this.dishIngredients = dishIngredients;
    }


    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", DishType=" + dishType +
                ", Price=" + price +
                ", DishIngredients=" + dishIngredients +
                '}';
    }


}
