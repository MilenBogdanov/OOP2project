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

    private boolean rowContainsValue(Row row, String columnName, String value) {
        Object columnValue = row.getValue(columnName);
        // Check if the column value is not null before comparing
        return columnValue != null && columnValue.equals(value);
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
