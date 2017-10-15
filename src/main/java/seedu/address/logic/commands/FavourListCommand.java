package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ListingUnit;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.person.predicates.UniqueAddressPredicate;
import seedu.address.model.person.predicates.UniqueEmailPredicate;
import seedu.address.model.person.predicates.UniquePhonePredicate;

/**
 * Collects a person identified using it's last displayed index from the address book into the favor list.
 */
public class CollectCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "collect";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": adds the person into favour list identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_COLLECT_PERSON_SUCCESS = "Collected Person: %1$s";

    private final Index targetIndex;

    public CollectCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToCollect = lastShownList.get(targetIndex.getZeroBased());

        switch (ListingUnit.getCurrentListingUnit()) {

            case ADDRESS:
                throw new CommandException("Cannot collect address");

            case PHONE:
                throw new CommandException("Cannot collect phone");

            case EMAIL:
                throw new CommandException("Cannot collect email");

            default:
                try {
                    model.collectPerson(personToCollect);
                } catch (DuplicatePersonException pnfe) {
                   throw new CommandException(pnfe.getMessage());
                }
            return new CommandResult(String.format(MESSAGE_COLLECT_PERSON_SUCCESS, personToCollect));
        }
    }



    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CollectCommand // instanceof handles nulls
                && this.targetIndex.equals(((CollectCommand) other).targetIndex)); // state check
    }
}
