package seedu.address.testutil;

import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;

import seedu.address.model.insurance.ReadOnlyInsurance;
import seedu.address.model.person.Person;

//@@atuhor RSJunior37
/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalInsurances {
    public static final ReadOnlyInsurance COMMON_INSURANCE =
            new LifeInsuranceBuilder().withInsuranceName("Common Insurance")
            .withOwner(new Person(ALICE))
            .withBeneficiary(new Person(BENSON))
            .withInsured(new Person(CARL))
            .withPremium("123.45")
            .withContractFileName("common.pdf")
            .withSigningDate("01 Jan 2017")
            .withExpiryDate("31 Dec 2020").build();

    private TypicalInsurances() {} // prevents instantiation
}
