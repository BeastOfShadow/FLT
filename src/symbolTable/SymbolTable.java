package symbolTable;

import java.util.HashMap;

import ast.LangType;

public class SymbolTable {
    private static HashMap<String, Attributes> table;

    /**
     * Initializes the symbol table.
     * This method should be called before any other operations on the symbol table.
     */
    public static void init() {
        table = new HashMap<>();
    }

    /**
     * Enters a new entry into the symbol table.
     * If an entry with the same ID already exists, it returns false.
     * Otherwise, it adds the entry and returns true.
     * 
     * @param id    the identifier for the entry
     * @param entry the attributes of the entry
     * @return true if the entry was added, false if it already exists
     */
    public static boolean enter(String id, Attributes entry) {
        if (table.containsKey(id))
            return false;

        table.put(id, entry);
        return true;
    }

    /**
     * Looks up an entry in the symbol table by its ID.
     * 
     * @param id the identifier of the entry to look up
     * @return the attributes of the entry, or null if it does not exist
     */
    public static Attributes lookup(String id) {
        return table.get(id);
    }

    /**
     * Checks if an entry with the given ID exists in the symbol table.
     * 
     * @param id the identifier to check
     * @return true if the entry exists, false otherwise
     */
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

    /**
     * Checks if the symbol table is empty.
     * 
     * @return true if the symbol table is empty, false otherwise
     */
    public static int size() {
        return table.size();
    }
}
