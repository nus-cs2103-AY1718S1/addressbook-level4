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
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ChangeFontSizeEvent;
import seedu.address.commons.events.ui.SwitchThemeRequestEvent;
import seedu.address.model.FontSizeUnit;
import seedu.address.model.ListingUnit;
import seedu.address.model.module.ReadOnlyLesson;


/**
 * An UI component that displays information of a {@code Person}.
 */
public class LessonListCard extends UiPart<Region> {

    private static final String FXML = "LessonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final ReadOnlyLesson lesson;

    private final ImageView star = new ImageView("/images/bookmark.png");

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
    private Label venue;
    @FXML
    private Label timeSlot;
    @FXML
    private FlowPane lecturers;

    @FXML
    private Label bookmark;


    public LessonListCard(ReadOnlyLesson lesson, int displayedIndex) {
        super(FXML);
        this.lesson = lesson;
        id.setText(displayedIndex + ". ");
        initLecturers(lesson);
        bindListeners(lesson);
        registerAsAnEventHandler(this);
        FontSizeUnit currFontSize = FontSizeUnit.getCurrentFontSizeUnit();
        setFontSizeUnit(currFontSize);
        switchCard();
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
        venue.textProperty().bind(Bindings.convert(lesson.locationProperty()));
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
     * Change the card state to hide irrelevant information and only show address
     */
    private void switchToModuleCard() {
        code.setVisible(true);
        venue.setVisible(false);
        group.setVisible(false);
        timeSlot.setVisible(false);
        classType.setVisible(false);
        lecturers.setVisible(false);
    }

    /**
     * Change the card state to hide irrelevant information and only show phone
     */
    private void switchToLocationCard() {
        code.setVisible(false);
        venue.setVisible(true);
        group.setVisible(false);
        timeSlot.setVisible(false);
        classType.setVisible(false);
        lecturers.setVisible(false);
        bookmark.setVisible(false);

    }

    /**
     * Change the card state to hide irrelevant information and only show lesson
     */
    private void switchToLessonCard() {
        if (lesson.isMarked()) {
            star.setFitWidth(30);
            star.setFitHeight(30);
            bookmark.setGraphic(star);
            bookmark.setVisible(true);
        }
    }

    /**
     * Change the card state depending on the current listing unit
     */
    private void switchCard() {
        switch (ListingUnit.getCurrentListingUnit()) {
        case LOCATION:
            switchToLocationCard();
            break;

        case MODULE:
            switchToModuleCard();
            break;

        default:
            switchToLessonCard();

        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof LessonListCard)) {
            return false;
        }

        // state check
        LessonListCard card = (LessonListCard) other;
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
        venue.setStyle("-fx-font-size: " + fontSize + ";");
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
