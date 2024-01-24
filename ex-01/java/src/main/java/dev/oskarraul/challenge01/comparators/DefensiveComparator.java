package dev.oskarraul.challenge01.comparators;

import dev.oskarraul.challenge01.domain.Player;

import java.util.Comparator;

public class DefensiveComparator implements Comparator<Player> {
    @Override
    public int compare(Player o1, Player o2) {
        // corner cases
        if (o1 == null && o2 == null) {
            return 0;
        }
        if (o1 ==null) {
            return -1;
        }
        if (o2 == null) {
            return 1;
        }
        return Integer.compare(o1.getStats().getDefensiveInvolvements(), o2.getStats().getDefensiveInvolvements());
    }
}
