package dev.oskarraul.challenge01.business;

import dev.oskarraul.challenge01.domain.Player;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@AllArgsConstructor
public class MaximumCalculator {
    private final PlayersDatabaseManager dbManager;
    private final Comparator<Player> assistsComparator;
    private final Comparator<Player> defensiveComparator;
    private final Comparator<Player> goalsComparator;
    private final Comparator<Player> passingComparator;
    private final Comparator<Player> speedComparator;

    public List<Player> getFastest() {
        return findPlayersWithBestValue(
                findMaximumPlayerByComparator(speedComparator)
                        .getStats()
                        .getSpeed(),
                player -> player.getStats().getSpeed());
    }

    public List<Player> playersWithMoreGoals() {
        return findPlayersWithBestValue(
                findMaximumPlayerByComparator(goalsComparator)
                        .getStats()
                        .getGoals(),
                player -> player.getStats().getGoals());
    }

    public List<Player> playersWithMostAssists() {
        return findPlayersWithBestValue(
                findMaximumPlayerByComparator(assistsComparator)
                        .getStats()
                        .getAssists(),
                player -> player.getStats().getAssists());
    }

    public List<Player> playersWithHighestPassingAccuracy() {
        return findPlayersWithBestValue(
                findMaximumPlayerByComparator(passingComparator)
                        .getStats()
                        .getPassingAccuracy(),
                player -> player.getStats().getPassingAccuracy());
    }

    public List<Player> playersWithMostDefensiveInvolvements() {
        return findPlayersWithBestValue(
                findMaximumPlayerByComparator(defensiveComparator)
                        .getStats()
                        .getDefensiveInvolvements(),
                player -> player.getStats().getDefensiveInvolvements());
    }

    private Player findMaximumPlayerByComparator(Comparator<Player> comparator) {
        return Optional
                .ofNullable(dbManager.getAllPlayers())
                .orElse(List.of())
                .stream()
                .max(comparator)
                .orElse(null);
    }

    private List<Player> findPlayersWithBestValue(int bestValue, Function<Player, Integer> statGetter) {
        return  Optional
                .ofNullable(dbManager.getAllPlayers())
                .orElse(List.of())
                .stream()
                .filter(p->statGetter.apply(p) == bestValue)
                .toList();
    }
}
