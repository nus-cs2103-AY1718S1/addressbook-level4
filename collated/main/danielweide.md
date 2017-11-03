# danielweide
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
    public static final String MESSAGE_SELECT_PERSON_SUCCESS = "Selected Person: %1$s";

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
        QrGenerateCommand qrGenerateCommand = new QrGenerateCommand();
        qrGenerateCommand.qrCall(phoneOfPerson);
        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex));
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
        String qrCodeA = "http://api.qrserver.com/v1/create-qr-code/?color=000000&bgcolor=FFFFFF&data=tel%3A";
        String qrCodeB = "&qzone=1&margin=0&size=150x150&ecc=L";
        String fullQr = qrCodeA + phoneNum + qrCodeB;
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
