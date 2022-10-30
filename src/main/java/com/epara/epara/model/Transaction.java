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
@TypeDef(name = "json", typeClass = JsonBinaryType.class)
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @NotNull
    private LocalDate creationDate = LocalDate.now();

    private boolean success;

    @NotNull
    private String base;

    @ElementCollection
    @NotNull
    Map<String, Double> rates;

}
