package project.OOP2.f22621615.functionality;

import project.OOP2.f22621615.database.Database;
import project.OOP2.f22621615.database.Table;
import project.OOP2.f22621615.interfaces.Command;

public class ShowTablesCommand implements Command {
    private Database database;

    public ShowTablesCommand(Database database) {
        this.database = database;
    }

    @Override
    public void execute() {
        System.out.println("Tables loaded in the database:");
        System.out.println("+----------------------+");
        System.out.println("|      Table Name      |");
        System.out.println("+----------------------+");

        for (Table table : database.getTables()) {
            System.out.printf("| %-20s |%n", table.getName());
        }

        System.out.println("+----------------------+");
    }
}