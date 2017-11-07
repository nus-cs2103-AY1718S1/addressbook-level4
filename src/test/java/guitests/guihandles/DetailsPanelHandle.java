package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

//@@author archthegit

/**
 * A handle to the {@code DetailsPanel} of the application.
 */
public class DetailsPanelHandle extends NodeHandle<Node> {
    public static final String DETAILS_PANEL_ID = "#detailsPanelPlaceholder";

    private static final String NAME_FIELD_ID = "#name";
    private static final String ADDRESS_FIELD_ID = "#address";
    private static final String PHONE_FIELD_ID = "#phone";
    private static final String EMAIL_FIELD_ID = "#email";
    private static final String HOME_PHONE_FIELD_ID = "#homePhone";
    private static final String SCHOOL_EMAIL_FIELD_ID = "#schoolEmail";
    private static final String BIRTHDAY_FIELD_ID = "#birthday";
    private static final String WEBSITE_FIELD_ID = "#website";
    private static final String TAGS_FIELD_ID = "#tags";
    private static final String ADDRESS_FIELD_FIELD_ID = "#addressField";
    private static final String PHONE_FIELD_FIELD_ID = "#phoneField";
    private static final String HOME_PHONE_FIELD_FIELD_ID = "#homePhoneField";
    private static final String EMAIL_FIELD_FIELD_ID = "#emailField";
    private static final String SCHOOL_EMAIL_FIELD_FIELD_ID = "#schoolEmailField";
    private static final String BIRTHDAY_FIELD_FIELD_ID = "#birthdayField";
    private static final String WEBSITE_FIELD_FIELD_ID = "#websiteField";

    private final Label nameLabel;
    private final Label addressLabel;
    private final Label phoneLabel;
    private final Label homePhoneLabel;
    private final Label emailLabel;
    private final Label schEmailLabel;
    private final Label birthdayLabel;
    private final Label websiteLabel;
    private final List<Label> tagLabels;

    private final Text addressText;
    private final Text phoneText;
    private final Text homePhoneText;
    private final Text emailText;
    private final Text schEmailText;
    private final Text birthdayText;
    private final Text websiteText;

    private String latestName;
    private String latestAddress;
    private String latestPhone;
    private String latestEmail;
    private String latestSchoolEmail;
    private String latestBirthday;
    private String latestWebsite;
    private String latestHomePhone;
    private List<String> latestTags;

    public DetailsPanelHandle(Node detailsPanelNode) {
        super(detailsPanelNode);

        this.nameLabel = getChildNode(NAME_FIELD_ID);
        this.addressLabel = getChildNode(ADDRESS_FIELD_ID);
        this.phoneLabel = getChildNode(PHONE_FIELD_ID);
        this.emailLabel = getChildNode(EMAIL_FIELD_ID);
        this.schEmailLabel = getChildNode(SCHOOL_EMAIL_FIELD_ID);
        this.birthdayLabel = getChildNode(BIRTHDAY_FIELD_ID);
        this.websiteLabel = getChildNode(WEBSITE_FIELD_ID);
        this.homePhoneLabel = getChildNode(HOME_PHONE_FIELD_ID);

        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        this.tagLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());

        this.addressText = getChildNode(ADDRESS_FIELD_FIELD_ID);
        this.phoneText = getChildNode(PHONE_FIELD_FIELD_ID);
        this.emailText = getChildNode(EMAIL_FIELD_FIELD_ID);
        this.schEmailText = getChildNode(SCHOOL_EMAIL_FIELD_FIELD_ID);
        this.birthdayText = getChildNode(BIRTHDAY_FIELD_FIELD_ID);
        this.websiteText = getChildNode(WEBSITE_FIELD_FIELD_ID);
        this.homePhoneText = getChildNode(HOME_PHONE_FIELD_FIELD_ID);
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

    public String getSchEmail() {
        return schEmailLabel.getText();
    }

    public String getBirthday() {
        return birthdayLabel.getText();
    }

    public String getWebsite() {
        return  websiteLabel.getText();
    }

    public String getHomePhone() {
        return homePhoneLabel.getText();
    }

    public String getAddressField() {
        return addressText.getText();
    }

    public String getPhoneField() {
        return phoneText.getText();
    }

    public String getHomePhoneField() {
        return homePhoneText.getText();
    }

    public String getEmailField() {
        return emailText.getText();
    }

    public String getSchEmailField() {
        return schEmailText.getText();
    }

    public String getBirthdayField() {
        return birthdayText.getText();
    }

    public String getWebsiteField () {
        return websiteText.getText();
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    /**
     * Remember the details of the person that was last selected
     */
    public void rememberSelectedPersonDetails() {
        latestAddress = getAddress();
        latestSchoolEmail = getSchEmail();
        latestEmail = getEmail();
        latestName = getName();
        latestPhone = getPhone();
        latestBirthday = getBirthday();
        latestTags = getTags();
        latestWebsite = getWebsite();
        latestHomePhone = getHomePhone();
    }

    /**
     * Returns true if the selected {@code Person} is different from the value remembered by the most recent
     * {@code rememberSelectedPersonDetails()} call.
     */
    public boolean isSelectedPersonChanged() {
        return !getName().equals(latestName)
                || !getAddress().equals(latestAddress)
                || !getPhone().equals(latestPhone)
                || !getSchEmail().equals(latestSchoolEmail)
                || !getEmail().equals(latestEmail)
                || !getBirthday().equals(latestBirthday)
                || !getWebsite().equals(latestWebsite)
                || !getHomePhone().equals(latestHomePhone)
                || !getTags().equals(latestTags);
    }

}

