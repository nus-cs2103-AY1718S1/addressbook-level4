package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.email.Email;


//@@author jin-ting

/**
 * Opening email application to send email to contatcs.
 */
public class EmailCommand extends Command {

    public static final String COMMAND_WORD = "email";
    public static final String COMMAND_ALIAS = "m";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " (alias: " + COMMAND_ALIAS + ")"
            + ": Opening up email platform to send email to person(s)\n"
            + "Parameters: INDEX\n"
            + MESSAGE_GET_MORE_HELP;

    public static final String MESSAGE_DISPLAY_EMAIL_SUCCESS = "Sending email to %1$s";

    private Set<String> recipientSet = new HashSet<>();

    private final Set<Index> targetIndices;

    public EmailCommand(Set<Index> targetIndices) {
        requireNonNull(targetIndices);
        this.targetIndices = targetIndices;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        for (Index targetIndex : targetIndices) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            ReadOnlyPerson targetRecipient = lastShownList.get(targetIndex.getZeroBased());
            for (Email email : targetRecipient.getEmails()) {
                recipientSet.add(email.toString());
            }
        }

        String recipientList = String.join(",", recipientSet);
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            if (desktop.isSupported(Desktop.Action.MAIL)) {
                try {
                    URI mailto = new URI("mailto:" + recipientList + "?subject=Hello%20World");
                    desktop.mail(mailto);
                } catch (URISyntaxException | IOException e) {
                    e.printStackTrace();

                }
            }

        }

        return new CommandResult(String.format(MESSAGE_DISPLAY_EMAIL_SUCCESS, recipientList));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailCommand); // instanceof handles nulls
    }

}
