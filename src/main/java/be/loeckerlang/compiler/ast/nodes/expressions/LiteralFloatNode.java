package be.loeckerlang.compiler.ast.nodes.expressions;

import be.loeckerlang.compiler.ast.nodes.ASTNode;
import be.loeckerlang.compiler.tokens.Token;

/**
 * A float node
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public class LiteralFloatNode extends LiteralNode {

    /**
     * Create a literal node
     * @since 0.1.0
     */
    public LiteralFloatNode(ASTNode parent, Token value) {
        super(parent, value);
    }
}
