package be.loeckerlang.compiler.ast.nodes;

import be.loeckerlang.compiler.ast.ASTBuilder;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * A statement node
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public abstract class StatementNode extends ASTNode {

    /**
     * Parse optional decorators
     *
     * @since    0.1.0
     */
    public static StatementNode parseNextStatement(ASTBuilder builder, ASTNode parent) {

        VariableDeclarationStatementNode declaration = new VariableDeclarationStatementNode();

        if (declaration.doOptionalParse(builder, parent)) {
            return declaration;
        }

        return null;
    }

}
