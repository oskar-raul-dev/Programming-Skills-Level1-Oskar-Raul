package dev.oskarraul.challenge01.business;

import dev.oskarraul.challenge01.domain.Player;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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
        Player max = findMaximumPlayerByComparator(speedComparator);
        return findPlayersWithBestValue(
                max.getStats().getSpeed(),
                player -> player.getStats().getSpeed());
    }

    public List<Player> playersWithMoreGoals() {
        Player max = findMaximumPlayerByComparator(goalsComparator);
        return findPlayersWithBestValue(
                max.getStats().getGoals(),
                player -> player.getStats().getGoals());
    }

    public List<Player> playersWithMostAssists() {
        Player max = findMaximumPlayerByComparator(assistsComparator);
        return findPlayersWithBestValue(
                max.getStats().getAssists(),
                player -> player.getStats().getAssists());
    }

    public List<Player> playersWithHighestPassingAccuracy() {
        Player max = findMaximumPlayerByComparator(passingComparator);
        return findPlayersWithBestValue(
                max.getStats().getPassingAccuracy(),
                player -> player.getStats().getPassingAccuracy());
    }

    public List<Player> playersWithMostDefensiveInvolvements() {
        Player max = findMaximumPlayerByComparator(defensiveComparator);
        return findPlayersWithBestValue(
                max.getStats().getDefensiveInvolvements(),
                player -> player.getStats().getDefensiveInvolvements());
    }

    private Player findMaximumPlayerByComparator(Comparator<Player> comparator) {
        final List<Player> players = dbManager.getAllPlayers();
        if (players == null || players.isEmpty()) {
            return null;
        }

        // TODO: Convert to full functional
        Player  playerWithMax = players.get(0);

        // calculate maximum value compared with
        //given comparator
        for (int i = 1; i < players.size(); i++) {
            Player p = players.get(i);
            if (comparator.compare(playerWithMax, p) < 0) {
                playerWithMax = p;
            }
        }
        return playerWithMax;
    }

    private List<Player> findPlayersWithBestValue(int bestValue, Function<Player, Integer> statGetter) {
        // iterate all players catalog in order to test if
        // there are more then a player with maximum value
        // for given stat getter
        List<Player> players = dbManager.getAllPlayers();
        List<Player> best = new ArrayList<>();
        for (Player p : players) {
            if (statGetter.apply(p) == bestValue) {
                best.add(p);
            }
        }
        return best;
    }
}
