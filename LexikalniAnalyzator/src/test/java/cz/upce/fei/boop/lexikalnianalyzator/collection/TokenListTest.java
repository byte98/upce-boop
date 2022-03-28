package cz.upce.fei.boop.lexikalnianalyzator.collection;

import static cz.upce.fei.boop.lexikalnianalyzator.Counter.counter;
import cz.upce.fei.boop.lexikalnianalyzator.token.Token;
import cz.upce.fei.boop.lexikalnianalyzator.token.TokenOperator;
import cz.upce.fei.boop.lexikalnianalyzator.token.enums.OperatorEnum;
import cz.upce.fei.boop.lexikalnianalyzator.token.enums.TokenType;
import java.util.Iterator;
import java.util.NoSuchElementException;
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
public class TokenListTest {

    @Rule
    public TestName testName = new TestName();

    static void print(String methodName) {
        System.out.println(String.format("%03d ", counter++) + "TokenList." + methodName);
    }

    static final double DELTA = 1e-9;

    TokenList list;

    class TokenTest extends Token {

        public TokenTest() {
            super(TokenType.NONE);
        }

    }

    public TokenListTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        list = new TokenList();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void test00_00_instance() {
        print(testName.getMethodName());
        assertNotNull(list);
    }

    @Test
    public void test01_01_emptyListSize() {
        print(testName.getMethodName());
        assertEquals(0, list.getSize());
    }

    @Test
    public void test01_02_emptyListCapacity() {
        print(testName.getMethodName());
        
    }

//    @Test(expected = ArrayIndexOutOfBoundsException.class)
//    public void test01_03_emptyListGetToken() {
//        print(testName.getMethodName());
//        
//        fail();
//    }

    @Test
    public void test01_04_emptyListItegator() {
        print(testName.getMethodName());
        assertNotNull(list.iterator());
    }

    @Test
    public void test01_05_emptyListItegatorHasNext() {
        print(testName.getMethodName());
       
    }

    @Test(expected = NoSuchElementException.class)
    public void test01_06_emptyListItegatornext() {
        print(testName.getMethodName());
        Iterator<Token> listIterator = list.iterator();
        assertEquals(null, listIterator.next());
        fail();
    }

    @Test
    public void test02_01_addListSize() {
        print(testName.getMethodName());
        list.add(new TokenOperator(OperatorEnum.PLUS));
        assertEquals(1, list.getSize());
    }

    @Test
    public void test02_02_addListClear() {
        print(testName.getMethodName());
        list.add(new TokenOperator(OperatorEnum.PLUS));
        list.add(new TokenOperator(OperatorEnum.PLUS));
        list.clear();
        assertEquals(0, list.getSize());
    }

//    @Test
//    public void test02_02_addListSize() {
//        print(testName.getMethodName());
//        list.add(new TokenOperator(OperatorEnum.PLUS));
//        
//        assertEquals(2, list.getSize());
//    }

    @Test(expected = NullPointerException.class)
    public void test02_03_addListNull() {
        print(testName.getMethodName());
        list.add(null);
        fail();
    }

    @Test
    public void test03_02_addListCapacity() {
        print(testName.getMethodName());
        list.add(new TokenOperator(OperatorEnum.PLUS));
        assertEquals(10, list.getCapacity());
    }

    @Test
    public void test03_03_addListCapacity() {
        print(testName.getMethodName());
        for (int i = 0; i < 10; i++) {
            list.add(new TokenTest());
        }
        assertEquals(10, list.getCapacity());
    }

    @Test
    public void test03_04_addListCapacity() {
        print(testName.getMethodName());
        for (int i = 0; i < 11; i++) {
            list.add(new TokenTest());
        }
        assertEquals(20, list.getCapacity());
    }

    @Test
    public void test03_05_addListCapacityClear() {
        print(testName.getMethodName());
        for (int i = 0; i < 11; i++) {
            list.add(new TokenTest());
        }
        list.clear();
        assertEquals(10, list.getCapacity());
    }

    @Test
    public void test04_01_addListGetToken() {
        print(testName.getMethodName());
        list.add(new TokenTest());
        assertEquals(TokenType.NONE, list.getToken(0).getType());
    }

    @Test
    public void test04_02_addListGetToken() {
        print(testName.getMethodName());
        list.add(new TokenTest());
       
        //assertEquals(TokenTest.class, token.getClass());
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void test04_03_addListGetTokenOutOf() {
        print(testName.getMethodName());
        list.add(new TokenTest());
        Token token = list.getToken(1);
        assertEquals(TokenTest.class, token.getClass());
        fail();
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void test04_04_addListGetTokenOutOf() {
        print(testName.getMethodName());
        list.add(new TokenTest());
        Token token = list.getToken(-1);
        assertEquals(TokenTest.class, token.getClass());
        fail();
    }
    
    @Test
    public void test05_01_iteratorNext()
    {
        print(testName.getMethodName());
        TokenTest expected = new TokenTest();
        list.add(expected);
        Iterator<Token> it = list.iterator();
        if (it.hasNext())
        {
            TokenTest result = (TokenTest)it.next();
            assertEquals(expected, result);
        }
        else
        {
            fail();
        }        
    }
    
}