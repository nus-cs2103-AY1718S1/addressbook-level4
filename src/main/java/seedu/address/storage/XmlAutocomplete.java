package seedu.address.storage;

import static seedu.address.logic.parser.ParserUtil.EMPTY_STRING;
import static seedu.address.ui.CommandBox.AUTOCOMPLETE_FILE_NAME;
import static seedu.address.ui.CommandBox.ERROR_MESSAGE_CREATE_FILE_FAILED;
import static seedu.address.ui.CommandBox.STORAGE_FILE_NAME;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import seedu.address.logic.commands.exceptions.CommandException;
//@@author justintkj
/**
 * Stores Autocomplete data in an XML file
 */
public class XmlAutocomplete {
    private static String[] possibleSuggestion = {"add", "birthday", "clear", "list", "help", "removetag", "image",
        "edit", "find", "delete", "select", "favourite", "history", "undo", "redo", "email", "sort", "sort name",
        "map", "sort number", "sort email", "sort address", "sort remark", "sort birthday", "sort favourite",
        "exit", "fuzzyfind", "sort numtimessearched"};
    private static ArrayList<String> mainPossibleSuggestion = new ArrayList<String>(Arrays.asList(possibleSuggestion));

    /**
     * Creates a new storage file named Autocomplete.xml
     *
     * @param ex exception to be handled
     */
    public static ArrayList<String> createNewStorageFile(Exception ex) throws IOException {
        File file = new File(STORAGE_FILE_NAME);
        file.createNewFile();
        addNewDataInStorage(EMPTY_STRING);
        return  mainPossibleSuggestion;
    }

    /**
     * Overloads the createNewStorageFile method
     *
     * @throws CommandException unable to create new file
     */
    public static void createNewStorageFile() throws CommandException {
        try {
            File file = new File(STORAGE_FILE_NAME);
            file.createNewFile();
            addNewDataInStorage(EMPTY_STRING);
        } catch (IOException ioe) {
            throw new CommandException(ERROR_MESSAGE_CREATE_FILE_FAILED);
        }
    }
    /**
     * Updates Storagefile with new content, used as refresh if no content
     *
     * @param commandWord input to be added to storage
     * @throws FileNotFoundException file cannot be found
     */
    public static void addNewDataInStorage(String commandWord) throws FileNotFoundException {
        if (!commandWord.equals(EMPTY_STRING)) {
            mainPossibleSuggestion.add(commandWord.trim());
        }
        XMLEncoder e = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(AUTOCOMPLETE_FILE_NAME)));
        e.writeObject(mainPossibleSuggestion);
        e.close();
    }
    /**
     * Reads the storage file to update mainpossiblesuggestion
     *
     * @throws FileNotFoundException storage file cannot be found
     */
    public static ArrayList<String> updateAutocompleteWithStorageFile() throws FileNotFoundException {
        XMLDecoder e = readStorageFile();
        mainPossibleSuggestion = ((ArrayList<String>) e.readObject());
        e.close();
        return mainPossibleSuggestion;
    }

    /**
     * Reads the storage file
     *
     * @return  an input stream for reading archive
     * @throws FileNotFoundException storagefile cannot be found
     */
    public static XMLDecoder readStorageFile() throws FileNotFoundException {
        return new XMLDecoder(new FileInputStream(STORAGE_FILE_NAME));
    }

    /**
     * Adds in a valid command string into autocomplete.xml storage
     *
     * @param commandWord
     * @throws CommandException if autocomplete.xml cannot be made.
     */
    public static ArrayList<String> setAddSuggestion(String commandWord) throws CommandException {
        if (mainPossibleSuggestion.contains(commandWord)) {
            return mainPossibleSuggestion;
        }
        try {
            addNewDataInStorage(commandWord);
        } catch (Exception ex) {
            createNewStorageFile();
        }
        return mainPossibleSuggestion;
    }
}
