package com.epara.exchange.model;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.TypeDef;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Map;

@Entity(name = "Transaction")
@Table(name = "transaction")
@TypeDef(name = "json", typeClass = JsonBinaryType.class)
public class Transaction {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id")
    private String id;
    @Column(name = "creationDate")
    @NotNull
    private LocalDate creationDate = LocalDate.now();

    @Column(name = "success")
    @NotNull
    private boolean success;

    @Column(name = "base")
    @NotNull
    private String base;

    @ElementCollection
    @NotNull
    Map<String, Double> rates;

    public Transaction() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Map<String, Double> getRates() {
        return rates;
    }

    public void setRates(Map<String, Double> rates) {
        this.rates = rates;
    }

    public Transaction(String id, LocalDate creationDate, boolean success, String base, Map<String, Double> rates) {
        this.id = id;
        this.creationDate = creationDate;
        this.success = success;
        this.base = base;
        this.rates = rates;
    }
}
