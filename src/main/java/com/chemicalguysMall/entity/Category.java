package com.chemicalguysMall.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "category")
@Getter @Setter @ToString
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "category_id")
    private Long id;

    @Column(nullable = false)
    private String category_nm;

    /*
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL,
               orphanRemoval = true, fetch = FetchType.LAZY)
    List<Item> items = new ArrayList<>();
     */
}
