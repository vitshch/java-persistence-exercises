package com.bobocode.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PRIVATE;

/**
 * todo:
 * - configure JPA entity
 * - specify table name: "product"
 * - configure auto generated identifier
 * - configure mandatory column "name" for field {@link Product#name}
 * <p>
 * - configure lazy many-to-one relation between {@link Product} and {@link Company}
 * - configure foreign key column "company_id" references company table
 * - override equals() and hashCode() considering entity id
 */
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
