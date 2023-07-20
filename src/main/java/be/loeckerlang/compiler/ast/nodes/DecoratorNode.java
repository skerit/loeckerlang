package be.loeckerlang.compiler.ast.nodes;

import be.loeckerlang.compiler.ast.ASTBuilder;
import be.loeckerlang.compiler.tokens.Token;

import java.util.ArrayList;
import java.util.List;

/**
 * The Decorator Node class:
 * Represents a decorator on a class or method
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public class DecoratorNode extends ASTNode {

    // The path to the decorator
    private QualifiedNameNode path = null;

    // The (optional) arguments
    private ArgumentsNode arguments = null;


    /**
     * Parse an IdentifierNode at the current position
     *
     * @since    0.1.0
     */
    protected void parseContents(ASTBuilder builder) {

        Token token = builder.peekCurrent();

        if (!builder.consume(Token.Type.AT)) {
            builder.reportSyntaxError("Expected @");
            return;
        }

        if (!builder.consume(Token.Type.IDENTIFIER)) {
            builder.reportSyntaxError("Expected identifier");
            return;
        }

        this.path = new QualifiedNameNode();
        this.path.parse(builder, this);

        this.arguments = ArgumentsNode.parseOptional(builder, this);

        token = builder.peekCurrent();
        Token.Type type = token.getType();

        // See if this decorator has arguments
        if (type == Token.Type.LEFT_PAREN) {

        }
    }

    /**
     * Should this start with a specific token?
     *
     * @since    0.1.0
     */
    @Override
    protected Token.Type shouldStartWith() {
        return Token.Type.AT;
    }

    /**
     * Parse optional decorators
     *
     * @since    0.1.0
     */
    public static ListOfNodes<DecoratorNode> parseOptionalList(ASTBuilder builder, ASTNode parent) {

        Token current = builder.peekCurrent();
        Token.Type current_type = current.getType();

        if (current_type != Token.Type.AT) {
            return new ListOfNodes<>();
        }

        List<DecoratorNode> result = new ArrayList<>();

        while (current_type == Token.Type.AT) {

            DecoratorNode decorator = new DecoratorNode();
            decorator.parse(builder, parent);

            result.add(decorator);

            current = builder.peekCurrent();
            current_type = current.getType();
        }

        return new ListOfNodes<>(result);
    }
}
