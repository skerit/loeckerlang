package be.loeckerlang.compiler.ast.nodes;

import be.loeckerlang.compiler.ast.ASTBuilder;
import be.loeckerlang.compiler.tokens.Token;

/**
 * Special statements used by the compiler itself.
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public class BuiltinTargetNode extends ASTNode {

    private String target_name;
    private boolean for_async = false;
    private String code;

    /**
     * Should this start with a specific token?
     *
     * @since    0.1.0
     */
    @Override
    protected Token.Type shouldStartWith() {
        return Token.Type.TARGET;
    }

    /**
     * Should this end with a specific token?
     *
     * @since    0.1.0
     */
    @Override
    protected Token.Type shouldEndWith() {
        return Token.Type.SEMICOLON;
    }

    /**
     * Parse the contents of this node
     *
     * @since    0.1.0
     */
    @Override
    protected void parseContents(ASTBuilder builder) {

        if (!builder.currentTokensAre(Token.Type.IDENTIFIER)) {
            builder.reportSyntaxError("Expected the target name");
            return;
        }

        this.target_name = builder.consume().getLexeme();

        if (builder.consume(Token.Type.ASYNC)) {
            this.for_async = true;
        }

        Token current = builder.peekCurrent();

        if (current.getType() == Token.Type.IDENTIFIER && current.getLexeme().equals("sync")) {
            this.for_async = false;
            builder.consume();
        }

        if (!builder.currentTokensAre(Token.Type.STRING_LITERAL)) {
            builder.reportSyntaxError("Expected the target body");
            return;
        }

        this.code = builder.consume().getLexeme();

        // Remove the first & last character
        this.code = this.code.substring(1, this.code.length() - 1);
    }

    /**
     * Parse a target
     *
     * @since    0.1.0
     */
    public static BuiltinTargetNode parseNext(ASTBuilder builder, ASTNode parent) {

        BuiltinTargetNode target = new BuiltinTargetNode();

        if (target.doOptionalParse(builder, parent)) {
            return target;
        }

        return null;
    }
}
