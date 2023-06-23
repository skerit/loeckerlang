package be.loeckerlang;

import be.loeckerlang.compiler.tokens.Token;
import be.loeckerlang.compiler.tokens.Tokenizer;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        String input = "namespace rocks.blackblock;\n" +
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

        Tokenizer tokenizer = new Tokenizer(input);
        List<Token> tokens = tokenizer.tokenize();

        for (Token token : tokens) {
            System.out.println(token);
        }
    }
}