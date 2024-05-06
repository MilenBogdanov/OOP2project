package project.OOP2.f22621615.functionality;
import project.OOP2.f22621615.database.Database;
import project.OOP2.f22621615.database.Row;
import project.OOP2.f22621615.database.Table;
import project.OOP2.f22621615.interfaces.Command;
import java.io.FileWriter;
import java.io.IOException;

public class DeleteCommand implements Command {
    private final Database database;
    private String tableName;
    private String searchColumnName;
    private String searchColumnValue;

    public DeleteCommand(Database database, String tableName, String searchColumnName, String searchColumnValue) {
        this.database = database;
        this.tableName = tableName;
        this.searchColumnName = searchColumnName;
        this.searchColumnValue = searchColumnValue;
    }

    @Override
    public void execute() {
        Table table = database.getTableByName(tableName);
        if (table != null) {
            int rowsDeleted = deleteRows(table);
            System.out.println(rowsDeleted + " rows deleted from table '" + tableName + "'.");
            updateTextFile(table); // Update the associated text file
        } else {
            System.out.println("Table '" + tableName + "' not found.");
        }
    }

    private int deleteRows(Table table) {
        int rowsDeleted = 0;
        // Get the rows of the table
        for (Row row : table.getRows()) {
            // Check if the search column value matches
            String value = String.valueOf(row.getValue(searchColumnName));
            if (value.equals(searchColumnValue)) {
                // Remove the row
                table.getRows().remove(row);
                rowsDeleted++;
            }
        }
        return rowsDeleted;
    }

    private void updateTextFile(Table table) {
        try {
            StringBuilder content = new StringBuilder();
            // Append table name and columns to content
            content.append("TableName: ").append(table.getName()).append("\n");
            content.append("Columns:\n");
            for (String columnName : table.getColumnNames()) {
                content.append("column: ").append(columnName).append(" ").append(table.getColumnType(columnName)).append("\n");
            }
            content.append("Rows:\n");

            // Append rows to content
            for (Row row : table.getRows()) {
                StringBuilder rowContent = new StringBuilder();
                for (String columnName : table.getColumnNames()) {
                    rowContent.append(row.getValue(columnName)).append(" ");
                }
                content.append(rowContent.toString().trim()).append("\n");
            }

            // Write content to the associated text file
            FileWriter writer = new FileWriter(table.getAssociatedTextFile());
            writer.write(content.toString());
            writer.close();
        } catch (IOException e) {
            System.out.println("Error updating text file: " + e.getMessage());
        }
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setSearchColumnName(String searchColumnName) {
        this.searchColumnName = searchColumnName;
    }

    public void setSearchColumnValue(String searchColumnValue) {
        this.searchColumnValue = searchColumnValue;
    }
}