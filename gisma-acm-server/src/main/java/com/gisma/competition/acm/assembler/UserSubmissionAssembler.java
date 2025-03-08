package com.gisma.competition.acm.assembler;

import com.gisma.competition.acm.api.dto.LastUserSubmissionResponseDto;
import com.gisma.competition.acm.api.dto.UserSubmissionResponseDto;
import com.gisma.competition.acm.api.dto.UserSubmissionsResponseDto;
import com.gisma.competition.acm.persistence.entity.LeaderBoard;
import com.gisma.competition.acm.persistence.entity.UserCode;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserSubmissionAssembler {

    public UserSubmissionsResponseDto toUserSubmissionsResponseDto(List<UserCode> userCodes, List<LeaderBoard> leaderBoards) {
        UserSubmissionsResponseDto userSubmissionsResponseDto = new UserSubmissionsResponseDto();
        List<LastUserSubmissionResponseDto> lastUserSubmissionResponseDtos = new ArrayList<>();
        for (LeaderBoard leaderBoard : leaderBoards) {
            LastUserSubmissionResponseDto lastUserSubmissionResponseDto = new LastUserSubmissionResponseDto();
            lastUserSubmissionResponseDto.setCompetitionId(leaderBoard.getCompetition().getCompetitionId());
            lastUserSubmissionResponseDto.setCpuTime(leaderBoard.getCpuTime());
            lastUserSubmissionResponseDto.setSubmissionTime(leaderBoard.getSubmissionTime());
            lastUserSubmissionResponseDto.setSuccess(leaderBoard.getSuccess());
            lastUserSubmissionResponseDto.setSuccessTestCasesCount(leaderBoard.getSuccessTestCasesCount());
            lastUserSubmissionResponseDto.setFailedTestCasesCount(leaderBoard.getFailureTestCasesCount());
            lastUserSubmissionResponseDtos.add(lastUserSubmissionResponseDto);
        }
        userSubmissionsResponseDto.setLastSubmissions(lastUserSubmissionResponseDtos);

        List<UserSubmissionResponseDto> userSubmissionResponseDtos = new ArrayList<>();
        for (UserCode userCode : userCodes) {
            UserSubmissionResponseDto userSubmissionResponseDto = new UserSubmissionResponseDto();
            userSubmissionResponseDto.setCompetitionId(userCode.getCompetitionId());
            userSubmissionResponseDto.setCpuTime(userCode.getCpuTime());
            userSubmissionResponseDto.setSubmissionTime(userCode.getSubmissionTime());
            userSubmissionResponseDtos.add(userSubmissionResponseDto);
        }
        userSubmissionsResponseDto.setAllSubmissions(userSubmissionResponseDtos);


        return userSubmissionsResponseDto;
    }

}
