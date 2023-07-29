package be.loeckerlang.compiler.intermediate.tables;

import be.loeckerlang.compiler.ast.nodes.FieldDeclarationNode;
import be.loeckerlang.compiler.ast.nodes.MethodDeclarationNode;

/**
 * The SymbolTable builder
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public class ClassSymbolTableBuilder extends SymbolTableBuilder {

    private final ClassSymbolTable class_table;

    public ClassSymbolTableBuilder(ClassSymbolTable table) {
        super(table);
        this.class_table = table;
    }

    /**
     * Process a field declaration (in the current class)
     *
     * @since 0.1.0
     */
    @Override
    public void visit(FieldDeclarationNode node) {
        this.class_table.registerProperty(node);
    }

    /**
     * Process a method declaration (in the current class)
     *
     * @since 0.1.0
     */
    @Override
    public void visit(MethodDeclarationNode node) {
        this.class_table.registerMethod(node);
    }
}
