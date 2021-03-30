import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class WorksOnStringsTest {
    @Test
    public void findWordWithPatternTest() {
        assertEquals(WorksOnStrings.findWordWithPattern("hello world +amin +hello -amin -son", WorksOnStrings.MINUS_WORDS_PATTERN), new ArrayList<>(Arrays.asList("amin", "son")));
        assertEquals(WorksOnStrings.findWordWithPattern("-family tree -is -complicated -because -a -few", WorksOnStrings.MINUS_WORDS_PATTERN), new ArrayList<>(Arrays.asList("family", "is", "complicated", "because", "a", "few")));

        assertEquals(WorksOnStrings.findWordWithPattern("hello world +amin +hello -amin -son", WorksOnStrings.PLUS_WORDS_PATTERN), new ArrayList<>(Arrays.asList("amin", "hello")));
        assertEquals(WorksOnStrings.findWordWithPattern("+family +tree +is +complicated +because +a +few", WorksOnStrings.PLUS_WORDS_PATTERN), new ArrayList<>(Arrays.asList("family", "tree", "is", "complicated", "because", "a", "few")));

        assertEquals(WorksOnStrings.findWordWithPattern(" hello world +amin +hello -amin -son", WorksOnStrings.NORMAL_WORDS_PATTERN), new ArrayList<>(Arrays.asList("hello", "world")));
        assertEquals(WorksOnStrings.findWordWithPattern(" family tree is complicated because a few", WorksOnStrings.NORMAL_WORDS_PATTERN), new ArrayList<>(Arrays.asList("family", "tree", "is", "complicated", "because", "a", "few")));
    }

    @Test
    public void normalizeTheContentTest() {
        assertEquals(WorksOnStrings.normalizeTheContent(" Hello World! I am Amin Shafiei. What is your Plan?."),"hello world i am amin shafiei what is your plan");
    }

    @Test
    public void deleteRedundantCharsTest() {
        assertEquals(WorksOnStrings.deleteRedundantChars(";,?\"'#"), "");
        assertEquals(WorksOnStrings.deleteRedundantChars("hello :amin"), "hello amin");
        assertEquals(WorksOnStrings.deleteRedundantChars("What a pity!"), "What a pity");
    }
}