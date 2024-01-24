package dev.oskarraul.challenge01.menu;

import java.util.Scanner;

/**
 * Convinience functions for reading values from console
 * <p>
 * TODO: The idea is to create a generic data reader suitable for all challenges implemented
 * in command line console
 */
public class ConsoleDataReader {

    private final Scanner scanner;

    public ConsoleDataReader(Scanner scanner) {
        this.scanner = scanner;
    }

    public int readInteger(String message) {
        // loop until a valid value is input
        while (true) {
            // print input message
            System.out.print(String.format("%s: ", message));
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Invalid value. You must input an integer value. Try again");
            }
        }
    }

    public int readIntegerInRange(String message, int minimum, int maximum) {
        // loop until a valid value is input
        while (true) {
            // print input message
            System.out.print(String.format("%s (%d - %d): ", message, minimum, maximum));
            try {
                int value = Integer.parseInt(scanner.nextLine().trim());
                // test range
                if (value < minimum || value > maximum) {
                    throw new IntegerOutOfRangeException();
                }
                return value;
            } catch (IntegerOutOfRangeException e) {
                System.out.println(String.format("Invalid value. You must input an integer in range from %d to %d. " +
                        "Try again", minimum, maximum));
            } catch (Exception e) {
                System.out.println("Invalid value. You must input an integer value. Try again");
            }
        }
    }

    public int readIntegerZeroOrPositive(String message) {
        // loop until a valid value is input
        while (true) {
            // print input message
            System.out.print(String.format("%s: ", message));
            try {
                int value = Integer.parseInt(scanner.nextLine().trim());
                // test range
                if (value < 0) {
                    throw new IntegerOutOfRangeException();
                }
                return value;
            } catch (IntegerOutOfRangeException e) {
                System.out.println("Invalid value. You must input an integer greater than 0. Try again");
            } catch (Exception e) {
                System.out.println("Invalid value. You must input an integer value. Try again");
            }
        }
    }

    public int readIntegerPositive(String message) {
        // loop until a valid value is input
        while (true) {
            // print input message
            System.out.print(String.format("%s: ", message));
            try {
                int value = Integer.parseInt(scanner.nextLine().trim());
                // test range
                if (value <= 0) {
                    throw new IntegerOutOfRangeException();
                }
                return value;
            } catch (IntegerOutOfRangeException e) {
                System.out.println("Invalid value. You must input an integer greater than 0. Try again");
            } catch (Exception e) {
                System.out.println("Invalid value. You must input an integer value. Try again");
            }
        }
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        ConsoleDataReader reader = new ConsoleDataReader(s);

        int n = reader.readIntegerZeroOrPositive("Select the option");
        System.out.println("Selected option: " + n);

    }
}
