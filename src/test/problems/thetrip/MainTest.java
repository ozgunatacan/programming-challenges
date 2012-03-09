package problems.thetrip;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

public class MainTest {
    Main test;
    private static final String INPUT = "1\n10.00\n20.00\n30.00\n" +
                                        "2\n15.00\n15.01\n3.00\n3.01\n" +
                                        "3\n5000.00\n11.11\n11.11\n11.11\n11.11\n" +
                                        "4\n0.01\n0.03\n0.03\n" +
                                        "5\n25.00\n25.00\n25.00\n28.00\n" +
                                        "6\n10.01\n15.25\n18.96\n" +
                                        "7\n25.03\n25.00\n25.00\n25.00\n"+
            "15\n0.01\n0.03\n0.03\n0.03\n0.03\n0.03\n0.03\n0.03\n0.03\n0.03\n0.03\n0.03\n0.03\n0.03\n0.03\n0";

    private static final String EXPECTED_OUTPUT = "$10.00\n" +
                                                "$11.99\n" +
                                                "$3991.11\n" +
                                                "$0.01\n" +
                                                "$2.25\n" +
                                                "$4.73\n" +
                                                "$0.02\n"+
                                                "$0.02";

    private static ByteArrayOutputStream testOut = new ByteArrayOutputStream();

    @Before
    public void setUp() throws Exception {
        test = new Main();
        test.setTestData(new ByteArrayInputStream(INPUT.getBytes()),new PrintStream(testOut,true));
    }

    @Test
    public void testBegin() throws Exception {
        test.begin();
        Thread.sleep(3000);
        assertTestOutput();
    }
    
    public void assertTestOutput() {
       String actual = testOut.toString();
       Assert.assertEquals(EXPECTED_OUTPUT,actual);
    }
}
