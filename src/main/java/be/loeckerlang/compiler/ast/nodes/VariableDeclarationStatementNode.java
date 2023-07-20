package be.loeckerlang.compiler.ast.nodes;

import be.loeckerlang.compiler.ast.ASTBuilder;
import be.loeckerlang.compiler.ast.nodes.expressions.ExpressionNode;
import be.loeckerlang.compiler.tokens.Token;

/**
 * A variable declaration statement
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public class VariableDeclarationStatementNode extends StatementNode {

    protected QualifiedNameNode type = null;
    protected SimpleNameNode name = null;
    protected ExpressionNode initializer = null;

    /**
     * Parse the contents of this node
     *
     * @since    0.1.0
     */
    @Override
    protected void parseContents(ASTBuilder builder) {

        this.type = QualifiedNameNode.parseOptional(builder, this);

        if (this.type == null) {
            builder.reportSyntaxError("Expected type");
            return;
        }

        this.name = SimpleNameNode.parseOptional(builder, this);

        if (this.name == null) {
            builder.reportSyntaxError("Expected a name");
            return;
        }

        if (builder.currentTokensAre(Token.Type.SEMICOLON)) {
            return;
        }

        if (!builder.consume(Token.Type.EQUALS)) {
            builder.reportSyntaxError("Expected an equals sign");
            return;
        }

        this.initializer = ExpressionNode.parseExpression(builder, this);
    }

    /**
     * Should this end with a specific token?
     *
     * @since    0.1.0
     */
    protected Token.Type shouldEndWith() {
        return Token.Type.SEMICOLON;
    }
}
