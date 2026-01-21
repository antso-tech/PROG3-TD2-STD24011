package org.example;

import com.sun.jdi.Value;

import java.lang.classfile.Instruction;
import java.time.Instant;

public class StockMovement {
    int id;
    MovementTypeEnum type;
    StockValue value;
    Instant creationDateTime;

    public StockMovement(int id, MovementTypeEnum type, StockValue value, Instant creationDateTime) {
        this.id = id;
        this.type = type;
        this.value = value;
        this.creationDateTime = creationDateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MovementTypeEnum getType() {
        return type;
    }

    public void setType(MovementTypeEnum type) {
        this.type = type;
    }

    public StockValue getValue() {
        return value;
    }

    public void setValue(StockValue value) {
        this.value = value;
    }

    public Instant getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(Instant creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    @Override
    public String toString() {
        return "StockMovement{" +
                "id=" + id +
                ", type=" + type +
                ", value=" + value +
                ", creationDateTime=" + creationDateTime +
                '}';
    }
}
