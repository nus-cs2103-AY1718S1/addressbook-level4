package seedu.address.logic.commands;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Launches the mail composing window of the default mail client.
 */
public class EmailCommand extends Command {

    public static final String COMMAND_WORD = "mail";
    public static final Set<String> COMMAND_WORD_ABBREVIATIONS =
            new HashSet<>(Arrays.asList(COMMAND_WORD, "email", "mailto", "m"));
    public static final String COMMAND_HOTKEY = "Ctrl+M";
    public static final String FORMAT = "mail INDEX";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Launch the mail composing window of the default mail client.";
    public static final String MESSAGE_SUCCESS = "Opening email composer";
    public static final String MESSAGE_NOT_SUPPORTED = "Function not supported";
    private final Index targetIndex;
    private URI mailToUri = null;

    public EmailCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
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
                mailToUri = new URI("mailto:" + recipientEmailAddress);
                desktop.mail(mailToUri);
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
}
