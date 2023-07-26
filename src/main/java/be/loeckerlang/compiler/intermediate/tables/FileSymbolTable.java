package be.loeckerlang.compiler.intermediate.tables;

import java.util.HashMap;
import java.util.Map;

/**
 * The symbol table for a file
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public class FileSymbolTable extends SymbolTable {

    // The namespace this file belongs to
    private final NamespaceSymbolTable namespace;

    public FileSymbolTable(NamespaceSymbolTable namespace) {
        this.namespace = namespace;
    }

    /**
     * Get the name of this table
     *
     * @since    0.1.0
     */
    @Override
    public String getName() {
        return null;
    }

}
