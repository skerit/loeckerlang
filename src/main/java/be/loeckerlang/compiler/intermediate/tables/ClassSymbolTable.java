package be.loeckerlang.compiler.intermediate.tables;

import be.loeckerlang.compiler.ast.nodes.FieldDeclarationNode;
import be.loeckerlang.compiler.ast.nodes.MethodDeclarationNode;
import be.loeckerlang.compiler.intermediate.symbols.FieldSymbol;
import be.loeckerlang.compiler.intermediate.symbols.MethodSignatureGroup;

import java.util.HashMap;
import java.util.Map;

/**
 * The Class symbol table
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public class ClassSymbolTable extends SymbolTable {

    // All the declared properties
    protected final Map<String, FieldSymbol> properties = new HashMap<>();

    // All the declared methods
    protected final Map<String, MethodSignatureGroup> methods = new HashMap<>();

    // The name of the class
    protected final String name;

    /**
     * Initialize the instance
     *
     * @since    0.1.0
     */
    public ClassSymbolTable(SymbolTable parent, String name) {
        super(parent);
        this.name = name;
    }

    /**
     * Register a property
     *
     * @since    0.1.0
     */
    public void registerProperty(FieldDeclarationNode field_node) {
        FieldSymbol symbol = new FieldSymbol(this, field_node.getName().getName());
        this.properties.put(field_node.getName().getName(), symbol);
    }

    /**
     * Register a method
     *
     * @since    0.1.0
     */
    public void registerMethod(MethodDeclarationNode method_node) {

        String name = method_node.getName().getName();

        MethodSignatureGroup group = this.methods.get(name);

        if (group == null) {
            group = new MethodSignatureGroup(this, name);
            this.methods.put(name, group);
        }

        group.registerMethod(method_node);
    }

    /**
     * Get all the properties
     *
     * @since    0.1.0
     */
    public Map<String, FieldSymbol> getProperties() {
        return this.properties;
    }

    /**
     * Get all the methods
     *
     * @since    0.1.0
     */
    public Map<String, MethodSignatureGroup> getMethodSignatureGroups() {
        return this.methods;
    }

    /**
     * Get the simple name of this class
     *
     * @since    0.1.0
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the unique name of this class
     *
     * @since    0.1.0
     */
    public String getUniqueName() {
        return this.getNamespace().getUniqueName() + "." + this.getName();
    }

}
