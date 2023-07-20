package be.loeckerlang.compiler.ast;

import be.loeckerlang.compiler.ast.nodes.FileNode;
import be.loeckerlang.compiler.tokens.Token;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

/**
 * Turn the tokens into an AST
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public class ASTBuilder {

    private List<Token> tokens;
    private int position;
    private boolean throw_errors = true;
    private boolean has_error = false;

    public ASTBuilder(List<Token> tokens) {
        this.tokens = tokens;
        this.position = 0;
    }

    /**
     * Get a shallow copy
     *
     * @since    0.1.0
     */
    public ASTBuilder copy() {
        ASTBuilder result = new ASTBuilder(this.tokens);
        result.position = this.position;
        return result;
    }

    /**
     * Get a test copy
     *
     * @since    0.1.0
     */
    public ASTBuilder copyForTesting() {
        ASTBuilder result = this.copy();
        result.throw_errors = false;
        return result;
    }

    /**
     * Adopt the given builder
     *
     * @since    0.1.0
     */
    public void adopt(ASTBuilder other) {
        this.position = other.position;
    }

    /**
     * Are there more tokens?
     *
     * @since    0.1.0
     */
    public boolean hasMore() {
        return this.position < tokens.size();
    }

    /**
     * Does this builder have the given type as the current token?
     *
     * @since    0.1.0
     */
    public boolean hasCurrent(Token.Type type) {

        Token current = this.peekCurrent();

        if (current == Token.EMPTY || current == Token.EOF) {
            return false;
        }

        return current.getType() == type;
    }

    /**
     * Get the current token
     *
     * @since    0.1.0
     */
    @NonNull
    public Token peekCurrent() {
        if (this.position < tokens.size()) {
            return tokens.get(this.position);
        } else {
            return Token.EOF;
        }
    }

    /**
     * Peek at the next token
     *
     * @since    0.1.0
     */
    @NonNull
    public Token peekNext() {
        int next = this.position + 1;

        if (next < tokens.size()) {
            return tokens.get(next);
        } else {
            return Token.EOF;
        }
    }

    /**
     * Peek at the previous token
     *
     * @since    0.1.0
     */
    @NonNull
    public Token peekPrevious() {
        int previous = this.position - 1;

        if (previous >= 0) {
            return tokens.get(previous);
        } else {
            return Token.EMPTY;
        }
    }

    /**
     * See if the current/next tokens are of the given types
     *
     * @since    0.1.0
     */
    public boolean currentTokensAre(Token.Type... types) {

        int next = this.position;

        for (Token.Type type : types) {
            if (tokens.get(next).getType() != type) {
                return false;
            }
            next++;
        }

        return true;
    }

    /**
     * Get the next token
     *
     * @since    0.1.0
     */
    @NonNull
    public Token getCurrentAndConsume() {
        if (this.position < tokens.size()) {
            return tokens.get(this.position++);
        } else {
            return Token.EOF;
        }
    }

    /**
     * Consume the current position and return it
     *
     * @since    0.1.0
     */
    @NonNull
    public Token consume() {
        return this.getCurrentAndConsume();
    }

    /**
     * Consume the given token
     *
     * @since    0.1.0
     */
    public boolean consume(Token.Type... types) {

        if (!this.currentTokensAre(types)) {
            return false;
        }

        for (int i = 0; i < types.length; i++) {
            this.consume();
        }

        return true;
    }

    /**
     * Does this builder have errors?
     *
     * @since    0.1.0
     */
    public boolean hasErrors() {
        return this.has_error;
    }

    /**
     * Report a syntax error (by throwing an exception for now)
     *
     * @since    0.1.0
     */
    public void reportSyntaxError(String message) {

        this.has_error = true;

        System.out.println(" --TEST-- Syntax error: " + message);
        this.printErrorTraceHere();

        if (this.throw_errors) {
            throw new RuntimeException(message);
        }
    }

    /**
     * Print an error trace at the current position.
     * This print the previous 3 and next 3 lines,
     * and marks the current line.
     *
     * @since    0.1.0
     */
    private void printErrorTraceHere() {

        int start = this.position - 3;
        int end = this.position + 3;

        if (start < 0) {
            start = 0;
        }

        if (end > this.tokens.size()) {
            end = this.tokens.size();
        }

        for (int i = start; i < end; i++) {
            Token token = this.tokens.get(i);

            if (i == this.position) {
                System.out.println("  >> " + token);
            } else {
                System.out.println("     " + token);
            }
        }

    }

    /**
     * Create the tree
     *
     * @since    0.1.0
     */
    public FileNode buildAST() {

        FileNode result = new FileNode();
        result.startParsing(this);

        return result;
    }

}
