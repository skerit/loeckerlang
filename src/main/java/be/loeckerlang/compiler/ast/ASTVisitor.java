package be.loeckerlang.compiler.ast;

import be.loeckerlang.compiler.ast.nodes.*;
import be.loeckerlang.compiler.ast.nodes.expressions.LiteralNode;

/**
 * The AST visitor
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public interface ASTVisitor {

    /**
     * Visit a node
     *
     * @since    0.1.0
     */
    default void visit(ASTNode node) {
        if (node instanceof ClassNode class_node) {
            this.visit(class_node);
        } else if (node instanceof ClassModifiersNode class_modifiers_node) {
            this.visit(class_modifiers_node);
        } else if (node instanceof SimpleNameNode id_node) {
            this.visit(id_node);
        } else if (node instanceof QualifiedNameNode idp_node) {
            this.visit(idp_node);
        } else if (node instanceof FileNode main_node) {
            this.visit(main_node);
        } else if (node instanceof NamespaceNode ns_node) {
            this.visit(ns_node);
        } else if (node instanceof UseNode use_node) {
            this.visit(use_node);
        } else if (node instanceof FieldDeclarationNode fnode) {
            this.visit(fnode);
        } else if (node instanceof ListOfNodes<? extends ASTNode> list) {
            list.forEach(this::visit);
        } else if (node instanceof LiteralNode literal_node) {
            this.visit(literal_node);
        } else if (node instanceof MethodDeclarationNode method) {
           this.visit(method);
        } else if (node instanceof ParametersNode parameters) {
            this.visit(parameters);
        } else if (node instanceof ParameterNode parameter) {
            this.visit(parameter);
        } else if (node instanceof BlockNode block) {
            this.visit(block);
        } else {
            System.out.println("Unknown node: " + node.getClass().getName());
        }
    }

    void visit(BlockNode node);
    void visit(ParameterNode node);
    void visit(ParametersNode node);
    void visit(MethodDeclarationNode node);
    void visit(LiteralNode node);
    void visit(FieldDeclarationNode node);
    void visit(ClassModifiersNode node);
    void visit(ClassNode node);
    void visit(SimpleNameNode node);
    void visit(QualifiedNameNode node);
    void visit(FileNode node);
    void visit(NamespaceNode node);
    void visit(UseNode node);
}
