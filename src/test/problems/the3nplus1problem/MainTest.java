package problems.the3nplus1problem;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class MainTest {
    @Test
    public void testFirstRange () {
        Main main = new Main();
        assertEquals(125, main.findMax(100, 200));
        assertEquals(174, main.findMax(900, 1000));
        assertEquals(20, main.findMax(1, 10));
        assertEquals(89, main.findMax(201,210 ));
    }
}
