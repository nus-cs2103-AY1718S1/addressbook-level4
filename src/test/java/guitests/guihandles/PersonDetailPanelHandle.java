package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * A handle for the person detail panel of the App
 */
public class PersonDetailPanelHandle extends NodeHandle<Node> {
    public static final String PERSON_DETAIL_ID = "#personDetailPanel";
    private static final String NAME_FIELD_ID = "#name";
    private static final String ADDRESS_FIELD_ID = "#address";
    private static final String PHONE_FIELD_ID = "#phone";
    private static final String EMAIL_FIELD_ID = "#email";
    private static final String REMARK_FIELD_ID = "#remark";
    private static final String TAGS_FIELD_ID = "#tags";

    private Label nameLabel;
    private Label addressLabel;
    private Label phoneLabel;
    private Label emailLabel;
    private Label remarkLabel;
    private List<Label> tagLabels;

    public PersonDetailPanelHandle(Node personDetailNode) {
        super(personDetailNode);

        this.nameLabel = getChildNode(NAME_FIELD_ID);
        this.addressLabel = getChildNode(ADDRESS_FIELD_ID);
        this.phoneLabel = getChildNode(PHONE_FIELD_ID);
        this.emailLabel = getChildNode(EMAIL_FIELD_ID);
        this.remarkLabel = getChildNode(REMARK_FIELD_ID);
        updateTags();
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

    public String getRemark() {
        return remarkLabel.getText();
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    /**
     * Update tags in person detail panel
     */
    public void updateTags() {
        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        this.tagLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    /**
     * Returns a copy of an empty tag list.
     */
    public List<String> getEmptyTagList() {
        List<String> tagLabelsCopy = getTags();
        tagLabelsCopy.clear();
        return tagLabelsCopy;
    }
}
