package problems.cryptkicker;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringWhiteSpace;
import static org.hamcrest.Matchers.*;

public class MainTest {
    Main test;

    private static ByteArrayOutputStream outputActual = new ByteArrayOutputStream();
    private static String outputExpected;

    @Before
    public void setUp() throws Exception {
        test = new Main();
        test.setTestData(getClass().getClassLoader().getResourceAsStream("cryptkicker.input"),new PrintStream(outputActual,true));
        outputExpected = IOUtils.toString(getClass().getClassLoader().getResourceAsStream("cryptkicker.output"));
    }

    @Test
    public void testSolution() throws Exception {
        test.solution();
        Thread.sleep(3000);
        System.err.println(outputActual.toString());
        assertThat(outputActual.toString(),equalTo(outputExpected));
    }

    @Test
    public void testCryptLetter() {
        Main.CryptLetter cryptLetter = new Main.CryptLetter('a');
        assertThat(cryptLetter.isDecrypted(),is(false));
        cryptLetter.setCryptChar('k');
        assertThat(cryptLetter.isDecrypted(),is(true));
    }

    @Test
    public void testCryptAlphabetDecrypt() {
        Main.CryptAlphabet cryptAlphabet = new Main.CryptAlphabet();
        assertThat(cryptAlphabet.isSolved(),is(false));
        cryptAlphabet.updateAlphabetWithDecryptInfo("abc", "jkl");
        assertThat(cryptAlphabet.isSolved(),is(false));
        assertThat(cryptAlphabet.getCryptLetterByOriginalChar('a').isDecrypted(), is(true));
        assertThat(cryptAlphabet.getCryptLetterByOriginalChar('a').getCryptChar(),is('j'));
        assertThat(cryptAlphabet.getCryptLetterByOriginalChar('b').isDecrypted(), is(true));
        assertThat(cryptAlphabet.getCryptLetterByOriginalChar('b').getCryptChar(),is('k'));
        assertThat(cryptAlphabet.getCryptLetterByOriginalChar('c').isDecrypted(),is(true));
        assertThat(cryptAlphabet.getCryptLetterByOriginalChar('c').getCryptChar(),is('l'));
        assertThat(cryptAlphabet.getCryptLetterByOriginalChar('d').isDecrypted(),is(false));
        assertThat(cryptAlphabet.getCryptLetterByOriginalChar('d').getCryptChar(),is('*'));
    }

    @Test
    public void testCryptAlphabetDecryptFromOptions() {
        Main.CryptAlphabet cryptAlphabet = new Main.CryptAlphabet();        
        cryptAlphabet.updateAlphabetWithDecryptInfo("abc", "jkl");
        cryptAlphabet.updateAlphabetWithDecryptInfo("def", "mno");
        String d =cryptAlphabet.decryptByLetter("mlo");
        assertThat(d,is("dcf"));
    }

    @Test
    public void testDictionaryDecrypt() {
        Main.Dictionary dictionary = new Main.Dictionary();
        dictionary.addWord("picture");
        dictionary.addWord("table");
        dictionary.addWord("paper");
        dictionary.addWord("a");
        dictionary.addWord("or");
        dictionary.addWord("keyboard");
        dictionary.addWord("food");
        dictionary.addWord("ball");
        dictionary.addWord("pull");
        dictionary.addWord("drop");
        dictionary.addWord("areru");
        dictionary.addWord("taket");
        String similar = dictionary.decrypt("baar");
        assertThat(similar,is("food"));
        similar = dictionary.decrypt("pitt");  //both ball and pull might be
        assertThat(similar,nullValue());
        similar = dictionary.decrypt("abebu");  //both ball and pull might be
        assertThat(similar,is("areru"));
    }

    @Test
    public void testDictionaryDuplicateLocations() {
        Main.Dictionary dictionary = new Main.Dictionary();
        Map<Character,Set<Integer>> res = dictionary.getDuplicateLocations("cicibaba");
        assertThat(res.get('i').size(),is(2));
        assertThat(res.get('i').contains(1),is(true));
        assertThat(res.get('i').contains(3),is(true));

        assertThat(res.get('a').size(),is(2));
        assertThat(res.get('a').contains(5),is(true));
        assertThat(res.get('a').contains(7),is(true));
    }

}
