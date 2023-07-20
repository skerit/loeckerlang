package be.loeckerlang.compiler.ast.nodes.expressions;

import be.loeckerlang.compiler.ast.nodes.ASTNode;
import be.loeckerlang.compiler.tokens.Token;

/**
 * An integer node
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public class LiteralIntegerNode extends LiteralNode {

    /**
     * Create a literal node
     * @since 0.1.0
     */
    public LiteralIntegerNode(ASTNode parent, Token value) {
        super(parent, value);
    }
}
