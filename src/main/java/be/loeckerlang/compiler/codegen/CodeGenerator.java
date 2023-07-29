package be.loeckerlang.compiler.codegen;

import be.loeckerlang.compiler.intermediate.Symbol;
import be.loeckerlang.compiler.intermediate.symbols.FieldSymbol;
import be.loeckerlang.compiler.intermediate.tables.GlobalSymbolTable;
import be.loeckerlang.compiler.intermediate.tables.SymbolTable;

import java.util.HashMap;
import java.util.Map;

/**
 * Turn the intermediate Symbols into code.
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public abstract class CodeGenerator {

    // The root/global table
    protected final GlobalSymbolTable global_table;

    // The map to the compiled symbol names
    private Map<String, String> symbol_names = new HashMap<>();

    // The current id number
    private int symbol_id = 0;

    /**
     * Create the CodeGenerator instance
     *
     * @since    0.1.0
     */
    public CodeGenerator(GlobalSymbolTable global_table) {
        this.global_table = global_table;
    }

    /**
     * Get a unique id number
     *
     * @since    0.1.0
     */
    protected int getUniqueId() {
        return this.symbol_id++;
    }

    /**
     * Get the name to use for a symbol in the compiled code.
     *
     * @since    0.1.0
     */
    protected String getSymbolName(Symbol symbol) {

        if (symbol instanceof GlobalSymbolTable) {
            return "$global";
        }

        if (symbol instanceof FieldSymbol field_symbol) {
            return field_symbol.getName();
        }

        SymbolTable parent_table = symbol.getParentScope();
        String name = symbol.getName();

        if (name == null) {
            return null;
        }

        if (parent_table != null) {
            String parent_name = this.getSymbolName(parent_table);

            if (parent_name != null && !parent_name.isEmpty()) {
                name = parent_name + "_" + name;
            }
        }

        String result = this.symbol_names.get(name);

        if (result == null) {
            result = name + "_" + this.symbol_id++;
            this.symbol_names.put(name, result);
        }

        return result;
    }

    /**
     * Actually generate the single code file
     *
     * @since    0.1.0
     */
    public abstract String generateCode();
}
