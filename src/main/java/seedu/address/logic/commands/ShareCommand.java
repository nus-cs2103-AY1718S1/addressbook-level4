package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEB_LINK;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.CopyToClipboardRequestEvent;

import seedu.address.model.person.Email;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UserPerson;
import seedu.address.model.person.weblink.WebLink;

//@@author bladerail
/**
 * Displays an add command for the user's contact
 */
public class ShareCommand extends Command {
    public static final String COMMAND_WORD = "share";
    public static final String COMMAND_ALIAS = "sh";

    public static final String MESSAGE_SUCCESS = "Add Command generated. Copied to clipboard! \n%1$s";

    @Override
    public CommandResult execute() {
        UserPerson userPerson = model.getUserPerson();
        String result = addCommandBuilder(userPerson);

        EventsCenter.getInstance().post(new CopyToClipboardRequestEvent(result));

        return new CommandResult(String.format(MESSAGE_SUCCESS, result));
    }

    /**
     * Builds an addCommand for the model's userPerson
     * @return String
     */
    public static String addCommandBuilder(ReadOnlyPerson src) {
        final StringBuilder builder = new StringBuilder();
        builder.append(AddCommand.COMMAND_WORD)
                .append(" ")
                .append(PREFIX_NAME)
                .append(src.getName())
                .append(" ")
                .append(PREFIX_PHONE)
                .append(src.getPhone())
                .append(" ")
                .append(PREFIX_ADDRESS)
                .append(src.getAddress());
        for (Email email : src.getEmail()) {
            builder.append(" ")
                    .append(PREFIX_EMAIL)
                    .append(email);
        }

        for (WebLink webLink : src.getWebLinks()) {
            builder.append(" ")
                    .append(PREFIX_WEB_LINK)
                    .append(webLink.toStringWebLink());
        }

        return builder.toString();
    }
}

