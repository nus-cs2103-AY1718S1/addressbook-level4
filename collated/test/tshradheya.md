# tshradheya
###### \java\guitests\guihandles\DetailsPanelHandle.java
``` java
package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;

/**
 * Provides a handle to a person details in the panel.
 */
public class DetailsPanelHandle extends NodeHandle<Node> {
    private static final String NAME_FIELD_ID = "#detailsName";
    private static final String ADDRESS_FIELD_ID = "#detailsAddress";
    private static final String PHONE_FIELD_ID = "#detailsPhone";
    private static final String EMAIL_FIELD_ID = "#detailsEmail";
    private static final String NICKNAME_FIELD_ID = "#detailsNickname";
    private static final String BIRTHDAY_FIELD_ID = "#detailsBirthday";
    private static final String DISPLAY_PICTURE_FIELD_ID = "#detailsDisplayPicture";
    private static final String TAGS_FIELD_ID = "#detailsTag";

    private final Label nameLabel;
    private final Label addressLabel;
    private final Label phoneLabel;
    private final Label emailLabel;
    private final Label nicknameLabel;
    private final Label birthdayLabel;
    private final ImageView displayPictureImageView;
    private final List<Label> tagLabels;

    public DetailsPanelHandle(Node cardNode) {
        super(cardNode);

        this.nameLabel = getChildNode(NAME_FIELD_ID);
        this.addressLabel = getChildNode(ADDRESS_FIELD_ID);
        this.phoneLabel = getChildNode(PHONE_FIELD_ID);
        this.emailLabel = getChildNode(EMAIL_FIELD_ID);
        this.nicknameLabel = getChildNode(NICKNAME_FIELD_ID);
        this.birthdayLabel = getChildNode(BIRTHDAY_FIELD_ID);
        this.displayPictureImageView = getChildNode(DISPLAY_PICTURE_FIELD_ID);

        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        this.tagLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getAddress() {
        return addressLabel.getText();
    }

    public String getPhone() {
        return phoneLabel.getText();
    }

    public String getEmail() {
        return emailLabel.getText();
    }

    public String getNickname() {
        return nicknameLabel.getText();
    }

    public String getBirthday() {
        return birthdayLabel.getText();
    }

    public Image getDisplayPictureImageView() {
        return displayPictureImageView.getImage();
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }
}
```
###### \java\guitests\guihandles\PopularContactCardHandle.java
``` java
package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

/**
 * Provides a handle to a person card in the Popular Contact list panel.
 */
public class PopularContactCardHandle extends NodeHandle<Node> {

    private static final String RANK_FEILD_ID = "#rank";
    private static final String NAME_FIELD_ID = "#popularContactName";
    private static final String DISPLAY_PICTURE_FIELD_ID = "#popularContactDisplayPicture";

    private final Label idLabel;
    private final Label nameLabel;
    private final Circle displayPictureImageView;

    public PopularContactCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(RANK_FEILD_ID);
        this.nameLabel = getChildNode(NAME_FIELD_ID);
        this.displayPictureImageView = getChildNode(DISPLAY_PICTURE_FIELD_ID);

    }

    public String getRank() {
        return idLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }

    public Paint getDisplayPictureImageView() {
        return displayPictureImageView.getFill();
    }
}
```
###### \java\guitests\guihandles\PopularContactsPanelHandle.java
``` java
package guitests.guihandles;

import java.util.List;
import java.util.Optional;

import javafx.scene.control.ListView;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.ui.PopularContactCard;

/**
 * Provides a handle for {@code PopularContactListPanel} containing the list of {@code PopularContactCard}
 */
public class PopularContactsPanelHandle extends NodeHandle<ListView<PopularContactCard>> {

    public static final String PERSON_LIST_VIEW_ID = "#popularContactListView";

    private Optional<PopularContactCard> lastRememberedSelectedPopularContactCard;

    public PopularContactsPanelHandle(ListView<PopularContactCard> personListPanelNode) {
        super(personListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code PersonCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     */
    public PopularContactCardHandle getHandleToSelectedCard() {
        List<PopularContactCard> personList = getRootNode().getSelectionModel().getSelectedItems();

        if (personList.size() != 1) {
            throw new AssertionError("Person list size expected 1.");
        }

        return new PopularContactCardHandle(personList.get(0).getRoot());
    }

    /**
     * Returns the index of the selected card.
     */
    public int getSelectedCardIndex() {
        return getRootNode().getSelectionModel().getSelectedIndex();
    }

    /**
     * Returns true if a card is currently selected.
     */
    public boolean isAnyCardSelected() {
        List<PopularContactCard> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display and select the person.
     */
    public void navigateToCard(ReadOnlyPerson person) {
        List<PopularContactCard> cards = getRootNode().getItems();
        Optional<PopularContactCard> matchingCard = cards.stream().filter(card -> card.person.equals(person)).findFirst();

        if (!matchingCard.isPresent()) {
            throw new IllegalArgumentException("Person does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(matchingCard.get());
            getRootNode().getSelectionModel().select(matchingCard.get());
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Returns the person card handle of a person associated with the {@code index} in the list.
     */
    public PopularContactCardHandle getPopularContactCardHandle(int index) {
        return getPopularContactCardHandle(getRootNode().getItems().get(index).person);
    }

    /**
     * Returns the {@code PersonCardHandle} of the specified {@code person} in the list.
     */
    public PopularContactCardHandle getPopularContactCardHandle(ReadOnlyPerson person) {
        Optional<PopularContactCardHandle> handle = getRootNode().getItems().stream()
                .filter(card -> card.person.equals(person))
                .map(card -> new PopularContactCardHandle(card.getRoot()))
                .findFirst();
        return handle.orElseThrow(() -> new IllegalArgumentException("Person does not exist."));
    }

    /**
     * Selects the {@code PersonCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Remembers the selected {@code PersonCard} in the list.
     */
    public void rememberSelectedPersonCard() {
        List<PopularContactCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedPopularContactCard = Optional.empty();
        } else {
            lastRememberedSelectedPopularContactCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code PersonCard} is different from the value remembered by the most recent
     * {@code rememberSelectedPersonCard()} call.
     */
    public boolean isSelectedPersonCardChanged() {
        List<PopularContactCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedPopularContactCard.isPresent();
        } else {
            return !lastRememberedSelectedPopularContactCard.isPresent()
                    || !lastRememberedSelectedPopularContactCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
```
###### \java\guitests\SampleDataTest.java
``` java
    @Test
    public void reminder_dataFileDoesNotExist_loadSampleData() {
        Reminder[] expectedList = SampleDataUtil.getSampleReminders();
        assertListMatchingReminders(SampleDataUtil.getSampleReminderList(), expectedList);
    }
```
###### \java\seedu\address\logic\commands\DetailsCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalReminders.getUniqueTypicalReminders;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.ShowDetailsEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;

public class DetailsCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getUniqueTypicalReminders(), new UserPrefs());
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastPersonIndex = Index.fromOneBased(model.getFilteredPersonList().size());

        assertExecutionSuccess(INDEX_FIRST_PERSON);
        assertExecutionSuccess(INDEX_THIRD_PERSON);
        assertExecutionSuccess(lastPersonIndex);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showFirstPersonOnly(model);

        assertExecutionSuccess(INDEX_FIRST_PERSON);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showFirstPersonOnly(model);

        Index outOfBoundsIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DetailsCommand detailsFirstCommand = new DetailsCommand(INDEX_FIRST_PERSON);
        DetailsCommand detailsSecondCommand = new DetailsCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(detailsFirstCommand.equals(detailsFirstCommand));

        // same values -> returns true
        DetailsCommand selectFirstCommandCopy = new DetailsCommand(INDEX_FIRST_PERSON);
        assertTrue(detailsFirstCommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(detailsFirstCommand.equals(1));

        // null -> returns false
        assertFalse(detailsFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(detailsFirstCommand.equals(detailsSecondCommand));
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index}, and checks that {@code JumpToListRequestEvent}
     * is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index) {
        DetailsCommand detailsCommand = prepareCommand(index);

        try {
            CommandResult commandResult = detailsCommand.execute();
            assertEquals(String.format(DetailsCommand.MESSAGE_DETAILS_PERSON_SUCCESS, index.getOneBased()),
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        ShowDetailsEvent lastEvent = (ShowDetailsEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(index, Index.fromZeroBased(lastEvent.targetIndex));
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        DetailsCommand detailsCommand = prepareCommand(index);

        try {
            detailsCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code SelectCommand} with parameters {@code index}.
     */
    private DetailsCommand prepareCommand(Index index) {
        DetailsCommand detailsCommand = new DetailsCommand(index);
        detailsCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return detailsCommand;
    }
}
```
###### \java\seedu\address\logic\commands\DisplayPictureCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DISPLAY_PICTURE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DISPLAY_PICTURE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DISPLAYPIC_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalReminders.getUniqueTypicalReminders;

import java.io.File;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.DisplayPicture;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class DisplayPictureCommandTest {

    public static final String INDEX_FIRST_PERSON_EMAIL = "alice@example.com";

    public static final  String DISPLAY_PICTURE_ALICE_PATH =
            new File("./src/test/resources/pictures/" + VALID_DISPLAYPIC_ALICE)
                    .getAbsolutePath();

    private static final String INVALID_PATH = " . /?no/2 t hing";
    private Model model = new ModelManager(getTypicalAddressBook(), getUniqueTypicalReminders(), new UserPrefs());

    @Test
    public void execute_setDisplayPicture_success() throws Exception {
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withDisplayPicture(Integer.toString(INDEX_FIRST_PERSON_EMAIL.hashCode())).build();

        DisplayPictureCommand displayPictureCommand = prepareCommand(INDEX_FIRST_PERSON,
                DISPLAY_PICTURE_ALICE_PATH);

        String expectedMessage = String.format(DisplayPictureCommand.MESSAGE_ADD_DISPLAYPICTURE_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                getUniqueTypicalReminders(), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(displayPictureCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_setDisplayPicture_successWithNoPath() throws Exception {
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withDisplayPicture("").build();

        DisplayPictureCommand displayPictureCommand = prepareCommand(INDEX_FIRST_PERSON,
                "");

        String expectedMessage = String.format(DisplayPictureCommand.MESSAGE_DELETE_DISPLAYPICTURE_SUCCESS,
                editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                getUniqueTypicalReminders(), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(displayPictureCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DisplayPictureCommand displayPictureCommand = prepareCommand(outOfBoundIndex, DISPLAY_PICTURE_ALICE_PATH);

        assertCommandFailure(displayPictureCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidPersonIndexFilteredList_failure() throws Exception {
        showFirstPersonOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still within bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DisplayPictureCommand displayPictureCommand = prepareCommand(outOfBoundIndex, DISPLAY_PICTURE_ALICE_PATH);

        assertCommandFailure(displayPictureCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }


    @Test
    public void equals() {
        final DisplayPictureCommand standardCommand =
                new DisplayPictureCommand(INDEX_FIRST_PERSON, DISPLAY_PICTURE_AMY);
        final DisplayPictureCommand commandWithSameValues =
                new DisplayPictureCommand(INDEX_FIRST_PERSON, DISPLAY_PICTURE_AMY);
        final DisplayPictureCommand commandWithDifferentIndex =
                new DisplayPictureCommand(INDEX_SECOND_PERSON, DISPLAY_PICTURE_AMY);
        final DisplayPictureCommand commandWithDifferentDisplayPicture =
                new DisplayPictureCommand(INDEX_FIRST_PERSON, DISPLAY_PICTURE_BOB);
        final DisplayPictureCommand commandWithDifferentValues =
                new DisplayPictureCommand(INDEX_SECOND_PERSON, DISPLAY_PICTURE_BOB);

        // same object -> Returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different object, same type with same values -> return true
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same type but different index -> returns false
        assertFalse(standardCommand.equals(commandWithDifferentIndex));

        // same type but different nickname -> returns false
        assertFalse(standardCommand.equals(commandWithDifferentDisplayPicture));

        // same type but different index and nickname -> returns false
        assertFalse(standardCommand.equals(commandWithDifferentValues));
    }

    /**
     * Returns an {@code DisplayPictureCommand} with parameters {@code index} and {@code displaypic}
     */
    private DisplayPictureCommand prepareCommand(Index index, String displaypic) {
        DisplayPictureCommand displayPictureCommand = new DisplayPictureCommand(index, new DisplayPicture(displaypic));
        displayPictureCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return displayPictureCommand;
    }
}
```
###### \java\seedu\address\logic\commands\EmailCommandTest.java
``` java
package seedu.address.logic.commands;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.email.Body;
import seedu.address.model.email.Service;
import seedu.address.model.email.Subject;
import seedu.address.model.person.PersonContainsTagPredicate;


public class EmailCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void processEmailTestFail() throws ParseException {
        thrown.expect(ParseException.class);
        EmailCommand command = new EmailCommand(new PersonContainsTagPredicate("friends"), new Subject("hello"),
                new Body("hi"), new Service("gmail"));
        command.processEmail("");
    }

    @Test
    public void checkServiceValid_throwsException() throws ParseException {
        thrown.expect(ParseException.class);
        EmailCommand command = new EmailCommand(new PersonContainsTagPredicate("friends"), new Subject("hello"),
                new Body("hi"), new Service("gmail"));
        command.checkServiceValid(new Service("notPresent"));
    }

    @Test
    public void executeParseExceptionThrown() {
        EmailCommand command = new EmailCommand(new PersonContainsTagPredicate("friend"), new Subject("hello"),
                new Body("hi"), new Service("non existing"));
        CommandResult result = command.execute();
        assertEquals(result.feedbackToUser, EmailCommand.MESSAGE_NOT_SENT);
    }

    @Test
    public void equals() {
        EmailCommand command = new EmailCommand(new PersonContainsTagPredicate("friends"), new Subject("hello"),
                new Body("hi"), new Service("gmail"));
        EmailCommand sameCommand = new EmailCommand(new PersonContainsTagPredicate("friends"), new Subject("hello"),
                new Body("hi"), new Service("gmail"));
        EmailCommand differentCommand = new EmailCommand(new PersonContainsTagPredicate("hmm"), new Subject("hi"),
                new Body("yo"), new Service("outlook"));

        assertTrue(command.equals(sameCommand));

        assertTrue(command.equals(command));

        assertFalse(command == null);

        assertFalse(sameCommand.equals(differentCommand));
    }


}
```
###### \java\seedu\address\logic\commands\LocationCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalReminders.getUniqueTypicalReminders;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.ShowLocationEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;

public class LocationCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model = new ModelManager(getTypicalAddressBook(), getUniqueTypicalReminders(), new UserPrefs());

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        LocationCommand locationCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(locationCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidPersonIndexFilteredList_failure() throws Exception {
        showFirstPersonOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still within bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        LocationCommand locationCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(locationCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final LocationCommand standardCommand = new LocationCommand(INDEX_FIRST_PERSON);
        final LocationCommand commandWithSameIndex = new LocationCommand(INDEX_FIRST_PERSON);
        final LocationCommand commandWithDifferentIndex = new LocationCommand(INDEX_SECOND_PERSON);


        // same object -> Returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different object, same type with same values -> return true
        assertTrue(standardCommand.equals(commandWithSameIndex));

        // same type but different index -> returns false
        assertFalse(standardCommand.equals(commandWithDifferentIndex));

    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastPersonIndex = Index.fromOneBased(model.getFilteredPersonList().size());

        assertExecutionSuccess(INDEX_FIRST_PERSON);
        assertExecutionSuccess(INDEX_THIRD_PERSON);
        assertExecutionSuccess(lastPersonIndex);
    }

    @Test
    public void checkIfEventCollected() throws CommandException {
        LocationCommand locationCommand = prepareCommand(INDEX_FIRST_PERSON);
        locationCommand.execute();
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ShowLocationEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 3);
    }

    /**
     * Executes a {@code LocationCommand} with the given {@code index}, and checks that {@code ShowLocationEvent}
     * is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index) {
        LocationCommand locationCommand = prepareCommand(index);

        try {
            CommandResult commandResult = locationCommand.execute();
            assertEquals(String.format(LocationCommand.MESSAGE_FIND_LOCATION_SUCCESS,
                    model.getFilteredPersonList().get(index.getZeroBased()).getName().fullName,
                    model.getFilteredPersonList().get(index.getZeroBased()).getAddress().value),
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        ShowLocationEvent lastEvent = (ShowLocationEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(model.getFilteredPersonList().get(index.getZeroBased()), lastEvent.person);
    }

    /**
     * Returns an {@code LocationCommand} with parameters {@code index}}
     */
    private LocationCommand prepareCommand(Index index) {
        LocationCommand locationCommand = new LocationCommand(index);
        locationCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return locationCommand;
    }
}
```
###### \java\seedu\address\logic\commands\ViewTagCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalReminders.getUniqueTypicalReminders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.PersonContainsTagPredicate;
import seedu.address.model.person.ReadOnlyPerson;

public class ViewTagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getUniqueTypicalReminders(), new UserPrefs());

    @Test
    public void equals() {
        PersonContainsTagPredicate firstPredicate =
                new PersonContainsTagPredicate("foo");
        PersonContainsTagPredicate secondPredicate =
                new PersonContainsTagPredicate("bar");

        ViewTagCommand viewTagFirstCommand = new ViewTagCommand(firstPredicate);
        ViewTagCommand viewTagSecondCommand = new ViewTagCommand(secondPredicate);

        // same object -> returns true
        assertTrue(viewTagFirstCommand.equals(viewTagFirstCommand));

        // same values -> returns true
        ViewTagCommand viewTagFirstCommandCopy = new ViewTagCommand(firstPredicate);
        assertTrue(viewTagFirstCommand.equals(viewTagFirstCommandCopy));

        // different types -> returns false
        assertFalse(viewTagFirstCommand.equals(1));

        // null -> returns false
        assertFalse(viewTagFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(viewTagFirstCommand.equals(viewTagSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        ViewTagCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_oneKeyword_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        ViewTagCommand command = prepareCommand("friend");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ALICE, BENSON));
    }

    @Test
    public void execute_oneKeyword_singlePersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        ViewTagCommand command = prepareCommand("enemy");
        assertCommandSuccess(command, expectedMessage, Collections.singletonList(ELLE));
    }

    /**
     * Parses {@code keyword} into a {@code ViewTagCommand}.
     */
    private ViewTagCommand prepareCommand(String keyword) {
        ViewTagCommand command =
                new ViewTagCommand(new PersonContainsTagPredicate(keyword));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(ViewTagCommand command, String expectedMessage,
                                      List<ReadOnlyPerson> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }

}
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_viewtag() throws Exception {
        String keyword = "foo";
        ViewTagCommand command = (ViewTagCommand) parser.parseCommand(ViewTagCommand.COMMAND_WORD + " " + keyword);

        assertEquals(new ViewTagCommand(new PersonContainsTagPredicate(keyword)), command);
    }

    @Test
    public void parseCommand_email() throws Exception {
        String tag = "friends";
        Service service = new Service("gmail");
        Subject subject = new Subject("hello");
        Body body = new Body("meeting at 8pm");

        EmailCommand command = (EmailCommand) parser.parseCommand(EmailCommand.COMMAND_WORD
                + " " + PREFIX_EMAIL_SERVICE + service.service + " " + PREFIX_EMAIL_TO + tag
                + " " + PREFIX_EMAIL_SUBJECT + subject.subject + " " + PREFIX_EMAIL_BODY + body.body);

        assertEquals(new EmailCommand(new PersonContainsTagPredicate(tag), subject, body, service), command);
    }
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java

    @Test
    public void parseCommand_displayPicture() throws  Exception {
        final DisplayPicture displayPicture  = new DisplayPicture(Integer.toString(VALID_EMAIL_AMY.hashCode()));

        DisplayPictureCommand displayPictureCommand =
                (DisplayPictureCommand) parser.parseCommand(DisplayPictureCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + displayPicture.getPath());

        assertEquals(new DisplayPictureCommand(INDEX_FIRST_PERSON, displayPicture), displayPictureCommand);
    }
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java

    @Test
    public void parseCommand_details() throws Exception {
        DetailsCommand command = (DetailsCommand) parser.parseCommand(
                DetailsCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DetailsCommand(INDEX_FIRST_PERSON), command);
    }
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java

    @Test
    public void parseCommand_location() throws Exception {
        LocationCommand command = (LocationCommand) parser.parseCommand(
                LocationCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new LocationCommand(INDEX_FIRST_PERSON), command);
    }
```
###### \java\seedu\address\logic\parser\DetailsCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.io.IOException;

import org.junit.Test;

import seedu.address.logic.commands.DetailsCommand;

public class DetailsCommandParserTest {

    private DetailsCommandParser parser = new DetailsCommandParser();

    @Test
    public void parse_validArgs_returnsSelectCommand() throws IOException {
        assertParseSuccess(parser, "1", new DetailsCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() throws IOException {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DetailsCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\DisplayPictureCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DisplayPictureCommand;
import seedu.address.model.person.DisplayPicture;

public class DisplayPictureCommandParserTest {

    private DisplayPictureCommandParser parser = new DisplayPictureCommandParser();

    @Test
    public void parse_indexSpecified_failure() throws Exception {
        final DisplayPicture displayPicture = new DisplayPicture("somepath");


        Index targetIndex = INDEX_FIRST_PERSON;

        // file path present
        String userInput = targetIndex.getOneBased() + " " +  "somepath";
        DisplayPictureCommand expectedCommand = new DisplayPictureCommand(INDEX_FIRST_PERSON,
                new DisplayPicture(displayPicture.getPath()));
        assertParseSuccess(parser, userInput, expectedCommand);

        //no file path
        userInput = targetIndex.getOneBased() + " ";
        expectedCommand = new DisplayPictureCommand(INDEX_FIRST_PERSON,
                new DisplayPicture(""));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_noFieldSpecified_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DisplayPictureCommand.MESSAGE_USAGE);

        // nothing at all
        assertParseFailure(parser, DisplayPictureCommand.COMMAND_WORD, expectedMessage);
    }
}
```
###### \java\seedu\address\logic\parser\EmailCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.EmailBuilder.DEFAULT_BODY;
import static seedu.address.testutil.EmailBuilder.DEFAULT_SERVICE;
import static seedu.address.testutil.EmailBuilder.DEFAULT_SUBJECT;
import static seedu.address.testutil.EmailBuilder.DEFAULT_TAG;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.EmailCommand;
import seedu.address.model.email.Body;
import seedu.address.model.email.Service;
import seedu.address.model.email.Subject;
import seedu.address.model.person.PersonContainsTagPredicate;
import seedu.address.testutil.EmailBuilder;

public class EmailCommandParserTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private EmailCommandParser emailCommandParser = new EmailCommandParser();

    @Test
    public void allFieldsPresent_success() throws IOException {
        EmailCommand emailCommand = new EmailBuilder().withPredicate(new PersonContainsTagPredicate(DEFAULT_TAG))
                .withSubject(new Subject(DEFAULT_SUBJECT)).withBody(new Body(DEFAULT_BODY))
                .withService(new Service(DEFAULT_SERVICE)).build();

        assertParseSuccess(emailCommandParser, EmailCommand.COMMAND_WORD + " s/"
                        + DEFAULT_SERVICE + " to/" + DEFAULT_TAG + " sub/" + DEFAULT_SUBJECT + " body/" + DEFAULT_BODY,
                new EmailCommand(new PersonContainsTagPredicate(DEFAULT_TAG), new Subject(DEFAULT_SUBJECT),
                        new Body(DEFAULT_BODY), new Service(DEFAULT_SERVICE)));
    }

    @Test
    public void optionalFieldsMissingSuccess() throws IOException {
        EmailCommand emailCommand = new EmailBuilder().withPredicate(new PersonContainsTagPredicate(DEFAULT_TAG))
                .withService(new Service(DEFAULT_SERVICE)).build();

        assertParseSuccess(emailCommandParser, EmailCommand.COMMAND_WORD + " s/"
                        + DEFAULT_SERVICE + " to/" + DEFAULT_TAG + " sub/" + " body/",
                new EmailCommand(new PersonContainsTagPredicate(DEFAULT_TAG), new Subject(""),
                        new Body(""), new Service(DEFAULT_SERVICE)));
    }

    @Test
    public void parse_compulsoryFieldMissingRecipients_failure() throws Exception {
        EmailCommand emailCommand = new EmailBuilder().withPredicate(new PersonContainsTagPredicate(DEFAULT_TAG)).build();

        assertParseFailure(emailCommandParser, EmailCommand.COMMAND_WORD + " s/"
                        + DEFAULT_SERVICE + " sub/" + " body/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_compulsoryFieldMissingService_failure() throws Exception {

        assertParseFailure(emailCommandParser, EmailCommand.COMMAND_WORD + " to/"
                        + DEFAULT_TAG + " sub/" + " body/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\LocationCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.LocationCommand;

public class LocationCommandParserTest {
    private LocationCommandParser parser = new LocationCommandParser();

    @Test
    public void parse_validArgs_returnsLocationCommand() throws Exception {
        assertParseSuccess(parser, "1", new LocationCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() throws Exception {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, LocationCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\ViewTagCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ViewTagCommand;
import seedu.address.model.person.PersonContainsTagPredicate;

public class ViewTagCommandParserTest {

    private ViewTagCommandParser parser = new ViewTagCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() throws Exception {
        assertParseFailure(parser, "   ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewTagCommand.MESSAGE_USAGE));
    }


    @Test
    public void parse_validArgs_returnsViewTagCommand() throws Exception {

        ViewTagCommand expectedViewTagCommand =
                new ViewTagCommand(new PersonContainsTagPredicate("foo"));

        //Testing when no whitespaces trailing keywords
        assertParseSuccess(parser, "foo", expectedViewTagCommand);

        //Testing when whitespaces are present before or after keyword
        assertParseSuccess(parser, " \n  foo  ", expectedViewTagCommand);
    }

}
```
###### \java\seedu\address\model\email\BodyTest.java
``` java
package seedu.address.model.email;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BodyTest {

    private Body body = new Body("gmail");
    private Body sameBody = new Body("gmail");
    private Body anotherBody = new Body("outlook");

    @Test
    public void service_equals() {
        assertTrue(body.equals(sameBody));

        assertFalse(body == null);

        assertTrue(body.equals(body));

        assertFalse(body.equals(anotherBody));

        assertEquals(body.toString(), sameBody.toString());

        assertEquals(body.hashCode(), sameBody.hashCode());

        assertNotEquals(body.hashCode(), anotherBody.hashCode());
    }
}
```
###### \java\seedu\address\model\email\ServiceTest.java
``` java
package seedu.address.model.email;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ServiceTest {
    private Service service = new Service("gmail");
    private Service sameService = new Service("gmail");
    private Service anotherService = new Service("outlook");

    @Test
    public void service_equals() {
        assertTrue(service.equals(sameService));

        assertFalse(service == null);

        assertTrue(service.equals(service));

        assertFalse(service.equals(anotherService));

        assertEquals(service.toString(), sameService.toString());
        assertEquals(service.hashCode(), sameService.hashCode());
    }
}
```
###### \java\seedu\address\model\email\SubjectTest.java
``` java
package seedu.address.model.email;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class SubjectTest {

    private Subject subject = new Subject("message");
    private Subject sameSubject = new Subject("message");
    private Subject anotherSubject = new Subject("different message");

    @Test
    public void service_equals() {
        assertTrue(subject.equals(sameSubject));

        assertFalse(subject == null);

        assertTrue(subject.equals(subject));

        assertFalse(subject.equals(anotherSubject));

        assertEquals(subject.toString(), sameSubject.toString());

        assertEquals(subject.hashCode(), sameSubject.hashCode());

        assertNotEquals(subject.hashCode(), anotherSubject.hashCode());

        assertNotEquals(subject.toString(), anotherSubject.toString());
    }
}
```
###### \java\seedu\address\model\ModelManagerTest.java
``` java

    @Test
    public void addDisplayPicture_eventRaised() throws IOException {
        ModelManager modelManager = new ModelManager();
        modelManager.addDisplayPicture(TEST_DATA_FOLDER + "1137944384.png", 1137944384);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DisplayPictureChangedEvent);
    }

    @Test
    public void selectCommandExecuted_eventRaised() throws PersonNotFoundException {
        ModelManager modelManager = new ModelManager();
        modelManager.showPersonWebpage(ALICE);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof LoadPersonWebpageEvent);
    }

    @Test
    public void selectedPerson_counterIncreased() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        UserPrefs userPrefs = new UserPrefs();
        UniqueReminderList uniqueReminders = getUniqueTypicalReminders();

        ModelManager modelManager = new ModelManager(addressBook, uniqueReminders, userPrefs);
        UpdatePopularityCounterForSelectionEvent updatePopularityCounterForSelectionEvent =
                new UpdatePopularityCounterForSelectionEvent(BENSON);
        modelManager.handleUpdatePopularityCounterForSelectionEvent(updatePopularityCounterForSelectionEvent);
        assertEquals(modelManager.getPopularContactList().get(0), BENSON);
    }

    @Test
    public void test_indexOfGivenPerson() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        UserPrefs userPrefs = new UserPrefs();
        UniqueReminderList uniqueReminders = getUniqueTypicalReminders();
        ModelManager modelManager = new ModelManager(addressBook, uniqueReminders, userPrefs);
        Index expectedIndex = Index.fromOneBased(1);

        UpdateListForSelectionEvent updateListForSelectionEvent = new UpdateListForSelectionEvent(ALICE);
        modelManager.handleUpdateListForSelectionEvent(updateListForSelectionEvent);
        assertEquals(updateListForSelectionEvent.getIndex().getZeroBased(), expectedIndex.getZeroBased());
    }
```
###### \java\seedu\address\model\person\DisplayPictureTest.java
``` java
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DISPLAYPIC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DISPLAYPIC_BOB;

import org.junit.Test;

public class DisplayPictureTest {
    @Test
    public void equals() {
        DisplayPicture standardDisplayPicture = new DisplayPicture(VALID_DISPLAYPIC_AMY);
        DisplayPicture sameDisplayPicture = new DisplayPicture(VALID_DISPLAYPIC_AMY);
        DisplayPicture differentDisplayPicture = new DisplayPicture(VALID_DISPLAYPIC_BOB);

        // same object -> returns true
        assertTrue(standardDisplayPicture.equals(standardDisplayPicture));

        // null -> returns false
        assertFalse(standardDisplayPicture == null);

        // different type -> returns false
        assertFalse(standardDisplayPicture.equals("String"));

        // different nickname -> returns false
        assertFalse(sameDisplayPicture.equals(differentDisplayPicture));

        // same nickname -> returns true
        assertTrue(standardDisplayPicture.equals(sameDisplayPicture));
    }
}
```
###### \java\seedu\address\model\person\PersonContainsTagPredicateTest.java
``` java
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonContainsTagPredicateTest {

    @Test
    public void equals() {

        String firstPredicateKeyword = "foo";
        String secondPredicateKeyword = "bar";

        PersonContainsTagPredicate firstPredicate = new PersonContainsTagPredicate(firstPredicateKeyword);
        PersonContainsTagPredicate secondPredicate = new PersonContainsTagPredicate(secondPredicateKeyword);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PersonContainsTagPredicate firstPredicateCopy = new PersonContainsTagPredicate(firstPredicateKeyword);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_personContainsTag_returnsTrue() {

        // multiple people having 'friend' tag
        PersonContainsTagPredicate predicate = new PersonContainsTagPredicate("friend");
        assertTrue(predicate.test(new PersonBuilder().withTags("friend").build()));

        // one person having the tag
        predicate = new PersonContainsTagPredicate("enemy");
        assertTrue(predicate.test(new PersonBuilder().withTags("enemy").build()));

        // mixed-case tag
        predicate = new PersonContainsTagPredicate("coLLeagUe");
        assertTrue(predicate.test(new PersonBuilder().withTags("colleague").build()));

        // no one with the tag
        predicate = new PersonContainsTagPredicate("cs2103");
        assertTrue(predicate.test(new PersonBuilder().withTags("cs2103").build()));

    }

    @Test
    public void test_personDoesNotContainTag_returnsFalse() {

        // no keywords
        PersonContainsTagPredicate predicate = new PersonContainsTagPredicate("");
        assertFalse(predicate.test(new PersonBuilder().withTags("enemy").build()));

        // not matching
        predicate = new PersonContainsTagPredicate("friend");
        assertFalse(predicate.test(new PersonBuilder().withTags("enemy").build()));

        // people associated with empty tag
        predicate = new PersonContainsTagPredicate("");
        assertFalse(predicate.test(new PersonBuilder().withTags("friend").build()));

        // keyword matches name but not tag
        predicate = new PersonContainsTagPredicate("Alice");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

    }
}
```
###### \java\seedu\address\model\person\PopularityCounterTest.java
``` java
package seedu.address.model.person;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotSame;

import org.junit.Test;

public class PopularityCounterTest {

    @Test
    public void popularityCounterEquals() {

        PopularityCounter popularityCounter = new PopularityCounter();
        PopularityCounter samePopularityCounter = new PopularityCounter();

        assertEquals(popularityCounter, samePopularityCounter);

        samePopularityCounter.increasePopularityCounter();

        assertNotSame(popularityCounter, samePopularityCounter);
    }
}
```
###### \java\seedu\address\storage\AddressBookPictureStorageTest.java
``` java
package seedu.address.storage;

import static junit.framework.TestCase.assertEquals;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class AddressBookPictureStorageTest {

    private static final String PATH = "/pictures/default.png";
    private static final String INVALID_PATH = ".2? 2./";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void createTest() {
        AddressBookPictureStorage addressBookPictureStorage = new AddressBookPictureStorage(PATH);
        assertEquals(addressBookPictureStorage.getAddressBookPicturePath(), PATH);
    }

    @Test
    public void saveAddressBookPicturePath_nullFilePath_throwsNullPointerException() throws IOException {
        thrown.expect(NullPointerException.class);
        AddressBookPictureStorage addressBookPictureStorage = new AddressBookPictureStorage(null);
        addressBookPictureStorage.createPictureStorageFolder();
    }
}
```
###### \java\seedu\address\storage\ImageDisplayPictureStorageTest.java
``` java
package seedu.address.storage;

import static junit.framework.TestCase.assertEquals;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.util.FileUtil;
import seedu.address.logic.parser.exceptions.ImageException;

public class ImageDisplayPictureStorageTest {
    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/ImageDisplayPicture/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void read_notValidPath_exceptionThrown() throws Exception {

        thrown.expect(ImageException.class);
        DisplayPictureStorage displayPictureStorage = new ImageDisplayPictureStorage();
        displayPictureStorage.readImageFromDevice("34?/32 2dsd k", 232);
        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    /**
     * Ensures no exception is thrown and command happens successfully
     * @throws IOException not expected
     */
    @Test
    public void read_validPath_success() throws IOException {
        Exception exception = null;
        try {
            DisplayPictureStorage displayPictureStorage = new ImageDisplayPictureStorage();
            displayPictureStorage.readImageFromDevice(TEST_DATA_FOLDER + "1137944384.png", 1137944384);
        } catch (Exception e) {
            exception = e;
        }
        assertEquals(exception, null);
    }

}
```
###### \java\seedu\address\storage\StorageManagerTest.java
``` java

    @Test
    public void handleDisplayPictureChangedEvent_exceptionThrown_eventRaised() throws IOException {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlAddressBookStorage("dummy"),
                new XmlRemindersStorageExceptionThrowingStub("dummy"),
                new JsonUserPrefsStorage("dummy"), new ImageDisplayPictureStorage());
        storage.handleDisplayPictureChangedEvent(new DisplayPictureChangedEvent("dummy", 123));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }
```
###### \java\seedu\address\testutil\EmailBuilder.java
``` java
package seedu.address.testutil;

import seedu.address.logic.commands.EmailCommand;
import seedu.address.model.email.Body;
import seedu.address.model.email.Service;
import seedu.address.model.email.Subject;
import seedu.address.model.person.PersonContainsTagPredicate;

/**
 * A utility class to help with building Email content
 */
public class EmailBuilder {

    public static final String DEFAULT_SERVICE = "gmail";
    public static final String DEFAULT_TAG = "friends";
    public static final String DEFAULT_SUBJECT = "hello";
    public static final String DEFAULT_BODY = "how are you?";

    private EmailCommand emailCommand;

    public EmailBuilder() {
        this.emailCommand = new EmailCommand(new PersonContainsTagPredicate(DEFAULT_TAG),
                new Subject(DEFAULT_SUBJECT), new Body(DEFAULT_BODY), new Service(DEFAULT_SERVICE));
    }

    public EmailBuilder(EmailCommand toCopy) {
        emailCommand = toCopy;
    }

    /** Assigns a service to email command */
    public EmailBuilder withService(Service service) {
        emailCommand.setService(service);
        return this;
    }

    /** Assigns a subject to email command */
    public EmailBuilder withSubject(Subject subject) {
        emailCommand.setSubject(subject);
        return this;
    }

    /** Assigns a body to email command */
    public EmailBuilder withBody(Body body) {
        emailCommand.setBody(body);
        return this;
    }

    /** Assigns a predicate to email command to represent recipients */
    public EmailBuilder withPredicate(PersonContainsTagPredicate personContainsTagPredicate) {
        emailCommand.setPredicate(personContainsTagPredicate);
        return this;
    }
    public EmailCommand build() {
        return this.emailCommand;
    }
}
```
###### \java\seedu\address\ui\DetailsPanelTest.java
``` java
package seedu.address.ui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPersonDetailsPanel;

import org.junit.Test;

import guitests.guihandles.DetailsPanelHandle;
import seedu.address.model.person.DisplayPicture;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.PersonBuilder;

public class DetailsPanelTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Person personWithNoTags = new PersonBuilder().withTags(new String[0]).build();
        DetailsPanel detailsPanel = new DetailsPanel(personWithNoTags);
        uiPartRule.setUiPart(detailsPanel);
        assertCardDisplay(detailsPanel, personWithNoTags, 1);

        // with tags
        Person personWithTags = new PersonBuilder().build();
        detailsPanel = new DetailsPanel(personWithTags);
        uiPartRule.setUiPart(detailsPanel);
        assertCardDisplay(detailsPanel, personWithTags, 2);

        // changes made to Person reflects on card
        guiRobot.interact(() -> {
            personWithTags.setName(ALICE.getName());
            personWithTags.setAddress(ALICE.getAddress());
            personWithTags.setEmail(ALICE.getEmail());
            personWithTags.setPhone(ALICE.getPhone());
            personWithTags.setNickname(ALICE.getNickname());
            personWithNoTags.setDisplayPicture(ALICE.getDisplayPicture());
            personWithTags.setTags(ALICE.getTags());
        });
        assertCardDisplay(detailsPanel, personWithTags, 2);
    }

    @Test
    public void displayImage() {
        Person personWithDisplayPicture = new PersonBuilder().withDisplayPicture("1137944384").build();
        DetailsPanel detailsPanel = new DetailsPanel(personWithDisplayPicture);
        uiPartRule.setUiPart(detailsPanel);
        assertCardDisplay(detailsPanel, personWithDisplayPicture, 1);

        // changes made to Person reflects on card
        guiRobot.interact(() -> {
            personWithDisplayPicture.setName(ALICE.getName());
            personWithDisplayPicture.setAddress(ALICE.getAddress());
            personWithDisplayPicture.setEmail(ALICE.getEmail());
            personWithDisplayPicture.setPhone(ALICE.getPhone());
            personWithDisplayPicture.setNickname(ALICE.getNickname());
            personWithDisplayPicture.setDisplayPicture(new DisplayPicture("1137944384"));
            personWithDisplayPicture.setTags(ALICE.getTags());
        });
    }

    @Test
    public void equals() {
        Person person = new PersonBuilder().build();
        DetailsPanel detailsPanel = new DetailsPanel(person);

        // same person, same index -> returns true
        DetailsPanel copy = new DetailsPanel(person);
        assertTrue(detailsPanel.equals(copy));

        // same object -> returns true
        assertTrue(detailsPanel.equals(detailsPanel));

        // null -> returns false
        assertFalse(detailsPanel.equals(null));

        // different types -> returns false
        assertFalse(detailsPanel.equals(0));

        // different person, same index -> returns false
        Person differentPerson = new PersonBuilder().withName("differentName").build();
        assertFalse(detailsPanel.equals(new DetailsPanel(differentPerson)));
    }

    /**
     * Asserts that {@code personCard} displays the details of {@code expectedPerson} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(DetailsPanel detailsPanel, ReadOnlyPerson expectedPerson, int expectedId) {
        guiRobot.pauseForHuman();

        DetailsPanelHandle detailsPanelHandle = new DetailsPanelHandle(detailsPanel.getRoot());

        // verify person details are displayed correctly
        assertCardDisplaysPersonDetailsPanel(expectedPerson, detailsPanelHandle);
    }
}
```
###### \java\seedu\address\ui\PopularContactCardTest.java
``` java
package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPopularPerson;

import org.junit.Test;

import guitests.guihandles.PopularContactCardHandle;
import seedu.address.model.person.DisplayPicture;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.PersonBuilder;

public class PopularContactCardTest extends GuiUnitTest {

    @Test
    public void displayImage() {
        Person personWithDisplayPicture = new PersonBuilder().withDisplayPicture("1137944384").build();
        PopularContactCard popularContactCard = new PopularContactCard(personWithDisplayPicture, 1);
        uiPartRule.setUiPart(popularContactCard);
        assertCardDisplay(popularContactCard, personWithDisplayPicture, 1);

        // changes made to Person reflects on card
        guiRobot.interact(() -> {
            personWithDisplayPicture.setName(ALICE.getName());
            personWithDisplayPicture.setAddress(ALICE.getAddress());
            personWithDisplayPicture.setEmail(ALICE.getEmail());
            personWithDisplayPicture.setPhone(ALICE.getPhone());
            personWithDisplayPicture.setNickname(ALICE.getNickname());
            personWithDisplayPicture.setDisplayPicture(new DisplayPicture("1137944384"));
            personWithDisplayPicture.setTags(ALICE.getTags());
        });
    }

    @Test
    public void equals() {
        Person person = new PersonBuilder().build();
        PopularContactCard popularContactCard = new PopularContactCard(person, 0);

        // same object -> returns true
        assertTrue(popularContactCard.equals(popularContactCard));

        // null -> returns false
        assertFalse(popularContactCard.equals(null));

        // different types -> returns false
        assertFalse(popularContactCard.equals(0));

        // different person, same index -> returns false
        Person differentPerson = new PersonBuilder().withName("differentName").build();
        assertFalse(popularContactCard.equals(new PopularContactCard(differentPerson, 0)));

        // same person, different index -> returns false
        assertFalse(popularContactCard.equals(new PopularContactCard(person, 1)));
    }

    /**
     * Asserts that {@code popularContactCard} displays the details of {@code expectedPerson} correctly and matches
     * {@code expectedRank}.
     */
    private void assertCardDisplay(PopularContactCard popularContactCard, ReadOnlyPerson expectedPerson,
                                   int expectedRank) {
        guiRobot.pauseForHuman();

        PopularContactCardHandle popularContactCardHandle = new PopularContactCardHandle(popularContactCard.getRoot());

        // verify id is displayed correctly
        assertEquals("#" + Integer.toString(expectedRank) + " ", popularContactCardHandle.getRank());

        // verify person details are displayed correctly
        assertCardDisplaysPopularPerson(expectedPerson, popularContactCardHandle);
    }
}
```
###### \java\seedu\address\ui\PopularContactListPanelTest.java
``` java
package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getPopularPersons;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPopularPerson;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEquals;
import static seedu.address.ui.testutil.GuiTestAssert.assertPopularCardEquals;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import guitests.guihandles.PersonListPanelHandle;
import guitests.guihandles.PopularContactCardHandle;
import guitests.guihandles.PopularContactsPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.ReadOnlyPerson;

public class PopularContactListPanelTest extends GuiUnitTest {
    private static final ObservableList<ReadOnlyPerson> TYPICAL_PERSONS =
            FXCollections.observableList(getPopularPersons());

    private PopularContactsPanelHandle popularContactsPanelHandle;

    private PersonListPanelHandle personListPanelHandle;

    @Before
    public void setUp() {
        PopularContactPanel popularContactPanel = new PopularContactPanel(TYPICAL_PERSONS);
        PersonListPanel personListPanel = new PersonListPanel(TYPICAL_PERSONS);

        uiPartRule.setUiPart(popularContactPanel);

        popularContactsPanelHandle = new PopularContactsPanelHandle(getChildNode(popularContactPanel.getRoot(),
                popularContactsPanelHandle.PERSON_LIST_VIEW_ID));

        personListPanelHandle = new PersonListPanelHandle(getChildNode(personListPanel.getRoot(),
                personListPanelHandle.PERSON_LIST_VIEW_ID));
    }

    @Test
    public void display() {
        for (int i = 0; i < TYPICAL_PERSONS.size(); i++) {
            popularContactsPanelHandle.navigateToCard(TYPICAL_PERSONS.get(i));
            ReadOnlyPerson expectedPerson = TYPICAL_PERSONS.get(i);
            PopularContactCardHandle actualCard = popularContactsPanelHandle.getPopularContactCardHandle(i);

            assertCardDisplaysPopularPerson(expectedPerson, actualCard);
            assertEquals("#" + Integer.toString(i + 1) + " ", actualCard.getRank());
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        popularContactsPanelHandle.select(INDEX_SECOND_PERSON.getZeroBased());
        personListPanelHandle.select(INDEX_SECOND_PERSON.getZeroBased());
        guiRobot.pauseForHuman();

        PopularContactCardHandle expectedCard = popularContactsPanelHandle
                .getPopularContactCardHandle(INDEX_SECOND_PERSON.getZeroBased());
        PopularContactCardHandle selectedCard = popularContactsPanelHandle.getHandleToSelectedCard();

        PersonCardHandle expectedPersonCard = personListPanelHandle
                .getPersonCardHandle(INDEX_SECOND_PERSON.getZeroBased());
        PersonCardHandle selectedPersonCard = personListPanelHandle.getHandleToSelectedCard();

        assertCardEquals(expectedPersonCard, selectedPersonCard);
        assertPopularCardEquals(expectedCard, selectedCard);

    }
}
```
###### \java\seedu\address\ui\testutil\GuiTestAssert.java
``` java
    /**
     * Asserts that the list in {@code uniqueReminderList} displays the details of {@code reminders} correctly and
     * in the correct order.
     */
    public static void assertListMatchingReminders(ReadOnlyUniqueReminderList uniqueReminderList, Reminder... reminders) {
        for (int i = 0; i < reminders.length; i++) {
            assertEquals(uniqueReminderList.asObservableList().get(i), reminders[i]);

        }
    }
```
###### \java\systemtests\DetailsCommandSystemTest.java
``` java
package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.DetailsCommand.MESSAGE_DETAILS_PERSON_SUCCESS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ClearCommand;

import seedu.address.logic.commands.DetailsCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;

public class DetailsCommandSystemTest extends AddressBookSystemTest {
    @Test
    public void details() {
        /* Case: select the first card in the person list, command with leading spaces and trailing spaces
         * -> selected
         */
        String command = "   " + DetailsCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased() + "   ";
        assertCommandSuccess(command, INDEX_FIRST_PERSON);

        /* Case: select the last card in the person list -> selected */
        Index personCount = Index.fromOneBased(getTypicalPersons().size());
        command = DetailsCommand.COMMAND_WORD + " " + personCount.getOneBased();
        assertCommandSuccess(command, personCount);

        /* Case: undo previous selection -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo selecting last card in the list -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: select the middle card in the person list -> selected */
        Index middleIndex = Index.fromOneBased(personCount.getOneBased() / 2);
        command = DetailsCommand.COMMAND_WORD + " " + middleIndex.getOneBased();
        assertCommandSuccess(command, middleIndex);

        /* Case: invalid index (size + 1) -> rejected */
        int invalidIndex = getModel().getFilteredPersonList().size() + 1;
        assertCommandFailure(DetailsCommand.COMMAND_WORD + " " + invalidIndex, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* Case: select the current selected card -> selected */
        assertCommandSuccess(command, middleIndex);

        /* Case: filtered person list, select index within bounds of address book but out of bounds of person list
         * -> rejected
         */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        invalidIndex = getModel().getAddressBook().getPersonList().size();
        assertCommandFailure(DetailsCommand.COMMAND_WORD + " " + invalidIndex, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* Case: filtered person list, select index within bounds of address book and person list -> selected */
        Index validIndex = Index.fromOneBased(1);
        assert validIndex.getZeroBased() < getModel().getFilteredPersonList().size();
        command = DetailsCommand.COMMAND_WORD + " " + validIndex.getOneBased();
        assertCommandSuccess(command, validIndex);

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(DetailsCommand.COMMAND_WORD + " " + 0,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DetailsCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(DetailsCommand.COMMAND_WORD + " " + -1,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DetailsCommand.MESSAGE_USAGE));

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(DetailsCommand.COMMAND_WORD + " abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DetailsCommand.MESSAGE_USAGE));

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(DetailsCommand.COMMAND_WORD + " 1 abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DetailsCommand.MESSAGE_USAGE));

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("SeLeCt 1", MESSAGE_UNKNOWN_COMMAND);

        /* Case: select from empty address book -> rejected */
        executeCommand(ClearCommand.COMMAND_WORD);
        assert getModel().getAddressBook().getPersonList().size() == 0;
        assertCommandFailure(DetailsCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased(),
                MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays the success message of executing select command with the {@code expectedSelectedCardIndex}
     * of the selected person, and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the command box has the default style class and the status bar remain unchanged. The resulting
     * browser url and selected card will be verified if the current selected card and the card at
     * {@code expectedSelectedCardIndex} are different.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        String expectedResultMessage = String.format(
                MESSAGE_DETAILS_PERSON_SUCCESS, expectedSelectedCardIndex.getOneBased());
        int preExecutionSelectedCardIndex = getPersonListPanel().getSelectedCardIndex();

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        if (preExecutionSelectedCardIndex == expectedSelectedCardIndex.getZeroBased()) {
            assertSelectedCardUnchangedForDetails();
        } else {
            assertSelectedCardChangedForDetails(expectedSelectedCardIndex);
        }

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
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
}
```
###### \java\systemtests\EmailCommandSystemTest.java
``` java
package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.EmailBuilder.DEFAULT_BODY;
import static seedu.address.testutil.EmailBuilder.DEFAULT_SERVICE;
import static seedu.address.testutil.EmailBuilder.DEFAULT_SUBJECT;
import static seedu.address.testutil.EmailBuilder.DEFAULT_TAG;

import org.junit.Test;

import seedu.address.logic.commands.EmailCommand;
import seedu.address.model.Model;

public class EmailCommandSystemTest extends AddressBookSystemTest {


    @Test
    public void sendingEmail() throws Exception {
        /** With all prefixes */
        String command  = EmailCommand.COMMAND_WORD + " s/" + DEFAULT_SERVICE + " to/" + DEFAULT_TAG + " sub/"
                + DEFAULT_SUBJECT + " body/" + DEFAULT_BODY;
        Model model = getModel();
        assertCommandSuccess(command, model, "");

        /** With optional prefixes missing*/
        command  = EmailCommand.COMMAND_WORD + " s/" + DEFAULT_SERVICE + " to/" + DEFAULT_TAG + " sub/"
                + DEFAULT_SUBJECT;
        model = getModel();
        assertCommandSuccess(command, model, "");

        /** With compulsory prefix missing*/
        command  = EmailCommand.COMMAND_WORD + " to/" + DEFAULT_TAG + " sub/"
                + DEFAULT_SUBJECT;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));
    }

    /**
     * To assert successful command
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        expectedModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        assertCommandBoxShowsDefaultStyle();
        assertSelectedCardUnchanged();
    }

    /**
     * To assert failed command
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}

```
###### \java\systemtests\PopularContactsSystemTest.java
``` java
package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.DetailsCommand.MESSAGE_DETAILS_PERSON_SUCCESS;
import static seedu.address.logic.commands.LocationCommand.MESSAGE_FIND_LOCATION_SUCCESS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.KEYWORD_TAG_ENEMY;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DetailsCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.LocationCommand;
import seedu.address.logic.commands.ViewTagCommand;
import seedu.address.model.Model;

public class PopularContactsSystemTest extends AddressBookSystemTest {

    @Test
    public void favouriteContactsTest() {

        /* Selecting first one to increase popularity counter by one */
        String command = "   " + DetailsCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased() + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredListPopularContacts(expectedModel, ALICE, BENSON, CARL, DANIEL, ELLE);
        assertCommandSuccessForDetails(command, INDEX_FIRST_PERSON);
        assertSelectedCardChangedForDetails(INDEX_FIRST_PERSON);


        /* Executing viewtag to increase popularity counter by one for everyone in the tag */
        command = "    " + ViewTagCommand.COMMAND_WORD + " " + KEYWORD_TAG_ENEMY + "    ";
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, ELLE);
        ModelHelper.setFilteredListPopularContacts(expectedModel, ALICE, ELLE, BENSON, CARL, DANIEL);
        assertCommandSuccessForViewTag(command, expectedModel);

        /* Executing location to increase popularity counter by one for everyone in the tag */
        command = "    " + LocationCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased() + "    ";
        expectedModel = getModel();
        ModelHelper.setFilteredListPopularContacts(expectedModel, ELLE, ALICE, BENSON, CARL, DANIEL);
        assertCommandSuccessForLocation(command, INDEX_FIRST_PERSON, expectedModel);

        /* Listing all contacts */
        command = "    " + ListCommand.COMMAND_WORD;
        executeCommand(command);

        /* Executing viewtag to increase popularity counter by one for everyone in the tag */
        command = "    " + ViewTagCommand.COMMAND_WORD + " " + "owesmoney";
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, BENSON);
        ModelHelper.setFilteredListPopularContacts(expectedModel, ELLE, ALICE, BENSON, CARL, DANIEL);
        assertCommandSuccessForViewTag(command, expectedModel);

    }

    /**
     * Command success for Select
     * @param command
     * @param expectedSelectedCardIndex
     */
    private void assertCommandSuccessForDetails(String command, Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        String expectedResultMessage = String.format(
                MESSAGE_DETAILS_PERSON_SUCCESS, expectedSelectedCardIndex.getOneBased());

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_PERSONS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccessForViewTag(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_PERSONS_LISTED_OVERVIEW, expectedModel.getFilteredPersonList().size());

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        if (expectedModel.getFilteredPersonList().size() != 0) {
            assertStatusBarUnchangedExceptSyncStatus();
        } else {
            assertStatusBarUnchanged();
        }

    }

    /**
     * Executes command and checks the state and equality of model
     * @param command
     * @param expectedSelectedCardIndex
     * @param expectedModel
     */
    private void assertCommandSuccessForLocation(String command, Index expectedSelectedCardIndex, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_FIND_LOCATION_SUCCESS,
                expectedModel.getFilteredPersonList().get(expectedSelectedCardIndex.getZeroBased()).getName(),
                expectedModel.getFilteredPersonList().get(expectedSelectedCardIndex.getZeroBased()).getAddress());

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

}


```
###### \java\systemtests\ViewTagCommandSystemTest.java
``` java
package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.KEYWORD_TAG_FRIEND;

import org.junit.Test;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.ViewTagCommand;
import seedu.address.model.Model;

public class ViewTagCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void viewtag() {

        /* Case: Multiple people having one Tag, command with leading spaces and trailing spaces
         * -> 2 persons found
         */
        String command = "    " + ViewTagCommand.COMMAND_WORD + " " + KEYWORD_TAG_FRIEND + "    ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, ALICE, BENSON); // Alice and Benson have tags as "friend"
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();


        /* Case: Repeat previous command with no extra spaces -> 2 persons found */
        command = ViewTagCommand.COMMAND_WORD + " " + KEYWORD_TAG_FRIEND;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: Only person having the given tag -> 1 person found */
        command = ViewTagCommand.COMMAND_WORD + " enemy";
        ModelHelper.setFilteredList(expectedModel, ELLE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: When multiple people have a tag with other tags too -> 2 persons found */
        command = ViewTagCommand.COMMAND_WORD + " relative";
        ModelHelper.setFilteredList(expectedModel, CARL, DANIEL); // Carl and Daniel have 2 tags and one is "family"
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: undo previous find command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous find command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: find same persons in address book with tag after deleting 1 of them -> 1 person found */
        executeCommand(DeleteCommand.COMMAND_WORD + " 1");
        assert !getModel().getAddressBook().getPersonList().contains(CARL);
        command = ViewTagCommand.COMMAND_WORD + " relative";
        expectedModel = getModel();

        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person in address book, keyword is same as name but of different case -> 1 person found */
        command = ViewTagCommand.COMMAND_WORD + " rElATivE";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person in address book, keyword is substring of name -> 0 persons found */
        command = ViewTagCommand.COMMAND_WORD + " relat";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person in address book, name is substring of keyword -> 0 persons found */
        command = ViewTagCommand.COMMAND_WORD + " myrelative";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find persons for a tag which doesn't exist-> 0 persons found */
        command = ViewTagCommand.COMMAND_WORD + " school";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person in empty address book -> 0 persons found */
        executeCommand(ClearCommand.COMMAND_WORD);
        assert getModel().getAddressBook().getPersonList().size() == 0;
        command = ViewTagCommand.COMMAND_WORD + " " + KEYWORD_TAG_FRIEND;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "viEwTaG owesMONey";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);


    }



    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_PERSONS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_PERSONS_LISTED_OVERVIEW, expectedModel.getFilteredPersonList().size());

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        if (expectedModel.getFilteredPersonList().size() != 0) {
            assertStatusBarUnchangedExceptSyncStatus();
        } else {
            assertStatusBarUnchanged();
        }

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
}
```
