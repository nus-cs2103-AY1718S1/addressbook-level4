package seedu.address.logic.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Retrieves the location of the import file and passes the FileInputStream into
 * a new ImportCommand Object.
 */
public class ImportCommandParser implements Parser<ImportCommand> {

    @Override
    public ImportCommand parse(String userInput) throws ParseException {
        JButton open = new JButton();
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new java.io.File("C:/"));
        fc.setDialogTitle("Select your vCard file");
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (fc.showOpenDialog(open) == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            try {
                FileInputStream fis = new FileInputStream(file);
                return new ImportCommand(fis);
            } catch (IOException ioe) {
                throw new ParseException(ImportCommand.MESSAGE_FILEERROR);
            }
        } else {
            throw new ParseException(ImportCommand.MESSAGE_NOFILECHOSEN);
        }

    }
}
