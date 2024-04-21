package project.OOP2.f22621615.database;
import java.util.HashMap;
import java.util.Map;

public class Row {
    private Map<String, Object> values;

    public Row() {
        this.values = new HashMap<>();
    }

    public void addValue(String columnName, Object value) {
        values.put(columnName, value);
    }

    public Object getValue(String columnName) {
        return values.get(columnName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("| ");
        for (Map.Entry<String, Object> entry : values.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append(" | ");
        }
        return sb.toString();
    }
}