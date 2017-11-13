# zenghou
###### /java/seedu/address/commons/core/Config.java
``` java
    public String getUserCredsFilePath() {
        return userCredsFilePath;
    }

    public void setUserCredsFilePath(String userCredsFilePath) {
        this.userCredsFilePath = userCredsFilePath;
    }
```
###### /java/seedu/address/commons/events/model/UserCredsChangedEvent.java
``` java
package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates that UserCreds isValidated flag has changed.
 */
public class UserCredsChangedEvent extends BaseEvent {

    @Override
    public String toString() {
        return UserCredsChangedEvent.class.getSimpleName();
    }
}
```
###### /java/seedu/address/logic/commands/LoginCommand.java
``` java
package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.user.UserCreds;

/**
 * Authenticates a user with username and password
 */
public class LoginCommand extends Command {
    public static final String COMMAND_WORD = "login";

    public static final String MESSAGE_USAGE = COMMAND_WORD  + ": Authenticates a user of the address book."
            + " Parameters: "
            + PREFIX_USERNAME + "USERNAME "
            + PREFIX_PASSWORD + "PASSWORD ";

    public static final String MESSAGE_SUCCESS = "Successfully logged in!";
    public static final String MESSAGE_FAILURE = "Please ensure that username and password are entered correctly!";

    private UserCreds userCreds;

    public LoginCommand(String username, String password) {
        this.userCreds = new UserCreds(username, password);

    }

    @Override
    public CommandResult execute() throws CommandException {
        UserCreds userCredsInModel = model.getUserCreds();
        boolean isVerifiedUser = UserCreds.isValidUser(userCreds, userCredsInModel);
        if (isVerifiedUser) {
            model.updateUserCreds();
            userCredsInModel.validateCurrentSession();
            return new CommandResult(MESSAGE_SUCCESS);
        }
        throw new CommandException(MESSAGE_FAILURE);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LoginCommand // instanceof handles nulls
                && userCreds.equals(((LoginCommand) other).userCreds));
    }

}
```
###### /java/seedu/address/logic/commands/RemarkCommand.java
``` java
package seedu.address.logic.commands;

import static seedu.address.logic.commands.EditCommand.MESSAGE_DUPLICATE_PERSON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Email;
import seedu.address.model.person.Facebook;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Remark;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Adds a remark to a person by INDEX
 */
public class RemarkCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "remark";
    public static final String COMMAND_ALIAS = "rmk";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the remark for a person specified in the INDEX.\n"
            + "Parameters: INDEX (must be a positive integer) " + PREFIX_REMARK + "[REMARK]\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_REMARK + " Likes to drink coffee";

    public static final String MESSAGE_SUCCESS = "New remark added: %1$s";

    private final Index targetIndex;
    private Remark remark;

    public RemarkCommand(Index index, Remark remark) {
        this.targetIndex = index;
        this.remark = remark;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToAddRemarkTo = lastShownList.get(targetIndex.getZeroBased());
        ReadOnlyPerson personWithRemarkAdded = addRemarkToPerson(personToAddRemarkTo, remark);

        try {
            model.updatePerson(personToAddRemarkTo, personWithRemarkAdded);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, personWithRemarkAdded));

    }

    public Remark getRemark() {
        return remark;
    }

    public Index getIndex() {
        return targetIndex;
    }

    /**
     * Creates and returns a {@code Person} with the remark {@code rmk}
     */
    private static Person addRemarkToPerson (ReadOnlyPerson personToAddRemarkTo, Remark rmk) {
        assert personToAddRemarkTo != null;

        Name name = personToAddRemarkTo.getName();
        Phone phone = personToAddRemarkTo.getPhone();
        Email email = personToAddRemarkTo.getEmail();
        Address address = personToAddRemarkTo.getAddress();
        Birthday birthday = personToAddRemarkTo.getBirthday();
        Facebook facebook = personToAddRemarkTo.getFacebook();
        Set<Tag> tags = personToAddRemarkTo.getTags();

        Person personWithRemark = new Person(name, phone, email, address, birthday, facebook, tags);
        personWithRemark.setRemark(rmk);

        return personWithRemark;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof RemarkCommand) {
            RemarkCommand otherObject = (RemarkCommand) other;
            return this.getRemark().equals(otherObject.getRemark())
                    && this.getIndex().equals(otherObject.getIndex());
        }
        return false;
    }
}
```
###### /java/seedu/address/logic/commands/SearchCommand.java
``` java
package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.DetailsContainKeyphrasePredicate;

/**
 * Searches and lists all persons in address book whose details (e.g. Name, Phone, Email, etc.)
 * contain the keyphrase.
 * Keyphrase matching is case insensitive.
 */
public class SearchCommand extends Command {

    public static final String COMMAND_WORD = "search";
    public static final String COMMAND_ALIAS = "sh";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Searches for all persons whose details (i.e. name, "
            + "phone, email, address, remark or tags) exactly matches the "
            + "the specified keyword/phrase (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final DetailsContainKeyphrasePredicate predicate;

    public SearchCommand(DetailsContainKeyphrasePredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() throws CommandException {
        model.updateFilteredPersonList(predicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SearchCommand // instanceof handles nulls
                && this.predicate.equals(((SearchCommand) other).predicate)); // state check
    }
}
```
###### /java/seedu/address/logic/parser/LoginCommandParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import java.util.stream.Stream;

import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new LoginCommand object
 */
public class LoginCommandParser implements Parser<LoginCommand> {

    @Override
    public LoginCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(userInput, PREFIX_USERNAME, PREFIX_PASSWORD);

        if (!arePrefixesPresent(argumentMultimap, PREFIX_USERNAME, PREFIX_PASSWORD)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoginCommand.MESSAGE_USAGE));
        }

        String username = argumentMultimap.getValue(PREFIX_USERNAME).get();
        String password = argumentMultimap.getValue(PREFIX_PASSWORD).get();
        requireNonNull(username);
        requireNonNull(password);
        return new LoginCommand(username, password);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
```
###### /java/seedu/address/model/ModelManager.java
``` java
    /**
     * Returns userCreds to be used by Logic component for user verification
     */
    @Override
    public synchronized UserCreds getUserCreds() {
        return userCreds;
    }

```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public synchronized void updateUserCreds() {
        indicateUserCredsChanged();
    }

```
###### /java/seedu/address/model/ModelManager.java
``` java
    /** Raises an event to indicate the UserCreds has changed */
    private synchronized void indicateUserCredsChanged() {
        raise(new UserCredsChangedEvent());
    }
```
###### /java/seedu/address/model/person/DetailsContainKeyphrasePredicate.java
``` java
package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that either one of {@code ReadOnlyPerson}'s {@code Name, Phone, Address, Email, Tag, Remarks} matches,
 * partially or in full, any of the keywords given.
 */
public class DetailsContainKeyphrasePredicate implements Predicate<ReadOnlyPerson> {
    public final String keyphrase;

    public DetailsContainKeyphrasePredicate(String keyphrase)  {
        this.keyphrase = keyphrase;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return personDetailsContainsKeyphrase(person, keyphrase);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DetailsContainKeyphrasePredicate // instanceof handles nulls
                && this.keyphrase.equals(((DetailsContainKeyphrasePredicate) other).keyphrase)); // state check
    }

    /**
     * Tests if the {@code keyphrase} entered matches, partially or in full, any attribute of a {@code person}
     * @param person
     * @param keyphrase
     * @return true if any attribute of the {@code person}'s details match the keyphrase
     */
    private boolean personDetailsContainsKeyphrase(ReadOnlyPerson person, String keyphrase) {
        boolean nameContainsKeyphrase = StringUtil.caseInsensitiveContains(person.getName().fullName, keyphrase);
        boolean emailContainsKeyphrase = StringUtil.caseInsensitiveContains(person.getEmail().value, keyphrase);
        boolean phoneContainsKeyphrase = StringUtil.caseInsensitiveContains(person.getPhone().value, keyphrase);
        boolean addressContainsKeyphrase = StringUtil.caseInsensitiveContains(person.getAddress().value, keyphrase);
        boolean facebookContainsKeyphrase = StringUtil.caseInsensitiveContains(person.getFacebook().value, keyphrase);
        boolean remarkContainsKeyphrase = StringUtil.caseInsensitiveContains(person.getRemark().value, keyphrase);
        boolean tagContainsKeyphrase = StringUtil.caseInsensitiveContains(person.getTags().toString(), keyphrase);

        return nameContainsKeyphrase || emailContainsKeyphrase || phoneContainsKeyphrase || addressContainsKeyphrase
                || facebookContainsKeyphrase || remarkContainsKeyphrase || tagContainsKeyphrase;
    }
}
```
###### /java/seedu/address/model/person/Remark.java
``` java
package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a remark about a Person in the address book.
 * Guarantees: immutable
 */
public class Remark {

    public static final String MESSAGE_REMARK_CONSTRAINTS =
            "Remarks can take any value, and it may be blank";

    public final String value;

    /**
     * Validates given remark.
     */
    public Remark(String remark) {
        requireNonNull(remark);
        this.value = remark;
    }

    /**
     * Returns an empty Birthday object.
     */
    public static Remark getEmptyRemark() {
        return new Remark("");
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Remark // instanceof handles nulls
                && this.value.equals(((Remark) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### /java/seedu/address/model/user/UserCreds.java
``` java
package seedu.address.model.user;

import java.util.Objects;

/**
 * Represents a user's credentials
 */
public class UserCreds {
    private String username;
    private int passwordHash;
    private boolean isValidated = false;

    public UserCreds() {
        username = "admin";
        passwordHash = "password".hashCode();
    }

    public UserCreds(String username, String password) {
        this.username = username;
        this.passwordHash = password.hashCode();
    }

    /**
     * Takes in two UserCreds object and verifies if they match
     * @param currentSessionCredentials enter by current user
     * @param savedCredentials in the system
     * @return true if current user is a valid user
     */
    public static boolean isValidUser(UserCreds currentSessionCredentials, UserCreds savedCredentials) {
        return currentSessionCredentials.equals(savedCredentials);
    }

    /**
     * Update isValidated flag to true after current user's credentials matches the saved credentials
     * {@link #isValidUser(UserCreds, UserCreds)}
     * Flag is only valid for this current session, and should be set back to false after current session ends.
     */
    public void validateCurrentSession() {
        isValidated = true;
    }

    /**
     * Checks if current user is validated.
     * @return False if current user is not logged in
     */
    public boolean isValidSession() {
        return isValidated;
    }

    /**
     * Takes in a new String {@code password} and updates the passwordHash
     */
    public void updatePassword(String newPassword) {
        passwordHash = newPassword.hashCode();
    }

    public void updateUsername(String newUsername) {
        username = newUsername;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UserCreds // instanceof handles nulls
                && this.username.equals(((UserCreds) other).username)
                && this.passwordHash == ((UserCreds) other).passwordHash);
    }

    @Override
    public String toString() {
        return "User: " + username;
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, passwordHash);
    }

}
```
###### /java/seedu/address/storage/Storage.java
``` java
    @Override
    Optional<UserCreds> readUserCreds() throws DataConversionException, IOException;

```
###### /java/seedu/address/storage/Storage.java
``` java
    @Override
    void saveUserCreds(UserCreds userCreds) throws IOException;
```
###### /java/seedu/address/storage/StorageManager.java
``` java
    @Override
    public String getUserCredsFilePath() {
        return userCredsStorage.getUserCredsFilePath();
    }

```
###### /java/seedu/address/storage/StorageManager.java
``` java
    @Override
    public Optional<UserCreds> readUserCreds() throws DataConversionException, IOException {
        return userCredsStorage.readUserCreds();
    }

```
###### /java/seedu/address/storage/StorageManager.java
``` java
    @Override
    public void saveUserCreds(UserCreds userCreds) throws IOException {
        userCredsStorage.saveUserCreds(userCreds);
    }
```
###### /java/seedu/address/ui/PersonProfile.java
``` java
package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.PictureSize;
import facebook4j.auth.AccessToken;
import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.ReadOnlyPerson;

/** Contains the profile panel of a person */
public class PersonProfile extends UiPart<Region> {

    private static final String FXML = "PersonProfile.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private final Facebook facebook = new FacebookFactory().getInstance();

    private final String appId = "383233665430563";
    private final String appSecret = "c127fe47ff977d7da7be5f38b4662ba5";
    private final String appToken = "383233665430563|lAaXz9VGaRFJNVLTI9iTKKl1pWk";
    private final String defaultProfilePicture = "images/fail.png";

    @FXML
    private Label profile;

    @FXML
    private Label name;

    @FXML
    private Label birthday;

    @FXML
    private Label remark;

    @FXML
    private ImageView profilePicture;

    @FXML
    private FlowPane tags;

    public PersonProfile() {
        super(FXML);
        registerAsAnEventHandler(this);
        facebook.setOAuthAppId(appId, appSecret);
        facebook.setOAuthAccessToken(new AccessToken(appToken, null));
    }

    /**
     * Sets the individual UI elements to the respective {@code Person} properties
     * so the profile correctly reflects the selected person.
     */
    private void initPersonProfile(ReadOnlyPerson person) {
        profile.setText("PROFILE");
        name.setText(person.getName().toString());
        birthday.setText(person.getBirthday().toString());
        remark.setText(person.getRemark().toString());
        tags.getChildren().clear(); // clear existing tags
        person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    /**
     * Retrieves a profile picture from Facebook {@see Facebook#getPictureURL}and sets that image as the contact image
     * for the Person's profile. A default image would be assigned if no picture is retrieved from Facebook.
     */
    private void setProfilePicture(ReadOnlyPerson person) {
        Image profilePic;
        try {
            profilePic = new Image(facebook.getPictureURL(person.getFacebook().value, PictureSize.large).toString());
        } catch (FacebookException fbe) {
            profilePic = new Image(defaultProfilePicture);
            fbe.printStackTrace();
        }
        profilePicture.setImage(profilePic);
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        ReadOnlyPerson selectedPerson = event.getNewSelection().person;
        initPersonProfile(selectedPerson);
        setProfilePicture(selectedPerson);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonProfile)) {
            return false;
        }

        // state check
        PersonProfile profile = (PersonProfile) other;
        return name.getText().equals(profile.name.getText())
                && remark.getText().equals(profile.remark.getText());
    }
}
```
###### /java/seedu/address/ui/UiManager.java
``` java
    @Subscribe
    private void handleUserCredsChangedEvent(UserCredsChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.fillRestOfInnerParts();
    }
}
```
###### /resources/view/PersonProfile.fxml
``` fxml

<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.image.Image?>
<?import javafx.scene.image.ImageView?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.FlowPane?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.layout.VBox?>
<?import javafx.scene.text.Font?>

<GridPane maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="476.0" prefWidth="306.0" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
  <columnConstraints>
    <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
  </columnConstraints>
  <rowConstraints>
      <RowConstraints maxHeight="230.0" minHeight="10.0" prefHeight="33.0" vgrow="SOMETIMES" />
    <RowConstraints maxHeight="455.0" minHeight="10.0" prefHeight="443.0" vgrow="SOMETIMES" />
  </rowConstraints>
   <children>
      <Label fx:id="profile" />
      <VBox prefHeight="482.0" prefWidth="306.0" styleClass="profile-wrapper" GridPane.rowIndex="1">
         <children>
            <ImageView fx:id="profilePicture" fitHeight="120.0" fitWidth="120.0" pickOnBounds="true" preserveRatio="true" translateX="93.0">
               <VBox.margin>
                  <Insets top="5.0" />
               </VBox.margin></ImageView>
            <Label fx:id="name" alignment="CENTER" contentDisplay="CENTER" prefHeight="30.0" prefWidth="300.0" styleClass="name" textAlignment="CENTER">
               <VBox.margin>
                  <Insets left="5.0" right="5.0" top="5.0" />
               </VBox.margin></Label>
            <HBox prefHeight="30.0" prefWidth="300.0" styleClass="birthday">
               <children>
                  <Label text="Birthday: ">
                     <font>
                        <Font size="18.0" />
                     </font>
                     <padding>
                        <Insets bottom="5.0" left="5.0" right="5.0" top="5.0" />
                     </padding>
                  </Label>
                  <Label fx:id="birthday" styleClass="birthday-field">
                     <padding>
                        <Insets bottom="5.0" left="5.0" right="5.0" top="5.0" />
                     </padding>
                     <font>
                        <Font size="18.0" />
                     </font>
                  </Label>
               </children>
               <padding>
                  <Insets bottom="5.0" left="5.0" right="5.0" top="5.0" />
               </padding>
               <VBox.margin>
                  <Insets bottom="5.0" left="5.0" right="5.0" top="5.0" />
               </VBox.margin>
            </HBox>
            <VBox prefHeight="200.0" prefWidth="100.0" styleClass="remarks">
               <children>
                  <Label text="Remarks:">
                     <font>
                        <Font size="18.0" />
                     </font>
                     <VBox.margin>
                        <Insets left="5.0" right="5.0" top="5.0" />
                     </VBox.margin>
                     <padding>
                        <Insets left="5.0" right="5.0" />
                     </padding>
                  </Label>
                  <Label fx:id="remark" alignment="TOP_LEFT" prefHeight="71.0" prefWidth="286.0" styleClass="remarks-content">
                     <padding>
                        <Insets left="5.0" right="5.0" />
                     </padding>
                     <font>
                        <Font size="18.0" />
                     </font>
                     <VBox.margin>
                        <Insets left="5.0" right="5.0" />
                     </VBox.margin>
                  </Label>
               </children>
               <VBox.margin>
                  <Insets bottom="5.0" left="5.0" right="5.0" top="5.0" />
               </VBox.margin>
            </VBox>
            <VBox prefHeight="140.0" prefWidth="306.0" styleClass="tags-box">
               <children>
                  <HBox prefHeight="44.0" prefWidth="306.0">
                     <children>
                        <ImageView fitHeight="25.0" fitWidth="25.0" pickOnBounds="true" preserveRatio="true">
                           <image>
                              <Image url="@../images/tags.jpeg" />
                           </image>
                        </ImageView>
                        <Label text="Tags" translateX="5.0" translateY="4.0" />
                     </children>
                     <padding>
                        <Insets bottom="5.0" left="5.0" right="5.0" top="5.0" />
                     </padding>
                     <VBox.margin>
                        <Insets />
                     </VBox.margin>
                  </HBox>
                  <FlowPane fx:id="tags" prefHeight="76.0" prefWidth="296.0">
                     <padding>
                        <Insets bottom="5.0" left="5.0" right="5.0" top="5.0" />
                     </padding></FlowPane>
               </children>
               <padding>
                  <Insets bottom="5.0" left="5.0" right="5.0" top="5.0" />
               </padding>
               <VBox.margin>
                  <Insets bottom="5.0" left="5.0" right="5.0" top="5.0" />
               </VBox.margin>
            </VBox>
         </children>
      </VBox>
   </children>
</GridPane>
```
