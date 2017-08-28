package io.dehli.transactions.models;

import org.hibernate.annotations.ColumnDefault;
import javax.persistence.*;

@Entity
@Table(name="transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    /// The id the transaction corresponds to
    @Column(nullable = false)
    private Integer account_id;

    /// How much money is stored in the user's account
    @Column(nullable = false)
    private Double amount;

    protected Transaction() {}

    public Transaction(Integer account_id, Double amount) {
        this.account_id = account_id;
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAccount_id() {
        return account_id;
    }

    public void setAccount_id(Integer account_id) {
        this.account_id = account_id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

}
