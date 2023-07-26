package be.loeckerlang.compiler.ast.nodes;

import be.loeckerlang.compiler.ast.ASTBuilder;

/**
 * The main node:
 * represents a single file
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public class FileNode extends ASTNode {

    // Have we started parsing?
    private boolean started_parsing = false;

    // The namespace node
    private NamespaceNode namespace = null;

    // Use nodes
    private ListOfNodes<UseNode> uses = null;

    // The class
    private ClassNode class_node = null;

    // The path to the file
    private String file_path = null;

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
        this.namespace.parse(builder, this);

        this.uses = UseNode.parseOptional(builder, this);
        this.class_node = ClassNode.parseOptional(builder, this);
    }

    /**
     * Get the namespace node
     *
     * @since    0.1.0
     */
    public NamespaceNode getNamespace() {
        return this.namespace;
    }

    /**
     * Get the uses
     *
     * @since    0.1.0
     */
    public ListOfNodes<UseNode> getUses() {
        return this.uses;
    }

    /**
     * Get the main class node
     *
     * @since    0.1.0
     */
    public ClassNode getClassNode() {
        return this.class_node;
    }

    /**
     * Set the file path
     *
     * @since    0.1.0
     */
    public void setFilePath(String path) {
        this.file_path = path;
    }

    /**
     * Get the full path of the file
     *
     * @since    0.1.0
     */
    public String getFilePath() {
        return this.file_path;
    }
}
