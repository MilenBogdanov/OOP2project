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

public class LoadTableFromXMLFileCommand implements Command {
    private Database database;
    private String fileName;

    public LoadTableFromXMLFileCommand(Database database, String fileName) {
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

                if (line.startsWith("<table")) {
                    int start = line.indexOf('"');
                    int end = line.indexOf('"', start + 1);
                    tableName = line.substring(start + 1, end);

                    // Check if table already exists
                    if (database.getTableByName(tableName) != null) {
                        System.out.println("Table '" + tableName + "' already exists in the database.");
                        continue; // Skip adding the table and continue to the next iteration
                    }

                    inTable = true;
                    table = new Table(tableName);
                    continue;
                }

                if (inTable && line.startsWith("</table")) {
                    inTable = false;
                    if (table != null) {
                        database.addTable(table);
                        System.out.println("Table '" + tableName + "' loaded successfully from XML file.");
                    }
                    continue;
                }

                if (inTable && line.startsWith("<column")) {
                    int nameStart = line.indexOf("name=\"") + 6;
                    int nameEnd = line.indexOf('"', nameStart);
                    String columnName = line.substring(nameStart, nameEnd);

                    int typeStart = line.indexOf("type=\"") + 6;
                    int typeEnd = line.indexOf('"', typeStart);
                    String dataTypeStr = line.substring(typeStart, typeEnd);

                    DataType dataType;
                    if (dataTypeStr.equals("int")) {
                        dataType = DataType.INTEGER;
                    } else if (dataTypeStr.equals("varchar")) {
                        dataType = DataType.STRING;
                    } else if (dataTypeStr.equals("double")) {
                        dataType = DataType.FLOAT;
                    } else {
                        System.out.println("Error: Unrecognized data type '" + dataTypeStr + "'.");
                        return;
                    }

                    if (table != null) {
                        table.addColumn(new Column(columnName, dataType));
                    }
                }

                if (inTable && line.startsWith("<row")) {
                    Row row = parseRow(line, table);
                    if (row != null && table != null) {
                        table.addRow(row); // Add the parsed row to the table
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private Row parseRow(String line, Table table) {
        Row row = new Row();

        // Find and parse values for all columns in the row
        int startIndex = line.indexOf("<row>") + "<row>".length();
        int endIndex = line.indexOf("</row>");
        if (startIndex != -1 && endIndex != -1) {
            String rowContent = line.substring(startIndex, endIndex);

            // Split the row content into individual column entries
            String[] columnEntries = rowContent.split("</");
            for (String entry : columnEntries) {
                // Find the column name and value
                int columnNameStart = entry.indexOf("<") + 1;
                int columnNameEnd = entry.indexOf(">");
                int valueStart = columnNameEnd + 1;

                String columnName = entry.substring(columnNameStart, columnNameEnd);
                String valueStr = entry.substring(valueStart);

                // Trim the value string to remove leading/trailing whitespace
                valueStr = valueStr.trim();

                // Add the column value to the row
                row.addValue(columnName, valueStr);
            }
        }

        return row;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
