package ru.itentica.autoservice.entities;

public enum PriceItemUnit {
    HUMAN_HOURS(1, "Чел.ч"),
    LITERS(2, "л.");

    private final int id;
    private final String title;

    PriceItemUnit(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
