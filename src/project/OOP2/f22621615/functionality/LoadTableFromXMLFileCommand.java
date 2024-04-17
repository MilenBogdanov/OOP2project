package project.OOP2.f22621615.functionality;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import project.OOP2.f22621615.database.Column;
import project.OOP2.f22621615.database.Database;
import project.OOP2.f22621615.database.Table;
import project.OOP2.f22621615.enums.DataType;
import project.OOP2.f22621615.interfaces.Command;

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
                    continue;
                }

                if (inTable && line.startsWith("</table")) {
                    inTable = false;
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

                    Table table = new Table(tableName);
                    table.addColumn(new Column(columnName, dataType));
                    database.addTable(table);
                }
            }

            System.out.println("Table '" + tableName + "' loaded successfully from XML file.");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}