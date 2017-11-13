//@@author zenghou
package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * Provides a handle to a person profile. Similar to {@see PersonCardHandle}
 */
public class PersonProfileHandle extends NodeHandle<Node> {
    public static final String PROFILE_FIELD_ID = "#profile";
    public static final String NAME_FIELD_ID = "#name";
    public static final String REMARK_FIELD_ID = "#remark";
    public static final String TAG_FIELD_ID = "#tags";

    private final Label profileLabel;
    private final Label nameLabel;
    private final Label remarkLabel;
    private final List<Label> tagLabels;

    public PersonProfileHandle(Node profileNode) {
        super(profileNode);

        this.profileLabel = getChildNode(PROFILE_FIELD_ID);
        this.nameLabel = getChildNode(NAME_FIELD_ID);
        this.remarkLabel = getChildNode(REMARK_FIELD_ID);

        Region tagsContainer = getChildNode(TAG_FIELD_ID);
        this.tagLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public String getProfile() {
        return profileLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
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
}
