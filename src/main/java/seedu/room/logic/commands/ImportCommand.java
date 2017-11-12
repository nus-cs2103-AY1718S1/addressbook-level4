package seedu.room.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.ObservableList;
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

            addUniquePerson(newList, namesAdded);
            String namesFeedback = getNamesFeedback(namesAdded);

            return new CommandResult(String.format(MESSAGE_SUCCESS + " Added: " + namesFeedback));
        } catch (NullPointerException e) {
            throw new CommandException(MESSAGE_ERROR);
        } catch (DataConversionException e) {
            throw new CommandException(MESSAGE_ERROR);
        } catch (IOException e) {
            throw new CommandException(MESSAGE_ERROR);
        } catch (NoUniqueImport noUniqueImport) {
            throw new CommandException(MESSAGE_FILE_NOT_UNIQUE);
        }
    }

    /**
     * Add only persons not found in current ResidentBook
     *
     * @param newList    import file viewed as ReadOnlyResidentBook
     * @param namesAdded list of names that are added
     */
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

    /**
     * Form the string to return as feedback to user
     * @param namesAdded list of names that are added
     * @return concatenated string of all names
     * @throws NoUniqueImport no resident has been added to list (invalid import)
     */
    private String getNamesFeedback(ArrayList<String> namesAdded) throws NoUniqueImport {
        String namesFeedback = "";
        if (namesAdded.size() == 0) {
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
