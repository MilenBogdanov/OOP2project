package project.OOP2.f22621615.functionality;

import project.OOP2.f22621615.database.Column;
import project.OOP2.f22621615.database.Database;
import project.OOP2.f22621615.database.Table;
import project.OOP2.f22621615.interfaces.Command;
import project.OOP2.f22621615.enums.DataType;

import java.util.ArrayList;
import java.util.List;

public class DescribeTableCommand implements Command {
    private final Database database;
    private final String tableName;

    public DescribeTableCommand(Database database, String tableName) {
        this.database = database;
        this.tableName = tableName;
    }

    @Override
    public void execute() {
        if (!database.isEmpty()) {
            describe(tableName);
        } else {
            System.out.println("Database is empty. Load a table first.");
        }
    }

    public void describe(String tableName) {
        Table table = database.getTableByName(tableName);
        if (table != null) {
            System.out.println("Table: " + tableName);
            System.out.println("+----------------------+------------+");
            System.out.println("| Column Name          | Data Type  |");
            System.out.println("+----------------------+------------+");
            List<Column> columns = table.getColumns();
            for (Column column : columns) {
                String columnName = column.getName();
                DataType dataType = column.getType();
                System.out.printf("| %-20s | %-10s |%n", columnName, dataType);
            }
            System.out.println("+----------------------+------------+");
        } else {
         System.out.println("Table '" + tableName + "' not found.");
        }
    }
}