package io.dehli.accounts.models;

import org.hibernate.annotations.ColumnDefault;
import javax.persistence.*;

@Entity
@Table(name="accounts")
public class Account {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    /// The name of the account
    private String name;

    /// How much money is stored in the user's account
    @Column(nullable = false)
    @ColumnDefault("0")
    private Double balance = 0.0;

    protected Account() {}

    public Account(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public void addToBalance(Double amount) {
        this.balance += amount;
    }
}
