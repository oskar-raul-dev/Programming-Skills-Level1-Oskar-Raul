package dev.oskarraul.challenge01.business;

import dev.oskarraul.challenge01.domain.Player;
import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PlayersDatabaseManager {
    @Getter
    private final List<Player> allPlayers;
    private final Map<Integer, Player> playersByNumber;

    public PlayersDatabaseManager(List<Player> allPlayers) {
        this.allPlayers = allPlayers;
        this.playersByNumber = new LinkedHashMap<>();
        Optional.ofNullable(allPlayers)
                .orElse(List.of())
                .forEach(p -> playersByNumber.put(p.getNumber(), p));
    }

    public Player getPlayerByNumber(int number) {
        return playersByNumber.get(number);
    }
}
