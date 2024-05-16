package project.OOP2.f22621615.functionality;

import project.OOP2.f22621615.database.Database;
import project.OOP2.f22621615.database.Row;
import project.OOP2.f22621615.database.Table;
import project.OOP2.f22621615.interfaces.Command;

import java.io.FileWriter;
import java.io.IOException;

public class InsertCommand implements Command {
    private Database database;
    private String tableName;
    private String[] values;

    public InsertCommand(Database database, String tableName, String[] values) {
        this.database = database;
        this.tableName = tableName;
        this.values = values;
    }

    @Override
    public void execute() {
        Table table = database.getTableByName(tableName);
        if (table != null) {
            if (values.length == table.getColumns().size()) {
                Row newRow = new Row();
                for (int i = 0; i < values.length; i++) {
                    newRow.addValue(table.getColumns().get(i).getName(), values[i]);
                }
                table.addRow(newRow);
                updateTextFile(table);
                System.out.println("Row inserted successfully into table '" + tableName + "'.");
            } else {
                System.out.println("Number of values does not match the number of columns in the table.");
            }
        } else {
            System.out.println("Table '" + tableName + "' not found.");
        }
    }

    private void updateTextFile(Table table) {
        String fileName = table.getAssociatedTextFile();
        try {
            FileWriter writer = new FileWriter(fileName, true);

            StringBuilder newRow = new StringBuilder();
            for (String value : values) {
                newRow.append(value).append(" ");
            }
            newRow.append(System.lineSeparator());

            writer.write(newRow.toString());
            writer.close();
        } catch (IOException e) {
            System.out.println("Error updating text file: " + e.getMessage());
        }
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setValues(String[] values) {
        this.values = values;
    }
}