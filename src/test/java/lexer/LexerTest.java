package lexer;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LexerTest {
    @Test
    public void scanShouldIncrementLineNumberOnNewLine() {
        Lexer lexer = new Lexer();
        try {
            List<Token> tokens = lexer.scan("3+2\n4-8");
            assertEquals(2, lexer.line);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void scanShouldIgnoreDoubleSlashComments() {
        Lexer lexer = new Lexer();
        try {
            List<Token> tokens = lexer.scan("Ignore comments// this shoudn't show");
            assertEquals(2, tokens.size());
            assertEquals(Token.ID, tokens.get(0).tag);
            assertEquals("Ignore", ((Word) tokens.get(0)).lexeme);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void scanShouldIgnoreMultilineComments() {
        Lexer lexer = new Lexer();
        try {
            List<Token> tokens = lexer.scan("x+/**/5");
            assertEquals(3, tokens.size());
            assertEquals(Token.ID, tokens.get(0).tag);
            assertEquals(Token.OP, tokens.get(1).tag);
            assertEquals(Token.NUM, tokens.get(2).tag);

            tokens = lexer.scan("hello /*This should\nbe\nignored\n  */3");
            assertEquals(2, tokens.size());
            assertEquals(Token.ID, tokens.get(0).tag);
            assertEquals(Token.NUM, tokens.get(1).tag);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void scanShouldRecognizeLessThanLessThanOrEqualAndSameForGreaterThan() {
        Lexer lexer = new Lexer();
        try {
            List<Token> tokens = lexer.scan("3 < 2");
            assertEquals("<", ((Operator) tokens.get(1)).oper);
            tokens = lexer.scan("3 <= 2");
            assertEquals("<=", ((Operator) tokens.get(1)).oper);
            tokens = lexer.scan("3 > 2");
            assertEquals(">", ((Operator) tokens.get(1)).oper);
            tokens = lexer.scan("3 >= 2");
            assertEquals(">=", ((Operator) tokens.get(1)).oper);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void scanShouldIgnoreWhiteSpace() {
        Lexer lexer = new Lexer();
        try {
            // The argument to lexer.scan has a tab between 56 and -12 and three spaces
            // between -12 and *16
            List<Token> tokens = lexer.scan("56  -12   *16");
            assertEquals(5, tokens.size());
            assertEquals(56, ((Num)tokens.get(0)).value);
            assertEquals('-', ((Operator)tokens.get(1)).oper);
            assertEquals(12, ((Num)tokens.get(2)).value);
            assertEquals('*', ((Operator)tokens.get(3)).oper);
            assertEquals(16, ((Num)tokens.get(4)).value);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
