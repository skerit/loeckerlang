package be.loeckerlang.compiler.ast.nodes;

import be.loeckerlang.compiler.ast.ASTBuilder;
import be.loeckerlang.compiler.tokens.Token;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Collection;
import java.util.List;

/**
 * Modifiers used by fields
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public class FieldModifiersNode extends BaseModifiersNode {

    private static final List<Token.Type> FIELD_MODIFIER_TOKENS = List.of(
            Token.Type.PRIVATE,
            Token.Type.PROTECTED,
            Token.Type.PUBLIC,
            Token.Type.FINAL,
            Token.Type.STATIC,
            Token.Type.IMMUTABLE,
            Token.Type.BUILTIN
    );

    /**
     * Return all the allowed tokens
     *
     * @since    0.1.0
     */
    public Collection<Token.Type> getAllowedTokens() {
        return FIELD_MODIFIER_TOKENS;
    }

    /**
     * Parse optional decorators
     *
     * @since    0.1.0
     */
    @NonNull
    public static FieldModifiersNode parseOptional(ASTBuilder builder, ASTNode parent) {
        FieldModifiersNode result = new FieldModifiersNode();
        result.parse(builder, parent);
        return result;
    }
}
