package lexer;

import java.io.IOException;
import java.util.List;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        //System.out.println(args[0]);
        Lexer lexer = new Lexer();
        try {
            List<Token> tokens = lexer.scan(args[0]);
            for (Token token: tokens) {
                System.out.println(token);
            }
        } catch (IOException e) {
            System.err.println("IOException occurred during scanning");
        }
    }
}
