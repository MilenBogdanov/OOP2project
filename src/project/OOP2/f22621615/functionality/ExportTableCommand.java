package project.OOP2.f22621615.functionality;

/*import project.OOP2.f22621615.database.Database;
import project.OOP2.f22621615.database.Row;
import project.OOP2.f22621615.database.Table;
import project.OOP2.f22621615.interfaces.Command;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

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
            List<Row> rows = table.getRows();
            try (FileWriter writer = new FileWriter(fileName)) {
                // Write table data to the file
                for (Row row : rows) {
                    writer.write(row.toString() + System.lineSeparator());
                }
                System.out.println("Table '" + tableName + "' exported to file '" + fileName + "'.");
            } catch (IOException e) {
                System.out.println("Error exporting table to file: " + e.getMessage());
            }
        } else {
            System.out.println("Table '" + tableName + "' not found.");
        }
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
*/