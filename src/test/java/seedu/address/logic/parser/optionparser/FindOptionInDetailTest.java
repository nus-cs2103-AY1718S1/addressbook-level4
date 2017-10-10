package seedu.address.logic.parser.optionparser;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindCommand.FindDetailDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.DetailsContainsPredicate;
import seedu.address.testutil.FindDetailDescriptorBuilder;

public class FindOptionInDetailTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void test_parseEmptyArg_throwException() throws Exception {
        thrown.expect(ParseException.class);
        new FindOptionInDetail("").parse();
    }

    @Test
    public void test_parseOptionArgs_assertSuccess() throws Exception {
        String inputOptionArgs;
        FindDetailDescriptor expectedDescriptor;

        // test name
        inputOptionArgs = "n/Ali";
        expectedDescriptor = new FindDetailDescriptorBuilder().withName("Ali").build();
        assertParseSuccess(expectedDescriptor, new FindOptionInDetail(inputOptionArgs).parse());

        // test phone
        inputOptionArgs = "p/999";
        expectedDescriptor = new FindDetailDescriptorBuilder().withPhone("999").build();
        assertParseSuccess(expectedDescriptor, new FindOptionInDetail(inputOptionArgs).parse());

        // test email
        inputOptionArgs = "e/com";
        expectedDescriptor = new FindDetailDescriptorBuilder().withEmail("com").build();
        assertParseSuccess(expectedDescriptor, new FindOptionInDetail(inputOptionArgs).parse());

        // test address
        inputOptionArgs = "a/pgp";
        expectedDescriptor = new FindDetailDescriptorBuilder().withAddress("pgp").build();
        assertParseSuccess(expectedDescriptor, new FindOptionInDetail(inputOptionArgs).parse());

        // test one tag
        inputOptionArgs = "t/fr";
        expectedDescriptor = new FindDetailDescriptorBuilder().withTags("fr").build();
        assertParseSuccess(expectedDescriptor, new FindOptionInDetail(inputOptionArgs).parse());

        // test tags
        inputOptionArgs = "t/fr t/family";
        expectedDescriptor = new FindDetailDescriptorBuilder().withTags("fr", "family").build();
        assertParseSuccess(expectedDescriptor, new FindOptionInDetail(inputOptionArgs).parse());

        // test mix
        inputOptionArgs = "n/alic a/pgp";
        expectedDescriptor = new FindDetailDescriptorBuilder().withName("alic").withAddress("pgp").build();
        assertParseSuccess(expectedDescriptor, new FindOptionInDetail(inputOptionArgs).parse());

        // test mix
        inputOptionArgs = "e/12 p/qq";
        expectedDescriptor = new FindDetailDescriptorBuilder().withEmail("12").withPhone("qq").build();
        assertParseSuccess(expectedDescriptor, new FindOptionInDetail(inputOptionArgs).parse());

        // test mix
        inputOptionArgs = "n/alic p/999 e/u.nus.edu a/utown t/friend t/classmate";
        expectedDescriptor = new FindDetailDescriptorBuilder()
                .withName("alic").withPhone("999").withEmail("u.nus.edu")
                .withAddress("utown").withTags("friend", "classmate").build();
        assertParseSuccess(expectedDescriptor, new FindOptionInDetail(inputOptionArgs).parse());

        // test add whitespace
        inputOptionArgs = "n/ Ali";
        expectedDescriptor = new FindDetailDescriptorBuilder().withName("Ali").build();
        assertParseSuccess(expectedDescriptor, new FindOptionInDetail(inputOptionArgs).parse());

        // test add whitespace
        inputOptionArgs = " n/ Ali ";
        expectedDescriptor = new FindDetailDescriptorBuilder().withName("Ali").build();
        assertParseSuccess(expectedDescriptor, new FindOptionInDetail(inputOptionArgs).parse());
    }

    @Test
    public void test_inValidDescriptor_wrongPrefix() throws Exception {
        thrown.expect(ParseException.class);
        String inputOptionArgs = "pp/999";
        new FindOptionInDetail(inputOptionArgs).parse();
    }

    @Test
    public void test_inValidDescriptor_noPrefix() throws Exception {
        thrown.expect(ParseException.class);
        String inputOptionArgs = "pgp";
        new FindOptionInDetail(inputOptionArgs).parse();
    }

    private static void assertParseSuccess(FindDetailDescriptor excepted, FindCommand actual) {
        Assert.assertTrue(actual.equals(new FindCommand(new DetailsContainsPredicate(excepted))));
    }

}
