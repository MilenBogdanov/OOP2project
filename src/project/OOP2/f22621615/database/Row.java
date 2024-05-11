package project.OOP2.f22621615.database;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class Row {
    private Map<String, Object> values;

    public Row() {
        this.values = new LinkedHashMap<>();
    }

    public void addValue(String columnName, Object value) {
        values.put(columnName, value);
    }

    public Object getValue(String columnName) {
        return values.get(columnName);
    }

    public void setValue(String columnName, Object value) {
        values.put(columnName, value);
    }

    public Set<String> getColumnNames() {
        return values.keySet();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String columnName : getColumnNames()) {
            Object value = values.get(columnName);
            sb.append(value != null ? value : "null").append(" ");
        }
        return sb.toString().trim();
    }
}
