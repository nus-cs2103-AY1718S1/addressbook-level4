# ReneeSeet
###### \java\guitests\guihandles\EmailPanelHandle.java
``` java
/**
 * A handler for the {@code MassEmailPanel} of the UI.
 */

public class EmailPanelHandle extends NodeHandle<Node>  {

    public static final String EMAIL_ID = "#emailpanel";
    public static final String SUBJECT_TEXTBOX_ID = "emailSubjectBox";
    public static final String TO_TEXTBOX_ID = "recipientsBox";
    public static final String SEND_BUTTON_ID = "sendButton";
    public static final String MESSAGE_TEXTBOX_ID = "emailMessage";

    private Node recipientBox;
    private Node subjectBox;
    private Node sendButton;
    private Node emailMessage;

    public EmailPanelHandle(Node emailPanelNode) {
        super(emailPanelNode);
        Pane pane = getChildNode(EMAIL_ID);
        ObservableList<Node> listNode = pane.getChildren();
        for (int i = 0; i < listNode.size(); i++) {
            if (listNode.get(i).getId() != null) {
                if (listNode.get(i).getId().equals(TO_TEXTBOX_ID)) {
                    recipientBox = listNode.get(i);
                } else if (listNode.get(i).getId().equals(SEND_BUTTON_ID)) {
                    sendButton = listNode.get(i);
                } else if (listNode.get(i).getId().equals(MESSAGE_TEXTBOX_ID)) {
                    emailMessage = listNode.get(i);
                } else if (listNode.get(i).getId().equals(SUBJECT_TEXTBOX_ID)) {
                    subjectBox = listNode.get(i);
                }
            }
        }
    }

    public String getRecipientsText() {
        TextField f = (TextField) recipientBox;
        return f.getText();
    }

    public String getSubjectText() {
        TextField f = (TextField) subjectBox;
        return f.getText();
    }

    public String getMessageText() {
        TextArea f = (TextArea) emailMessage;
        return f.getText();
    }

    public Button getSendButton() {
        Button f = (Button) sendButton;
        return f;
    }

}
```
###### \java\seedu\address\logic\commands\MassEmailCommandTest.java
``` java

/**
 * Contains integration tests (interaction with the Model) and unit tests for MassEmailCommand.
 */

public class MassEmailCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_NOBODY_FOUND);
        MassEmailCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    //test 'all' predicate
    public void execute_massEmail_allsuccess() throws Exception {
        MassEmailCommand command = prepareCommand("all");
        String expectedMessage = buildExpectedMessage(getTypicalAddressBook().getPersonList());
        assertCommandSuccess(command, expectedMessage , getTypicalAddressBook().getPersonList());
    }

    @Test
    // one valid tag
    public void  execute_tagEmail_success() throws  Exception {
        MassEmailCommand command = prepareCommand("family");
        String expectedMessage = buildExpectedMessage(Arrays.asList(ALICE));
        assertCommandSuccess(command, expectedMessage , Arrays.asList(ALICE));
    }

    @Test
    //no vaild tag
    public void  executenoVaildTagEmailsuccess() throws  Exception {
        MassEmailCommand command = prepareCommand("hello");
        String expectedMessage = buildExpectedMessage(Collections.emptyList());
        assertCommandSuccess(command, expectedMessage , Collections.emptyList());
    }

    @Test
    // 1 vaild tag and 1 invalid tag
    public void  executevalidInvalidtagEmailsuccess() throws  Exception {
        MassEmailCommand command = prepareCommand("family hello");
        String expectedMessage = buildExpectedMessage(Arrays.asList(ALICE));
        assertCommandSuccess(command, expectedMessage , Arrays.asList(ALICE));
    }

    /**
     * Parses {@code userInput} into a {@code MassEmailCommand}.
     */
    private MassEmailCommand prepareCommand(String userInput) {
        MassEmailCommand command =
                new MassEmailCommand(new TagMatchingPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory() , new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(MassEmailCommand command,
                                      String expectedMessage, List<ReadOnlyPerson> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage , commandResult.feedbackToUser);
        assertEquals(expectedList , model.getFilteredPersonList());
        assertEquals(expectedAddressBook , model.getAddressBook());
    }

    /**
     * Builds Expected Message for Mass Email
     */
    private String buildExpectedMessage(List<ReadOnlyPerson> expectedList) {
        if (!expectedList.isEmpty()) {
            StringBuilder mess = new StringBuilder(
                    String.format(Messages.MESSAGE_SMS_CONFIRMATION, expectedList.size()));
            mess.append("\n");
            for (int i = 0; i < expectedList.size(); i++) {
                mess.append(expectedList.get(i).getEmail());
                mess.append("\n");
            }
            return mess.toString();
        } else {
            return MESSAGE_NOBODY_FOUND;
        }
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_massEmail() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        MassEmailCommand command = (MassEmailCommand) parser.parseCommand(
                MassEmailCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new MassEmailCommand(new TagMatchingPredicate(keywords)), command);
    }
```
###### \java\seedu\address\logic\parser\MassEmailCommandParserTest.java
``` java

public class MassEmailCommandParserTest {

    private MassEmailParser parser = new MassEmailParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MassEmailCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsMassEmailCommand() {
        // no leading and trailing whitespaces
        MassEmailCommand expectedMassEmailCommand =
                new MassEmailCommand(new TagMatchingPredicate((Arrays.asList("friends", "OwesMoney"))));
        assertParseSuccess(parser, "friends OwesMoney", expectedMassEmailCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n friends \n \t OwesMoney  \t", expectedMassEmailCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, " ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MassEmailCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\model\TagMatchingPredicateTest.java
``` java

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.model.tag.TagMatchingPredicate;
import seedu.address.testutil.PersonBuilder;

public class TagMatchingPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        TagMatchingPredicate firstPredicate = new TagMatchingPredicate(firstPredicateKeywordList);
        TagMatchingPredicate secondPredicate = new TagMatchingPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TagMatchingPredicate firstPredicateCopy = new TagMatchingPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_tagContainsKeywords_returnsTrue() {
        // One keyword
        TagMatchingPredicate predicate = new TagMatchingPredicate(Collections.singletonList("friends"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friends").build()));

        // Multiple keywords
        predicate = new  TagMatchingPredicate(Arrays.asList("sec2", "sec1"));
        assertTrue(predicate.test(new PersonBuilder().withTags("sec2", "sec1").build()));

        // Only one matching keyword
        predicate = new  TagMatchingPredicate(Arrays.asList("sec1"));
        assertTrue(predicate.test(new PersonBuilder().withTags("sec2", "sec1").build()));

        //if keyword is 'all'
        predicate = new  TagMatchingPredicate(Arrays.asList("all"));
        assertTrue(predicate.test(new PersonBuilder().withTags("sec2", "sec1").build()));
        assertTrue(predicate.test(new PersonBuilder().withTags("sec3").build()));

    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        TagMatchingPredicate predicate = new  TagMatchingPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withTags("sec1").build()));

        // Non-matching keyword
        predicate = new  TagMatchingPredicate(Arrays.asList("sec1"));
        assertFalse(predicate.test(new PersonBuilder().withTags("sec3").build()));

        // Mixed-case keywords
        predicate = new  TagMatchingPredicate(Arrays.asList("SeC1", "Sec2"));
        assertFalse(predicate.test(new PersonBuilder().withTags("sec2", "sec1").build()));

        predicate = new  TagMatchingPredicate(Arrays.asList("ALL"));
        assertFalse(predicate.test(new PersonBuilder().withTags("sec2", "sec1").build()));

        // Keywords match phone, email and address
        predicate = new  TagMatchingPredicate(Arrays.asList("12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
        //Keywords match phone
        predicate = new  TagMatchingPredicate(Arrays.asList("12345"));
        assertFalse(predicate.test(new PersonBuilder().withPhone("12345").build()));

        //Keywords match address
        predicate = new  TagMatchingPredicate(Arrays.asList("Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withAddress("Main Street").build()));

        //Keywords match email
        predicate = new  TagMatchingPredicate(Arrays.asList("alice", "email.com"));
        assertFalse(predicate.test(new PersonBuilder().withEmail("alice@email.com").build()));
    }
}
```
###### \java\seedu\address\testutil\PersonBuilder.java
``` java
    public static final String DEFAULT_DATE = "27/20/2017";
```
###### \java\seedu\address\testutil\PersonBuilder.java
``` java
            JoinDate defaultDate = new JoinDate();
            this.person = new Person(defaultName, defaultPhone, defaultEmail, defaultAddress, defaultDate, defaultTags);
```
###### \java\seedu\address\ui\EmailPanelTest.java
``` java

public class EmailPanelTest extends GuiUnitTest  {

    private static final ObservableList<ReadOnlyPerson> TYPICAL_PERSONS =
            FXCollections.observableList(getTypicalPersons());
    private EmailPanel emailPanel;
    private EmailPanelHandle emailPanelHandle;
    private String expectedEmail;
    private ArrayList<String> emails;

    @Before
    public void setUp() {
        StringBuilder expectedEmailbuilder = new StringBuilder();
        emails = new ArrayList<String>();
        for (int i = 0; i < TYPICAL_PERSONS.size(); i++) {
            emails.add(TYPICAL_PERSONS.get(i).getEmail().toString());
            expectedEmailbuilder.append(TYPICAL_PERSONS.get(i).getEmail().toString()).append(";");
        }
        expectedEmail = expectedEmailbuilder.toString();
        guiRobot.interact(() -> emailPanel = new EmailPanel(emails));
        uiPartRule.setUiPart(emailPanel);

        emailPanelHandle = new EmailPanelHandle(emailPanel.getRoot());
    }

    @Test
    public void display() {
        //check that the emails get added to "to:" textbox correctly
        assertEquals(expectedEmail, emailPanelHandle.getRecipientsText());
        //check that the subject box is empty
        assertEquals("", emailPanelHandle.getSubjectText());
        //check that the Message box is
        assertEquals("", emailPanelHandle.getMessageText());
    }
}
```
###### \java\systemtests\MassEmailCommandSystemTest.java
``` java

public class MassEmailCommandSystemTest extends AddressBookSystemTest {

    private static final String MESSAGE_INVALID_EMAIL_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, MassEmailCommand.MESSAGE_USAGE);
    @Test
    public void massEmail() throws Exception {
        Model expectedModel = getModel();
        //Case: Invalid mass email command
        String command = MassEmailCommand.COMMAND_WORD;
        String expectedResultMessage = String.format(MESSAGE_INVALID_EMAIL_COMMAND_FORMAT);
        assertCommandFailure(command, expectedResultMessage);

        //Case: valid mass email command
        command = MassEmailCommand.COMMAND_WORD + " " + "all";
        expectedResultMessage = buildExpectedMessage(getModel().getAddressBook().getPersonList());
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        //Case: one valid tag
        command = MassEmailCommand.COMMAND_WORD + " " + "family";
        expectedModel.updateFilteredPersonList(new TagMatchingPredicate(Arrays.asList("family")));
        expectedResultMessage = buildExpectedMessage(Arrays.asList(ALICE));
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        //Case: no valid tag
        command = MassEmailCommand.COMMAND_WORD + " " + "hello";
        expectedModel.updateFilteredPersonList(new TagMatchingPredicate(Arrays.asList("hello")));
        expectedResultMessage = buildExpectedMessage(Collections.emptyList());
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        // Case: undo  --> nothing to undo
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        //case:redo -->nothing to redo
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, ReadOnlyPerson)} except that the result
     * display box displays {@code expectedResultMessage} and the model related components equal to
     * {@code expectedModel}.
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();
        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Builds Expected Message for Mass Email
     */
    private String buildExpectedMessage(List<ReadOnlyPerson> expectedList) {
        if (!expectedList.isEmpty()) {
            StringBuilder mess = new StringBuilder(
                    String.format(Messages.MESSAGE_SMS_CONFIRMATION, expectedList.size()));
            mess.append("\n");
            for (int i = 0; i < expectedList.size(); i++) {
                mess.append(expectedList.get(i).getEmail());
                mess.append("\n");
            }
            return mess.toString();
        } else {
            return Messages.MESSAGE_NOBODY_FOUND;
        }
    }
}
```
