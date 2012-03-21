package problems.quicksort;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringWhiteSpace;
import static org.hamcrest.Matchers.is;

public class MainTest {
        Main test;

    private static ByteArrayOutputStream outputActual = new ByteArrayOutputStream();
    private static String outputExpected;

    @Before
    public void setUp() throws Exception {
        test = new Main();
        test.setTestData(getClass().getClassLoader().getResourceAsStream("quicksort/input"),new PrintStream(outputActual,true));
        outputExpected = IOUtils.toString(getClass().getClassLoader().getResourceAsStream("quicksort/output"));
    }

    @Test
    public void testSolution() throws Exception {
        test.solution();
        Thread.sleep(3000);
        System.err.println(outputActual.toString());
        assertThat(outputActual.toString(),equalToIgnoringWhiteSpace(outputExpected));
    }

    @Test
    public void testQuickSort() {
        Integer[] a = {3,4,5,6,1,0,12,34,2,6,8,9};
        test.quicksort(Main.Mode.FIRST,a,0,a.length-1);
        Integer[] aSorted = {0, 1, 2, 3, 4, 5, 6, 6, 8, 9, 12, 34};
        System.err.println(Arrays.toString(a));
    }
}
