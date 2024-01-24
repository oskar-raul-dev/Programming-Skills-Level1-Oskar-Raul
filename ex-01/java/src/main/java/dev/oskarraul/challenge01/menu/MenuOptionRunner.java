package dev.oskarraul.challenge01.menu;

import lombok.Getter;

public class MenuOptionRunner {
    @Getter
    private final ConsoleDataReader consoleReader;

    public MenuOptionRunner(ConsoleDataReader consoleReader) {
        this.consoleReader = consoleReader;
    }

    public void menuSingleAction(String title, MenuOption[] options) {
        printTitle(title);
        printMenuOptions(options);
        // ask user to input an option from menu
        int option = consoleReader.readIntegerInRange("Select your option", 1, options.length);

        // execute the action to selected option
        options[option - 1].getAction().run();
    }

    public void menuCycle(String title, MenuOption[] options, String exitOptionMessage) {
        // infinite loop until exit action is selected
        do {
            printTitle(title);
            printMenuOptionsWithExitOption(options, exitOptionMessage);

            // ask user to input an option from menu
            int option = consoleReader.readIntegerInRange("Select your option", 1, options.length + 1);

            // exit if exit option is selected
            if (option == options.length + 1) {
                return;
            }

            // execute the action to selected option
            options[option - 1].getAction().run();
        } while(true);
    }

    private void printTitle(String title) {
        System.out.println("---");
        System.out.println(title);
        System.out.println("---");
        System.out.println();
    }

    private void printMenuOptions(MenuOption[] options) {
        int optionNumber = 1;
        for (MenuOption m : options) {
            System.out.println(String.format("%d - %s", optionNumber++, m.getName()));
        }
    }

    private void printMenuOptionsWithExitOption(MenuOption[] options, String exitOptionMessage) {
       printMenuOptions(options);
        System.out.println(String.format("%d - %s", options.length + 1, exitOptionMessage));
    }

}
