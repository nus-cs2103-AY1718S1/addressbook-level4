package seedu.room.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.room.MainApp;
import seedu.room.commons.exceptions.DataConversionException;
import seedu.room.logic.commands.exceptions.CommandException;
import seedu.room.logic.commands.exceptions.NoUniqueImport;
import seedu.room.model.ReadOnlyResidentBook;
import seedu.room.model.person.ReadOnlyPerson;
import seedu.room.model.person.exceptions.DuplicatePersonException;
import seedu.room.storage.XmlFileStorage;

//@@author blackroxs

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

    public static final String NAME_SEPERATOR = ", ";
    public static final String MESSAGE_FILE_NOT_UNIQUE = "No unique residents found.";
    private String filePath;

    public ImportCommand(String filePath) {
        this.filePath = filePath;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            ReadOnlyResidentBook newList = XmlFileStorage.loadDataFromSaveFile(new File(filePath));

            ArrayList<String> namesAdded = new ArrayList<>();
            String namesFeedback = "";

            addUniquePerson(newList, namesAdded);
            namesFeedback = getNamesFeedback(namesAdded, namesFeedback);

            return new CommandResult(String.format(MESSAGE_SUCCESS + " Added: " + namesFeedback));
        } catch (NullPointerException e) {
            throw new CommandException(MESSAGE_ERROR + " NULL ");
        } catch (DataConversionException e) {
            throw new CommandException(MESSAGE_ERROR + " DATA ");
        } catch (IOException e) {
            throw new CommandException(MESSAGE_ERROR + " IO ");
        } catch (NoUniqueImport noUniqueImport) {
            throw new CommandException(MESSAGE_FILE_NOT_UNIQUE);
        }
    }

    private void addUniquePerson(ReadOnlyResidentBook newList, ArrayList<String> namesAdded) {
        ObservableList<ReadOnlyPerson> personList = newList.getPersonList();

        for (ReadOnlyPerson p : personList) {
            try {
                model.addPerson(p);
            } catch (DuplicatePersonException e) {
                continue;
            }
            namesAdded.add(p.getName().fullName);
        }
    }

    private String getNamesFeedback(ArrayList<String> namesAdded, String namesFeedback) throws NoUniqueImport {

        if(namesAdded.size() == 0 ){
            throw new NoUniqueImport(MESSAGE_FILE_NOT_UNIQUE);
        }

        for (int i = 0; i < namesAdded.size(); i++) {
            namesFeedback += namesAdded.get(i);

            if (i + 1 != namesAdded.size()) {
                namesFeedback += NAME_SEPERATOR;
            }
        }
        return namesFeedback;
    }
}
