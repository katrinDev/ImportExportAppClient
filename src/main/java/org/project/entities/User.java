package org.project.entities;

import com.google.gson.annotations.Expose;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class User {

    private int userId;

    private String login;

    private String password;

    private Role role;

    private Person person;


    @Expose(serialize = false)
    private Set<TradeOperation> operations = new HashSet<>();


    public Set<TradeOperation> getOperations() {
        return operations;
    }

    public void setOperations(Set<TradeOperation> operations) {
        this.operations = operations;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId && Objects.equals(login, user.login) && Objects.equals(password, user.password) && Objects.equals(role, user.role) && Objects.equals(person, user.person);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, login, password, role, person);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", person=" + person +
                '}';
    }
}
