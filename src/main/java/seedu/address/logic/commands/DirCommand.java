package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.GotoRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.place.ReadOnlyPlace;
import seedu.address.ui.MainWindow;
//@@author Chng-Zhi-Xuan
/**
 * Shows direction from place1 to place2 using their last displayed index in the tourist book
 */
public class DirCommand extends Command {

    public static final String COMMAND_WORD = "dir";
    public static final String COMMAND_WORD_ALIAS = "Dir";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows the direction from one place to another, specified by their index used in the last list"
            + " that it was shown.\n"
            + "Parameters: INDEX1 INDEX2 (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 2";

    public static final String MESSAGE_DIR_SUCCESS = "Directions shown on Google maps.";

    private final Index indexFrom;
    private final Index indexTo;
    private final String emptySpace = " ";
    private final String plus = "+";
    private final String singaporeConcat = "+Singapore+";

    public DirCommand(Index indexFrom, Index indexTo) {
        this.indexFrom = indexFrom;
        this.indexTo = indexTo;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPlace> lastShownList = model.getFilteredPlaceList();

        if (indexFrom.getZeroBased() == indexTo.getZeroBased()
            || indexFrom.getZeroBased() >= lastShownList.size()
            || indexTo.getZeroBased() >= lastShownList.size()) {

            throw new CommandException(Messages.MESSAGE_INVALID_PLACE_DISPLAYED_INDEX);
        }

        ReadOnlyPlace placeStart = lastShownList.get(indexFrom.getZeroBased());
        ReadOnlyPlace placeDestination = lastShownList.get(indexTo.getZeroBased());

        String fullUrl = "https://www.google.com.sg/maps/dir/"
                + placeStart.getName().fullName.replaceAll(emptySpace, plus)
                + singaporeConcat
                + placeStart.getPostalCode()
                + "/"
                + placeDestination.getName().fullName.replaceAll(emptySpace, plus)
                + singaporeConcat
                + placeDestination.getPostalCode();



        MainWindow.loadUrl(fullUrl);


        EventsCenter.getInstance().post(new GotoRequestEvent(indexFrom));
        return new CommandResult(String.format(MESSAGE_DIR_SUCCESS, indexFrom.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DirCommand // instanceof handles nulls
                && this.indexTo.equals(((DirCommand) other).indexTo)); // state check
    }
}
