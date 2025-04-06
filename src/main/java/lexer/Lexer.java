package lexer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Lexer {
    public int line = 1;
    private char peek = ' ';
    private HashMap<String, Word> words = new HashMap<>();
    private static int EOF = -1;

    public Lexer() {
        reserve(new Word(Token.TRUE, "true"));
        reserve(new Word(Token.FALSE, "false"));
    }

    public void reserve(Word word) {
        words.put(word.lexeme, word);
    }

    /**
     * Scans for tokens until an EOF (-1) is reached
     *
     * @param input String to be scanned into tokens
     * @return <code>java.util.List</code> of <code>Token</code> objects
     */
    public List<Token> scan(String input) throws IOException {
        List<Token> tokens = new ArrayList<>();
        int position = 0;
        while (position < input.length()) {
            peek = input.charAt(position);
            if (peek == ' ' || peek == '\r') {
                position++;
            } else if (peek == '\n') {
                line++;
                position++;
            } else if (peek == '/') {
                if (position < input.length() - 1 && input.charAt(position + 1) == '/') {
                    position += 2; // consume '//'
                    while (position < input.length() && input.charAt(position) != '\n') {
                        position++; // consume comment
                    }
                } else if (position < input.length() - 1 && input.charAt(position + 1) == '*') {
                    position += 2;
                    while (position < input.length() - 1 && input.charAt(position) != '*' && input.charAt(position + 1) != '/') {
                        position++; // consume multiline comment
                    }
                    position += 2; // move cursor past "*/"
                } else {
                    tokens.add(new Operator("/"));
                    position++;
                }
            } else if (peek == '<') {
                if (position < input.length() - 1 && input.charAt(position + 1) == '=') {
                    position += 2;
                    tokens.add(new Operator("<="));
                } else {
                    tokens.add(new Operator("<"));
                    position++;
                }
            } else if (peek == '>') {
                if (position < input.length() - 1 && input.charAt(position + 1) == '=') {
                    position += 2;
                    tokens.add(new Operator(">="));
                } else {
                    tokens.add(new Operator(">"));
                    position++;
                }
            } else if (Character.isDigit(peek)) {
                int v = 0;
                while (position < input.length() && Character.isDigit(input.charAt(position))) {
                    v = v * 10 + Character.digit(input.charAt(position++), 10);
                }
                tokens.add(new Num(v));
            } else if (Character.isLetter(peek)) {
                StringBuffer sb = new StringBuffer();
                do {
                    sb.append(input.charAt(position++));
                } while (position < input.length() && Character.isLetterOrDigit(input.charAt(position)));

                String str = sb.toString();
                Word word = words.get(str);
                if (word != null) {
                    tokens.add(word);
                } else {
                    Word w = new Word(Token.ID, str);
                    words.put(str, w);
                    tokens.add(w);
                }
            } else {
                tokens.add(new Operator(Character.toString(peek)));
                position++;
            }
        }
        return tokens;
    }
}
