package be.loeckerlang.compiler.ast;

import be.loeckerlang.compiler.ast.nodes.MainNode;
import be.loeckerlang.compiler.tokens.Token;

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

    public ASTBuilder(List<Token> tokens) {
        this.tokens = tokens;
        this.position = 0;
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

        if (current == null) {
            return false;
        }

        return current.getType() == type;
    }

    /**
     * Get the current token
     *
     * @since    0.1.0
     */
    public Token peekCurrent() {
        if (this.position < tokens.size()) {
            return tokens.get(this.position);
        } else {
            return null;
        }
    }

    /**
     * Peek at the next token
     *
     * @since    0.1.0
     */
    public Token peekNext() {
        int next = this.position + 1;

        if (next < tokens.size()) {
            return tokens.get(next);
        } else {
            return null;
        }
    }

    /**
     * Peek at the previous token
     *
     * @since    0.1.0
     */
    public Token peekPrevious() {
        int previous = this.position - 1;

        if (previous >= 0) {
            return tokens.get(previous);
        } else {
            return null;
        }
    }

    /**
     * Get the next token
     *
     * @since    0.1.0
     */
    public Token getNext() {
        if (this.position < tokens.size()) {
            return tokens.get(this.position++);
        } else {
            return null;
        }
    }

    /**
     * Consume the current position and return it
     *
     * @since    0.1.0
     */
    public Token consume() {
        return this.getNext();
    }

    /**
     * Report a syntax error (by throwing an exception for now)
     *
     * @since    0.1.0
     */
    public void reportSyntaxError(String message) {
        throw new RuntimeException(message);
    }

    /**
     * Create the tree
     *
     * @since    0.1.0
     */
    public MainNode buildAST() {

        MainNode result = new MainNode();
        result.startParsing(this);

        return result;
    }

}
