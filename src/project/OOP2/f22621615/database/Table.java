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
}