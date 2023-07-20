package be.loeckerlang.compiler.ast.nodes.expressions;

import be.loeckerlang.compiler.ast.ASTBuilder;
import be.loeckerlang.compiler.ast.nodes.ASTNode;
import be.loeckerlang.compiler.ast.nodes.ArgumentsNode;
import be.loeckerlang.compiler.ast.nodes.SimpleNameNode;

/**
 * Representing a method/function call
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public class MethodInvocationNode extends ExpressionNode {

    // Optional expression
    protected ExpressionNode expression;

    // The name of the method
    protected SimpleNameNode name;

    // The arguments
    protected ArgumentsNode arguments;

    /**
     * Create a new MethodInvocationNode
     *
     * @param expression
     * @param name
     * @param arguments
     */
    public MethodInvocationNode(ASTNode parent, ExpressionNode expression, SimpleNameNode name, ArgumentsNode arguments) {
        this.setParent(parent);
        this.expression = expression;
        this.name = name;
        this.arguments = arguments;
    }

    @Override
    protected void parseContents(ASTBuilder builder) {
        builder.reportSyntaxError("MethodInvocationNode does not parse itself");
    }
}
