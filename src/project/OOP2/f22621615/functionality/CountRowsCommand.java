package project.OOP2.f22621615.functionality;

import project.OOP2.f22621615.database.Database;
import project.OOP2.f22621615.database.Row;
import project.OOP2.f22621615.database.Table;
import project.OOP2.f22621615.interfaces.Command;

public class CountRowsCommand implements Command {
    private final Database database;
    private String tableName;
    private String searchColumnName;
    private String searchValue;

    public CountRowsCommand(Database database, String tableName, String searchColumnName, String searchValue) {
        this.database = database;
        this.tableName = tableName;
        this.searchColumnName = searchColumnName;
        this.searchValue = searchValue;
    }

    @Override
    public void execute() {
        Table table = database.getTableByName(tableName);
        if (table != null) {
            int rowCount = countRows(table);
            System.out.println("Number of rows in table '" + tableName + "' where column '" + searchColumnName + "' contains value '" + searchValue + "': " + rowCount);
        } else {
            System.out.println("Table '" + tableName + "' not found.");
        }
    }

    private int countRows(Table table) {
        int count = 0;
        for (Row row : table.getRows()) {
            if (rowContainsValue(row, searchColumnName, searchValue)) {
                count++;
            }
        }
        return count;
    }

    private boolean rowContainsValue(Row row, String columnName, String value) {
        Object columnValue = row.getValue(columnName);
        return columnValue != null && columnValue.toString().contains(value);
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setSearchColumnName(String searchColumnName) {
        this.searchColumnName = searchColumnName;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }
}