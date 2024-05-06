package project.OOP2.f22621615.database;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private String name;
    private List<Column> columns;
    private List<Row> rows; // Add rows list

    public Table(String name) {
        this.name = name;
        this.columns = new ArrayList<>();
        this.rows = new ArrayList<>(); // Initialize rows list
    }

    public void addColumn(Column column) {
        columns.add(column);
    }

    public String getName() {
        return name;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public List<Row> getRows() {
        return rows;
    }

    // Add method to add row to the table
    public void addRow(Row row) {
        rows.add(row);
    }

    @Override
    public String toString() {
        return "Table{" +
                "name='" + name + '\'' +
                ", columns=" + columns +
                ", rows=" + rows + // Include rows in the string representation
                '}';
    }

    public List<String> getColumnNames() {
        List<String> columnNames = new ArrayList<>();
        for (Column column : columns) {
            columnNames.add(column.getName());
        }
        return columnNames;
    }

    public String getColumnType(String columnName) {
        for (Column column : columns) {
            if (column.getName().equals(columnName)) {
                return String.valueOf(column.getType());
            }
        }
        return null; // Return null if column not found
    }

    public String getAssociatedTextFile() {
        // Implement this method based on your project structure
        // Return the associated text file name for this table
        // For example:
        return name + ".txt";
    }

    public void updateRow(Row updatedRow) {
        for (int i = 0; i < rows.size(); i++) {
            Row row = rows.get(i);
            // Check if the row IDs match
            if (row.getValue("id").equals(updatedRow.getValue("id"))) {
                // Replace the old row with the updated row
                rows.set(i, updatedRow);
                return;
            }
        }
    }
}
