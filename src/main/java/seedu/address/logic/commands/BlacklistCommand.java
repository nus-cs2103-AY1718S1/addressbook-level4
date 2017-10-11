package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_BLACKLISTED_PERSONS;

/**
 * Lists all blacklisted persons in the address book to the user.
 */
public class BlacklistCommand extends Command {

    public static final String COMMAND_WORD = "blacklist";
    public static final String COMMAND_WORD_ALIAS = "bl";

    public static final String MESSAGE_SUCCESS = "Blacklist: Listed all clients "
            + "who are prohibited from borrowing money";


    @Override
    public CommandResult execute() {
        model.changeListTo(COMMAND_WORD);
        model.updateFilteredBlacklistedPersonList(PREDICATE_SHOW_ALL_BLACKLISTED_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
