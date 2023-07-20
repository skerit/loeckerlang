package be.loeckerlang.compiler.ast.nodes;

import be.loeckerlang.compiler.ast.ASTBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * A list of nodes
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public class ListOfNodes<T extends ASTNode> extends ASTNode {

    public List<T> nodes = null;

    /**
     * Initiate the instance
     *
     * @since    0.1.0
     */
    public ListOfNodes() {}

    /**
     * Initiate the instance
     *
     * @since    0.1.0
     */
    public ListOfNodes(List<T> nodes) {
        this.nodes = nodes;
    }

    /**
     * Get the size
     *
     * @since    0.1.0
     */
    public int size() {
        if (this.nodes == null) {
            return 0;
        }

        return this.nodes.size();
    }

    /**
     * Is it empty?
     *
     * @since    0.1.0
     */
    public boolean isEmpty() {
        return this.nodes == null || this.nodes.isEmpty();
    }

    /**
     * Add a node
     *
     * @since    0.1.0
     */
    public void add(T node) {

        if (this.nodes == null) {
            this.nodes = new ArrayList<>();
        }

        this.nodes.add(node);
    }

    /**
     * Iterate over all
     *
     * @since    0.1.0
     */
    public void forEach(Consumer<T> node) {
        if (this.nodes == null) {
            return;
        }

        for (T item : this.nodes) {
            node.accept(item);
        }
    }

    /**
     * Let them parse themselves
     *
     * @since    0.1.0
     */
    @Override
    protected void parseContents(ASTBuilder builder) {
        throw new RuntimeException("ListOfNodes should not be parsed directly");
    }

    /**
     * Set the nodes
     *
     * @since    0.1.0
     */
    public void setNodes(List<T> nodes) {
        this.nodes = nodes;
    }

    /**
     * Get the list of nodes
     *
     * @since    0.1.0
     */
    public List<T> getNodes() {
        return this.nodes;
    }
}
