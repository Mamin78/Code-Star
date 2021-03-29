import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class WorksOnStringsTest {
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"hello world +amin +hello -amin -son", WorksOnStrings.MINUS_WORDS_PATTERN, new ArrayList<>(Arrays.asList("amin", "son"))},
                {"-family tree -is -complicated -because -a -few", WorksOnStrings.MINUS_WORDS_PATTERN, new ArrayList<>(Arrays.asList("family", "is", "complicated", "because", "a", "few"))},

                {"hello world +amin +hello -amin -son", WorksOnStrings.PLUS_WORDS_PATTERN, new ArrayList<>(Arrays.asList("amin", "hello"))},
                {"+family +tree +is +complicated +because +a +few", WorksOnStrings.PLUS_WORDS_PATTERN, new ArrayList<>(Arrays.asList("family", "tree", "is", "complicated", "because", "a", "few"))},

                {" hello world +amin +hello -amin -son", WorksOnStrings.NORMAL_WORDS_PATTERN, new ArrayList<>(Arrays.asList("hello", "world"))},
                {" family tree is complicated because a few", WorksOnStrings.NORMAL_WORDS_PATTERN, new ArrayList<>(Arrays.asList("family", "tree", "is", "complicated", "because", "a", "few"))},
        });
    }

    private String query;
    private String pattern;
    private ArrayList<String> expected;

    public WorksOnStringsTest(String query, String pattern, ArrayList<String> expected) {
        this.query = query;
        this.pattern = pattern;
        this.expected = expected;
    }

    @Test
    public void findWordWithPatternTest() {
        assertEquals(WorksOnStrings.findWordWithPattern(this.query, this.pattern), this.expected);
    }
}