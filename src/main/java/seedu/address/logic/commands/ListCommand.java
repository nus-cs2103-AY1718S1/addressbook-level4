package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.model.person.PersonContainsKeywordsPredicate;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    //@@author aggarwalRuchir
    public static final String MESSAGE_SUCCESS_FULLLIST  = "Listed all persons";
    public static final String MESSAGE_SUCCESS_FILTEREDLIST  = "Listed all persons with tag: ";

    public static final String MESSAGE_NOENTRIESFOUND = "No person with given tags found.";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists all the people in address or people with certain tags.\n"
            + "Parameters: [optional]Tag\n"
            + "Example: " + COMMAND_WORD + "\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_TAG + "friends";

    private final PersonContainsKeywordsPredicate predicate;

    public ListCommand() {
        this.predicate = null;
    }

    public ListCommand (PersonContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        if (this.predicate != null) {
            model.updateFilteredPersonList(predicate);
        } else {
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(MESSAGE_SUCCESS_FULLLIST);
        }

        if (areEntriesWithTagsFound()) {
            return new CommandResult(MESSAGE_SUCCESS_FILTEREDLIST + this.predicate.returnListOfTagsAsString());
        } else {
            return new CommandResult(MESSAGE_NOENTRIESFOUND);
        }
    }

    /**
     * Returns true if any entries are found for a tag in the address book
     */
    private boolean areEntriesWithTagsFound() {
        if (model.getFilteredPersonList().size() != 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ListCommand)) {
            return false;
        }

        // other has null predicate
        if ((((ListCommand) other).predicate == null) && (this.predicate == null)) {
            return true;
        }

        return this.predicate.equals(((ListCommand) other).predicate);
    }
}
