# danielweide
###### \java\seedu\address\commons\events\ui\QrEvent.java
``` java
/**
 * Represents a selection change in the Qr Event
 */
public class QrEvent extends BaseEvent {
    private ReadOnlyPerson person;
    public QrEvent(ReadOnlyPerson person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
    public ReadOnlyPerson getPerson() {
        return person;
    }
}
```
###### \java\seedu\address\logic\commands\ClearLogCommand.java
``` java
public class ClearLogCommand extends Command {
    public static final String COMMAND_WORD = "clearlog";
    public static final String COMMAND_ALIAS = "cl";
    public static final String MESSAGE_SUCCESS = "ConnectUs.txt log has been cleared!";

    /**
     * Output Results After "Clearing" ConnectUsLog.txt file
     */
    public CommandResult execute() throws CommandException, IOException {
        File file = new File("ConnectUsLog.txt");
        file.delete();
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
```
###### \java\seedu\address\logic\commands\LoggingCommand.java
``` java
public class LoggingCommand {
    /**
     * Writes onto ConnectUsLog.txt field when new action is executed
     */
    public void keepLog(String logText, String functionType) {
        try (FileWriter fileWrite = new FileWriter("ConnectUsLog.txt", true);
             BufferedWriter buffWriter = new BufferedWriter(fileWrite);
             PrintWriter out = new PrintWriter(buffWriter)) {
            out.println(functionType + "\t" + logText + "\t" + LocalDateTime.now() + "\n");
        } catch (IOException e) {
            System.out.println("Error With ConnectUs.txt Logging");
        }
    }
    /**
     * Starts recording activity the moment application starts up
     */
    public void startUpLog() {
        try (FileWriter fileWrite = new FileWriter("ConnectUsLog.txt", true);
             BufferedWriter buffWriter = new BufferedWriter(fileWrite);
             PrintWriter out = new PrintWriter(buffWriter)) {
            out.println("Application Started on " + LocalDateTime.now());
        } catch (IOException e) {
            System.out.println("Error With ConnectUs.txt Logging");
        }
    }
}
```
###### \java\seedu\address\logic\commands\QrCallCommand.java
``` java
public class QrCallCommand extends Command {

    public static final String COMMAND_WORD = "qrcall";
    public static final String COMMAND_ALIAS = "qc";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Select Person based on Index to generate QR Code for calling\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_SELECT_PERSON_SUCCESS = "Generated Qr for Selected Person: %1$s";

    private final Index targetIndex;

    public QrCallCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        int indexOfPersonInList = targetIndex.getOneBased() - 1;
        EventsCenter.getInstance().post(new QrEvent(lastShownList.get(indexOfPersonInList)));
        return new CommandResult(String.format(MESSAGE_SELECT_PERSON_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof QrCallCommand // instanceof handles nulls
                && this.targetIndex.equals(((QrCallCommand) other).targetIndex)); // state check
    }

}
```
###### \java\seedu\address\logic\commands\QrGenCallCommand.java
``` java
public class QrGenCallCommand {
    /**
     * Generate PhoneCall QRCode
     */
    public String qrCall(String phoneNum) {
        String qrCodeA = "http://";
        String qrCodeB = "api.qrserver.com/";
        String qrCodeC = "v1/";
        String qrCodeD = "create-qr-code/";
        String qrCodeE = "?color=000000";
        String qrCodeF = "&bgcolor=FFFFFF";
        String qrCodeG = "&data";
        String qrCodeH = "=tel";
        String qrCodeI = "%3A";
        String qrCodeJ = "&qzone";
        String qrCodeK = "=1";
        String qrCodeL = "&margin";
        String qrCodeM = "=0";
        String qrCodeN = "&size";
        String qrCodeO = "=500x500";
        String qrCodeP = "&ecc";
        String qrCodeQ = "=L";
        String qrLineA = qrCodeA + qrCodeB + qrCodeC + qrCodeD + qrCodeE + qrCodeF
                + qrCodeG + qrCodeH + qrCodeI;
        String qrLineB = qrCodeJ + qrCodeK + qrCodeL + qrCodeM + qrCodeN + qrCodeO
                + qrCodeP + qrCodeQ;
        String fullQr = qrLineA + phoneNum + qrLineB;
        return fullQr;
    }
}
```
###### \java\seedu\address\logic\commands\QrGenSaveContactCommand.java
``` java
public class QrGenSaveContactCommand {
    /**
     * Generates SaveContact QR Link in forms of String
     */
    public String qrSaveContact(String phoneNum, String contactName, String contactEmail) {
        String qrA = "http://";
        String qrB = "api.qrserver.com/";
        String qrC = "v1/";
        String qrD = "create-qr-code/";
        String qrE = "?color=000000";
        String qrF = "&bgcolor=FFFFFF";
        String qrG = "&data=BEGIN";
        String qrH = "%3AVCARD";
        String qrI = "%0AVERSION";
        String qrJ = "%3A2.1%0";
        String qrK = "AFN%3A";
        String lineA = qrA + qrB + qrC + qrD + qrE + qrF + qrG + qrH + qrI
                + qrJ + qrK;
        String newName = contactName.replace(' ', '+');
        String lineB = "%0AN%3A%3B";
        String qrL = "%0ATEL";
        String qrM = "%3BWORK";
        String qrN = "%3BVOICE%3A";
        String lineC = qrL + qrM + qrN;
        String qrO = "%0AEMAIL";
        String qrP = "%3BWORK";
        String qrQ = "%3BINTERNET%3A";
        String lineD = qrO + qrP + qrQ;
        String qrCodeA = "%0AEND";
        String qrCodeB = "%3AVCARD";
        String qrCodeC = "%0A&qzone=1";
        String qrCodeD = "&margin=0";
        String qrCodeE = "&size=500x500";
        String qrCodeF = "&ecc=L";
        String lineE = qrCodeA + qrCodeB + qrCodeC + qrCodeD + qrCodeE + qrCodeF;
        String fullQr = lineA + newName + lineB + newName + lineC + phoneNum + lineD + contactEmail + lineE;
        return fullQr;
    }
}
```
###### \java\seedu\address\logic\commands\QrGenSmsCommand.java
``` java
public class QrGenSmsCommand {
    /**
     * Generates SMS QR Link in forms of String
     */
    public String qrSms(String phoneNum, String contactName) {
        String qrCodeA = "http://";
        String qrCodeB = "api.qrserver.com/";
        String qrCodeC = "v1/";
        String qrCodeD = "create-qr-code/";
        String qrCodeE = "?color=000000";
        String qrCodeF = "&bgcolor=FFFFFF";
        String qrCodeG = "&data";
        String qrCodeH = "=SMSTO";
        String qrCodeI = "%3A";
        String qrCodeJ = "&qzone";
        String qrCodeK = "=1";
        String qrCodeL = "&margin";
        String qrCodeM = "=0";
        String qrCodeN = "&size";
        String qrCodeO = "=500x500";
        String qrCodeP = "&ecc";
        String qrCodeQ = "=L";
        String qrLineA = qrCodeA + qrCodeB + qrCodeC + qrCodeD + qrCodeE + qrCodeF
                + qrCodeG + qrCodeH + qrCodeI;
        String qrLineB = qrCodeI + "Dear+" + contactName + "%2C";
        String qrLineC = qrCodeJ + qrCodeK + qrCodeL + qrCodeM + qrCodeN + qrCodeO
                + qrCodeP + qrCodeQ;
        String fullQr = qrLineA + phoneNum + qrLineB + qrLineC;
        return fullQr;
    }
}
```
###### \java\seedu\address\logic\commands\QrSaveContactCommand.java
``` java
public class QrSaveContactCommand extends Command {

    public static final String COMMAND_WORD = "qrsave";
    public static final String COMMAND_ALIAS = "qrs";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Select Person based on Index to generate QR Code for Contact Saving\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_SELECT_PERSON_SUCCESS = "Generated Contact Saving Qr for Selected Person: %1$s";

    private final Index targetIndex;

    public QrSaveContactCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        int indexOfPersonInList = 0;
        indexOfPersonInList = targetIndex.getOneBased() - 1;
        EventsCenter.getInstance().post(new QrSaveEvent(lastShownList.get(indexOfPersonInList)));
        return new CommandResult(String.format(MESSAGE_SELECT_PERSON_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof QrSaveContactCommand // instanceof handles nulls
                && this.targetIndex.equals(((QrSaveContactCommand) other).targetIndex)); // state check
    }

}
```
###### \java\seedu\address\logic\commands\QrSmsCommand.java
``` java
public class QrSmsCommand extends Command {

    public static final String COMMAND_WORD = "qrsms";
    public static final String COMMAND_ALIAS = "qs";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Select Person based on Index to generate QR Code for SMS\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_SELECT_PERSON_SUCCESS = "Generated SMS Qr for Selected Person: %1$s";

    private final Index targetIndex;

    public QrSmsCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        int indexOfPersonInList;
        indexOfPersonInList = targetIndex.getOneBased() - 1;
        EventsCenter.getInstance().post(new QrSmsEvent(lastShownList.get(indexOfPersonInList)));
        return new CommandResult(String.format(MESSAGE_SELECT_PERSON_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof QrSmsCommand // instanceof handles nulls
                && this.targetIndex.equals(((QrSmsCommand) other).targetIndex)); // state check
    }

}
```
###### \java\seedu\address\logic\parser\QrCallCommandParser.java
``` java
public class QrCallCommandParser implements Parser<QrCallCommand> {
    /**
     * Parses input arguments and creates a new QrCallCommand object
     */
    public QrCallCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new QrCallCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, QrCallCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\logic\parser\QrSaveContactCommandParser.java
``` java
public class QrSaveContactCommandParser implements Parser<QrSaveContactCommand> {
    /**
     * Parses input arguments and creates a new QrSaveContactCommand object
     */
    public QrSaveContactCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new QrSaveContactCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, QrSaveContactCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\logic\parser\QrSmsCommandParser.java
``` java
public class QrSmsCommandParser implements Parser<QrSmsCommand> {
    /**
     * Parses input arguments and creates a new QrSmsCommand object
     */
    public QrSmsCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new QrSmsCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, QrSmsCommand.MESSAGE_USAGE));
        }
    }
}
```
