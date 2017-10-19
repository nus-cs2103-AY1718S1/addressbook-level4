package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.function.Predicate;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists Address Book contacts with specified tag.\n"
            + "Parameters: TAG\n"
            + "Example: " + COMMAND_WORD + " friend";

    public static final String MESSAGE_SUCCESS = "Listed all persons%s";

    private String tagToList;

    public ListCommand(String tag) {
        this.tagToList = tag;
    }

    @Override
    public CommandResult execute() throws CommandException {
        if ("all".equalsIgnoreCase(tagToList)) {
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(String.format(MESSAGE_SUCCESS, "."));
        }

        try {
            Predicate <ReadOnlyPerson> predicateShowAllTagged = model.getPredicateForTags(tagToList);
            model.updateFilteredPersonList(predicateShowAllTagged);
            String concat = " with " + tagToList + " tag.";
            return new CommandResult(String.format(MESSAGE_SUCCESS, concat));
        } catch (IllegalValueException ive) {
            throw new CommandException(Tag.MESSAGE_TAG_CONSTRAINTS);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListCommand // instanceof handles nulls
                && this.tagToList.equals(((ListCommand) other).tagToList)); // state check
    }
}
