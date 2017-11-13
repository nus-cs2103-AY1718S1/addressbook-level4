package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.AppUtil.PanelChoice;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.insurance.ReadOnlyInsurance;
import seedu.address.model.insurance.exceptions.InsuranceNotFoundException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class DeleteCommand extends UndoableCommand {

    public static final String[] COMMAND_WORDS = {"delete", "del", "d", "-"};
    public static final String COMMAND_WORD = "delete";

    //@@author Juxarius
    public static final String MESSAGE_USAGE = concatenateCommandWords(COMMAND_WORDS)
            + ": Deletes the person/insurance identified by the index number used in the last listing.\n"
            + "An additional argument left/l/right/r/person/p/insurance/i can be added to indicate\n"
            + "choice of left or right panel. Choice is person panel by default.\n"
            + "Parameters: INDEX (must be a positive integer) [PANEL_CHOICE]\n"
            + "Example: " + COMMAND_WORD + " 1 insurance";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";
    public static final String MESSAGE_DELETE_INSURANCE_SUCCESS = "Deleted Insurance %1$s";

    private final Index targetIndex;
    private final PanelChoice panelChoice;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
        this.panelChoice = PanelChoice.PERSON;
    }


    public DeleteCommand(Index targetIndex, PanelChoice panelChoice) {
        this.targetIndex = targetIndex;
        this.panelChoice = panelChoice;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        List<ReadOnlyInsurance> insuranceList = model.getFilteredInsuranceList();

        if (panelChoice == PanelChoice.PERSON && targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        } else if (panelChoice == PanelChoice.INSURANCE && targetIndex.getZeroBased() >= insuranceList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_INSURANCE_DISPLAYED_INDEX);
        }

        String commandResultMessage = "";
        try {
            if (panelChoice == PanelChoice.PERSON) {
                ReadOnlyPerson personToDelete = lastShownList.get(targetIndex.getZeroBased());
                model.deletePerson(personToDelete);
                commandResultMessage = String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);
            } else if (panelChoice == PanelChoice.INSURANCE) {
                ReadOnlyInsurance insuranceToDelete = insuranceList.get(targetIndex.getZeroBased());
                model.deleteInsurance(insuranceToDelete);
                commandResultMessage = String.format(MESSAGE_DELETE_INSURANCE_SUCCESS,
                        insuranceToDelete.getInsuranceName());
            }
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        } catch (InsuranceNotFoundException infe) {
            assert false : "The target insurance cannot be missing";
        }
        return new CommandResult(commandResultMessage);
    }
    //@@author

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }

    //@@author arnollim
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(COMMAND_WORD + " ")
                .append(this.targetIndex.getOneBased());
        String command = builder.toString();
        return command;
    }
    //@@author
}
