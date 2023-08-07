package be.loeckerlang.compiler.intermediate.symbols;

import be.loeckerlang.compiler.ast.nodes.BlockNode;
import be.loeckerlang.compiler.ast.nodes.FunctionModifiersNode;
import be.loeckerlang.compiler.ast.nodes.MethodDeclarationNode;
import be.loeckerlang.compiler.ast.nodes.ModifiersNode;
import be.loeckerlang.compiler.intermediate.HasModifiers;
import be.loeckerlang.compiler.intermediate.tables.Block;
import be.loeckerlang.compiler.intermediate.tables.ClassSymbolTable;
import be.loeckerlang.compiler.intermediate.tables.SymbolTable;

/**
 * Represents a single method
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public class Method extends SymbolTable implements HasModifiers {

    private final MethodSignatureGroup signature_group;
    private final MethodSignature signature;
    private final ClassSymbolTable class_table;

    private FunctionModifiersNode modifiers;
    private Block body = null;

    public Method(MethodSignatureGroup signature_group, MethodSignature signature) {
        super(signature_group.getParentScope());
        this.signature_group = signature_group;
        this.signature = signature;
        this.class_table = signature_group.getParentScope();
    }

    /**
     * Get the name of this table
     *
     * @since    0.1.0
     */
    @Override
    public String getName() {
        return this.signature_group.getName();
    }

    /**
     * Get the parent table
     *
     * @since    0.1.0
     */
    @Override
    public ClassSymbolTable getParentScope() {
        return this.class_table;
    }

    /**
     * Process the declaration
     *
     * @since    0.1.0
     */
    public void processDeclaration(MethodDeclarationNode node) {

        this.modifiers = node.getModifiers();
        this.body = Block.fromNode(this, node.getBody());
    }

    /**
     * Does this method do async stuff?
     *
     * @since    0.1.0
     */
    public boolean isAsync() {
        return false;
    }

    /**
     * Get the modifiers AST node
     *
     * @since    0.1.0
     */
    @Override
    public ModifiersNode getModifiersNode() {
        return null;
    }
}
