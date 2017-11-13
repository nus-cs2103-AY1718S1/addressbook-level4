package seedu.address.logic.commands;

//@@author jeffreygohkw
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.BrowserPanelNavigateEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Location;


/**
 * Navigates from one address to another with the help of Google Maps
 */
public class NavigateCommand extends Command {

    public static final String COMMAND_WORD = "navigate";
    public static final String COMMAND_ALIAS = "nav";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Get directions from one address to another.\n"
            + "Parameters: [fp/INDEX] OR [ft/INDEX] (must be a positive integer) OR [fa/ADDRESS] (Only one of three)"
            + " AND [tp/INDEX] OR [tt/INDEX] (must be a positive integer) OR [ta/ADDRESS] (Only one of three)\n"
            + "Example: " + COMMAND_WORD + " fp/2 ta/University Town";

    public static final String MESSAGE_NAVIGATE_SUCCESS = "Navigating from %1$s to %2$s";
    public static final String MESSAGE_MULTIPLE_FROM_ERROR = "Only one type of From prefix allowed.";
    public static final String MESSAGE_MULTIPLE_TO_ERROR = "Only one type of To prefix allowed.";
    public static final String MESSAGE_PRIVATE_PERSON_ADDRESS_ERROR = "Address of the Person at index %1$s is private.";
    public static final String MESSAGE_PERSON_HAS_NO_ADDRESS_ERROR = "Person at index %1$s does not have an address.";
    public static final String MESSAGE_TASK_HAS_NO_ADDRESS_ERROR = "Task at index %1$s does not have an address.";

    private final Location locationFrom;
    private final Location locationTo;
    private final Index fromIndex;
    private final Index toIndex;
    private final boolean fromIsTask;
    private final boolean toIsTask;
    public NavigateCommand(Location locationFrom, Location locationTo, Index fromIndex, Index toIndex,
                           boolean fromIsTask, boolean toIsTask) throws IllegalValueException {
        Location from = null;
        Location to = null;
        checkDuplicateFromAndToLocation(locationFrom, locationTo, fromIndex, toIndex);

        if (locationFrom != null) {
            from = locationFrom;
        }
        if (locationTo != null) {
            to = locationTo;
        }

        this.locationFrom = from;
        this.locationTo = to;
        this.toIndex = toIndex;
        this.fromIndex = fromIndex;
        this.toIsTask = toIsTask;
        this.fromIsTask = fromIsTask;
    }

    /**
     * Throws an IllegalArgumentException if there is both locationFrom and fromIndex are not null,
     * or if both locationTo and toIndex are not null.
     */
    private void checkDuplicateFromAndToLocation(Location locationFrom, Location locationTo,
                                                 Index fromIndex, Index toIndex) throws IllegalArgumentException {
        if (locationFrom != null && fromIndex != null) {
            throw new IllegalArgumentException(MESSAGE_MULTIPLE_FROM_ERROR);
        }
        if (locationTo != null && toIndex != null) {
            throw new IllegalArgumentException(MESSAGE_MULTIPLE_TO_ERROR);
        }
    }

    private Location setLocationByIndex(Index index, boolean isTask) throws IllegalValueException, CommandException {
        if (isTask) {
            if (model.getFilteredTaskList().get(index.getZeroBased()).getTaskAddress().toString().equals("")) {
                throw new CommandException(String.format(MESSAGE_TASK_HAS_NO_ADDRESS_ERROR, index.getOneBased()));
            } else {
                return new Location(model.getFilteredTaskList().get(index.getZeroBased()).getTaskAddress().toString());
            }
        } else {
            if (model.getFilteredPersonList().get(index.getZeroBased()).getAddress().toString().equals("")) {
                throw new CommandException(String.format(MESSAGE_PERSON_HAS_NO_ADDRESS_ERROR, index.getOneBased()));
            } else if (model.getFilteredPersonList().get(index.getZeroBased()).getAddress().getIsPrivate()) {
                throw new CommandException(String.format(MESSAGE_PRIVATE_PERSON_ADDRESS_ERROR, index.getOneBased()));
            } else {
                return new Location(model.getFilteredPersonList().get(index.getZeroBased())
                        .getAddress().toString());
            }
        }
    }

    @Override
    public CommandResult execute() throws CommandException {
        Location from;
        Location to;
        if (fromIndex != null) {
            try {
                from = setLocationByIndex(fromIndex, fromIsTask);
            } catch (IllegalValueException e) {
                throw new IllegalArgumentException(MESSAGE_PRIVATE_PERSON_ADDRESS_ERROR);
            }
        } else {
            from = locationFrom;
        }
        if (toIndex != null) {
            try {
                to = setLocationByIndex(toIndex, toIsTask);
            } catch (IllegalValueException e) {
                throw new IllegalArgumentException(MESSAGE_PRIVATE_PERSON_ADDRESS_ERROR);
            }
        } else {
            to = locationTo;
        }
        EventsCenter.getInstance().post(new BrowserPanelNavigateEvent(from, to));
        return new CommandResult(String.format(MESSAGE_NAVIGATE_SUCCESS, from, to));
    }

    public Location getLocationFrom() {
        return locationFrom;
    }

    public Location getLocationTo() {
        return locationTo;
    }

    public Index getFromIndex() {
        return fromIndex;
    }

    public Index getToIndex() {
        return toIndex;
    }

    public boolean isFromIsTask() {
        return fromIsTask;
    }

    public boolean isToIsTask() {
        return toIsTask;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NavigateCommand // instanceof handles nulls
                && equalsLocationFrom(other)
                && equalsLocationTo(other)
                && equalsFromIndex(other)
                && equalsToIndex(other)
                && equalsFromIsTask(other)
                && equalsToIsTask(other)); // state check
    }

    /**
     * Checks if the (@codde locationFrom) of this object is equal to that of the other Object
     * @param other The other Object we are comparing against
     * @return True if both are null or both have the same value
     */
    private boolean equalsLocationFrom(Object other) {
        if (this.locationFrom == null) {
            return ((NavigateCommand) other).locationFrom == null;
        } else {
            return this.locationFrom.equals(((NavigateCommand) other).locationFrom);
        }
    }

    /**
     * Checks if the (@codde locationTo) of this object is equal to that of the other Object
     * @param other The other Object we are comparing against
     * @return True if both are null or both have the same value
     */
    private boolean equalsLocationTo(Object other) {
        if (this.locationTo == null) {
            return ((NavigateCommand) other).locationTo == null;
        } else {
            return this.locationTo.equals(((NavigateCommand) other).locationTo);
        }
    }

    /**
     * Checks if the (@codde fromIndex) of this object is equal to that of the other Object
     * @param other The other Object we are comparing against
     * @return True if both are null or both have the same value
     */
    private boolean equalsFromIndex(Object other) {
        if (this.fromIndex == null) {
            return ((NavigateCommand) other).fromIndex == null;
        } else {
            return this.fromIndex.equals(((NavigateCommand) other).fromIndex);
        }
    }

    /**
     * Checks if the (@codde toIndex) of this object is equal to that of the other Object
     * @param other The other Object we are comparing against
     * @return True if both are null or both have the same value
     */
    private boolean equalsToIndex(Object other) {
        if (this.toIndex == null) {
            return ((NavigateCommand) other).toIndex == null;
        } else {
            return this.toIndex.equals(((NavigateCommand) other).toIndex);
        }
    }

    /**
     * Checks if the (@codde fromIsTask) of this object is equal to that of the other Object
     * @param other The other Object we are comparing against
     * @return True if both are null or both have the same value
     */
    private boolean equalsFromIsTask(Object other) {
        return this.fromIsTask == (((NavigateCommand) other).fromIsTask);
    }

    /**
     * Checks if the (@codde toIsTask) of this object is equal to that of the other Object
     * @param other The other Object we are comparing against
     * @return True if both are null or both have the same value
     */
    private boolean equalsToIsTask(Object other) {
        return this.toIsTask == (((NavigateCommand) other).toIsTask);
    }
}
