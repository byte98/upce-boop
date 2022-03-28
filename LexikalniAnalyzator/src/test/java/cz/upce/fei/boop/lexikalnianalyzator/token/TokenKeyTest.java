package cz.upce.fei.boop.lexikalnianalyzator.token;

import static cz.upce.fei.boop.lexikalnianalyzator.Counter.counter;
import cz.upce.fei.boop.lexikalnianalyzator.token.enums.KeyWord;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.TestName;

/**
 *
 * @author karel@simerda.cz
 */
public class TokenKeyTest {

    @Rule
    public TestName testName = new TestName();

    static void print(String methodName) {
        System.out.println(String.format("%03d ", counter++) + "TokenKey." + methodName);
    }

    static final double DELTA = 1e-9;

    public TokenKeyTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void test01_01_class() {
        print(testName.getMethodName());
        Token token = new TokenKey(KeyWord.BEGIN);
        assertEquals(TokenKey.class, token.getClass());
    }

    @Test
    public void test01_02_begin() {
        print(testName.getMethodName());
        TokenKey token = new TokenKey(KeyWord.BEGIN);
        assertEquals(KeyWord.BEGIN, token.getKeyWord());
    }

    @Test
    public void test01_03_else() {
        print(testName.getMethodName());
        TokenKey token = new TokenKey(KeyWord.ELSE);
        assertEquals(KeyWord.ELSE, token.getKeyWord());
    }

    @Test
    public void test01_04_end() {
        print(testName.getMethodName());
        TokenKey token = new TokenKey(KeyWord.END);
        assertEquals(KeyWord.END, token.getKeyWord());
    }

    @Test
    public void test01_05_for() {
        print(testName.getMethodName());
        TokenKey token = new TokenKey(KeyWord.FOR);
        assertEquals(KeyWord.FOR, token.getKeyWord());
    }

    @Test
    public void test01_06_if() {
        print(testName.getMethodName());
        TokenKey token = new TokenKey(KeyWord.IF);
        assertEquals(KeyWord.IF, token.getKeyWord());
    }

    @Test
    public void test01_07_noKey() {
        print(testName.getMethodName());
        TokenKey token = new TokenKey(KeyWord.NO_KEY);
        assertEquals(KeyWord.NO_KEY, token.getKeyWord());
    }

    @Test
    public void test01_08_return() {
        print(testName.getMethodName());
        TokenKey token = new TokenKey(KeyWord.RETURN);
        assertEquals(KeyWord.RETURN, token.getKeyWord());
    }

    @Test
    public void test01_10_then() {
        print(testName.getMethodName());
        TokenKey token = new TokenKey(KeyWord.THEN);
        assertEquals(KeyWord.THEN, token.getKeyWord());
    }

    @Test
    public void test01_11_while() {
        print(testName.getMethodName());
        TokenKey token = new TokenKey(KeyWord.WHILE);
        assertEquals(KeyWord.WHILE, token.getKeyWord());
    }

    @Test
    public void testToString() {
        String strExpected = "KeyToken{klicoveSlovo=begin}";
        String strResult = new TokenKey(KeyWord.BEGIN).toString();
        assertEquals(strExpected, strResult);
    }

}
