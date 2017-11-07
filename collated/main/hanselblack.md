# hanselblack
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
        Remark updatedRemark = editPersonDescriptor.getRemark().orElse(personToEdit.getRemark());
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
            this.remark = toCopy.remark;
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
        public void setRemark(Remark remark) {
            this.remark = remark;
        }

        public Optional<Remark> getRemark() {
            return Optional.ofNullable(remark);
        }
```
###### \java\seedu\address\logic\commands\EmailCommand.java
``` java
/**
 * Emails the list of contact details to the input email address
 */
public class EmailCommand extends Command {

    public static final String COMMAND_WORD = "email";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Emails the person's contact details identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Email Sent!";

    private static final String MESSAGE_FAILURE = "Email was not sent!";

    private final Index targetIndex;
    private String recipientEmail;

    public EmailCommand(Index targetIndex, String recipientEmail) {
        this.targetIndex = targetIndex;
        this.recipientEmail = recipientEmail;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson person = lastShownList.get(targetIndex.getZeroBased());

        String name = person.getName().fullName;
        String phone = person.getPhone().toString();
        String address = person.getAddress().toString();
        String email = person.getEmail().toString();
        String remark  = person.getRemark().toString();
        String tags = "";
        for (Tag tag :  person.getTags()) {
            tags += tag.tagName + " ";
        }

        String to = recipientEmail;
        // Sender's email ID needs to be mentioned
        String from = "unifycs2103@gmail.com";
        // For Gmail host
        String host = "smtp.gmail.com";
        // Get system properties
        Properties props = System.getProperties();

        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected  PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from, "CS2103CS2103");
                    }
                }
        );

        try {

            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("Unify: Address Book: " + name + "Exported Data");

            MimeBodyPart messageBodyPart = new MimeBodyPart();

            //set the actual message
            messageBodyPart.setContent("<img src='https://github.com/CS2103AUG2017-W11-B4/"
                            + "main/blob/master/docs/images/email_header.png?raw=true'/>"
                            + "<br/><br/><br/><img src='https://github.com/CS2103AUG2017-W11-B4/"
                            + "main/blob/master/docs/images/email_subheader.png?raw=true'/>"
                            + "<br/><br/>"
                            + "<table>"
                            + "<tr><td style=\"height:20px; width:80px; margin:0;\">"
                            + "<b>Name</b></td><td>" + name + "</td></tr>"
                            + "<tr><td style=\"height:20px; width:80px; margin:0;\">"
                            + "<b>Phone</b></td><td>" + phone + "</td></tr>"
                            + "<tr><td style=\"height:20px; width:80px; margin:0;\">"
                            + "<b>Address</b></td><td>" + address + "</td></tr>"
                            + "<tr><td style=\"height:20px; width:80px; margin:0;\">"
                            + "<b>Email</b></td><td>" + email + "</td></tr>"
                            + "<tr><td style=\"height:20px; width:80px; margin:0;\">"
                            + "<b>Remark</b></td><td>" + remark + "</td></tr>"
                            + "<tr><td style=\"height:20px; width:80px; margin:0;\">"
                            + "<b>Tags</b></td><td>" + tags + "</td></tr>"
                            + "</table>",
                    "text/html");

            Multipart multipart = new MimeMultipart();

            //set text message part
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);

            Transport.send(message);
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (MessagingException msg) {
            msg.printStackTrace();
            return new CommandResult(MESSAGE_FAILURE);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailCommand // instanceof handles nulls
                && this.targetIndex.equals(((EmailCommand) other).targetIndex)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\MusicCommand.java
``` java
/**
 * Plays Music with music play command
 * Pause Music with music pause command
 * Stop Music with music stop command
 */
public class MusicCommand extends Command {

    public static final String COMMAND_WORD = "music";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": play/pause/stop music "
            + "of your selected genre.\n"
            + "Parameters: ACTION (must be either play, pause or stop) "
            + "GENRE (must be either pop, dance or classic) \n"
            + "Example: " + COMMAND_WORD + " play classic ";

    private static final String MESSAGE_STOP = "Music Stopped";

    private static String messagePause = "Music Paused";

    private static String messageSuccess = "Music Playing";

    private static MediaPlayer mediaPlayer;

    private static int trackNumber = 1;

    private String command;
    private String genre = "pop";
    private String[] genreList = {"pop", "dance", "classic"};

    public MusicCommand(String command, String genre) {
        this.command = command;
        this.genre = genre;
    }

    public MusicCommand(String command) {
        this.command = command;
    }

    /**
     * Returns whether music is currently playing.
     */
    public static boolean isPlaying() {
        if (mediaPlayer == null) {
            return false;
        }
        return mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING;
    }

    @Override
    public CommandResult execute() {
        boolean genreExist = Arrays.asList(genreList).contains(genre);
        switch (command) {
        case "play":
            if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PAUSED) {
                mediaPlayer.play();
                return new CommandResult(messageSuccess);
            }
            if (genreExist) {
                String musicFile = getClass().getResource("/audio/music/"
                        + genre + trackNumber + ".mp3").toExternalForm();
                messageSuccess = genre.toUpperCase() + " Music Playing";
                if (trackNumber < 2) {
                    trackNumber++;
                } else {
                    trackNumber = 1;
                }
                if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                    mediaPlayer.stop();
                }
                Media sound = new Media(musicFile);
                mediaPlayer = new MediaPlayer(sound);
                mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                mediaPlayer.setVolume(2.0);
                mediaPlayer.play();
                return new CommandResult(messageSuccess);
            } else {
                return new CommandResult(MESSAGE_USAGE);
            }
        case "stop":
            if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                mediaPlayer.stop();
            }
            return new CommandResult(MESSAGE_STOP);
        case "pause":
            if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                mediaPlayer.pause();
            }
            messagePause = genre.toUpperCase() + " Music Paused";
            return new CommandResult(messagePause);
        default:
            return new CommandResult(MESSAGE_USAGE);
        }
    }
}
```
###### \java\seedu\address\logic\commands\RemarkCommand.java
``` java
/**
 * Edits the remark for a person specified in the INDEX
 */
public class RemarkCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "remark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the remark of the person identified "
        + "by the index number used in the last person listing. "
        + "Existing remark will be overwritten by the input.\n"
        + "Parameters: INDEX (must be a positive integer) "
        + PREFIX_REMARK + "[REMARK]\n"
        + "Example: " + COMMAND_WORD + " 1 "
        + PREFIX_REMARK + "Likes to swim.";

    private static final String MESSAGE_ADD_REMARK_SUCCESS = "Added remark to Person: %1$s";
    private static final String MESSAGE_DELETE_REMARK_SUCCESS = "Removed remark from Person: %1$s";
    private static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final Remark remark;

    /**
    * @param index of the person in the filtered person list to edit the remark
    * @param remark of the person
    */
    public RemarkCommand(Index index, Remark remark) {
        requireNonNull(index);
        requireNonNull(remark);

        this.index = index;
        this.remark = remark;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), remark, personToEdit.getAvatar(), personToEdit.getTags());

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }

        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    /**
     * Returns sucess message depends on different scenario such as adding or deleting remarks
     */
    private String generateSuccessMessage(ReadOnlyPerson personToEdit) {
        if (!remark.value.isEmpty()) {
            return String.format(MESSAGE_ADD_REMARK_SUCCESS, personToEdit);
        } else {
            return String.format(MESSAGE_DELETE_REMARK_SUCCESS, personToEdit);
        }
    }

    @Override
    public boolean equals(Object other) {
        //short circuit if same object
        if (other == this) {
            return true;
        }

        //instanceof handles null
        if (!(other instanceof RemarkCommand)) {
            return false;
        }

        //state check
        RemarkCommand e = (RemarkCommand) other;
        return index.equals(e.index) && remark.equals(e.remark);
    }

}
```
###### \java\seedu\address\logic\parser\AddCommandParser.java
``` java
            Remark remark = ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK)).get();
```
###### \java\seedu\address\logic\parser\EditCommandParser.java
``` java
            ParserUtil.editParseRemark(argMultimap.getValue(PREFIX_REMARK)).ifPresent(editPersonDescriptor::setRemark);
```
###### \java\seedu\address\logic\parser\EmailCommandParser.java
``` java
/**
 * Parses input arguments and creates a new EmailCommand object
 */
public class EmailCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the EmailCommand
     * and returns an EmailCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EmailCommand parse(String arguments) throws ParseException {
        String[] args = arguments.trim().split("\\s+");
        if (args.length == 2) {
            String email = args[1];
            try {
                Index index = ParserUtil.parseIndex(args[0]);
                return new EmailCommand(index, email);
            } catch (IllegalValueException ive) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));
            }
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\logic\parser\MusicCommandParser.java
``` java
/**
 * Parses input arguments and creates a new MusicCommand object
 */
public class MusicCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the String of words
     * and returns an MusicCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MusicCommand parse(String arguments) throws ParseException {
        String[] args = arguments.trim().split("\\s+");
        if (args.length == 1) {
            return new MusicCommand(args[0]);
        } else if (args.length == 2) {
            return new MusicCommand(args[0], args[1]);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MusicCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> remark} into an {@code Optional<Remark>} if {@code remark} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Remark> parseRemark(Optional<String> remark) throws IllegalValueException {
        requireNonNull(remark);
        return remark.isPresent() ? Optional.of(new Remark(remark.get())) : Optional.of(new Remark(""));
    }
    /**
     * Parses a {@code Optional<String> remark} into an {@code Optional<Remark>} if {@code remark} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Remark> editParseRemark(Optional<String> remark) throws IllegalValueException {
        requireNonNull(remark);
        return remark.isPresent() ? Optional.of(new Remark(remark.get())) : Optional.empty();
    }
```
###### \java\seedu\address\model\person\Remark.java
``` java
/**
 * Represents a Person's remark in the address book.
 * Guarantees: immutable; is always valid
 **/

public class Remark {

    public static final String MESSAGE_REMARK_CONSTRAINTS = "Person remarks can take any values, can even be blank";

    public final String value;

    public Remark(String remark) {
        requireNonNull(remark);
        this.value = remark;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof Remark && this.value.equals(((Remark) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
