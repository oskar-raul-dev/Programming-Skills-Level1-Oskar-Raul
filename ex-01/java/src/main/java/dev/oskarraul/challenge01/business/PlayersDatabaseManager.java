package dev.oskarraul.challenge01.business;

import dev.oskarraul.challenge01.comparators.AssistsComparator;
import dev.oskarraul.challenge01.comparators.DefensiveComparator;
import dev.oskarraul.challenge01.comparators.GoalComparator;
import dev.oskarraul.challenge01.comparators.PassingAccuracyComparator;
import dev.oskarraul.challenge01.comparators.SpeedComparator;
import dev.oskarraul.challenge01.domain.Player;
import lombok.Getter;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PlayersDatabaseManager {
    @Getter
    private final List<Player> allPlayers;
    private final Map<Integer, Player> playersByNumber;

    public PlayersDatabaseManager(List<Player> allPlayers) {
        this.allPlayers = allPlayers;
        this.playersByNumber = new LinkedHashMap<>();
        createPlayersMap();
    }

    public Player getPlayerByNumber(int number) {
        return playersByNumber.get(number);
    }

    private void createPlayersMap() {
        for (Player p : allPlayers) {
            playersByNumber.put(p.getNumber(), p);
        }
    }
}
