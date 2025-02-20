package com.gisma.competition.acm.persistence.repository;

import com.gisma.competition.acm.persistence.entity.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestCaseRepository extends JpaRepository<TestCase, Integer> {
}
