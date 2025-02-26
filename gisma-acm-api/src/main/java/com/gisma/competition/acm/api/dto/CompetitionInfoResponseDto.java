package com.gisma.competition.acm.api.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CompetitionInfoResponseDto extends BaseCompetitionDto {

    private Integer competitionId;
    private Boolean isFinished;
}
