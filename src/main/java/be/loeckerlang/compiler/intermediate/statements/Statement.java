package be.loeckerlang.compiler.intermediate.statements;

import be.loeckerlang.compiler.ast.nodes.StatementNode;

/**
 * Represents an intermediate statement
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public abstract class Statement {

    private final StatementNode ast_node;

    public Statement(StatementNode node) {
        this.ast_node = node;
    }

}
