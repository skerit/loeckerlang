package be.loeckerlang.compiler.ast.nodes;

import be.loeckerlang.compiler.ast.ASTBuilder;
import be.loeckerlang.compiler.tokens.Token;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Collection;
import java.util.List;

/**
 * All the modifiers a function can have
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public class FunctionModifiersNode extends BaseModifiersNode {

    private static final List<Token.Type> FUNCTION_MODIFIER_TOKENS = List.of(
            Token.Type.PRIVATE,
            Token.Type.PROTECTED,
            Token.Type.PUBLIC,
            Token.Type.ABSTRACT,
            Token.Type.FINAL,
            Token.Type.STATIC,
            Token.Type.IMMUTABLE,
            Token.Type.BUILTIN,
            Token.Type.MEMOIZED
    );

    /**
     * Return all the allowed tokens
     *
     * @since    0.1.0
     */
    public Collection<Token.Type> getAllowedTokens() {
        return FUNCTION_MODIFIER_TOKENS;
    }

    /**
     * Parse optional decorators
     *
     * @since    0.1.0
     */
    @NonNull
    public static FunctionModifiersNode parseOptional(ASTBuilder builder, ASTNode parent) {
        FunctionModifiersNode result = new FunctionModifiersNode();
        result.parse(builder, parent);
        return result;
    }
}
