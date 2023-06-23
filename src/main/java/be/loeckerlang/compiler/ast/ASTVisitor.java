package be.loeckerlang.compiler.ast;

import be.loeckerlang.compiler.ast.nodes.ASTNode;

/**
 * The AST visitor
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public abstract class ASTVisitor {
    public abstract void visit(ASTNode node);
}
