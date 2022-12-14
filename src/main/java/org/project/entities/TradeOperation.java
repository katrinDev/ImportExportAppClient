package org.project.entities;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class TradeOperation {
    private int operationId;
    private String operationType;
    private double fullCost;

    private String supplyDate;

    private Company company;

    private transient List<Order> orders;

    Set<User> users = new HashSet<>();

    public int getOperationId() {
        return operationId;
    }


    public void setOperationId(int operationId) {
        this.operationId = operationId;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }


    public Double getFullCost() {
        return fullCost;
    }

    public void setFullCost(Double fullCost) {
        this.fullCost = fullCost;
    }

    public String getSupplyDate() {
        return supplyDate;
    }

    public void setSupplyDate(String supplyDate) {
        this.supplyDate = supplyDate;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }


    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public void setUser(User user){ this.users.add(user);}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TradeOperation that = (TradeOperation) o;
        return operationId == that.operationId && Objects.equals(operationType, that.operationType) && Objects.equals(fullCost, that.fullCost) && Objects.equals(supplyDate, that.supplyDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operationId, operationType, fullCost, supplyDate);
    }

    @Override
    public String toString() {
        return "TradeOperation{" +
                "operationId=" + operationId +
                ", operationType='" + operationType + '\'' +
                ", fullCost=" + fullCost +
                ", supplyDate=" + supplyDate +
                ", company=" + company +
                ", orders=" + orders +
                ", users=" + users +
                '}';
    }
}
