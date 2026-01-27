package org.example;

public class StockValue {
    private double value;
    private UnitType unit;

    public StockValue() {

    }

    public StockValue(double value, UnitType unit) {
        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public UnitType getUnit() {
        return unit;
    }

    public void setUnit(UnitType unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "StockValue{" +
                "value=" + value +
                ", unit=" + unit +
                '}';
    }
}
