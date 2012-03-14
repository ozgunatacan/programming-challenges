package problems.pokerhands;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

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
        test.setTestData(getClass().getClassLoader().getResourceAsStream("pokerhands.input"),new PrintStream(outputActual,true));
        outputExpected = IOUtils.toString(getClass().getClassLoader().getResourceAsStream("pokerhands.output"));
    }

    @Test
    public void testSolution() throws Exception {
        test.solution();
        Thread.sleep(3000);
        System.err.println(outputActual.toString());
        assertThat(outputActual.toString(),equalToIgnoringWhiteSpace(outputExpected));
    }

    @Test
    public void testCardConstruction() {
        Main.Card card = new Main.Card("TS");
        Main.Card card1 = new Main.Card("2S");
        assertThat(card.compareTo(card1), is(1));
        card = new Main.Card("TS");
        card1 = new Main.Card("TC");
        assertThat(card.compareTo(card1), is(0));
        card = new Main.Card("TH");
        card1 = new Main.Card("JS");
        assertThat(card.compareTo(card1), is(-1));
        card = new Main.Card("AH");
        card1 = new Main.Card("KD");
        assertThat(card.compareTo(card1), is(1));
        card = new Main.Card("TH");
        card1 = new Main.Card("TD");
        assertThat(card.compareTo(card1), is(0));
    }

    @Test
    public void testHighCardHandEquality() {
        Main.Card card = new Main.Card("TH");
        Main.Card card1 = new Main.Card("8C");
        Main.Card card2 = new Main.Card("6S");
        Main.Card card3 = new Main.Card("2D");
        Main.Card card4 = new Main.Card("4S");
        List<Main.Card> cards1 = Arrays.asList(card,card1,card2,card3,card4);
        Main.Hand hand1 = new Main.HighCard(cards1);
        
        Main.Card card5 = new Main.Card("TH");
        Main.Card card6 = new Main.Card("8C");
        Main.Card card7 = new Main.Card("6S");
        Main.Card card8 = new Main.Card("2D");
        Main.Card card9 = new Main.Card("4S");
        List<Main.Card> cards2 = Arrays.asList(card5,card6,card7,card8,card9);
        Main.Hand hand2 = new Main.HighCard(cards2);
        assertThat(hand1.compareTo(hand2), is(0));
    }

    @Test
    public void testHighCardHandEquality2() {
        Main.Card card = new Main.Card("TH");
        Main.Card card1 = new Main.Card("8S");
        Main.Card card2 = new Main.Card("6D");
        Main.Card card3 = new Main.Card("2C");
        Main.Card card4 = new Main.Card("4S");
        List<Main.Card> cards1 = Arrays.asList(card,card1,card2,card3,card4);
        Main.Hand hand1 = new Main.HighCard(cards1);

        Main.Card card5 = new Main.Card("TH");
        Main.Card card6 = new Main.Card("8C");
        Main.Card card7 = new Main.Card("6S");
        Main.Card card8 = new Main.Card("2D");
        Main.Card card9 = new Main.Card("4S");
        List<Main.Card> cards2 = Arrays.asList(card5,card6,card7,card8,card9);
        Main.Hand hand2 = new Main.HighCard(cards2);
        assertThat(hand1.compareTo(hand2), is(0));
    }

    @Test
    public void testHighCardHandBigger() {
        Main.Card card = new Main.Card("TH");
        Main.Card card1 = new Main.Card("8S");
        Main.Card card2 = new Main.Card("6D");
        Main.Card card3 = new Main.Card("2C");
        Main.Card card4 = new Main.Card("4S");
        List<Main.Card> cards1 = Arrays.asList(card,card1,card2,card3,card4);
        Main.Hand hand1 = new Main.HighCard(cards1);

        Main.Card card5 = new Main.Card("9H");
        Main.Card card6 = new Main.Card("8C");
        Main.Card card7 = new Main.Card("6S");
        Main.Card card8 = new Main.Card("2D");
        Main.Card card9 = new Main.Card("4S");
        List<Main.Card> cards2 = Arrays.asList(card5,card6,card7,card8,card9);
        Main.Hand hand2 = new Main.HighCard(cards2);
        assertThat(hand1.compareTo(hand2), is(1));
    }

    @Test
    public void testPair() {
        Main.Card card = new Main.Card("TH");
        Main.Card card1 = new Main.Card("8S");
        Main.Card card2 = new Main.Card("6D");
        Main.Card card3 = new Main.Card("2C");
        Main.Card card4 = new Main.Card("4S");
        List<Main.Card> cards1 = Arrays.asList(card,card1,card2,card3,card4);
        Main.Hand hand1 = new Main.Pair(cards1);
        assertThat(hand1.isValid(cards1), is(false));

        Main.Card card5 = new Main.Card("9H");
        Main.Card card6 = new Main.Card("9H");
        Main.Card card7 = new Main.Card("6S");
        Main.Card card8 = new Main.Card("2D");
        Main.Card card9 = new Main.Card("4S");
        List<Main.Card> cards2 = Arrays.asList(card5,card6,card7,card8,card9);
        Main.Hand hand2 = new Main.Pair(cards2);
        assertThat(hand2.isValid(cards2), is(true));

        card = new Main.Card("TH");
        card1 = new Main.Card("TS");
        card2 = new Main.Card("6D");
        card3 = new Main.Card("2C");
        card4 = new Main.Card("4S");
        cards1 = Arrays.asList(card,card1,card2,card3,card4);
        hand1 = new Main.Pair(cards1);
        assertThat(hand1.isValid(cards1), is(true));

        card = new Main.Card("TH");
        card1 = new Main.Card("TH");
        card2 = new Main.Card("6D");
        card3 = new Main.Card("6S");
        card4 = new Main.Card("4S");
        cards1 = Arrays.asList(card,card1,card2,card3,card4);
        hand1 = new Main.Pair(cards1);
        assertThat(hand1.isValid(cards1), is(false));

    }

    @Test
    public void testPairComparison() {
        Main.Card card = new Main.Card("TH");
        Main.Card card1 = new Main.Card("TH");
        Main.Card card2 = new Main.Card("6D");
        Main.Card card3 = new Main.Card("2C");
        Main.Card card4 = new Main.Card("4S");
        List<Main.Card> cards1 = Arrays.asList(card,card1,card2,card3,card4);
        Main.Hand hand1 = new Main.Pair(cards1);

        Main.Card card5 = new Main.Card("9H");
        Main.Card card6 = new Main.Card("9C");
        Main.Card card7 = new Main.Card("6S");
        Main.Card card8 = new Main.Card("2D");
        Main.Card card9 = new Main.Card("4S");
        List<Main.Card> cards2 = Arrays.asList(card5,card6,card7,card8,card9);
        Main.Hand hand2 = new Main.Pair(cards2);
        assertThat(hand1.compareTo(hand2), is(1));

    }

    @Test
    public void testPairComparison2() {
        Main.Card card = new Main.Card("TH");
        Main.Card card1 = new Main.Card("8H");
        Main.Card card2 = new Main.Card("6D");
        Main.Card card3 = new Main.Card("2C");
        Main.Card card4 = new Main.Card("4S");
        List<Main.Card> cards1 = Arrays.asList(card,card1,card2,card3,card4);
        Main.Hand hand1 = new Main.HighCard(cards1);

        Main.Card card5 = new Main.Card("9H");
        Main.Card card6 = new Main.Card("9C");
        Main.Card card7 = new Main.Card("6S");
        Main.Card card8 = new Main.Card("2D");
        Main.Card card9 = new Main.Card("4S");
        List<Main.Card> cards2 = Arrays.asList(card5,card6,card7,card8,card9);
        Main.Hand hand2 = new Main.Pair(cards2);
        assertThat(hand1.compareTo(hand2), is(-1));

    }

    @Test
    public void testPairComparison3() {
        Main.Card card = new Main.Card("TH");
        Main.Card card1 = new Main.Card("TH");
        Main.Card card2 = new Main.Card("6D");
        Main.Card card3 = new Main.Card("2C");
        Main.Card card4 = new Main.Card("4S");
        List<Main.Card> cards1 = Arrays.asList(card,card1,card2,card3,card4);
        Main.Hand hand1 = new Main.Pair(cards1);

        Main.Card card5 = new Main.Card("TH");
        Main.Card card6 = new Main.Card("TH");
        Main.Card card7 = new Main.Card("6S");
        Main.Card card8 = new Main.Card("2D");
        Main.Card card9 = new Main.Card("4S");
        List<Main.Card> cards2 = Arrays.asList(card5,card6,card7,card8,card9);
        Main.Hand hand2 = new Main.Pair(cards2);
        assertThat(hand1.compareTo(hand2), is(0));

    }

    @Test
    public void testPairComparison4() {
        Main.Card card = new Main.Card("TH");
        Main.Card card1 = new Main.Card("TH");
        Main.Card card2 = new Main.Card("6D");
        Main.Card card3 = new Main.Card("2C");
        Main.Card card4 = new Main.Card("3S");
        List<Main.Card> cards1 = Arrays.asList(card,card1,card2,card3,card4);
        Main.Hand hand1 = new Main.Pair(cards1);

        Main.Card card5 = new Main.Card("TH");
        Main.Card card6 = new Main.Card("TH");
        Main.Card card7 = new Main.Card("6S");
        Main.Card card8 = new Main.Card("2D");
        Main.Card card9 = new Main.Card("4S");
        List<Main.Card> cards2 = Arrays.asList(card5,card6,card7,card8,card9);
        Main.Hand hand2 = new Main.Pair(cards2);
        assertThat(hand1.compareTo(hand2), is(-1));

    }

    @Test
    public void testTwoPairs() {
        Main.Card card = new Main.Card("TH");
        Main.Card card1 = new Main.Card("8S");
        Main.Card card2 = new Main.Card("6D");
        Main.Card card3 = new Main.Card("2C");
        Main.Card card4 = new Main.Card("4S");
        List<Main.Card> cards1 = Arrays.asList(card,card1,card2,card3,card4);
        Main.Hand hand1 = new Main.TwoPairs(cards1);
        assertThat(hand1.isValid(cards1), is(false));

        Main.Card card5 = new Main.Card("9H");
        Main.Card card6 = new Main.Card("9H");
        Main.Card card7 = new Main.Card("6S");
        Main.Card card8 = new Main.Card("2D");
        Main.Card card9 = new Main.Card("4S");
        List<Main.Card> cards2 = Arrays.asList(card5,card6,card7,card8,card9);
        Main.Hand hand2 = new Main.TwoPairs(cards2);
        assertThat(hand2.isValid(cards2), is(false));

        card = new Main.Card("TH");
        card1 = new Main.Card("TS");
        card2 = new Main.Card("6D");
        card3 = new Main.Card("6C");
        card4 = new Main.Card("4S");
        cards1 = Arrays.asList(card,card1,card2,card3,card4);
        hand1 = new Main.TwoPairs(cards1);
        assertThat(hand1.isValid(cards1), is(true));

        card = new Main.Card("TC");
        card1 = new Main.Card("TS");
        card2 = new Main.Card("TD");
        card3 = new Main.Card("6S");
        card4 = new Main.Card("6S");
        cards1 = Arrays.asList(card,card1,card2,card3,card4);
        hand1 = new Main.TwoPairs(cards1);
        assertThat(hand1.isValid(cards1), is(false));

    }


    @Test
    public void testTwoPairsComparison() {
        Main.Card card = new Main.Card("TH");
        Main.Card card1 = new Main.Card("TH");
        Main.Card card2 = new Main.Card("6D");
        Main.Card card3 = new Main.Card("6C");
        Main.Card card4 = new Main.Card("4S");
        List<Main.Card> cards1 = Arrays.asList(card,card1,card2,card3,card4);
        Main.Hand hand1 = new Main.TwoPairs(cards1);

        Main.Card card5 = new Main.Card("9H");
        Main.Card card6 = new Main.Card("9C");
        Main.Card card7 = new Main.Card("6S");
        Main.Card card8 = new Main.Card("6D");
        Main.Card card9 = new Main.Card("4S");
        List<Main.Card> cards2 = Arrays.asList(card5,card6,card7,card8,card9);
        Main.Hand hand2 = new Main.TwoPairs(cards2);
        assertThat(hand1.compareTo(hand2), is(1));

    }

    @Test
    public void testTwoPairsComparison2() {
        Main.Card card = new Main.Card("8H");
        Main.Card card1 = new Main.Card("8H");
        Main.Card card2 = new Main.Card("6D");
        Main.Card card3 = new Main.Card("2C");
        Main.Card card4 = new Main.Card("4S");
        List<Main.Card> cards1 = Arrays.asList(card,card1,card2,card3,card4);
        Main.Hand hand1 = new Main.Pair(cards1);

        Main.Card card5 = new Main.Card("7H");
        Main.Card card6 = new Main.Card("7C");
        Main.Card card7 = new Main.Card("6S");
        Main.Card card8 = new Main.Card("6D");
        Main.Card card9 = new Main.Card("4S");
        List<Main.Card> cards2 = Arrays.asList(card5,card6,card7,card8,card9);
        Main.Hand hand2 = new Main.TwoPairs(cards2);
        assertThat(hand1.compareTo(hand2), is(-1));

    }

    @Test
    public void testTwoPairsComparison3() {
        Main.Card card = new Main.Card("TH");
        Main.Card card1 = new Main.Card("TH");
        Main.Card card2 = new Main.Card("6D");
        Main.Card card3 = new Main.Card("6C");
        Main.Card card4 = new Main.Card("4S");
        List<Main.Card> cards1 = Arrays.asList(card,card1,card2,card3,card4);
        Main.Hand hand1 = new Main.TwoPairs(cards1);

        Main.Card card5 = new Main.Card("TH");
        Main.Card card6 = new Main.Card("TH");
        Main.Card card7 = new Main.Card("6S");
        Main.Card card8 = new Main.Card("6D");
        Main.Card card9 = new Main.Card("4S");
        List<Main.Card> cards2 = Arrays.asList(card5,card6,card7,card8,card9);
        Main.Hand hand2 = new Main.TwoPairs(cards2);
        assertThat(hand1.compareTo(hand2), is(0));

    }

    @Test
    public void testPairsComparison4() {
        Main.Card card = new Main.Card("TH");
        Main.Card card1 = new Main.Card("TH");
        Main.Card card2 = new Main.Card("6D");
        Main.Card card3 = new Main.Card("6C");
        Main.Card card4 = new Main.Card("3S");
        List<Main.Card> cards1 = Arrays.asList(card,card1,card2,card3,card4);
        Main.Hand hand1 = new Main.TwoPairs(cards1);

        Main.Card card5 = new Main.Card("TH");
        Main.Card card6 = new Main.Card("TH");
        Main.Card card7 = new Main.Card("6S");
        Main.Card card8 = new Main.Card("6D");
        Main.Card card9 = new Main.Card("4S");
        List<Main.Card> cards2 = Arrays.asList(card5,card6,card7,card8,card9);
        Main.Hand hand2 = new Main.TwoPairs(cards2);
        assertThat(hand1.compareTo(hand2), is(-1));

    }

    @Test
    public void testThreeOfAKind() {
        Main.Card card = new Main.Card("TH");
        Main.Card card1 = new Main.Card("8S");
        Main.Card card2 = new Main.Card("6D");
        Main.Card card3 = new Main.Card("2C");
        Main.Card card4 = new Main.Card("4S");
        List<Main.Card> cards1 = Arrays.asList(card,card1,card2,card3,card4);
        Main.Hand hand1 = new Main.ThreeOfAKind(cards1);
        assertThat(hand1.isValid(cards1), is(false));

        Main.Card card5 = new Main.Card("9H");
        Main.Card card6 = new Main.Card("9H");
        Main.Card card7 = new Main.Card("6S");
        Main.Card card8 = new Main.Card("2D");
        Main.Card card9 = new Main.Card("4S");
        List<Main.Card> cards2 = Arrays.asList(card5,card6,card7,card8,card9);
        Main.Hand hand2 = new Main.ThreeOfAKind(cards2);
        assertThat(hand2.isValid(cards2), is(false));

        card = new Main.Card("TH");
        card1 = new Main.Card("TS");
        card2 = new Main.Card("6D");
        card3 = new Main.Card("6C");
        card4 = new Main.Card("4S");
        cards1 = Arrays.asList(card,card1,card2,card3,card4);
        hand1 = new Main.ThreeOfAKind(cards1);
        assertThat(hand1.isValid(cards1), is(false));

        card = new Main.Card("TC");
        card1 = new Main.Card("TS");
        card2 = new Main.Card("TD");
        card3 = new Main.Card("6S");
        card4 = new Main.Card("5S");
        cards1 = Arrays.asList(card,card1,card2,card3,card4);
        hand1 = new Main.ThreeOfAKind(cards1);
        assertThat(hand1.isValid(cards1), is(true));

        card = new Main.Card("TC");
        card1 = new Main.Card("TS");
        card2 = new Main.Card("TD");
        card3 = new Main.Card("TH");
        card4 = new Main.Card("6S");
        cards1 = Arrays.asList(card,card1,card2,card3,card4);
        hand1 = new Main.ThreeOfAKind(cards1);
        assertThat(hand1.isValid(cards1), is(false));

    }

    @Test
    public void testThreeOfAKindComparison() {
        Main.Card card = new Main.Card("TH");
        Main.Card card1 = new Main.Card("TH");
        Main.Card card2 = new Main.Card("6D");
        Main.Card card3 = new Main.Card("6C");
        Main.Card card4 = new Main.Card("4S");
        List<Main.Card> cards1 = Arrays.asList(card,card1,card2,card3,card4);
        Main.Hand hand1 = new Main.TwoPairs(cards1);

        Main.Card card5 = new Main.Card("9H");
        Main.Card card6 = new Main.Card("9C");
        Main.Card card7 = new Main.Card("9S");
        Main.Card card8 = new Main.Card("6D");
        Main.Card card9 = new Main.Card("4S");
        List<Main.Card> cards2 = Arrays.asList(card5,card6,card7,card8,card9);
        Main.Hand hand2 = new Main.ThreeOfAKind(cards2);
        assertThat(hand1.compareTo(hand2), is(-1));

    }

    @Test
    public void testThreeOfAKindComparison2() {
        Main.Card card = new Main.Card("9H");
        Main.Card card1 = new Main.Card("9H");
        Main.Card card2 = new Main.Card("9D");
        Main.Card card3 = new Main.Card("7C");
        Main.Card card4 = new Main.Card("4S");
        List<Main.Card> cards1 = Arrays.asList(card,card1,card2,card3,card4);
        Main.Hand hand1 = new Main.ThreeOfAKind(cards1);

        Main.Card card5 = new Main.Card("7H");
        Main.Card card6 = new Main.Card("7C");
        Main.Card card7 = new Main.Card("7S");
        Main.Card card8 = new Main.Card("6D");
        Main.Card card9 = new Main.Card("4S");
        List<Main.Card> cards2 = Arrays.asList(card5,card6,card7,card8,card9);
        Main.Hand hand2 = new Main.ThreeOfAKind(cards2);
        assertThat(hand1.compareTo(hand2), is(1));

    }

    @Test
    public void testThreeOfAKindComparison3() {
        Main.Card card = new Main.Card("TH");
        Main.Card card1 = new Main.Card("TH");
        Main.Card card2 = new Main.Card("TD");
        Main.Card card3 = new Main.Card("6C");
        Main.Card card4 = new Main.Card("6S");
        List<Main.Card> cards1 = Arrays.asList(card,card1,card2,card3,card4);
        Main.Hand hand1 = new Main.ThreeOfAKind(cards1);

        Main.Card card5 = new Main.Card("TH");
        Main.Card card6 = new Main.Card("TH");
        Main.Card card7 = new Main.Card("TS");
        Main.Card card8 = new Main.Card("6D");
        Main.Card card9 = new Main.Card("4S");
        List<Main.Card> cards2 = Arrays.asList(card5,card6,card7,card8,card9);
        Main.Hand hand2 = new Main.ThreeOfAKind(cards2);
        assertThat(hand1.compareTo(hand2), is(0));

    }

    @Test
    public void testThreeOfAKindComparison4() {
        Main.Card card = new Main.Card("TH");
        Main.Card card1 = new Main.Card("TH");
        Main.Card card2 = new Main.Card("TD");
        Main.Card card3 = new Main.Card("6C");
        Main.Card card4 = new Main.Card("3S");
        List<Main.Card> cards1 = Arrays.asList(card,card1,card2,card3,card4);
        Main.Hand hand1 = new Main.ThreeOfAKind(cards1);

        Main.Card card5 = new Main.Card("TH");
        Main.Card card6 = new Main.Card("TH");
        Main.Card card7 = new Main.Card("TS");
        Main.Card card8 = new Main.Card("6D");
        Main.Card card9 = new Main.Card("4S");
        List<Main.Card> cards2 = Arrays.asList(card5,card6,card7,card8,card9);
        Main.Hand hand2 = new Main.ThreeOfAKind(cards2);
        assertThat(hand1.compareTo(hand2), is(0));

    }

    @Test
    public void testStraight() {
        Main.Card card = new Main.Card("TH");
        Main.Card card1 = new Main.Card("9S");
        Main.Card card2 = new Main.Card("8D");
        Main.Card card3 = new Main.Card("7C");
        Main.Card card4 = new Main.Card("6S");
        List<Main.Card> cards1 = Arrays.asList(card,card1,card2,card3,card4);
        Main.Hand hand1 = new Main.Straight(cards1);
        assertThat(hand1.isValid(cards1), is(true));

        Main.Card card5 = new Main.Card("9H");
        Main.Card card6 = new Main.Card("9H");
        Main.Card card7 = new Main.Card("6S");
        Main.Card card8 = new Main.Card("2D");
        Main.Card card9 = new Main.Card("4S");
        List<Main.Card> cards2 = Arrays.asList(card5,card6,card7,card8,card9);
        Main.Hand hand2 = new Main.Straight(cards2);
        assertThat(hand2.isValid(cards2), is(false));

        card = new Main.Card("TH");
        card1 = new Main.Card("TS");
        card2 = new Main.Card("6D");
        card3 = new Main.Card("6C");
        card4 = new Main.Card("4S");
        cards1 = Arrays.asList(card,card1,card2,card3,card4);
        hand1 = new Main.Straight(cards1);
        assertThat(hand1.isValid(cards1), is(false));

        card = new Main.Card("TC");
        card1 = new Main.Card("TS");
        card2 = new Main.Card("TD");
        card3 = new Main.Card("6S");
        card4 = new Main.Card("6S");
        cards1 = Arrays.asList(card,card1,card2,card3,card4);
        hand1 = new Main.Straight(cards1);
        assertThat(hand1.isValid(cards1), is(false));

        card = new Main.Card("AC");
        card1 = new Main.Card("KS");
        card2 = new Main.Card("QD");
        card3 = new Main.Card("JH");
        card4 = new Main.Card("TS");
        cards1 = Arrays.asList(card,card1,card2,card3,card4);
        hand1 = new Main.Straight(cards1);
        assertThat(hand1.isValid(cards1), is(true));

    }

    @Test
    public void testStraightComparison() {
        Main.Card card = new Main.Card("TH");
        Main.Card card1 = new Main.Card("TH");
        Main.Card card2 = new Main.Card("TD");
        Main.Card card3 = new Main.Card("6C");
        Main.Card card4 = new Main.Card("4S");
        List<Main.Card> cards1 = Arrays.asList(card,card1,card2,card3,card4);
        Main.Hand hand1 = new Main.ThreeOfAKind(cards1);

        Main.Card card5 = new Main.Card("9H");
        Main.Card card6 = new Main.Card("8C");
        Main.Card card7 = new Main.Card("7S");
        Main.Card card8 = new Main.Card("6D");
        Main.Card card9 = new Main.Card("5S");
        List<Main.Card> cards2 = Arrays.asList(card5,card6,card7,card8,card9);
        Main.Hand hand2 = new Main.Straight(cards2);
        assertThat(hand1.compareTo(hand2), is(-1));

    }

    @Test
    public void testStraightComparison2() {
        Main.Card card = new Main.Card("9H");
        Main.Card card1 = new Main.Card("8H");
        Main.Card card2 = new Main.Card("7D");
        Main.Card card3 = new Main.Card("6C");
        Main.Card card4 = new Main.Card("5S");
        List<Main.Card> cards1 = Arrays.asList(card,card1,card2,card3,card4);
        Main.Hand hand1 = new Main.Straight(cards1);

        Main.Card card5 = new Main.Card("TH");
        Main.Card card6 = new Main.Card("9C");
        Main.Card card7 = new Main.Card("8S");
        Main.Card card8 = new Main.Card("7D");
        Main.Card card9 = new Main.Card("6S");
        List<Main.Card> cards2 = Arrays.asList(card5,card6,card7,card8,card9);
        Main.Hand hand2 = new Main.Straight(cards2);
        assertThat(hand1.compareTo(hand2), is(-1));

    }

    @Test
    public void testStraightComparison3() {
        Main.Card card = new Main.Card("TH");
        Main.Card card1 = new Main.Card("9H");
        Main.Card card2 = new Main.Card("8D");
        Main.Card card3 = new Main.Card("7C");
        Main.Card card4 = new Main.Card("6S");
        List<Main.Card> cards1 = Arrays.asList(card,card1,card2,card3,card4);
        Main.Hand hand1 = new Main.Straight(cards1);

        Main.Card card5 = new Main.Card("TH");
        Main.Card card6 = new Main.Card("9H");
        Main.Card card7 = new Main.Card("7S");
        Main.Card card8 = new Main.Card("8D");
        Main.Card card9 = new Main.Card("6S");
        List<Main.Card> cards2 = Arrays.asList(card5,card6,card7,card8,card9);
        Main.Hand hand2 = new Main.Straight(cards2);
        assertThat(hand1.compareTo(hand2), is(0));

    }

    @Test
    public void testFlush() {
        Main.Card card = new Main.Card("TH");
        Main.Card card1 = new Main.Card("9S");
        Main.Card card2 = new Main.Card("8D");
        Main.Card card3 = new Main.Card("7C");
        Main.Card card4 = new Main.Card("6S");
        List<Main.Card> cards1 = Arrays.asList(card,card1,card2,card3,card4);
        Main.Hand hand1 = new Main.Flush(cards1);
        assertThat(hand1.isValid(cards1), is(false));

        Main.Card card5 = new Main.Card("9H");
        Main.Card card6 = new Main.Card("8H");
        Main.Card card7 = new Main.Card("6H");
        Main.Card card8 = new Main.Card("2H");
        Main.Card card9 = new Main.Card("4H");
        List<Main.Card> cards2 = Arrays.asList(card5,card6,card7,card8,card9);
        Main.Hand hand2 = new Main.Flush(cards2);
        assertThat(hand2.isValid(cards2), is(true));

        card = new Main.Card("TH");
        card1 = new Main.Card("TD");
        card2 = new Main.Card("6D");
        card3 = new Main.Card("6D");
        card4 = new Main.Card("4D");
        cards1 = Arrays.asList(card,card1,card2,card3,card4);
        hand1 = new Main.Flush(cards1);
        assertThat(hand1.isValid(cards1), is(false));

    }

    @Test
    public void testFlushComparison() {
        Main.Card card = new Main.Card("TH");
        Main.Card card1 = new Main.Card("8H");
        Main.Card card2 = new Main.Card("6H");
        Main.Card card3 = new Main.Card("2H");
        Main.Card card4 = new Main.Card("4H");
        List<Main.Card> cards1 = Arrays.asList(card,card1,card2,card3,card4);
        Main.Hand hand1 = new Main.Flush(cards1);

        Main.Card card5 = new Main.Card("9C");
        Main.Card card6 = new Main.Card("8C");
        Main.Card card7 = new Main.Card("6C");
        Main.Card card8 = new Main.Card("2C");
        Main.Card card9 = new Main.Card("4C");
        List<Main.Card> cards2 = Arrays.asList(card5,card6,card7,card8,card9);
        Main.Hand hand2 = new Main.Flush(cards2);
        assertThat(hand1.compareTo(hand2), is(1));
    }

    @Test
    public void testFlushComparison2() {
        Main.Card card = new Main.Card("TC");
        Main.Card card1 = new Main.Card("9H");
        Main.Card card2 = new Main.Card("8H");
        Main.Card card3 = new Main.Card("7H");
        Main.Card card4 = new Main.Card("6H");
        List<Main.Card> cards1 = Arrays.asList(card,card1,card2,card3,card4);
        Main.Hand hand1 = new Main.Straight(cards1);

        Main.Card card5 = new Main.Card("9C");
        Main.Card card6 = new Main.Card("8C");
        Main.Card card7 = new Main.Card("6C");
        Main.Card card8 = new Main.Card("2C");
        Main.Card card9 = new Main.Card("4C");
        List<Main.Card> cards2 = Arrays.asList(card5,card6,card7,card8,card9);
        Main.Hand hand2 = new Main.Flush(cards2);
        assertThat(hand1.compareTo(hand2), is(-1));
    }

    @Test
    public void testFullHouse() {
        Main.Card card = new Main.Card("TH");
        Main.Card card1 = new Main.Card("8S");
        Main.Card card2 = new Main.Card("6D");
        Main.Card card3 = new Main.Card("2C");
        Main.Card card4 = new Main.Card("4S");
        List<Main.Card> cards1 = Arrays.asList(card,card1,card2,card3,card4);
        Main.Hand hand1 = new Main.FullHouse(cards1);
        assertThat(hand1.isValid(cards1), is(false));

        Main.Card card5 = new Main.Card("9H");
        Main.Card card6 = new Main.Card("9H");
        Main.Card card7 = new Main.Card("6S");
        Main.Card card8 = new Main.Card("2D");
        Main.Card card9 = new Main.Card("4S");
        List<Main.Card> cards2 = Arrays.asList(card5,card6,card7,card8,card9);
        Main.Hand hand2 = new Main.FullHouse(cards2);
        assertThat(hand2.isValid(cards2), is(false));

        card = new Main.Card("TH");
        card1 = new Main.Card("TS");
        card2 = new Main.Card("6D");
        card3 = new Main.Card("6C");
        card4 = new Main.Card("4S");
        cards1 = Arrays.asList(card,card1,card2,card3,card4);
        hand1 = new Main.FullHouse(cards1);
        assertThat(hand1.isValid(cards1), is(false));

        card = new Main.Card("TC");
        card1 = new Main.Card("TS");
        card2 = new Main.Card("TD");
        card3 = new Main.Card("6S");
        card4 = new Main.Card("5S");
        cards1 = Arrays.asList(card,card1,card2,card3,card4);
        hand1 = new Main.FullHouse(cards1);
        assertThat(hand1.isValid(cards1), is(false));

        card = new Main.Card("TC");
        card1 = new Main.Card("TS");
        card2 = new Main.Card("TD");
        card3 = new Main.Card("TH");
        card4 = new Main.Card("6S");
        cards1 = Arrays.asList(card,card1,card2,card3,card4);
        hand1 = new Main.FullHouse(cards1);
        assertThat(hand1.isValid(cards1), is(false));

        card = new Main.Card("TC");
        card1 = new Main.Card("TS");
        card2 = new Main.Card("TD");
        card3 = new Main.Card("6H");
        card4 = new Main.Card("6S");
        cards1 = Arrays.asList(card,card1,card2,card3,card4);
        hand1 = new Main.FullHouse(cards1);
        assertThat(hand1.isValid(cards1), is(true));


    }

    @Test
    public void testFullHouseComparison() {
        Main.Card card = new Main.Card("TH");
        Main.Card card1 = new Main.Card("9H");
        Main.Card card2 = new Main.Card("6H");
        Main.Card card3 = new Main.Card("5H");
        Main.Card card4 = new Main.Card("4H");
        List<Main.Card> cards1 = Arrays.asList(card,card1,card2,card3,card4);
        Main.Hand hand1 = new Main.Flush(cards1);

        Main.Card card5 = new Main.Card("9H");
        Main.Card card6 = new Main.Card("9C");
        Main.Card card7 = new Main.Card("9S");
        Main.Card card8 = new Main.Card("6D");
        Main.Card card9 = new Main.Card("6S");
        List<Main.Card> cards2 = Arrays.asList(card5,card6,card7,card8,card9);
        Main.Hand hand2 = new Main.FullHouse(cards2);
        assertThat(hand1.compareTo(hand2), is(-1));

    }

    @Test
    public void testFullHouseComparison2() {
        Main.Card card = new Main.Card("9H");
        Main.Card card1 = new Main.Card("9H");
        Main.Card card2 = new Main.Card("9D");
        Main.Card card3 = new Main.Card("7C");
        Main.Card card4 = new Main.Card("7S");
        List<Main.Card> cards1 = Arrays.asList(card,card1,card2,card3,card4);
        Main.Hand hand1 = new Main.FullHouse(cards1);

        Main.Card card5 = new Main.Card("7H");
        Main.Card card6 = new Main.Card("7C");
        Main.Card card7 = new Main.Card("7S");
        Main.Card card8 = new Main.Card("6D");
        Main.Card card9 = new Main.Card("6S");
        List<Main.Card> cards2 = Arrays.asList(card5,card6,card7,card8,card9);
        Main.Hand hand2 = new Main.FullHouse(cards2);
        assertThat(hand1.compareTo(hand2), is(1));

    }

    @Test
    public void testFullHouseComparison3() {
        Main.Card card = new Main.Card("TH");
        Main.Card card1 = new Main.Card("TC");
        Main.Card card2 = new Main.Card("TD");
        Main.Card card3 = new Main.Card("6C");
        Main.Card card4 = new Main.Card("6S");
        List<Main.Card> cards1 = Arrays.asList(card,card1,card2,card3,card4);
        Main.Hand hand1 = new Main.ThreeOfAKind(cards1);

        Main.Card card5 = new Main.Card("TH");
        Main.Card card6 = new Main.Card("TC");
        Main.Card card7 = new Main.Card("TS");
        Main.Card card8 = new Main.Card("5D");
        Main.Card card9 = new Main.Card("5S");
        List<Main.Card> cards2 = Arrays.asList(card5,card6,card7,card8,card9);
        Main.Hand hand2 = new Main.ThreeOfAKind(cards2);
        assertThat(hand1.compareTo(hand2), is(0));

    }

    @Test
    public void testFourOfAKind() {
        Main.Card card = new Main.Card("TH");
        Main.Card card1 = new Main.Card("TS");
        Main.Card card2 = new Main.Card("TD");
        Main.Card card3 = new Main.Card("TC");
        Main.Card card4 = new Main.Card("4S");
        List<Main.Card> cards1 = Arrays.asList(card,card1,card2,card3,card4);
        Main.Hand hand1 = new Main.FourOfAKind(cards1);
        assertThat(hand1.isValid(cards1), is(true));

        Main.Card card5 = new Main.Card("9C");
        Main.Card card6 = new Main.Card("9C");
        Main.Card card7 = new Main.Card("9C");
        Main.Card card8 = new Main.Card("2C");
        Main.Card card9 = new Main.Card("4C");
        List<Main.Card> cards2 = Arrays.asList(card5,card6,card7,card8,card9);
        Main.Hand hand2 = new Main.FourOfAKind(cards2);
        assertThat(hand2.isValid(cards2), is(false));

        card = new Main.Card("TH");
        card1 = new Main.Card("TS");
        card2 = new Main.Card("6D");
        card3 = new Main.Card("6C");
        card4 = new Main.Card("4S");
        cards1 = Arrays.asList(card,card1,card2,card3,card4);
        hand1 = new Main.FourOfAKind(cards1);
        assertThat(hand1.isValid(cards1), is(false));

        card = new Main.Card("TC");
        card1 = new Main.Card("TS");
        card2 = new Main.Card("TD");
        card3 = new Main.Card("6S");
        card4 = new Main.Card("5S");
        cards1 = Arrays.asList(card,card1,card2,card3,card4);
        hand1 = new Main.FourOfAKind(cards1);
        assertThat(hand1.isValid(cards1), is(false));

        card = new Main.Card("TC");
        card1 = new Main.Card("TS");
        card2 = new Main.Card("TD");
        card3 = new Main.Card("TH");
        card4 = new Main.Card("6S");
        cards1 = Arrays.asList(card,card1,card2,card3,card4);
        hand1 = new Main.FourOfAKind(cards1);
        assertThat(hand1.isValid(cards1), is(true));

    }

    @Test
    public void testFourOfAKindComparison() {
        Main.Card card = new Main.Card("TH");
        Main.Card card1 = new Main.Card("TS");
        Main.Card card2 = new Main.Card("TC");
        Main.Card card3 = new Main.Card("4D");
        Main.Card card4 = new Main.Card("4H");
        List<Main.Card> cards1 = Arrays.asList(card,card1,card2,card3,card4);
        Main.Hand hand1 = new Main.FullHouse(cards1);

        Main.Card card5 = new Main.Card("9H");
        Main.Card card6 = new Main.Card("9C");
        Main.Card card7 = new Main.Card("9S");
        Main.Card card8 = new Main.Card("9D");
        Main.Card card9 = new Main.Card("6S");
        List<Main.Card> cards2 = Arrays.asList(card5,card6,card7,card8,card9);
        Main.Hand hand2 = new Main.FourOfAKind(cards2);
        assertThat(hand1.compareTo(hand2), is(-1));

    }

    @Test
    public void testFourOfAKindComparison2() {
        Main.Card card = new Main.Card("TH");
        Main.Card card1 = new Main.Card("TS");
        Main.Card card2 = new Main.Card("TC");
        Main.Card card3 = new Main.Card("TD");
        Main.Card card4 = new Main.Card("4H");
        List<Main.Card> cards1 = Arrays.asList(card,card1,card2,card3,card4);
        Main.Hand hand1 = new Main.FourOfAKind(cards1);

        Main.Card card5 = new Main.Card("9H");
        Main.Card card6 = new Main.Card("9C");
        Main.Card card7 = new Main.Card("9S");
        Main.Card card8 = new Main.Card("9D");
        Main.Card card9 = new Main.Card("6S");
        List<Main.Card> cards2 = Arrays.asList(card5,card6,card7,card8,card9);
        Main.Hand hand2 = new Main.FourOfAKind(cards2);
        assertThat(hand1.compareTo(hand2), is(1));
    }

    @Test
    public void testFourOfAKindComparison3() {
        Main.Card card = new Main.Card("TH");
        Main.Card card1 = new Main.Card("TS");
        Main.Card card2 = new Main.Card("TC");
        Main.Card card3 = new Main.Card("TD");
        Main.Card card4 = new Main.Card("4H");
        List<Main.Card> cards1 = Arrays.asList(card,card1,card2,card3,card4);
        Main.Hand hand1 = new Main.FourOfAKind(cards1);

        Main.Card card5 = new Main.Card("TH");
        Main.Card card6 = new Main.Card("TC");
        Main.Card card7 = new Main.Card("TS");
        Main.Card card8 = new Main.Card("TD");
        Main.Card card9 = new Main.Card("TS");
        List<Main.Card> cards2 = Arrays.asList(card5,card6,card7,card8,card9);
        Main.Hand hand2 = new Main.FourOfAKind(cards2);
        assertThat(hand1.compareTo(hand2), is(0));
    }

    @Test
    public void testStraightFlush() {
        Main.Card card = new Main.Card("TH");
        Main.Card card1 = new Main.Card("9S");
        Main.Card card2 = new Main.Card("8D");
        Main.Card card3 = new Main.Card("7C");
        Main.Card card4 = new Main.Card("6S");
        List<Main.Card> cards1 = Arrays.asList(card,card1,card2,card3,card4);
        Main.Hand hand1 = new Main.StraightFlush(cards1);
        assertThat(hand1.isValid(cards1), is(false));

        Main.Card card5 = new Main.Card("9H");
        Main.Card card6 = new Main.Card("8H");
        Main.Card card7 = new Main.Card("5H");
        Main.Card card8 = new Main.Card("6H");
        Main.Card card9 = new Main.Card("7H");
        List<Main.Card> cards2 = Arrays.asList(card5,card6,card7,card8,card9);
        Main.Hand hand2 = new Main.StraightFlush(cards2);
        assertThat(hand2.isValid(cards2), is(true));

        card = new Main.Card("TH");
        card1 = new Main.Card("9H");
        card2 = new Main.Card("8H");
        card3 = new Main.Card("7H");
        card4 = new Main.Card("6D");
        cards1 = Arrays.asList(card,card1,card2,card3,card4);
        hand1 = new Main.StraightFlush(cards1);
        assertThat(hand1.isValid(cards1), is(false));

    }

    @Test
    public void testStraightFlushComparison() {
        Main.Card card = new Main.Card("AH");
        Main.Card card1 = new Main.Card("AD");
        Main.Card card2 = new Main.Card("AC");
        Main.Card card3 = new Main.Card("AS");
        Main.Card card4 = new Main.Card("4H");
        List<Main.Card> cards1 = Arrays.asList(card,card1,card2,card3,card4);
        Main.Hand hand1 = new Main.FourOfAKind(cards1);

        Main.Card card5 = new Main.Card("9C");
        Main.Card card6 = new Main.Card("8C");
        Main.Card card7 = new Main.Card("6C");
        Main.Card card8 = new Main.Card("7C");
        Main.Card card9 = new Main.Card("5C");
        List<Main.Card> cards2 = Arrays.asList(card5,card6,card7,card8,card9);
        Main.Hand hand2 = new Main.StraightFlush(cards2);
        assertThat(hand1.compareTo(hand2), is(-1));
    }

    @Test
    public void testStraightFlushComparison2() {
        Main.Card card = new Main.Card("TH");
        Main.Card card1 = new Main.Card("9H");
        Main.Card card2 = new Main.Card("8H");
        Main.Card card3 = new Main.Card("7H");
        Main.Card card4 = new Main.Card("6H");
        List<Main.Card> cards1 = Arrays.asList(card,card1,card2,card3,card4);
        Main.Hand hand1 = new Main.StraightFlush(cards1);

        Main.Card card5 = new Main.Card("9C");
        Main.Card card6 = new Main.Card("8C");
        Main.Card card7 = new Main.Card("6C");
        Main.Card card8 = new Main.Card("7C");
        Main.Card card9 = new Main.Card("5C");
        List<Main.Card> cards2 = Arrays.asList(card5,card6,card7,card8,card9);
        Main.Hand hand2 = new Main.StraightFlush(cards2);
        assertThat(hand1.compareTo(hand2), is(0));
    }

}
