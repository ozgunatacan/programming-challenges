package problems.lcddisplay;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.hamcrest.Matcher;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


import java.io.*;

public class MainTest {
    Main test;

    private static ByteArrayOutputStream outputActual = new ByteArrayOutputStream();
    private static String outputExpected;

    @Before
    public void setUp() throws Exception {
        test = new Main();
        test.setTestData(getClass().getClassLoader().getResourceAsStream("lcddisplay.input"),new PrintStream(outputActual,true));
        outputExpected = IOUtils.toString(getClass().getClassLoader().getResourceAsStream("lcddisplay.output"));
    }

    @Test
    public void testSolution() throws Exception {
        test.solution();
        Thread.sleep(3000);
        System.err.println(outputActual.toString());
        assertThat(outputExpected,equalToIgnoringWhiteSpace(outputActual.toString()));
    }

}
