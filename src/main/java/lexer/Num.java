package lexer;

public class Num extends Token {
    public final int value;

    @Override
    public String toString() {
        return "Num{" +
                "value=" + value +
                '}';
    }

    public Num(int value) {
        super(NUM);
        this.value = value;
    }
}
