package be.loeckerlang.compiler.ast.nodes;

import be.loeckerlang.compiler.ast.ASTBuilder;
import be.loeckerlang.compiler.tokens.Token;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * The Class Node class
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public class ClassNode extends ASTNode {

    // The decorators of this class
    private ListOfNodes<DecoratorNode> decorators;

    // The modifiers of the class
    private ClassModifiersNode modifiers;

    // The name of the class
    private SimpleNameNode name = null;

    // Field declarations
    private ListOfNodes<FieldDeclarationNode> fields;

    // Method declarations
    private ListOfNodes<MethodDeclarationNode> methods;

    // Ancestors
    private ClassAncestors ancestors = null;

    /**
     * Create a new instance
     *
     * @since    0.1.0
     */
    public ClassNode(ListOfNodes<DecoratorNode> decorators, ClassModifiersNode modifiers) {
        this.decorators = decorators;
        this.modifiers = modifiers;
    }

    /**
     * Parse a ClassNode at the current position
     *
     * @since    0.1.0
     */
    @Override
    protected void parseContents(ASTBuilder builder) {

        if (!builder.consume(Token.Type.CLASS)) {
            builder.reportSyntaxError("Expected class keyword");
            return;
        }

        // Parse the class name
        this.name = new SimpleNameNode();
        this.name.parse(builder, this);

        if (builder.consume(Token.Type.INHERITS)) {
            // Consume ancestors!
            this.ancestors = new ClassAncestors();
            this.ancestors.parse(builder, this);
        }

        if (!builder.consume(Token.Type.LEFT_BRACE)) {
            builder.reportSyntaxError("Expected opening brace");
            return;
        }

        this.fields = new ListOfNodes<>();
        this.methods = new ListOfNodes<>();

        // Parse the body
        while (builder.hasMore() && builder.peekCurrent().getType() != Token.Type.RIGHT_BRACE) {
            FieldDeclarationNode field = FieldDeclarationNode.parseOptional(builder, this);

            if (field != null) {
                this.fields.add(field);
                continue;
            }

            MethodDeclarationNode method = MethodDeclarationNode.parseOptional(builder, this);

            if (method != null) {
                this.methods.add(method);
                continue;
            }

            builder.reportSyntaxError("Expected field or method declaration");

        }
    }

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
    public ClassModifiersNode getModifiers() {
        return this.modifiers;
    }

    /**
     * Get the name
     *
     * @since    0.1.0
     */
    public SimpleNameNode getName() {
        return this.name;
    }

    /**
     * Get the fields
     *
     * @since    0.1.0
     */
    public ListOfNodes<FieldDeclarationNode> getFields() {
        return this.fields;
    }

    /**
     * Get the methods
     *
     * @since    0.1.0
     */
    public ListOfNodes<MethodDeclarationNode> getMethods() {
        return this.methods;
    }

    /**
     * Parse optional decorators
     *
     * @since    0.1.0
     */
    @NonNull
    public static ClassNode parseOptional(ASTBuilder builder, ASTNode parent) {

        ListOfNodes<DecoratorNode> decorators = DecoratorNode.parseOptionalList(builder, parent);
        ClassModifiersNode modifiers = ClassModifiersNode.parseOptional(builder, parent);

        ClassNode result = new ClassNode(decorators, modifiers);
        result.parse(builder, parent);
        return result;
    }
}
