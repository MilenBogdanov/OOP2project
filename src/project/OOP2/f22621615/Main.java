package project.OOP2.f22621615;

/*import project.OOP2.f22621615.basic_filefunctions.CommandCenter;
import project.OOP2.f22621615.database.Database;
import project.OOP2.f22621615.interfaces.CommandEnum;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.setProperty("user.dir", "D:\\OOP2\\project\\OOP2_project");
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
                System.out.print("Enter the txt file name: ");
                parameter = scanner.nextLine();
            }

            if (commandName.equals("delete")) {
                System.out.print("Enter the table name, search column name, and search value separated by space: ");
                parameter = scanner.nextLine();
            }

            if (commandName.equals("open")) {
                System.out.println("Opening file: " + parameter);
            }

            if (commandName.equals("describe")) {
                System.out.print("Enter the table name: ");
                parameter = scanner.nextLine();
            }

            if (commandName.equals("addcolumn")) {
                System.out.print("Enter table name, column name, and column type separated by space: ");
                String[] params = scanner.nextLine().split("\\s+");
                if (params.length == 3) {
                    commandCenter.executeCommand(commandName, params[0] + " " + params[1] + " " + params[2]);
                } else {
                    System.out.println("Invalid input. Please enter table name, column name, and column type separated by space.");
                    continue;
                }
                continue;
            }

            if (commandName.equals("update")) {
                System.out.print("Enter the table name, search column name, search value, target column name, and target value separated by space: ");
                String[] params = scanner.nextLine().split("\\s+");
                if (params.length == 5) {
                    commandCenter.executeCommand(commandName, params[0] + " " + params[1] + " " + params[2] + " " + params[3] + " " + params[4]);
                } else {
                    System.out.println("Invalid input. Please enter table name, search column name, search value, target column name, and target value separated by space.");
                    continue;
                }
                continue;
            }

            if (commandName.equals("print")) {
                System.out.print("Enter the table name: ");
                parameter = scanner.nextLine();
            }

            if (commandName.equals("export")) {
                System.out.print("Enter the table name and text file name separated by space: ");
                String[] params = scanner.nextLine().split(" ", 2);
                if (params.length == 2) {
                    parameter = params[0] + " " + params[1];
                } else {
                    System.out.println("Invalid input. Please enter the table name and text file name separated by space.");
                    continue;
                }
            }

            commandCenter.executeCommand(commandName, parameter);

            if (commandName.equals("exit")) {
                break;
            }
        }
        scanner.close();
    }
}*/
/*import project.OOP2.f22621615.basic_filefunctions.CommandCenter;
import project.OOP2.f22621615.database.Database;
import project.OOP2.f22621615.enums.CommandEnum;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.setProperty("user.dir", "D:\\OOP2\\project\\OOP2_project");
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
            CommandEnum commandName;
            try {
                commandName = CommandEnum.valueOf(tokens[0].toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid command. Type 'help' to see the list of commands.");
                continue;
            }
            String parameter = tokens.length > 1 ? tokens[1] : "";

            switch (commandName) {
                case LOAD:
                    System.out.print("Enter the txt file name: ");
                    parameter = scanner.nextLine();
                    break;//
                case DELETE:
                    System.out.print("Enter the table name, search column name, and search value separated by space: ");
                    parameter = scanner.nextLine();
                    break;
                case OPEN:
                    System.out.println("Opening file: " + parameter);
                    break;
                case DESCRIBE:
                    System.out.print("Enter the table name: ");
                    parameter = scanner.nextLine();
                    break;//
                case ADDCOLUMN:
                    System.out.print("Enter table name, column name, and column type separated by space: ");
                    String[] params = scanner.nextLine().split("\\s+");
                    if (params.length == 3) {
                        parameter = params[0] + " " + params[1] + " " + params[2];
                    } else {
                        System.out.println("Invalid input. Please enter table name, column name, and column type separated by space.");
                        continue;
                    }
                    break;//
                case UPDATE:
                    System.out.print("Enter the table name, search column name, search value, target column name, and target value separated by space: ");
                    String[] updateParams = scanner.nextLine().split("\\s+");
                    if (updateParams.length == 5) {
                        parameter = updateParams[0] + " " + updateParams[1] + " " + updateParams[2] + " " + updateParams[3] + " " + updateParams[4];
                    } else {
                        System.out.println("Invalid input. Please enter table name, search column name, search value, target column name, and target value separated by space.");
                        continue;
                    }
                    break;//
                case PRINT:
                    System.out.print("Enter the table name: ");
                    parameter = scanner.nextLine();
                    break;//
                case EXPORT:
                    System.out.print("Enter the table name and text file name separated by space: ");
                    String[] exportParams = scanner.nextLine().split(" ", 2);
                    if (exportParams.length == 2) {
                        parameter = exportParams[0] + " " + exportParams[1];
                    } else {
                        System.out.println("Invalid input. Please enter the table name and text file name separated by space.");
                        continue;
                    }
                    break;
                default:
                    break;//
            }

            commandCenter.executeCommand(commandName, parameter);

            if (commandName == CommandEnum.EXIT) {
                break;
            }
        }
        scanner.close();
    }
}*/import project.OOP2.f22621615.basic_filefunctions.CommandCenter;
import project.OOP2.f22621615.database.Database;
import project.OOP2.f22621615.enums.CommandEnum;

import java.util.Scanner;
/**
 * The main class to run the program.
 */
public class Main {
    /**
     * The main method to start the program.
     *
     * @param args The command-line arguments.
     */
    public static void main(String[] args) {
        System.setProperty("user.dir", "D:\\OOP2\\project\\OOP2_project");
        StringBuilder fileContent = new StringBuilder();
        Database database = new Database();

        CommandCenter commandCenter = new CommandCenter(fileContent, database);
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to OOP-1 project!");
        System.out.println("Author - Milen Bogdanov, fn22621615");
        System.out.println();
        System.out.println("Current working directory: " + System.getProperty("user.dir"));
        while (true) {
            System.out.print("Enter a command: ");
            String input = scanner.nextLine();

            String[] tokens = input.split(" ", 2);
            CommandEnum commandName;
            try {
                commandName = CommandEnum.valueOf(tokens[0].toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid command. Type 'help' to see the list of commands.");
                continue;
            }
            String parameter = tokens.length > 1 ? tokens[1] : "";

            commandCenter.executeCommand(commandName, parameter);

            if (commandName == CommandEnum.EXIT) {
                break;
            }
        }
        scanner.close();
    }
}