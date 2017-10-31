package seedu.address.logic.commands;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import seedu.address.logic.commands.exceptions.CommandException;

/**
 * export the person details in txt
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_SUCCESS = "new file created";
    public static final String MESSAGE_USAGE = COMMAND_WORD + "export the person details in txt file";
    private String filepath;
    public ExportCommand (String f) {
        this.filepath = f.trim();
    }
    @Override
    public CommandResult execute() throws CommandException {
        try {
            BufferedWriter output = null;
            File file = new File(filepath);
            output = new BufferedWriter(new FileWriter(file));

            for (int i = 0; i < model.getAddressBook().getPersonList().size(); i++) {
                output.write("name:" + model.getAddressBook().getPersonList().get(i).getName().fullName);
                output.newLine();
                output.write("phone:" + model.getAddressBook().getPersonList().get(i).getPhone().toString());
                output.newLine();
                output.write("address:" + model.getAddressBook().getPersonList().get(i).getAddress().toString());
                output.newLine();
                output.write("email:" + model.getAddressBook().getPersonList().get(i).getEmail().toString());
                output.newLine();
                output.write("tags:" + model.getAddressBook().getPersonList().get(i).getTags().toString());
                output.write("==============================");
                output.newLine();

            }
            output.close();
            return new CommandResult(MESSAGE_SUCCESS);

        } catch (Exception e) {
            throw new CommandException("can't create a file in the path"+ filepath.toString());
        }
    }
}
