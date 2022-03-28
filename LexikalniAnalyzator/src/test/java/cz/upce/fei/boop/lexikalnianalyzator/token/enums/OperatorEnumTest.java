package cz.upce.fei.boop.lexikalnianalyzator.token.enums;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kasi0004
 */
public class OperatorEnumTest {

    public OperatorEnumTest() {
    }

    @Test
    public void testValues() {
    }

    @Test
    public void testValueOf() {
    }

    @Test
    public void testGetName() {
    }

    @Test
    public void testToString() {
        String strExpected = "Operator{plus}";
        String strResult = OperatorEnum.PLUS.toString();
        assertEquals(strExpected, strResult);
    }

}
