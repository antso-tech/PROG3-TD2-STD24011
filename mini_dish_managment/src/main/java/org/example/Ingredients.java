package org.example;

import java.util.Objects;

public class Ingredients {
    private final int id;
    private final String name;
    private final double price;
    private final CategoryEnum category;
    private final Dish dish;

    public Ingredients(int id, String name, double price, CategoryEnum category, Dish dish) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.dish = dish;
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

    public Dish getDish() {
        return dish;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Ingredients that = (Ingredients) o;
        return id == that.id && Double.compare(price, that.price) == 0 && Objects.equals(name, that.name) && category == that.category && Objects.equals(dish, that.dish);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, category, dish);
    }

    @Override
    public String toString() {
        return "Ingredients{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", category=" + category +
                ", dish=" + dish +
                '}';
    }
}
