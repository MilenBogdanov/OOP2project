package project.OOP2.f22621615;

import java.util.Map;

public interface CommandExecutor {
    void open(String filePath);

    void close();

    void save();

    void saveAs(String filePath);

    void help();

    void exit();

    // Метод за работа с XML
    void processXml(String xmlContent);

    String readXml(String filePath);

    void writeXml(String filePath, String xmlContent);

    Map<String, String> readXmlAttributes(String xmlFilePath);
}