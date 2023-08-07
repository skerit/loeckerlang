package be.loeckerlang.compiler.intermediate.tables;

import be.loeckerlang.compiler.ast.nodes.BlockNode;
import be.loeckerlang.compiler.ast.nodes.BuiltinTargetNode;
import be.loeckerlang.compiler.intermediate.symbols.Method;

import java.util.List;

/**
 * Represents a block of code
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public class Block extends SymbolTable {

    protected Method parent_method;
    protected List<BuiltinTargetNode> builtin_targets;

    /**
     * Initialize a new instance of a block
     * that is the main code block of a method
     *
     * @since    0.1.0
     */
    public Block(Method parent_method) {
        super(parent_method);
        this.parent_method = parent_method;
    }

    /**
     * Initialize a new instance of a block
     * that is a nested block
     *
     * @since    0.1.0
     */
    public Block(Block parent_block) {
        super(parent_block);
        this.parent_method = parent_block.getParentMethod();
    }

    /**
     * Get the parent method
     *
     * @since    0.1.0
     */
    public Method getParentMethod() {
        return this.parent_method;
    }

    /**
     * Create a block from the given node
     *
     * @since    0.1.0
     */
    public static Block fromNode(Method parent_method, BlockNode node) {
        Block block = new Block(parent_method);

        if (node.hasBuiltinTargets()) {
            block.builtin_targets = node.getBuiltinTargets();
        } else {

        }

        return block;
    }
}
