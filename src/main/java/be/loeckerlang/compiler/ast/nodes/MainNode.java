package be.loeckerlang.compiler.ast.nodes;

import be.loeckerlang.compiler.ast.ASTBuilder;

/**
 * The main node:
 * represents a single file
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public class MainNode extends ASTNode {

    // Have we started parsing?
    private boolean started_parsing = false;

    // The namespace node
    private NamespaceNode namespace = null;

    /**
     * Let them parse themselves
     *
     * @since    0.1.0
     */
    public void startParsing(ASTBuilder builder) {

        if (this.started_parsing) {
            throw new IllegalStateException("This node has already started parsing");
        }

        this.started_parsing = true;
        this.parse(builder, null);
    }

    /**
     * Parse the contents of the file
     *
     * @since    0.1.0
     */
    protected void parseContents(ASTBuilder builder) {

        this.namespace = new NamespaceNode();



    }

}
