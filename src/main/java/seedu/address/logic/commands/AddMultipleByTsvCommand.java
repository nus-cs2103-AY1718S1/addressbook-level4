package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;

import java.util.ArrayList;

import static java.util.Objects.requireNonNull;

public class AddMultipleByTsvCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addMulTsv";
    public static final String COMMAND_ALIAS = "addMT";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds multiple people to the address book "
            + "given a tsv (tab separated value) txt file containing their contact information. "
            + "Parameters: TSV_PATH\n"
            + "Example: " + COMMAND_WORD + " D:/Contacts.txt";

    public static final String MESSAGE_SUCCESS = "%d new person (people) added";
    public static final String MESSAGE_DUPLICATE_PERSON = "%d new person (people) duplicated";

    private final ArrayList<Person> toAdd;

    public AddMultipleByTsvCommand(ArrayList<ReadOnlyPerson> toAddPeople) {
        this.toAdd = new ArrayList<Person>();
        for (ReadOnlyPerson person: toAddPeople) {
            toAdd.add(new Person(person));
        }
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        int numAdded = 0;
        int numDuplicated = 0;

        for(Person person: toAdd) {
            try {
                model.addPerson(person);
                numAdded++;
            } catch (DuplicatePersonException e) {
                numDuplicated++;
            }
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, numAdded) + ", "
                + String.format(MESSAGE_DUPLICATE_PERSON, numDuplicated));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddMultipleByTsvCommand // instanceof handles nulls
                && toAdd.equals(((AddMultipleByTsvCommand) other).toAdd));
    }

}
