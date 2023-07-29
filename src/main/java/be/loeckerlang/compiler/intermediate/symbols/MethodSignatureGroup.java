package be.loeckerlang.compiler.intermediate.symbols;

import be.loeckerlang.compiler.ast.nodes.MethodDeclarationNode;
import be.loeckerlang.compiler.codegen.CodeGenerator;
import be.loeckerlang.compiler.intermediate.Symbol;
import be.loeckerlang.compiler.intermediate.tables.ClassSymbolTable;
import be.loeckerlang.compiler.intermediate.tables.SymbolTable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a group of method signatures.
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public class MethodSignatureGroup extends Symbol {

    private Map<MethodSignature, Method> methods = new HashMap<>();
    private ClassSymbolTable parent;
    private String name;

    public MethodSignatureGroup(ClassSymbolTable parent, String name) {
        this.parent = parent;
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public ClassSymbolTable getParentScope() {
        return this.parent;
    }

    /**
     * Register a method
     *
     * @since    0.1.0
     */
    public void registerMethod(MethodDeclarationNode method_node) {

        MethodSignature signature = MethodSignature.createFromNode(this.parent, method_node);

        if (this.methods.containsKey(signature)) {
            method_node.reportCompilationError("Method with this signature already exists");
            return;
        }

        Method method = new Method(this, signature);
        this.methods.put(signature, method);
        method.processDeclaration(method_node);
    }

    /**
     * Get all the methods
     *
     * @since    0.1.0
     */
    public Map<MethodSignature, Method> getMethods() {
        return this.methods;
    }
}
