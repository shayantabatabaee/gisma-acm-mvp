package com.gisma.competition.acm.persistence.entity;

import com.gisma.competition.acm.persistence.enumeration.ArgumentTypeModel;
import com.gisma.competition.acm.persistence.enumeration.DataTypeModel;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
public class TestCase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer testCaseId;

    @Column(nullable = false)
    private Integer argumentId;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "competition_id", nullable = false)
    private Competition competition;

    @Column(nullable = false)
    private boolean isArray;

    @Column(nullable = false)
    private DataTypeModel dataType;

    @Column(nullable = false)
    private String value;

    @Column(nullable = false)
    private ArgumentTypeModel argumentType;
}
