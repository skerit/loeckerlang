package be.loeckerlang.compiler.ast.nodes;

import be.loeckerlang.compiler.ast.ASTBuilder;
import be.loeckerlang.compiler.tokens.Token;

/**
 * The Use Node class:
 * Require a certain package
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public class UseNode extends ASTNode {

    // The path of the package
    private IdentifierPathNode path = null;

    /**
     * Parse a UseNode at the current position
     *
     * @since    0.1.0
     */
    public void parseContents(ASTBuilder builder) {
        this.path = new IdentifierPathNode();
        this.path.parse(builder, this);
    }

    /**
     * Should this start with a specific token?
     *
     * @since    0.1.0
     */
    protected Token.Type shouldStartWith() {
        return Token.Type.USE;
    }

    /**
     * Should this end with a specific token?
     *
     * @since    0.1.0
     */
    @Override
    public Token.Type shouldEndWith() {
        return Token.Type.SEMICOLON;
    }
}
