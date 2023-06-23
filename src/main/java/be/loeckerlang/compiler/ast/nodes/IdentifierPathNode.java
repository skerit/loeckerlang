package be.loeckerlang.compiler.ast.nodes;

import be.loeckerlang.compiler.ast.ASTBuilder;
import be.loeckerlang.compiler.tokens.Token;

import java.util.ArrayList;
import java.util.List;

/**
 * The IdentifierPath Node class:
 * Represents a path of identifiers
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public class IdentifierPathNode extends ASTNode {

    private List<IdentifierNode> identifiers = null;

    /**
     * Get all the identifiers in this path
     *
     * @since    0.1.0
     */
    public List<IdentifierNode> getIdentifiers() {
        return identifiers;
    }

    /**
     * Parse a IdentifierPathNode at the current position
     *
     * @since    0.1.0
     */
    @Override
    protected void parseContents(ASTBuilder builder) {

        Token first_token = builder.peekCurrent();

        if (first_token.getType() != Token.Type.IDENTIFIER) {
            builder.reportSyntaxError("Expected identifier");
        }

        List<IdentifierNode> identifiers = new ArrayList<>();

        while (builder.hasMore()) {
            Token identifier_token = builder.getNext();
            Token next_token = builder.peekCurrent();

            IdentifierNode identifier = new IdentifierNode();
            identifier.parse(builder, this);

            identifiers.add(identifier);

            if (next_token == null) {
                builder.reportSyntaxError("Expected a token after identifier path, but got null");
            }

            Token.Type next_type = next_token.getType();

            if (next_type == Token.Type.SEMICOLON) {
                break;
            }

            if (next_type == Token.Type.PERIOD) {
                builder.consume();
                continue;
            }

            if (next_type == Token.Type.IDENTIFIER) {
                builder.reportSyntaxError("An identifier can not follow an identifier");
            }

            break;
        }

        this.identifiers = identifiers;
    }
}