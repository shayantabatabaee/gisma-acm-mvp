package com.gisma.competition.acm.persistence.repository;

import com.gisma.competition.acm.persistence.entity.Competition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompetitionRepository extends JpaRepository<Competition, Integer> {
    Optional<Competition> getCompetitionByName(String name);
}
