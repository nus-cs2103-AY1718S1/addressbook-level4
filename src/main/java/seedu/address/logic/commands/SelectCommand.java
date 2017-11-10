package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.util.AppUtil.PanelChoice;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.insurance.ReadOnlyInsurance;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Selects a person identified using it's last displayed index from the address book.
 */
public class SelectCommand extends Command {

    public static final String[] COMMAND_WORDS = {"select", "s", "choose", "sel"};
    public static final String COMMAND_WORD = "select";

    public static final String MESSAGE_USAGE = concatenateCommandWords(COMMAND_WORDS)
            + ": Selects the person/insurance identified by the index number used in the last listing.\n"
            + "An additional argument left/l/right/r/person/p/insurance/i can be added to indicate\n"
            + "choice of left or right panel. Choice is person panel by default.\n"
            + "Parameters: INDEX (must be a positive integer) [PANEL_CHOICE]\n"
            + "Example: " + COMMAND_WORD + " 1 r";

    public static final String MESSAGE_SELECT_PERSON_SUCCESS = "Selected Person: %1$s";
    public static final String MESSAGE_SELECT_INSURANCE_SUCCESS = "Selected Insurance: %1$s";

    private final Index targetIndex;
    private final PanelChoice panelChoice;

    //@@author Juxarius
    public SelectCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
        this.panelChoice = PanelChoice.PERSON;
    }

    public SelectCommand(Index targetIndex, PanelChoice panelChoice) {
        this.targetIndex = targetIndex;
        this.panelChoice = panelChoice;
    }


    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        List<ReadOnlyInsurance> insuranceList = model.getFilteredInsuranceList();

        if (panelChoice == PanelChoice.PERSON && targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        } else if (panelChoice == PanelChoice.INSURANCE && targetIndex.getZeroBased() >= insuranceList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_INSURANCE_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex, panelChoice));
        return new CommandResult(String.format(
                panelChoice == PanelChoice.PERSON ? MESSAGE_SELECT_PERSON_SUCCESS : MESSAGE_SELECT_INSURANCE_SUCCESS,
                targetIndex.getOneBased()));
    }
    //@@author

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectCommand // instanceof handles nulls
                && this.targetIndex.equals(((SelectCommand) other).targetIndex)); // state check
    }
}
