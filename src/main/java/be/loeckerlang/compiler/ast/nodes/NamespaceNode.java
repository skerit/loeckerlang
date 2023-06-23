package be.loeckerlang.compiler.ast.nodes;

import be.loeckerlang.compiler.ast.ASTBuilder;
import be.loeckerlang.compiler.tokens.Token;

/**
 * The namespace node:
 * defines the name space the current file is in
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public class NamespaceNode extends ASTNode {

    private IdentifierPathNode path = null;

    /**
     * Parse a NamespaceNode at the current position
     *
     * @since    0.1.0
     */
    @Override
    protected void parseContents(ASTBuilder builder) {
        IdentifierPathNode path = new IdentifierPathNode();
        path.parse(builder, this);
        this.path = path;
    }

    /**
     * Should this start with a specific token?
     *
     * @since    0.1.0
     */
    protected Token.Type shouldStartWith() {
        return Token.Type.NAMESPACE;
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
