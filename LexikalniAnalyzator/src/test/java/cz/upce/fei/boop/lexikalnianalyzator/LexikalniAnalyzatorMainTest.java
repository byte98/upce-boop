/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.upce.fei.boop.lexikalnianalyzator;

import cz.upce.fei.boop.lexikalnianalyzator.statemachine.AnalyzerException;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class LexikalniAnalyzatorMainTest
{
    
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    private final String exceptionFile = "__exception.txt";
    private final String[] exceptionInput = {"#"};
    private final String correctFile = "__input.txt";
    private final String[] correctInput = {"begin;"};
    private final String[] correctOutput = {
        "STATE1: Nepovoleny znak '?'/65535",
        "KeyToken{klicoveSlovo=begin}",
        "SeparatorToken{středník}",
        "SeparatorToken{bílý znak}",
        "SeparatorToken{bílý znak}"};
    
    /**
     * Writes strings to file
     * @param path Path to file to which will be content written into
     * @param content Content which will be written to file
     */
    private void writeToFile(String path, String[] content) throws IOException
    {
        FileWriter fw = new FileWriter(path);
        for (String line: content)
        {
            fw.write(line + System.lineSeparator());
        }
        fw.close();
    }
    
    @Before
    public void makeFiles() throws IOException
    {
        this.writeToFile(this.correctFile, this.correctInput);
        this.writeToFile(this.exceptionFile, this.exceptionInput);
    }
    
    @Before
    public void setUpStreams()
    {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams()
    {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }
    
    @After
    public void cleanFiles()
    {
        new File(this.correctFile).delete();
        new File(this.exceptionFile).delete();
    }
   
    @Test
    public void test001_01_println() throws IOException
    {
        LexikalniAnalyzatorMain.main();
        assertEquals("Chybí vstupní argument se jménem souboru", outContent.toString().trim());
    }
   
    @Test(expected = FileNotFoundException.class)
    public void test001_02_FileNotFoundException() throws IOException
    {
        LexikalniAnalyzatorMain.main("cesta k neexistujicímu souboru");
        fail();
    }
    
    @Test(expected = IOException.class)
    public void test001_03_IOException() throws IOException
    {
        LexikalniAnalyzatorMain.main("cesta k neexistujicímu souboru");
        fail();
    }
    
    @Test(expected = AssertionError.class)
    public void test002_01_AssertionError() throws IOException
    {
        LexikalniAnalyzatorMain.main(this.exceptionFile);
        fail(); 
    }
    
    @Test
    public void test002_02_Commands() throws IOException
    {
        LexikalniAnalyzatorMain.main(this.correctFile);
        String[] results = this.outContent.toString().trim().split(System.lineSeparator());
        if (results.length == this.correctOutput.length)
        {
            for (int i = 0; i < results.length; i++)
            {
               assertEquals(results[i], this.correctOutput[i]);
            }
        }
        else fail();
    }
    
    @Test
    public void test002_03_instanceCoverage()
    {
        LexikalniAnalyzatorMain main = new LexikalniAnalyzatorMain();
        assertNotNull(main);
    }
}
