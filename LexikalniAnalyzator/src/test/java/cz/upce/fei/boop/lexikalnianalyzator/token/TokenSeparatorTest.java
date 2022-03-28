package cz.upce.fei.boop.lexikalnianalyzator.token;

import static cz.upce.fei.boop.lexikalnianalyzator.Counter.counter;
import cz.upce.fei.boop.lexikalnianalyzator.token.enums.SeparatorEnum;
import cz.upce.fei.boop.lexikalnianalyzator.token.enums.SeparatorEnumTest;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.TestName;

/**
 *
 * @author karel@simerda.cz
 */
public class TokenSeparatorTest {

    @Rule
    public TestName testName = new TestName();

    static void print(String methodName) {
        System.out.println(String.format("%03d TokenSeparator.%s",
                counter++, methodName));
    }

    static final double DELTA = 1e-9;

    public TokenSeparatorTest() {
    }

    @Test
    public void test01_01_class() {
        print(testName.getMethodName());
        Token token = new TokenSeparator(SeparatorEnum.SEMICOLON);
        assertEquals(TokenSeparator.class, token.getClass());
    }

    @Test
    public void test01_02_semicolon() {
        print(testName.getMethodName());
        TokenSeparator token = new TokenSeparator(SeparatorEnum.SEMICOLON);
        assertEquals(SeparatorEnum.SEMICOLON, token.getSep());
    }

    @Test
    public void test01_03_colon() {
        print(testName.getMethodName());
        TokenSeparator token = new TokenSeparator(SeparatorEnum.COLON);
        assertEquals(SeparatorEnum.COLON, token.getSep());
    }

    @Test
    public void test01_04_comma() {
        print(testName.getMethodName());
        TokenSeparator token = new TokenSeparator(SeparatorEnum.COMMA);
        assertEquals(SeparatorEnum.COMMA, token.getSep());
    }

    @Test
    public void test01_05_equals() {
        print(testName.getMethodName());
        TokenSeparator token = new TokenSeparator(SeparatorEnum.EQUALS);
        assertEquals(SeparatorEnum.EQUALS, token.getSep());
    }

    @Test
    public void test01_06_whiteChar() {
        print(testName.getMethodName());
        TokenSeparator token = new TokenSeparator(SeparatorEnum.WHITE_CHAR);
        assertEquals(SeparatorEnum.WHITE_CHAR, token.getSep());
    }

    @Test
    public void test01_07_none() {
        print(testName.getMethodName());
        TokenSeparator token = new TokenSeparator(SeparatorEnum.NONE);
        assertEquals(SeparatorEnum.NONE, token.getSep());
    }

    @Test
    public void testToString() {
        String strExpected = "SeparatorToken{středník}";
        String strResult = new TokenSeparator(SeparatorEnum.SEMICOLON).toString();
        assertEquals(strExpected, strResult);
    }
}
