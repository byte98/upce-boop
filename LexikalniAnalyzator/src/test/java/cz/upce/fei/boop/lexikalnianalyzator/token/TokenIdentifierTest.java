package cz.upce.fei.boop.lexikalnianalyzator.token;

import static cz.upce.fei.boop.lexikalnianalyzator.Counter.counter;
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
public class TokenIdentifierTest {

    @Rule
    public TestName testName = new TestName();

    static void print(String methodName) {
        System.out.println(String.format("%03d TokenIdentifier.%s",
                counter++, methodName));
    }

    static final double DELTA = 1e-9;

    public TokenIdentifierTest() {
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
        Token token = new TokenIdentifier("abc123_");
        assertEquals(TokenIdentifier.class, token.getClass());
    }

    @Test
    public void test01_02_idetifier() {
        print(testName.getMethodName());
        TokenIdentifier token = new TokenIdentifier("abc123_");
        assertEquals("abc123_", token.getNazev());
    }

    @Test
    public void testToString() {
        String strExpected = "IdentifierToken{ident}";
        String strResult = new TokenIdentifier("ident").toString();
        assertEquals(strExpected, strResult);
    }
}
