package guitests.guihandles;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

//@@author pohjie
/**
 * The intention behind writing this file was to change the test cases such that
 * the integration tests will use the PersonInfo panel rather than the BrowserPanel.
 * However, due to heavy coupling and a lack of time, I've decided to use mainly unit tests
 * to test my features. I understand this is not ideal but this is the best compromise
 * I can make now.
 */

/**
 * A handler for the {@code PersonInfoPanel} of the UI.
 */
public class PersonInfoHandle extends NodeHandle<Node> {

    public static final String PERSON_INFO_ID = "#browserPlaceholder";
    public static final String PROFILE_PIC_ID = "profilePic";
    public static final String NAME_ID = "name";
    public static final String PHONE_ID = "phone";
    public static final String ADDRESS_ID = "address";
    public static final String EMAIL_ID = "email";
    public static final String JOIN_DATE_ID = "date";
    public static final String ATTENDANCE_ID = "attendance";

    private Node personInfoNode;
    private Node profilePicImg;
    private Node nameLabel;
    private Node phoneLabel;
    private Node addressLabel;
    private Node emailLabel;
    private Node joinDateLabel;
    private Node attendancePieChart;

    public PersonInfoHandle(Node personInfoNode) {
        super(personInfoNode);
        StackPane stackPane = getChildNode(PERSON_INFO_ID);
        ObservableList<Node> listNode = stackPane.getChildren();
        for (int i = 0; i < listNode.size(); i++) {
            if (!listNode.get(i).getId().equals(null) && listNode.get(i).getId().equals(PROFILE_PIC_ID)) {
                profilePicImg = listNode.get(i);
            } else if (!listNode.get(i).equals(null) && listNode.get(i).getId().equals(NAME_ID)) {
                nameLabel = listNode.get(i);
            } else if (!listNode.get(i).equals(null) && listNode.get(i).getId().equals(PHONE_ID)) {
                phoneLabel = listNode.get(i);
            } else if (!listNode.get(i).equals(null) && listNode.get(i).getId().equals(ADDRESS_ID)) {
                addressLabel = listNode.get(i);
            } else if (!listNode.get(i).equals(null) && listNode.get(i).getId().equals(EMAIL_ID)) {
                emailLabel = listNode.get(i);
            } else if (!listNode.get(i).equals(null) && listNode.get(i).getId().equals(JOIN_DATE_ID)) {
                joinDateLabel = listNode.get(i);
            } else if (!listNode.get(i).equals(null) && listNode.get(i).getId().equals(ATTENDANCE_ID)) {
                attendancePieChart = listNode.get(i);
            }
        }
    }

    public String getProfilePicText() {
        TextField f = (TextField) profilePicImg;
        return f.getText();
    }

    public String getNameText() {
        TextField f = (TextField) nameLabel;
        return f.getText();
    }

    public String getPhoneText() {
        TextField f = (TextField) phoneLabel;
        return f.getText();
    }

    public String getAddressText() {
        TextField f = (TextField) addressLabel;
        return f.getText();
    }

    public String getEmailText() {
        TextField f = (TextField) emailLabel;
        return f.getText();
    }

    public String getJoinDateText() {
        TextField f = (TextField) joinDateLabel;
        return f.getText();
    }

    public String getAttendanceText() {
        TextField f = (TextField) attendancePieChart;
        return f.getText();
    }
}
