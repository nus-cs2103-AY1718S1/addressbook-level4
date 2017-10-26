package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;


public class ArgumentWildcardMatcherTest {
    @Test
    public void wildcard_matcherSuccess() {
        String args = "Fa* *s* G*d a?b";
        List<String> keywords = ArgumentWildcardMatcher.processKeywords(Arrays.asList(args.split("\\s+")));
        List<String> expected = Arrays.asList("fa\\S*", "\\S*s\\S*", "g\\S*d", "a\\Sb");
        assertEquals(keywords, expected);
    }
}
