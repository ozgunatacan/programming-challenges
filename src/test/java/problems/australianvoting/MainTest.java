package problems.australianvoting;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class MainTest {
    Main test;

    private static ByteArrayOutputStream outputActual = new ByteArrayOutputStream();
    private static String outputExpected;

    @Before
    public void setUp() throws Exception {
        test = new Main();
        test.setTestData(getClass().getClassLoader().getResourceAsStream("voting.input"),new PrintStream(outputActual,true));
        outputExpected = IOUtils.toString(getClass().getClassLoader().getResourceAsStream("voting.output"));
    }

    @Test
    public void testSolution() throws Exception {
        test.solution();
        Thread.sleep(3000);
        System.err.println(outputActual.toString());
        assertThat(outputActual.toString(),equalTo(outputExpected));
    }

}
