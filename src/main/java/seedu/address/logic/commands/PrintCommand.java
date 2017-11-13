//@@author arnollim
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_FILEPATH;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import seedu.address.model.insurance.ReadOnlyInsurance;
import seedu.address.model.insurance.UniqueLifeInsuranceList;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Prints the list of contacts, along with any associated
 * insurance policies where the contact is involved in,
 * into a printable, readable .txt file.
 */
public class PrintCommand extends Command {

    public static final String[] COMMAND_WORDS = {"print"};
    public static final String COMMAND_WORD = "print";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Saves the addressbook into a .txt file named by you for your viewing.\n"
            + "Example: " + COMMAND_WORD + " filename\n"
            + "file can then be found in the in data/ folder as data/filename.txt\n"
            + MESSAGE_INVALID_FILEPATH;
    public static final String MESSAGE_SUCCESS = "Address Book has been saved!\n"
            + "Find your Address Book in the %1$s.txt file you created "
            + "in the same directory as the application file path!";

    private final String fileName;

    public PrintCommand(String fileName) {
        requireNonNull(fileName);

        this.fileName = fileName;
    }


    @Override
    public CommandResult execute() {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        List<String> lines = new ArrayList<>();
        String timeStamp = new SimpleDateFormat("dd/MM/YYYY" + " " + "HH:mm:ss").format(new Date());

        //First line in the .txt file is the time and date printed, so that the user will know the recency
        //of the printed address book
        lines.add("LISA was last updated on: " + timeStamp + "\n\n");
        //Next, feedback the total number of contacts at said time and date
        lines.add("There are " + lastShownList.size() + " contacts in LISA\n\n");

        int personIndex = 1;

        //iterating through each person in LISA
        for (ReadOnlyPerson person: lastShownList) {
            String entry = personIndex + ". " + person.getAsParagraph();
            lines.add(entry);
            lines.add("\n" + person.getName().fullName
                    + " is a personnel involved in the following insurance policies:\n");

            UniqueLifeInsuranceList insurances = person.getLifeInsurances();
            int insuranceIndex = 1;

            //within each person, iterate through all the insurance policies associated
            //with this person
            for (ReadOnlyInsurance insurance: insurances) {
                String insuranceHeader = "Insurance Policy " + insuranceIndex
                        + ": " + insurance.getInsuranceName() + " ======";
                lines.add(insuranceHeader);
                String owner = insurance.getOwner().getName();
                String insured = insurance.getInsured().getName();
                String beneficiary = insurance.getBeneficiary().getName();
                String premium = insurance.getPremium().toString();
                String signingDate = insurance.getSigningDateString();
                String expiryDate = insurance.getExpiryDateString();
                lines.add("Owner: " + owner + "\n"
                        + "Insured: " + insured + "\n"
                        + "Beneficiary: " + beneficiary + "\n"
                        + "Premium: " + premium + "\n"
                        + "Signing Date: " + signingDate + "\n"
                        + "Expiry Date: " + expiryDate
                );

                //insuranceEnd is just a printed line "=====" which ties with the length
                //of insuranceHeader to make the txt file more organised.
                String insuranceEnd = "";
                int headerLength = insuranceHeader.length();
                for (int i = 1; i <= headerLength; i++) {
                    insuranceEnd = insuranceEnd + "=";
                }
                lines.add(insuranceEnd);

                lines.add("\n");
                insuranceIndex++;
            }
            lines.add("--------End of " + person.getName().fullName + "'s profile");
            lines.add("\n");
            personIndex++;
        }

        Path file = Paths.get(fileName + ".txt");
        try {
            Files.write(file, lines, Charset.forName("UTF-8"));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        String feedbackToUser = String.format(MESSAGE_SUCCESS, this.fileName);
        return new CommandResult(feedbackToUser);
    }

}
