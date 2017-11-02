# lawwman
###### \java\seedu\address\logic\commands\OverdueListCommandTest.java
``` java
public class OverdueListCommandTest {

    private Model model;
    private Model expectedModel;
    private OverdueListCommand overdueListCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        overdueListCommand = new OverdueListCommand();
        overdueListCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        model.setCurrentListName("overduelist");
        assertCommandSuccess(overdueListCommand, model,
                ListObserver.OVERDUELIST_NAME_DISPLAY_FORMAT + OverdueListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showFirstOverdueDebtPersonOnly(model);
        assertCommandSuccess(overdueListCommand, model,
                ListObserver.OVERDUELIST_NAME_DISPLAY_FORMAT + OverdueListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
```
###### \java\seedu\address\logic\ListObserverTest.java
``` java
/**
 * Tests the ListObserver class.
 * It is expected that the TypicalAddressBook in the {@code TypicalPersons} class has a person residing in every list.
 */
public class ListObserverTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private ListObserver listObserver = new ListObserver(model);

    @Test
    public void checkCurrentFilteredList() {
        model.setCurrentListName("list");
        assertEquals(listObserver.getCurrentFilteredList(), model.getFilteredPersonList());
        model.setCurrentListName("blacklist");
        assertEquals(listObserver.getCurrentFilteredList(), model.getFilteredBlacklistedPersonList());
        model.setCurrentListName("whitelist");
        assertEquals(listObserver.getCurrentFilteredList(), model.getFilteredWhitelistedPersonList());
        model.setCurrentListName("overduelist");
        assertEquals(listObserver.getCurrentFilteredList(), model.getFilteredOverduePersonList());
    }

    @Test
    public void checkCurrentListName() {
        model.setCurrentListName("list");
        assertEquals(listObserver.getCurrentListName(), "MASTERLIST:\n");
        model.setCurrentListName("blacklist");
        assertEquals(listObserver.getCurrentListName(), "BLACKLIST:\n");
        model.setCurrentListName("whitelist");
        assertEquals(listObserver.getCurrentListName(), "WHITELIST:\n");
        model.setCurrentListName("overduelist");
        assertEquals(listObserver.getCurrentListName(), "OVERDUELIST:\n");
    }

    @Test
    public void updateCurrentFilteredListTest() {
        Person personToFind = (Person) model.getFilteredPersonList().get(0);
        String nameToFind = personToFind.getName().fullName;
        String[] keywords = nameToFind.split("\\s+");
        NameContainsKeywordsPredicate findPredicate = new NameContainsKeywordsPredicate(Arrays.asList(keywords));
        assertEquals(listObserver.updateCurrentFilteredList(findPredicate), 1);

        listObserver.updateCurrentFilteredList(PREDICATE_SHOW_ALL_PERSONS); // reset predicate

        model.setCurrentListName("blacklist");
        // typical addressbook has blacklisted person
        personToFind = (Person) model.getFilteredBlacklistedPersonList().get(0);
        nameToFind = personToFind.getName().fullName;
        keywords = nameToFind.split("\\s+");
        findPredicate = new NameContainsKeywordsPredicate(Arrays.asList(keywords));
        assertEquals(listObserver.updateCurrentFilteredList(findPredicate), 1);

        listObserver.updateCurrentFilteredList(PREDICATE_SHOW_ALL_PERSONS); // reset predicate

        model.setCurrentListName("whitelist");
        // typical addressbook has whitelisted person
        personToFind = (Person) model.getFilteredBlacklistedPersonList().get(0);
        nameToFind = personToFind.getName().fullName;
        keywords = nameToFind.split("\\s+");
        findPredicate = new NameContainsKeywordsPredicate(Arrays.asList(keywords));
        assertEquals(listObserver.updateCurrentFilteredList(findPredicate), 1);

        listObserver.updateCurrentFilteredList(PREDICATE_SHOW_ALL_PERSONS); // reset predicate

        model.setCurrentListName("overduelist");
        // typical addressbook has overdue debt person
        personToFind = (Person) model.getFilteredBlacklistedPersonList().get(0);
        nameToFind = personToFind.getName().fullName;
        keywords = nameToFind.split("\\s+");
        findPredicate = new NameContainsKeywordsPredicate(Arrays.asList(keywords));
        assertEquals(listObserver.updateCurrentFilteredList(findPredicate), 1);
    }

    /**
     * Assuming that the typicalAddressBook in the {@code model} has at least 1 person in each list
     */
    @Test
    public void checkIndexOfCurrentPersonInList() {
        model.setCurrentListName("list");
        Person personToCheck = (Person) model.getFilteredPersonList().get(0);
        Index indexOfPerson = listObserver.getIndexofPersonInCurrentList(personToCheck);
        assertEquals(0, indexOfPerson.getZeroBased());

        model.setCurrentListName("blacklist");
        personToCheck = (Person) model.getFilteredBlacklistedPersonList().get(0);
        indexOfPerson = listObserver.getIndexofPersonInCurrentList(personToCheck);
        assertEquals(0, indexOfPerson.getZeroBased());

        model.setCurrentListName("whitelist");
        personToCheck = (Person) model.getFilteredWhitelistedPersonList().get(0);
        indexOfPerson = listObserver.getIndexofPersonInCurrentList(personToCheck);
        assertEquals(0, indexOfPerson.getZeroBased());

        model.setCurrentListName("overduelist");
        personToCheck = (Person) model.getFilteredOverduePersonList().get(0);
        indexOfPerson = listObserver.getIndexofPersonInCurrentList(personToCheck);
        assertEquals(0, indexOfPerson.getZeroBased());
    }
}
```
###### \java\seedu\address\model\DateUtilTest.java
``` java
public class DateUtilTest {
    private static final String sampleDate1 = "Thu, 18 Oct, Year 2018";
    private static final String sampleDateInput1 = "18-10-2018";
    private static final Date sampleDateClass1 = new GregorianCalendar(2018, 9, 18).getTime();
    private static final Date sampleOldDateClass1 = new GregorianCalendar(2018, 8, 18).getTime();
    private static final String sampleDate2 = "Fri, 13 Dec, Year 2019";
    private static final String sampleDateInput2 = "13-12-2019";
    private static final Date sampleDateClass2 = new GregorianCalendar(2019, 11, 13).getTime();
    private static final Date sampleOldDateClass2 = new GregorianCalendar(2019, 10, 13).getTime();
    private static final String sampleDate3 = "Sat, 05 Jan, Year 2019";
    private static final String sampleDateInput3 = "05-01-2019";
    private static final Date sampleDateClass3 = new GregorianCalendar(2019, 0, 5).getTime();
    private static final Date sampleOldDateClass3 = new GregorianCalendar(2018, 11, 5).getTime();
    private static final String sampleDate4 = "Sat, 23 Mar, Year 2019";
    private static final String sampleDateInput4 = "23-03-2019";
    private static final Date sampleDateClass4 = new GregorianCalendar(2019, 2, 23).getTime();
    private static final Date sampleOldDateClass4 = new GregorianCalendar(2019, 1, 23).getTime();

    @Test
    public void formatDateTest() {
        assertEquals(sampleDate1, formatDate(sampleDateInput1));
        assertEquals(sampleDate1, formatDate(sampleDateClass1));
        assertEquals(sampleDate2, formatDate(sampleDateInput2));
        assertEquals(sampleDate2, formatDate(sampleDateClass2));
        assertEquals(sampleDate3, formatDate(sampleDateInput3));
        assertEquals(sampleDate3, formatDate(sampleDateClass3));
        assertEquals(sampleDate4, formatDate(sampleDateInput4));
        assertEquals(sampleDate4, formatDate(sampleDateClass4));
    }

    @Test
    public void checkLeapYearTest() {
        // leap years have to be divisible by 4
        // if a leap year is divisible by 100, it is not a leap year unless it is ALSO divisible by 400
        assertFalse(checkLeapYear(2017));
        assertFalse(checkLeapYear(2019));
        assertFalse(checkLeapYear(1999));
        assertFalse(checkLeapYear(1995));
        assertFalse(checkLeapYear(1800));
        assertFalse(checkLeapYear(2200));

        assertTrue(checkLeapYear(2000));
        assertTrue(checkLeapYear(2400));
        assertTrue(checkLeapYear(2016));
        assertTrue(checkLeapYear(2020));
    }

    @Test
    public void isValidDateTest() {
        // invalid dates
        assertFalse(isValidDateFormat("3-3-2019")); // correct format is 03-03-2019
        assertFalse(isValidDateFormat("3-march-2019")); // correct format is 03-03-2019
        assertFalse(isValidDateFormat("3rd March 2019")); //correct format is 03-03-2019
        assertFalse(isValidDateFormat("29-02-2017")); // only leap years have 29 days

        // valid dates
        assertTrue(isValidDateFormat(sampleDateInput1));
        assertTrue(isValidDateFormat(sampleDateInput2));
        assertTrue(isValidDateFormat(sampleDateInput3));
        assertTrue(isValidDateFormat(sampleDateInput4));
    }

    @Test
    public void convertStringToDateTest() {
        assertEquals(convertStringToDate(sampleDate1), sampleDateClass1);
        assertEquals(convertStringToDate(sampleDate2), sampleDateClass2);
        assertEquals(convertStringToDate(sampleDate3), sampleDateClass3);
        assertEquals(convertStringToDate(sampleDate4), sampleDateClass4);
    }

    @Test
    public void getMonthFromStringTest() {
        assertEquals(1, getMonthFromString("Jan"));
        assertEquals(2, getMonthFromString("Feb"));
        assertEquals(3, getMonthFromString("Mar"));
        assertEquals(4, getMonthFromString("Apr"));
        assertEquals(5, getMonthFromString("May"));
        assertEquals(6, getMonthFromString("Jun"));
        assertEquals(7, getMonthFromString("Jul"));
        assertEquals(8, getMonthFromString("Aug"));
        assertEquals(9, getMonthFromString("Sep"));
        assertEquals(10, getMonthFromString("Oct"));
        assertEquals(11, getMonthFromString("Nov"));
        assertEquals(12, getMonthFromString("Dec"));
    }

    @Test
    public void compareDateTest() {
        //same date
        assertTrue(compareDates(new Date(), new Date()));
        //first date before second one
        assertTrue(compareDates(sampleDateClass1, sampleDateClass2));
        assertTrue(compareDates(sampleDateClass3, sampleDateClass4));
        //second date before first one
        assertFalse(compareDates(sampleDateClass2, sampleDateClass1));
        assertFalse(compareDates(sampleDateClass4, sampleDateClass3));
    }

    @Test
    public void generateOutdatedDebtDateTest() {
        assertEquals(DateUtil.generateOutdatedDebtDate(sampleDateClass1), sampleOldDateClass1);
        assertEquals(DateUtil.generateOutdatedDebtDate(sampleDateClass2), sampleOldDateClass2);
        assertEquals(DateUtil.generateOutdatedDebtDate(sampleDateClass3), sampleOldDateClass3);
        assertEquals(DateUtil.generateOutdatedDebtDate(sampleDateClass4), sampleOldDateClass4);

    }

    @Test
    public void getNumberOfMonthBetweenDatesTest() {
        // one month gap
        assertEquals(DateUtil.getNumberOfMonthBetweenDates(sampleDateClass1, sampleOldDateClass1), 1);
        // 1 month gap, but both dates have different years.
        assertEquals(DateUtil.getNumberOfMonthBetweenDates(sampleDateClass2, sampleOldDateClass2), 1);
        // 5 month gap, both dates have different years.
        assertEquals(DateUtil.getNumberOfMonthBetweenDates(sampleDateClass4, sampleDateClass1), 5);
        // Large gap between both dates.
        assertEquals(DateUtil.getNumberOfMonthBetweenDates(new GregorianCalendar(2021, 2, 23).getTime(),
                sampleDateClass1), 29);

    }
}
```
###### \java\seedu\address\model\InterestTest.java
``` java
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
        String actualDebt = model.getFilteredPersonList().get(personToTestIdx).getDebt().toString();
        String expectedDebt = generateExpectedDebt(personToTest);
        assertEquals(expectedDebt, actualDebt);
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

        lastAccruedDate = (generateDateFromString(sampleDateInput6));
        personToTest.setLastAccruedDate(lastAccruedDate);
        assertEquals(personToTest.checkLastAccruedDate(generateDateFromString(sampleDateInput6)), 0);
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

    /**
     * Generate expected debt for person under test.
     */
    private String generateExpectedDebt(Person person) {
        Person personUnderTest = person;
        int numberOfMonths = personUnderTest.checkLastAccruedDate(new Date());
        double accruedAmount = Double.parseDouble(personUnderTest.calcAccruedAmount(numberOfMonths));
        double expectedDebt = accruedAmount + personUnderTest.getDebt().toNumber();
        return Double.toString(expectedDebt);
    }
}
```
###### \java\seedu\address\model\person\DeadlineTest.java
``` java
public class DeadlineTest {

    @Test
    public void isValidDeadLine() {
        // invalid deadlines
        assertFalse(Deadline.isValidDeadline("")); // empty string
        assertFalse(Deadline.isValidDeadline(" ")); // spaces only
        assertFalse(Deadline.isValidDeadline("91")); // just 2 digits
        assertFalse(Deadline.isValidDeadline("dead line")); // non-numeric
        assertFalse(Deadline.isValidDeadline("91-20-30")); // incorrect format
        assertFalse(Deadline.isValidDeadline("21-21-2017")); // impossible date

        // valid deadlines
        assertTrue(Deadline.isValidDeadline("11-11-2019")); // correct format
    }

    @Test
    public void compareTo() throws Exception {
        Deadline noDeadline = new Deadline(NO_DEADLINE_SET);
        Deadline earlyDeadline = new Deadline("01-01-2001");
        Deadline lateDeadline = new Deadline("31-12-2099");

        assertEquals(0, noDeadline.compareTo(noDeadline));
        assertEquals(1, noDeadline.compareTo(lateDeadline));
        assertEquals(-1, earlyDeadline.compareTo(noDeadline));
        assertEquals(-1, earlyDeadline.compareTo(lateDeadline));
        assertEquals(1, lateDeadline.compareTo(earlyDeadline));
    }
}
```
###### \java\seedu\address\model\person\DebtTest.java
``` java
public class DebtTest {

    @Test
    public void isValidDebt() {
        // invalid debt
        assertFalse(Debt.isValidDebt("")); // empty string
        assertFalse(Debt.isValidDebt(" ")); // spaces only
        assertFalse(Debt.isValidDebt("phone")); // non-numeric
        assertFalse(Debt.isValidDebt("9011p041")); // alphabets within digits
        assertFalse(Debt.isValidDebt("9312 1534")); // spaces within digits
        assertFalse(Debt.isValidDebt("123.345")); // more than two decimal places
        assertFalse(Debt.isValidDebt("123.3")); // only one decimal places
        // valid debts
        assertTrue(Debt.isValidDebt("9")); // exactly 1 number
        assertTrue(Debt.isValidDebt("124293842033123")); // huge debts
        assertTrue(Debt.isValidDebt("124293842033123.10")); // two decimal places
    }
}
```
