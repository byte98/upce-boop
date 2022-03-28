package cz.upce.fei.boop.lexikalnianalyzator.token.enums;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kasi0004
 */
public class TokenTypeTest {
    
    public TokenTypeTest() {
    }

    @Test
    public void testValues() {
    }

    @Test
    public void testValueOf() {
    }

    @Test
    public void testToString() {
         String strExpected = "Token{klíčové slovo}";
        String strResult = TokenType.KEY.toString();
        assertEquals(strExpected,strResult);
    }
    
}
