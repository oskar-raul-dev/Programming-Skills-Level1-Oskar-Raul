package dev.oskarraul.challenge01.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerStats {
    @JsonProperty("goals")
    int goals;

    @JsonProperty("speed")
    int speed;

    @JsonProperty("assists")
    int assists;

    @JsonProperty("passing_accuracy")
    int passingAccuracy;

    @JsonProperty("defensive_involvements")
    int defensiveInvolvements;
}
