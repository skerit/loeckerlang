package be.loeckerlang.compiler.codegen;

import be.loeckerlang.compiler.intermediate.symbols.Method;
import be.loeckerlang.compiler.intermediate.symbols.MethodSignature;
import be.loeckerlang.compiler.intermediate.symbols.MethodSignatureGroup;
import be.loeckerlang.compiler.intermediate.tables.ClassSymbolTable;
import be.loeckerlang.compiler.intermediate.tables.FileSymbolTable;
import be.loeckerlang.compiler.intermediate.tables.GlobalSymbolTable;
import be.loeckerlang.compiler.intermediate.tables.NamespaceSymbolTable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Turn the intermediate Symbols into JavaScript code.
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public class JavaScriptCodeGenerator extends CodeGenerator {

    private StringBuilder result;
    private Map<MethodSignature, Callable> callables = new HashMap<>();

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

        this.callables.forEach((methodSignature, callable) -> {
            this.result.append(callable.getCode());
        });

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

        // Create the methods
        class_table.getMethodSignatureGroups().forEach((method_name, method_group) -> {
            this.processMethodSignatureGroup(class_table, method_name, method_group);
        });
    }

    /**
     * Generate the code for a file
     *
     * @since 0.1.0
     */
    protected void processMethodSignatureGroup(ClassSymbolTable class_table, String name, MethodSignatureGroup method_group) {

        String method_group_name = this.getSymbolName(method_group);

        method_group.getMethods().forEach((signature, method) -> {

            Callable callable = this.getOrCreateCallable(signature);

            callable.setAsync(method.isAsync());
            callable.setParameters(signature.getParameters());

            this.processMethodBody(callable, method);

        });
    }

    /**
     * Process the method body
     *
     * @since 0.1.0
     */
    protected void processMethodBody(Callable callable, Method method) {

        //callable.setBody(body);
    }

    /**
     * Get the JavaScript function name of the given method
     *
     * @since 0.1.0
     */
    public Callable getOrCreateCallable(MethodSignature method) {

        Callable result = this.callables.get(method);

        if (result == null) {
            String unique_name = method.getName() + "_" + this.getUniqueId();
            result = new Callable(unique_name, method.getName());
            this.callables.put(method, result);
        }

        return result;
    }

    /**
     * A callable javascript function
     *
     * @since 0.1.0
     */
    public static class Callable {

        // The unique id of this callable
        protected final String id;

        // The original name of this callable
        protected final String name;

        // The body of this callable
        protected StringBuilder body;

        // Is this callable async?
        protected boolean is_async = false;

        // The parameters of this callable
        protected List<String> parameters = null;

        public Callable(String id, String name) {
            this.id = id;
            this.name = name;
            this.body = new StringBuilder();
        }

        /**
         * Set this callable to be async
         *
         * @since 0.1.0
         */
        public void setAsync(boolean is_async) {
            this.is_async = is_async;
        }

        /**
         * Set the parameter names
         *
         * @since 0.1.0
         */
        public void setParameters(List<String> parameters) {
            this.parameters = parameters;
        }

        /**
         * Set the JavaScript body
         *
         * @since 0.1.0
         */
        public void setBody(String body) {
            this.body = new StringBuilder(body);
        }

        /**
         * Get the JavaScript code
         *
         * @since 0.1.0
         */
        public String getCode() {

            StringBuilder result = new StringBuilder();

            if (this.is_async) {
                result.append("async ");
            }

            result.append("function ").append(this.id).append("(");
            result.append("$self");

            if (this.parameters != null) {
                this.parameters.forEach((parameter_name) -> {
                    result.append(", ").append(parameter_name);
                });
            }

            result.append(") {\n");

            result.append(this.body);

            result.append("}\n");

            return result.toString();
        }


    }
}
