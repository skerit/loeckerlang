package be.loeckerlang;

import be.loeckerlang.compiler.ast.ASTBuilder;
import be.loeckerlang.compiler.ast.nodes.FileNode;
import be.loeckerlang.compiler.ast.visitors.ASTPrinter;
import be.loeckerlang.compiler.codegen.JavaScriptCodeGenerator;
import be.loeckerlang.compiler.intermediate.tables.GlobalSymbolTable;
import be.loeckerlang.compiler.intermediate.tables.SymbolTable;
import be.loeckerlang.compiler.intermediate.tables.SymbolTableBuilder;
import be.loeckerlang.compiler.tokens.Token;
import be.loeckerlang.compiler.tokens.Tokenizer;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Loeckerlang test!");

        // Read the test.loeckerlang file from the resources/examples folder
        URL file = Main.class.getResource("/examples/integer.loeckerlang");

        // Turn the URL into a Path instance
        Path file_path = Paths.get(file.getPath());

        // Read the contents of the file
        String input = new String(Files.readAllBytes(file_path));

        /*String input = "namespace rocks.blackblock;\n" +
                "\n" +
                "use oatscript.lang.Emitter;\n" +
                "use oatscript.lang.On;\n" +
                "\n" +
                "@Emitter()\n" +
                "class Test {\n" +
                "\n" +
                "    public Test() {\n" +
                "        Emitter.emit(Startup);\n" +
                "    }\n" +
                "\n" +
                "    @On(Startup)\n" +
                "    private void onStartup() {\n" +
                "        print(\"Hello World!\");\n" +
                "    }\n" +
                "\n" +
                "    @On(Saving)\n" +
                "    private void onSave() {\n" +
                "        print(\"Saving!\");\n" +
                "    }\n" +
                "}\n";
         */

        Tokenizer tokenizer = new Tokenizer(input);
        List<Token> tokens = tokenizer.tokenize();

        for (Token token : tokens) {
            System.out.println(token);
        }

        ASTBuilder builder = new ASTBuilder(tokens);
        FileNode node = builder.buildAST();
        node.setFilePath(file_path.toString());

        ASTPrinter printer = new ASTPrinter();
        printer.visit(node);

        GlobalSymbolTable global = new GlobalSymbolTable();
        SymbolTableBuilder table_builder = new SymbolTableBuilder(global);
        table_builder.visit(node);

        JavaScriptCodeGenerator generator = new JavaScriptCodeGenerator(global);
        String code = generator.generateCode();

        System.out.println(code);

    }
}