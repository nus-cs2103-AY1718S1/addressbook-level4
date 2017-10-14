package seedu.address.ui;

import static seedu.address.logic.commands.CustomiseCommand.FONT_SIZE_LARGE;
import static seedu.address.logic.commands.CustomiseCommand.FONT_SIZE_NORMAL;
import static seedu.address.logic.commands.CustomiseCommand.FONT_SIZE_SMALL;
import static seedu.address.logic.commands.CustomiseCommand.FONT_SIZE_XLARGE;
import static seedu.address.logic.commands.CustomiseCommand.FONT_SIZE_XSMALL;

import com.google.common.eventbus.Subscribe;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.commons.events.ui.ChangeFontSizeEvent;
import seedu.address.model.FontSizeUnit;
import seedu.address.model.ListingUnit;
import seedu.address.model.module.ReadOnlyLesson;


/**
 * An UI component that displays information of a {@code Person}.
 */
public class LessonCard extends UiPart<Region> {

    private static final String FXML = "LessonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final ReadOnlyLesson lesson;

    @FXML
    private HBox cardPane;
    @FXML
    private Label code;
    @FXML
    private Label id;
    @FXML
    private Label classType;
    @FXML
    private Label group;
    @FXML
    private Label location;
    @FXML
    private Label timeSlot;
    @FXML
    private FlowPane lecturers;

    public LessonCard(ReadOnlyLesson lesson, int displayedIndex) {
        super(FXML);
        this.lesson = lesson;
        id.setText(displayedIndex + ". ");
        initLecturers(lesson);
        bindListeners(lesson);
        registerAsAnEventHandler(this);
        ListingUnit currentUnit = ListingUnit.getCurrentListingUnit();
        FontSizeUnit currFontSize = FontSizeUnit.getCurrentFontSizeUnit();
        setFontSizeUnit(currFontSize);

        switch (currentUnit) {
        case MODULE:
            switchToModuleCard();
            break;

        case LOCATION:
            switchToLocationCard();
            break;

        case LESSON:
            switchToLessonCard();
            break;

        default:
        }
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyLesson lesson) {
        code.textProperty().bind(Bindings.convert(lesson.codeProperty()));
        classType.textProperty().bind(Bindings.convert(lesson.classTypeProperty()));
        group.textProperty().bind(Bindings.convert(lesson.groupProperty()));
        timeSlot.textProperty().bind(Bindings.convert(lesson.timeSlotProperty()));
        location.textProperty().bind(Bindings.convert(lesson.locationProperty()));
        lesson.lecturersProperty().addListener((observable, oldValue, newValue) -> {
            lecturers.getChildren().clear();
            lesson.getLecturers().forEach(lecturer -> lecturers.getChildren()
                    .add(new Label(lecturer.lecturerName)));
        });
    }


    private void initLecturers(ReadOnlyLesson lesson) {
        lesson.getLecturers().forEach(lecturer -> lecturers.getChildren().add(new Label(lecturer.lecturerName)));
    }

    /**
     * change the card state to hide irrelevant information and only show address
     */
    private void switchToModuleCard() {
        code.setVisible(true);
        location.setVisible(false);
        group.setVisible(false);
        timeSlot.setVisible(false);
        classType.setVisible(false);
        lecturers.setVisible(false);
        code.setStyle("-fx-font: 16 arial;");
    }

    /**
     * change the card state to hide irrelevant information and only show phone
     */
    private void switchToLocationCard() {
        code.setVisible(false);
        location.setVisible(true);
        group.setVisible(false);
        timeSlot.setVisible(false);
        classType.setVisible(false);
        lecturers.setVisible(false);
        location.setStyle("-fx-font: 16 arial;");

    }

    /**
     * change the card state to hide irrelevant information and only show lesson
     */
    private void switchToLessonCard() {
        code.setVisible(true);
        location.setVisible(true);
        group.setVisible(true);
        timeSlot.setVisible(true);
        classType.setVisible(true);
        lecturers.setVisible(true);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof LessonCard)) {
            return false;
        }

        // state check
        LessonCard card = (LessonCard) other;
        return id.getText().equals(card.id.getText())
                && lesson.equals(card.lesson);
    }

    @Subscribe
    private void handleChangeFontSizeEvent(ChangeFontSizeEvent event) {
        setFontSize(event.message);
    }

    private void setFontSize(String userPref) {
        switch (userPref) {
        case FONT_SIZE_XSMALL:
            setFontSizeHelper("x-small");
            break;

        case FONT_SIZE_SMALL:
            setFontSizeHelper("small");
            break;

        case FONT_SIZE_NORMAL:
            setFontSizeHelper("normal");
            break;

        case FONT_SIZE_LARGE:
            setFontSizeHelper("x-large");
            break;

        case FONT_SIZE_XLARGE:
            setFontSizeHelper("xx-large");
            break;

        default:
            break;
        }
    }

    private void setFontSizeHelper(String fontSize) {
        code.setStyle("-fx-font-size: " + fontSize + ";");
        id.setStyle("-fx-font-size: " + fontSize + ";");
        location.setStyle("-fx-font-size: " + fontSize + ";");
        classType.setStyle("-fx-font-size: " + fontSize + ";");
        timeSlot.setStyle("-fx-font-size: " + fontSize + ";");
        group.setStyle("-fx-font-size: " + fontSize + ";");
        lecturers.setStyle("-fx-font-size: " + fontSize + ";");
    }

    private void setFontSizeUnit(FontSizeUnit currFontSizeUnit) {
        switch (currFontSizeUnit) {
        case FONT_SIZE_XSMALL_UNIT:
            setFontSize(FONT_SIZE_XSMALL);
            break;

        case FONT_SIZE_SMALL_UNIT:
            setFontSize(FONT_SIZE_SMALL);
            break;

        case FONT_SIZE_NORMAL_UNIT:
            setFontSize(FONT_SIZE_NORMAL);
            break;

        case FONT_SIZE_LARGE_UNIT:
            setFontSize(FONT_SIZE_LARGE);
            break;

        case FONT_SIZE_XLARGE_UNIT:
            setFontSize(FONT_SIZE_XLARGE);
            break;

        default:
            break;
        }
    }

}
