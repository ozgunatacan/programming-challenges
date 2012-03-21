package problems.stackofflapjacks;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;

public class MainTest {
    Main test;

    private static ByteArrayOutputStream outputActual = new ByteArrayOutputStream();
    private static String outputExpected;

    @Before
    public void setUp() throws Exception {
        test = new Main();
        test.setTestData(getClass().getClassLoader().getResourceAsStream("stackofflapjacks/input"),new PrintStream(outputActual,true));
        outputExpected = IOUtils.toString(getClass().getClassLoader().getResourceAsStream("stackofflapjacks/output"));
    }

    @Test
    public void testSolution() throws Exception {
        test.solution();
        Thread.sleep(3000);
        System.err.println(outputActual.toString());
        assertThat(outputActual.toString(),equalToIgnoringWhiteSpace(outputExpected));
    }

    @Test
    public void testFlip(){
        int[] a = {5,4,3,2,1};
        int[] a2 = {5,1,2,3,4};
        int[] aFlip = {1,2,3,4,5};
        test.flip(a,1);
        assertThat(a,is(aFlip));

        test.flip(a2,1);
        test.flip(a2,2);
        assertThat(a2,is(aFlip));
    }

    @Test
    public void testMaxIndex(){
        int[] a = {3,4,5,2,1};
        assertThat(test.maxIndex(a,1),is(1));
        assertThat(test.maxIndex(a,4),is(2));
    }


    @Test
    public void testIsSorted(){
        int[] a = {1,2,3,4,5,6,7,8,9};
        int[] aFlip = {9,8,7,6,5,4,3,2,1};
        int[] b = {1,2,3,4};
        int[] bFlip = {4,3,2,1};
        assertThat(test.isSorted(a),is(true));
        assertThat(test.isSorted(aFlip),is(false));
        assertThat(test.isSorted(b),is(true));
    }

}
