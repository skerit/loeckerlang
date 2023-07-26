package be.loeckerlang.compiler.ast.nodes;

import be.loeckerlang.compiler.ast.ASTBuilder;
import be.loeckerlang.compiler.tokens.Token;

/**
 * The MethodDeclaration Node class:
 * Represents a method declaration in a class
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public class MethodDeclarationNode extends ClassMemberNode<FunctionModifiersNode> {

    protected ParametersNode parameters = null;
    protected BlockNode body = null;

    /**
     * Should this end with a specific token?
     *
     * @since    0.1.0
     */
    protected Token.Type shouldEndWith() {
        return Token.Type.RIGHT_BRACE;
    }

    /**
     * Get the parameters
     *
     * @since    0.1.0
     */
    public ParametersNode getParameters() {
        return this.parameters;
    }

    /**
     * Get the body
     *
     * @since    0.1.0
     */
    public BlockNode getBody() {
        return this.body;
    }

    /**
     * Parse the decorators
     *
     * @since    0.1.0
     */
    @Override
    protected FunctionModifiersNode parseModifiers(ASTBuilder builder) {
        return FunctionModifiersNode.parseOptional(builder, this);
    }

    /**
     * Parse the contents of this node
     *
     * @since    0.1.0
     */
    @Override
    protected void parseContents(ASTBuilder builder) {

        super.parseContents(builder);

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

        this.parameters = new ParametersNode();
        this.parameters.parse(builder, this);

        if (builder.peekCurrent().getType() == Token.Type.LEFT_BRACE) {
            this.body = new BlockNode();
            this.body.parse(builder, this);
        } else {
            builder.reportSyntaxError("Expected a body");
        }
    }

    /**
     * Look ahead and see if this is a valid FieldDeclarationNode
     *
     * @since    0.1.0
     */
    public static MethodDeclarationNode parseOptional(ASTBuilder builder, ASTNode parent) {

        MethodDeclarationNode result = new MethodDeclarationNode();

        if (result.doOptionalParse(builder, parent)) {
            return result;
        } else {
            return null;
        }
    }
}
