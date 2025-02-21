package com.gisma.competition.acm.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Template {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer templateId;

    @OneToOne
    @JoinColumn(name = "competition_id", unique = true, nullable = false)
    private Competition competition;

    @Lob
    @Column(nullable = false)
    private String className;

    @Column(nullable = false)
    private String methodName;
}
