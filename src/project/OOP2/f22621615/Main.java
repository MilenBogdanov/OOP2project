package project.OOP2.f22621615;

import project.OOP2.f22621615.basic_filefunctions.CommandCenter;
import project.OOP2.f22621615.database.Database;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        StringBuilder fileContent = new StringBuilder();
        Database database = new Database(); // Create Database instance

        // Create CommandCenter instance with Database and fileContent
        CommandCenter commandCenter = new CommandCenter(fileContent, database);
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the File Manager!");
        System.out.println("Current working directory: " + System.getProperty("user.dir"));
        while (true) {
            System.out.print("Enter a command: ");
            String input = scanner.nextLine();

            String[] tokens = input.split(" ", 2);
            String commandName = tokens[0];
            String parameter = tokens.length > 1 ? tokens[1] : "";

            // Прочетете името на файла от конзолата
            if (commandName.equals("load")) {
                System.out.print("Enter the XML file name: ");
                parameter = scanner.nextLine();
            }

            commandCenter.executeCommand(commandName, parameter);

            if (commandName.equals("exit")) {
                break;
            }
        }
        scanner.close();
    }
}