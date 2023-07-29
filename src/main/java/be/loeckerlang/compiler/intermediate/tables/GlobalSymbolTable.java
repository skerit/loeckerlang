package be.loeckerlang.compiler.intermediate.tables;

import be.loeckerlang.compiler.codegen.CodeGenerator;

import java.util.HashMap;
import java.util.Map;

/**
 * The root/global symbol table.
 * represents the entire project.
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public class GlobalSymbolTable extends SymbolTable {

    private final Map<String, NamespaceSymbolTable> namespaces = new HashMap<>();
    private CodeGenerator compiler = null;

    /**
     * Initialize the instance
     *
     * @since    0.1.0
     */
    public GlobalSymbolTable() {
        super(null);
    }

    /**
     * Initialize the instance
     *
     * @since    0.1.0
     */
    public GlobalSymbolTable(CodeGenerator compiler) {
        super(null);
        this.compiler = compiler;
    }

    /**
     * Get the root/global table
     *
     * @since    0.1.0
     */
    @Override
    public GlobalSymbolTable getRoot() {
        return this;
    }

    /**
     * Get a namespace symbol table
     *
     * @since 0.1.0
     */
    public NamespaceSymbolTable getNamespace(String name) {

        NamespaceSymbolTable result = this.namespaces.get(name);

        if (result == null) {
            result = new NamespaceSymbolTable(this, name);
            this.namespaces.put(name, result);
        }

        return result;
    }

    /**
     * Get all the current namespaces
     *
     * @since 0.1.0
     */
    public Map<String, NamespaceSymbolTable> getNamespaces() {
        return this.namespaces;
    }
}
