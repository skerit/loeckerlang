package be.loeckerlang.compiler.ast.nodes;

import be.loeckerlang.compiler.ast.ASTBuilder;
import be.loeckerlang.compiler.ast.nodes.expressions.ExpressionNode;
import be.loeckerlang.compiler.tokens.Token;

import java.util.ArrayList;
import java.util.List;

/**
 * The QualifiedName Node class:
 * Represents a path of identifiers
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public class QualifiedNameNode extends ExpressionNode {

    // The parent qualifier
    private QualifiedNameNode qualifier = null;

    // The main name
    private SimpleNameNode name = null;

    /**
     * Initialize a new instance with a list of simple name nodes
     *
     * @since    0.1.0
     */
    public QualifiedNameNode(List<SimpleNameNode> identifiers) {

        int size = identifiers.size();

        if (size == 0) {
            throw new IllegalArgumentException("Cannot create QualifiedNameNode with empty list");
        }

        this.name = identifiers.remove(size - 1);

        if (identifiers.size() > 0) {
            this.qualifier = new QualifiedNameNode(identifiers);
        }
    }

    /**
     * Create a new empty instance
     *
     * @since    0.1.0
     */
    public QualifiedNameNode() {}

    /**
     * Get the qualifier
     *
     * @since    0.1.0
     */
    public QualifiedNameNode getQualifier() {
        return this.qualifier;
    }

    /**
     * Get the name
     *
     * @since    0.1.0
     */
    public SimpleNameNode getName() {
        return this.name;
    }

    /**
     * Pop off the last identifier
     *
     * @since    0.1.0
     */
    public SimpleNameNode pop() {

        if (this.qualifier == null) {
            SimpleNameNode result = this.name;
            this.name = null;
            return result;
        }

        return this.qualifier.pop();
    }

    /**
     * Parse a IdentifierPathNode at the current position
     *
     * @since    0.1.0
     */
    @Override
    protected void parseContents(ASTBuilder builder) {

        Token first_token = builder.peekCurrent();

        if (first_token.getType() != Token.Type.IDENTIFIER) {
            builder.reportSyntaxError("Expected identifier");
        }

        List<SimpleNameNode> identifiers = new ArrayList<>();

        while (builder.hasMore()) {
            //Token identifier_token = builder.getCurrentAndConsume();


            SimpleNameNode identifier = new SimpleNameNode();
            identifier.parse(builder, this);
            identifiers.add(identifier);

            System.out.println("Parsed:" + identifier);

            Token next_token = builder.peekCurrent();
            System.out.println("Parsing:" + next_token);

            if (next_token == Token.EMPTY) {
                builder.reportSyntaxError("Expected a token after identifier path, but got nothing");
            }

            Token.Type next_type = next_token.getType();

            if (next_type == Token.Type.SEMICOLON) {
                break;
            }

            if (next_type == Token.Type.PERIOD) {
                builder.consume();
                continue;
            }

            break;
        }

        if (identifiers.size() == 1) {
            this.name = identifiers.get(0);
        } else {
            List<SimpleNameNode> qualifiers = identifiers.subList(0, identifiers.size() - 1);
            this.qualifier = new QualifiedNameNode(qualifiers);
            this.name = identifiers.get(identifiers.size() - 1);
        }
    }

    /**
     * Parse an optional
     *
     * @since    0.1.0
     */
    public static QualifiedNameNode parseOptional(ASTBuilder builder, ASTNode parent) {

        Token current = builder.peekCurrent();

        if (current.getType() != Token.Type.IDENTIFIER) {
            return null;
        }

        QualifiedNameNode result = new QualifiedNameNode();
        result.parse(builder, parent);

        return result;
    }
}