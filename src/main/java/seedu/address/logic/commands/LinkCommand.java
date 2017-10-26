package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Remark;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing person in the address book.
 */
public class LinkCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "link";
    public static final String COMMAND_ALIAS = "lk";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Link the task with the people specified "
            + "by the index number used in the last person listing.\n "
            + "Parameters: TaskIndex (must be a positive integer) "
            + PREFIX_PERSON + "personIndex "
            + "[" + PREFIX_PERSON + "personIndex]... \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PERSON + "2";


    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "linked Task: %1$s";
    private final Index index;
    private final ArrayList<Index> personIndices;

    /**
     * @param index of the task in the filtered task list to edit
     * @param
     */
    public LinkCommand(Index index, ArrayList<Index> personIndices) {
        requireNonNull(index);
        requireNonNull(personIndices);

        this.index = index;
        this.personIndices = personIndices;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        throw new CommandException("not implemented yet");
    }
}
