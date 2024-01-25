package com.bobocode.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import org.hibernate.annotations.NaturalId;

import java.util.HashSet;
import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

/**
 * todo:
 * - implement equals() and hashCode() based on {@link Book#isbn}
 * - make setter for field {@link Book#authors} private
 * - initialize field {@link Book#authors} as new {@link HashSet}
 * <p>
 * - configure JPA entity
 * - specify table name: "book"
 * - configure auto generated identifier
 * - configure mandatory column "name" for field {@link Book#name}
 * - configure mandatory unique column "isbn" for field {@link Book#isbn}, it is a natural key candidate
 * <p>
 * - configure many-to-many relation as mapped on the {@link Author} side
 */
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "isbn")

@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @NaturalId
    @Column(nullable = false, unique = true)
    private String isbn;

    @Setter(value = PRIVATE)
    @ManyToMany(mappedBy = "books")
    private Set<Author> authors = new HashSet<>();

}
