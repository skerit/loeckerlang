package be.loeckerlang.compiler.ast.nodes;

import be.loeckerlang.compiler.ast.ASTBuilder;
import be.loeckerlang.compiler.ast.nodes.expressions.ExpressionNode;
import be.loeckerlang.compiler.tokens.Token;

/**
 * The SimpleName Node class:
 * Represents a single identifier
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public class SimpleNameNode extends ExpressionNode {

    private String identifier = null;

    /**
     * Parse an IdentifierNode at the current position
     *
     * @since    0.1.0
     */
    protected void parseContents(ASTBuilder builder) {

        Token token = builder.peekCurrent();
        Token.Type type = token.getType();

        if (type == Token.Type.IDENTIFIER) {
            builder.consume();
            this.identifier = token.getLexeme();
        } else {
            builder.reportSyntaxError("Expected identifier, but got " + type);
        }
    }

    /**
     * Get the name of the identifier
     *
     * @since    0.1.0
     */
    public String getName() {
        return this.identifier;
    }

    /**
     * Parse an optional name
     *
     * @since    0.1.0
     */
    public static SimpleNameNode parseOptional(ASTBuilder builder, ASTNode parent) {

        Token token = builder.peekCurrent();
        Token.Type type = token.getType();

        if (type == Token.Type.IDENTIFIER) {
            SimpleNameNode result = new SimpleNameNode();
            result.parse(builder, parent);
            return result;
        }

        return null;
    }
}
