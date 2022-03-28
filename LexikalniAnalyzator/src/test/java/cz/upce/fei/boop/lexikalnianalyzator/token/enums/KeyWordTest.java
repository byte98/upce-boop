package cz.upce.fei.boop.lexikalnianalyzator.token.enums;

import static cz.upce.fei.boop.lexikalnianalyzator.token.enums.KeyWord.*;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kasi0004
 */
public class KeyWordTest {

    public KeyWordTest() {
    }

    @Test
    public void testValues() {
        KeyWord[] keysExpected = {BEGIN, END, FOR, WHILE, IF, THEN, ELSE, RETURN, NO_KEY};
        KeyWord[] keysResult = KeyWord.values();
        assertArrayEquals(keysExpected, keysResult);
    }

    @Test
    public void testValueOf() {
        KeyWord expected = BEGIN;
        KeyWord result = KeyWord.valueOf("BEGIN");
        assertEquals(expected, result);
    }

    @Test
    public void testGetName() {
        String[] expected = {"begin", "end", "for", "while", "if", "then", "else", "return", "no key"};
        String[] result = new String[KeyWord.values().length];
        KeyWord[] keys = KeyWord.values();
        for (int i = 0; i < KeyWord.values().length; i++) {
            result[i] = keys[i].getName();
        }
        assertArrayEquals(expected, result);
    }

    @Test
    public void testToString() {
        String strExpected = "KeyWord{key=begin}";
        String strResult = BEGIN.toString();
        assertEquals(strExpected,strResult);
    }

}
