package project.OOP2.f22621615;

public interface TableCommandExecutor {
    void showTables();

    void describe(String tableName);

    void print(String tableName);

    void select(int columnNumber, String value, String tableName);

    void addColumn(String tableName, String columnName, String columnType);

    void update(String tableName, int searchColumnNumber, String searchValue, int targetColumnNumber, String targetValue);

    void delete(String tableName, int searchColumnNumber, String searchValue);

    void insert(String tableName, String values);

    void innerJoin(String table1, int columnNumber1, String table2, int columnNumber2);

    void rename(String oldName, String newName);

    void count(String tableName, int searchColumnNumber, String searchValue);

    void aggregate(String tableName, int searchColumnNumber, String searchValue, int targetColumnNumber, String operation);

    // методи за работа с XML
    String readTableAsXml(String tableName);

    void writeTableAsXml(String tableName, String xmlContent);
}