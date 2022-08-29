package ru.itentica.autoservice.entities;


import java.util.Arrays;

public enum PriceItem {
    WHEEL_REPLACE(1, "Wheel replace", 500, PriceItemUnit.HUMAN_HOURS),
    OIL_CHANGE(2, "Oil change", 1200, PriceItemUnit.HUMAN_HOURS),
    OIL_NUMBER_ONE(3, "Oil Shell", 900, PriceItemUnit.LITERS);


    private final int id;

    private final String title;

    private final double price;

    private final PriceItemUnit unit;

    PriceItem(int id, String title, double price, PriceItemUnit unit) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.unit = unit;
    }

    public static PriceItem getById(int id) {
        return Arrays.stream(values())
                .filter(priceItem -> priceItem.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public PriceItemUnit getUnit() {
        return unit;
    }
}
