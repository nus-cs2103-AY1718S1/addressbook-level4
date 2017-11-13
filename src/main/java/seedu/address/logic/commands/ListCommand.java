package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Arrays;

import seedu.address.model.person.ContainsKeywordsPredicate;

//@@author tingtx

/**
 * Lists all persons in the address book to the user or
 * list a specified group of persons.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": List the Address Book or ONE specified group of contact.\n"
            + "Parameters: [g/GROUP]\n"
            + "Example: " + COMMAND_WORD + "\n"
            + "Example: " + COMMAND_WORD + " g/friends";
    public static final String MESSAGE_LIST_ALL_SUCCESS = "Listed all persons";
    public static final String MESSAGE_LIST_GROUP_SUCCESS = "Listed all persons in ";
    public static final String MESSAGE_LIST_WRONG_PARAMETER = "Group does not exist!";

    private final ContainsKeywordsPredicate predicate;
    private final String listParameter;

    public ListCommand(String predicate) {
        this.listParameter = predicate;
        predicate = predicate.isEmpty() ? predicate : predicate.substring(2).trim();
        this.predicate = new ContainsKeywordsPredicate(Arrays.asList(predicate));

    }

    public static String getCommandWord() {
        return COMMAND_WORD;
    }

    @Override
    public CommandResult execute() {

        if (listParameter.isEmpty()) {
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(MESSAGE_LIST_ALL_SUCCESS);
        }

        model.updateFilteredPersonList(predicate);

        if (model.getFilteredPersonList().size() == 0) {
            return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size())
                    + " " + MESSAGE_LIST_WRONG_PARAMETER);
        }
        return new CommandResult(MESSAGE_LIST_GROUP_SUCCESS + listParameter.substring(2).trim());

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListCommand // instanceof handles nulls
                && this.listParameter.equals(((ListCommand) other).listParameter)); // state check
    }
}
