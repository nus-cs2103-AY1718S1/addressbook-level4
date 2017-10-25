package seedu.address.logic.commands;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import seedu.address.model.person.ReadOnlyPerson;

/**
 * export contacts to external source (in .vcf format)
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";
    public static final String COMMAND_ALIAS = "ex";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Export contact details to external source (in .vcf format).\n"
            + "Parameters: FILENAME \n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Contacts successfully exported as output.vcf !!";



    @Override
    public CommandResult execute() {

        try {
            writeToFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }

    /**
     * This method handles the writing of contacts to a file
     */
    private void writeToFile() throws IOException {

        final String filename = "output.vcf";

        FileWriter fw = new FileWriter(filename);
        BufferedWriter bw = new BufferedWriter(fw);

        for (ReadOnlyPerson p : model.getAddressBook().getPersonList()) {

            String header = "BEGIN:VCARD\n";
            String version = "VERSION:3.0\n";
            String fullName = "FN:" + p.getName().toString() + "\n";
            String name = "N:;" + p.getName().toString() + ";;;\n";
            String email = "EMAIL;TYPE=INTERNET;TYPE=HOME:" + p.getEmail().toString() + "\n";
            String tel = "TEL;TYPE=CELL:" + p.getPhone().toString() + "\n";
            String address = "ADR:;;" + p.getAddress().toString() + ";;;;\n";
            String footer = "END:VCARD\n";

            bw.write(header);
            bw.write(version);
            bw.write(fullName);
            bw.write(name);
            bw.write(email);
            bw.write(tel);
            bw.write(address);
            bw.write(footer);
        }

        if (bw != null) {
            bw.close();
        }

        if (fw != null) {
            fw.close();
        }

    }
}
