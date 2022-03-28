package cz.upce.fei.boop.lexikalnianalyzator.token;

import static cz.upce.fei.boop.lexikalnianalyzator.Counter.counter;
import cz.upce.fei.boop.lexikalnianalyzator.token.enums.OperatorEnum;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.TestName;

/**
 *
 * @author karel@simerda.cz
 */
public class TokenOperatorTest {

    @Rule
    public TestName testName = new TestName();

    static void print(String methodName) {
        System.out.println(String.format("%03d TokenOperator.%s",
                counter++, methodName));
    }

    static final double DELTA = 1e-9;

    public TokenOperatorTest() {
    }

    @Test
    public void test01_01_class() {
        print(testName.getMethodName());
        Token token = new TokenOperator(OperatorEnum.PLUS);
        assertEquals(TokenOperator.class, token.getClass());
    }

    @Test
    public void test01_02_plus() {
        print(testName.getMethodName());
        TokenOperator token = new TokenOperator(OperatorEnum.PLUS);
        assertEquals(OperatorEnum.PLUS, token.getOperator());
    }

    @Test
    public void test01_03_minus() {
        print(testName.getMethodName());
        TokenOperator token = new TokenOperator(OperatorEnum.MINUS);
        assertEquals(OperatorEnum.MINUS, token.getOperator());
    }

    @Test
    public void test01_04_multiply() {
        print(testName.getMethodName());
        TokenOperator token = new TokenOperator(OperatorEnum.MULTIPLY);
        assertEquals(OperatorEnum.MULTIPLY, token.getOperator());
    }

    @Test
    public void test01_05_divide() {
        print(testName.getMethodName());
        TokenOperator token = new TokenOperator(OperatorEnum.DIVIDE);
        assertEquals(OperatorEnum.DIVIDE, token.getOperator());
    }

    @Test
    public void test01_06_none() {
        print(testName.getMethodName());
        TokenOperator token = new TokenOperator(OperatorEnum.NONE);
        assertEquals(OperatorEnum.NONE, token.getOperator());
    }

    @Test
    public void testToString() {
        String strExpected = "OperatorToken{operator=plus}";
        String strResult = new TokenOperator(OperatorEnum.PLUS).toString();
        assertEquals(strExpected, strResult);
    }

}
