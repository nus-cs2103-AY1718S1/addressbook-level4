# danielweide
###### /java/seedu/address/logic/commands/ClearLogCommandTest.java
``` java
/**
 * Test for ClearLogCommand by deleting ConnectUsLog.txt file
 */
public class ClearLogCommandTest {
    @Before
    public void prepareCommand() throws CommandException, IOException {
        ClearLogCommand clearLogCommand = new ClearLogCommand();
        clearLogCommand.execute(); //There would be no more ConnectUs.txt file after this
    }
    @Test
    public void execute_clearConnectUsLogSuccess() {
        assertFalse(new File("ConnectUsLog.txt").isFile());
    }
}
```
###### /java/seedu/address/logic/commands/LoggingCommandTest.java
``` java
public class LoggingCommandTest {
    /**
     * Calls Method that will log into ConnectUsLog.txt when application is running
     */
    @Before
    public void prepareStartUpCommand() throws CommandException, IOException {
        LoggingCommand loggingCommand = new LoggingCommand();
        ClearLogCommand clearLogCommand = new ClearLogCommand();
        clearLogCommand.execute(); //remove ConnectUs.txt file
        loggingCommand.startUpLog(); //create new ConnectUs.txt file
    }
    @Test
    public void execute_checkIfLoggingExist() {

        assertTrue(new File("ConnectUsLog.txt").isFile());

    }
    @Test
    public void execute_checkIfLoggingIsDone() {
        int numOfLines = 0;
        int expectedLines = 1;
        try {
            numOfLines = countLines("ConnectUsLog.txt");
        } catch (Exception e) {
            numOfLines = -1;
        }
        assertEquals(expectedLines, numOfLines);
    }
    /**
     * Count Number of Lines in txt file
     */
    private static int countLines(String filename) throws Exception {
        InputStream inputStream = new BufferedInputStream(new FileInputStream(filename));
        try {
            byte[] reading = new byte[1024];
            int count = 0;
            int readChars = 0;
            boolean empty = true;
            while ((readChars = inputStream.read(reading)) != -1) {
                empty = false;
                for (int i = 0; i < readChars; i++) {
                    if (reading[i] == '\n') {
                        ++count;
                    }
                }
            }
            return (count == 0 && !empty) ? 1 : count;
        } finally {
            inputStream.close();
        }
    }
}
```
###### /java/seedu/address/logic/commands/QrCallCommandTest.java
``` java
/**
 * Test For QrCallCommand for Equal Cases
 */
public class QrCallCommandTest {

    @Test
    public void equals() {
        QrCallCommand firstQrCallCommand = new QrCallCommand(INDEX_FIRST_PERSON);
        QrCallCommand secondQrCallCommand = new QrCallCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(firstQrCallCommand.equals(firstQrCallCommand));

        // same values -> returns true
        QrCallCommand firstQrCallCommandCopy = new QrCallCommand(INDEX_FIRST_PERSON);
        assertTrue(firstQrCallCommand.equals(firstQrCallCommandCopy));

        // different types -> returns false
        assertFalse(firstQrCallCommand.equals(1));

        // null -> returns false
        assertFalse(firstQrCallCommand.equals(null));

        // different person -> returns false
        assertFalse(firstQrCallCommand.equals(secondQrCallCommand));
    }
}
```
###### /java/seedu/address/logic/commands/QrGenCallCommandTest.java
``` java
/**
 * Test For QrCallCommand for Equal Cases
 */
public class QrGenCallCommandTest {
    private String phoneNumberOne;
    private String phoneNumberTwo;
    @Before
    public void prepareCommand() {
        phoneNumberOne = "91234567";
        phoneNumberTwo = "81234567";
    }
    @Test
    public void linkCheck() {
        QrGenCallCommand genCallCommand = new QrGenCallCommand();
        String firstQrLink = genCallCommand.qrCall(phoneNumberOne);
        String secondQrLink = genCallCommand.qrCall(phoneNumberTwo);

        // same object -> returns true
        assertTrue(firstQrLink.equals(firstQrLink));

        // same values -> returns true
        String firstQrLinkCopy = genCallCommand.qrCall(phoneNumberOne);
        assertTrue(firstQrLink.equals(firstQrLinkCopy));

        // different types -> returns false
        assertFalse(firstQrLink.equals(1));

        // null -> returns false
        assertFalse(firstQrLink.equals(null));

        // different link -> returns false
        assertFalse(firstQrLink.equals(secondQrLink));
    }
}
```
###### /java/seedu/address/logic/commands/QrGenSaveContactCommandTest.java
``` java
/**
 * Test For QrCallCommand for Equal Cases
 */
public class QrGenSaveContactCommandTest {
    private String phoneNumberOne;
    private String phoneNumberTwo;
    private String nameOne;
    private String nameTwo;
    private String emailOne;
    private String emailTwo;
    @Before
    public void prepareCommand() {
        phoneNumberOne = "91234567";
        phoneNumberTwo = "81234567";
        nameOne = "Daniel";
        nameTwo = "John";
        emailOne = "Daniel@gmail.com";
        emailTwo = "John@gmail.com";
    }
    @Test
    public void linkCheck() {
        QrGenSaveContactCommand genSaveContactCommand = new QrGenSaveContactCommand();
        String firstQrLink = genSaveContactCommand.qrSaveContact(phoneNumberOne, nameOne, emailOne);
        String secondQrLink = genSaveContactCommand.qrSaveContact(phoneNumberTwo, nameTwo, emailTwo);

        // same object -> returns true
        assertTrue(firstQrLink.equals(firstQrLink));

        // same values -> returns true
        String firstQrLinkCopy = genSaveContactCommand.qrSaveContact(phoneNumberOne, nameOne, emailOne);
        assertTrue(firstQrLink.equals(firstQrLinkCopy));

        // different types -> returns false
        assertFalse(firstQrLink.equals(1));

        // null -> returns false
        assertFalse(firstQrLink.equals(null));

        // different link -> returns false
        assertFalse(firstQrLink.equals(secondQrLink));
    }
}
```
###### /java/seedu/address/logic/commands/QrGenSmsCommandTest.java
``` java
/**
 * Test For QrCallCommand for Equal Cases
 */
public class QrGenSmsCommandTest {
    private String phoneNumberOne;
    private String phoneNumberTwo;
    private String nameOne;
    private String nameTwo;
    @Before
    public void prepareCommand() {
        phoneNumberOne = "91234567";
        phoneNumberTwo = "81234567";
        nameOne = "Daniel";
        nameTwo = "John";
    }
    @Test
    public void linkCheck() {
        QrGenSmsCommand genSmsCommand = new QrGenSmsCommand();
        String firstQrLink = genSmsCommand.qrSms(phoneNumberOne, nameOne);
        String secondQrLink = genSmsCommand.qrSms(phoneNumberTwo, nameTwo);

        // same object -> returns true
        assertTrue(firstQrLink.equals(firstQrLink));

        // same values -> returns true
        String firstQrLinkCopy = genSmsCommand.qrSms(phoneNumberOne, nameOne);
        assertTrue(firstQrLink.equals(firstQrLinkCopy));

        // different types -> returns false
        assertFalse(firstQrLink.equals(1));

        // null -> returns false
        assertFalse(firstQrLink.equals(null));

        // different link -> returns false
        assertFalse(firstQrLink.equals(secondQrLink));
    }
}
```
###### /java/seedu/address/logic/commands/QrSaveContactCommandTest.java
``` java
/**
 * Test For QrSaveContactCommand for Equal Cases
 */
public class QrSaveContactCommandTest {

    @Test
    public void equals() {
        QrSaveContactCommand firstQrSaveCommand = new QrSaveContactCommand(INDEX_FIRST_PERSON);
        QrSaveContactCommand secondQrSaveCommand = new QrSaveContactCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(firstQrSaveCommand.equals(firstQrSaveCommand));

        // same values -> returns true
        QrSaveContactCommand firstQrSaveCommandCopy = new QrSaveContactCommand(INDEX_FIRST_PERSON);
        assertTrue(firstQrSaveCommand.equals(firstQrSaveCommandCopy));

        // different types -> returns false
        assertFalse(firstQrSaveCommand.equals(1));

        // null -> returns false
        assertFalse(firstQrSaveCommand.equals(null));

        // different person -> returns false
        assertFalse(firstQrSaveCommand.equals(secondQrSaveCommand));
    }
}
```
###### /java/seedu/address/logic/commands/QrSmsCommandTest.java
``` java
/**
 * Test For QrSmsCommand for Equal Cases
 */
public class QrSmsCommandTest {

    @Test
    public void equals() {
        QrSmsCommand firstQrSmsCommand = new QrSmsCommand(INDEX_FIRST_PERSON);
        QrSmsCommand secondQrSmsCommand = new QrSmsCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(firstQrSmsCommand.equals(firstQrSmsCommand));

        // same values -> returns true
        QrSmsCommand firstQrSmsCommandCopy = new QrSmsCommand(INDEX_FIRST_PERSON);
        assertTrue(firstQrSmsCommand.equals(firstQrSmsCommandCopy));

        // different types -> returns false
        assertFalse(firstQrSmsCommand.equals(1));

        // null -> returns false
        assertFalse(firstQrSmsCommand.equals(null));

        // different person -> returns false
        assertFalse(firstQrSmsCommand.equals(secondQrSmsCommand));
    }
}
```
###### /java/seedu/address/logic/parser/QrCallCommandParserTest.java
``` java
/**
 * Test scope: similar to {@code SelectCommandParserTest}.
 * @see SelectCommandParserTest
 */
public class QrCallCommandParserTest {

    private QrCallCommandParser parser = new QrCallCommandParser();

    @Test
    public void parse_validArgs_returnsQrCallCommand() {
        assertParseSuccess(parser, "1", new QrCallCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, QrCallCommand.MESSAGE_USAGE));
    }
}
```
###### /java/seedu/address/logic/parser/QrSaveContactCommandParserTest.java
``` java
/**
 * Test scope: similar to {@code QrSmsCommandParserTest}.
 * @see QrSmsCommandParserTest
 */
public class QrSaveContactCommandParserTest {

    private QrSaveContactCommandParser parser = new QrSaveContactCommandParser();

    @Test
    public void parse_validArgs_returnsQrSmsCommand() {
        assertParseSuccess(parser, "1", new QrSaveContactCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                QrSaveContactCommand.MESSAGE_USAGE));
    }
}
```
###### /java/seedu/address/logic/parser/QrSmsCommandParserTest.java
``` java
/**
 * Test scope: similar to {@code QrCallCommandParserTest}.
 * @see QrCallCommandParserTest
 */
public class QrSmsCommandParserTest {

    private QrSmsCommandParser parser = new QrSmsCommandParser();

    @Test
    public void parse_validArgs_returnsQrSmsCommand() {
        assertParseSuccess(parser, "1", new QrSmsCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, QrSmsCommand.MESSAGE_USAGE));
    }
}
```
