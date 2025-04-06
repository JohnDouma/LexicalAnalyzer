package lexer;

public class Token {
    public static final int NUM = 256;
    public static final int ID = 257;
    public static final int TRUE = 258;
    public static final int FALSE = 259;
    public static final int OP = 260;

    @Override
    public String toString() {
        return "Token{" +
                "tag=" + tag +
                '}';
    }

    public final int tag;

    public Token(int tag) {
        this.tag = tag;
    }
}
