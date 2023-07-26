package be.loeckerlang.compiler.ast.nodes;

import be.loeckerlang.compiler.ast.ASTBuilder;
import be.loeckerlang.compiler.tokens.Token;

/**
 * Represents all the (direct) ancestors of a class
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public class ClassAncestors extends ASTNode {

    private ListOfNodes<QualifiedNameNode> ancestors = null;

    /**
     * Parse the contents of this node
     *
     * @since    0.1.0
     */
    @Override
    protected void parseContents(ASTBuilder builder) {

        do {
            QualifiedNameNode ancestor = QualifiedNameNode.parseOptional(builder, this);

            if (this.ancestors == null) {
                this.ancestors = new ListOfNodes<>();
            }

            this.ancestors.add(ancestor);

        } while (builder.consume(Token.Type.COMMA));
    }
}
