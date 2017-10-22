package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static seedu.address.model.util.DateUtil.generateOutdatedDebtDate;

import java.util.Date;

import org.junit.Test;

import seedu.address.model.person.Person;
import seedu.address.model.util.DateUtil;
import seedu.address.testutil.PersonBuilder;

public class InterestTest {
    // dates that interest should affect debt
    private final String sampleDateInput1 = "01-11-2017";
    private final String sampleDateInput2 = "01-01-2017";
    private final String sampleDateInput3 = "01-03-2017";
    private final String sampleDateInput4 = "01-05-2017";
    private final String sampleDateInput5 = "01-04-2017";

    // dates that interest should not affect debt
    private final String sampleDateInput6 = "20-05-2017";

    @Test
    public void checkUpdateDebtTest() {
        // personToTest has interest rate of 1% and debt of $10,000
        Person personToTest = new PersonBuilder().withDebt("10000").build();
        Date lastAccruedDate = generateOutdatedDebtDate(generateDateFromString(sampleDateInput1));
        personToTest.setLastAccruedDate(lastAccruedDate);
        assertEquals(personToTest.checkUpdateDebt(generateDateFromString(sampleDateInput1)), 1);
        lastAccruedDate = generateOutdatedDebtDate(generateDateFromString(sampleDateInput2));
        personToTest.setLastAccruedDate(lastAccruedDate);
        assertEquals(personToTest.checkUpdateDebt(generateDateFromString(sampleDateInput2)), 1);
        lastAccruedDate = generateOutdatedDebtDate(generateDateFromString(sampleDateInput3));
        personToTest.setLastAccruedDate(lastAccruedDate);
        assertEquals(personToTest.checkUpdateDebt(generateDateFromString(sampleDateInput3)), 1);
        lastAccruedDate = generateOutdatedDebtDate(generateDateFromString(sampleDateInput4));
        personToTest.setLastAccruedDate(lastAccruedDate);
        assertEquals(personToTest.checkUpdateDebt(generateDateFromString(sampleDateInput4)), 1);
        lastAccruedDate = generateOutdatedDebtDate(generateDateFromString(sampleDateInput5));
        personToTest.setLastAccruedDate(lastAccruedDate);
        assertEquals(personToTest.checkUpdateDebt(generateDateFromString(sampleDateInput5)), 1);


        //assert should return false as it is not the first day of the month
        lastAccruedDate = generateOutdatedDebtDate(generateDateFromString(sampleDateInput6));
        personToTest.setLastAccruedDate(lastAccruedDate);
        assertEquals(personToTest.checkUpdateDebt(generateDateFromString(sampleDateInput6)), 0);
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

    /**
     * Generate date to be used for testing
     */
    private Date generateDateFromString(String dateString) {
        String date = DateUtil.formatDate(dateString);
        return DateUtil.convertStringToDate(date);
    }
}
