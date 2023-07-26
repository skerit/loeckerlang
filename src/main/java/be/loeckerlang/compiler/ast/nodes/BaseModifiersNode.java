package be.loeckerlang.compiler.ast.nodes;

import be.loeckerlang.compiler.ast.ASTBuilder;
import be.loeckerlang.compiler.tokens.Token;

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

    /**
     * Should this be private?
     *
     * @since    0.1.0
     */
    public boolean isPrivate() {
        return this.hasToken(Token.Type.PRIVATE);
    }

    /**
     * Should this be protected?
     *
     * @since    0.1.0
     */
    public boolean isProtected() {
        return this.hasToken(Token.Type.PROTECTED);
    }

    /**
     * Should this be public?
     *
     * @since    0.1.0
     */
    public boolean isPublic() {
        return this.hasToken(Token.Type.PUBLIC);
    }

    /**
     * Should this be abstract?
     *
     * @since    0.1.0
     */
    public boolean isAbstract() {
        return this.hasToken(Token.Type.ABSTRACT);
    }

    /**
     * Should this be final?
     *
     * @since    0.1.0
     */
    public boolean isFinal() {
        return this.hasToken(Token.Type.FINAL);
    }

    /**
     * Is this builtin?
     *
     * @since    0.1.0
     */
    public boolean isBuiltin() {
        return this.hasToken(Token.Type.BUILTIN);
    }

}
