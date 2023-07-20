package be.loeckerlang.compiler.ast.nodes;

import be.loeckerlang.compiler.ast.ASTBuilder;
import be.loeckerlang.compiler.ast.nodes.expressions.ExpressionNode;
import be.loeckerlang.compiler.tokens.Token;

/**
 * An expression statement
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public class ExpressionStatementNode extends StatementNode {

    private ExpressionNode expression;

    @Override
    protected void parseContents(ASTBuilder builder) {
        this.expression = ExpressionNode.parseExpression(builder, this);
    }

    /**
     * Should this end with a specific token?
     *
     * @since    0.1.0
     */
    @Override
    protected Token.Type shouldEndWith() {
        return Token.Type.SEMICOLON;
    }
}
