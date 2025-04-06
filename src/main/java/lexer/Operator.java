package lexer;

public class Operator extends Token {
    public String oper;

    public Operator(String oper) {
        super(Token.OP);
        this.oper = oper;
    }

    @Override
    public String toString() {
        return "Operator{" +
                "oper='" + oper + '\'' +
                '}';
    }
}
