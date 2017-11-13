package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Launches the mail composing window of the default mail client.
 */
public class EmailCommand extends Command {

    public static final String COMMAND_WORD = "email";
    public static final Set<String> COMMAND_WORD_ABBREVIATIONS =
            new HashSet<>(Arrays.asList(COMMAND_WORD, "mail", "mailto", "m"));
    public static final String COMMAND_HOTKEY = "Ctrl+M";
    public static final String FORMAT = "email INDEX [s/SUBJECT]";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Launch the mail composing window of the default mail client.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_SUBJECT + "SUBJECT]\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_SUBJECT + "Hello";
    public static final String MESSAGE_SUCCESS = "Opening email composer";
    public static final String MESSAGE_NOT_SUPPORTED = "Function not supported";
    private final Index targetIndex;
    private final String subject;
    private URI mailtoUri = null;

    public EmailCommand(Index targetIndex, String subject) {
        this.targetIndex = targetIndex;
        this.subject = subject;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getLatestPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson recipient  = lastShownList.get(targetIndex.getZeroBased());

        String recipientEmailAddress = recipient.getEmail().value;

        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.MAIL)) {
            Desktop desktop = Desktop.getDesktop();
            try {
                mailtoUri = new URI("mailto:" + recipientEmailAddress + "?subject=" + subject);
                desktop.mail(mailtoUri);
                selectRecipient(targetIndex);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new CommandResult(String.format(MESSAGE_SUCCESS));
        } else {
            throw new CommandException(String.format(MESSAGE_NOT_SUPPORTED));
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailCommand // instanceof handles nulls
                && this.targetIndex.equals(((EmailCommand) other).targetIndex))
                && this.subject.equals(((EmailCommand) other).subject); // state check
    }

    /**
     * Selects the {@code recipient} for the contact details to be displayed
     */
    private void selectRecipient(Index index) {
        EventsCenter.getInstance().post(new JumpToListRequestEvent(index));
    }
}
