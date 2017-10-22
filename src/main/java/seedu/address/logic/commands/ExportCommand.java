package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Export the information about a person as a line of code that can be
 * recognized by the Add command of 3W, for immigration purposes.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";
    public static final String COMMAND_ALIAS = "ep";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Export the details of the person identified "
            + "by the index number used in the last person listing. \n"
            + "Output will be in an add command format, which can be "
            + "directly given to 3W to excute.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " 1 ";

    private final Index targetIndex;

    /**
     * @param index of the person in the filtered person list to edit
     */
    public ExportCommand(Index index) {
        requireNonNull(index);
        this.targetIndex = index;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex));
        ReadOnlyPerson personToExport = lastShownList.get(targetIndex.getZeroBased());

        final StringBuilder builder = new StringBuilder();
        builder.append("add n/")
                .append(personToExport.getName())
                .append(" p/")
                .append(personToExport.getPhone())
                .append(" e/")
                .append(personToExport.getEmail())
                .append(" a/")
                .append(personToExport.getAddress())
                .append(" r/")
                .append(personToExport.getRemark())
                .append(" t/");
        personToExport.getTags().forEach(builder::append);
        return new CommandResult(builder.toString());

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExportCommand // instanceof handles nulls
                && this.targetIndex.equals(((ExportCommand) other).targetIndex)); // state check
    }

}
