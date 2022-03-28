/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package util;

import java.awt.Color;
import static java.awt.Color.*;
import org.junit.Test;
import static org.junit.Assert.*;
import static util.Barva.*;

/**
 *
 * @author karel@simerda.cz
 */
public class BarvaTest {

    public BarvaTest() {
    }

    @Test
    public void testValues() {
        Barva[] barvyResult = Barva.values();
        Barva[] barvyExpected = {BILA, CERNA, CERVENA, ZELENA, MODRA};
        assertArrayEquals(barvyExpected, barvyResult);
    }

    @Test
    public void testValueOf() {
        Barva result = Barva.valueOf("BILA");
        Barva expected = BILA;
        assertEquals(expected, result);
    }

    @Test
    public void testGetColor() {
        Color[] barvyResult = {BILA.getColor(), CERNA.getColor(), CERVENA.getColor(), ZELENA.getColor(), MODRA.getColor()};
        Color[] barvyExpected = {WHITE, BLACK, RED, GREEN, BLUE};
        assertArrayEquals(barvyExpected, barvyResult);
    }

    @Test
    public void testGetNazev() {
        String[] barvyResult = {BILA.getNazev(), CERNA.getNazev(), CERVENA.getNazev(), ZELENA.getNazev(), MODRA.getNazev()};
        String[] barvyExpected = {"bílá", "černá", "červená", "zelená", "modrá"};
        assertArrayEquals(barvyExpected, barvyResult);
    }

    @Test
    public void testToString() {
        String[] barvyResult = {BILA.toString(), CERNA.toString(), CERVENA.toString(), ZELENA.toString(), MODRA.toString()};
        String[] barvyExpected = {"bílá", "černá", "červená", "zelená", "modrá"};
        assertArrayEquals(barvyExpected, barvyResult);
    }

}
