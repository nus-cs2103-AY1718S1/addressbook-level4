//@@author nguyenvanhoang7398
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.StringJoiner;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * Command to add multiple contacts at once
 */
public class AddMultipleByTsvCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addMulTsv";
    public static final String COMMAND_ALIAS = "addMT";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds multiple people to the address book "
            + "given a tsv (tab separated value) txt file containing their contact information. "
            + "Parameters: TSV_PATH\n"
            + "Example: " + COMMAND_WORD + " D:/Contacts.txt";

    public static final String MESSAGE_SUCCESS = "%d new person (people) added";
    public static final String MESSAGE_DUPLICATE_PERSON = "%d new person (people) duplicated";
    public static final String MESSAGE_NUMBER_OF_ENTRIES_FAILED = "%d entry (entries) failed: ";

    private final ArrayList<Person> toAdd;
    private final ArrayList<Integer> failedEntries;
    private final boolean isFileFound;

    public AddMultipleByTsvCommand(ArrayList<ReadOnlyPerson> toAddPeople, ArrayList<Integer> failedEntries,
                                   boolean isFileFound) {
        this.toAdd = new ArrayList<Person>();
        this.failedEntries = failedEntries;
        this.isFileFound = isFileFound;
        for (ReadOnlyPerson person: toAddPeople) {
            toAdd.add(new Person(person));
        }
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        if (!isFileFound) {
            return new CommandResult(Messages.MESSAGE_FILE_NOT_FOUND);
        }
        int numAdded = 0;
        int numDuplicated = 0;

        for (Person person: toAdd) {
            try {
                model.addPerson(person);
                numAdded++;
            } catch (DuplicatePersonException e) {
                numDuplicated++;
            }
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, numAdded) + ", "
                + String.format(MESSAGE_DUPLICATE_PERSON, numDuplicated) + ", "
                + String.format(MESSAGE_NUMBER_OF_ENTRIES_FAILED, failedEntries.size())
                + joinFailedEntries(failedEntries));
    }

    /**
     * Join the list of integers of failed entries into string
     * @param failedEntries
     * @return
     */
    private static String joinFailedEntries(ArrayList<Integer> failedEntries) {
        StringJoiner joiner = new StringJoiner(", ");
        for (Integer entry: failedEntries) {
            joiner.add(entry.toString());
        }
        return joiner.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddMultipleByTsvCommand // instanceof handles nulls
                && toAdd.equals(((AddMultipleByTsvCommand) other).toAdd));
    }

}
