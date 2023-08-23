package com.chemicalguysMall.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "notice")
@Getter @Setter @ToString
public class Notice extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "notice_id")
    private Long id;

    @Column(length = 200, nullable = false)
    private String subject;

    @Lob
    @Column(nullable = false)
    private String content;

}
