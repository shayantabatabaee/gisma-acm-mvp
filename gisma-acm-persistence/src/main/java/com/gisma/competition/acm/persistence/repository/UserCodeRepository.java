package com.gisma.competition.acm.persistence.repository;

import com.gisma.competition.acm.persistence.entity.UserCode;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCodeRepository extends MongoRepository<UserCode, String> {
    List<UserCode> getUserCodesByUserId(Integer userId);
}
