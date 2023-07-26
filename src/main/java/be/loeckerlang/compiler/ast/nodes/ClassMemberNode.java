package be.loeckerlang.compiler.ast.nodes;

import be.loeckerlang.compiler.ast.ASTBuilder;

/**
 * Represents a member of a class
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public abstract class ClassMemberNode<T extends ModifiersNode> extends ASTNode {

    protected ListOfNodes<DecoratorNode> decorators = null;
    protected T modifiers = null;
    protected QualifiedNameNode type = null;
    protected SimpleNameNode name = null;

    /**
     * Parse the contents of this node
     *
     * @since    0.1.0
     */
    @Override
    protected void parseContents(ASTBuilder builder) {
        this.decorators = DecoratorNode.parseOptionalList(builder, this);
        this.modifiers = this.parseModifiers(builder);
    }

    /**
     * Parse the modifiers
     *
     * @since    0.1.0
     */
    protected abstract T parseModifiers(ASTBuilder builder);

    /**
     * Get the decorators
     *
     * @since    0.1.0
     */
    public ListOfNodes<DecoratorNode> getDecorators() {
        return this.decorators;
    }

    /**
     * Get the modifiers
     *
     * @since    0.1.0
     */
    public T getModifiers() {
        return this.modifiers;
    }

    /**
     * Get the type
     *
     * @since    0.1.0
     */
    public QualifiedNameNode getType() {
        return this.type;
    }

    /**
     * Get the name
     *
     * @since    0.1.0
     */
    public SimpleNameNode getName() {
        return this.name;
    }
}
