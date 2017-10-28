package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static seedu.address.model.util.DateUtil.generateOutdatedDebtDate;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Date;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.exceptions.UserNotFoundException;
import seedu.address.logic.Password;
import seedu.address.logic.Username;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.util.DateUtil;
import seedu.address.testutil.PersonBuilder;

public class InterestTest {
    // dates that interest should affect debt
    private final String sampleDateInput1 = "01-11-2017";
    private final String sampleDateInput2 = "01-01-2017";
    private final String sampleDateInput3 = "01-03-2017";
    private final String sampleDateInput4 = "01-05-2017";
    private final String sampleDateInput5 = "01-04-2017";
    private final String sampleDateInput6 = "20-05-2017";

    @Test
    public void respondToLoginEvent() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Person personToTest = new PersonBuilder().withName("AA").withDebt("10000").withInterest("1").build();
        Date lastAccruedDate = generateDateFromString(sampleDateInput2);
        personToTest.setLastAccruedDate(lastAccruedDate);
        try {
            model.addPerson(personToTest);
        } catch (DuplicatePersonException dpe) {
            assert false : " Should not happen as personToAdd is unique to typical persons";
        }
        try {
            Username username = new Username("loanShark97");
            Password password = new Password("hitMeUp123");
            model.authenticateUser(username, password); // raise login event.
        } catch (IllegalValueException ive) {
            assert false : "Username or password not valid.";
        } catch (UserNotFoundException unfe) {
            assert false : "User not found.";
        }
        // model should have handled login event and updated personToTest's debt
        int personToTestIdx = model.getFilteredPersonList().size() - 1;
        assertEquals("10936.85", model.getFilteredPersonList().get(personToTestIdx).getDebt().toString());
    }

    @Test
    public void checkLastAccruedTest() {
        // personToTest has interest rate of 1% and debt of $10,000
        Person personToTest = new PersonBuilder().withDebt("10000").build();
        Date lastAccruedDate = generateOutdatedDebtDate(generateDateFromString(sampleDateInput1));
        personToTest.setLastAccruedDate(lastAccruedDate);
        assertEquals(personToTest.checkLastAccruedDate(generateDateFromString(sampleDateInput1)), 1);

        lastAccruedDate = generateOutdatedDebtDate(generateDateFromString(sampleDateInput2));
        personToTest.setLastAccruedDate(lastAccruedDate);
        assertEquals(personToTest.checkLastAccruedDate(generateDateFromString(sampleDateInput2)), 1);

        lastAccruedDate = generateOutdatedDebtDate(generateDateFromString(sampleDateInput3));
        personToTest.setLastAccruedDate(lastAccruedDate);
        assertEquals(personToTest.checkLastAccruedDate(generateDateFromString(sampleDateInput3)), 1);

        lastAccruedDate = generateOutdatedDebtDate(generateDateFromString(sampleDateInput4));
        personToTest.setLastAccruedDate(lastAccruedDate);
        assertEquals(personToTest.checkLastAccruedDate(generateDateFromString(sampleDateInput4)), 1);

        lastAccruedDate = generateOutdatedDebtDate(generateDateFromString(sampleDateInput5));
        personToTest.setLastAccruedDate(lastAccruedDate);
        assertEquals(personToTest.checkLastAccruedDate(generateDateFromString(sampleDateInput5)), 1);

        lastAccruedDate = generateOutdatedDebtDate(generateDateFromString(sampleDateInput6));
        personToTest.setLastAccruedDate(lastAccruedDate);
        assertEquals(personToTest.checkLastAccruedDate(generateDateFromString(sampleDateInput6)), 1);
    }

    @Test
    public void checkAmountAccruedTest() {
        // personToTest has interest rate of 1% and debt of $10,000
        Person personToTest = new PersonBuilder().withDebt("10000").build();
        assertEquals(personToTest.calcAccruedAmount(1), "100.00");

        personToTest = new PersonBuilder().withDebt("200").withInterest("3").build();
        assertEquals(personToTest.calcAccruedAmount(1), "6.00");

        personToTest = new PersonBuilder().withDebt("50").withInterest("3").build();
        assertEquals(personToTest.calcAccruedAmount(1), "1.50");

        personToTest = new PersonBuilder().withDebt("3").withInterest("1").build();
        assertEquals(personToTest.calcAccruedAmount(1), "0.03");

        personToTest = new PersonBuilder().withDebt("11.10").withInterest("1").build();
        assertEquals(personToTest.calcAccruedAmount(1), "0.11");
    }

    @Test
    public void updatePersonDebtTest() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Person personToTest = new PersonBuilder().withName("AA").withDebt("10000").withInterest("1").build();
        try {
            model.addPerson(personToTest);
        } catch (DuplicatePersonException dpe) {
            assert false : " Should not happen as personToAdd is unique to typical persons";
        }
        // personToTest added to end of addressbook
        int personToTestIdx = model.getFilteredPersonList().size() - 1;
        model.updateDebtFromInterest(model.getFilteredPersonList().get(personToTestIdx), 1);
        assertEquals("10100.00", model.getFilteredPersonList().get(personToTestIdx).getDebt().toString());

        model.updateDebtFromInterest(model.getFilteredPersonList().get(personToTestIdx), 1);
        assertEquals("10201.00", model.getFilteredPersonList().get(personToTestIdx).getDebt().toString());
        // interest compounded for a duration of 2 months
        model.updateDebtFromInterest(model.getFilteredPersonList().get(personToTestIdx), 2);
        assertEquals("10406.04", model.getFilteredPersonList().get(personToTestIdx).getDebt().toString());
        // interest compounded for a duration of 5 months
        model.updateDebtFromInterest(model.getFilteredPersonList().get(personToTestIdx), 5);
        assertEquals("10936.85", model.getFilteredPersonList().get(personToTestIdx).getDebt().toString());
    }

    /**
     * Generate date to be used for testing
     */
    private Date generateDateFromString(String dateString) {
        String date = DateUtil.formatDate(dateString);
        return DateUtil.convertStringToDate(date);
    }
}
