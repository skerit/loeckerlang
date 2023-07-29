package be.loeckerlang.compiler.ast.nodes;

import be.loeckerlang.compiler.tokens.Token;

/**
 * The Modifiers Node class
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public abstract class ModifiersNode extends GroupOfTokensNode {

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

    /**
     * Is this static?
     *
     * @since    0.1.0
     */
    public boolean isStatic() {
        return this.hasToken(Token.Type.STATIC);
    }

    /**
     * Is this immutable?
     *
     * @since    0.1.0
     */
    public boolean isImmutable() {
        return this.hasToken(Token.Type.IMMUTABLE);
    }

    /**
     * Is this memoized?
     *
     * @since    0.1.0
     */
    public boolean isMemoized() {
        return this.hasToken(Token.Type.MEMOIZED);
    }

}
