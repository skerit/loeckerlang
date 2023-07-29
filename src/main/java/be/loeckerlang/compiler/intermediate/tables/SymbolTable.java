package be.loeckerlang.compiler.intermediate.tables;

import be.loeckerlang.compiler.intermediate.Symbol;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * A symbol table (like a scope)
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public class SymbolTable extends Symbol {

    // The optional parent symbol table
    @Nullable
    protected SymbolTable parent;

    // All the sub classes by their name
    protected final Map<String, ClassSymbolTable> classes = new HashMap<>();

    // All the declared variables by their name
    protected final Map<String, Symbol> variables = new HashMap<>();

    // All the declared functions by their name
    protected final Map<String, Symbol> functions = new HashMap<>();

    /**
     * Initialize the instance
     *
     * @since    0.1.0
     */
    public SymbolTable(SymbolTable parent) {
        this.parent = parent;
    }

    /**
     * Initialize the instance without a parent
     *
     * @since    0.1.0
     */
    public SymbolTable() {
        this(null);
    }

    /**
     * Get the parent table
     *
     * @since    0.1.0
     */
    @Override
    public SymbolTable getParentScope() {
        return this.parent;
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

    /**
     * Get the root/global table
     *
     * @since    0.1.0
     */
    public GlobalSymbolTable getRoot() {

        if (this.parent == null) {
            return null;
        }

        return this.parent.getRoot();
    }

    /**
     * Get a variable by name
     *
     * @since    0.1.0
     */
    @Nullable
    public Symbol getVariable(String name) {

        Symbol result = this.variables.get(name);

        if (result == null && this.parent != null) {
            result = this.parent.getVariable(name);
        }

        return result;
    }

    /**
     * Get the namespace this is in
     *
     * @since    0.1.0
     */
    @Nullable
    public NamespaceSymbolTable getNamespace() {

        if (this instanceof NamespaceSymbolTable ns_table) {
            return ns_table;
        }

        if (this.parent == null) {
            return null;
        }

        return this.parent.getNamespace();
    }

    /**
     * Create and register a class
     *
     * @since    0.1.0
     */
    public ClassSymbolTable createClass(String name) {
        ClassSymbolTable result = new ClassSymbolTable(this, name);

        this.classes.put(name, result);

        return result;
    }

    /**
     * Get all the classes
     *
     * @since 0.1.0
     */
    public Map<String, ClassSymbolTable> getClasses() {
        return this.classes;
    }
}
