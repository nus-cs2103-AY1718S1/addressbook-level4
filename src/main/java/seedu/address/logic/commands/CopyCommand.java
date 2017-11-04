package seedu.address.logic.commands;

import java.util.List;

import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class CopyCommand extends Command {

    public static final String COMMAND_WORD = "copy";
    public static final String COMMAND_ALIAS = "c"; // shorthand equivalent alias

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Copies email address of the person identified by index numbers used in the last person listing.\n"
            + COMMAND_ALIAS + ": Shorthand equivalent for Copy. \n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example 1: " + COMMAND_ALIAS + " 1 \n"
            + "Example 2: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_COPY_PERSON_SUCCESS = "%1$s's email address has been copied to your clipboard.";
    public static final String MESSAGE_COPY_PERSON_EMPTY = "%1$s has no email address.";

    private final Index targetIndex;
    private ReadOnlyPerson targetPerson;

    public CopyCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    //@@author rushan-khor
    @Override
    public CommandResult execute() throws CommandException {
        String targetEmail = getTargetEmail();
        String commandResultMessage = "";

        boolean emailIsValid = isEmailValid(targetEmail);
        if (emailIsValid) {
            putIntoClipboard(targetEmail);
            commandResultMessage = String.format(MESSAGE_COPY_PERSON_SUCCESS, targetPerson.getName());
        } else {
            commandResultMessage = String.format(MESSAGE_COPY_PERSON_EMPTY, targetPerson.getName());
        }

        return new CommandResult(commandResultMessage);
    }

    /**
     * Gets the target person's email address.
     * @return     the email address of the person at the list {@code targetIndex}
     * @exception  CommandException if the {@code targetIndex}
     *             argument is greater than or equal to the {@code lastShownList} size.
     */
    public String getTargetEmail() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        boolean indexIsOutOfBounds = targetIndex.getZeroBased() >= lastShownList.size();
        if (indexIsOutOfBounds) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        targetPerson = lastShownList.get(targetIndex.getZeroBased());
        return targetPerson.getEmail().toString();
    }

    public boolean isEmailValid(String email) {
        return !"null@null.com".equalsIgnoreCase(email) && !"".equals(email);
    }

    /**
     * Puts target person's email address into the system clipboard.
     */
    private void putIntoClipboard(String resultantEmailAddress) {
        Clipboard systemClipboard = Clipboard.getSystemClipboard();
        ClipboardContent systemClipboardContent = new ClipboardContent();

        systemClipboardContent.putString(resultantEmailAddress);
        systemClipboard.setContent(systemClipboardContent);
    }
    //@@author

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CopyCommand // instanceof handles nulls
                && this.targetIndex.equals(((CopyCommand) other).targetIndex)); // state check
    }
}
