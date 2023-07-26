package be.loeckerlang.compiler.ast.nodes;

import be.loeckerlang.compiler.ast.ASTBuilder;
import be.loeckerlang.compiler.ast.ASTVisitor;
import be.loeckerlang.compiler.tokens.Token;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Map;

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
     * Set the parent
     *
     * @since    0.1.0
     */
    protected void setParent(ASTNode parent) {
        this.parent = parent;
    }

    /**
     * Get the parent
     *
     * @since    0.1.0
     */
    @Nullable
    public ASTNode getParent() {
        return this.parent;
    }

    /**
     * Let them parse themselves optionally
     *
     * @since    0.1.0
     */
    protected boolean doOptionalParse(ASTBuilder builder, ASTNode parent) {

        // Create a copy of the builder
        ASTBuilder look_ahead = builder.copyForTesting();

        // Parse the contents
        this.parse(look_ahead, parent);

        // If it failed, return false
        if (look_ahead.hasErrors()) {
            return false;
        }

        builder.adopt(look_ahead);

        return true;
    }

    /**
     * Let them parse themselves
     *
     * @since    0.1.0
     */
    protected void parse(ASTBuilder builder, ASTNode parent) {

        // Set the parent
        this.setParent(parent);

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

    /**
     * Apply the visitor
     *
     * @since    0.1.0
     */
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Report a compilation error
     *
     * @since    0.1.0
     */
    public void reportCompilationError(String message) {
        throw new RuntimeException("Compilation error: " + message);
    }

    /**
     * Return a string representation of this node
     *
     * @since    0.1.0
     */
    @Override
    public String toString() {

        String result = this.getClass().getSimpleName();

        result += "{first_token=" + this.first_token + ", last_token=" + this.last_token + "}";

        return result;
    }
}
