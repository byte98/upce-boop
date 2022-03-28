package cz.upce.fei.boop.lexikalnianalyzator.token;

import static cz.upce.fei.boop.lexikalnianalyzator.Counter.counter;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.TestName;

/**
 *
 * @author karel@simerda.cz
 */
public class TokenLongNumberTest {

    @Rule
    public TestName testName = new TestName();

    static void print(String methodName) {
        System.out.println(String.format("%03d TokenLongNumber.%s",
                counter++, methodName));
    }

    static final double DELTA = 1e-9;

    @Test
    public void test01_01_class() {
        print(testName.getMethodName());
        Token token = new TokenLongNumber(123456);
        assertEquals(TokenLongNumber.class, token.getClass());
    }

    @Test
    public void test01_01_value() {
        print(testName.getMethodName());
        TokenLongNumber token = new TokenLongNumber(123456);
        assertEquals(123456, token.getValue());
    }

    @Test
    public void testToString() {
        String strExpected = "LongNumberToken{value=123}";
        String strResult = new TokenLongNumber(123).toString();
        assertEquals(strExpected, strResult);
    }

}
