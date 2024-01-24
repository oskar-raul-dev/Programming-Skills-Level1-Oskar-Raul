package dev.oskarraul.challenge01.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Player {
    int number;
    private String name;
    @Builder.Default
    PlayerStats stats = new PlayerStats();
}
