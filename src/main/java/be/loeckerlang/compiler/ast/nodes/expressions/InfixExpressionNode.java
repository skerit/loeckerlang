package be.loeckerlang.compiler.ast.nodes.expressions;

import be.loeckerlang.compiler.ast.ASTBuilder;
import be.loeckerlang.compiler.ast.nodes.ASTNode;
import be.loeckerlang.compiler.tokens.Token;

/**
 * The Infix Expression Node class:
 * Represents an infix expression
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public abstract class InfixExpressionNode extends ExpressionNode {

    protected ExpressionNode left_operand;
    protected ExpressionNode right_operand;
    protected Token.Type operator;

    /**
     * Create an infix expression node
     *
     * @since    0.1.0
     *
     * @param    parent
     * @param    left_operand
     * @param    right_operand
     * @param    operator
     */
    public InfixExpressionNode(ASTNode parent, ExpressionNode left_operand, ExpressionNode right_operand, Token.Type operator) {
        this.setParent(parent);
        this.left_operand = left_operand;
        this.right_operand = right_operand;
        this.operator = operator;

        this.setFirstToken(left_operand.getFirstToken());
        this.setLastToken(right_operand.getLastToken());
    }

    /**
     * Parse the contents
     *
     * @since    0.1.0
     */
    @Override
    protected void parseContents(ASTBuilder builder) {
        builder.reportSyntaxError("Infix expressions do not parse themselves");
    }
}
