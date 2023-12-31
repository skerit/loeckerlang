package be.loeckerlang.compiler.ast.nodes;

import be.loeckerlang.compiler.ast.ASTBuilder;
import be.loeckerlang.compiler.intermediate.tables.FileSymbolTable;
import be.loeckerlang.compiler.intermediate.tables.SymbolTable;
import be.loeckerlang.compiler.tokens.Token;

/**
 * The namespace node:
 * defines the name space the current file is in
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public class NamespaceNode extends ASTNode {

    private QualifiedNameNode path = null;

    /**
     * Parse a NamespaceNode at the current position
     *
     * @since    0.1.0
     */
    @Override
    protected void parseContents(ASTBuilder builder) {
        QualifiedNameNode path = new QualifiedNameNode();
        path.parse(builder, this);
        this.path = path;
    }

    /**
     * Get the path as a string
     *
     * @since    0.1.0
     */
    public String getPathAsString() {
        return this.path.getPathAsString();
    }

    /**
     * Get the path
     *
     * @since    0.1.0
     */
    public QualifiedNameNode getPath() {
        return this.path;
    }

    /**
     * Should this start with a specific token?
     *
     * @since    0.1.0
     */
    protected Token.Type shouldStartWith() {
        return Token.Type.NAMESPACE;
    }

    /**
     * Should this end with a specific token?
     *
     * @since    0.1.0
     */
    @Override
    public Token.Type shouldEndWith() {
        return Token.Type.SEMICOLON;
    }
}
