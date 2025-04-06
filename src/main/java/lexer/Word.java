package lexer;

public class Word extends Token {
    public final String lexeme;

    public Word(int tag, String word) {
        super(tag);
        lexeme = word;
    }

    @Override
    public String toString() {
        return "Word{" +
                "lexeme='" + lexeme + '\'' +
                '}';
    }
}
