package be.loeckerlang.compiler.ast.nodes;

import be.loeckerlang.compiler.ast.ASTBuilder;

/**
 * The Class Node class
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public class ClassNode extends ASTNode {

    // The modifiers of the class
    private ClassModifiersNode modifiers = null;

    // The name of the class
    private IdentifierNode name = null;

    /**
     * Parse a ClassNode at the current position
     *
     * @since    0.1.0
     */
    @Override
    public void parseContents(ASTBuilder builder) {

        // Parse the optional modifiers
        this.modifiers = new ClassModifiersNode();
        this.modifiers.parse(builder, this);

        // Parse the class name
        this.name = new IdentifierNode();
        this.name.parse(builder, this);


    }
}
