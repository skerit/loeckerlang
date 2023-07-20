package be.loeckerlang.compiler.ast.nodes;

import be.loeckerlang.compiler.ast.ASTBuilder;
import be.loeckerlang.compiler.tokens.Token;

import java.util.ArrayList;
import java.util.List;

/**
 * The Use Node class:
 * Require a certain package
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public class UseNode extends ASTNode {

    // The path of the package
    private QualifiedNameNode path = null;

    /**
     * Parse a UseNode at the current position
     *
     * @since    0.1.0
     */
    protected void parseContents(ASTBuilder builder) {
        this.path = new QualifiedNameNode();
        this.path.parse(builder, this);
    }

    /**
     * Get the path
     *
     * @since    0.1.0
     */
    public QualifiedNameNode getPath() {
        return this.path;
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

    /**
     * Parse optional decorators
     *
     * @since    0.1.0
     */
    public static ListOfNodes<UseNode> parseOptional(ASTBuilder builder, ASTNode parent) {

        Token current = builder.peekCurrent();
        Token.Type current_type = current.getType();

        if (current_type != Token.Type.USE) {
            return new ListOfNodes<>();
        }

        List<UseNode> result = new ArrayList<>();

        while (current_type == Token.Type.USE) {

            UseNode uses = new UseNode();
            uses.parse(builder, parent);

            result.add(uses);

            current = builder.peekCurrent();
            current_type = current.getType();
        }

        return new ListOfNodes<>(result);
    }
}
