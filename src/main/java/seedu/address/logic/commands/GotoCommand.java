//@@author thanhson16198
package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.GotoRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.place.ReadOnlyPlace;
import seedu.address.ui.MainWindow;

/**
 * Display the place identified using it's last displayed index from the address book.
 */
public class GotoCommand extends Command {

    public static final String COMMAND_WORD = "goto";
    public static final String COMMAND_WORD_ALIAS = "Goto";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Display the place on Google Map identified by the index number used in the last place listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_GOTO_SUCCESS = "Go to: %1$s";

    private final Index targetIndex;

    public GotoCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPlace> lastShownList = model.getFilteredPlaceList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PLACE_DISPLAYED_INDEX);
        }

        ReadOnlyPlace locationToGo = lastShownList.get(targetIndex.getZeroBased());

        //  Open the Google Maps on BrowserPanel
        MainWindow.loadUrl("https://www.google.com.sg/maps/place/"
                + locationToGo.getName().fullName.replaceAll(" ", "+") + "+"
                + locationToGo.getPostalCode().toString().replaceAll(" ", "+"));

        EventsCenter.getInstance().post(new GotoRequestEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_GOTO_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GotoCommand // instanceof handles nulls
                && this.targetIndex.equals(((GotoCommand) other).targetIndex)); // state check
    }
}
//@@author
