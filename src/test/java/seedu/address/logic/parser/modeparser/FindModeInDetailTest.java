package seedu.address.logic.parser.modeparser;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindCommand.FindDetailDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.DetailsContainsPredicate;
import seedu.address.testutil.FindDetailDescriptorBuilder;

public class FindModeInDetailTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void test_parseEmptyArg_throwException() throws Exception {
        thrown.expect(ParseException.class);
        new FindModeInDetail("").parse();
    }

    @Test
    public void test_parseModeArgs_assertSuccess() throws Exception {
        String inputModeArgs;
        FindDetailDescriptor expectedDescriptor;

        // test name
        inputModeArgs = "n/Ali";
        expectedDescriptor = new FindDetailDescriptorBuilder().withName("Ali").build();
        assertParseSuccess(expectedDescriptor, new FindModeInDetail(inputModeArgs).parse());

        // test phone
        inputModeArgs = "p/999";
        expectedDescriptor = new FindDetailDescriptorBuilder().withPhone("999").build();
        assertParseSuccess(expectedDescriptor, new FindModeInDetail(inputModeArgs).parse());

        // test email
        inputModeArgs = "e/com";
        expectedDescriptor = new FindDetailDescriptorBuilder().withEmail("com").build();
        assertParseSuccess(expectedDescriptor, new FindModeInDetail(inputModeArgs).parse());

        // test address
        inputModeArgs = "a/pgp";
        expectedDescriptor = new FindDetailDescriptorBuilder().withAddress("pgp").build();
        assertParseSuccess(expectedDescriptor, new FindModeInDetail(inputModeArgs).parse());

        // test one tag
        inputModeArgs = "t/fr";
        expectedDescriptor = new FindDetailDescriptorBuilder().withTags("fr").build();
        assertParseSuccess(expectedDescriptor, new FindModeInDetail(inputModeArgs).parse());

        // test tags
        inputModeArgs = "t/fr t/family";
        expectedDescriptor = new FindDetailDescriptorBuilder().withTags("fr", "family").build();
        assertParseSuccess(expectedDescriptor, new FindModeInDetail(inputModeArgs).parse());

        // test mix
        inputModeArgs = "n/alic a/pgp";
        expectedDescriptor = new FindDetailDescriptorBuilder().withName("alic").withAddress("pgp").build();
        assertParseSuccess(expectedDescriptor, new FindModeInDetail(inputModeArgs).parse());

        // test mix
        inputModeArgs = "e/12 p/qq";
        expectedDescriptor = new FindDetailDescriptorBuilder().withEmail("12").withPhone("qq").build();
        assertParseSuccess(expectedDescriptor, new FindModeInDetail(inputModeArgs).parse());

        // test mix
        inputModeArgs = "n/alic p/999 e/u.nus.edu a/utown t/friend t/classmate";
        expectedDescriptor = new FindDetailDescriptorBuilder()
                .withName("alic").withPhone("999").withEmail("u.nus.edu")
                .withAddress("utown").withTags("friend", "classmate").build();
        assertParseSuccess(expectedDescriptor, new FindModeInDetail(inputModeArgs).parse());

        // test add whitespace
        inputModeArgs = "n/ Ali";
        expectedDescriptor = new FindDetailDescriptorBuilder().withName("Ali").build();
        assertParseSuccess(expectedDescriptor, new FindModeInDetail(inputModeArgs).parse());

        // test add whitespace
        inputModeArgs = " n/ Ali ";
        expectedDescriptor = new FindDetailDescriptorBuilder().withName("Ali").build();
        assertParseSuccess(expectedDescriptor, new FindModeInDetail(inputModeArgs).parse());
    }

    @Test
    public void test_inValidDescriptor_wrongPrefix() throws Exception {
        thrown.expect(ParseException.class);
        String inputModeArgs = "pp/999";
        new FindModeInDetail(inputModeArgs).parse();
    }

    @Test
    public void test_inValidDescriptor_noPrefix() throws Exception {
        thrown.expect(ParseException.class);
        String inputModeArgs = "pgp";
        new FindModeInDetail(inputModeArgs).parse();
    }

    private static void assertParseSuccess(FindDetailDescriptor excepted, FindCommand actual) {
        Assert.assertTrue(actual.equals(new FindCommand(new DetailsContainsPredicate(excepted))));
    }

}