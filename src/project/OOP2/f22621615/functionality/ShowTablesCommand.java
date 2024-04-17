package project.OOP2.f22621615.functionality;

import project.OOP2.f22621615.database.Column;
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

        for (Table table : database.getTables()) {
            System.out.println("Table: " + table.getName());
            System.out.println("Columns:");

            for (Column column : table.getColumns()) {
                System.out.println("  - " + column.getName() + ": " + column.getType());
            }

            System.out.println(); // Add a blank line between tables
        }
    }
}