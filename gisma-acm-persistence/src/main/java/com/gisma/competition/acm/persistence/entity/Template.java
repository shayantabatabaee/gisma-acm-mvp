package com.gisma.competition.acm.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@Table(name = "template",
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_competition_id", columnNames = "competition_id")
        })
public class Template {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer templateId;

    @OneToOne
    @ToString.Exclude
    @JsonIgnore
    @JoinColumn(name = "competition_id", unique = true, nullable = false)
    private Competition competition;

    @Column(nullable = false)
    private String className;

    @Column(nullable = false)
    private String methodName;
}
