package be.loeckerlang.compiler.ast.nodes;

import be.loeckerlang.compiler.ast.ASTBuilder;

/**
 * Base modifiers used by most things that have modifiers
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public abstract class BaseModifiersNode extends ModifiersNode {

    /**
     * Parse the contents of this node
     *
     * @since    0.1.0
     */
    @Override
    protected void parseContents(ASTBuilder builder) {
        super.parseContents(builder);

        if (this.isPrivate() && this.isPublic()) {
            builder.reportSyntaxError("Class can't be both private and public");
        }

        if (this.isPrivate() && this.isProtected()) {
            builder.reportSyntaxError("Class can't be both private and protected");
        }

        if (this.isPublic() && this.isProtected()) {
            builder.reportSyntaxError("Class can't be both public and protected");
        }

        if (this.isAbstract() && this.isFinal()) {
            builder.reportSyntaxError("Class can't be both abstract and final");
        }
    }



}
