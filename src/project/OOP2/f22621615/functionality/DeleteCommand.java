package project.OOP2.f22621615.functionality;

import project.OOP2.f22621615.database.Column;
import project.OOP2.f22621615.database.Database;
import project.OOP2.f22621615.database.Row;
import project.OOP2.f22621615.database.Table;
import project.OOP2.f22621615.interfaces.Command;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

public class DeleteCommand implements Command {
    private final Database database;
    private String tableName;
    private String searchColumnName;
    private Object searchColumnValue;

    public DeleteCommand(Database database, String tableName, String searchColumnName, Object searchColumnValue) {
        this.database = database;
        this.tableName = tableName;
        this.searchColumnName = searchColumnName;
        this.searchColumnValue = searchColumnValue;
    }

    @Override
    public void execute() {
        Table table = database.getTableByName(tableName);
        if (table != null) {
            Iterator<Row> iterator = table.getRows().iterator();
            boolean deleted = false;
            boolean noRowsFound = true;
            while (iterator.hasNext()) {
                Row row = iterator.next();
                Object columnValue = row.getValue(searchColumnName);
                if (columnValue != null && columnValue.equals(searchColumnValue)) {
                    iterator.remove();
                    deleted = true;
                    noRowsFound = false;
                }
            }
            if (deleted) {
                System.out.println("Rows deleted successfully from table '" + tableName + "'.");
                updateFile(table);
            } else if (noRowsFound) {
                System.out.println("No rows found matching the search criteria.");
            }
        } else {
            System.out.println("Table '" + tableName + "' not found.");
        }
    }

    private void updateFile(Table table) {
        String fileName = table.getAssociatedTextFile();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("TableName: " + table.getName());
            writer.newLine();
            writer.write("Columns:");
            writer.newLine();
            for (Column column : table.getColumns()) {
                writer.write("column: " + column.getName() + " " + column.getType());
                writer.newLine();
            }
            writer.write("Rows:");
            writer.newLine();
            for (Row row : table.getRows()) {
                writer.write(row.toString());
                writer.newLine();
            }
            System.out.println("File updated successfully: " + fileName);
        } catch (IOException e) {
            System.out.println("Error updating file: " + e.getMessage());
        }
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setSearchColumnName(String searchColumnName) {
        this.searchColumnName = searchColumnName;
    }

    public void setSearchColumnValue(Object searchColumnValue) {
        this.searchColumnValue = searchColumnValue;
    }
}