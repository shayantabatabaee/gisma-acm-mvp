package com.gisma.competition.acm.persistence.repository;

import com.gisma.competition.acm.persistence.entity.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplateRepository extends JpaRepository<Template, Integer> {

}
