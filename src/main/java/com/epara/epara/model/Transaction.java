package com.epara.epara.model;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.TypeDef;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Map;

@Entity(name = "Transaction")
@Table(name = "transaction")
@TypeDef(name = "json", typeClass = JsonBinaryType.class)
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
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

}
