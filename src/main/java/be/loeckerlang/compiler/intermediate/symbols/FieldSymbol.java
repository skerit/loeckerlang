package be.loeckerlang.compiler.intermediate.symbols;

import be.loeckerlang.compiler.intermediate.Symbol;
import be.loeckerlang.compiler.intermediate.tables.ClassSymbolTable;

/**
 * A field
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public class FieldSymbol extends Symbol {

    private final String name;
    private final ClassSymbolTable parent;

    public FieldSymbol(ClassSymbolTable parent, String name) {
        this.parent = parent;
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Get the parent table
     *
     * @since    0.1.0
     */
    @Override
    public ClassSymbolTable getParentScope() {
        return this.parent;
    }
}
