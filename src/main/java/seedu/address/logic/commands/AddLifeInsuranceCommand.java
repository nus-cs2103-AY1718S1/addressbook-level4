package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BENEFICIARY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTRACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPIRY_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INSURED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OWNER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PREMIUM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SIGNING_DATE;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.insurance.LifeInsurance;
import seedu.address.model.insurance.ReadOnlyInsurance;
import seedu.address.model.person.ReadOnlyPerson;

//@@author OscarWang114
/**
 * Creates an insurance in Lisa
 */
public class AddLifeInsuranceCommand extends UndoableCommand {
    public static final String[] COMMAND_WORDS = {"addli", "ali", "+"};
    public static final String COMMAND_WORD = "addli";

    public static final String MESSAGE_USAGE = concatenateCommandWords(COMMAND_WORDS)
            + ": Adds an insurance to Lisa. "
            + "Parameters: "
            + PREFIX_OWNER + "OWNER "
            + PREFIX_INSURED + "INSURED "
            + PREFIX_BENEFICIARY + "BENEFICIARY "
            + PREFIX_CONTRACT + "CONTRACT"
            + PREFIX_PREMIUM + "PREMIUM "
            + PREFIX_SIGNING_DATE + "SIGNING_DATE "
            + PREFIX_EXPIRY_DATE + "EXPIRY_DATE\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_OWNER + "Alex Yeoh"
            + PREFIX_INSURED + "John Doe "
            + PREFIX_BENEFICIARY + "Mary Jane "
            + PREFIX_CONTRACT + "normal_plan.pdf"
            + PREFIX_PREMIUM + "500 "
            + PREFIX_SIGNING_DATE + "01 11 2017 "
            + PREFIX_EXPIRY_DATE + "01 11 2018 ";

    public static final String MESSAGE_SUCCESS = "New insurance added: %1$s";

    private final LifeInsurance lifeInsurance;

    /**
     * Creates an AddLifeInsuranceCommand to add the specified Insurance
     */
    public AddLifeInsuranceCommand(ReadOnlyInsurance toAdd) {
        lifeInsurance = new LifeInsurance(toAdd);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        List<ReadOnlyPerson> personList = model.getFilteredPersonList();
        isAnyPersonInList(personList, lifeInsurance);
        model.addInsurance(lifeInsurance);

        return new CommandResult(String.format(MESSAGE_SUCCESS, lifeInsurance));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddLifeInsuranceCommand); // instanceof handles nulls
                //&& toAdd.equals(((AddLifeInsuranceCommand) other).toAdd));
        //TODO: need to compare every nonstatic class member.
    }

    //@@author arnollim
    @Override
    public String toString() {
        return COMMAND_WORD;
    }
    //@@author

    /**
     * Check if all the Person parameters required to create an insurance are inside the list
     */
    public void isAnyPersonInList(List<ReadOnlyPerson> list, LifeInsurance lifeInsurance) {
        String ownerName = lifeInsurance.getOwner().getName();
        String insuredName = lifeInsurance.getInsured().getName();
        String beneficiaryName = lifeInsurance.getBeneficiary().getName();
        for (ReadOnlyPerson person: list) {
            String lowerCaseName = person.getName().toString().toLowerCase();
            if (lowerCaseName.equals(ownerName)) {
                lifeInsurance.getOwner().setPerson(person);
            }
            if (lowerCaseName.equals(insuredName)) {
                lifeInsurance.getInsured().setPerson(person);
            }
            if (lowerCaseName.equals(beneficiaryName)) {
                lifeInsurance.getBeneficiary().setPerson(person);
            }
        }
    }
}
