package project.OOP2.f22621615.functionality;

import project.OOP2.f22621615.database.Database;
import project.OOP2.f22621615.database.Row;
import project.OOP2.f22621615.database.Table;
import project.OOP2.f22621615.interfaces.Command;

import java.util.List;

public class SelectCommand implements Command {
    private final Database database;
    private String columnName; // Modify to store the column name instead of index
    private String value;
    private String tableName;

    public SelectCommand(Database database, String columnName, String value, String tableName) {
        this.database = database;
        this.columnName = columnName; // Store the column name
        this.value = value;
        this.tableName = tableName;
    }

    @Override
    public void execute() {
        Table table = database.getTableByName(tableName);
        if (table != null) {
            List<Row> rows = table.getRows();
            System.out.println("Rows from table " + tableName + " where column " + columnName + " has value '" + value + "':");
            for (Row row : rows) {
                if (rowContainsValue(row, columnName, value)) {
                    System.out.println(row);
                }
            }
        } else {
            System.out.println("Table '" + tableName + "' not found.");
        }
    }
    /*@Override
    public void execute() {
        Table table = database.getTableByName(tableName);
        if (table != null) {
            List<Row> rows = table.getRows();
            System.out.println("Rows from table " + tableName + " where column " + columnName + " has value '" + value + "':");
            for (Row row : rows) {
                if (rowContainsValue(row, columnName, value)) {
                    System.out.println("| first_name: " + row.getValue("first_name") + " | last_name: " + row.getValue("last_name") + " | id: " + row.getValue("id") + " | salary: " + row.getValue("salary") + " |");
                }
            }
        } else {
            System.out.println("Table '" + tableName + "' not found.");
        }
    }*/

    private boolean rowContainsValue(Row row, String columnName, String value) {
        return row.getValue(columnName).equals(value); // Use column name instead of index
    }
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}