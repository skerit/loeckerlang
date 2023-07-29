package be.loeckerlang.compiler.ast.nodes;

import be.loeckerlang.compiler.ast.ASTBuilder;
import be.loeckerlang.compiler.tokens.Token;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents generics, the stuff between the angle brackets in a type:
 * `<Integer, String>`
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public class TypeGenericsNode extends ASTNode {

    // Generics contain a list of types
    protected List<SingleTypeNode> types = new ArrayList<>();

    /**
     * Parse the contents of this node
     *
     * @since    0.1.0
     */
    @Override
    protected void parseContents(ASTBuilder builder) {

        do {
            SingleTypeNode type = new SingleTypeNode();
            type.parse(builder, this);
            this.types.add(type);
        } while (builder.consume(Token.Type.COMMA));
    }

    /**
     * Should this start with a specific token?
     *
     * @since    0.1.0
     */
    @Override
    protected Token.Type shouldStartWith() {
        return Token.Type.LESS_THAN;
    }

    /**
     * Should this end with a specific token?
     *
     * @since    0.1.0
     */
    @Override
    protected Token.Type shouldEndWith() {
        return Token.Type.GREATER_THAN;
    }
}
