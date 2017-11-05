package seedu.address.logic.commands;

import static seedu.address.commons.util.StringUtil.containsWordIgnoreCase;

import com.sun.media.jfxmedia.logging.Logger;
import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;

public class EmailCommand extends Command {
    public static final String COMMAND_WORD = "email";
    public static final String COMMAND_ALIAS = "em";
    private String EMAIL_ADDRESS ="default@example.com";
    private String name = "defaultname";
    private ObservableList<ReadOnlyPerson> persons;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Emails person whose name contains any of "
            + "the specified keywords (not case-sensitive).\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alex yeoh ";

    public EmailCommand(String name){
        this.name = name;
    }

    @Override
    public CommandResult execute() throws CommandException {
        Desktop desktop;
        persons = model.getAddressBook().getPersonList();

        Iterator<ReadOnlyPerson> iterator = persons.iterator();
        while(iterator.hasNext()){
            ReadOnlyPerson person = iterator.next();
            if(containsWordIgnoreCase(person.getName().getValue(), name)){
                EMAIL_ADDRESS = person.getEmail().getValue();
            }
        }
        try {

            if (Desktop.isDesktopSupported()
                    && (desktop = Desktop.getDesktop()).isSupported(Desktop.Action.MAIL)) {
                URI mailto = new URI("mailto:" + EMAIL_ADDRESS);
                desktop.mail(mailto);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return new CommandResult(EMAIL_ADDRESS);
    }
}
