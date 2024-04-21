package project.OOP2.f22621615.functionality;

import project.OOP2.f22621615.database.Database;
import project.OOP2.f22621615.database.Row;
import project.OOP2.f22621615.database.Table;
import project.OOP2.f22621615.interfaces.Command;

import java.util.List;
import java.util.Scanner;

public class PrintTableCommand implements Command {
    private Database database;
    private String tableName;

    public PrintTableCommand(Database database, String tableName) {
        this.database = database;
        this.tableName = tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public void execute() {
        Table table = database.getTableByName(tableName);
        if (table == null) {
            System.out.println("Error: Table '" + tableName + "' not found.");
            return;
        }

        List<Row> rows = table.getRows();
        if (rows.isEmpty()) {
            System.out.println("Table '" + tableName + "' is empty.");
            return;
        }

        int pageSize = 10; // Number of rows per page
        int totalPages = (int) Math.ceil((double) rows.size() / pageSize);
        int currentPage = 1;

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Page " + currentPage + " of " + totalPages);
            System.out.println("------------------------");
            int startIndex = (currentPage - 1) * pageSize;
            int endIndex = Math.min(startIndex + pageSize, rows.size());

            for (int i = startIndex; i < endIndex; i++) {
                Row row = rows.get(i);
                System.out.println(row); // Outputting the entire row
            }

            System.out.println("------------------------");
            System.out.println("Commands: next | prev | exit");
            System.out.print("Enter command: ");
            String command = scanner.nextLine();

            switch (command.toLowerCase()) {
                case "next":
                    if (currentPage < totalPages) {
                        currentPage++;
                    } else {
                        System.out.println("Already on the last page.");
                    }
                    break;
                case "prev":
                    if (currentPage > 1) {
                        currentPage--;
                    } else {
                        System.out.println("Already on the first page.");
                    }
                    break;
                case "exit":
                    return;
                default:
                    System.out.println("Invalid command. Please enter 'next', 'prev', or 'exit'.");
                    break;
            }
        }
    }
}
