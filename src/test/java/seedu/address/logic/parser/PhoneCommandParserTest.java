package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;


import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.PhoneCommand;
import seedu.address.model.person.phone.Phone;


/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class PhoneCommandParserTest {

    private PhoneCommandParser parser = new PhoneCommandParser();

        /*
        @Test
        public void parse_validArgs_returnsPhoneCommand () throws IllegalValueException {
            PhoneCommand expectedPhoneCommand = new PhoneCommand(INDEX_FIRST_PERSON, "add", new Phone("233333"));
            assertParseSuccess(parser, "1 add 233333", expectedPhoneCommand);
        }
        */


        @Test
        public void parse_invalidArgs_throwsParseException () {
            assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, PhoneCommand.MESSAGE_USAGE));
        }



}