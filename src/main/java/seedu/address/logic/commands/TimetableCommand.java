package seedu.address.logic.commands;

import static seedu.address.commons.core.MessageAlignmentFormatter.FORMAT_ALIGNMENT_TO_EXAMPLE;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.TimetableDisplayEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;

//@@author zacharytang
/**
 * Selects persons identified using their last displayed indexes and displays a combined timetable
 * of the selected persons
 */
public class TimetableCommand extends Command {

    public static final String COMMAND_WORD = "whenfree";
    public static final String COMMAND_ALIAS = "wf";
    public static final String COMMAND_SECONDARY = "timetable";

    public static final String MESSAGE_USAGE =  "| " + COMMAND_WORD + " |"
            + ": Displays a combined timetable of persons, identified using their last displayed indexes.\n"
            + "Parameters: INDEX (must be positive integers)\n"
            + "Example: " + COMMAND_WORD + " 1\n"
            + FORMAT_ALIGNMENT_TO_EXAMPLE + COMMAND_WORD + " 1, 2, 3\n"
            + FORMAT_ALIGNMENT_TO_EXAMPLE + COMMAND_WORD + " 2 4";

    public static final String MESSAGE_DISPLAY_SUCCESS = "Displayed timetables: ";

    private final ArrayList<Index> targetIndexes;

    public TimetableCommand(ArrayList<Index> targetIndexes) {
        this.targetIndexes = targetIndexes;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        List<ReadOnlyPerson> personsToDisplay;

        // If no indexes passed, display all persons listed
        if (targetIndexes.size() == 0) {
            personsToDisplay = model.getFilteredPersonList();
        } else {
            personsToDisplay = new ArrayList<>();

            for (Index index : targetIndexes) {
                if (index.getZeroBased() >= lastShownList.size()) {
                    throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
                }
                ReadOnlyPerson personSelected = lastShownList.get(index.getZeroBased());
                personsToDisplay.add(personSelected);
            }
        }

        EventsCenter.getInstance().post(new TimetableDisplayEvent(personsToDisplay));
        return new CommandResult(generateResultMsg(personsToDisplay));
    }

    /**
     * Generates the success message for the timetable command
     */
    private String generateResultMsg(List<ReadOnlyPerson> personList) {
        StringBuilder msg = new StringBuilder();

        msg.append(MESSAGE_DISPLAY_SUCCESS);

        for (ReadOnlyPerson person : personList) {
            msg.append("[");
            msg.append(person.getName().toString());
            msg.append("]\n");
        }

        return msg.toString();
    }
}
