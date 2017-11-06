package seedu.address.logic.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import seedu.address.commons.core.EventsCenter;
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

    @Override
    public ImportCommand parse(String userInput) throws ParseException {
        File file = null;
        if (userInput.trim().isEmpty()) {
            FileWrapper fw = new FileWrapper(file);
            EventsCenter.getInstance().post(new ImportFileChooseEvent(fw));
            file = fw.getFile();
            if (file == null) {
                throw new ParseException("Import cancelled");
            }
        } else {
            file = new File(userInput.trim());
        }
        if (file.getName().endsWith(".xml")) {
            try {
                ReadOnlyAddressBook importingBook = XmlFileStorage.loadDataFromSaveFile(file);
                List<ReadOnlyPerson> importList = importingBook.getPersonList();

                return new ImportCommand(importList);
            } catch (DataConversionException dce) {
                throw new ParseException(ImportCommand.MESSAGE_FILE_CORRUPT);
            } catch (FileNotFoundException fnfe) {
                throw new ParseException(ImportCommand.MESSAGE_FILE_NOT_FOUND);
            }

        } else if (file.getName().endsWith(".vcf")) {
            try {
                List<ReadOnlyPerson> importList = VcfImport.getPersonList(file);
                return new ImportCommand(importList);
            } catch (IllegalValueException ive) {
                throw new ParseException(ImportCommand.MESSAGE_FILE_CORRUPT);
            } catch (IOException ioe) {
                throw new ParseException(ImportCommand.MESSAGE_FILE_NOT_FOUND);
            }

        } else {
            throw new ParseException(ImportCommand.MESSAGE_WRONG_FORMAT);
        }
    }

}
