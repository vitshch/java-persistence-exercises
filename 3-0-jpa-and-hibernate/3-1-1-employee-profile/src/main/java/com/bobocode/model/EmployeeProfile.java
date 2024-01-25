package com.bobocode.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * todo:
 * - configure JPA entity
 * - specify table name: "employee_profile"
 * - configure not nullable columns: position, department
 *
 * - map relation between {@link Employee} and {@link EmployeeProfile} using foreign_key column: "employee_id"
 * - configure a derived identifier. E.g. map "employee_id" column should be also a primary key (id) for this entity
 */
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "employee_profile")
public class EmployeeProfile {

    @Id
    private Long id;

    @OneToOne
    @JoinColumn(name = "employee_id")
    @MapsId
    private Employee employee;

    @Column(nullable = false)
    private String position;

    @Column(nullable = false)
    private String department;
}
