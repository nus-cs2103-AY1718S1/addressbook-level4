package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.model.person.Address;
import seedu.address.model.person.Name;
import seedu.address.model.person.ReadOnlyPerson;

public class PrintCommand extends Command {

    public static final String[] COMMAND_WORDS = {"print"};
    public static final String COMMAND_WORD = "print";

    public static final String MESSAGE_USAGE = "";

    private final String fileName = "lol.txt";

    /*
    public PrintCommand(String filename) {
        requireNonNull(filename);

        this.fileName = filename;
    }
    */

    @Override
    public CommandResult execute() {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        List<String> lines = new ArrayList<>();
        String timeStamp = new SimpleDateFormat("dd/MM/YYYY" + " "+ "HH:mm:ss").format(new Date());
        lines.add("Addressbook was last updated on: " + timeStamp +"\n");

        int personIndex = 1;
        for (ReadOnlyPerson person: lastShownList) {
            String entry = personIndex + ". " + person.getAsText();
            lines.add(entry);
            lines.add("Insurance Policy: =========");
            String owner = "";//person.getLifeInsurance().getOwner().getName().fullName;
            String insured = "";//person.getLifeInsurance().getInsured().getName().fullName;
            String beneficiary = ""; //person.getLifeInsurance().getBeneficiary().getName().fullName;
            String premium = ""; //person.getLifeInsurance().getPremium().toString();
            String signingdate = ""; //person.getLifeInsurance().getSigningDate();
            String expirydate = "";//person.getLifeInsurance().getExpiryDate();
            lines.add("Owner: " + owner +"\n"
                    + "Insured: " + insured + "\n"
                    + "Beneficiary: " + beneficiary + "\n"
                    + "Premium: " + premium + "\n"
                    + "Signing Date: " + signingdate + "\n"
                    + "Expiry Date: " + expirydate + "\n"
            );
            lines.add("============");
            lines.add("\n");
            personIndex++;
        }

        //Path file = Paths.get("doc/books/"+ fileName +".txt");
        Path file = Paths.get("docs/books/lol.txt");
        try {
            Files.write(file, lines, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //System.out.println("test");
        return new CommandResult("lol");
    }

}
