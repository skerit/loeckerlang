package be.loeckerlang.compiler.intermediate.tables;

import be.loeckerlang.compiler.ast.ASTVisitor;
import be.loeckerlang.compiler.ast.nodes.*;
import be.loeckerlang.compiler.ast.nodes.expressions.LiteralNode;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * The SymbolTable builder
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public class SymbolTableBuilder implements ASTVisitor {

    // The symbol table this is working on
    private final SymbolTable symbol_table;

    // The root/global symbol table
    private final GlobalSymbolTable global_symbol_table;

    /**
     * Create the SymbolTable builder
     * @since 0.1.0
     */
    public SymbolTableBuilder(GlobalSymbolTable table) {
        this.symbol_table = table;
        this.global_symbol_table = table;
    }

    /**
     * Create the SymbolTable builder
     * @since 0.1.0
     */
    public SymbolTableBuilder(SymbolTable table) {
        this.symbol_table = table;
        this.global_symbol_table = table.getRoot();
    }

    /**
     * Get the current symbol table
     *
     * @since 0.1.0
     */
    public SymbolTable getSymbolTable() {
        return this.symbol_table;
    }

    /**
     * Get the root symbol table
     * (Kind of like the "global" scope)
     *
     * @since 0.1.0
     */
    public GlobalSymbolTable getRoot() {
        return this.symbol_table.getRoot();
    }

    /**
     * Get a namespace symbol table
     *
     * @since 0.1.0
     */
    @Nullable
    public NamespaceSymbolTable getNamespace(NamespaceNode namespace_node) {

        if (namespace_node == null) {
            return null;
        }

        String path = namespace_node.getPathAsString();

        if (path == null || path.isBlank()) {
            return null;
        }

        return this.getNamespace(path);
    }

    /**
     * Get a namespace symbol table
     *
     * @since 0.1.0
     */
    public NamespaceSymbolTable getNamespace(String name) {
        return this.getRoot().getNamespace(name);
    }

    /**
     * Create a child symbol table & return its builder
     *
     * @since 0.1.0
     */
    public SymbolTableBuilder createChild() {
        return new SymbolTableBuilder(new SymbolTable(this.symbol_table));
    }

    /**
     * Process a file node
     *
     * @since 0.1.0
     */
    @Override
    public void visit(FileNode node) {

        // Make sure the namespace exists
        NamespaceSymbolTable namespace = this.getNamespace(node.getNamespace());

        if (namespace == null) {
            node.reportCompilationError("File doesn't have a namespace");
            return;
        }

        // Create a new symbol table for this file
        FileSymbolTable file_table = namespace.createFileSymbolTable(node);

        SymbolTableBuilder file_builder = new SymbolTableBuilder(file_table);

        if (node.getUses() != null) {
            node.getUses().accept(file_builder);
        }

        if (node.getClassNode() != null) {
            node.getClassNode().accept(file_builder);
        }
    }

    /**
     * Process a use node
     *
     * @since 0.1.0
     */
    @Override
    public void visit(UseNode node) {

        // @TODO

    }

    /**
     * Process a class
     *
     * @since 0.1.0
     */
    @Override
    public void visit(ClassNode node) {

        ClassSymbolTable class_symbol_table = this.symbol_table.createClass(node.getName().getName());

        ClassSymbolTableBuilder class_builder = new ClassSymbolTableBuilder(class_symbol_table);

        if (node.getModifiers() != null) {
            node.getModifiers().accept(class_builder);
        }

        if (node.getFields() != null) {
            node.getFields().accept(class_builder);
        }

        if (node.getMethods() != null) {
            node.getMethods().accept(class_builder);
        }

    }

    /**
     * Process a field declaration (in the current class)
     *
     * @since 0.1.0
     */
    @Override
    public void visit(FieldDeclarationNode node) {
        throw new RuntimeException("Only valid inside a ClassSymbolTableBuilder");
    }

    @Override
    public void visit(BlockNode node) {

    }

    @Override
    public void visit(ParameterNode node) {

    }

    @Override
    public void visit(ParametersNode node) {

    }

    @Override
    public void visit(MethodDeclarationNode node) {

    }

    @Override
    public void visit(LiteralNode node) {

    }



    @Override
    public void visit(ClassModifiersNode node) {

    }



    @Override
    public void visit(SimpleNameNode node) {

    }

    @Override
    public void visit(QualifiedNameNode node) {

    }



    @Override
    public void visit(NamespaceNode node) {

    }


}
