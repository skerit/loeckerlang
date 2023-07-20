package be.loeckerlang.compiler.tokens;

import be.loeckerlang.compiler.tokens.Token;

import java.util.ArrayList;
import java.util.List;

/**
 * Tokenize code
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public class Tokenizer {

    // The original input
    private final String input;

    // The current position
    private int position = 0;

    // The current line
    private int line = 0;

    // The current column on the current line
    private int column = 0;

    // All the tokens
    private List<Token> tokens;

    // The current token
    private Token current_token;

    /**
     * Initialize the tokenizer
     *
     * @since    0.1.0
     */
    public Tokenizer(String input) {
        this.input = input;
        this.tokens = new ArrayList<>(100);
        this.current_token = null;
    }

    /**
     * Tokenize the input
     *
     * @since    0.1.0
     */
    public List<Token> tokenize() {
        while (position < input.length()) {
            char currentChar = input.charAt(position);

            if (this.consumeNewline()) {
                continue;
            }

            if (Character.isWhitespace(currentChar)) {
                position++;
            } else if (currentChar == '.') {
                addToken(Token.Type.PERIOD, ".");
                position++;
            } else if (currentChar == '@') {
                addToken(Token.Type.AT, "@");
                position++;
            } else if (currentChar == '/') {
                if (position + 1 < input.length() && input.charAt(position + 1) == '/') {
                    consumeLineComment();
                } else if (position + 1 < input.length() && input.charAt(position + 1) == '*') {
                    consumeMultilineComment();
                } else {
                    addToken(Token.Type.SLASH, "/");
                    position++;
                }
            } else if (currentChar == '{') {
                addToken(Token.Type.LEFT_BRACE, "{");
                position++;
            } else if (currentChar == '}') {
                addToken(Token.Type.RIGHT_BRACE, "}");
                position++;
            } else if (currentChar == '(') {
                addToken(Token.Type.LEFT_PAREN, "(");
                position++;
            } else if (currentChar == ')') {
                addToken(Token.Type.RIGHT_PAREN, ")");
                position++;
            } else if (currentChar == ';') {
                addToken(Token.Type.SEMICOLON, ";");
                position++;
            } else if (currentChar == ':') {
                addToken(Token.Type.COLON, ":");
                position++;
            } else if (currentChar == ',') {
                addToken(Token.Type.COMMA, ",");
                position++;
            } else if (currentChar == '+') {
                addToken(Token.Type.PLUS, "+");
                position++;
            } else if (currentChar == '-') {
                addToken(Token.Type.MINUS, "-");
                position++;
            } else if (currentChar == '*') {
                addToken(Token.Type.STAR, "*");
                position++;
            } else if (currentChar == '^') {
                addToken(Token.Type.CIRCUMFLEX, "^");
                position++;
            } else if (currentChar == '=') {
                if (position + 1 < input.length() && input.charAt(position + 1) == '=') {
                    addToken(Token.Type.DOUBLE_EQUALS, "==");
                    position += 2;
                } else {
                    addToken(Token.Type.EQUALS, "=");
                    position++;
                }
            } else if (Character.isLetter(currentChar) || currentChar == '_') {
                consumeIdentifier();
            } else if (Character.isDigit(currentChar)) {
                consumeNumber();
            } else if (currentChar == '"') {
                consumeStringLiteral();
            } else {
                consumeUnknown();
            }
        }

        return tokens;
    }

    /**
     * Add a token of the given type & lexeme at the current position
     *
     * @since    0.1.0
     */
    private void addToken(Token.Type type, String lexeme) {
        this.current_token = new Token(type, lexeme, this.line, this.column);
        tokens.add(this.current_token);
    }

    /**
     * Consume newlines,
     * and update the line number & column number
     *
     * @since    0.1.0
     */
    private boolean consumeNewline() {

        if (position + 1 < input.length() && input.charAt(position) == '\r' && input.charAt(position + 1) == '\n') {
            position += 2;
            line++;
            column = 0;
            return true;
        } else if (position < input.length() && input.charAt(position) == '\n') {
            position++;
            line++;
            column = 0;
            return true;
        }

        return false;
    }

    private void consumeUnknown() {

        if (this.current_token != null && this.current_token.getType() == Token.Type.UNKNOWN) {
            this.current_token.append(input.charAt(position));
        } else {
            addToken(Token.Type.UNKNOWN, "" + input.charAt(position));
        }

        position++;
    }



    private void consumeLineComment() {
        position += 2;

        while (position < input.length() && input.charAt(position) != '\n') {
            position++;
        }
    }

    private void consumeMultilineComment() {
        position += 2;

        while (position < input.length()) {
            if (input.charAt(position) == '*' && position + 1 < input.length() && input.charAt(position + 1) == '/') {
                position += 2;
                break;
            }
            position++;
        }
    }

    private void consumeIdentifier() {
        int start = position;
        position++;

        while (position < input.length() && (Character.isLetterOrDigit(input.charAt(position)) || input.charAt(position) == '_')) {
            position++;
        }

        String lexeme = input.substring(start, position);
        Token.Type type = getIdentifierTokenType(lexeme);
        addToken(type, lexeme);
    }

    private Token.Type getIdentifierTokenType(String lexeme) {
        switch (lexeme) {
            case "namespace":
                return Token.Type.NAMESPACE;
            case "package":
                return Token.Type.PACKAGE;
            case "class":
                return Token.Type.CLASS;
            case "inherits":
                return Token.Type.INHERITS;
            case "var":
                return Token.Type.VAR;
            case "abstract":
                return Token.Type.ABSTRACT;
            case "if":
                return Token.Type.IF;
            case "else":
                return Token.Type.ELSE;
            case "return":
                return Token.Type.RETURN;
            case "true":
            case "false":
                return Token.Type.BOOLEAN_LITERAL;
            case "new":
                return Token.Type.NEW;
            case "void":
                return Token.Type.VOID;
            case "protected":
                return Token.Type.PROTECTED;
            case "private":
                return Token.Type.PRIVATE;
            case "use":
                return Token.Type.USE;
            case "public":
                return Token.Type.PUBLIC;
            case "final":
                return Token.Type.FINAL;
            case "static":
                return Token.Type.STATIC;
            default:
                return Token.Type.IDENTIFIER;
        }
    }

    private void consumeNumber() {
        int start = position;

        while (position < input.length() && Character.isDigit(input.charAt(position))) {
            position++;
        }

        if (position < input.length() && input.charAt(position) == '.') {
            position++;
            while (position < input.length() && Character.isDigit(input.charAt(position))) {
                position++;
            }

            addToken(Token.Type.FLOAT_LITERAL, input.substring(start, position));
        } else {
            addToken(Token.Type.INTEGER_LITERAL, input.substring(start, position));
        }
    }

    private void consumeStringLiteral() {
        int start = position;
        position++;

        StringBuilder lexemeBuilder = new StringBuilder();

        while (position < input.length() && input.charAt(position) != '"') {
            char currentChar = input.charAt(position);
            if (currentChar == '\\') {
                position++;
                if (position < input.length()) {
                    char escapedChar = input.charAt(position);
                    switch (escapedChar) {
                        case 'n':
                            lexemeBuilder.append('\n');
                            break;
                        case 't':
                            lexemeBuilder.append('\t');
                            break;
                        case 'r':
                            lexemeBuilder.append('\r');
                            break;
                        case 'b':
                            lexemeBuilder.append('\b');
                            break;
                        case 'f':
                            lexemeBuilder.append('\f');
                            break;
                        case '"':
                            lexemeBuilder.append('"');
                            break;
                        case '\'':
                            lexemeBuilder.append('\'');
                            break;
                        case '\\':
                            lexemeBuilder.append('\\');
                            break;
                        default:
                            lexemeBuilder.append(escapedChar);
                    }
                }
            } else {
                lexemeBuilder.append(currentChar);
            }

            position++;
        }

        if (position < input.length() && input.charAt(position) == '"') {
            position++;
            addToken(Token.Type.STRING_LITERAL, lexemeBuilder.toString());
        }
    }
}
