package be.loeckerlang.compiler.intermediate.statements;

import be.loeckerlang.compiler.ast.nodes.VariableDeclarationStatementNode;

/**
 * A variable declaration
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public class VariableDeclaration extends Statement {

    public VariableDeclaration(VariableDeclarationStatementNode node) {
        super(node);
    }
}
