package be.loeckerlang.compiler.intermediate.symbols;

import be.loeckerlang.compiler.ast.nodes.MethodDeclarationNode;
import be.loeckerlang.compiler.ast.nodes.ParametersNode;
import be.loeckerlang.compiler.intermediate.tables.SymbolTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a signature of a method.
 * This represents all the arguments, but not the return type.
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public class MethodSignature {

    private List<String> types = new ArrayList<>();
    private List<String> names = new ArrayList<>();
    private String name;

    /**
     * Create a method signature from a declaration node
     *
     * @since    0.1.0
     */
    private MethodSignature(String name) {
        this.name = name;
    }

    /**
     * Extract the signature from the declaration node
     *
     * @since    0.1.0
     */
    private void extractFromDeclaration(SymbolTable scope, ParametersNode parameters_node) {

        if (parameters_node == null) {
            return;
        }

        parameters_node.getParameters().forEach(parameter_node -> {

            // @TODO: needs to be the resolved path eventually
            String type_path = parameter_node.getType().getPathAsString();
            String parameter_name = parameter_node.getName().getName();

            this.types.add(type_path);
            this.names.add(parameter_name);
        });
    }

    /**
     * Get the name
     *
     * @since    0.1.0
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the parameters
     *
     * @since    0.1.0
     */
    public List<String> getParameters() {
        return this.names;
    }

    /**
     * Get the hashcode of the string & the types
     *
     * @since    0.1.0
     */
    @Override
    public int hashCode() {

        int hash = this.name.hashCode();

        for (String type : this.types) {
            hash += type.hashCode();
        }

        return hash;
    }

    /**
     * Compare this signature to another
     *
     * @since    0.1.0
     */
    @Override
    public boolean equals(Object other) {

        if (other == null) {
            return false;
        }

        if (this == other) {
            return true;
        }

        if (!(other instanceof MethodSignature other_signature)) {
            return false;
        }

        if (!this.name.equals(other_signature.name)) {
            return false;
        }

        return this.types.equals(other_signature.types);
    }

    /**
     * Create a signature from the given declaration node
     *
     * @since    0.1.0
     */
    public static MethodSignature createFromNode(SymbolTable scope, MethodDeclarationNode method_node) {
        MethodSignature result = new MethodSignature(method_node.getName().getName());
        result.extractFromDeclaration(scope, method_node.getParameters());
        return result;
    }


}
