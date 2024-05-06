package project.OOP2.f22621615.functionality;

import project.OOP2.f22621615.database.Column;
import project.OOP2.f22621615.database.Database;
import project.OOP2.f22621615.database.Row;
import project.OOP2.f22621615.database.Table;
import project.OOP2.f22621615.enums.DataType;
import project.OOP2.f22621615.interfaces.Command;

import java.io.FileWriter;
import java.io.IOException;

public class AddColumnCommand implements Command {
    private Database database;
    private String tableName;
    private String columnName;
    private DataType columnType;
    private String fileName;

    public AddColumnCommand(Database database, String tableName, String columnName, DataType columnType, String fileName) {
        this.database = database;
        this.tableName = tableName;
        this.columnName = columnName;
        this.columnType = columnType;
        this.fileName = fileName;
    }

    @Override
    public void execute() {
        Table table = database.getTableByName(tableName);
        if (table != null) {
            // Check if the column already exists
            boolean columnExists = table.getColumns().stream().anyMatch(c -> c.getName().equals(columnName));
            if (columnExists) {
                System.out.println("Column '" + columnName + "' already exists in table '" + tableName + "'.");
            } else {
                // Add the new column
                Column newColumn = new Column(columnName, columnType);
                table.addColumn(newColumn);

                // Update existing rows with default values for the new column
                for (Row row : table.getRows()) {
                    row.addValue(columnName, null);
                }

                // Update the text file
                updateTextFile(table);

                System.out.println("Column '" + columnName + "' added successfully to table '" + tableName + "'.");
            }
        } else {
            System.out.println("Table '" + tableName + "' not found.");
        }
    }

    // Method to get the default value based on the column type
    private Object getDefaultValue(DataType dataType) {
        switch (dataType) {
            case INTEGER:
                return 0; // Default value for INTEGER type
            case STRING:
                return ""; // Default value for STRING type
            case FLOAT:
                return 0.0; // Default value for FLOAT type
            default:
                return null;
        }
    }

    // Method to update the text file with the new table structure
    private void updateTextFile(Table table) {
        try (FileWriter writer = new FileWriter(fileName)) {
            // Write the table structure to the text file
            writer.write("TableName: " + table.getName() + "\n");
            writer.write("Columns:\n");
            for (Column column : table.getColumns()) {
                writer.write("column: " + column.getName() + " " + column.getType() + "\n");
            }
            writer.write("Rows:\n");
            for (Row row : table.getRows()) {
                StringBuilder rowString = new StringBuilder();
                for (Column column : table.getColumns()) {
                    String value = String.valueOf(row.getValue(column.getName()));
                    rowString.append(value).append(" ");
                }
                writer.write(rowString.toString().trim() + "\n");
            }
            System.out.println("Table structure updated in the text file.");
        } catch (IOException e) {
            System.out.println("Error updating text file: " + e.getMessage());
        }
    }

    // Setter methods for table name, column name, and column type
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public void setColumnType(DataType columnType) {
        this.columnType = columnType;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}