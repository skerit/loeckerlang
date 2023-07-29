package be.loeckerlang.compiler.ast.nodes;

import be.loeckerlang.compiler.ast.ASTBuilder;
import be.loeckerlang.compiler.tokens.Token;

/**
 * A single parameter definition
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public class ParameterNode extends ASTNode {

    protected SingleTypeNode type = null;
    protected SimpleNameNode name = null;

    /**
     * Get the type
     *
     * @since    0.1.0
     */
    public SingleTypeNode getType() {
        return this.type;
    }

    /**
     * Get the name
     *
     * @since    0.1.0
     */
    public SimpleNameNode getName() {
        return this.name;
    }

    /**
     * Parse the contents of this node
     *
     * @since    0.1.0
     */
    @Override
    protected void parseContents(ASTBuilder builder) {

        this.type = SingleTypeNode.parseOptional(builder, this);

        if (this.type == null) {
            builder.reportSyntaxError("Expected type");
            return;
        }

        this.name = SimpleNameNode.parseOptional(builder, this);

        if (this.name == null) {
            builder.reportSyntaxError("Expected a name");
            return;
        }
    }

    /**
     * Parse all the parameters
     *
     * @since    0.1.0
     */
    public static ListOfNodes<ParameterNode> parseList(ASTBuilder builder, ASTNode parent) {

        ListOfNodes<ParameterNode> result = new ListOfNodes<>();

        if (builder.consume(Token.Type.RIGHT_PAREN)) {
            return result;
        }

        do {
            ParameterNode param = new ParameterNode();
            param.parse(builder, parent);
        } while (builder.currentTokensAre(Token.Type.COMMA));

        if (!builder.consume(Token.Type.RIGHT_PAREN)) {
            builder.reportSyntaxError("Expected a closing parenthesis");
        }

        return result;
    }
}
