# justuswah
###### \java\seedu\address\logic\commands\SmsCommandTest.java
``` java
public class SmsCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_unauthorizedIndex_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size());
        assertExecutionFailure(outOfBoundsIndex, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Executes a {@code SmsCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        SmsCommand smsCommand = new SmsCommand(index, "hi");

        try {
            smsCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (ApiException ae) {
            assertEquals(expectedMessage, MESSAGE_SMS_NUMBER_UNAUTHORIZED);
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        } catch (CommandException ce) {
            assertEquals(expectedMessage, MESSAGE_SMS_NUMBER_UNAUTHORIZED);
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        } catch (NullPointerException npe) {
            assertEquals(expectedMessage, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }
}
```
