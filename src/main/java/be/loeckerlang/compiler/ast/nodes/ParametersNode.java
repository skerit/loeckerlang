package be.loeckerlang.compiler.ast.nodes;

import be.loeckerlang.compiler.ast.ASTBuilder;
import be.loeckerlang.compiler.tokens.Token;

/**
 * The parameters definition of a method
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public class ParametersNode extends ASTNode {

    protected ListOfNodes<ParameterNode> parameters = null;

    /**
     * Parse the contents of this node
     *
     * @since    0.1.0
     */
    @Override
    protected void parseContents(ASTBuilder builder) {

        if (!builder.consume(Token.Type.LEFT_PAREN)) {
            builder.reportSyntaxError("Expected a left parenthesis");
            return;
        }

        this.parameters = ParameterNode.parseList(builder, this);
    }

    public int size() {

        if (this.parameters == null) {
            return 0;
        }

        return this.parameters.size();
    }

    /**
     * Get the parameters
     *
     * @since    0.1.0
     */
    public ListOfNodes<ParameterNode> getParameters() {
        return this.parameters;
    }

}
