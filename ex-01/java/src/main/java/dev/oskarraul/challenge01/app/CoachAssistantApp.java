package dev.oskarraul.challenge01.app;

import dev.oskarraul.challenge01.business.MaximumCalculator;
import dev.oskarraul.challenge01.business.PlayersDatabaseManager;
import dev.oskarraul.challenge01.domain.Player;
import dev.oskarraul.challenge01.domain.PlayerStats;
import dev.oskarraul.challenge01.menu.ConsoleDataReader;
import dev.oskarraul.challenge01.menu.MenuOption;
import dev.oskarraul.challenge01.menu.MenuOptionRunner;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * Main class with business logic
 */
@Getter
@AllArgsConstructor
public class CoachAssistantApp {
    private final ConsoleDataReader consoleDataReader;
    private final MenuOptionRunner optionRunner;
    private final PlayersDatabaseManager playersDb;
    private final MaximumCalculator maximumCalculator;

    public void mainMenuCycle() {
        optionRunner.menuCycle("Footbal Coach Assistant 1.0", getMenuOptions(), "Exit Program");
    }

    private MenuOption[] getMenuOptions() {
        return new MenuOption[]{
                new MenuOption("Player Review", new PlayerReviewAction()),
                new MenuOption("Compare two players", new PlayerCompareAction()),
                new MenuOption("Fastest player", new FastestPlayerAction()),
                new MenuOption("Top goal scorer", new TopGoalPlayerAction()),
                new MenuOption("Player with most assists", new MostAssistsPlayerAction()),
                new MenuOption("Player with the highest passing accuracy", new HighestPassingAccuracyPlayerAction()),
                new MenuOption("Player with the most defensive involvements", new MostAssistsPlayerAction()),
        };
    }

    private Player menuSelectPlayer(String message) {
        while (true) {
            int playerNumber = getConsoleDataReader().readIntegerZeroOrPositive(message);
            if (playerNumber == 0) {
                return null;
            }
            Player player = getPlayersDb().getPlayerByNumber(playerNumber);
            if (player != null) {
                return player;
            }
            System.out.println("Player with number " + playerNumber + " does not exist! Try Again");
        }
    }

    private Player menuSelectSecondPlayer(String message, Player firstPlayer) {
        while (true) {
            Player player = menuSelectPlayer(message);
            if (player == null || !firstPlayer.equals(player)) {
                return player;
            }
            System.out.println("You must select a different player than " + player.getNumber() + "! Try again.");
        }
    }

    class PlayerReviewAction implements Runnable {
        public void run() {
            System.out.println("**Player Preview**");
            Player player = menuSelectPlayer("Input Player Number (Or 0 to cancel)");
            if (player == null) {
                System.out.println("Operation cancelled.");
                System.out.println();
                return;
            }
            printPlayerInformation(player);
        }

        private void printPlayerInformation(Player player) {
            System.out.println("Player information");
            System.out.println("----");
            System.out.println("* Number: " + player.getNumber());
            System.out.println("* Name: " + player.getName());
            System.out.println("* Stats: ");
            PlayerStats stats = player.getStats();
            System.out.println("    - Goals: " + stats.getGoals());
            System.out.println("    - Speed: " + stats.getSpeed() + " point(s)");
            System.out.println("    - Assists: " + stats.getAssists() + " point(s)");
            System.out.println("    - PassingAccuracy: " + stats.getPassingAccuracy() + " point(s)");
            System.out.println("    - DeffensiveInvolvements: " + stats.getDefensiveInvolvements());
            System.out.println();
        }
    }

    class PlayerCompareAction implements Runnable {
        public void run() {
            System.out.println("**Players Comparison**");
            System.out.println("You must select two distinct players");

            Player player1 = menuSelectPlayer("Input First Player Number (Or 0 to cancel)");
            if (player1 == null) {
                System.out.println("Operation cancelled.");
                System.out.println();
                return;
            }
            System.out.println("Player " + player1.getName() + "(" + player1.getNumber() + ") selected");
            Player player2 = menuSelectSecondPlayer("Input Second Player Number (Or 0 to cancel)", player1);
            if (player2 == null) {
                System.out.println("Operation cancelled.");
                System.out.println();
                return;
            }
            System.out.println("Player " + player2.getName() + "(" + player2.getNumber() + ") selected");
            printPlayerComparison(player1, player2);
        }

        private void printPlayerComparison(Player player1, Player player2) {
            System.out.println("Players comparison");
            System.out.println("----");
            String name1 = player1.getNumber() + " " + player1.getName();
            String name2 = player2.getNumber() + " " + player2.getName();
            System.out.println("* Player 1: " + name1);
            System.out.println("* Player 2: " + name2);
            System.out.println("* Stats: ");
            PlayerStats stats1 = player1.getStats();
            PlayerStats stats2 = player2.getStats();
            System.out.println("    - Goals: " + name1 + ": " + stats1.getGoals() + " -- " + name2 + ": " + stats2.getGoals());
            System.out.println("    - Speed: " + name1 + ": " + stats1.getSpeed() + " point(s) -- " + name2 + ": " + stats2.getSpeed() + "  point(s)");
            System.out.println("    - Assists: " + name1 + ": " + stats1.getAssists() + " point(s) -- " + name2 + ": "
                    + stats2.getAssists() + "  point(s)");
            System.out.println("    - PassingAccuracy: " + name1 + ": " + stats1.getPassingAccuracy() + " point(s) --" +
                    " " + name2 + ": " + stats2.getPassingAccuracy() + "  point(s)");
            System.out.println("    - DeffensiveInvolvements: " + name1 + ": " + stats1.getDefensiveInvolvements() +
                    " point(s) -- " + name2 + ": " + stats2.getDefensiveInvolvements() + "  point(s)");
            System.out.println();
        }

    }

    class FastestPlayerAction implements Runnable {
        public void run() {
            List<Player> best = maximumCalculator.getFastest();
            if (best.size() == 1) {
                // only one fasted
                Player fastestPlayer = best.get(0);
                System.out.println("The fastest player is: " + fastestPlayer.getName() + "(" + fastestPlayer.getNumber() + ") with " + fastestPlayer.getStats().getSpeed() + " point(s)");
            } else {
                // It is a tie
                int value = best.get(0).getStats().getSpeed();
                System.out.println("There is a tie of " + best.size() + " fastest players with " + value + " point(s)");
                for (Player p : best) {
                    System.out.println("* " + p.getName() + "(" + p.getNumber() + ")");
                }
            }
        }
    }

    class TopGoalPlayerAction implements Runnable {
        public void run() {
            List<Player> best = maximumCalculator.playersWithMoreGoals();
            if (best.size() == 1) {
                // only one with most goals
                Player player = best.get(0);
                System.out.println("The player with most goals is: " + player.getName() + "(" + player.getNumber() +
                        ") with " + player.getStats().getGoals() + " goal(s)");
            } else {
                // It is a tie
                int value = best.get(0).getStats().getGoals();
                System.out.println("There is a tie of " + best.size() + " players with most goals with " + value + " " +
                        "goal(s)");
                for (Player p : best) {
                    System.out.println("* " + p.getName() + "(" + p.getNumber() + ")");
                }
            }
        }
    }

    class MostAssistsPlayerAction implements Runnable {
        public void run() {
            List<Player> best = maximumCalculator.playersWithMostAssists();
            if (best.size() == 1) {
                // only one with most assists
                Player fastestPlayer = best.get(0);
                System.out.println("The player with most assists is: " + fastestPlayer.getName() + "(" + fastestPlayer.getNumber() + ") with " + fastestPlayer.getStats().getAssists() + " point(s)");
            } else {
                // It is a tie
                int value = best.get(0).getStats().getAssists();
                System.out.println("There is a tie of " + best.size() + " players with most assists with " + value +
                        " point(s)");
                for (Player p : best) {
                    System.out.println("* " + p.getName() + "(" + p.getNumber() + ")");
                }
            }
        }
    }

    class HighestPassingAccuracyPlayerAction implements Runnable {
        public void run() {
            List<Player> best = maximumCalculator.playersWithHighestPassingAccuracy();
            if (best.size() == 1) {
                // only one with more accuracy
                Player fastestPlayer = best.get(0);
                System.out.println("The player most passing accuracy is: " + fastestPlayer.getName() + "(" + fastestPlayer.getNumber() + ") with " + fastestPlayer.getStats().getPassingAccuracy() + " point(s)");
            } else {
                // It is a tie
                int value = best.get(0).getStats().getPassingAccuracy();
                System.out.println("There is a tie of " + best.size() + " players with most passing accuracy with " + value + " point(s)");
                for (Player p : best) {
                    System.out.println("* " + p.getName() + "(" + p.getNumber() + ")");
                }
            }
        }
    }

    class MostDefensiveInvolvementsPlayerAction implements Runnable {
        public void run() {
            List<Player> best = maximumCalculator.playersWithMostDefensiveInvolvements();
            if (best.size() == 1) {
                // only one with more accuracy
                Player fastestPlayer = best.get(0);
                System.out.println("The player most defensive involvements is: " + fastestPlayer.getName() + "(" + fastestPlayer.getNumber() + ") with " + fastestPlayer.getStats().getDefensiveInvolvements() + " point(s)");
            } else {
                // It is a tie
                int value = best.get(0).getStats().getDefensiveInvolvements();
                System.out.println("There is a tie of " + best.size() + " players with most defensive involvements " +
                        "with " + value + " point(s)");
                for (Player p : best) {
                    System.out.println("* " + p.getName() + "(" + p.getNumber() + ")");
                }
            }
        }
    }
}
