package com.gisma.competition.acm.persistence.repository;

import com.gisma.competition.acm.persistence.entity.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestCaseRepository extends JpaRepository<TestCase, Integer> {

    List<TestCase> findByCompetition_CompetitionId(Integer competitionCompetitionId);

}
