package be.loeckerlang.compiler.intermediate.statements;

import be.loeckerlang.compiler.ast.nodes.VariableDeclarationStatementNode;
import be.loeckerlang.compiler.intermediate.expressions.Expression;
import be.loeckerlang.compiler.intermediate.symbols.Type;

/**
 * A variable declaration
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public class VariableDeclaration extends Statement {

    protected Type type;
    protected String name;
    protected Expression initializer;

    public VariableDeclaration(VariableDeclarationStatementNode node) {
        super(node);
    }
}
