package com.gisma.competition.acm.service;

import com.gisma.competition.acm.api.dto.SubmitCompetitionResponseDto;
import com.gisma.competition.acm.persistence.entity.LeaderBoard;
import com.gisma.competition.acm.persistence.repository.CompetitionRepository;
import com.gisma.competition.acm.persistence.repository.LeaderBoardRepository;
import com.gisma.competition.acm.persistence.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LeaderBoardService {

    private final LeaderBoardRepository leaderBoardRepository;
    private final UserRepository userRepository;
    private final CompetitionRepository competitionRepository;

    public void saveOrUpdateLeaderBoard(SubmitCompetitionResponseDto responseDto, int userId) {
        LeaderBoard leaderBoard = leaderBoardRepository.
                findByCompetition_CompetitionIdAndUser_UserId(responseDto.getCompetitionId(), userId).orElseGet(() -> {
                    LeaderBoard newLeaderBoard = new LeaderBoard();
                    newLeaderBoard.setCompetition(competitionRepository.findById(responseDto.getCompetitionId()).orElseThrow());
                    newLeaderBoard.setUser(userRepository.findById(userId).orElseThrow());
                    return newLeaderBoard;
                });

        leaderBoard.setSubmissionTime(System.currentTimeMillis());
        leaderBoard.setCpuTime(responseDto.getCpuTime());
        leaderBoard.setSuccess(responseDto.getSuccess());
        leaderBoard.setFailureTestCasesCount(responseDto.getFailureTestCases().size());
        leaderBoard.setSuccessTestCasesCount(responseDto.getSuccessTestCases().size());
        leaderBoardRepository.save(leaderBoard);
    }
}
