package be.loeckerlang.compiler.ast.nodes;

import be.loeckerlang.compiler.ast.ASTBuilder;
import be.loeckerlang.compiler.tokens.Token;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Collection;
import java.util.List;

/**
 * All the modifiers a class can have
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public class ClassModifiersNode extends BaseModifiersNode {

    private static final List<Token.Type> CLASS_MODIFIER_TOKENS = List.of(
        Token.Type.PRIVATE,
        Token.Type.PROTECTED,
        Token.Type.PUBLIC,
        Token.Type.ABSTRACT,
        Token.Type.FINAL,
        Token.Type.STATIC,
        Token.Type.IMMUTABLE
    );

    /**
     * Return all the allowed tokens
     *
     * @since    0.1.0
     */
    public Collection<Token.Type> getAllowedTokens() {
        return CLASS_MODIFIER_TOKENS;
    }

    /**
     * Parse optional decorators
     *
     * @since    0.1.0
     */
    @NonNull
    public static ClassModifiersNode parseOptional(ASTBuilder builder, ASTNode parent) {
        ClassModifiersNode result = new ClassModifiersNode();
        result.parse(builder, parent);
        return result;
    }
}
