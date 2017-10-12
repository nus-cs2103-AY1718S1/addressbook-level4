package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;

import java.util.List;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

public class LocateCommand extends Command{

    public static final String COMMAND_WORD = "locate";
    public static final String COMMAND_ALIAS = "lct";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Locate the person specified on google map.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_LOCATE_PERSON_SUCCESS = "Address searched as showed";

    private final Index targetIndex;

    public LocateCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_LOCATE_PERSON_SUCCESS);
    }
}
