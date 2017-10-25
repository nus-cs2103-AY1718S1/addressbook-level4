package seedu.room.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.room.MainApp;
import seedu.room.commons.exceptions.DataConversionException;
import seedu.room.logic.commands.exceptions.CommandException;
import seedu.room.model.ReadOnlyResidentBook;
import seedu.room.model.person.ReadOnlyPerson;
import seedu.room.model.person.exceptions.DuplicatePersonException;

/**
 * Import contacts from xml file.
 */
public class ImportCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "import";
    public static final String COMMAND_ALIAS = "i";
    public static final String MESSAGE_SUCCESS = "Import successful.";
    public static final String MESSAGE_ERROR = "Import error. Please check your file path or XML file.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds all persons in the XML file onto "
            + "the current resident book.\n"
            + "Parameters: FILE_PATH \n"
            + "Example: " + COMMAND_WORD + " friendsContacts.xml";

    private String filePath;

    public ImportCommand(String filePath) {
        this.filePath = filePath;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            Optional<ReadOnlyResidentBook> newContacts = MainApp.getBackup().readResidentBook(filePath);
            ReadOnlyResidentBook newList = newContacts.orElse(null);

            ArrayList<String> namesAdded = new ArrayList<>();
            String namesFeedback = "";
            if (newList != null) {
                ObservableList<ReadOnlyPerson> personList = newList.getPersonList();

                for (ReadOnlyPerson p : personList) {
                    try {
                        model.addPerson(p);
                    } catch (DuplicatePersonException e) {
                        continue;
                    }
                    namesAdded.add(p.getName().fullName);
                }

                for (int i = 0; i < namesAdded.size(); i++) {
                    namesFeedback += namesAdded.get(i);

                    if (i + 1 != namesAdded.size()) {
                        namesFeedback += ", ";
                    }
                }
                return new CommandResult(String.format(MESSAGE_SUCCESS, namesFeedback));
            }
            return new CommandResult(String.format(MESSAGE_ERROR));
        } catch (DataConversionException e) {
            throw new CommandException(MESSAGE_ERROR);
        } catch (IOException e) {
            throw new CommandException(MESSAGE_ERROR);
        }
    }
}
