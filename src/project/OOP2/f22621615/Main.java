package project.OOP2.f22621615;

import project.OOP2.f22621615.basic_filefunctions.CommandCenter;
import project.OOP2.f22621615.database.Database;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        StringBuilder fileContent = new StringBuilder();
        Database database = new Database();

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

            if (commandName.equals("load")) {
                System.out.print("Enter the XML file name: ");
                parameter = scanner.nextLine();
            }

            if (commandName.equals("open")) {
                System.out.println("Opening file: " + parameter);
            }

            // Add handling for describe command
            if (commandName.equals("describe")) {
                System.out.print("Enter the table name: ");
                parameter = scanner.nextLine();
            }

            // Add handling for print command
            if (commandName.equals("print")) {
                System.out.print("Enter the table name: ");
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