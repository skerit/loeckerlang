package be.loeckerlang.compiler.ast.nodes.expressions;

import be.loeckerlang.compiler.ast.nodes.ASTNode;
import be.loeckerlang.compiler.tokens.Token;

/**
 * An addition expression
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public class ExpressionAdditionNode extends InfixExpressionNode {

    /**
     * Create an infix expression node
     *
     * @since 0.1.0
     */
    public ExpressionAdditionNode(ASTNode parent, ExpressionNode left_operand, ExpressionNode right_operand) {
        super(parent, left_operand, right_operand, Token.Type.PLUS);
    }
}
