package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;



/**
 * export the person details in txt
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_SUCCESS = "new file created";
    public static final String MESSAGE_USAGE = COMMAND_WORD + "export the person details in txt file";
    private String filepath;
    public ExportCommand (String f) {
        this.filepath = f;
    }
    @Override
    public CommandResult execute() throws CommandException{
        try{
            Writer output = null;
            File file = new File(filepath);
            output = new BufferedWriter(new FileWriter(file));

            for (int i = 0; i < model.getAddressBook().getPersonList().size(); i++) {
                output.write();
                output.newline();
            }
            output.close();
            return new CommandResult(MESSAGE_SUCCESS);

        } catch (Exception e){
            throw new CommandException("can't create a file in the path");
        }
    }
}
