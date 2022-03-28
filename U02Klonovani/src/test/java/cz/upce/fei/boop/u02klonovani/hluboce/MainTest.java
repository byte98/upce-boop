package cz.upce.fei.boop.u02klonovani.hluboce;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kasi0004
 */
public class MainTest {
    
    public MainTest() {
    }

    @Test
    public void testMain() throws Exception {
        Main.main(null);
    }
    
     @Test
    public void testMain2() throws Exception {
       new Main();
    }
    
}
