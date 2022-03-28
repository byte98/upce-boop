package cz.upce.fei.boop.lexikalnianalyzator.statemachine;

import static cz.upce.fei.boop.lexikalnianalyzator.Counter.counter;
import cz.upce.fei.boop.lexikalnianalyzator.LexikalniAnalyzatorMain;
import cz.upce.fei.boop.lexikalnianalyzator.collection.TokenList;
import cz.upce.fei.boop.lexikalnianalyzator.token.*;
import cz.upce.fei.boop.lexikalnianalyzator.token.enums.KeyWord;
import static cz.upce.fei.boop.lexikalnianalyzator.token.enums.KeyWord.*;
import cz.upce.fei.boop.lexikalnianalyzator.token.enums.*;
import static cz.upce.fei.boop.lexikalnianalyzator.token.enums.SeparatorEnum.*;
import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.TestName;

/**
 * TODO Sestavit testovací případy třídy StateMachine s cílem dosáhnout co
 * největšího pokrytí.
 * <p>
 * Česky stručný popis JUnit 4 je na http://voho.eu/wiki/java-junit/ nebo
 * https://www.itnetwork.cz/java/testovani/testovani-v-jave-prvni-unit-test-v-junit
 * <p>
 * Třída {@code StateMachine} je součásti projektu Lexikální analyzátor, který
 * byl v minulých letech zadán jako první semestrální práce předmětu objektově
 * orientované programování. Pro vypracování testu a pochopení celého projektu
 * je k dispozici písemné zadání.
 *
 * <p>
 * Požadavky na testovací případy (metody):
 *
 * <ol>
 * <li>Doporučuje se volit název testovací metody podle typu kontroly s vhodným
 * číselným prefixem. Velmi se osvědčilo používat tvarové číslování. Například
 * dvouúrovňové, tj. test001_01, test002_20 apod. To umožňuje vkládat do
 * testovací třídy další testy, které patří do stejného typu kontroly. Číselná
 * identifikace umožňuje rychlejší orientaci v testovacích metodách.
 *
 * <li> Každá testovací metoda bude obsahovat na prvním řádku výpis názvu testu
 * tímto příkazem {@code print(testName.getMethodName()) }. Tím se vytiskne
 * pořadové číslo test a název metody.
 *
 * <li> Instaci třídy {@code StateMachine} nemusíme v testovací metodě vytvářet,
 * protože je automaticky vytvořena před každým spuštěním jakékoliv testovací
 * metody.
 *
 * <li>Instance testované třídy se automaticky vytváří metodou {@code setUp} s
 * anotací {code @Before}.
 *
 * <li>Úklid po vykonání testovací metody provede metoda {@code tearDown} s
 * anotací {code @After}.
 *
 * <li> Velmi se doporučuje aby byla pouze jedna kontrola v testovací metodě,
 * tj. jedna metoda {@code assert}.
 *
 *
 * <li>Pokud se kontroluje reálná číselná hodnota (float, double), tak je nutné
 * zvolit třetí parametr, kterým se volí přesnost porovnání. V našem případě
 * budeme používat přesnost, která dána konstantou {@code DELTA}.
 *
 *
 *
 * </ol>
 *
 *
 * @author
 */
public class StateMachineTest {

    @Rule
    public TestName testName = new TestName();

    static void print(String methodName) {
        System.out.println(String.format("%03d StateMachine.%s   ",
                counter++, methodName));
    }

    static final double DELTA = 1e-9;

    Machine machine;
    TokenList tokens;

    public StateMachineTest() {
    }

    @Before
    public void setUp() {
        tokens = new TokenList();
        machine = new StateMachine(tokens);
    }

    @After
    public void tearDown() {
        tokens = null;
        machine = null;
    }

    @Test
    public void test001_02_identifier() {
        print(testName.getMethodName());
        machine.execute("abcdef\n");
        assertEquals(TokenType.IDENTIFIER, tokens.getToken(0).getType());
    }
    
    // Tests of execute method
    @Test
    public void test002_01_state3()
    {
        print(testName.getMethodName());
        machine.execute('0');
        assertEquals(State.STATE3, machine.getState());
    }
    
    @Test
    public void test002_02_state6()
    {
        print(testName.getMethodName());
        machine.execute('5');
        assertEquals(State.STATE6, machine.getState());
    }
    
    
    @Test
    public void test002_03_state1()
    {
        print(testName.getMethodName());
        machine.execute(' ');
        assertEquals(State.STATE1, machine.getState());
    }
    
    
    @Test
    public void test002_04_state1()
    {
        print(testName.getMethodName());
        machine.execute('+');
        assertEquals(State.STATE1, machine.getState());
    }

    
    @Test
    public void test002_05_state15()
    {
        print(testName.getMethodName());
        machine.execute('{');
        assertEquals(State.STATE15, machine.getState());
    }
    
    
    @Test(expected = AnalyzerException.class)
    public void test002_06_analyzerException()
    {
        print(testName.getMethodName());
        machine.execute('#');
        fail();
    }

    @Test
    public void test002_07_state5()
    {
        print(testName.getMethodName());
        machine.execute("01");
        assertEquals(State.STATE5, machine.getState());
    }
    
    @Test
    public void test002_08_state4()
    {
        print(testName.getMethodName());
        machine.execute("0x");
        assertEquals(State.STATE4, machine.getState());
    }
   
    @Test
    public void test002_09_state7()
    {
        print(testName.getMethodName());
        machine.execute("0.");
        assertEquals(State.STATE7, machine.getState());
    }
    
    
    @Test
    public void test002_10_state1()
    {
        print(testName.getMethodName());
        machine.execute("0x;");
        assertEquals(State.STATE1, machine.getState());
    }
    
     @Test(expected = AnalyzerException.class)
    public void test002_11_AnalyzerException()
    {
        print(testName.getMethodName());
        machine.execute("01.");
        fail();
    }
    
    @Test
    public void test002_12_state1()
    {
        print(testName.getMethodName());
        machine.execute("01;");
        assertEquals(State.STATE1, machine.getState());
    }
    
     @Test
    public void test002_13_state9()
    {
        print(testName.getMethodName());
        machine.execute("2e");
        assertEquals(State.STATE9, machine.getState());
    }
    
     @Test
    public void test002_14_state12()
    {
        print(testName.getMethodName());
        machine.execute("2e5");
        assertEquals(State.STATE12, machine.getState());
    }
    
    @Test
    public void test002_15_state1()
    {
        print(testName.getMethodName());
        machine.execute("1;");
        assertEquals(State.STATE1, machine.getState());
    }
    
    @Test
    public void test002_16_state8()
    {
        print(testName.getMethodName());
        machine.execute("3.1");
        assertEquals(State.STATE8, machine.getState());
    }
    
    @Test
    public void test002_17_state9()
    {
        print(testName.getMethodName());
        machine.execute("3.e");
        assertEquals(State.STATE9, machine.getState());
    }
    
    @Test
    public void test002_18_state1()
    {
        print(testName.getMethodName());
        machine.execute("3.;");
        assertEquals(State.STATE1, machine.getState());
    }
    
    
    @Test(expected = AnalyzerException.class)
    public void test002_19_AnalyzerException()
    {
        print(testName.getMethodName());
        machine.execute("0.e;");
        fail();
    }
    
    @Test
    public void test002_20_state1()
    {
        print(testName.getMethodName());
        machine.execute("3.1;");
        assertEquals(State.STATE1, machine.getState());
    }
    
    @Test
    public void test002_21_state11()
    {
        print(testName.getMethodName());
        machine.execute("1e+");
        assertEquals(State.STATE11, machine.getState());
    }
    
    @Test
    public void test002_22_state11()
    {
        print(testName.getMethodName());
        machine.execute("1e-");
        assertEquals(State.STATE11, machine.getState());
    }
    
    @Test
    public void test002_23_state1()
    {
        print(testName.getMethodName());
        machine.execute("1.1+");
        assertEquals(State.STATE1, machine.getState());
    }
    
    @Test(expected = AnalyzerException.class)
    public void test002_24_AnalyzerException()
    {
        print(testName.getMethodName());
        machine.execute("1e-;");
        fail();
    }
    
    @Test
    public void test002_25_state1()
    {
        print(testName.getMethodName());
        machine.execute(',');
        assertEquals(State.STATE1, machine.getState());
    }
    
    @Test
    public void test002_26_state1()
    {
        print(testName.getMethodName());
        machine.execute(':');
        assertEquals(State.STATE1, machine.getState());
    }
    
    @Test
    public void test002_27_state1()
    {
        print(testName.getMethodName());
        machine.execute('=');
        assertEquals(State.STATE1, machine.getState());
    }
    
    @Test
    public void test002_28_state1()
    {
        print(testName.getMethodName());
        machine.execute('+');
        assertEquals(State.STATE1, machine.getState());
    }
    
    @Test
    public void test002_29_state1()
    {
        print(testName.getMethodName());
        machine.execute('-');
        assertEquals(State.STATE1, machine.getState());
    }
    
    @Test
    public void test002_30_state1()
    {
        print(testName.getMethodName());
        machine.execute('*');
        assertEquals(State.STATE1, machine.getState());
    }
    
    @Test
    public void test002_31_state1()
    {
        print(testName.getMethodName());
        machine.execute('/');
        assertEquals(State.STATE1, machine.getState());
    }
    
    @Test
    public void test002_32_state1()
    {
        print(testName.getMethodName());
        machine.execute('{');
        assertEquals(State.STATE15, machine.getState());
    }
    @Test
    public void test002_33_state1()
    {
        print(testName.getMethodName());
        machine.execute("{}");
        assertEquals(State.STATE1, machine.getState());
    }
    
    @Test
    public void test002_34_state4() throws IOException
    {
        print(testName.getMethodName());
        machine.execute("0xa");
        assertEquals(State.STATE4, machine.getState());
    }
    
    @Test
    public void test002_35_state4() throws IOException
    {
        print(testName.getMethodName());
        machine.execute("0x1");
        assertEquals(State.STATE4, machine.getState());
    }
    
    @Test
    public void test002_36_state9()
    {
        print(testName.getMethodName());
        machine.execute("3.5e");
        assertEquals(State.STATE9, machine.getState());
    }
    
    @Test
    public void test002_37_state1()
    {
        print(testName.getMethodName());
        machine.execute("3.5e12;");
        assertEquals(State.STATE1, machine.getState());
    }
    
    @Test (expected = AnalyzerException.class)
    public void test003_01_AnalyzerException()
    {
        print(testName.getMethodName());
        machine.execute("a#");
        fail();
    }

    @Test (expected = AnalyzerException.class)
    public void test003_02_AnalyzerException()
    {
        print(testName.getMethodName());
        machine.execute("0#");
        fail();
    }
    
    @Test (expected = AnalyzerException.class)
    public void test003_03_AnalyzerException()
    {
        print(testName.getMethodName());
        machine.execute("0x#");
        fail();
    }
    
    @Test (expected = AnalyzerException.class)
    public void test003_04_AnalyzerException()
    {
        print(testName.getMethodName());
        machine.execute("1#");
        fail();
    }
    
    @Test (expected = AnalyzerException.class)
    public void test003_05_AnalyzerException()
    {
        print(testName.getMethodName());
        machine.execute("1.#");
        fail();
    }
    
    @Test (expected = AnalyzerException.class)
    public void test003_06_AnalyzerException()
    {
        print(testName.getMethodName());
        machine.execute("1.5#");
        fail();
    }
    @Test (expected = AnalyzerException.class)
    public void test003_07_AnalyzerException()
    {
        print(testName.getMethodName());
        machine.execute("10e2#");
        fail();
    }
}