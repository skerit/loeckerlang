package be.loeckerlang.compiler.ast.nodes.expressions;

import be.loeckerlang.compiler.ast.ASTBuilder;
import be.loeckerlang.compiler.ast.nodes.ASTNode;
import be.loeckerlang.compiler.ast.nodes.ArgumentsNode;
import be.loeckerlang.compiler.ast.nodes.QualifiedNameNode;
import be.loeckerlang.compiler.ast.nodes.SimpleNameNode;
import be.loeckerlang.compiler.tokens.Token;

/**
 * An expression node
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public abstract class ExpressionNode extends ASTNode {

    /**
     * Actually parse an expression
     *
     * @since    0.1.0
     */
    public static ExpressionNode parseExpression(ASTBuilder builder, ASTNode parent) {
        return ExpressionNode.parseAdditionSubtraction(builder, parent);
    }

    /**
     * Parse additions & subtractions
     *
     * @since    0.1.0
     */
    private static ExpressionNode parseAdditionSubtraction(ASTBuilder builder, ASTNode parent) {
        ExpressionNode left_operand = parseMultiplicationDivision(builder, parent);

        while (builder.hasMore()) {
            Token token = builder.peekCurrent();

            if (token.getType() == Token.Type.PLUS) {
                builder.consume();
                ExpressionNode right_operand = parseMultiplicationDivision(builder, parent);
                left_operand = new ExpressionAdditionNode(parent, left_operand, right_operand);
            } else if (token.getType() == Token.Type.MINUS) {
                builder.consume();
                ExpressionNode right_operand = parseMultiplicationDivision(builder, parent);
                left_operand = new ExpressionSubtractionNode(parent, left_operand, right_operand);
            } else {
                break;
            }
        }

        return left_operand;
    }

    /**
     * Parse multiplications & divisions
     *
     * @since    0.1.0
     */
    private static ExpressionNode parseMultiplicationDivision(ASTBuilder builder, ASTNode parent) {
        ExpressionNode left_operand = parseExponentiation(builder, parent);

        while (builder.hasMore()) {

            if (builder.consume(Token.Type.STAR)) {
                ExpressionNode right_operand = parseExponentiation(builder, parent);
                left_operand = new ExpressionMultiplicationNode(parent, left_operand, right_operand);
            } else if (builder.consume(Token.Type.SLASH)) {
                ExpressionNode right_operand = parseExponentiation(builder, parent);
                left_operand = new ExpressionDivisionNode(parent, left_operand, right_operand);
            } else {
                break;
            }
        }

        return left_operand;
    }

    /**
     * Parse exponentiation
     * (we actually don't have this operator yet)
     *
     * @since    0.1.0
     */
    private static ExpressionNode parseExponentiation(ASTBuilder builder, ASTNode parent) {
        return parseFunctionCall(builder, parent);
    }

    /**
     * Parse function calls
     *
     * @since    0.1.0
     */
    private static ExpressionNode parseFunctionCall(ASTBuilder builder, ASTNode parent) {
        ExpressionNode left = parentheseAwareParse(builder, parent);

        if (builder.consume(Token.Type.LEFT_PAREN)) {

            ExpressionNode expression = null;
            SimpleNameNode name = null;

            // A parentheses was found, so this is a function call
            if (left instanceof QualifiedNameNode qnn) {
                name = qnn.pop();
                expression = qnn;
            } else if (left instanceof SimpleNameNode snn) {
                name = snn;
            } else {
                builder.reportSyntaxError("Invalid function call");
            }

            ArgumentsNode args = null;

            // TODO

            return new MethodInvocationNode(parent, expression, name, args);
        }

        return left;
    }

    /**
     * Parse parentheses
     *
     * @since    0.1.0
     */
    private static ExpressionNode parentheseAwareParse(ASTBuilder builder, ASTNode parent) {

        Token current = builder.peekCurrent();

        if (builder.consume(Token.Type.INTEGER_LITERAL)) {
            return new LiteralIntegerNode(parent, current);
        }

        if (builder.consume(Token.Type.FLOAT_LITERAL)) {
            return new LiteralFloatNode(parent, current);
        }

        if (builder.consume(Token.Type.STRING_LITERAL)) {
            return new LiteralStringNode(parent, current);
        }

        if (current.getType() == Token.Type.IDENTIFIER) {
            QualifiedNameNode qnn = new QualifiedNameNode();
            qnn.parse(builder, parent);
            return qnn;
        }

        if (builder.consume(Token.Type.LEFT_PAREN)) {
            ExpressionNode expression = parseAdditionSubtraction(builder, parent);

            if (builder.consume(Token.Type.RIGHT_PAREN)) {
                return expression;
            } else {
                builder.reportSyntaxError("Mismatched parentheses");
            }
        }

        builder.reportSyntaxError("Invalid expression");

        return null;
    }

}
