package cz.upce.fei.boop.lexikalnianalyzator.token.enums;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kasi0004
 */
public class SeparatorEnumTest {
    
    public SeparatorEnumTest() {
    }

    @Test
    public void testValues() {
    }

    @Test
    public void testValueOf() {
    }

    @Test
    public void testGetNazev() {
    }

    
    @Test
    public void testToString() {
        String strExpected = "Separator{středník}";
        String strResult =  SeparatorEnum.SEMICOLON.toString();
        assertEquals(strExpected, strResult);
    }
}
