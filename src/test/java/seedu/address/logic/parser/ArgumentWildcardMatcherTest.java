package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;


public class ArgumentWildcardMatcherTest {
    @Test
    public void wildcard_matcherSuccess() {
        String args = "Fa* *s* G*d";
        List<String> keywords = ArgumentWildcardMatcher.processKeywords(Arrays.asList(args.split("\\s+")));
        List<String> expected = Arrays.asList("fa\\S*", "\\S*s\\S*", "g\\S*d");
        assertEquals(keywords, expected);
    }
}
