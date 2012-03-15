package problems.stackemup;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class MainTest {
    Main test;

    private static ByteArrayOutputStream outputActual = new ByteArrayOutputStream();
    private static String outputExpected;

    @Before
    public void setUp() throws Exception {
        test = new Main();
        test.setTestData(getClass().getClassLoader().getResourceAsStream("stackemup.input"),new PrintStream(outputActual,true));
        outputExpected = IOUtils.toString(getClass().getClassLoader().getResourceAsStream("stackemup.output"));
    }

    @Test
    public void testSolution() throws Exception {
        test.solution();
        Thread.sleep(3000);
        System.err.println(outputActual.toString());
        assertThat(outputActual.toString(),equalTo(outputExpected));
    }

    @Test
    public void testCardCreationAndString() {
        Main.Card card = new Main.Card(52);
        assertThat(card.toString(),is("Ace of Spades"));
        card = new Main.Card(13);
        assertThat(card.toString(),is("Ace of Clubs"));
        card = new Main.Card(14);
        assertThat(card.toString(),is("2 of Diamonds"));
    }
}
