package seedu.address.model.task;

import java.util.Collections;
import java.util.Set;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * This is a task class with only a name
 */
public class Task implements ReadOnlyTask {
    private static Integer indexTask;

    private ObjectProperty<String> taskName;
    private ObjectProperty<String> taskDescription;
    private ObjectProperty<String> startDateTime;
    private ObjectProperty<String> endDateTime;
    private ObjectProperty<Integer> taskIndex;
    private ObjectProperty<UniqueTagList> tags;


    /**
     * Default constructor may not be used
     */
    public Task () {
        if (indexTask == null) {
            indexTask = 1;
        }
        this.taskIndex = new SimpleObjectProperty<>(indexTask);
        this.tags = new SimpleObjectProperty<>(new UniqueTagList());
        this.startDateTime = new SimpleObjectProperty<>("");
        this.endDateTime = new SimpleObjectProperty<>("");
    }

    /**
     * Constructor with a name and description only to be passed in (first type)
     * @param name, the name of this task
     * @param description, the description of this task
     */
    public Task (String name, String description) {
        this();
        this.taskName = new SimpleObjectProperty<>(name);
        this.taskDescription = new SimpleObjectProperty<>(description);
    }

    /**
     * Constructor with also a time only to be passed in (second type, modelled as deadline/time point)
     * @param name, the name of this task
     * @param description, the description of this task
     * @param startDateTime, the start date and time of this task
     */
    public Task (String name, String description, String startDateTime) {
        this();
        this.taskName = new SimpleObjectProperty<>(name);
        this.taskDescription = new SimpleObjectProperty<>(description);
        this.startDateTime = new SimpleObjectProperty<>(startDateTime);
    }

    /**
     * Constructor with also a time only to be passed in (third type, start and end)
     * @param name, the name of this task
     * @param description, the description of this task
     * @param startDateTime, the start date and time of this task
     * @param endDateTime, the end date and time of this task
     */
    public Task (String name, String description, String startDateTime, String endDateTime) {
        this();
        this.taskName = new SimpleObjectProperty<>(name);
        this.taskDescription = new SimpleObjectProperty<>(description);
        this.startDateTime = new SimpleObjectProperty<>(startDateTime);
        this.endDateTime = new SimpleObjectProperty<>(endDateTime);
    }

    /**
     * get index from this task
     * @return index
     */
    public int getIndex () {
        return taskIndex.get();
    }

    /**
     * get name from this task
     * @return name
     */
    public String getName () {
        return taskName.get();
    }

    /**
     * get description from this task
     * @return description
     */
    public String getDescription () {
        return taskDescription.get();
    }

    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags.get().toSet());
    }

    public String getStartDateTime () {
        return startDateTime.get();
    }

    public String getEndDateTime () {
        return endDateTime.get();
    }

    public ObjectProperty<String> nameProperty() {
        return taskName;
    }
    public ObjectProperty<String> descriptionProperty() {
        return taskDescription;
    }
    public ObjectProperty<String> startTimeProperty() {
        return startDateTime;
    }
    public ObjectProperty<String> endTimeProperty() {
        return endDateTime;
    }
    public ObjectProperty<UniqueTagList> tagProperty() {
        return tags;
    }

}
