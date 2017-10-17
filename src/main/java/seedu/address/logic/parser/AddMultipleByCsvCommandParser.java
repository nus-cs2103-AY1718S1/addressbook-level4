package seedu.address.logic.parser;

import seedu.address.logic.ContactCsvReader;
import seedu.address.logic.commands.AddMultipleByCsvCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;

import java.util.Arrays;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class AddMultipleByCsvCommandParser implements Parser<AddMultipleByCsvCommand> {

    public AddMultipleByCsvCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMultipleByCsvCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");
        String contactCsvFilePath = nameKeywords[1];
        ContactCsvReader contactCsvReader = new ContactCsvReader(contactCsvFilePath);

        return new AddMultipleByCsvCommand(contactCsvReader.readContactsFromFile());
    }
}
