package be.loeckerlang.compiler.intermediate.expressions;

import be.loeckerlang.compiler.ast.nodes.expressions.ExpressionNode;

public abstract class Expression {

    private final ExpressionNode ast_node;
    private boolean is_async = false;

    public Expression(ExpressionNode node) {
        this.ast_node = node;
    }

    public ExpressionNode getAstNode() {
        return this.ast_node;
    }

    public boolean isAsync() {
        return this.is_async;
    }

    public void setAsync(boolean value) {
        this.is_async = value;
    }
}
