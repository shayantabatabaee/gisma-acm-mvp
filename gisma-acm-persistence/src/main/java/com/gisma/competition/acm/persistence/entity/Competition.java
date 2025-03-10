package com.gisma.competition.acm.persistence.entity;

import com.gisma.competition.acm.persistence.enumeration.CompetitionLevelModel;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "competition",
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_competition_name", columnNames = "name")
        })
public class Competition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer competitionId;

    @Column(nullable = false, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CompetitionLevelModel level;

    @Column(nullable = false)
    private Long startTime;

    @Column(nullable = false)
    private Long duration;

    @Lob
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String description;

    @OneToOne(mappedBy = "competition", cascade = CascadeType.ALL, orphanRemoval = true)
    private Template template;

    @OneToMany(mappedBy = "competition", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TestCase> testCases;
}
