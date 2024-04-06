package project.OOP2.f22621615;

import project.OOP2.f22621615.basic_filefunctions.CommandCenter;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        StringBuilder fileContent = new StringBuilder();
        CommandCenter commandCenter = new CommandCenter(fileContent);
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the File Manager!");

        while (true) {
            System.out.print("Enter a command: ");
            String input = scanner.nextLine();

            String[] tokens = input.split(" ", 2);
            String commandName = tokens[0];
            String parameter = tokens.length > 1 ? tokens[1] : "";

            commandCenter.executeCommand(commandName, parameter);

            if (commandName.equals("exit")) {
                break;
            }
        }

        scanner.close();
    }
}