package seedu.address.model.task;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.Set;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.util.SampleDataUtil;

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
    private ObjectProperty<Integer> taskPriority;
    private ObjectProperty<UniqueTagList> tags;
    private ObjectProperty<Boolean> complete;

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
        this.complete = new SimpleObjectProperty<>(false);
        this.taskPriority = new SimpleObjectProperty<>(1);
    }

    /**
     * Helper constructor, for: copy
     * @param tags, the tag set that already present
     */
    public Task (Set<Tag> tags) {
        this();
        this.tags = new SimpleObjectProperty<>(new UniqueTagList(tags));
    }

    /**
     * Constructor with complete
     * @param state, the marked completed state of the task
     */
    public Task(Boolean state) {
        this();
        this.complete = new SimpleObjectProperty<>(state);
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
        this.complete = new SimpleObjectProperty<>(false);
        this.taskPriority = new SimpleObjectProperty<>(1);
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
        this.complete = new SimpleObjectProperty<>(false);
        this.taskPriority = new SimpleObjectProperty<>(1);
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
        this.complete = new SimpleObjectProperty<>(false);
        this.taskPriority = new SimpleObjectProperty<>(1);
    }

    /**
     * Constructor for taking also a priority set by the user
     * @param name
     * @param description
     * @param startDateTime
     * @param endDateTime
     * @param priority
     */
    public Task (String name, String description, String startDateTime, String endDateTime,
                 int priority) {
        this();
        this.taskName = new SimpleObjectProperty<>(name);
        this.taskDescription = new SimpleObjectProperty<>(description);
        this.startDateTime = new SimpleObjectProperty<>(startDateTime);
        this.endDateTime = new SimpleObjectProperty<>(endDateTime);
        setPriority(priority);
    }

    /**
     * Constructor with also a time only to be passed in (third type, start and end) and the complete state
     * @param name, the name of this task
     * @param description, the description of this task
     * @param startDateTime, the start date and time of this task
     * @param endDateTime, the end date and time of this task
     * @param tags, the tag set
     */
    public Task (String name, String description, String startDateTime, String endDateTime,
                 Set<Tag> tags, Boolean state) {
        this(tags);
        this.taskName = new SimpleObjectProperty<>(name);
        this.taskDescription = new SimpleObjectProperty<>(description);
        this.startDateTime = new SimpleObjectProperty<>(startDateTime);
        this.endDateTime = new SimpleObjectProperty<>(endDateTime);
        this.complete = new SimpleObjectProperty<>(state);
    }

    /**
     * Constructor for copy constructor
     * with also a time only to be passed in (third type, start and end) and the complete state
     * @param name, the name of this task
     * @param description, the description of this task
     * @param startDateTime, the start date and time of this task
     * @param endDateTime, the end date and time of this task
     * @param tags, the tag set
     * @param priority, the priority value
     */
    public Task (String name, String description, String startDateTime, String endDateTime,
                 Set<Tag> tags, Boolean state, Integer priority) {
        this(tags);
        this.taskName = new SimpleObjectProperty<>(name);
        this.taskDescription = new SimpleObjectProperty<>(description);
        this.startDateTime = new SimpleObjectProperty<>(startDateTime);
        this.endDateTime = new SimpleObjectProperty<>(endDateTime);
        this.complete = new SimpleObjectProperty<>(state);
        setPriority(priority);
    }

    /**
     * Copy constructor for task class
     * @param task
     */
    public Task (ReadOnlyTask task) {
        this(task.getName(), task.getDescription(), task.getStartDateTime(),
                task.getEndDateTime(), task.getTags(), task.getComplete(), task.getPriority());
    }

    /**
     * Copy constructor for task class with complete
     * @param task
     */
    public Task (ReadOnlyTask task, boolean complete) {
        this(task.getName(), task.getDescription(), task.getStartDateTime(),
                task.getEndDateTime(), task.getTags(), complete, task.getPriority());
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

    public Boolean getComplete () {
        return complete.get();
    }

    public Integer getPriority () {
        return taskPriority.get();
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

    public ObjectProperty<Integer> priorityProperty() {
        return taskPriority;
    }

    public ObjectProperty<UniqueTagList> tagProperty() {
        return tags;
    }

    public ObjectProperty<Boolean> completeProperty() {
        return complete;
    }

    public void setName(String name) {
        this.taskName.set(requireNonNull(name));
    }

    public void setDescription(String description) {
        this.taskName.set(requireNonNull(description));
    }

    public void setStartDateTime(String startDateTime) {
        this.taskName.set(requireNonNull(startDateTime));
    }

    public void setEndDateTime(String endDateTime) {
        this.taskName.set(requireNonNull(endDateTime));
    }

    /**
     * Setter for priority property (functional)
     * In all priority settings we will use this function, to prevent illegal values
     * @param priority
     */
    public void setPriority(Integer priority) {
        if (priority > 5 || priority < 0) {
            this.taskPriority.set(requireNonNull(1));
        } else {
            this.taskPriority.set(requireNonNull(priority));
        }
    }

    public void setTags(Set<Tag> replacement) {
        tags.set(new UniqueTagList(replacement));
    }

    public void setComplete() {
        this.complete.set(requireNonNull(true));
    }

    /**
     * Set a new tag set along with the new task construction
     * This method should not be usd if the
     * @param tags , to be set
     * @return the newly constructed task
     */
    public Task withTags(String ... tags) {
        try {
            this.setTags(SampleDataUtil.getTagSet(tags));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("tags are expected to be unique.");
        }
        return this;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
