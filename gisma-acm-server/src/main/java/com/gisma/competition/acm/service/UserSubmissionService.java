package com.gisma.competition.acm.service;

import com.gisma.competition.acm.api.dto.UserSubmissionsResponseDto;
import com.gisma.competition.acm.api.exception.UserIdNotExistsException;
import com.gisma.competition.acm.assembler.UserSubmissionAssembler;
import com.gisma.competition.acm.persistence.entity.LeaderBoard;
import com.gisma.competition.acm.persistence.entity.User;
import com.gisma.competition.acm.persistence.entity.UserCode;
import com.gisma.competition.acm.persistence.repository.LeaderBoardRepository;
import com.gisma.competition.acm.persistence.repository.UserCodeRepository;
import com.gisma.competition.acm.persistence.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserSubmissionService {

    private final LeaderBoardRepository leaderBoardRepository;
    private final UserCodeRepository userCodeRepository;
    private final UserRepository userRepository;
    private final UserSubmissionAssembler userSubmissionAssembler;

    public UserSubmissionsResponseDto getUserSubmissions(Integer userId) throws UserIdNotExistsException {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new UserIdNotExistsException();

        }
        List<LeaderBoard> leaderBoards = leaderBoardRepository.getLeaderBoardsByUser_UserId(userId);
        List<UserCode> userCodes = userCodeRepository.getUserCodesByUserId(userId);
        return userSubmissionAssembler.toUserSubmissionsResponseDto(userCodes, leaderBoards);
    }

}
