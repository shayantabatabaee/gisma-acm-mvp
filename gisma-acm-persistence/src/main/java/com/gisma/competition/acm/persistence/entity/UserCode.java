package com.gisma.competition.acm.persistence.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "user_codes")
public class UserCode {

    @Id
    private String id;

    @Indexed(name = "idx_user_id")
    private Integer userId;

    private Integer competitionId;
    private Long submissionTime;
    private Double cpuTime;
    private String code;

}
