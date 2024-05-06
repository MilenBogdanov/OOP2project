package project.OOP2.f22621615.functionality;

import project.OOP2.f22621615.database.Column;
import project.OOP2.f22621615.database.Database;
import project.OOP2.f22621615.database.Row;
import project.OOP2.f22621615.database.Table;
import project.OOP2.f22621615.interfaces.Command;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UpdateCommand implements Command {
    private Database database;
    private String tableName;
    private String searchColumnName;
    private Object searchColumnValue;
    private String targetColumnName;
    private Object targetColumnValue;
    private String fileName; // Filename instance variable

    public UpdateCommand(Database database, String tableName, String searchColumnName, Object searchColumnValue, String targetColumnName, Object targetColumnValue) {
        this.database = database;
        this.tableName = tableName;
        this.searchColumnName = searchColumnName;
        this.searchColumnValue = searchColumnValue;
        this.targetColumnName = targetColumnName;
        this.targetColumnValue = targetColumnValue;
    }

    // Method to set the filename
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void execute() {
        Table table = database.getTableByName(tableName);
        if (table != null) {
            boolean updated = false;
            // Get the rows of the table
            List<Row> updatedRows = new ArrayList<>();
            for (Row row : table.getRows()) {
                // Check if the search column value matches
                Object value = row.getValue(searchColumnName);
                if (value != null && value.equals(searchColumnValue)) {
                    // Update the target column value
                    row.setValue(targetColumnName, targetColumnValue); // Update without converting to string
                    updatedRows.add(row);
                    updated = true;
                }
            }
            if (updated) {
                // Save the updated rows back to the table
                for (Row updatedRow : updatedRows) {
                    table.updateRow(updatedRow);
                }
                // Save the updated table to the text file
                saveTableToFile(table, fileName); // Use the filename instance variable
                System.out.println("Rows updated successfully and saved to file: " + fileName);
            } else {
                System.out.println("No rows found matching the search criteria.");
            }
        } else {
            System.out.println("Table '" + tableName + "' not found.");
        }
    }

    private void saveTableToFile(Table table, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            // Write table schema
            writer.write("TableName: " + table.getName());
            writer.newLine();
            writer.write("Columns:");
            writer.newLine();
            for (Column column : table.getColumns()) {
                writer.write("column: " + column);
                writer.newLine();
            }
            writer.write("Rows:");
            writer.newLine();
            // Write table rows
            for (Row row : table.getRows()) {
                writer.write(row.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving table to file: " + e.getMessage());
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

    public void setTargetColumnName(String targetColumnName) {
        this.targetColumnName = targetColumnName;
    }

    public void setTargetColumnValue(Object targetColumnValue) {
        this.targetColumnValue = targetColumnValue;
    }
}