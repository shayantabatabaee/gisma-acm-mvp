package com.gisma.competition.acm.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@Table(name = "leader_board",
        uniqueConstraints = {
                @UniqueConstraint(name = "uc_leaderboard_competition_id_user_id",
                        columnNames = {"competition_id", "user_id"})
        },
        indexes = {
                @Index(name = "idx_leaderboard_competition_id", columnList = "competition_id"),
                @Index(name = "idx_leaderboard_user_id", columnList = "user_id")
        })
public class LeaderBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer leaderBoardId;

    @ManyToOne
    @ToString.Exclude
    @JsonIgnore
    @JoinColumn(name = "competition_id", nullable = false)
    private Competition competition;

    @ManyToOne
    @ToString.Exclude
    @JsonIgnore
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private long submissionTime;

    @Column(nullable = false)
    private Integer successTestCasesCount;

    @Column(nullable = false)
    private Integer failureTestCasesCount;

    @Column(nullable = false)
    private Boolean success;

    @Column(nullable = false)
    private Double cpuTime;
}
