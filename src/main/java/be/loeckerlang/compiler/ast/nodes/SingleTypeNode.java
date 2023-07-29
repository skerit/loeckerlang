package be.loeckerlang.compiler.ast.nodes;

import be.loeckerlang.compiler.ast.ASTBuilder;
import be.loeckerlang.compiler.tokens.Token;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * A single type node, like:
 * `Integer`, or with generics: `List<String>`.
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public class SingleTypeNode extends ASTNode {

    // The name of the type. Probably refers to a class
    protected QualifiedNameNode type_name;

    // The generic arguments
    protected TypeGenericsNode generics = null;

    /**
     * Parse the contents of this node
     *
     * @since    0.1.0
     */
    @Override
    protected void parseContents(ASTBuilder builder) {

        this.type_name = QualifiedNameNode.parseOptional(builder, this);

        if (this.type_name == null) {
            builder.reportSyntaxError("Expected a type name");
            return;
        }

        if (builder.hasCurrent(Token.Type.LESS_THAN)) {
            this.generics = new TypeGenericsNode();
            this.generics.parse(builder, this);
        }
	}

    /**
     * Get the type
     *
     * @since    0.1.0
     */
    public QualifiedNameNode getTypeName() {
        return this.type_name;
    }

    /**
     * Get the generics
     *
     * @since    0.1.0
     */
    @Nullable
    public TypeGenericsNode getGenerics() {
        return this.generics;
    }

    /**
     * Parse an optional
     *
     * @since    0.1.0
     */
    public static SingleTypeNode parseOptional(ASTBuilder builder, ASTNode parent) {

        Token current = builder.peekCurrent();

        if (current.getType() != Token.Type.IDENTIFIER) {
            return null;
        }

        SingleTypeNode result = new SingleTypeNode();
        result.parse(builder, parent);

        return result;
    }
}
