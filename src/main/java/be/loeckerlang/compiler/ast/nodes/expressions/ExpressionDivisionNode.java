package be.loeckerlang.compiler.ast.nodes.expressions;

import be.loeckerlang.compiler.ast.nodes.ASTNode;
import be.loeckerlang.compiler.tokens.Token;

/**
 * A division expression
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public class ExpressionDivisionNode extends InfixExpressionNode {

    /**
     * Create the instance
     *
     * @since 0.1.0
     */
    public ExpressionDivisionNode(ASTNode parent, ExpressionNode left_operand, ExpressionNode right_operand) {
        super(parent, left_operand, right_operand, Token.Type.SLASH);
    }
}
