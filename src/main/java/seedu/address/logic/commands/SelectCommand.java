package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.ChangeSearchEvent;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;

import static seedu.address.logic.parser.optionparser.CommandOptionUtil.PREFIX_OPTION_INDICATOR;

/**
 * Selects a person identified using it's last displayed index from the address book.
 */
public class SelectCommand extends Command {

    public static final String COMMAND_WORD = "select";
    public static final String COMMAND_ALIAS = "s";

    /* Mode prefix definitions */
    public static final String PREFIX_SELECT_SEARCH_NAME = PREFIX_OPTION_INDICATOR + "n";
    public static final String PREFIX_SELECT_SEARCH_PHONE = PREFIX_OPTION_INDICATOR + "p";
    public static final String PREFIX_SELECT_SEARCH_EMAIL = PREFIX_OPTION_INDICATOR + "e";
    public static final String PREFIX_SELECT_SEARCH_ADDRESS = PREFIX_OPTION_INDICATOR + "a";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the person identified by the index number used in the last person listing.\n"
            + "Format: KEYWORD [OPTION] INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_SELECT_SEARCH_NAME + " 1\n"
            + "Options:\n"
            + "\t" + PREFIX_SELECT_SEARCH_NAME + "\t\tsearch name on browser\n"
            + "\t" + PREFIX_SELECT_SEARCH_PHONE + "\t\tsearch phone on browser\n"
            + "\t" + PREFIX_SELECT_SEARCH_EMAIL + "\t\tsearch email on browser\n"
            + "\t" + PREFIX_SELECT_SEARCH_ADDRESS + "\t\tshow address on google map";

    public static final String MESSAGE_SELECT_PERSON_SUCCESS = "Selected Person: %1$s";

    private final Index targetIndex;
    private final int searchMode;

    public SelectCommand(Index targetIndex, int searchMode) {
        this.targetIndex = targetIndex;
        this.searchMode = searchMode;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new ChangeSearchEvent(searchMode));
        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_SELECT_PERSON_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectCommand // instanceof handles nulls
                && this.targetIndex.equals(((SelectCommand) other).targetIndex))
                && this.searchMode == ((SelectCommand) other).searchMode; // state check
    }
}
