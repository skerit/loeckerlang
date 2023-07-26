package be.loeckerlang.compiler.ast.nodes;

import be.loeckerlang.compiler.ast.ASTBuilder;
import be.loeckerlang.compiler.ast.nodes.expressions.ExpressionNode;
import be.loeckerlang.compiler.tokens.Token;

/**
 * The FieldDeclaration Node class
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public class FieldDeclarationNode extends ClassMemberNode<FieldModifiersNode> {

    protected ExpressionNode initializer = null;

    /**
     * Should this end with a specific token?
     *
     * @since    0.1.0
     */
    protected Token.Type shouldEndWith() {
        return Token.Type.SEMICOLON;
    }

    /**
     * Parse the decorators
     *
     * @since    0.1.0
     */
    @Override
    protected FieldModifiersNode parseModifiers(ASTBuilder builder) {
        return FieldModifiersNode.parseOptional(builder, this);
    }

    /**
     * Parse the contents of this node
     *
     * @since    0.1.0
     */
    @Override
    protected void parseContents(ASTBuilder builder) {

        super.parseContents(builder);

        ASTNode parent = this;

        // Builtin properties have no type, but also can't be used by Loeckerlang code
        if (!this.getModifiers().isBuiltin()) {
            this.type = QualifiedNameNode.parseOptional(builder, parent);

            if (this.type == null) {
                builder.reportSyntaxError("Expected type");
                return;
            }
        }

        System.out.println("FieldDeclarationNode.parseContents: " + this.type);

        this.name = SimpleNameNode.parseOptional(builder, parent);

        if (this.name == null) {
            builder.reportSyntaxError("Expected a name");
            return;
        }

        Token token = builder.peekCurrent();
        Token.Type token_type = token.getType();

        // If there is a parenthesis, it's probably a function declaration
        if (token_type == Token.Type.LEFT_PAREN) {
            builder.reportSyntaxError("Unexpected parenthesis");
            return;
        }

        ExpressionNode value = null;

        // If there is a equal sign, it has a default value
        if (builder.consume(Token.Type.EQUALS)) {
            value = ExpressionNode.parseExpression(builder, parent);
        }

        FieldDeclarationNode result = this;
        result.type = type;
        result.initializer = value;
    }

    /**
     * Get the value
     *
     * @since    0.1.0
     */
    public ExpressionNode getInitializer() {
        return this.initializer;
    }

    /**
     * Look ahead and see if this is a valid FieldDeclarationNode
     *
     * @since    0.1.0
     */
    public static FieldDeclarationNode parseOptional(ASTBuilder builder, ASTNode parent) {

        FieldDeclarationNode result = new FieldDeclarationNode();

        if (result.doOptionalParse(builder, parent)) {
            return result;
        } else {
            return null;
        }
    }
}
