package com.gisma.competition.acm.service;

import com.gisma.competition.acm.persistence.entity.UserCode;
import com.gisma.competition.acm.persistence.repository.UserCodeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserCodeService {

    private final UserCodeRepository userCodeRepository;

    public void saveCode(String code,
                         Integer userId,
                         Long submissionTime,
                         Integer competitionId,
                         Double cpuTime) {
        UserCode userCode = new UserCode();
        userCode.setCode(code);
        userCode.setUserId(userId);
        userCode.setSubmissionTime(submissionTime);
        userCode.setCompetitionId(competitionId);
        userCode.setCpuTime(cpuTime);
        userCodeRepository.save(userCode);
    }
}
