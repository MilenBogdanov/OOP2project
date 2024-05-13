package project.OOP2.f22621615.functionality;


import project.OOP2.f22621615.database.Database;
import project.OOP2.f22621615.database.Row;
import project.OOP2.f22621615.database.Table;
import project.OOP2.f22621615.enums.DataType;
import project.OOP2.f22621615.interfaces.Command;

import java.util.List;
import java.text.DecimalFormat;

public class AggregateCommand implements Command {
    private Database database;
    private String tableName;
    private String searchColumnName;
    private Object searchValue;
    private String targetColumnName;
    private String operation;

    public AggregateCommand(Database database) {
        this.database = database;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setSearchColumnName(String searchColumnName) {
        this.searchColumnName = searchColumnName;
    }

    public void setSearchValue(Object searchValue) {
        this.searchValue = searchValue;
    }

    public void setTargetColumnName(String targetColumnName) {
        this.targetColumnName = targetColumnName;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    @Override
    public void execute() {
        Table table = database.getTableByName(tableName);
        if (table == null) {
            System.out.println("Table '" + tableName + "' not found.");
            return;
        }

        DataType searchColumnType = table.getColumn(searchColumnName).getType();
        DataType targetColumnType = table.getColumn(targetColumnName).getType();
        if (!isNumericType(searchColumnType) || !isNumericType(targetColumnType)) {
            System.out.println("Error: Search column and target column must be numeric.");
            return;
        }

        double result = 0;
        double productResult = 1;

        List<Row> rows = table.getRows();
        for (Row row : rows) {
            Object searchColumnValue = row.getValue(searchColumnName);
            if (searchColumnValue.equals(searchValue)) {
                Object targetColumnValue = row.getValue(targetColumnName);
                double numericValue = Double.parseDouble(targetColumnValue.toString());
                switch (operation) {
                    case "sum":
                        result += numericValue;
                        break;
                    case "product":
                        productResult *= numericValue;
                        break;
                    case "maximum":
                        result = Math.max(result, numericValue);
                        break;
                    case "minimum":
                        result = (result == 0) ? numericValue : Math.min(result, numericValue);
                        break;
                    default:
                        System.out.println("Unsupported operation: " + operation);
                        return;
                }
            }
        }

        if (operation.equals("product")) {
            DecimalFormat df = new DecimalFormat("#,###");
            String formattedResult = df.format(productResult);
            System.out.println("Aggregate result: " + formattedResult + " BGN");
        } else {
            System.out.println("Aggregate result: " + result + " BGN");
        }
    }

    private boolean isNumericType(DataType dataType) {
        return dataType == DataType.INTEGER || dataType == DataType.FLOAT;
    }
}