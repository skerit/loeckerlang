package be.loeckerlang.compiler.ast.nodes.expressions;

import be.loeckerlang.compiler.ast.nodes.ASTNode;
import be.loeckerlang.compiler.tokens.Token;

/**
 * A multiplication expression
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public class ExpressionMultiplicationNode extends InfixExpressionNode {

    /**
     * Create the instance
     *
     * @since 0.1.0
     */
    public ExpressionMultiplicationNode(ASTNode parent, ExpressionNode left_operand, ExpressionNode right_operand) {
        super(parent, left_operand, right_operand, Token.Type.STAR);
    }

}
