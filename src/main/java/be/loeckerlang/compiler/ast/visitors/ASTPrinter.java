package be.loeckerlang.compiler.ast.visitors;

import be.loeckerlang.compiler.ast.ASTVisitor;
import be.loeckerlang.compiler.ast.nodes.*;
import be.loeckerlang.compiler.ast.nodes.expressions.LiteralNode;

/**
 * Print the tree
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public class ASTPrinter implements ASTVisitor {

    private int indentation = 0;

    private void indent() {
        for (int i = 0; i < this.indentation; i++) {
            // Adjust the indentation to your preference
            System.out.print("  ");
        }
    }

    private void println(String str) {
        this.indent();
        System.out.println(str);
    }

    @Override
    public void visit(LiteralNode node) {
        this.println("LiteralNode (" + node.getClass().getSimpleName() + ")");
        this.indentation++;

        if (node.getTokenValue() != null) {
            this.println(node.getTokenValue().toString());
        }

        this.indentation--;
    }

    @Override
    public void visit(ClassModifiersNode node) {

        this.println("ClassModifiersNode");
        this.indentation++;

        if (node.isPublic()) {
            this.println("public");
        }

        if (node.isAbstract()) {
            this.println("abstract");
        }

        if (node.isFinal()) {
            this.println("final");
        }

        if (node.isPrivate()) {
            this.println("private");
        }

        if (node.isProtected()) {
            this.println("protected");
        }

        this.indentation--;
    }

    @Override
    public void visit(FieldDeclarationNode node) {

        this.println("FieldDeclarationNode");
        this.indentation++;

        if (node.getDecorators() != null) {
            node.getDecorators().accept(this);
        }

        if (node.getModifiers() != null) {
            node.getModifiers().accept(this);
        }

        if (node.getType() != null) {
            this.println("Type");
            this.indentation++;
            node.getType().accept(this);
            this.indentation--;
        }

        if (node.getName() != null) {
            this.println("Name");
            this.indentation++;
            node.getName().accept(this);
            this.indentation--;
        }

        if (node.getInitializer() != null) {
            this.println("Initializer");
            this.indentation++;
            node.getInitializer().accept(this);
            this.indentation--;
        }

        this.indentation--;
    }

    @Override
    public void visit(ClassNode node) {
        this.println("ClassNode");
        this.indentation++;

        node.getDecorators().accept(this);

        if (node.getModifiers() != null) {
            node.getModifiers().accept(this);
        }

        if (node.getName() != null) {
            node.getName().accept(this);
        }

        if (node.getFields() != null) {
            this.println("Fields");
            this.indentation++;

            node.getFields().accept(this);

            this.indentation--;
        }

        if (node.getMethods() != null) {
            this.println("Methods");
            this.indentation++;

            node.getMethods().accept(this);

            this.indentation--;
        }

        this.indentation--;
    }

    @Override
    public void visit(MethodDeclarationNode method) {
        this.println("MethodDeclarationNode");
        this.indentation++;

        if (method.getDecorators() != null) {
            method.getDecorators().accept(this);
        }

        if (method.getModifiers() != null) {
            method.getModifiers().accept(this);
        }

        if (method.getType() != null) {
            this.println("ReturnType");
            this.indentation++;
            method.getType().accept(this);
            this.indentation--;
        }

        if (method.getName() != null) {
            this.println("Name");
            this.indentation++;
            method.getName().accept(this);
            this.indentation--;
        }

        if (method.getParameters() != null) {
            this.println("Parameters");
            this.indentation++;
            method.getParameters().accept(this);
            this.indentation--;
        }

        if (method.getBody() != null) {
            this.println("Body");
            this.indentation++;
            method.getBody().accept(this);
            this.indentation--;
        }

        this.indentation--;
    }

    @Override
    public void visit(BlockNode block) {

        this.println("BlockNode");
        this.indentation++;

        if (block.getStatements() != null) {
            this.println("Statements (" + block.getStatements().size() + ")");
            this.indentation++;
            block.getStatements().accept(this);
            this.indentation--;
        }

        this.indentation--;

    }

    @Override
    public void visit(ParametersNode parameters) {
        this.println("ParametersNode");
        this.indentation++;

        if (parameters.getParameters() != null) {
            this.println("Parameters (" + parameters.getParameters().size() + ")");
            this.indentation++;
            parameters.getParameters().accept(this);
            this.indentation--;
        }

        this.indentation--;
    }

    @Override
    public void visit(ParameterNode parameter) {
        this.println("ParameterNode");
        this.indentation++;

        if (parameter.getType() != null) {
            this.println("Type");
            this.indentation++;
            parameter.getType().accept(this);
            this.indentation--;
        }

        if (parameter.getName() != null) {
            this.println("Name");
            this.indentation++;
            parameter.getName().accept(this);
            this.indentation--;
        }

        this.indentation--;
    }

    @Override
    public void visit(SimpleNameNode node) {
        this.println("SimpleName: " + node.getName());
    }

    @Override
    public void visit(QualifiedNameNode node) {
        this.println("QualifiedName");
        this.indentation++;

        QualifiedNameNode qualifier = node.getQualifier();

        if (qualifier != null) {
            qualifier.accept(this);
        }

        node.getName().accept(this);

        this.indentation--;
    }

    @Override
    public void visit(FileNode node) {
        this.println("FileNode");
        this.indentation++;
        node.getNamespace().accept(this);
        node.getUses().accept(this);
        node.getClassNode().accept(this);
        this.indentation--;
    }

    @Override
    public void visit(NamespaceNode node) {
        this.println("NamespaceNode");
        this.indentation++;
        node.getPath().accept(this);
        this.indentation--;
    }

    @Override
    public void visit(UseNode node) {
        this.println("UseNode");

        this.indentation++;
        node.getPath().accept(this);
        this.indentation--;
    }
}
