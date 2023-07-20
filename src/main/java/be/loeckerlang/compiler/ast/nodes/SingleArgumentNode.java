package be.loeckerlang.compiler.ast.nodes;

import be.loeckerlang.compiler.ast.ASTBuilder;
import be.loeckerlang.compiler.ast.nodes.expressions.ExpressionNode;
import be.loeckerlang.compiler.tokens.Token;

/**
 * The Argument Node class:
 * Represents a single argument
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public class SingleArgumentNode extends ASTNode {

    // Arguments can have an optional name
    private SimpleNameNode name = null;

    /**
     * Parse the contents
     *
     * @since    0.1.0
     */
    @Override
    protected void parseContents(ASTBuilder builder) {

        if (builder.currentTokensAre(Token.Type.IDENTIFIER, Token.Type.COLON)) {
            this.name = new SimpleNameNode();
            this.name.parse(builder, this);

            // Consume the colon
            builder.consume();
        }

        Token current = builder.peekCurrent();

        if (current.getType() == Token.Type.RIGHT_PAREN) {
            return;
        }

        // Parse the expression
        ExpressionNode expression = ExpressionNode.parseExpression(builder, this);
    }
}
