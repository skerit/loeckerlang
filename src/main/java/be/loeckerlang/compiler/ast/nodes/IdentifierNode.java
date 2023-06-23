package be.loeckerlang.compiler.ast.nodes;

import be.loeckerlang.compiler.ast.ASTBuilder;
import be.loeckerlang.compiler.tokens.Token;

/**
 * The Identifier Node class:
 * Represents a single identifier
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public class IdentifierNode extends ASTNode {

    private String identifier = null;

    /**
     * Parse an IdentifierNode at the current position
     *
     * @since    0.1.0
     */
    public void parseContents(ASTBuilder builder) {

        Token token = builder.peekCurrent();
        Token.Type type = token.getType();

        if (type == Token.Type.IDENTIFIER) {
            builder.consume();
            this.identifier = token.getLexeme();
        } else {
            builder.reportSyntaxError("Expected identifier, but got " + type);
        }
    }
}
