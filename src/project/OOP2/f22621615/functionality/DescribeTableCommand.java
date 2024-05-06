package project.OOP2.f22621615.functionality;

import project.OOP2.f22621615.database.Column;
import project.OOP2.f22621615.database.Database;
import project.OOP2.f22621615.database.Table;
import project.OOP2.f22621615.interfaces.Command;
import project.OOP2.f22621615.enums.DataType;

import java.util.ArrayList;
import java.util.List;

public class DescribeTableCommand implements Command {
    private final Database database;
    private final String tableName;

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
            System.out.println("TableName: " + tableName);
            System.out.println("----------------------------------------------------------");
            List<Column> columns = table.getColumns();
            for (Column column : columns) {
                String columnName = column.getName();
                String dataTypeStr = column.getType().toString(); // convert DataType to string
                int typeStart = 0;
                int typeEnd = dataTypeStr.indexOf("("); // find the position of the opening parenthesis
                if (typeEnd == -1) {
                    typeEnd = dataTypeStr.length(); // if no opening parenthesis, assume the whole string is the data type
                }
                String parsedDataTypeStr = dataTypeStr.substring(typeStart, typeEnd); // extract the data type string
                System.out.printf("%s - ", columnName);
                DataType dataType;
                if (parsedDataTypeStr.equals("INTEGER")) {
                    dataType = DataType.INTEGER;
                } else if (parsedDataTypeStr.equals("STRING")) {
                    dataType = DataType.STRING;
                } else if (parsedDataTypeStr.equals("FLOAT")) {
                    dataType = DataType.FLOAT;
                } else if (parsedDataTypeStr.equals("NULL")) {
                    dataType = DataType.NULL;
                }
                else {
                    System.out.println("Error: Unrecognized data type '" + parsedDataTypeStr + "'.");
                    return;
                }
                System.out.println(dataType);
            }
            System.out.println("----------------------------------------------------------");
        }
    }
}