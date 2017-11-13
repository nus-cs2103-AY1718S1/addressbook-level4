# awarenessxz
###### \java\guitests\guihandles\EmailMessageDisplayHandle.java
``` java
/**
 * A handler for the {@code MessageDisplay} of EmailMessageDisplay of the UI
 */
public class EmailMessageDisplayHandle extends NodeHandle<TextArea> {
    public static final String MESSAGE_DISPLAY_ID = "#messageArea";

    public EmailMessageDisplayHandle(TextArea messageDisplayNode) {
        super(messageDisplayNode);
    }

    /**
     * Returns the text in the message display.
     */
    public String getText() {
        return getRootNode().getText();
    }
}
```
###### \java\guitests\guihandles\EmailRecipientsDisplayHandle.java
``` java
/**
 * A handler for the {@code RecipientsDisplay} of EmailMessageDisplay of the UI
 */
public class EmailRecipientsDisplayHandle extends NodeHandle<TextArea> {
    public static final String RECIPIENTS_DISPLAY_ID = "#recipientsArea";

    public EmailRecipientsDisplayHandle(TextArea recipientsDisplayNode) {
        super(recipientsDisplayNode);
    }

    /**
     * Returns the text in the message display.
     */
    public String getText() {
        return getRootNode().getText();
    }
}
```
###### \java\guitests\guihandles\EmailSubjectDisplayHandle.java
``` java
/**
 * A handler for the {@code SubjectDisplay} of EmailMessageDisplay of the UI
 */
public class EmailSubjectDisplayHandle extends NodeHandle<TextArea> {
    public static final String SUBJECT_DISPLAY_ID = "#subjectArea";

    public EmailSubjectDisplayHandle(TextArea subjectDisplayNode) {
        super(subjectDisplayNode);
    }

    /**
     * Returns the text in the message display.
     */
    public String getText() {
        return getRootNode().getText();
    }
}
```
###### \java\guitests\guihandles\LeftDisplayPanelHandle.java
``` java
/**
 * A handler for the {@code LeftDisplayPanel} of the UI
 */
public class LeftDisplayPanelHandle extends NodeHandle<TabPane> {
    public static final String LEFT_DISPLAY_ID = "#leftDisplayPanel";
    private final PersonListPanelHandle personListPanel;
    private final EmailRecipientsDisplayHandle emailRecipientsDisplay;
    private final EmailMessageDisplayHandle emailMessageDisplay;
    private final EmailSubjectDisplayHandle emailSubjectDisplay;

    public LeftDisplayPanelHandle(TabPane leftDisplayPanelNode) {
        super(leftDisplayPanelNode);

        personListPanel = new PersonListPanelHandle(getChildNode(PersonListPanelHandle.PERSON_LIST_VIEW_ID));
        emailMessageDisplay = new EmailMessageDisplayHandle(getChildNode(EmailMessageDisplayHandle.MESSAGE_DISPLAY_ID));
        emailRecipientsDisplay = new EmailRecipientsDisplayHandle(
                getChildNode(EmailRecipientsDisplayHandle.RECIPIENTS_DISPLAY_ID));
        emailSubjectDisplay = new EmailSubjectDisplayHandle(getChildNode(EmailSubjectDisplayHandle.SUBJECT_DISPLAY_ID));
    }

    /**
     * Toggle the Tabs
     */
    public void toggle(int index) {
        click();
        guiRobot.interact(() -> getRootNode().getSelectionModel().select(index));
        guiRobot.pauseForHuman();
    }

    /**
     * Returns the text in the result display.
     */
    public int getSelectedTabIndex() {
        return getRootNode().getSelectionModel().getSelectedIndex();
    }

    public ObservableList<Tab> getTabs() {
        return getRootNode().getTabs();
    }

    public PersonListPanelHandle getPersonListPanel() {
        return personListPanel;
    }

    public EmailMessageDisplayHandle getEmailMessageDisplay() {
        return emailMessageDisplay;
    }

    public EmailRecipientsDisplayHandle getEmailRecipientsDisplay() {
        return emailRecipientsDisplay;
    }

    public EmailSubjectDisplayHandle getEmailSubjectDisplay() {
        return emailSubjectDisplay;
    }
}
```
###### \java\seedu\address\email\EmailComposeTest.java
``` java
public class EmailComposeTest {
    private EmailCompose emailCompose = new EmailCompose();

    @Test
    public void resetData() {
        //Creates standard EmailCompose class
        MessageDraft message = new MessageDraft("message", "subject");
        emailCompose.composeEmail(message);
        EmailCompose standardEmailCompose = new EmailCompose();
        standardEmailCompose.composeEmail(message);

        //both object should be same
        assertTrue(standardEmailCompose.equals(emailCompose));

        //after reset, object should be different
        emailCompose.resetData();
        assertFalse(standardEmailCompose.equals(emailCompose));
    }

    @Test
    public void composeEmail() {
        MessageDraft message;

        //Empty Message Draft
        message = new MessageDraft();
        emailCompose.composeEmail(message);
        assertEquals(message, emailCompose.getMessage());

        //Draft with only message filed
        message = new MessageDraft();
        message.setMessage("HELLO");
        emailCompose.composeEmail(message);
        assertEquals(message, emailCompose.getMessage());

        //Draft with only subject filled
        message = new MessageDraft();
        message.setSubject("SUBJECT");
        emailCompose.composeEmail(message);
        assertEquals(message, emailCompose.getMessage());

        //Draft with both message and email filled
        message = new MessageDraft("message", "subject");
        emailCompose.composeEmail(message);
        assertEquals(message, emailCompose.getMessage());

    }

    @Test
    public void equals() {
        //Set up expected EmailCompose
        EmailCompose standardEmailCompose = new EmailCompose();
        MessageDraft message = new MessageDraft("adam@gmail.com", "password");
        standardEmailCompose.composeEmail(message);

        //same values --> returns true
        EmailCompose emailCompose = new EmailCompose();
        emailCompose.composeEmail(message);
        assertTrue(standardEmailCompose.equals(emailCompose));

        //same object --> returns true
        assertTrue(standardEmailCompose.equals(standardEmailCompose));

        //null --> returns false
        assertFalse(standardEmailCompose.equals(null));

        //different type --> return false
        assertFalse(standardEmailCompose.equals(5));

        //different message --> return false
        emailCompose = new EmailCompose();
        emailCompose.composeEmail(new MessageDraft("sam@gmail.com", "password"));
        assertFalse(standardEmailCompose.equals(emailCompose));

        //different subject --> return false
        emailCompose = new EmailCompose();
        emailCompose.composeEmail(new MessageDraft("adam@gmail.com", "password123"));
        assertFalse(standardEmailCompose.equals(emailCompose));

    }
}
```
###### \java\seedu\address\email\EmailLoginTest.java
``` java
public class EmailLoginTest {
    private EmailLogin emailLogin = new EmailLogin();

    @Test
    public void isUserLogin() {
        //Methods only ensures there are 2 string inputs,
        //Verification of input is down in parser and method wrongUserEmailFormat

        try {
            //login without any values --> return false
            emailLogin.loginEmail(new String[0]);
            assertFalse(emailLogin.isUserLogin());

            //login with only 1 value --> return false
            emailLogin = new EmailLogin();
            String[] loginDetails1 = {"adam@gmail.com"};
            emailLogin.loginEmail(loginDetails1);
            assertFalse(emailLogin.isUserLogin());

            //login with 2 values --> return true
            emailLogin = new EmailLogin();
            String[] loginDetails2 = {"adam@gmail.com", "password"};
            emailLogin.loginEmail(loginDetails2);
            assertTrue(emailLogin.isUserLogin());

            //login with gmail --> return true
            emailLogin = new EmailLogin();
            String[] loginDetails3 = {"adam@gmail.com", "password"};
            emailLogin.loginEmail(loginDetails3);
            assertTrue(emailLogin.isUserLogin());

            //empty --> returns false
            emailLogin = new EmailLogin();
            emailLogin.loginEmail(new String[0]);
            assertFalse(emailLogin.isUserLogin());

        } catch (EmailLoginInvalidException e) {
            assert false : "Should not hit this case";
        }

        try {
            //login with non gmail --> return false
            emailLogin = new EmailLogin();
            String[] loginDetails4 = {"adam@yahoo.com", "password"};
            emailLogin.loginEmail(loginDetails4);
            assertFalse(emailLogin.isUserLogin());

        } catch (EmailLoginInvalidException e) {
            assert true : "user is unable to login with non gmail account";
        }
    }

    @Test
    public void getEmailLogin() {
        String [] loginDetails = {"adam@gmail.com", "password"};

        //user is not login --> return ""
        assertEquals("", emailLogin.getEmailLogin());

        //user is login --> returns user
        try {
            emailLogin.loginEmail(loginDetails);
        } catch (EmailLoginInvalidException e) {
            assert false : "shouldn't hit this case at all";
        }
        assertEquals("adam@gmail.com", emailLogin.getEmailLogin());
    }

    @Test
    public void getPassword() {
        String [] loginDetails = {"adam@gmail.com", "password"};

        //user is not login --> return ""
        assertEquals("", emailLogin.getPassword());

        //user is login --> returns password
        try {
            emailLogin.loginEmail(loginDetails);
        } catch (EmailLoginInvalidException e) {
            assert false : "shouldn't hit this case at all";
        }
        assertEquals("password", emailLogin.getPassword());
    }

    @Test
    public void resetData() {
        //Creates standard EmailLogin class
        String [] loginDetails = {"adam@gmail.com", "password"};
        EmailLogin standardEmailLogin = new EmailLogin();
        try {
            emailLogin.loginEmail(loginDetails);
            standardEmailLogin.loginEmail(loginDetails);
        } catch (EmailLoginInvalidException e) {
            assert false : "shouldn't hit this case at all";
        }

        //both object should be same
        assertTrue(standardEmailLogin.equals(emailLogin));

        //after reset, object should be different
        emailLogin.resetData();
        assertFalse(standardEmailLogin.equals(emailLogin));
    }

    @Test
    public void equals() {
        try {
            //Set up expected EmailCompose
            EmailLogin standardEmailLogin = new EmailLogin();
            String[] loginDetails = {"adam@gmail.com", "password"};
            standardEmailLogin.loginEmail(loginDetails);

            //same values --> returns true
            EmailLogin emailLogin = new EmailLogin();
            emailLogin.loginEmail(loginDetails);
            assertTrue(standardEmailLogin.equals(emailLogin));

            //same object --> returns true
            assertTrue(standardEmailLogin.equals(standardEmailLogin));

            //null --> returns false
            assertFalse(standardEmailLogin.equals(null));

            //different type --> return false
            assertFalse(standardEmailLogin.equals(5));

            //different login Details --> return false
            emailLogin = new EmailLogin();
            String [] loginDetails2 = {"alex@gmail.com", "password"};
            emailLogin.loginEmail(loginDetails2);
            assertFalse(standardEmailLogin.equals(emailLogin));

        } catch (EmailLoginInvalidException e) {
            assert false : "should not hit this case";
        }
    }
}
```
###### \java\seedu\address\email\EmailManagerTest.java
``` java
public class EmailManagerTest {

    private Email email = new EmailManager();

    @Test
    public void getEmailDraft() {
        MessageDraft message = new MessageDraft("message", "subject");
        email.composeEmail(message);

        //getEmailDraft should be equal
        assertTrue(message.equals(email.getEmailDraft()));
    }

    @Test
    public void isUserLogin() {
        //user is not login -> returns false
        assertFalse(email.isUserLogin());

        //user is login -> returns true
        String [] loginDetails = {"adam@gmail.com", "password"};
        try {
            email.loginEmail(loginDetails);
            assertTrue(email.isUserLogin());
        } catch (EmailLoginInvalidException e) {
            assert false : "shouldn't hit this at all";
        }
    }

    @Test
    public void equals() {
        try {
            //Set up expected Email
            Email standardEmail = new EmailManager();
            MessageDraft message = new MessageDraft("Hello", "subject");
            String[] loginDetails = {"adam@gmail.com", "password"};
            standardEmail.loginEmail(loginDetails);
            standardEmail.composeEmail(message);

            //same values --> returns true
            email = new EmailManager();
            email.composeEmail(message);
            email.loginEmail(loginDetails);
            assertTrue(standardEmail.equals(email));

            //same object --> returns true
            assertTrue(standardEmail.equals(standardEmail));

            //null --> returns false
            assertFalse(standardEmail.equals(null));

            //different type --> return false
            assertFalse(standardEmail.equals(5));

            //different message --> return false
            email = new EmailManager();
            email.composeEmail(new MessageDraft());
            email.loginEmail(loginDetails);
            assertFalse(standardEmail.equals(email));

            //different login --> return false
            email.composeEmail(message);
            String[] loginDetails2 = {"bernice@gmail.com", "password"};
            email.loginEmail(loginDetails2);
            assertFalse(standardEmail.equals(email));

        } catch (EmailLoginInvalidException e) {
            assert false : "shouldn't hit this test case";
        }

    }
}
```
###### \java\seedu\address\email\EmailSendTest.java
``` java
public class EmailSendTest {
    private EmailSend emailSend = new EmailSend();

    @Test
    public void sendEmail() {
        EmailCompose emailCompose = new EmailCompose();
        EmailLogin emailLogin = new EmailLogin();

        //email draft does not have message and subject
        try {
            MessageDraft messageDraft = new MessageDraft();
            emailCompose.composeEmail(messageDraft);
            emailSend.sendEmail(emailCompose, emailLogin);
        } catch (EmailMessageEmptyException e) {
            assert true : "error was hit as expected";
        } catch (Exception e) {
            assert false : "error was hit unexpectedly";
        }

        //user is not logged in
        try {
            String [] loginDetails = {"adam@yahoo.com", "password"};
            emailLogin.loginEmail(loginDetails);
            emailSend.sendEmail(emailCompose, emailLogin);
        } catch (EmailLoginInvalidException e) {
            assert true : "error was hit as expected";
        } catch (Exception e) {
            assert false : "error was hit unexpectedly";
        }
    }
}
```
###### \java\seedu\address\email\message\DraftListTest.java
``` java
public class DraftListTest {
    private DraftList draftList = new DraftList();

    @Test
    public void composeEmail() {
        MessageDraft message;

        //Empty Message Draft
        message = new MessageDraft();
        draftList.composeEmail(message);
        assertEquals(message, draftList.getMessage(0));

        //Draft with only message filed
        message = new MessageDraft();
        message.setMessage("HELLO");
        draftList.composeEmail(message);
        assertEquals(message, draftList.getMessage(0));

        //Draft with only subject filled
        message = new MessageDraft();
        message.setSubject("SUBJECT");
        draftList.composeEmail(message);
        assertEquals(message, draftList.getMessage(0));

        //Draft with both message and email filled
        message = new MessageDraft("message", "subject");
        draftList.composeEmail(message);
        assertEquals(message, draftList.getMessage(0));

    }

    @Test
    public void equals() {
        //Set up expected EmailCompose
        DraftList standardDraftList = new DraftList();
        MessageDraft message = new MessageDraft("adam@gmail.com", "password");
        standardDraftList.composeEmail(message);

        //same values --> returns true
        DraftList draftList = new DraftList();
        draftList.composeEmail(message);
        assertTrue(standardDraftList.equals(draftList));

        //same object --> returns true
        assertTrue(standardDraftList.equals(standardDraftList));

        //null --> returns false
        assertFalse(standardDraftList.equals(null));

        //different type --> return false
        assertFalse(standardDraftList.equals(5));

        //different message --> return false
        draftList = new DraftList();
        draftList.composeEmail(new MessageDraft("sam@gmail.com", "password"));
        assertFalse(standardDraftList.equals(draftList));

        //different subject --> return false
        draftList = new DraftList();
        draftList.composeEmail(new MessageDraft("adam@gmail.com", "password123"));
        assertFalse(standardDraftList.equals(draftList));
    }
}
```
###### \java\seedu\address\email\message\MessageDraftTest.java
``` java
public class MessageDraftTest {

    private MessageDraft message;

    @Test
    public void containsContent() {
        //blank message and subject
        message = new MessageDraft();
        assertFalse(message.containsContent());

        //blank message only
        message.setSubject("new subject");
        assertFalse(message.containsContent());

        //blank subject only
        message = new MessageDraft();
        message.setMessage("new Message");
        assertFalse(message.containsContent());

        //both message and subject are not blank
        message = new MessageDraft();
        message.setSubject("new subject");
        message.setMessage("new Message");
        assertTrue(message.containsContent());
    }

    @Test
    public void equals() {
        final MessageDraft standardMessageDraft = new MessageDraft("Hello", "Subject");
        InternetAddress[] recipientsEmail = new InternetAddress[2];
        try {
            recipientsEmail[0] = new InternetAddress("ben@gmail.com");
            recipientsEmail[1] = new InternetAddress("adam@gmail.com");
            standardMessageDraft.setRecipientsEmail(recipientsEmail);
        } catch (Exception e) {
            assert false : "The internet address should be valid";
        }

        MessageDraft message;

        //no values
        assertFalse(standardMessageDraft.equals(new MessageDraft()));

        //only message same
        message = new MessageDraft("Hello", "");
        assertFalse(standardMessageDraft.equals(message));

        //only subject same
        message = new MessageDraft("", "Subject");
        assertFalse(standardMessageDraft.equals(message));

        //only recipients list same
        message = new MessageDraft();
        message.setRecipientsEmail(recipientsEmail);
        assertFalse(standardMessageDraft.equals(message));

        //only message and subject same
        message = new MessageDraft("Hello", "Subject");
        assertFalse(standardMessageDraft.equals(message));

        //only message and recipients list same
        message = new MessageDraft("Hello", "");
        message.setRecipientsEmail(recipientsEmail);
        assertFalse(standardMessageDraft.equals(message));

        //only subject and recipients list same
        message = new MessageDraft("", "Subject");
        message.setRecipientsEmail(recipientsEmail);
        assertFalse(standardMessageDraft.equals(message));

        //message, subject and recipients list same
        message = new MessageDraft("Hello", "Subject");
        message.setRecipientsEmail(recipientsEmail);
        assertTrue(standardMessageDraft.equals(message));

        //message, subject and recipients list all wrong
        message = new MessageDraft("ELLO", "ubject");
        try {
            recipientsEmail[0] = new InternetAddress("james@gmail.com");
            recipientsEmail[1] = new InternetAddress("alice@gmail.com");
            message.setRecipientsEmail(recipientsEmail);
        } catch (Exception e) {
            assert false : "The internet address should be valid";
        }
        assertFalse(standardMessageDraft.equals(message));
    }
}
```
###### \java\seedu\address\logic\commands\EmailCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code EmailCommand}.
 */
public class EmailCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model;
    private Predicate<ReadOnlyPerson> predicateShowNoPerson = unused -> false;

    @Before
    public void setUp() {
        ImageInit.checkDirectories();
        ImageInit.initPictures();
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new EmailManager());
    }

    @After
    public void recovery() {
        ImageInit.deleteEditedFiles();
        ImageInit.deleteImagesFiles();
    }

    @Test
    public void email_sendingFail_emailMessageEmptyException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingEmailMessageEmptyException();

        thrown.expect(CommandException.class);
        thrown.expectMessage(EmailCommand.MESSAGE_EMPTY_INVALID);

        String [] loginDetails = {"adam@gmail.com", "password"};
        EmailTask task = new EmailTask("send");
        getEmailCommand("", "", loginDetails, task, modelStub).execute();
    }

    @Test
    public void email_sendingFail_emailLoginInvalidException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingEmailLoginInvalidException();

        thrown.expect(CommandException.class);
        thrown.expectMessage(EmailCommand.MESSAGE_LOGIN_INVALID);

        EmailTask task = new EmailTask("send");

        //Non Gmail Login --> Throws exception
        String [] loginDetail1 = {"adam@yahoo.com", "password"};
        getEmailCommand("", "", loginDetail1, task, modelStub).execute();

        //Invalid Login Details Only accepts 2 fields --> Throws exception
        String [] loginDetail2 = {"adam@yahoo.com", "password", "hello"};
        getEmailCommand("", "", loginDetail2, task, modelStub).execute();

        //Invalid Login Details --> Throws exception
        String [] loginDetail3 = {"password", "adam@gmail.com"};
        getEmailCommand("", "", loginDetail3, task, modelStub).execute();
    }

    @Test
    public void email_sendingFail_emailRecipientsEmptyException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingEmailRecipientsEmptyException();

        thrown.expect(CommandException.class);
        thrown.expectMessage(EmailCommand.MESSAGE_RECIPIENT_INVALID);

        String [] loginDetails = {"adam@gmail.com", "password"};
        EmailTask task = new EmailTask("send");
        getEmailCommand("", "", loginDetails, task, modelStub).execute();
    }

    @Test
    public void email_sendingFail_authenticationFailedException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingAuthenticationFailedException();

        thrown.expect(CommandException.class);
        thrown.expectMessage(EmailCommand.MESSAGE_AUTHENTICATION_FAIL);

        String [] loginDetails = {"adam@gmail.com", "password"};
        EmailTask task = new EmailTask("send");
        getEmailCommand("", "", loginDetails, task, modelStub).execute();
    }

    @Test
    public void execute_emailClear_success() throws Exception {
        String [] loginDetails = {"adam@gmail.com", "password"};
        EmailTask task = new EmailTask("clear");
        EmailCommand emailCommand = getEmailCommand("message", "subject", loginDetails, task, model);

        String expectedMessage = String.format(EmailCommand.MESSAGE_SUCCESS, "cleared.");

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), model.getEmailManager());
        expectedModel.clearEmailDraft();

        assertCommandSuccess(emailCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_emailDraft_success() throws Exception {
        String [] loginDetails = {"adam@gmail.com", "password"};
        EmailTask task = new EmailTask();
        EmailCommand emailCommand = getEmailCommand("message", "subject", loginDetails, task, model);

        String expectedMessage = String.format(EmailCommand.MESSAGE_SUCCESS,
                "drafted.\nYou are logged in to " + loginDetails[0]);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), model.getEmailManager());
        expectedModel.draftEmail(new MessageDraft("message", "subject"));

        assertCommandSuccess(emailCommand, model, expectedMessage, expectedModel);
    }

    /** =========== Model Stub that always throws exceptions ============================ */

    /**
     * A Model stub that always throw a EmailMessageEmptyException when trying to send email.
     */
    private class ModelStubThrowingEmailMessageEmptyException extends ModelStub {
        @Override
        public void sendEmail(MessageDraft message) throws EmailLoginInvalidException,
                EmailMessageEmptyException, EmailRecipientsEmptyException, AuthenticationFailedException {
            throw new EmailMessageEmptyException();
        }

        @Override
        public void loginEmail(String [] loginDetails) throws EmailLoginInvalidException {
            model.loginEmail(loginDetails);
        }

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            return model.getFilteredPersonList();
        }
    }

    /**
     * A Model stub that always throw a EmailLoginInvalidException when trying to send email.
     */
    private class ModelStubThrowingEmailLoginInvalidException extends ModelStub {
        @Override
        public void sendEmail(MessageDraft message) throws EmailLoginInvalidException,
                EmailMessageEmptyException, EmailRecipientsEmptyException, AuthenticationFailedException {
            throw new EmailLoginInvalidException();
        }

        @Override
        public void loginEmail(String [] loginDetails) throws EmailLoginInvalidException {
            model.loginEmail(loginDetails);
        }

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            return model.getFilteredPersonList();
        }
    }

    /**
     * A Model stub that always throw a EmailRecipientsEmptyException when trying to send email.
     */
    private class ModelStubThrowingEmailRecipientsEmptyException extends ModelStub {
        @Override
        public void sendEmail(MessageDraft message) throws EmailLoginInvalidException,
                EmailMessageEmptyException, EmailRecipientsEmptyException, AuthenticationFailedException {
            throw new EmailRecipientsEmptyException();
        }

        @Override
        public void loginEmail(String [] loginDetails) throws EmailLoginInvalidException {
            model.loginEmail(loginDetails);
        }

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            model.updateFilteredPersonList(predicateShowNoPerson);
            return model.getFilteredPersonList();
        }
    }

    /**
     * A Model stub that always throw a AuthenticationFailedException when trying to send email.
     */
    private class ModelStubThrowingAuthenticationFailedException extends ModelStub {
        @Override
        public void sendEmail(MessageDraft message) throws EmailLoginInvalidException,
                EmailMessageEmptyException, EmailRecipientsEmptyException, AuthenticationFailedException {
            throw new AuthenticationFailedException();
        }

        @Override
        public void loginEmail(String [] loginDetails) throws EmailLoginInvalidException {
            model.loginEmail(loginDetails);
        }

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            model.updateFilteredPersonList(predicateShowNoPerson);
            return model.getFilteredPersonList();
        }
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public String addImage(EmailAddress email, Photo photo) throws IOException {
            fail("This method should not be called.");
            return "";
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void backupAddressBook() {
            fail("This method should not be called.");
        }

        @Override
        public Email getEmailManager() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
                throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonListBirthdate() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void sortFilteredPersons(int sortOrder) {
            fail("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void deleteTag(Tag tag) {
            fail("This method should not not be called.");
        }

        @Override
        public void loginEmail(String [] loginDetails) throws EmailLoginInvalidException {
            fail("This method should not be called.");
        }

        @Override
        public void sendEmail(MessageDraft message) throws EmailLoginInvalidException,
                EmailMessageEmptyException, EmailRecipientsEmptyException, AuthenticationFailedException {
            fail("This method should not be called.");
        }

        @Override
        public String getEmailStatus() {
            fail("This method should not be called.");
            return "";
        }

        @Override
        public void clearEmailDraft() {
            fail("This method should not be called.");
        }

        @Override
        public void draftEmail(MessageDraft message) {
            fail("This method should not be called.");
        }

    }

    /** ================== End of Model Stub ========================================= */

    /**
     * Generates a new EmailCommand with the details of the given message.
     */
    private EmailCommand getEmailCommand(String message, String subject,
                                         String [] loginDetails, EmailTask task, Model model) {
        EmailCommand command = new EmailCommand(message, subject, loginDetails, task);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    @Test
    public void equals() {
        String [] loginDetails = {"adam@gmail.com:password"};
        EmailTask task = new EmailTask("send");

        EmailCommand emailFirstCommand = new EmailCommand("Hello", "subject",
                loginDetails, task);

        // same object -> returns true
        assertTrue(emailFirstCommand.equals(emailFirstCommand));

        // same values -> returns true
        EmailCommand emailFirstCommandcopy = new EmailCommand("Hello", "subject",
                loginDetails, task);
        assertTrue(emailFirstCommand.equals(emailFirstCommandcopy));

        // different types -> returns false
        assertFalse(emailFirstCommand.equals(1));

        // null -> returns false
        assertFalse(emailFirstCommand.equals(null));

        // different person -> returns false
        String [] loginDetails2 = {"bernice@gmail.com:word123"};
        EmailCommand emailSecondCommand = new EmailCommand("ello", "ubject",
                loginDetails2, new EmailTask());
        assertFalse(emailFirstCommand.equals(emailSecondCommand));
    }

}
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_email() throws Exception {
        String message = "message";
        String subject = "subject";
        String [] loginDetails = new String[0];

        //Using Email command
        EmailCommand command = (EmailCommand) parser.parseCommand(EmailCommand.COMMAND_WORD + " em/"
                + message + " es/" + subject);
        assertEquals(new EmailCommand(message, subject, loginDetails, new EmailTask("")), command);

        //Using Email alias
        command = (EmailCommand) parser.parseCommand(EmailCommand.COMMAND_ALIAS + " em/"
                + message + " es/" + subject);
        assertEquals(new EmailCommand(message, subject, loginDetails, new EmailTask("")), command);

    }
```
###### \java\seedu\address\logic\parser\EmailCommandParserTest.java
``` java
public class EmailCommandParserTest {
    private EmailCommandParser parser = new EmailCommandParser();

    @Test
    public void parse_invalidArgs_throwsParseException() {
        String [] loginDetails = {"adam@gmail.com", "password"};

        //zero Augment -> fails
        assertParseFailure(parser, "email", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EmailCommand.MESSAGE_USAGE));

        //Empty Message -> fails
        assertParseFailure(parser, "email em/ ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EmailCommand.MESSAGE_USAGE));

        //Empty Subject -> fails
        assertParseFailure(parser, "email es/", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EmailCommand.MESSAGE_USAGE));

        //Invalid Login Format - Multiple Arguments -fails
        assertParseFailure(parser, "email el/adam@gmail.com:password:testing",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));

        //Invalid Login Format - only login - fails
        assertParseFailure(parser, "email el/adam@gamil.com",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));

        //Invalid email task -- fails
        assertParseFailure(parser, "email et/testing",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));

        //Empty email task -- fails
        assertParseFailure(parser, "email et/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emailDraft_success() {
        String [] loginDetails = new String[0];
        EmailTask task = new EmailTask();
        String userInput = "";

        //valid email message
        userInput = "email em/message";
        EmailCommand expectedCommand = new EmailCommand("message", "", loginDetails, task);
        assertParseSuccess(parser, userInput, expectedCommand);

        //valid email subject
        userInput = "email es/subject";
        expectedCommand = new EmailCommand("", "subject", loginDetails, task);
        assertParseSuccess(parser, userInput, expectedCommand);

        //valid email message and subject
        userInput = "email em/message es/subject";
        expectedCommand = new EmailCommand("message", "subject", loginDetails, task);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_emailTask_success() {
        String [] loginDetails = new String[0];
        EmailTask task = new EmailTask("");
        String userInput = "";

        //valid task clear
        task.setTask("clear");
        userInput = "email et/clear";
        EmailCommand expectedCommand = new EmailCommand("", "", loginDetails, task);
        assertParseSuccess(parser, userInput, expectedCommand);

        //valid task send
        task.setTask("send");
        userInput = "email et/send";
        expectedCommand = new EmailCommand("", "", loginDetails, task);
        assertParseSuccess(parser, userInput, expectedCommand);

    }
}
```
###### \java\seedu\address\logic\parser\FindCommandParserTest.java
``` java
    @Test
    public void parse_validArgs_returnsFindCommand() {
        List<String> nameList = (List<String>) Arrays.asList("Alice", "Bob");
        List<String> emptyNameList = (List<String>) Arrays.asList(new String [0]);
        List<String> tagList = (List<String>) Arrays.asList("friends", "Colleagues");
        List<String> emptyTagList = (List<String>) Arrays.asList(new String [0]);

        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(nameList, emptyTagList), 0);
        assertParseSuccess(parser, " n/Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " n/ \n Alice \n \t Bob  \t", expectedFindCommand);

        // Find by Name only
        expectedFindCommand = new FindCommand(
                new NameContainsKeywordsPredicate(nameList, emptyTagList), 0);
        assertParseSuccess(parser, " n/Alice Bob", expectedFindCommand);

        // Find by Tag only
        expectedFindCommand = new FindCommand(
                new NameContainsKeywordsPredicate(emptyNameList, tagList), 0);
        assertParseSuccess(parser, " t/friends Colleagues", expectedFindCommand);

        // Find by both Name and Tag
        expectedFindCommand = new FindCommand(
                new NameContainsKeywordsPredicate(nameList, tagList), 0);
        assertParseSuccess(parser, " n/Alice Bob t/friends Colleagues", expectedFindCommand);

        // Find by tag with sort by email
        expectedFindCommand = new FindCommand(
                new NameContainsKeywordsPredicate(emptyNameList, tagList), 2);
        assertParseSuccess(parser, " t/friends Colleagues s/tag", expectedFindCommand);

    }

}
```
###### \java\seedu\address\logic\parser\ParserUtilTest.java
``` java
    @Test
    public void parseEmailDraft_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseEmailMessage(null);
        ParserUtil.parseEmailSubject(null);
        ParserUtil.parseLoginDetails(null);
    }

```
###### \java\seedu\address\logic\parser\ParserUtilTest.java
``` java
    @Test
    public void parseEmailDraft_emptyString_returnsEmptyString() throws Exception {
        assertTrue(ParserUtil.parseEmailMessage(Optional.of(EMPTY_STRING)).trim().isEmpty());
        assertTrue(ParserUtil.parseEmailSubject(Optional.of(EMPTY_STRING)).trim().isEmpty());
        assertTrue(ParserUtil.parseLoginDetails(Optional.of(EMPTY_STRING)).trim().isEmpty());
    }

```
###### \java\seedu\address\logic\parser\ParserUtilTest.java
``` java
    @Test
    public void parseEmailDraft_validArgs_returnsMessage() throws Exception {
        //Expected Email Message
        String expectedMessage = VALID_EMAIL_MESSAGE;
        String message = ParserUtil.parseEmailMessage(Optional.of(VALID_EMAIL_MESSAGE));

        //Expected Email Subject
        String expectedSubject = VALID_EMAIL_SUBJECT;
        String subject = ParserUtil.parseEmailSubject(Optional.of(VALID_EMAIL_SUBJECT));

        //Expected Email Login Details
        String expectedLoginDetails = VALID_EMAIL_LOGIN;
        String loginDetails = ParserUtil.parseEmailSubject(Optional.of(VALID_EMAIL_LOGIN));

        //Verifies if all argument are parsed correctly.
        assertEquals(expectedMessage, message);
        assertEquals(expectedSubject, subject);
        assertEquals(expectedLoginDetails, loginDetails);
    }

```
###### \java\seedu\address\logic\parser\ParserUtilTest.java
``` java
    @Test
    public void parseSortOrder() {
        int result;

        try {
            //assertEqual -1 if String empty
            result = ParserUtil.parseSortOrder(Optional.of(""));
            assertEquals(-1, result);

            //assertEqual 0 if String name
            result = ParserUtil.parseSortOrder(Optional.of("name"));
            assertEquals(0, result);

            //assertEqual 1 if String tag
            result = ParserUtil.parseSortOrder(Optional.of("tag"));
            assertEquals(1, result);

            //assertEqual 2 if String email
            result = ParserUtil.parseSortOrder(Optional.of("email"));
            assertEquals(2, result);

            //assertEqual 3 if String address
            result = ParserUtil.parseSortOrder(Optional.of("address"));
            assertEquals(3, result);

            //assertEqual -1 if String invalid
            result = ParserUtil.parseSortOrder(Optional.of("phone"));
            assertEquals(-1, result);

        } catch (IllegalValueException e) {
            assert false : "Should not hit this at all";
        }
    }
}
```
###### \java\seedu\address\ui\LeftDisplayPanelTest.java
``` java
/**
 * Test the Left Display Panel Toggle Tabs for the UI
 */
public class LeftDisplayPanelTest extends GuiUnitTest {

    private LeftDisplayPanelHandle leftDisplayPanelHandle;
    private LeftDisplayPanel leftDisplayPanel;

    @Before
    public void setUp() {
        Model model = new ModelManager();
        Logic logic = new LogicManager(model);

        leftDisplayPanel = new LeftDisplayPanel(
                logic.getFilteredPersonList(), logic.getFilteredPersonListBirthdate());
        uiPartRule.setUiPart(leftDisplayPanel);

        leftDisplayPanelHandle = new LeftDisplayPanelHandle(getChildNode(leftDisplayPanel.getRoot(),
                LeftDisplayPanelHandle.LEFT_DISPLAY_ID));
    }

    @Test
    public void display() {
        // default tab
        leftDisplayPanelHandle.toggle(0);
        assertEquals(0, leftDisplayPanelHandle.getSelectedTabIndex());

        //Set to Tab 3
        guiRobot.pauseForHuman();
        leftDisplayPanel.toggleTabs(2);
        assertEquals(2, leftDisplayPanelHandle.getSelectedTabIndex());

        //Toggle Shortcut Tab 1
        guiRobot.pauseForHuman();
        leftDisplayPanel.toggleTabs(-1);
        assertEquals(0, leftDisplayPanelHandle.getSelectedTabIndex());

        //Toggle shortcut to Tab 2
        guiRobot.pauseForHuman();
        leftDisplayPanel.toggleTabs(-1);
        assertEquals(1, leftDisplayPanelHandle.getSelectedTabIndex());
    }
```
###### \java\seedu\address\ui\MessageDisplayTest.java
``` java
/**
 * UI Test for Message Display
 */
public class MessageDisplayTest extends GuiUnitTest {
    private static final EmailDraftChangedEvent NEW_RESULT_EVENT_STUB =
            new EmailDraftChangedEvent(new MessageDraft("message", "subject"));

    private EmailMessageDisplayHandle messageDisplayHandle;
    private EmailSubjectDisplayHandle subjectDisplayHandle;
    private EmailRecipientsDisplayHandle recipientsDisplayHandle;

    @Before
    public void setUp() {
        MessageDisplay messageDisplay = new MessageDisplay();
        uiPartRule.setUiPart(messageDisplay);

        messageDisplayHandle = new EmailMessageDisplayHandle(getChildNode(messageDisplay.getRoot(),
                EmailMessageDisplayHandle.MESSAGE_DISPLAY_ID));
        subjectDisplayHandle = new EmailSubjectDisplayHandle(getChildNode(messageDisplay.getRoot(),
                EmailSubjectDisplayHandle.SUBJECT_DISPLAY_ID));
        recipientsDisplayHandle = new EmailRecipientsDisplayHandle(getChildNode(messageDisplay.getRoot(),
                EmailRecipientsDisplayHandle.RECIPIENTS_DISPLAY_ID));
    }

    @Test
    public void display() {
        // default result text
        guiRobot.pauseForHuman();
        assertEquals("", messageDisplayHandle.getText());
        assertEquals("", subjectDisplayHandle.getText());
        assertEquals("", recipientsDisplayHandle.getText());

        // new result received
        postNow(NEW_RESULT_EVENT_STUB);
        guiRobot.pauseForHuman();
        assertEquals(NEW_RESULT_EVENT_STUB.message.getMessage(), messageDisplayHandle.getText());
        assertEquals(NEW_RESULT_EVENT_STUB.message.getSubject(), subjectDisplayHandle.getText());
        assertEquals(NEW_RESULT_EVENT_STUB.message.getRecipientsEmailtoString(), recipientsDisplayHandle.getText());
    }

}
```
###### \java\systemtests\AddressBookSystemTest.java
``` java
    /**
     * Asserts that the {@code messageDisplay} display {@code expectedMessage}, the {@code subjectDisplay} display
     * {@code expectedSubject}, the {@code RecipientsDisplay} displays {@code expectedRecipients} and the
     * {@LeftDisplayPanelTab} display {@code expectedTabIndex}
     */
    protected void assertEmailDisplayExpected(String expectedMessage, String expectedSubject,
                                              String expectedRecipients, int expectedTabIndex) {
        assertEquals(expectedMessage, getEmailMessageDisplay().getText());
        assertEquals(expectedSubject, getEmailSubjectDisplay().getText());
        assertEquals(expectedRecipients, getEmailRecipientsDisplay().getText());
        assertEquals(expectedTabIndex, getLeftDisplayPanel().getSelectedTabIndex());
    }
```
###### \java\systemtests\EmailCommandSystemTest.java
``` java
public class EmailCommandSystemTest extends AddressBookSystemTest {

    private static final String EMAIL_SUCCESSFULLY_DRAFTED = "drafted.\n";
    private static final String EMAIL_SUCCESSFULLY_CLEARED = "cleared.";
    private static final String EMAIL_NOT_LOGIN_STATUS = "You are not logged in to any Gmail account.";
    private static final String EMAIL_LOGIN_STATUS = "You are logged in to %1$s";
    private static final String EMAIL_COMMAND_SEND = " " + PREFIX_EMAIL_TASK + "send";
    private static final String EMAIL_COMMAND_CLEAR = " " + PREFIX_EMAIL_TASK + "clear";
    private Predicate<ReadOnlyPerson> predicateShowNoPerson = unused -> false;

    @BeforeClass
    public static void setup() throws Exception {
        ImageInit.checkDirectories();
        ImageInit.initPictures();
    }

    @AfterClass
    public static void recovery() throws Exception {
        ImageInit.deleteEditedFiles();
        ImageInit.deleteImagesFiles();
    }

    @Test
    public void sendEmail() throws Exception {
        String command = "";
        MessageDraft message = new MessageDraft();
        String [] loginDetails = new String[0];
        EmailTask task = new EmailTask();

        /**
         * Case: Email Command without any arguments --> invalid
         */
        command = EmailCommand.COMMAND_WORD;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));

        /**
         * Case: Email Command to send email with invalid message --> invalid
         **/
        command = EmailCommand.COMMAND_WORD + INVALID_EMAIL_MESSAGE + EMAIL_COMMAND_SEND;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));

        /**
         * Case: Email Command to send email with empty subject --> invalid
         **/
        command = EmailCommand.COMMAND_WORD + INVALID_EMAIL_SUBJECT + EMAIL_COMMAND_SEND;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));

        /**
         * Case: Email Command to send email with empty message (no message and subject) --> invalid
         */
        command = EmailCommand.COMMAND_WORD + EMAIL_COMMAND_SEND;
        assertCommandFailure(command, EmailCommand.MESSAGE_EMPTY_INVALID);

        /**
         * Case: Email Command to send email with invalid login parameters --> invalid
         **/
        command = EmailCommand.COMMAND_WORD + INVALID_EMAIL_LOGIN_LENGTH + EMAIL_COMMAND_SEND;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));

        /**
         * Case: Email Command to send email with a non gmail account --> invalid
         **/
        command = EmailCommand.COMMAND_WORD + EMAIL_MESSAGE + EMAIL_SUBJECT + INVALID_EMAIL_LOGIN + EMAIL_COMMAND_SEND;
        assertCommandFailure(command, EmailCommand.MESSAGE_LOGIN_INVALID);

        /**
         * Case: Email Commmand to draft email with only message parameter --> success
         **/
        command = EmailCommand.COMMAND_WORD + EMAIL_MESSAGE;
        message = new MessageDraft(VALID_EMAIL_MESSAGE, "");
        assertCommandSucess(command, message, loginDetails, task, EMAIL_SUCCESSFULLY_DRAFTED
                + EMAIL_NOT_LOGIN_STATUS);

        /**
         * Case: Email Commmand to draft email with empty message parameter --> invalid
         **/
        command = EmailCommand.COMMAND_WORD + INVALID_EMAIL_MESSAGE;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));

        /**
         * Case: Email Commmand to draft email with only subject parameter --> success
         **/
        command = EmailCommand.COMMAND_WORD + EMAIL_SUBJECT;
        message = new MessageDraft("", VALID_EMAIL_SUBJECT);
        assertCommandSucess(command, message, loginDetails, task, EMAIL_SUCCESSFULLY_DRAFTED
                + EMAIL_NOT_LOGIN_STATUS);

        /**
         * Case: Email Commmand to draft email with empty subject parameter --> invalid
         **/
        command = EmailCommand.COMMAND_WORD + INVALID_EMAIL_SUBJECT;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));


        /**
         * Case: Email Commmand to draft email with login parameter --> success
         **/
        command = EmailCommand.COMMAND_WORD + EMAIL_LOGIN;
        message = new MessageDraft();
        loginDetails = VALID_EMAIL_LOGIN.split(":");
        assertCommandSucess(command, message, loginDetails, task, EMAIL_SUCCESSFULLY_DRAFTED
                + String.format(EMAIL_LOGIN_STATUS, loginDetails[0]));

        /**
         * Case: Email Commmand to draft email with invalid login parameter --> invalid
         **/
        command = EmailCommand.COMMAND_WORD + INVALID_EMAIL_LOGIN_LENGTH;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));

        /**
         * Case: Email Command to draft email with a non gmail account --> invalid
         **/
        command = EmailCommand.COMMAND_WORD + INVALID_EMAIL_LOGIN;
        assertCommandFailure(command, EmailCommand.MESSAGE_LOGIN_INVALID);

        /**
         * Case: Email Command to send email with an empty last display list --> invalid
         **/
        command = EmailCommand.COMMAND_WORD + EMAIL_MESSAGE + EMAIL_SUBJECT + EMAIL_LOGIN + EMAIL_COMMAND_SEND;
        assertCommandFailureEmptyList(command, EmailCommand.MESSAGE_RECIPIENT_INVALID);

        /**
         * Case: Email Command to clear current draft --> success
         */
        command = EmailCommand.COMMAND_WORD + EMAIL_COMMAND_CLEAR;
        message = new MessageDraft();
        loginDetails = VALID_EMAIL_LOGIN.split(":");
        task.setTask("clear");
        assertCommandSucess(command, message, loginDetails, task, EMAIL_SUCCESSFULLY_CLEARED);
    }

    private void setupModel(Model expectedModel) throws Exception {
        /**
         *  Set up Model such the list is empty
         */
        ReadOnlyPerson toAdd = new PersonBuilder().withName(VALID_NAME_JOHN_EMAILTESTER)
                .withEmailAddress(VALID_EMAIL_JOHN_EMAILTESTER).build();
        String findCommand = FindCommand.COMMAND_WORD + " " + PREFIX_NAME + VALID_NAME_JOHN_EMAILTESTER;
        ModelHelper.setFilteredList(expectedModel, toAdd);
        executeCommand(findCommand);
    }

    /**
     * Extract Email from last display person {@code lastshownList} into an InternetAddresss[] for sending email
     *
     * @params: last shown display person list
     * @return: list of internet email address
     **/
    private InternetAddress[] extractEmailFromContacts(List<ReadOnlyPerson> lastShownList) throws AddressException {
        InternetAddress [] recipientsEmail = new InternetAddress[lastShownList.size()];
        try {
            for (int i = 0; i < lastShownList.size(); i++) {
                recipientsEmail[i] = new InternetAddress(lastShownList.get(i).getEmailAddress().value);
            }
        } catch (AddressException e) {
            throw new AddressException();
        }
        return recipientsEmail;
    }

    /**
     * Executes the {@code EmailCommand} that sends or draft an email {@code message} and verifies the command box
     * displays the email status results of executing the {@code EmailCommand} and the model related components equal
     * to the current model with the send command composed with email message {@code message}. These verifications are
     * done by {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String,String,Model)}.<br>
     * Also verifies that the command box has the default style class, the status bar's sync status changes, the
            * browser url and selected card remains unchanged.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model).
            */
    private void assertCommandSucess(String command, MessageDraft message, String [] loginDetails,
                                     EmailTask task, String status) throws Exception {
        Model expectedModel = getModel();
        message.setRecipientsEmail(extractEmailFromContacts(expectedModel.getFilteredPersonList()));

        try {
            //Set up Email Details
            expectedModel.loginEmail(loginDetails);
            switch(task.getTask()) {
            case "send":
                expectedModel.sendEmail(message);
                break;
            case "clear":
                expectedModel.clearEmailDraft();
                break;
            default:
                expectedModel.draftEmail(message);
                break;
            }
        } catch (EmailLoginInvalidException e) {
            throw new IllegalArgumentException(EmailCommand.MESSAGE_LOGIN_INVALID);
        } catch (EmailMessageEmptyException e) {
            throw new IllegalArgumentException(EmailCommand.MESSAGE_EMPTY_INVALID);
        } catch (EmailRecipientsEmptyException e) {
            throw new IllegalArgumentException(EmailCommand.MESSAGE_RECIPIENT_INVALID);
        } catch (AuthenticationFailedException e) {
            throw new IllegalArgumentException(EmailCommand.MESSAGE_AUTHENTICATION_FAIL);
        } catch (RuntimeException e) {
            throw new IllegalArgumentException(EmailCommand.MESSAGE_FAIL_UNKNOWN);
        }
        String expectedResultMessage = String.format(EmailCommand.MESSAGE_SUCCESS, status);

        assertCommandSuccess(command, expectedModel, expectedModel.getEmailManager().getEmailDraft(),
                expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Message, String [], boolean)} except
     * that the result display box displays {@code expectedResultMessage}, the model related components equal to
     * {@code expectedModel} and the email tab displays {@code expectedMessage}.
     * @see EmailCommandSystemTest#
     * assertCommandSuccess(String, MessageDraft, String [], boolean)
     */
    private void assertCommandSuccess(String command, Model expectedModel, ReadOnlyMessageDraft expectedDraft,
                                      String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertEmailDisplayExpected(expectedDraft.getMessage(), expectedDraft.getSubject(),
                expectedDraft.getRecipientsEmailtoString(), 1);
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
    private void assertCommandFailure(String command, String expectedResultMessage) throws Exception {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, String)} except
     * that the displayed person list is empty.
     * @see EmailCommandSystemTest#
     * assertCommandSuccess(String, String)
     */
    private void assertCommandFailureEmptyList(String command, String expectedResultMessage) throws Exception {
        Model expectedModel = getModel();
        setupModel(expectedModel);

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
```
