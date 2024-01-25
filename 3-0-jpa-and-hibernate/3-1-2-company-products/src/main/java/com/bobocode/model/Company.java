package com.bobocode.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static lombok.AccessLevel.PRIVATE;

/**
 * todo:
 * - make setter for field {@link Company#products} private
 * - initialize field {@link Company#products} as new {@link ArrayList}
 * - implement a helper {@link Company#addProduct(Product)} that establishes a relation on both sides
 * - implement a helper {@link Company#removeProduct(Product)} that drops a relation on both sides
 * <p>
 * - configure JPA entity
 * - specify table name: "company"
 * - configure auto generated identifier
 * - configure mandatory column "name" for field {@link Company#name}
 * <p>
 * - configure one-to-many relationship as mapped on the child side
 * - override equals() and hashCode() considering entity id
 */
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "company")
public class Company {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Setter(PRIVATE)
    @OneToMany(mappedBy = "company")
    private List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        product.setCompany(this);
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
        product.setCompany(null);
    }
}