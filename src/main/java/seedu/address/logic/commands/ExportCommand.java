package seedu.address.logic.commands;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;

/**
 * Exports address book app contacts into an  contacts.vcf file.
 */
public class ExportCommand extends Command {
    public static final String COMMAND_WORD = "export";
    public static final String COMMAND_USAGE = COMMAND_WORD + " ";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports contacts into a .vcf file.";

    public static final String MESSAGE_SUCCESS = "Successfully exported contacts.";

    @Override
    public CommandResult execute() throws CommandException {
        File export = new File(System.getProperty("user.home"), "Desktop/export.vcf");
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(export));
            ObservableList<ReadOnlyPerson> list = model.getFilteredPersonList();

            for (int i = 0; i < list.size(); i++) {
                ReadOnlyPerson person = list.get(i);
                Set<Tag> tagList = person.getTags();
                bw.write("BEGIN:VCARD\n");
                bw.write("VERSON:2.1\n");
                bw.write("FN:" + person.getName() + "\n");
                bw.write("EMAIL:" + person.getEmail() + "\n");
                bw.write("TEL:" + person.getPhone() + "\n");
                bw.write("ADR:" + person.getAddress() + "\n");
                if (!person.getRemark().isEmpty()) {
                    bw.write("RM:" + person.getRemark() + "\n");
                }
                for (Tag tag : tagList) {
                    bw.write("TAG:" + tag.getTagName() + "\n");
                }
                bw.write("END:VCARD\n");
            }
        } catch (IOException ioe) {
            throw new CommandException("Problem writing to file");
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException ioe) {
                throw new CommandException("Problem closing file");
            }
        }


        return new CommandResult(MESSAGE_SUCCESS);
    }
}
