package project.OOP2.f22621615.database;

import java.util.ArrayList;
import java.util.List;

public class Database {
    private List<Table> tables;

    public Database() {
        this.tables = new ArrayList<>();
    }

    public void addTable(Table table) {
        if (!tables.contains(table)) {
            tables.add(table);
        }
    }

    public List<Table> getTables() {
        return tables;
    }

    public List<Row> getRows(String tableName) {
        for (Table table : tables) {
            if (table.getName().equals(tableName)) {
                return table.getRows();
            }
        }
        return null; // Ако таблицата със съответното име не съществува
    }

    public boolean tableExists(String tableName) {
        for (Table table : tables) {
            if (table.getName().equals(tableName)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public String toString() {
        return "Database{" +
                "tables=" + tables +
                '}';
    }

    public Table getTableByName(String tableName) {
        for (Table table : tables) {
            if (table.getName().equals(tableName)) {
                return table;
            }
        }
        return null; // If table with the given name is not found
    }
}
