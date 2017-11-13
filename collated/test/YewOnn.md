# YewOnn
###### \java\seedu\address\logic\commands\FindByPhoneCommandTest.java
``` java
public class FindByPhoneCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        PhoneContainsKeywordsPredicate firstPredicate =
                new PhoneContainsKeywordsPredicate(Collections.singletonList("first"));
        PhoneContainsKeywordsPredicate secondPredicate =
                new PhoneContainsKeywordsPredicate(Collections.singletonList("second"));

        FindByPhoneCommand findFirstCommand = new FindByPhoneCommand(firstPredicate);
        FindByPhoneCommand findSecondCommand = new FindByPhoneCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindByPhoneCommand findFirstCommandCopy = new FindByPhoneCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        //assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void executeZeroKeywordsNoPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        FindByPhoneCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }
```
###### \java\seedu\address\logic\MrtMapLogicTest.java
``` java

/**
 * Test the MrtMapLogic file to make sure it works well.
 */
public class MrtMapLogicTest {
    @Test
    /**
     * This is a trivial test case
     */
    public void testTrivialSortedMrtList() throws Exception {
        MrtMapLogic mrtMapLogic = new MrtMapLogic();
        ArrayList<String> mrtStations = new ArrayList<String>();
        mrtStations.add("Dhoby Ghaut");
        ArrayList<String> mrtNameList = mrtMapLogic.getSortedMrtList(mrtStations);
        String mrtStation = mrtNameList.get(0);
        assertEquals("Dhoby Ghaut", mrtStation);
    }

    @Test
    /**
     * Non-trivial test case. A passed test case means that the Dijkstra's algoritms is
     * also correctly implemented
     */
    public void testSortedMrtList() throws Exception {
        MrtMapLogic mrtMapLogic = new MrtMapLogic();
        ArrayList<String> mrtStations = new ArrayList<String>();
        mrtStations.add("Dhoby Ghaut");
        mrtStations.add("Jurong East");
        mrtStations.add("Bishan");
        ArrayList<String> mrtNameList = mrtMapLogic.getSortedMrtList(mrtStations);

        //Botanic Gardens is the most convenient meeting point for this case,
        //followed by dhoby ghaut than Newton
        assertEquals("Botanic Gardens", mrtNameList.get(0));
        assertEquals("Dhoby Ghaut", mrtNameList.get(1));
        assertEquals("Newton", mrtNameList.get(2));
    }

    @Test
    /**
     * Check if the method returns the correct mrtStation name list
     */
    public void getMrtStationNames() throws Exception {
        MrtMapLogic mrtMapLogic = new MrtMapLogic();
        ArrayList<String> mrtNameList = mrtMapLogic.getMrtStationNames();
        assertEquals("Jurong East", mrtNameList.get(0));
        assertEquals("Bukit Batok", mrtNameList.get(1));
        assertEquals("Bukit Gombak", mrtNameList.get(2));
    }

    @Test
    /**
     * Check if the method returns the correct mrtStation shortName list
     */
    public void getMrtStationShortNames() throws Exception {
        MrtMapLogic mrtMapLogic = new MrtMapLogic();
        ArrayList<String> mrtShortNameList = mrtMapLogic.getMrtStationShortNames();
        assertEquals("JUR", mrtShortNameList.get(0));
        assertEquals("BBT", mrtShortNameList.get(1));
        assertEquals("BGB", mrtShortNameList.get(2));
    }

    @Test
    /**
     * Check if it returns the correct mrt line list
     */
    public void getMrtLineNames() throws Exception {
        MrtMapLogic mrtMapLogic = new MrtMapLogic();
        ArrayList<ArrayList<String>> mrtLineNameList = mrtMapLogic.getMrtLineNames();
        //Jurong East is in both NS (NorthSouth Line) and EW (East West Line)
        assertEquals("NS", mrtLineNameList.get(0).get(0));
        assertEquals("EW", mrtLineNameList.get(0).get(1));

        //Potong Pasir is in NE (North East Line)
        assertEquals("NE", mrtLineNameList.get(70).get(0));

        //Promenade is in DT (DownTownLine), CC (CircleLine) and CE (CircleLine)
        assertEquals("DT", mrtLineNameList.get(122).get(0));
        assertEquals("CC", mrtLineNameList.get(122).get(1));
        assertEquals("CE", mrtLineNameList.get(122).get(2));
    }

    @Test
    /**
     * Check if it returns the correct mrt station code list
     */
    public void getMrtLineNumbers() throws Exception {
        MrtMapLogic mrtMapLogic = new MrtMapLogic();
        ArrayList<ArrayList<Integer>> mrtLineNumberList = mrtMapLogic.getMrtLineNumbers();
        //Jurong East's station codes are NS1 and EW 24
        assertEquals((long) 1, (long) mrtLineNumberList.get(0).get(0));
        assertEquals((long)24, (long) mrtLineNumberList.get(0).get(1));

        //Potong Pasir's station code is NE10
        assertEquals((long) 10, (long) mrtLineNumberList.get(70).get(0));

        //Promenade's station codes are DT15 and CC4
        assertEquals((long) 15, (long) mrtLineNumberList.get(122).get(0));
        assertEquals((long) 4, (long)mrtLineNumberList.get(122).get(1));
    }

    @Test
    public void isValidMrt() throws Exception {
        MrtMapLogic mrtMapLogic = new MrtMapLogic();

        assertTrue(mrtMapLogic.isValidMrt("Bishan"));
        assertFalse(mrtMapLogic.isValidMrt("noSuchStation"));
    }

    @Test
    public void getTravelTime() throws Exception {
        MrtMapLogic mrtMapLogic = new MrtMapLogic();
        int travelTime = mrtMapLogic.getTravelTime("Bishan", "Pasir Ris");
        assertEquals((long)36, (long) travelTime);

        travelTime = mrtMapLogic.getTravelTime("HarbourFront", "Pasir Ris");
        assertEquals((long)44, (long) travelTime);
    }

}
```
###### \java\seedu\address\logic\parser\FindByPhoneCommandParserTest.java
``` java
public class FindByPhoneCommandParserTest {

    private FindByPhoneCommandParser parser = new FindByPhoneCommandParser();

    @Test
    public void parseEmptyArgThrowsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindByPhoneCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseValidArgsReturnsFindCommand() {
        // no leading and trailing whitespaces
        FindByPhoneCommand expectedCommand =
                new FindByPhoneCommand(new PhoneContainsKeywordsPredicate(Arrays.asList("street", "raffles")));
        assertParseSuccess(parser, "street raffles", expectedCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n street \n \t raffles  \t", expectedCommand);
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindByPhoneCommand command = prepareCommand("95352563 87652533 9482224");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, DANIEL, ELLE));
    }

    /**
     * Parses {@code userInput} into a {@code FindCommand}.
     */
    private FindByPhoneCommand prepareCommand(String userInput) {
        FindByPhoneCommand command =
                new FindByPhoneCommand(new PhoneContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     *     - the {@code PhoneBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindByPhoneCommand command,
                                      String expectedMessage, List<ReadOnlyPerson> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
```
