package project.OOP2.f22621615;

public interface FileCommandExecutor {
    void importFile(String fileName);

    void export(String tableName, String fileName);

    // методи за работа с XML
    String readXml(String filePath);

    void writeXml(String filePath, String xmlContent);
}