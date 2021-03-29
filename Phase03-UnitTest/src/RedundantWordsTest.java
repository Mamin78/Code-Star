import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class RedundantWordsTest {
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"up", true}, {"up", true}, {"having", true}, {"hello", false}, {"world", false}
        });
    }

    private String input;
    private boolean expected;

    public RedundantWordsTest(String input, boolean expected) {
        this.input = input;
        this.expected = expected;
    }

    @Test
    public void isRedundantTest() {
        assertEquals(RedundantWords.isRedundantWord(input), this.expected);
    }
}