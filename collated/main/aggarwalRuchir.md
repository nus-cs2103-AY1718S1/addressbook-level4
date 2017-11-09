# aggarwalRuchir
###### /java/seedu/address/logic/commands/ListCommand.java
``` java
    public static final String MESSAGE_SUCCESS_FULLLIST  = "Listed all persons";
    public static final String MESSAGE_SUCCESS_FILTEREDLIST  = "Listed all persons with tag: ";

    public static final String MESSAGE_NOENTRIESFOUND = "No person with given tags found.";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists all the people in address or people with certain tags.\n"
            + "Parameters: [optional]Tag\n"
            + "Example: " + COMMAND_WORD + "\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_TAG + "friends";

    private final PersonContainsKeywordsPredicate predicate;

    public ListCommand() {
        this.predicate = null;
    }

    public ListCommand (PersonContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        if (this.predicate != null) {
            model.updateFilteredPersonList(predicate);
        } else {
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(MESSAGE_SUCCESS_FULLLIST);
        }

        if (areEntriesWithTagsFound()) {
            return new CommandResult(MESSAGE_SUCCESS_FILTEREDLIST + this.predicate.returnListOfTagsAsString());
        } else {
            return new CommandResult(MESSAGE_NOENTRIESFOUND);
        }
    }

    /**
     * Returns true if any entries are found for a tag in the address book
     */
    private boolean areEntriesWithTagsFound() {
        if (model.getFilteredPersonList().size() != 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ListCommand)) {
            return false;
        }

        // other has null predicate
        if ((((ListCommand) other).predicate == null) && (this.predicate == null)) {
            return true;
        }

        return this.predicate.equals(((ListCommand) other).predicate);
    }
}
```
###### /java/seedu/address/logic/parser/ListCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PersonContainsKeywordsPredicate;
import seedu.address.model.tag.Tag;


/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser implements Parser<ListCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns an ListCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);

        try {
            if (!isPrefixPresent(argMultimap, PREFIX_TAG)) {
                return new ListCommand();
            }

            Set<Tag> inputTag = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
            List<Tag> inputTagNames = new ArrayList<>(inputTag);

            return new ListCommand(new PersonContainsKeywordsPredicate(inputTagNames));

        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Returns true if none of the tag prefix contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean isPrefixPresent(ArgumentMultimap argumentMultimap, Prefix tagPrefix) {
        return Stream.of(tagPrefix).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
```
###### /java/seedu/address/model/person/PersonContainsKeywordsPredicate.java
``` java
package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.tag.Tag;


/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Tag} matches any of the keywords given.
 */
public class PersonContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<Tag> keywords;

    public PersonContainsKeywordsPredicate(List<Tag> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return keywords.stream()
                .anyMatch(keyword -> person.getTags().contains(keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((PersonContainsKeywordsPredicate) other).keywords)); // state check
    }

    /**
     * Returns all the tags as a single string
     */
    public String returnListOfTagsAsString() {
        String stringOfTags = "";
        for (Tag t : this.keywords) {
            stringOfTags += t.toString() + " ";
        }
        return stringOfTags;
    }


}


```
###### /java/seedu/address/model/person/Phone.java
``` java
    /**
     * Format given phone number into typical mobile format
     * For example for Singapore numbers: xxxx-xxxx
     */
    public static String formatPhone(String trimmedPhone) {
        int phoneLength = trimmedPhone.length();
        String formattedPhone = generateFormattedPhone(trimmedPhone, phoneLength);

        return formattedPhone;
    }


    /**
     * Generates and returns a string with digits in the phone and hyphens inserted to get right format
     */
    private static String generateFormattedPhone(String trimmedPhone, int phoneLength) {
        int digitAdded = 0;
        StringBuilder formattedPhone = new StringBuilder();
        for (int count = phoneLength - 1; count >= 0; count--) {
            formattedPhone.insert(0, trimmedPhone.charAt(count));

            digitAdded += 1;
            if (count == 0) {
                continue;
            }

            if (isHyphenNeeded(digitAdded)) {
                formattedPhone.insert(0, "-");
                digitAdded = 0;
            }
        }

        return formattedPhone.toString();
    }


    /**
     * Returns true if 4 digits added to the string and hyphen needs to be inserted
     */
    private static boolean isHyphenNeeded(int digitAdded) {
        return (digitAdded % 4 == 0);
    }
```
###### /java/seedu/address/ui/LoginWindow.java
``` java
package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.FxViewUtil;


/**
 * Controller for a login dialogue
 */
public class LoginWindow extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(LoginWindow.class);
    private static final String ICON = "/images/login_icon.png";
    private static final String FXML = "LoginWindow.fxml";
    private static final String TITLE = "Login";

    private boolean okClicked = false;

    @FXML
    private WebView browser;

    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField passwordTextField;

    private final Stage dialogStage;

    public LoginWindow() {
        super(FXML);
        Scene scene = new Scene(getRoot());
        //Null passed as the parent stage to make it non-modal.
        dialogStage = createDialogStage(TITLE, null, scene);
        dialogStage.setMaximized(false); //TODO: set a more appropriate initial size
        FxViewUtil.setStageIcon(dialogStage, ICON);
    }

    /**
     * Shows the help window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        logger.fine("Showing help page about the application.");
        dialogStage.showAndWait();
    }


    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        if (performLoginAttempt()) {
            okClicked = true;
            dialogStage.close();
        }
    }
    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }


    /**
     * Sets the login dialogue style to use the default style.
     */
    private void setStyleToDefault() {
        //TODO - restore if user restarts entering details
        ;
    }

    /**
     * Sets the login dialogue style to indicate a failed login
     */
    private void setMotionToindicateLoginFailure() {
        //TODO - change look/shake of dialog if user enters wrong details
        ;
    }

    /**
     * Performs a check whether the username and password entered by the user are correct or not
     * @return true if log in details are correct, else false
     */
    public boolean performLoginAttempt() {
        boolean loginAttemptBool = false;
        try {
            String errorMessage = "";
            String correctUsername = "admin";
            String correctPassword = "password";
            String enteredUsername = usernameTextField.getText().toString();
            String enteredPassword = passwordTextField.getText().toString();

            logger.info("username entered:" + enteredUsername);
            logger.info("password entered:" + enteredPassword);

            if ((enteredUsername.equals(correctUsername)) && (enteredPassword.equals(correctPassword))) {
                //TODO - send event for closing the login dialogue
                loginAttemptBool = true;
            } else {
                //TODO - In case wrong details entered
                errorMessage += "Incorrect username or password. Please enter correct details to start the app.";
            }

            if (errorMessage.length() != 0) {
                // Show the error message.
                Alert alert = new Alert(AlertType.ERROR);
                alert.initOwner(dialogStage);
                alert.setTitle("Incorrect Login");
                alert.setHeaderText("Error");
                alert.setContentText(errorMessage);

                alert.showAndWait();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            //TODO - Any exceptions?
        }

        return loginAttemptBool;
    }
}
```
###### /java/seedu/address/ui/MainWindow.java
``` java
    /**
     * Opens the help window.
     */
    @FXML
    public void handleLogin() {
        logger.info("Login: Enter username and password to open address book");
        LoginWindow loginWindow = new LoginWindow();
        loginWindow.show();
    }
```
###### /resources/view/LoginWindow.fxml
``` fxml
<?import javafx.scene.control.Button?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.control.TextField?>
<?import javafx.scene.layout.AnchorPane?>

<AnchorPane maxHeight="170.0" maxWidth="400.0" prefHeight="170.0" prefWidth="400.0" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <Label layoutX="14.0" layoutY="14.0" prefHeight="34.0" prefWidth="372.0" text="Address App requires the username and password to open" AnchorPane.bottomAnchor="134.0" AnchorPane.leftAnchor="14.0" AnchorPane.rightAnchor="14.0" AnchorPane.topAnchor="14.0" />
      <TextField fx:id="usernameTextField" layoutX="26.0" layoutY="62.0" prefHeight="27.0" prefWidth="372.0" promptText="Username..." style="-fx-border-width: 0;" AnchorPane.bottomAnchor="93.0" AnchorPane.leftAnchor="14.0" AnchorPane.rightAnchor="14.0" AnchorPane.topAnchor="62.0" />
      <TextField fx:id="passwordTextField" layoutX="26.0" layoutY="102.0" prefHeight="27.0" prefWidth="372.0" promptText="Password..." AnchorPane.bottomAnchor="53.0" AnchorPane.leftAnchor="14.0" AnchorPane.rightAnchor="14.0" AnchorPane.topAnchor="102.0" />
      <Button layoutX="242.0" layoutY="141.0" minWidth="61.0" mnemonicParsing="false" onAction="#handleOk" text="OK" />
      <Button layoutX="321.0" layoutY="141.0" mnemonicParsing="false" onAction="#handleCancel" text="Cancel" />
   </children>
</AnchorPane>
```
