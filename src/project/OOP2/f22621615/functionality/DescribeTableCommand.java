package project.OOP2.f22621615.functionality;
import project.OOP2.f22621615.database.Database;
import project.OOP2.f22621615.database.Table;
import project.OOP2.f22621615.interfaces.Command;

public class DescribeTableCommand implements Command {
    private Database database;
    private String tableName;

    public DescribeTableCommand(Database database, String tableName) {
        this.database = database;
        this.tableName = tableName;
    }

    @Override
    public void execute() {
        describe(tableName);
    }

    public void describe(String tableName) {
        Table table = database.getTableByName(tableName);
        if (table != null) {
            System.out.println("Table: " + table.getName());
            System.out.println("Columns:");
            table.getColumns().forEach(column -> System.out.println("  - " + column.getName() + ": " + column.getType()));
        } else {
            System.out.println("Table '" + tableName + "' not found.");
        }
    }
}