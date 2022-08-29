package ru.itentica.autoservice.entities;

import ru.itentica.autoservice.services.IdProvider;
public class WorkItem {
    private Long id;

    private int quantity;

    private double cost;

    private PriceItem priceItem;



    public WorkItem(int quantity, PriceItem priceItem) {
        id = IdProvider.getNextLongId();
        this.quantity = quantity;
        this.priceItem = priceItem;
        cost = priceItem.getPrice() * quantity;
    }

    public WorkItem() {

    }

    public Long getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getCost() {
        return cost;
    }

    public PriceItem getPriceItem() {
        return priceItem;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setPriceItem(PriceItem priceItem) {
        this.priceItem = priceItem;
    }

    @Override
    public String toString() {
        return "WorkItem{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", cost=" + cost +
                ", priceItem=" + priceItem +
                '}';
    }
}
