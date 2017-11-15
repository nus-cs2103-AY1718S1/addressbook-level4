//@@author shuang-yang
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.ShowPhotoSelectionEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Displays a file chooser for updating of photo.
 */
public class UpdatePhotoCommand extends Command {

    public static final String COMMAND_WORD = "updatephoto";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Update photo of the person identified by the index "
            + "number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_UPDATE_PHOTO_SUCCESS = "Updated photo of person: %1$s";

    private final Index index;

    public UpdatePhotoCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute() {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        EventsCenter.getInstance().post(new ShowPhotoSelectionEvent(index));
        return new CommandResult(String.format(MESSAGE_UPDATE_PHOTO_SUCCESS, lastShownList.get(index.getZeroBased())));
    }
}
