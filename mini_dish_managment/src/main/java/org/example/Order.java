package org.example;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Order {
    private int id;
    private String reference;
    private Instant creationDateTime;
    private List<DishOrder> dishOrder;

    public Order() {

    }

    public Order(int id, String reference, Instant creationDateTime) {
        this.id = id;
        this.reference = reference;
        this.creationDateTime = creationDateTime;
        this.dishOrder = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Instant getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(Instant creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public List<DishOrder> getDishOrder() {
        return dishOrder;
    }

    public void setDishOrder(List<DishOrder> dishOrder) {
        this.dishOrder = dishOrder;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", reference='" + reference + '\'' +
                ", creationDateTime=" + creationDateTime +
                ", dishOrder=" + dishOrder +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id && Objects.equals(reference, order.reference) && Objects.equals(creationDateTime, order.creationDateTime) && Objects.equals(dishOrder, order.dishOrder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, reference, creationDateTime, dishOrder);
    }


    public Double getTotalAmountWithoutVAT(){
        Double totalAmount = dishOrder
                .stream()
                .mapToDouble(dishOrder -> dishOrder
                        .getDish()
                        .getPrice() * dishOrder.getQuantity()).sum();
        System.out.println();
        return totalAmount;
    }


}
