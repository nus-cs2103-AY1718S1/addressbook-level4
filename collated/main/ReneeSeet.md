# ReneeSeet
###### \java\seedu\address\commons\core\Messages.java
``` java
    public static final String MESSAGE_MASS_CONFIRMATION = "Would you like to email all %1$d persons listed?";
    public static final String MESSAGE_EMAIL_SUCCESS = "Emails are sent successfully";
    public static final String MESSAGE_NOBODY_FOUND = "0 persons found";
```
###### \java\seedu\address\commons\events\external\SendEmailRequestEvent.java
``` java

/**
 * Indicates a request for Sending of Emails
 */

public class SendEmailRequestEvent extends BaseEvent {

    private String subject;
    private String message;
    private String[] recipients;

    public SendEmailRequestEvent(String subject, String message, String[] recipients) {
        this.subject = subject;
        this.message = message;
        this.recipients = recipients;
    }

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }

    public String[] getRecipients() {
        return recipients;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\ui\ClearRequestEvent.java
``` java
/**
 * Indicates a request for Clear of CYNC
 */

public class ClearRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\ui\MassEmailRequestEvent.java
``` java

/**
 * Indicates a request for MassEmailRequest
 */

public class MassEmailRequestEvent extends BaseEvent {

    private final ArrayList<String> emails;

    public MassEmailRequestEvent(ArrayList<String> emails) {
        this.emails = emails;
    }

    public ArrayList<String> getEmailList() {
        return emails;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\external\EmailManager.java
``` java
/**
 * Follows Singleton and Facade design pattern,
 * For Application to interact with the Google Gmail service
 */

public class EmailManager {

    private static final Logger logger = LogsCenter.getLogger(EmailManager.class);

    private static EmailManager instance = null;

    protected EmailManager() {
        registerAsAnEventHandler(this);
    }

    /**
     * Creates an instance of the EmailManager and registers it as an event handler
     * @return The Singleton instance of the EmailManager
     */
    public static EmailManager init() {
        if (instance == null) {
            instance = new EmailManager();
            new GmailApi();
        }
        return instance;
    }

    /**
     * Registers the object as an event handler at the {@link EventsCenter}
     * @param handler usually {@code this}
     */
    protected void registerAsAnEventHandler(Object handler) {
        EventsCenter.getInstance().registerHandler(handler);
    }

    @Subscribe
    public void handleSendEmailRequestEvent(SendEmailRequestEvent event) throws IOException, MessagingException {
        // Build a new authorized API client service.
        try {
            logger.info(LogsCenter.getEventHandlingLogMessage(event));
            Gmail service = GmailApi.getGmailService();
            String user = "me";
            String[] recipients = event.getRecipients();
            for (String s : recipients) {
                MimeMessage email = GmailApi.createEmail(s, user, event.getSubject(), event.getMessage());
                GmailApi.sendMessage(service, user, email);
                logger.info("EMAIL SENT");
            }
            EventsCenter.getInstance().post(new NewResultAvailableEvent(MESSAGE_EMAIL_SUCCESS));
        } catch (IOException e) {
            logger.info("IO");
        } catch (MessagingException d) {
            logger.info("messageException");
        }
    }
}
```
###### \java\seedu\address\external\GmailApi.java
``` java

/** call gmail API * */

public class GmailApi {

    /** Application name. */
    private static final String APPLICATION_NAME =
            "Gmail API Java Quickstart";
    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
            System.getProperty("user.home"), ".credentials/gmail-java-quickstart");
    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory dataStoryFactory;
    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY =
            JacksonFactory.getDefaultInstance();
    /** Global instance of the HTTP transport. */
    private static HttpTransport httpTransport;
    /** Global instance of the scopes required by this quickstart.
     *
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/gmail-java-quickstart
     */
    private static final List<String> SCOPES =
            Arrays.asList(GmailScopes.GMAIL_COMPOSE, GmailScopes.GMAIL_SEND);

    static {
        try {
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            dataStoryFactory = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Creates an authorized Credential object.
     * @return an authorized Credential object.
     * @throws IOException
     */
    public static Credential authorize() throws IOException {
        // Load client secrets.
        InputStream in =
                GmailApi.class.getResourceAsStream("/client_secret.json");
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        httpTransport, JSON_FACTORY, clientSecrets, SCOPES)
                        .setDataStoreFactory(dataStoryFactory)
                        .setAccessType("offline")
                        .build();
        Credential credential = new AuthorizationCodeInstalledApp(
                flow, new LocalServerReceiver()).authorize("user");
        System.out.println(
                "Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        return credential;
    }

    /**
     * Build and return an authorized Gmail client service.
     * @return an authorized Gmail client service
     * @throws IOException
     */
    public static Gmail getGmailService() throws IOException {
        Credential credential = authorize();
        return new Gmail.Builder(httpTransport, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    /**
     * Create a MimeMessage using the parameters provided.
     *
     * @param to email address of the receiver
     * @param from email address of the sender, the mailbox account
     * @param subject subject of the email
     * @param bodyText body text of the email
     * @return the MimeMessage to be used to send email
     * @throws MessagingException
     */
    public static MimeMessage createEmail(String to,
                                          String from,
                                          String subject,
                                          String bodyText)
            throws MessagingException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        MimeMessage email = new MimeMessage(session);

        email.setFrom(new InternetAddress(from));
        email.addRecipient(javax.mail.Message.RecipientType.TO,
                new InternetAddress(to));
        email.setSubject(subject);
        email.setText(bodyText);
        return email;
    }
    /**
     * Send an email from the user's mailbox to its recipient.
     *
     * @param service Authorized Gmail API instance.
     * @param userId User's email address. The special value "me"
     * can be used to indicate the authenticated user.
     * @param emailContent Email to be sent.
     * @return The sent message
     * @throws MessagingException
     * @throws IOException
     */
    public static Message sendMessage(Gmail service,
                                      String userId,
                                      MimeMessage emailContent)
            throws MessagingException, IOException {
        Message message = createMessageWithEmail(emailContent);
        message = service.users().messages().send(userId, message).execute();

        System.out.println("Message id: " + message.getId());
        System.out.println(message.toPrettyString());
        return message;
    }
    /**
     * Create a message from an email.
     *
     * @param emailContent Email to be set to raw of message
     * @return a message containing a base64url encoded email
     * @throws IOException
     * @throws MessagingException
     */
    public static Message createMessageWithEmail(MimeMessage emailContent)
            throws MessagingException, IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        emailContent.writeTo(buffer);
        byte[] bytes = buffer.toByteArray();
        String encodedEmail = Base64.encodeBase64URLSafeString(bytes);
        Message message = new Message();
        message.setRaw(encodedEmail);
        return message;
    }

}
```
###### \java\seedu\address\logic\commands\ClearCommand.java
``` java
        EventsCenter.getInstance().post(new ClearRequestEvent());
```
###### \java\seedu\address\logic\commands\Command.java
``` java
    /**
     * Constructs a feedback message to summarise an operation for mass emailing
     *
     * @param displaySize used to generate summary
     * @return summary message for persons displayed
     */

    public static String getMessageForMassEmail(int displaySize, ArrayList<String> emails) {
        if (displaySize != 0) {
            StringBuilder mess = new StringBuilder(String.format(Messages.MESSAGE_SMS_CONFIRMATION, displaySize));
            mess.append("\n");
            for (String email : emails) {
                mess.append(email);
                mess.append("\n");
            }
            return mess.toString();
        } else {
            return Messages.MESSAGE_NOBODY_FOUND;
        }
    }
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
        JoinDate date = personToEdit.getJoinDate();

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, date, updatedTags);
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
        private JoinDate date;
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
            this.date = toCopy.date;
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
        public void setJoinDate(JoinDate date) {
            this.date = date;
        }

        public JoinDate getJoinDate() {
            return date;
        }

```
###### \java\seedu\address\logic\commands\MassEmailCommand.java
``` java

/**
 * Finds and lists all persons in address book whose tags keywords for group emailing
 * Keyword matching is case-sensitive.
 */

public class MassEmailCommand extends Command {

    public static final String COMMAND_WORD = "email";
    public static final String MESSAGE_SUCCESS = "Listed all required emails";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": To email all persons with tag\n"
             + COMMAND_WORD + " all : To email everyone in CYNC\n"
             + "Parameters: KEYWORD [MORE_KEYWORDS]... \n"
             + "Example: " + COMMAND_WORD + " Sec 2 Sec 3\n";

    private final TagMatchingPredicate predicate;

    public MassEmailCommand(TagMatchingPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        ObservableList<ReadOnlyPerson> allPerson = model.getFilteredPersonList();
        ArrayList<String> emails = new ArrayList<String>();
        for (ReadOnlyPerson person : allPerson) {
            emails.add(person.getEmail().toString());
        }
        EventsCenter.getInstance().post(new MassEmailRequestEvent(emails));
        return new CommandResult(getMessageForMassEmail(allPerson.size(), emails));
    }

    public TagMatchingPredicate getPredicate() {
        return predicate;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MassEmailCommand // instanceof handles nulls
                && this.predicate.equals(((MassEmailCommand) other).predicate)); // state check
    }
}
```
###### \java\seedu\address\logic\parser\AddCommandParser.java
``` java
            JoinDate date = new JoinDate();
            ReadOnlyPerson person = new Person(name, phone, email, address, date, tagList);
```
###### \java\seedu\address\logic\parser\MassEmailParser.java
``` java

/**
 * Parses input arguments and creates a new MassEmailCommand object
 */

public class MassEmailParser implements Parser<MassEmailCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MassCommand
     * and returns an MassCommand object for execution.
     */

    public MassEmailCommand parse(String args)throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MassEmailCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new MassEmailCommand(new TagMatchingPredicate(Arrays.asList(nameKeywords)));
    }
}
```
###### \java\seedu\address\model\person\JoinDate.java
``` java

public class JoinDate {

    public static final String JOINDATE_VALIDATION_REGEX =
            "^([0-2][0-9]||3[0-1])/(0[0-9]||1[0-2])/([0-9][0-9])?[0-9][0-9]$";
    private String joinDate;

    /**
     * Default constructor
     * only used for testing
     */
    public JoinDate(String date) {
        joinDate = date;
    }

    public JoinDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy"); //e.g 16/10/2017
        LocalDate localDate = LocalDate.now();
        joinDate = dtf.format(localDate);
    }

    /**
     * Returns true if a given string is a valid Join Date.
     */
    public static boolean isValidDate(String test) {
        return test.matches(JOINDATE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return joinDate;
    }

}
```
###### \java\seedu\address\model\person\Person.java
``` java
    private ObjectProperty<JoinDate> joinDate;
```
###### \java\seedu\address\model\person\Person.java
``` java
        this.joinDate = new SimpleObjectProperty<>(joinDate);
```
###### \java\seedu\address\model\person\Person.java
``` java
    public JoinDate getJoinDate() {
        return joinDate.get();
    }

    @Override
    public ObjectProperty<JoinDate> joinDateProperty() {
        return joinDate;
    }
```
###### \java\seedu\address\model\person\ReadOnlyPerson.java
``` java
    ObjectProperty<JoinDate> joinDateProperty();
    JoinDate getJoinDate();
```
###### \java\seedu\address\model\person\ReadOnlyPerson.java
``` java
                .append(" Join Date: ")
                .append(getJoinDate())
```
###### \java\seedu\address\model\tag\TagMatchingPredicate.java
``` java
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Tags} matches any of the Tag keywords given.
 * if Tag Keywords is "all", returns true for everyone
 */
public class TagMatchingPredicate implements Predicate<ReadOnlyPerson> {

    private final List<String> keytags;

    public TagMatchingPredicate(List<String> keytags) {
        this.keytags = keytags;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        if (keytags.size() == 1 && keytags.contains("all")) {
            return true;
        }
        Set<Tag> tags = person.getTags();
        for (Tag s : tags) {
            for (String key : keytags) {
                if (key.equals(s.tagName)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagMatchingPredicate // instanceof handles nulls
                && this.keytags.equals(((TagMatchingPredicate) other).keytags)); // state check
    }

}
```
###### \java\seedu\address\storage\XmlAdaptedPerson.java
``` java
    @XmlElement(required = true)
    private String date;
```
###### \java\seedu\address\storage\XmlAdaptedPerson.java
``` java
        date = source.getJoinDate().toString();
```
###### \java\seedu\address\storage\XmlAdaptedPerson.java
``` java
        final JoinDate joinDate = new JoinDate(this.date);
```
###### \java\seedu\address\ui\EmailPanel.java
``` java

/**
 * The Email Panel of the App.
 */

public class EmailPanel extends UiPart<Region> {
    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "EmailPanel.fxml";
    private static final Logger logger = LogsCenter.getLogger(EmailPanel.class);

    @FXML
    private TextField emailSubjectBox;
    @FXML
    private TextField recipientsBox;
    @FXML
    private TextArea emailMessage;
    @FXML
    private Button sendButton;

    public  EmailPanel(ArrayList<String> emailList) {
        super(FXML);
        EmailManager.init();
        sendButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                    sendEmail();
                logger.info("SEND BUTTON CLICKED");
            }
            });
        String recipients = appendEmails(emailList);
        recipientsBox.setText(recipients);
        registerAsAnEventHandler(this);
    }

    /**
     * Method returns a string to be displayed in recipientBox
     */
    private String appendEmails(ArrayList<String> emails) {
        StringBuilder emailList = new StringBuilder();
        for (String s: emails) {
            emailList.append(s).append(";");
        }
        return emailList.toString();
    }
    /**
     * Method posts SendEmailRequestEvent
     */
    private void sendEmail() {
        String subject = emailSubjectBox.getText();
        String message = emailMessage.getText();
        String[] recipients = recipientsBox.getText().split(";");
        EventsCenter.getInstance().post(new SendEmailRequestEvent(subject, message, recipients));
    }
}
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    /**
     * Switch to the Email panel.
     */
    @FXML
    public void handleEmail(ArrayList<String> emails) {
        emailPanel = new EmailPanel(emails);
        browserPlaceholder.getChildren().add(emailPanel.getRoot());
        browserPlaceholder.getChildren().setAll(emailPanel.getRoot());
    }

    /**
     * Clear the browser when clear command called
     */
    @FXML
    public void handleClear() {
        browserPlaceholder.getChildren().clear();
    }
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    /**
     * handle the MassEmailRequestEvent to display email panel
     * @param event
     */
    @Subscribe
    private void handleMassEmailEvent(MassEmailRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleEmail(event.getEmailList());
    }
    /**
     * handle the ClearRequestEvent to clear browser placeholder
     * @param event
     */
    @Subscribe
    private void handleClearEvent(ClearRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleClear();
    }
```
###### \java\seedu\address\ui\PersonInfo.java
``` java
    @FXML
    private Label date;
```
###### \java\seedu\address\ui\PersonInfo.java
``` java
        date.setText(JOIN_DATE + person.getJoinDate().toString());
```
###### \resources\view\EmailPanel.fxml
``` fxml

<?import javafx.scene.control.Button?>
<?import javafx.scene.control.TextArea?>
<?import javafx.scene.control.TextField?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.Pane?>
<?import javafx.scene.text.Font?>
<?import javafx.scene.text.Text?>

<HBox alignment="CENTER" xmlns="http://javafx.com/javafx/8.0.121" xmlns:fx="http://javafx.com/fxml/1">
 <Pane fx:id="emailpanel" prefWidth="973.0" xmlns="http://javafx.com/javafx/8.0.121" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <Text layoutX="46.0" layoutY="53.0" strokeType="OUTSIDE" strokeWidth="0.0" text="To:">
         <font>
            <Font size="23.0" />
         </font></Text>
      <TextField fx:id="recipientsBox" layoutX="108.0" layoutY="28.0" prefHeight="45.0" prefWidth="839.0" />
      <Text layoutX="21.0" layoutY="114.0" strokeType="OUTSIDE" strokeWidth="0.0" text="Subject:">
         <font>
            <Font size="23.0" />
         </font></Text>
      <TextField fx:id="emailSubjectBox" layoutX="108.0" layoutY="82.0" prefHeight="45.0" prefWidth="839.0" />
      <TextArea fx:id="emailMessage" layoutX="29.0" layoutY="149.0" prefHeight="433.0" prefWidth="930.0" />
      <Button fx:id="sendButton" layoutX="474.0" layoutY="582.0" mnemonicParsing="false" text="Send" />
   </children>
 </Pane>
</HBox>
```
