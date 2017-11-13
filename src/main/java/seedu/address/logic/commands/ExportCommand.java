package seedu.address.logic.commands;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
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
    public static final String MESSAGE_EMPTY_BOOK = "No contacts found in Rubrika to export.";

    public static final String MESSAGE_SUCCESS = "Successfully exported contacts.";
    public static final String XML_EXTENSION = ".xml";
    public static final String VCF_EXTENSION = ".vcf";

    public final String filePath;

    private final Logger logger = LogsCenter.getLogger(ExportCommand.class);

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
            if (export.getName().endsWith(XML_EXTENSION)) {
                logger.info("Attempting to write to data file: " + export.getPath());
                XmlSerializableAddressBook xmlAddressBook = new XmlSerializableAddressBook(addressBook);
                export.createNewFile();
                XmlFileStorage.saveDataToFile(export, xmlAddressBook);
            } else if (export.getName().endsWith(VCF_EXTENSION)) {
                logger.info("Attempting to write to data file: " + export.getPath());
                VcfExport.saveDataToFile(export, addressBook.getPersonList());
            }
        } catch (IOException ioe) {
            assert false : "The file should have been created and writable";
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExportCommand // instanceof handles nulls
                && this.filePath.equals(((ExportCommand) other).filePath)); // state check
    }
}
