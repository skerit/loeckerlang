package be.loeckerlang.compiler.tokens;

/**
 * Representing a single token
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public class Token {

    // The empty token
    public static final Token EMPTY = new Token(Type.EMPTY, "", -1, -1);

    // The EOF token
    public static final Token EOF = new Token(Type.EOF, "", -1, -1);

    // The type of token
    private Type type;

    // The actual token value
    private String lexeme;

    // The line number
    private int line;

    // The column number
    private int column;

    /**
     * Initialize the token
     *
     * @since    0.1.0
     */
    public Token(Type type, String lexeme, int line, int column) {
        this.type = type;
        this.lexeme = lexeme;
        this.line = line;
        this.column = column;
    }

    /**
     * Get the type of token
     *
     * @since    0.1.0
     */
    public Type getType() {
        return type;
    }

    /**
     * Get the token value
     *
     * @since    0.1.0
     */
    public String getLexeme() {
        return lexeme;
    }

    /**
     * Append to the value
     *
     * @since    0.1.0
     */
    public void append(char value) {
        this.lexeme += value;
    }

    /**
     * Get the string representation of this token
     *
     * @since    0.1.0
     */
    @Override
    public String toString() {
        return "Token<" + this.type + ">{line=" + this.line + ", column=" + this.column + ", lexeme='" + this.lexeme + "'}";
    }

    /**
     * Representing a token type
     *
     * @since    0.1.0
     */
    public enum Type {
        AT,
        NAMESPACE,
        PACKAGE,
        CLASS,
        INHERITS,
        VAR,
        ABSTRACT,
        COMMA,
        IF,
        PERIOD,
        ELSE,
        RETURN,
        TRUE,
        FALSE,
        CIRCUMFLEX,
        NEW,
        VOID,
        PROTECTED,
        PUBLIC,
        PRIVATE,
        FINAL,
        IDENTIFIER,
        INTEGER_LITERAL,
        FLOAT_LITERAL,
        STRING_LITERAL,
        LEFT_BRACE,
        RIGHT_BRACE,
        LEFT_PAREN,
        RIGHT_PAREN,
        PLUS,
        MINUS,
        STAR,
        SEMICOLON,
        COLON,
        SLASH,
        EQUALS,
        DOUBLE_EQUALS,
        BOOLEAN_LITERAL,
        USE,
        STATIC,
        UNKNOWN,
        EMPTY,
        EOF
    }
}
