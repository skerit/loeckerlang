package be.loeckerlang.compiler.ast.nodes.expressions;

import be.loeckerlang.compiler.ast.ASTBuilder;
import be.loeckerlang.compiler.ast.nodes.ASTNode;
import be.loeckerlang.compiler.tokens.Token;

/**
 * A literal node
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public class LiteralNode extends ExpressionNode {

    protected Token token_value;

    /**
     * Create a literal node
     *
     * @since    0.1.0
     *
     * @param    parent
     * @param    value
     */
    public LiteralNode(ASTNode parent, Token value) {
        this.setParent(parent);
        this.token_value = value;
        this.setFirstToken(value);
        this.setLastToken(value);
    }

    public Token getTokenValue() {
        return this.token_value;
    }

    @Override
    protected void parseContents(ASTBuilder builder) {
        builder.reportSyntaxError("Literals do not parse themselves");
    }
}
