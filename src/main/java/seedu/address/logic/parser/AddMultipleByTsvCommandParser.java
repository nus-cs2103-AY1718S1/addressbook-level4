package seedu.address.logic.parser;

import seedu.address.logic.ContactTsvReader;
import seedu.address.logic.commands.AddMultipleByTsvCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ReadOnlyPerson;

import java.io.IOException;
import java.util.ArrayList;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class AddMultipleByTsvCommandParser implements Parser<AddMultipleByTsvCommand> {

    public AddMultipleByTsvCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMultipleByTsvCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");
        String contactCsvFilePath = nameKeywords[0];
        ContactTsvReader contactTsvReader = new ContactTsvReader(contactCsvFilePath);
        boolean isFileFound;
        ArrayList<ReadOnlyPerson> toAddPeople = new ArrayList<ReadOnlyPerson>();
        ArrayList<Integer> failedEntries = contactTsvReader.getFailedEntries();

        try {
            contactTsvReader.readContactsFromFile();
            toAddPeople = contactTsvReader.getToAddPeople();
            failedEntries = contactTsvReader.getFailedEntries();
            isFileFound = true;
        } catch (IOException e){
            isFileFound = false;
        }

        return new AddMultipleByTsvCommand(toAddPeople, failedEntries, isFileFound);
    }
}
