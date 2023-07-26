package be.loeckerlang.compiler.codegen;

import be.loeckerlang.compiler.intermediate.tables.ClassSymbolTable;
import be.loeckerlang.compiler.intermediate.tables.FileSymbolTable;
import be.loeckerlang.compiler.intermediate.tables.GlobalSymbolTable;
import be.loeckerlang.compiler.intermediate.tables.NamespaceSymbolTable;

import java.util.HashMap;
import java.util.Map;

/**
 * Turn the intermediate Symbols into JavaScript code.
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public class JavaScriptCodeGenerator extends CodeGenerator {

    private StringBuilder result;
    private Map<String, Callable> callables = new HashMap<>();

    public JavaScriptCodeGenerator(GlobalSymbolTable global_table) {
        super(global_table);
    }

    /**
     * Actually generate the code
     *
     * @since 0.1.0
     */
    public String generateCode() {

        this.result = new StringBuilder();
        this.global_table.getNamespaces().forEach(this::processNamespace);

        return this.result.toString();
    }

    /**
     * Generate the namespace code
     *
     * @since 0.1.0
     */
    protected void processNamespace(String name, NamespaceSymbolTable namespace) {

        this.result.append("/* Namespace: ").append(name).append(" */\n");
        namespace.getFiles().forEach(this::processNamespaceFile);

    }

    /**
     * Generate the code for a file
     *
     * @since 0.1.0
     */
    protected void processNamespaceFile(String name, FileSymbolTable file_table) {

        this.result.append("/* File: ").append(name).append(" */\n");
        file_table.getClasses().forEach(this::processClass);
    }

    /**
     * Generate the code for a class
     *
     * @since 0.1.0
     */
    protected void processClass(String name, ClassSymbolTable class_table) {

        this.result.append("/* Class: ").append(name).append(" */\n");

        String class_name = this.getSymbolName(class_table);

        String class_info_name = class_name + "_info";

        this.result.append("var ").append(class_info_name).append(" = {\n");
        this.result.append("    name: '").append(class_table.getName()).append("',\n");
        this.result.append("};\n");

        String class_creator_name = class_name + "_create";

        // This function creates the un-initialized class object
        this.result.append("function ").append(class_creator_name).append("() {\n");

        this.result.append("return {\n");
        this.result.append("  $class_info: ").append(class_info_name).append(",\n");

        // Add the fields
        class_table.getProperties().forEach((field_name, field) -> {
            this.result.append("  ").append(this.getSymbolName(field)).append(": null,\n");
        });

        this.result.append("};\n");
        this.result.append("}\n");
    }

    /**
     * A callable javascript function
     *
     * @since 0.1.0
     */
    public static class Callable {

        protected final String name;

        public Callable(String name) {
            this.name = name;
        }

    }
}
