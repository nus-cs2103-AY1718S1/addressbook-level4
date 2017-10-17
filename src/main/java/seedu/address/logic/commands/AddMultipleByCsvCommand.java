package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;

import java.util.ArrayList;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

public class AddMultipleByCsvCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addMulCsv";
    public static final String COMMAND_ALIAS = "addMC";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds multiple people to the address book "
            + "given a csv file containing their contact information. "
            + "Parameters: CSV_PATH\n"
            + "Example: " + COMMAND_WORD + " D:/Contacts.csv";

    public static final String MESSAGE_SUCCESS = "%d new person (people) added";
    public static final String MESSAGE_DUPLICATE_PERSON = "%d new person (people) duplicated";

    private final ArrayList<Person> toAdd;

    public AddMultipleByCsvCommand(ArrayList<ReadOnlyPerson> toAddPeople) {
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
                || (other instanceof AddMultipleByCsvCommand // instanceof handles nulls
                && toAdd.equals(((AddMultipleByCsvCommand) other).toAdd));
    }

}
