package be.loeckerlang.compiler.intermediate;

import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * A symbol table (like a scope)
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public class SymbolTable {

    @Nullable
    protected SymbolTable parent;

    public SymbolTable(SymbolTable parent) {
        this.parent = parent;
    }

    public SymbolTable() {
        this(null);
    }

    public SymbolTable getParent() {
        return this.parent;
    }



}
