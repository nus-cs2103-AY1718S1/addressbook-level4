package seedu.address.logic.commands;

import static seedu.address.commons.util.StringUtil.containsWordIgnoreCase;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;

//@@author dennaloh

/**
 * Emails a contact from the address book
 *
 */
public class EmailCommand extends Command {
    public static final String COMMAND_WORD = "email";
    public static final String COMMAND_ALIAS = "em";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Emails person whose name contains any of "
            + "the specified keywords (not case-sensitive). Needs Outlook or Apple Mail app.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alex yeoh ";

    public static final String MESSAGE_DUPLICATE_PERSON = "There are multiple contacts containing this name. "
            + "Type again with full name.";
    public static final String MESSAGE_SUCCESS = "Opened email!";

    private String email;
    private String name = "default name";
    private ObservableList<ReadOnlyPerson> persons;

    public EmailCommand (String name) {
        this.name = name;
    }

    @Override
    public CommandResult execute() throws CommandException {
        Desktop desktop;
        persons = model.getAddressBook().getPersonList();

        for (ReadOnlyPerson person : persons) {
            if (containsWordIgnoreCase(person.getName().getValue(), name)) {
                email = person.getEmail().getValue();
            }
        }
        if (model.haveDuplicate(name, persons)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }
        try {
            if (Desktop.isDesktopSupported()
                    && (desktop = Desktop.getDesktop()).isSupported(Desktop.Action.MAIL)) {
                URI mailto = new URI("mailto:" + email);
                desktop.mail(mailto);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
