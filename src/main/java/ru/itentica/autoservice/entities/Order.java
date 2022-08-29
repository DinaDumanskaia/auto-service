package ru.itentica.autoservice.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Order {
    private Long id;
    private String reason;
    private Date beginDate;
    private Date endDate;
    private String comment;
    private List<WorkItem> workItems = new ArrayList<>();
    private List<OrderHistoryItem> orderHistoryItems = new ArrayList<>();
    private Principal client;
    private Principal master;
    private Principal administrator;

    public Order(Long id, String reason, Date beginDate, Date endDate, String comment, List<WorkItem> workItems,
                 List<OrderHistoryItem> orderHistoryItems, Principal client, Principal master, Principal administrator)
    {
        this.id = id;
        this.reason = reason;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.comment = comment;
        this.workItems.addAll(workItems);
        this.orderHistoryItems.addAll(orderHistoryItems);
        this.client = client;
        this.master = master;
        this.administrator = administrator;
    }

    public Order(Long orderId, Order initialOrder, List<OrderHistoryItem> orderHistoryItem) {
        this(orderId,
                initialOrder.getReason(),
                initialOrder.getBeginDate(),
                initialOrder.getEndDate(),
                initialOrder.getComment(),
                initialOrder.getWorkItems(),
                orderHistoryItem,
                initialOrder.getClient(),
                initialOrder.getMaster(),
                initialOrder.getAdministrator());
    }

    public Order() {

    }

    public Long getId() {
        return id;
    }

    public String getReason() {
        return reason;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getComment() {
        return comment;
    }

    public List<WorkItem> getWorkItems() {
        return workItems;
    }

    public List<OrderHistoryItem> getOrderHistoryItemsList() {
        return orderHistoryItems;
    }

    public Principal getClient() {
        return client;
    }

    public Principal getMaster() {
        return master;
    }

    public Principal getAdministrator() {
        return administrator;
    }

    public void addWorkItem(WorkItem... workItems) {
        this.workItems.addAll(Arrays.asList(workItems));
    }

    public void setMaster(Principal master) {
        this.master = master;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setWorkItems(List<WorkItem> workItems) {
        this.workItems = workItems;
    }

    public void setOrderHistoryItemsList(List<OrderHistoryItem> orderHistoryItemsList) {
        this.orderHistoryItems = orderHistoryItemsList;
    }

    public void setClient(Principal client) {
        this.client = client;
    }

    public void setAdministrator(Principal administrator) {
        this.administrator = administrator;
    }

    public String showAllWorkItems() {
        if (workItems.isEmpty())
            return "No work items exists";

        StringBuilder sb = new StringBuilder();
        for(WorkItem workItem : workItems) {
            sb.append("[")
                    .append(workItem.getQuantity())
                    .append(" * ")
                    .append(workItem.getPriceItem().getTitle())
                    .append(" per ")
                    .append(workItem.getPriceItem().getPrice())
                    .append("] ");
        }

        return sb.toString();
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", reason='" + reason + '\'' +
                ", beginDate=" + beginDate +
                ", endDate=" + endDate +
                ", comment='" + comment + '\'' +
                ", workItems=" + workItems +
                ", orderHistoryItems=" + orderHistoryItems +
                ", client=" + client +
                ", master=" + master +
                ", administrator=" + administrator +
                '}';
    }
}
