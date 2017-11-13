package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_BLACKLISTED_PERSONS;

import seedu.address.commons.core.ListObserver;

//@@author jaivigneshvenugopal
/**
 * Lists all blacklisted persons in the address book to the user.
 */
public class BlacklistCommand extends Command {

    public static final String COMMAND_WORD = "blacklist";
    public static final String COMMAND_WORD_ALIAS = "bl";

    public static final String MESSAGE_SUCCESS = "Listed all debtors "
            + "who are prohibited from borrowing money";


    @Override
    public CommandResult execute() {
        requireNonNull(model);
        model.deselectPerson();
        model.changeListTo(COMMAND_WORD);
        model.updateFilteredBlacklistedPersonList(PREDICATE_SHOW_ALL_BLACKLISTED_PERSONS);
        String currentList = ListObserver.getCurrentListName();
        return new CommandResult(currentList + MESSAGE_SUCCESS);
    }
}
