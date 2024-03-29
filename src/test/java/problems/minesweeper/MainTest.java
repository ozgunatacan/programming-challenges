package problems.minesweeper;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import problems.minesweeper.Main;

public class MainTest {
    
    Main test;
    private static final String INPUT = "4 4\n*...\n....\n.*..\n....\n" +
                                        "3 5\n**...\n.....\n.*...\n0 0";

    @Before
    public void setUp() throws Exception {
        test = new Main();
        test.setTest(true);
        test.setTestData(new ByteArrayInputStream(INPUT.getBytes()));
    }

    @Test
    public void testBegin() throws Exception {
        test.begin();
    }
}
