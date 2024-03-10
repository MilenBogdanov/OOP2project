package project.OOP2.f22621615;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CmdCenter cmdCenter = new CmdCenter();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the File Commander!");

        while (true) {
            System.out.print("Enter a command: ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Exiting the program...");
                break;
            }

            cmdCenter.executeCommand(input);

            System.out.println();
        }

        scanner.close();
    }
}