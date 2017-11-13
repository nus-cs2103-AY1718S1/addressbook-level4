package seedu.address.commons.util;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.commons.util.HintUtil.findPrefixCompletionHint;
import static seedu.address.commons.util.HintUtil.findPrefixOfCompletionHint;
import static seedu.address.commons.util.HintUtil.getIndex;
import static seedu.address.commons.util.HintUtil.getPreambleIndex;
import static seedu.address.commons.util.HintUtil.getUncompletedPrefixes;
import static seedu.address.commons.util.HintUtil.hasIndex;
import static seedu.address.commons.util.HintUtil.hasPreambleIndex;
import static seedu.address.commons.util.HintUtil.offerHint;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import seedu.address.logic.parser.CliSyntax;
import seedu.address.logic.parser.Prefix;

public class HintUtilTest {

    @Test
    public void getUncompletedPrefixesTest() {
        List<Prefix> prefixListStub =
                Arrays.asList(CliSyntax.PREFIX_NAME, CliSyntax.PREFIX_PHONE, CliSyntax.PREFIX_EMAIL);
        assertGetUncompletedPrefixes(" n/", prefixListStub, prefixListStub.subList(1, 3));
        assertGetUncompletedPrefixes(" n/nick a/address", prefixListStub, prefixListStub.subList(1, 3));
        assertGetUncompletedPrefixes(" n/nick p/12321", prefixListStub, prefixListStub.subList(2, 3));
        assertGetUncompletedPrefixes(" e/random", prefixListStub, prefixListStub.subList(0, 2));

    }

    private void assertGetUncompletedPrefixes(String arguments,
                                            List<Prefix> prefixes,
                                            List<Prefix> expectedList) {
        assertEquals(expectedList, getUncompletedPrefixes(arguments, expectedList));
    }

    @Test
    public void findPrefixCompletionHintTest() {
        List<Prefix> prefixListStub =
                Arrays.asList(CliSyntax.PREFIX_NAME, CliSyntax.PREFIX_PHONE, CliSyntax.PREFIX_EMAIL);
        Optional<String> identifier = Optional.of("/");
        Optional<String> empty = Optional.empty();

        assertEquals(identifier, findPrefixCompletionHint(" n", prefixListStub));
        assertEquals(identifier, findPrefixCompletionHint(" n/ e", prefixListStub));
        assertEquals(identifier, findPrefixCompletionHint(" n/ e/ p", prefixListStub));

        assertEquals(empty, findPrefixCompletionHint(" n/ e/ p/", prefixListStub));
        assertEquals(empty, findPrefixCompletionHint(" ", prefixListStub));
        assertEquals(empty, findPrefixCompletionHint("", prefixListStub));
        assertEquals(empty, findPrefixCompletionHint(" r", prefixListStub));
    }

    @Test
    public void findPrefixOfCompletionHintTest() {
        List<Prefix> prefixListStub =
                Arrays.asList(CliSyntax.PREFIX_NAME, CliSyntax.PREFIX_PHONE, CliSyntax.PREFIX_EMAIL);
        assertEquals(CliSyntax.PREFIX_NAME, findPrefixOfCompletionHint(" n", prefixListStub));
        assertEquals(CliSyntax.PREFIX_PHONE, findPrefixOfCompletionHint(" p", prefixListStub));
        assertEquals(CliSyntax.PREFIX_NAME, findPrefixOfCompletionHint(" p/ n", prefixListStub));
        assertEquals(CliSyntax.PREFIX_EMAIL, findPrefixOfCompletionHint(" fdafa e", prefixListStub));

        assertEquals(CliSyntax.PREFIX_EMPTY, findPrefixOfCompletionHint(" fdafad", prefixListStub));
        assertEquals(CliSyntax.PREFIX_EMPTY, findPrefixOfCompletionHint(" fdafa e/", prefixListStub));
    }

    @Test
    public void offerHintTest() {
        List<Prefix> prefixListStub =
                Arrays.asList(CliSyntax.PREFIX_NAME, CliSyntax.PREFIX_PHONE, CliSyntax.PREFIX_EMAIL);
        assertEquals(CliSyntax.PREFIX_PHONE, offerHint(" n/", prefixListStub));
        assertEquals(CliSyntax.PREFIX_PHONE, offerHint(" n/ e/", prefixListStub));
        assertEquals(CliSyntax.PREFIX_NAME, offerHint(" ", prefixListStub));

        assertEquals(CliSyntax.PREFIX_NAME, offerHint(" fjdklasfjdksjf", prefixListStub));

        assertEquals(CliSyntax.PREFIX_EMPTY, offerHint(" n/ p/ e/", prefixListStub));
        assertEquals(CliSyntax.PREFIX_EMPTY, offerHint(" e/ p/ n/", prefixListStub));
    }

    @Test
    public void hasPreambleIndexTest() {
        assertTrue(hasPreambleIndex("3"));
        assertTrue(hasPreambleIndex(" 123"));
        assertTrue(hasPreambleIndex("  123434 "));

        assertFalse(hasPreambleIndex("  1234343nbnbn3---bn"));
        assertFalse(hasPreambleIndex(""));
        assertFalse(hasPreambleIndex(" -1"));
        assertFalse(hasPreambleIndex("dsdsdsd"));
    }

    @Test
    public void getPreambleIndexTest() {
        List<Prefix> prefixListStub =
                Arrays.asList(CliSyntax.PREFIX_NAME, CliSyntax.PREFIX_PHONE, CliSyntax.PREFIX_EMAIL);
        assertEquals(23, getPreambleIndex("23 n/ ", prefixListStub));
        assertEquals(1, getPreambleIndex("1 n/ ", prefixListStub));
        assertEquals(15656, getPreambleIndex("15656 n/ p/ e/ ", prefixListStub));

        assertEquals(-1, getPreambleIndex("gibberih n/ ", prefixListStub));
        assertEquals(-1, getPreambleIndex("n/gibberih p/12321 ", prefixListStub));

        //must be one based
        assertEquals(-1, getPreambleIndex("0 n/ ", prefixListStub));
    }

    @Test
    public void hasIndexTest() {
        assertTrue(hasIndex("2323"));
        assertTrue(hasIndex("1"));

        assertFalse(hasIndex("0"));
        assertFalse(hasIndex("-1"));
        assertFalse(hasIndex("123 fdafd"));
        assertFalse(hasIndex("fdd 123"));
        assertFalse(hasIndex("123fdd 123"));
    }

    @Test

    public void getIndexTest() {
        assertEquals(1, getIndex("1"));
        assertEquals(1232, getIndex("1232"));

        assertEquals(-1, getIndex("0"));
        assertEquals(-1, getIndex("-1"));
        assertEquals(-1, getIndex("-232"));
        assertEquals(-1, getIndex("abdc"));
        assertEquals(-1, getIndex("123 2abdc"));

    }
}
