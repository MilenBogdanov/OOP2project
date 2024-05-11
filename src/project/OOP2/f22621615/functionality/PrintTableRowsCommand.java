package project.OOP2.f22621615.functionality;

import project.OOP2.f22621615.database.Database;
import project.OOP2.f22621615.database.Row;
import project.OOP2.f22621615.database.Table;
import project.OOP2.f22621615.interfaces.Command;

import java.util.Scanner;

public class PrintTableRowsCommand implements Command {
    private Database database;
    private String tableName;

    public PrintTableRowsCommand(Database database) {
        this.database = database;
    }

    @Override
    public void execute() {
        Table table = database.getTableByName(tableName);
        if (table != null) {
            System.out.println("Rows from table " + tableName + ":");
            for (Row row : table.getRows()) {
                StringBuilder rowString = new StringBuilder(" - ");
                rowString.append(row.getValue("id")).append(" | ");
                rowString.append(row.getValue("first_name")).append(" | ");
                rowString.append(row.getValue("last_name")).append(" | ");
                rowString.append(row.getValue("salary"));
                System.out.println(rowString.toString().trim());
            }
        } else {
            System.out.println("Table '" + tableName + "' not found.");
        }
    }
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
