package be.loeckerlang.compiler.intermediate.tables;

import be.loeckerlang.compiler.ast.nodes.FileNode;

import java.util.HashMap;
import java.util.Map;

/**
 * A namespace symbol table.
 * Represents a namespace.
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public class NamespaceSymbolTable extends SymbolTable {

    private final Map<String, FileSymbolTable> files = new HashMap<>();
    private final String name;

    /**
     * Initialize the instance
     *
     * @since    0.1.0
     */
    public NamespaceSymbolTable(GlobalSymbolTable root, String name) {
        super(root);
        this.name = name;
    }

    /**
     * Create a new symbol table for a file
     *
     * @since    0.1.0
     */
    public FileSymbolTable createFileSymbolTable(FileNode file_node) {

        FileSymbolTable table = new FileSymbolTable(this);

        this.files.put(file_node.getFilePath(), table);

        return table;
    }

    /**
     * Get all the files in this namespace
     *
     * @since 0.1.0
     */
    public Map<String, FileSymbolTable> getFiles() {
        return this.files;
    }

    /**
     * Get the unique id of this namespace
     *
     * @since 0.1.0
     */
    public String getUniqueName() {
        return this.name;
    }

}
