package org.example;

import java.util.List;
import java.util.Objects;

public class Ingredient {
    private int id;
    private String name;
    private double price;
    private CategoryEnum category;
    private List<StockMovement> stockMovementList;

    public Ingredient(){

    }

    public Ingredient(int id, String name, double price, CategoryEnum category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setCategory(CategoryEnum category) {
        this.category = category;
    }

    public List<StockMovement> getStockMovementList() {
        return stockMovementList;
    }

    public void setStockMovementList(List<StockMovement> stockMovementList) {
        this.stockMovementList = stockMovementList;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return id == that.id && Double.compare(price, that.price) == 0 && Objects.equals(name, that.name) && category == that.category ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, category );
    }

    @Override
    public String toString() {
        return "Ingredients{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", category=" + category +
                '}';
    }

    void getStockValue(){
        return ;

    }


}
