package seedu.address.logic.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ImportFileChooseEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.storage.FileWrapper;
import seedu.address.storage.VcfImport;
import seedu.address.storage.XmlFileStorage;

//@@author freesoup
/**
 * Retrieves the location of the import file and passes the FileInputStream into
 * a new ImportCommand Object.
 */
public class ImportCommandParser implements Parser<ImportCommand> {

    private final Logger logger = LogsCenter.getLogger(ImportCommandParser.class);

    @Override
    public ImportCommand parse(String userInput) throws ParseException {
        File file;

        if (userInput.trim().isEmpty()) {
            file = getFileFromImportWindow();
        } else {
            file = new File(userInput.trim());
        }

        if (file.getName().endsWith(ImportCommand.XML_EXTENSION)) {
            return importByXml(file);
        } else if (file.getName().endsWith(ImportCommand.VCF_EXTENSION)) {
            return importByVcf(file);
        } else {
            throw new ParseException(ImportCommand.MESSAGE_WRONG_FORMAT);
        }
    }

    /**
     * Creates an ImportCommand with the list of contacts given in the vcf file.
     * @param file of .vcf file format containing list of contacts.
     * @return ImportCommand that contains the list of contact given in the vcf file
     * @throws ParseException if filepath given is not found or if file is corrupted.
     */
    private ImportCommand importByVcf(File file) throws ParseException {
        try {

            logger.info("Attempting to read data from file: " + file.getPath());
            List<ReadOnlyPerson> importList = VcfImport.getPersonList(file);

            return new ImportCommand(importList);
        } catch (IllegalValueException ive) {
            throw new ParseException(ImportCommand.MESSAGE_FILE_CORRUPT);
        } catch (IOException ioe) {
            throw new ParseException(ImportCommand.MESSAGE_FILE_NOT_FOUND);
        } catch (NullPointerException npe) {
            throw new ParseException(ImportCommand.MESSAGE_FILE_INVALID);
        }
    }

    /**
     * Creates an ImportCommand with the list of contacts given in the xml file.
     * @param file of .xml file format containing list of contacts.
     * @return ImportCommand that contains the list of contact given in the xml file
     * @throws ParseException if filepath given is not found or if file is corrupted.
     */
    private ImportCommand importByXml(File file) throws ParseException {
        try {

            logger.info("Attempting to read data from file: " + file.getPath());
            ReadOnlyAddressBook importingBook = XmlFileStorage.loadDataFromSaveFile(file);
            List<ReadOnlyPerson> importList = importingBook.getPersonList();

            return new ImportCommand(importList);
        } catch (DataConversionException dce) {
            throw new ParseException(ImportCommand.MESSAGE_FILE_CORRUPT);
        } catch (FileNotFoundException fnfe) {
            throw new ParseException(ImportCommand.MESSAGE_FILE_NOT_FOUND);
        }
    }

    /**
     * Raises an event to open a File Explorer and returns the file to be imported
     * @return File containing contacts to be created
     * @throws ParseException if File Explorer was closed before a file has been selected
     */
    private File getFileFromImportWindow() throws ParseException {
        File file;
        FileWrapper fw = new FileWrapper();

        EventsCenter.getInstance().post(new ImportFileChooseEvent(fw));
        file = fw.getFile();
        if (file == null) {
            throw new ParseException(ImportCommand.MESSAGE_IMPORT_CANCELLED);
        }
        return file;
    }
}
