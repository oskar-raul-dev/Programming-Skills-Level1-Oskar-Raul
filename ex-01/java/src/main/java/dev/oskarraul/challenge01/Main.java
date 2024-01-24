package dev.oskarraul.challenge01;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.oskarraul.challenge01.app.CoachAssistantApp;
import dev.oskarraul.challenge01.comparators.AssistsComparator;
import dev.oskarraul.challenge01.comparators.DefensiveComparator;
import dev.oskarraul.challenge01.comparators.GoalComparator;
import dev.oskarraul.challenge01.comparators.PassingAccuracyComparator;
import dev.oskarraul.challenge01.comparators.SpeedComparator;
import dev.oskarraul.challenge01.database.JsonDatabaseReader;
import dev.oskarraul.challenge01.business.MaximumCalculator;
import dev.oskarraul.challenge01.business.PlayersDatabaseManager;
import dev.oskarraul.challenge01.domain.Player;
import dev.oskarraul.challenge01.menu.ConsoleDataReader;
import dev.oskarraul.challenge01.menu.MenuOptionRunner;
import lombok.Getter;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

/**
 * Main program
 */
@Getter
public class Main {
    // Main program
    public static void main(String[] args) {
        // try to load initial catalog
        // from JSON file
        System.out.println("Loading player Database from JSON...");
        final List<Player> playersList = loadDatabaseFromJson();
        if (playersList == null) {
            System.exit(1);
            return;
        }
        System.out.println("JSON database Loaded.");
        final Scanner scanner = new Scanner(System.in);
        // start main menu cycle
        createCoachAssistantApp(scanner, playersList).mainMenuCycle();
        // after finish close all resources
        scanner.close();
    }

    private static List<Player> loadDatabaseFromJson() {
        try {
            JsonDatabaseReader dbReader = new JsonDatabaseReader(new ObjectMapper());
            InputStream databaseStream = Main
                    .class
                    .getClassLoader()
                    .getResourceAsStream("database/players.json");

            return dbReader.loadInitialCatalog(new InputStreamReader(databaseStream));
        } catch (Exception e) {
            System.out.println("Can't load database of players. Program will close...");
            e.printStackTrace();
            return null;
        }
    }

    private static CoachAssistantApp createCoachAssistantApp(Scanner scanner, List<Player> playerDatabase) {
        // init base components
        // via dependency injection on constructor
        ConsoleDataReader consoleReader = new ConsoleDataReader(scanner);
        PlayersDatabaseManager databaseManager = new PlayersDatabaseManager(playerDatabase);
        MaximumCalculator maximumCalculator = new MaximumCalculator(
                databaseManager,
                new AssistsComparator(),
                new DefensiveComparator(),
                new GoalComparator(),
                new PassingAccuracyComparator(),
                new SpeedComparator());

        // init main application class
        CoachAssistantApp app = new CoachAssistantApp(
                consoleReader,
                new MenuOptionRunner(consoleReader),
                databaseManager,
                maximumCalculator);
        return app;
    }
}
