package com.gisma.competition.acm.service;

import com.gisma.competition.acm.api.dto.SubmissionResponseDto;
import com.gisma.competition.acm.api.dto.SubmitCompetitionResponseDto;
import com.gisma.competition.acm.api.exception.CompetitionNotExistException;
import com.gisma.competition.acm.assembler.LeaderBoardAssembler;
import com.gisma.competition.acm.persistence.entity.Competition;
import com.gisma.competition.acm.persistence.entity.LeaderBoard;
import com.gisma.competition.acm.persistence.repository.CompetitionRepository;
import com.gisma.competition.acm.persistence.repository.LeaderBoardRepository;
import com.gisma.competition.acm.persistence.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LeaderBoardService {

    private final LeaderBoardRepository leaderBoardRepository;
    private final UserRepository userRepository;
    private final CompetitionRepository competitionRepository;
    private final LeaderBoardAssembler leaderBoardAssembler;

    @Transactional
    public void saveOrUpdateLeaderBoard(SubmitCompetitionResponseDto responseDto, int userId, Long submissionTime) {
        LeaderBoard leaderBoard = leaderBoardRepository.
                findByCompetition_CompetitionIdAndUser_UserId(responseDto.getCompetitionId(), userId).orElseGet(() -> {
                    LeaderBoard newLeaderBoard = new LeaderBoard();
                    newLeaderBoard.setCompetition(competitionRepository.findById(responseDto.getCompetitionId()).orElseThrow());
                    newLeaderBoard.setUser(userRepository.findById(userId).orElseThrow());
                    return newLeaderBoard;
                });

        leaderBoard.setSubmissionTime(submissionTime);
        leaderBoard.setCpuTime(responseDto.getCpuTime());
        leaderBoard.setSuccess(responseDto.getSuccess());
        leaderBoard.setFailureTestCasesCount(responseDto.getFailureTestCases().size());
        leaderBoard.setSuccessTestCasesCount(responseDto.getSuccessTestCases().size());
        leaderBoardRepository.save(leaderBoard);
    }

    public List<SubmissionResponseDto> getSuccessSubmissionSortByCpuTime(int competitionId) throws CompetitionNotExistException {
        Optional<Competition> competitionOptional = competitionRepository.getCompetitionByCompetitionId(competitionId);
        if (competitionOptional.isEmpty()) {
            throw new CompetitionNotExistException(competitionId);
        }
        List<LeaderBoard> leaderBoards = leaderBoardRepository.
                getLeaderBoardsByCompetition_CompetitionIdAndSuccessTrueOrderByCpuTimeAsc(competitionId);
        return leaderBoardAssembler.toSubmissionResponseDtos(leaderBoards);
    }
}
