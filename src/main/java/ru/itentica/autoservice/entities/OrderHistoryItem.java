package ru.itentica.autoservice.entities;

import java.util.Date;

public class OrderHistoryItem {
    private Long id;
    private OrderStatus status;
    private String comment;
    private Date creationDate;


    public OrderHistoryItem(Long id, OrderStatus status, String comment, Date creationDate) {
        this.id = id;
        this.status = status;
        this.comment = comment;
        this.creationDate = creationDate;
    }

    public OrderHistoryItem() {

    }

    public Long getId() {
        return id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public String getComment() {
        return comment;
    }

    public Date getCreationDate() {
        return creationDate;
    }
}
