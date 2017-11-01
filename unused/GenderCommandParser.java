//@@author eeching
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;

import java.util.StringTokenizer;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.GenderCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Gender;

/**
 * Parses input arguments and creates a new GenderCommand object
 */
public class GenderCommandParser implements Parser<GenderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the GenderCommand
     * and returns a GenderCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public GenderCommand parse(String args) throws ParseException {
        try {
            StringTokenizer st = new StringTokenizer(args);
            Index index = ParserUtil.parseIndex(st.nextToken());
            String genderInput = st.nextToken();
            String prefix = genderInput.substring(0, 2);

            if (!prefix.equals(PREFIX_GENDER.getPrefix())) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, GenderCommand.MESSAGE_USAGE));
            }

            Gender gender = new Gender(genderInput.substring(2));
            return new GenderCommand(index, gender);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, GenderCommand.MESSAGE_USAGE));
        }
    }

}