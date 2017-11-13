# zacharytang-unused
###### \CombinedTimetableDisplay.fxml
``` fxml

<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.control.SplitPane?>
<?import javafx.scene.layout.AnchorPane?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.layout.StackPane?>

<StackPane prefHeight="400.0" prefWidth="600.0" xmlns="http://javafx.com/javafx/8"
           xmlns:fx="http://javafx.com/fxml/1">
    <SplitPane dividerPositions="0.2" orientation="VERTICAL" prefHeight="400.0" prefWidth="600.0"
               AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0"
               AnchorPane.topAnchor="0.0">
        <AnchorPane styleClass="combined-timetable-label">
            <GridPane layoutX="151.0" layoutY="26.0" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0"
                      AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0">
                <columnConstraints>
                    <ColumnConstraints hgrow="SOMETIMES" maxWidth="300" minWidth="300.0" prefWidth="300"/>
                    <ColumnConstraints hgrow="SOMETIMES"/>
                </columnConstraints>
                <rowConstraints>
                    <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES"/>
                </rowConstraints>
                <HBox alignment="CENTER_RIGHT">
                    <Label alignment="CENTER" styleClass="combined-timetable-main-label" text="Timetables Displayed: ">
                        <HBox.margin>
                            <Insets right="2.5"/>
                        </HBox.margin>
                    </Label>
                </HBox>
                <HBox alignment="CENTER_LEFT" GridPane.columnIndex="1">
                    <Label fx:id="names" styleClass="combined-timetable-names" text="Label">
                        <HBox.margin>
                            <Insets left="2.5"/>
                        </HBox.margin>
                    </Label>
                </HBox>
            </GridPane>
        </AnchorPane>
        <AnchorPane fx:id="timetablePlaceholder" minHeight="0.0" minWidth="0.0" prefHeight="100.0" prefWidth="160.0"/>
    </SplitPane>
</StackPane>
```
###### \CombinedTimetableDisplay.java
``` java
/**
 * Display for the combined timetable command
 */
public class CombinedTimetableDisplay extends UiPart<Region> {

    public static final String FXML = "CombinedTimetableDisplay.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private TimetableDisplay timetableDisplay;

    @FXML
    private Label names;

    @FXML
    private AnchorPane timetablePlaceholder;

    public CombinedTimetableDisplay() {
        super(FXML);

        timetableDisplay = new TimetableDisplay(null);
        timetablePlaceholder.getChildren().add(timetableDisplay.getRoot());

        registerAsAnEventHandler(this);
    }

    /**
     * Refreshes the timetable display upon command
     */
    private void loadPersons(List<ReadOnlyPerson> persons) {
        names.setText(generateNamesString(persons));

        timetablePlaceholder.getChildren().removeAll();

        ArrayList<Timetable> timetables = new ArrayList<>();
        for (ReadOnlyPerson person : persons) {
            timetables.add(person.getTimetable());
        }
        timetableDisplay = new TimetableDisplay(timetables);
        timetablePlaceholder.getChildren().add(timetableDisplay.getRoot());
    }

    /**
     * Creates a string to display all names for the combined timetable displayed
     */
    private String generateNamesString(List<ReadOnlyPerson> persons) {
        StringBuilder names = new StringBuilder();
        for (ReadOnlyPerson person : persons) {
            names.append("[");
            names.append(person.getName().toString());
            names.append("] ");
        }
        return names.toString();
    }

    @Subscribe
    private void handleTimetableDisplayEvent(TimetableDisplayEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersons(event.personsToDisplay);
    }
}
```
###### \PersonInfoOverview.fxml
``` fxml
<StackPane styleClass="info-panel" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
    <!--Edit dividerPositions below to adjust vertical divider position between the person info and the timetable area -->
    <SplitPane dividerPositions="0.3" orientation="VERTICAL">
        <AnchorPane>
            <!--Edit dividerPositions below to adjust the divider position between the person photo and the person details-->
            <SplitPane dividerPositions="0.48333333333333334" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0">
                <AnchorPane fx:id="contactPhotoPane" minHeight="0.0" minWidth="0.0" styleClass="info-name-cell">
               <children>
                  <ImageView fx:id="contactPhoto" layoutX="1.0" layoutY="-1.0" pickOnBounds="true" preserveRatio="true" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0">

                  </ImageView>
               </children></AnchorPane>

                <AnchorPane minHeight="0.0" minWidth="0.0" styleClass="info-cell">
                    <VBox alignment="CENTER_LEFT" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="20.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0">
                        <HBox alignment="CENTER_LEFT" spacing="5">
                            <Label styleClass="display_small_label" text="Name:" />
                            <Label fx:id="name" styleClass="display_small_value" text="\$name" />
                        </HBox>
                        <HBox alignment="CENTER_LEFT" spacing="5">
                            <Label styleClass="display_small_label" text="Gender:" />
                            <Label fx:id="gender" styleClass="display_small_value" text="\$gender" />
                        </HBox>
                        <HBox alignment="CENTER_LEFT" spacing="5">
                            <Label styleClass="display_small_label" text="Matriculation No:" />
                            <Label fx:id="matricNo" styleClass="display_small_value" text="\$matricNo" />
                        </HBox>
                        <HBox alignment="CENTER_LEFT" spacing="5">
                            <Label styleClass="display_small_label" text="Phone No:" />
                            <Label fx:id="phone" styleClass="display_small_value" text="\$phone" />
                        </HBox>
                        <HBox alignment="CENTER_LEFT" spacing="5">
                            <Label styleClass="display_small_label" text="Address:" />
                            <Label fx:id="address" styleClass="display_small_value" text="\$address" wrapText="true"/>
                        </HBox>
                        <HBox alignment="CENTER_LEFT" spacing="5">
                            <Label styleClass="display_small_label" text="Email:" />
                            <Label fx:id="email" styleClass="display_small_value" text="\$email" />
                        </HBox>
                        <HBox alignment="CENTER_LEFT" spacing="5">
                            <Label styleClass="display_small_label" text="Birthday:" />
                            <Label fx:id="birthday" styleClass="display_small_value" text="\$birthday" />
                        </HBox>
                        <HBox alignment="CENTER_LEFT" spacing="5">
                            <Label styleClass="display_small_label" text="Remark:" />
                            <Label fx:id="remark" styleClass="display_small_value" text="\$remark" wrapText="true"/>
                        </HBox>
                    </VBox>
                </AnchorPane>
            </SplitPane>
        </AnchorPane>
        <AnchorPane fx:id="timetablePlaceholder" />
    </SplitPane>
</StackPane>
```
###### \PersonInfoOverview.java
``` java
/**
 * A UI component that displays a person's data on the main panel
 */
public class PersonInfoOverview extends UiPart<Region> {

    private static final String FXML = "PersonInfoOverview.fxml";
    private ReadOnlyPerson person;

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private TimetableDisplay timetableDisplay;

    @FXML
    private Label name;
    @FXML
    private Label gender;
    @FXML
    private Label matricNo;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label birthday;
    @FXML
    private Label remark;
    @FXML
    private AnchorPane contactPhotoPane;
    @FXML
    private ImageView contactPhoto;

    @FXML
    private AnchorPane timetablePlaceholder;

    public PersonInfoOverview() {
        super(FXML);

        this.person = null;
        loadDefaultPerson();

        contactPhoto.fitWidthProperty().bind(contactPhotoPane.widthProperty());
        contactPhoto.fitHeightProperty().bind(contactPhotoPane.heightProperty());
        registerAsAnEventHandler(this);
    }

    /**
     * Loads the default person when the app is first started
     */
    private void loadDefaultPerson() {
        name.setText("No person selected");
        gender.setText("");
        matricNo.setText("");
        phone.setText("");
        address.setText("");
        email.setText("");
        birthday.setText("");
        remark.setText("");

        setDefaultContactPhoto();

        timetableDisplay = new TimetableDisplay(null);
        timetablePlaceholder.getChildren().add(timetableDisplay.getRoot());
    }

    /**
     * Updates info with person selected
     */
    private void loadPerson(ReadOnlyPerson person) {
        this.person = person;

        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        gender.textProperty().bind(Bindings.convert(person.genderProperty()));
        matricNo.textProperty().bind(Bindings.convert(person.matricNoProperty()));
        phone.textProperty().bind(Bindings.convert(person.phoneProperty()));
        address.textProperty().bind(Bindings.convert(person.addressProperty()));
        email.textProperty().bind(Bindings.convert(person.emailProperty()));
        birthday.textProperty().bind(Bindings.convert(person.birthdayProperty()));
        remark.textProperty().bind(Bindings.convert(person.remarkProperty()));

        loadPhoto(person);

        timetablePlaceholder.getChildren().removeAll();

        ArrayList<Timetable> timetableToDisplay = new ArrayList<>();
        timetableToDisplay.add(person.getTimetable());
        timetableDisplay = new TimetableDisplay(timetableToDisplay);
        timetablePlaceholder.getChildren().add(timetableDisplay.getRoot());
    }

```
###### \PersonInfoOverview.java
``` java
    @Subscribe
    private void handlePersonSelectedEvent(PersonSelectedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPerson(event.person);
    }

    @Subscribe
    private void handlePersonPanelSelectionChangeEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPerson(event.getNewSelection().person);
    }
}
```
