package org.example;

import java.util.Objects;

public class Dish {
    private final int id;
    private final String name;
    private final DishtypeEnum DishType;

    public Dish(int id, String name, DishtypeEnum dishType) {
        this.id = id;
        this.name = name;
        DishType = dishType;
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

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", DishType=" + DishType +
                '}';
    }
}
