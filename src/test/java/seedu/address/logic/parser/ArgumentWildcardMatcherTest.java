package seedu.address.logic.parser;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;


public class ArgumentWildcardMatcherTest {
    @Test
    public void wildcard_matcherSuccess() {
        String args = "Fa* *s* G*d";
        String[] keywords = ArgumentWildcardMatcher.createKeywords(args);
        String[] expected = {"fa\\S*", "\\S*s\\S*", "g\\S*d"};
        assertArrayEquals(keywords, expected);
    }
}
