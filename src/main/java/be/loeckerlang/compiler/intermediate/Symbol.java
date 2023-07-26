package be.loeckerlang.compiler.intermediate;

import be.loeckerlang.compiler.intermediate.tables.SymbolTable;

/**
 * Represent a symbol in the symbol table.
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public abstract class Symbol {

    /**
     * Get the name of this symbol
     *
     * @since    0.1.0
     */
    public abstract String getName();

    /**
     * Get the parent SymbolTable it was declared in
     *
     * @since    0.1.0
     */
    public abstract SymbolTable getParentScope();
}
