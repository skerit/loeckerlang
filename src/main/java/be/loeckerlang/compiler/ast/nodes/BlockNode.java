package be.loeckerlang.compiler.ast.nodes;

import be.loeckerlang.compiler.ast.ASTBuilder;
import be.loeckerlang.compiler.tokens.Token;

/**
 * A block of code
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public class BlockNode extends ASTNode {

    protected ListOfNodes<StatementNode> statements = null;
    protected ListOfNodes<BuiltinTargetNode> builtin_targets = null;

    /**
     * Get all the statements
     *
     * @since   0.1.0
     */
    public ListOfNodes<StatementNode> getStatements() {
        return this.statements;
    }

    /**
     * Parse the contents of this node
     *
     * @since    0.1.0
     */
    @Override
    protected void parseContents(ASTBuilder builder) {

        if (this.getParent() instanceof MethodDeclarationNode method) {
            FunctionModifiersNode modifiers = method.getModifiers();

            if (modifiers.isAbstract()) {
                if (!builder.currentTokensAre(Token.Type.SEMICOLON)) {
                    builder.reportSyntaxError("Abstract methods must not have a block and end with a semicolon");
                    return;
                }
            }

            if (modifiers.isBuiltin()) {
                this.parseBuiltinContents(builder);
                return;
            }
        }

        this.statements = new ListOfNodes<>();

        // The block can be empty
        if (builder.currentTokensAre(Token.Type.RIGHT_BRACE)) {
            return;
        }

        do {
            StatementNode statement = StatementNode.parseNextStatement(builder, this);

            if (statement == null) {
                builder.reportSyntaxError("Expected a statement");
                return;
            }

            this.statements.add(statement);
        } while (!builder.currentTokensAre(Token.Type.RIGHT_BRACE));
    }

    /**
     * Parse the contents of this builtin block
     *
     * @since    0.1.0
     */
    protected void parseBuiltinContents(ASTBuilder builder) {

        this.builtin_targets = new ListOfNodes<>();

        // The block can be empty
        if (builder.currentTokensAre(Token.Type.RIGHT_BRACE)) {
            return;
        }

        do {
            BuiltinTargetNode target = BuiltinTargetNode.parseNext(builder, this);

            if (target == null) {
                builder.reportSyntaxError("Expected a builtin target");
                return;
            }

            this.builtin_targets.add(target);

        } while (!builder.currentTokensAre(Token.Type.RIGHT_BRACE));
    }

    /**
     * Should this start with a specific token?
     *
     * @since    0.1.0
     */
    @Override
    protected Token.Type shouldStartWith() {
        return Token.Type.LEFT_BRACE;
    }

    /**
     * Should this end with a specific token?
     *
     * @since    0.1.0
     */
    @Override
    protected Token.Type shouldEndWith() {
        return Token.Type.RIGHT_BRACE;
    }
}
