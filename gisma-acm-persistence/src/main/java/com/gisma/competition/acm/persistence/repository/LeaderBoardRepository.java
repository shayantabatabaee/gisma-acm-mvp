package com.gisma.competition.acm.persistence.repository;

import com.gisma.competition.acm.persistence.entity.LeaderBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LeaderBoardRepository extends JpaRepository<LeaderBoard, Integer> {

    Optional<LeaderBoard> findByCompetition_CompetitionIdAndUser_UserId(Integer competitionCompetitionId, Integer userUserId);

    List<LeaderBoard> getLeaderBoardsByCompetition_CompetitionIdAndSuccessTrueOrderByCpuTimeAsc(Integer competitionCompetitionId);

    List<LeaderBoard> getLeaderBoardsByUser_UserId(Integer userUserId);
}
