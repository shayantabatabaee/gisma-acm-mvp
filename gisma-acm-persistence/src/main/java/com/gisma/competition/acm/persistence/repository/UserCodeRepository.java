package com.gisma.competition.acm.persistence.repository;

import com.gisma.competition.acm.persistence.entity.UserCode;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCodeRepository extends MongoRepository<UserCode, String> {
}
