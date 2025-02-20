package com.gisma.competition.acm.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class TestCase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer testCaseId;

    @ManyToOne
    @JoinColumn(name = "competition_id", nullable = false)
    private Competition competition;

    @Column(nullable = false)
    private String input;

    @Column(nullable = false)
    private String expectedOutput;
}
