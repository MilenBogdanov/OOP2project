package project.OOP2.f22621615.functionality;

import project.OOP2.f22621615.database.Column;
import project.OOP2.f22621615.database.Database;
import project.OOP2.f22621615.database.Row;
import project.OOP2.f22621615.database.Table;
import project.OOP2.f22621615.enums.DataType;
import project.OOP2.f22621615.interfaces.Command;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class LoadTableFromTextFileCommand implements Command {
    private final Database database;
    private String fileName;

    public LoadTableFromTextFileCommand(Database database, String fileName) {
        this.database = database;
        this.fileName = fileName;
    }

    @Override
    public void execute() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            String tableName = null;
            boolean inTable = false;
            Table table = null;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.startsWith("TableName:")) {
                    // Extract table name
                    tableName = line.replace("TableName:", "").trim();
                    table = new Table(tableName);
                    inTable = true;
                    continue;
                }

                if (inTable && line.startsWith("Columns:")) {
                    // Skip the "Columns:" line
                    continue;
                }

                if (inTable && line.startsWith("Rows:")) {
                    // Start reading rows
                    while ((line = reader.readLine()) != null && !line.startsWith("Columns:")) {
                        // Parse row values
                        String[] values = line.split("\\s+");
                        if (table != null && !table.getColumns().isEmpty()) {
                            Row row = new Row();
                            for (int i = 0; i < values.length && i < table.getColumns().size(); i++) {
                                row.addValue(table.getColumns().get(i).getName(), values[i]);
                            }
                            table.addRow(row);
                        }
                    }
                    break;
                }

                if (inTable) {
                    // Parse column names and types
                    String[] columnData = line.split(":")[1].trim().split("\\s+");
                    for (int i = 0; i < columnData.length; i += 2) {
                        String columnName = columnData[i];
                        DataType dataType = DataType.valueOf(columnData[i + 1]);
                        table.addColumn(new Column(columnName, dataType));
                    }
                }
            }

            // Add the table to the database
            if (table != null) {
                database.addTable(table);
                System.out.println("Table '" + tableName + "' loaded successfully from text file.");
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
/*package project.OOP2.f22621615.functionality;

import project.OOP2.f22621615.database.Column;
import project.OOP2.f22621615.database.Database;
import project.OOP2.f22621615.database.Row;
import project.OOP2.f22621615.database.Table;
import project.OOP2.f22621615.enums.DataType;
import project.OOP2.f22621615.interfaces.Command;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LoadTableFromTextFileCommand implements Command {
    private final Database database;
    private String fileName;

    public LoadTableFromTextFileCommand(Database database, String fileName) {
        this.database = database;
        this.fileName = fileName;
    }
    @Override
    public void execute() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            String tableName = null;
            boolean inTable = false;
            Table table = null;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.startsWith("TableName:")) {
                    // Extract table name
                    tableName = line.replace("TableName:", "").trim();
                    table = new Table(tableName);
                    inTable = true;
                    continue;
                }

                if (inTable && line.startsWith("Columns:")) {
                    // Skip the "Columns:" line
                    continue;
                }

                if (inTable && line.startsWith("Rows:")) {
                    // Start parsing rows
                    break;
                }

                if (inTable) {
                    // Parse column data
                    String[] columnData = line.split(":");
                    String columnName = columnData[0].trim(); // Extract column name
                    String columnType = columnData[1].trim().split("\\s+")[1]; // Extract data type
                    DataType dataType = DataType.STRING; // Default data type is string
                    if (columnType.equals("INTEGER")) {
                        dataType = DataType.INTEGER;
                    } else if (columnType.equals("FLOAT")) {
                        dataType = DataType.FLOAT;
                    }
                    table.addColumn(new Column(columnName, dataType));
                }
            }

            while ((line = reader.readLine()) != null && !line.startsWith("TableName:")) {
                // Parse rows
                String[] values = line.split("\\s+-\\s+");
                Row row = new Row();
                for (int i = 0; i < values.length && i < table.getColumns().size(); i++) {
                    row.addValue(table.getColumns().get(i).getName(), values[i]);
                }
                table.addRow(row);
            }

            // Add the table to the database
            if (table != null) {
                database.addTable(table);
                System.out.println("Table '" + tableName + "' loaded successfully from text file.");
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}*/