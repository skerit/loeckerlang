package be.loeckerlang.compiler.ast.nodes;

import be.loeckerlang.compiler.ast.ASTBuilder;
import be.loeckerlang.compiler.tokens.Token;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * The GroupOfTokens Node class
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public abstract class GroupOfTokensNode extends ASTNode {

    // All the matching tokens
    protected Collection<Token> matching_tokens = new ArrayList<>();

    /**
     * Return all the allowed tokens
     *
     * @since    0.1.0
     */
    public abstract Collection<Token.Type> getAllowedTokens();

    /**
     * Is the given token type found?
     *
     * @since    0.1.0
     */
    public boolean hasToken(Token.Type type) {
        for (Token token : this.matching_tokens) {
            if (token.getType() == type) {
                return true;
            }
        }

        return false;
    }

    /**
     * Parse the contents of this node
     *
     * @since    0.1.0
     */
    @Override
    protected void parseContents(ASTBuilder builder) {

        Collection<Token.Type> allowed_tokens = this.getAllowedTokens();

        if (allowed_tokens == null) {
            throw new NullPointerException("Allowed tokens can't be null");
        }

        while (builder.hasMore()) {
            Token token = builder.peekCurrent();
            Token.Type type = token.getType();

            if (allowed_tokens.contains(type)) {
                builder.consume();
                this.matching_tokens.add(token);
            } else {
                break;
            }
        }
    }

    /**
     * Does the builder contain the expected types?
     *
     * @since    0.1.0
     */
    public boolean isFoundAtStartOf(ASTBuilder builder) {

        Collection<Token.Type> allowed_tokens = this.getAllowedTokens();

        if (allowed_tokens == null) {
            throw new NullPointerException("Allowed tokens can't be null");
        }

        for (Token.Type type : allowed_tokens) {
            if (builder.hasCurrent(type)) {
                return true;
            }
        }

        return false;
    }
}
