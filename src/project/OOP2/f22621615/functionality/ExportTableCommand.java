package project.OOP2.f22621615.functionality;

import project.OOP2.f22621615.database.Column;
import project.OOP2.f22621615.database.Database;
import project.OOP2.f22621615.database.Row;
import project.OOP2.f22621615.database.Table;
import project.OOP2.f22621615.interfaces.Command;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ExportTableCommand implements Command {
    private Database database;
    private String tableName;
    private String fileName;

    public ExportTableCommand(Database database, String tableName, String fileName) {
        this.database = database;
        this.tableName = tableName;
        this.fileName = fileName;
    }

    @Override
    public void execute() {
        Table table = database.getTableByName(tableName);
        if (table != null) {
            exportTable(table);
        } else {
            System.out.println("Table '" + tableName + "' not found.");
        }
    }

    private void exportTable(Table table) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            // Write table name
            writer.write("TableName: " + table.getName() + "\n");

            // Write column names
            for (Column column : table.getColumns()) {
                writer.write(column.getName() + " ");
            }
            writer.write("\n");

            // Write row data
            for (Row row : table.getRows()) {
                StringBuilder rowString = new StringBuilder();
                for (Column column : table.getColumns()) {
                    String value = String.valueOf(row.getValue(column.getName()));
                    if (value != null) {
                        rowString.append(value).append(" ");
                    }
                }
                writer.write(rowString.toString().trim() + "\n");
            }

            System.out.println("Table '" + tableName + "' exported successfully to file: " + fileName);
        } catch (IOException e) {
            System.out.println("Error exporting table: " + e.getMessage());
        }
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}