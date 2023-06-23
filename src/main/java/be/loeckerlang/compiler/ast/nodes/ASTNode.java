package be.loeckerlang.compiler.ast.nodes;

import be.loeckerlang.compiler.ast.ASTBuilder;
import be.loeckerlang.compiler.ast.ASTVisitor;
import be.loeckerlang.compiler.tokens.Token;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * The basic Node class
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public abstract class ASTNode {

    // The first token
    private Token first_token = null;

    // The last token
    private Token last_token = null;

    // The parent node (if any)
    private ASTNode parent = null;

    /**
     * Get the first token
     *
     * @since    0.1.0
     */
    public Token getFirstToken() {
        return this.first_token;
    }

    /**
     * Set the first token
     *
     * @since    0.1.0
     */
    protected void setFirstToken(Token token) {
        this.first_token = token;
    }

    /**
     * Get the last token
     *
     * @since    0.1.0
     */
    @Nullable
    public Token getLastToken() {
        return this.last_token;
    }

    /**
     * Set the last token
     *
     * @since    0.1.0
     */
    protected void setLastToken(Token token) {
        this.last_token = token;
    }

    /**
     * Let them parse themselves
     *
     * @since    0.1.0
     */
    protected void parse(ASTBuilder builder, ASTNode parent) {

        // Set the parent
        this.parent = parent;

        // Set the first token
        this.setFirstToken(builder.peekCurrent());

        // Do we expect a specific start token?
        Token.Type start_type = this.shouldStartWith();

        if (start_type != null) {
            Token start_token = builder.consume();

            if (start_token == null) {
                builder.reportSyntaxError("Expected token of type " + start_token + " at the start of " + this.getClass().getSimpleName() + ", but got null");
            }

            if (start_token.getType() != start_type) {
                builder.reportSyntaxError("Expected " + start_type + " at the start of " + this.getClass().getSimpleName() + ", but got " + start_token.getType());
            }
        }

        // Let them parse themselves
        this.parseContents(builder);

        // Do we expect a specific end token?
        Token.Type end_type = this.shouldEndWith();

        if (end_type != null) {
            Token end_token = builder.consume();

            if (end_token == null) {
                builder.reportSyntaxError("Expected token of type " + end_type + " after " + this.getClass().getSimpleName() + ", but got null");
            }

            if (end_token.getType() != end_type) {
                builder.reportSyntaxError("Expected " + end_type + " after " + this.getClass().getSimpleName() + ", but got " + end_token.getType());
            }
        }

        // Set the last token
        this.setLastToken(builder.peekPrevious());
    }

    /**
     * Should this start with a specific token?
     *
     * @since    0.1.0
     */
    protected Token.Type shouldStartWith() {
        return null;
    }

    /**
     * Should this end with a specific token?
     *
     * @since    0.1.0
     */
    protected Token.Type shouldEndWith() {
        return null;
    }

    /**
     * Let them parse themselves
     *
     * @since    0.1.0
     */
    protected abstract void parseContents(ASTBuilder builder);

    public void accept(ASTVisitor visitor) {

    }

}