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
