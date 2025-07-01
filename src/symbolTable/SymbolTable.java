package symbolTable;

import java.util.HashMap;

import ast.LangType;

public class SymbolTable {
    private static HashMap<String, Attributes> table;

    public static void init() {
       table = new HashMap<>();
    }

    public static boolean enter(String id, Attributes entry) {
        return table.put(id, entry) != null ? true : false;
    }

    public static Attributes lookup(String id) {
        return table.get(id);
    }

    public static String toStr() {
        StringBuilder sb = new StringBuilder();

        // Top border
        sb.append("┌────────────┬────────────┬────────────┐\n");
        sb.append(String.format("│ %-10s │ %-10s │ %-10s │\n", "ID", "Type", "Register"));
        // Header separator
        sb.append("├────────────┼────────────┼────────────┤\n");

        // Table rows
        for (String key : table.keySet()) {
            Attributes attr = table.get(key);
            sb.append(String.format("│ %-10s │ %-10s │ %-10s │\n",
                    key,
                    attr.getType(),
                    attr.getName(),
                    attr.getRegister()));
        }

        // Bottom border
        sb.append("└────────────┴────────────┴────────────┘\n");

        return sb.toString();
    }

    public static int size() {
        return table.size();
    }
}
