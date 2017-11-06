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

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports contacts into a vCard or XML file. "
            + "Parameters: FileName.xml Or FileName.vcf\n"
            + "Example: export sample.xml OR export sample.vcf";
    public static final String MESSAGE_WRONG_FILE_TYPE = "Export only exports .vcf and .xml file.";
    public static final String MESSAGE_FILE_NOT_FOUND = "File was not found in specified directory.";
    public static final String MESSAGE_EMPTY_BOOK = "No contacts found in Rubrika to export.";

    public static final String MESSAGE_SUCCESS = "Successfully exported contacts.";
    public final String filePath;

    public ExportCommand(String path) {
        this.filePath = path;
    }

    @Override
    public CommandResult execute() throws CommandException {
        File export = new File(filePath);
        ReadOnlyAddressBook addressBook = model.getAddressBook();

        if (addressBook.getPersonList().isEmpty()) {
            throw new CommandException(MESSAGE_EMPTY_BOOK);
        }
        try {
            if (export.getName().endsWith(".xml")) {
                XmlSerializableAddressBook xmlAddressBook = new XmlSerializableAddressBook(addressBook);
                export.createNewFile();
                XmlFileStorage.saveDataToFile(export, xmlAddressBook);
            } else if (export.getName().endsWith(".vcf")) {
                VcfExport.saveDataToFile(export, addressBook.getPersonList());
            }
        } catch (IOException ioe) {
            throw new CommandException(MESSAGE_FILE_NOT_FOUND);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
