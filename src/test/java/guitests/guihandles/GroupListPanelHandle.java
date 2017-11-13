//@@author conantteo
package guitests.guihandles;

import java.util.List;
import java.util.Optional;

import javafx.scene.control.ListView;
import seedu.address.model.tag.Tag;
import seedu.address.ui.GroupLabel;

/**
 * Provides a handle for {@code GroupListPanel} containing a list of {@code GroupLabel}
 */
public class GroupListPanelHandle extends NodeHandle<ListView<GroupLabel>> {
    public static final String GROUP_LIST_VIEW_ID = "#groupListView";

    public GroupListPanelHandle(ListView<GroupLabel> groupListPanelNode) {
        super(groupListPanelNode);
    }

    /**
     * Navigates the list view to display and select the particular tag {@param toTag}
     */
    public void navigateToTag(Tag toTag) {
        List<GroupLabel> groupLabels = getRootNode().getItems();
        Optional<GroupLabel> matchingTag = groupLabels.stream().filter(groupLabel -> groupLabel
                .getTag().equals(toTag)).findFirst();

        if (!matchingTag.isPresent()) {
            throw new IllegalArgumentException("Tag does not exists");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(matchingTag.get());
            getRootNode().getSelectionModel().select(matchingTag.get());
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Returns the GroupLabelHandle of a tag specified by {@code index} in this list.
     */
    public GroupLabelHandle getGroupLabelHandle(int index) {
        return getGroupLabelHandle(getRootNode().getItems().get(index).getTag());
    }

    public GroupLabelHandle getGroupLabelHandle(Tag tag) {
        Optional<GroupLabelHandle> handle = getRootNode().getItems().stream()
                .filter(label -> label.getTag().equals(tag))
                .map(label -> new GroupLabelHandle(label.getRoot()))
                .findFirst();
        return handle.orElseThrow(() -> new IllegalArgumentException("Tag does not exist."));
    }
}
