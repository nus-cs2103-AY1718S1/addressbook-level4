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
     *keepLog Method to Write Activity Log To The ConnectUsLog.txt file
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
     * startUpLog Method will record the time when the application starts
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
    public static final String COMMAND_ALIAS = "qr";

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
        String phoneOfPerson = lastShownList.get(indexOfPersonInList).getPhone().toString();
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
###### \java\seedu\address\logic\commands\QrGenerateCommand.java
``` java
public class QrGenerateCommand {
    /**
     * Method to Generate PhoneCall QRCode
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
        String qrCodeO = "=450x450";
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
###### \java\seedu\address\logic\commands\QrSaveContactCommand.java
``` java
public class QrSaveContactCommand {
    /**
     * Method to Generate SaveContact for Phone QRCode
     */
    public String qrSaveContact(String phoneNum, String contactName, String contactAddress, String contactEmail) {

        String qrCodeA = "http://";
        String qrCodeB = "api.qrserver.com/";
        String qrCodeC = "v1/";
        String qrCodeD = "create-qr-code/";
        String qrCodeE = "?color=000000";
        String qrCodeF = "&bgcolor=FFFFFF";
        String qrCodeG = "&data";
        String qrCodeH = "=BEGIN";
        String qrCodeI = "%3A";
        String qrCodeJ = "VCARD";
        String qrCodeK = "%0";
        String qrCodeL = "AVERSION";
        String qrCodeM = "%3A2.1";
        String qrCodeN = "%0AFN";
        String qrCodeO = "%3A";
        String qrLineA = qrCodeA + qrCodeB + qrCodeC + qrCodeD + qrCodeE + qrCodeF
                + qrCodeG + qrCodeH + qrCodeI + qrCodeJ + qrCodeK + qrCodeL
                + qrCodeM + qrCodeN + qrCodeO;

        String qrpartbCodeA = "%0";
        String qrpartbCodeB = "AN";
        String qrpartbCodeC = "%3A";
        String qrpartbCodeD = "%3B";
        String qrLineB = qrpartbCodeA + qrpartbCodeB + qrpartbCodeC + qrpartbCodeD;
        String qrpartcCodeA = "%0";
        String qrpartcCodeB = "ATEL";
        String qrpartcCodeC = "%3BWORK";
        String qrpartcCodeD = "%3BVOICE";
        String qrpartcCodeE = "%3A";
        String qrLineC = qrpartcCodeA + qrpartcCodeB + qrpartcCodeC + qrpartcCodeD + qrpartcCodeE;
        String qrpartdCodeA = "%0";
        String qrpartdCodeB = "AEMAIL";
        String qrpartdCodeC = "%3";
        String qrpartdCodeD = "BWORK";
        String qrpartdCodeE = "%3";
        String qrpartdCodeF = "BINTERNET";
        String qrpartdCodeG = "%3A";
        String qrLineD = qrpartdCodeA + qrpartdCodeB + qrpartdCodeC + qrpartdCodeD + qrpartdCodeE + qrpartdCodeF
                + qrpartdCodeG;
        String qrparteCodeA = "%0";
        String qrparteCodeB = "AORG";
        String qrparteCodeC = "%3A";
        String qrLineE = qrparteCodeA + qrparteCodeB + qrparteCodeC;
        String qrpartfCodeA = "%0";
        String qrpartfCodeB = "AEND";
        String qrpartfCodeC = "%3";
        String qrpartfCodeD = "AVCARD";
        String qrpartfCodeE = "%0A";
        String qrpartfCodeF = "&qzone";
        String qrpartfCodeG = "=1";
        String qrpartfCodeH = "&margin";
        String qrpartfCodeI = "=0";
        String qrpartfCodeJ = "&size";
        String qrpartfCodeK = "400x400";
        String qrpartfCodeL = "&ecc";
        String qrpartfCodeM = "=L";
        String qrLineF = qrpartfCodeA + qrpartfCodeB + qrpartfCodeC + qrpartfCodeD + qrpartfCodeE + qrpartfCodeF
                + qrpartfCodeG + qrpartfCodeH + qrpartfCodeI + qrpartfCodeJ + qrpartfCodeK + qrpartfCodeL
                + qrpartfCodeM;

        String fullQr = qrLineA + contactName + qrLineB + contactName + qrLineC + phoneNum + qrLineD + contactEmail
                + qrLineE + contactAddress + qrLineF;
        return fullQr;
    }
}
```
###### \java\seedu\address\logic\commands\QrSmsCommand.java
``` java
public class QrSmsCommand {
    /**
     * Method to Generate SMS to Contact QRCode
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
        String qrCodeO = "=450x450";
        String qrCodeP = "&ecc";
        String qrCodeQ = "=L";
        String qrLineA = qrCodeA + qrCodeB + qrCodeC + qrCodeD + qrCodeE + qrCodeF
                + qrCodeG + qrCodeH + qrCodeI;
        String qrLineB = "Dear+" + contactName + "%2C";
        String qrLineC = qrCodeJ + qrCodeK + qrCodeL + qrCodeM + qrCodeN + qrCodeO
                + qrCodeP + qrCodeQ;
        String fullQr = qrLineA + phoneNum + qrLineB + qrLineC;
        return fullQr;
    }
}
```
###### \java\seedu\address\logic\parser\QrCallCommandParser.java
``` java
public class QrCallCommandParser implements Parser<QrCallCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the QrCallCommand
     * and returns an QrCallCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
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
