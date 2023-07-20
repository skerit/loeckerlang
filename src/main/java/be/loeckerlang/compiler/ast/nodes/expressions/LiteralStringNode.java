package be.loeckerlang.compiler.ast.nodes.expressions;

import be.loeckerlang.compiler.ast.nodes.ASTNode;
import be.loeckerlang.compiler.tokens.Token;

/**
 * A string node
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public class LiteralStringNode extends LiteralNode {

    /**
     * Create a literal node
     * @since 0.1.0
     */
    public LiteralStringNode(ASTNode parent, Token value) {
        super(parent, value);
    }
}
