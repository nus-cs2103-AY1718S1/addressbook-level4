package seedu.address.logic.commands;

import java.io.File;
import java.io.IOException;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.storage.VcfExport;
import seedu.address.storage.XmlFileStorage;
import seedu.address.storage.XmlSerializableAddressBook;

//@@author freesoup
/**
 * Exports address book app contacts into an  contacts.vcf file.
 */
public class ExportCommand extends Command {
    public static final String COMMAND_WORD = "export";
    public static final String COMMAND_USAGE = COMMAND_WORD;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports contacts into a .vcf file.";
    public static final String MESSAGE_WRONG_FILE_TYPE = "Export only exports .vcf and .xml file.";
    public static final String MESSAGE_FILE_NOT_FOUND = "File was not found in specified directory.";

    public static final String MESSAGE_SUCCESS = "Successfully exported contacts.";
    public final String filePath;

    public ExportCommand(String path) {
        this.filePath = path;
    }

    @Override
    public CommandResult execute() throws CommandException {
        File export = new File(filePath);
        ReadOnlyAddressBook addressBook = model.getAddressBook();
        if (export.getName().endsWith(".xml")) {
            XmlSerializableAddressBook xmlAddressBook = new XmlSerializableAddressBook(addressBook);
            try {
                export.createNewFile();
                XmlFileStorage.saveDataToFile(export, xmlAddressBook);
            } catch (IOException ioe) {
                throw new CommandException(MESSAGE_FILE_NOT_FOUND);
            }
        } else if (export.getName().endsWith(".vcf")) {
            try {
                VcfExport.saveDataToFile(export, addressBook.getPersonList());
            } catch (IOException ioe) {
                throw new CommandException(MESSAGE_FILE_NOT_FOUND);
            }
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
