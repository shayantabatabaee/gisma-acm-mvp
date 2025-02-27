package com.gisma.competition.acm.assembler;

import com.gisma.competition.acm.api.dto.SubmissionResponseDto;
import com.gisma.competition.acm.persistence.entity.LeaderBoard;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LeaderBoardAssembler {

    public List<SubmissionResponseDto> toSubmissionResponseDtos(List<LeaderBoard> leaderBoards) {
        List<SubmissionResponseDto> submissionResponseDtos = new ArrayList<>();
        for (LeaderBoard leaderBoard : leaderBoards) {
            SubmissionResponseDto submissionResponseDto = new SubmissionResponseDto();
            submissionResponseDto.setSuccess(leaderBoard.getSuccess());
            submissionResponseDto.setSubmissionTime(leaderBoard.getSubmissionTime());
            submissionResponseDto.setUserId(leaderBoard.getUser().getUserId());
            submissionResponseDto.setCpuTime(leaderBoard.getCpuTime());
            submissionResponseDto.setSuccessTestCasesCount(leaderBoard.getSuccessTestCasesCount());
            submissionResponseDto.setFailedTestCasesCount(leaderBoard.getFailureTestCasesCount());
            submissionResponseDtos.add(submissionResponseDto);
        }
        return submissionResponseDtos;
    }

}
