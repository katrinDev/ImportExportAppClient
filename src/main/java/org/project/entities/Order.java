package org.project.entities;

import java.util.Objects;


public class Order {

    private int orderId;
    private int itemAmount;

    private Item item;

    private TradeOperation operation;

    public Order() {}

    public Order(Item item, int itemAmount) {
        this.itemAmount = itemAmount;
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public TradeOperation getOperation() {
        return operation;
    }

    public void setOperation(TradeOperation operation) {
        this.operation = operation;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }


    public int getItemAmount() {
        return itemAmount;
    }

    public void setItemAmount(int itemAmount) {
        this.itemAmount = itemAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderId == order.orderId &&  itemAmount == order.itemAmount && Objects.equals(item, order.item) && Objects.equals(operation, order.operation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, itemAmount, item, operation);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", itemAmount=" + itemAmount +
                ", item=" + item +
                ", operation=" + operation +
                '}';
    }
}
