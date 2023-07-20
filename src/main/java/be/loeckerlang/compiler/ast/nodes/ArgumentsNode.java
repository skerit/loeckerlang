package be.loeckerlang.compiler.ast.nodes;

import be.loeckerlang.compiler.ast.ASTBuilder;
import be.loeckerlang.compiler.tokens.Token;

/**
 * The arguments passed to a function call
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public class ArgumentsNode extends ASTNode {

    private ListOfNodes<SingleArgumentNode> arguments;

    /**
     * Parse the contents
     *
     * @since    0.1.0
     */
    @Override
    protected void parseContents(ASTBuilder builder) {
        this.arguments = new ListOfNodes<>();

        do {
            SingleArgumentNode argument = new SingleArgumentNode();
            argument.parse(builder, this);

            this.arguments.add(argument);

            Token current = builder.peekCurrent();
            Token.Type current_type = current.getType();

            if (current_type == Token.Type.COMMA) {
                builder.consume();
            } else {
                break;
            }
        } while(true);
    }

    /**
     * Should this start with a specific token?
     *
     * @since    0.1.0
     */
    @Override
    protected Token.Type shouldStartWith() {
        return Token.Type.LEFT_PAREN;
    }

    /**
     * Should this end with a specific token?
     *
     * @since    0.1.0
     */
    @Override
    protected Token.Type shouldEndWith() {
        return Token.Type.RIGHT_PAREN;
    }

    /**
     * Parse optional arguments
     *
     * @since    0.1.0
     */
    public static ArgumentsNode parseOptional(ASTBuilder builder, ASTNode parent) {

        ArgumentsNode result = new ArgumentsNode();

        Token current = builder.peekCurrent();
        Token.Type current_type = current.getType();

        if (current_type != Token.Type.LEFT_PAREN) {
            return result;
        }

        result.parse(builder, parent);

        return result;
    }
}
