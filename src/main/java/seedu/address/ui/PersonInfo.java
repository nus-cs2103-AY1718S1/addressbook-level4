package seedu.address.ui;

import java.util.logging.Logger;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.ReadOnlyPerson;

//@@author pohjie
/**
 * Panel containing the list of persons.
 */
public class PersonInfo extends UiPart<Region> {
    private static final String FXML = "PersonInfo.fxml";
    private static final String JOIN_DATE = "Joined date: ";

    public final ReadOnlyPerson person;

    private final Logger logger = LogsCenter.getLogger(PersonInfo.class);

    @FXML
    private ImageView profilePic;
    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    //@@author ReneeSeet
    @FXML
    private Label date;
    //@@author pohjie
    @FXML
    private PieChart attendance;

    /**
     * Upon the PersonPanelSelectionChangedEvent, the selected person will be loaded in this panel.
     * @param person
     */
    public PersonInfo (ReadOnlyPerson person) {
        super(FXML);
        this.person = person;
        profilePic.setImage(new Image(person.getProfilePic().toString()));
        name.setText(person.getName().toString());
        address.setText(person.getAddress().toString());
        phone.setText(person.getPhone().toString());
        email.setText(person.getEmail().toString());
        //@@author ReneeSeet
        date.setText(JOIN_DATE + person.getJoinDate().toString());
        //@@author pohjie
        bindListeners(person);

        /**
         * Note that in this version, attendanceData is not bound to the person.
         * This will be implemented in future versions when we implement the addAttendance feature.
         */
        ObservableList<PieChart.Data> attendanceData = FXCollections.observableArrayList(
                new PieChart.Data("Present", person.getAttendance().getAttended()),
                new PieChart.Data("Absent", person.getAttendance().getMissed()));

        attendance.setData(attendanceData);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     * @param person
     */
    private void bindListeners(ReadOnlyPerson person) {
        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        phone.textProperty().bind(Bindings.convert(person.phoneProperty()));
        address.textProperty().bind(Bindings.convert(person.addressProperty()));
        email.textProperty().bind(Bindings.convert(person.emailProperty()));
        date.textProperty().bind(Bindings.convert(person.joinDateProperty()));
    }

    /**
     * Checks if an Object other is the same object as PersonInfo
     * @param other
     * @return
     */
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        if (!(other instanceof PersonInfo)) {
            return false;
        }

        // state check
        PersonInfo info = (PersonInfo) other;
        return profilePic.equals(info.profilePic)
                && name.equals(info.name)
                && phone.equals(info.phone)
                && address.equals(info.address)
                && email.equals(info.email)
                && date.equals(info.date)
                && attendance.equals(info.attendance);
    }
}
