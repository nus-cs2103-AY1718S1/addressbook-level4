package seedu.address.model.task;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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

    private ObjectProperty<String> taskName;
    private ObjectProperty<String> taskDescription;
    private ObjectProperty<String> startDateTime;
    private ObjectProperty<String> endDateTime;
    private ObjectProperty<UniqueTagList> tags;
    private ObjectProperty<Boolean> complete;
    private ObjectProperty<Integer> id;
    private ObjectProperty<ArrayList<Integer>> peopleIndices;

    /**
     * Default constructor may not be used
     */
    public Task () {
        this.tags = new SimpleObjectProperty<>(new UniqueTagList());
        this.startDateTime = new SimpleObjectProperty<>("");
        this.endDateTime = new SimpleObjectProperty<>("");
        this.complete = new SimpleObjectProperty<>(false);
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
        this.complete = new SimpleObjectProperty<>(true);
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
    }

    /**
     * Constructor with also a time only to be passed in (third type, start and end)
     * @param name, the name of this task
     * @param description, the description of this task
     * @param startDateTime, the start date and time of this task
     * @param endDateTime, the end date and time of this task
     * @param tags, the tag set
     */
    public Task (String name, String description, String startDateTime, String endDateTime,
                 Set<Tag> tags, Boolean complete) {
        this(tags);
        this.taskName = new SimpleObjectProperty<>(name);
        this.taskDescription = new SimpleObjectProperty<>(description);
        this.startDateTime = new SimpleObjectProperty<>(startDateTime);
        this.endDateTime = new SimpleObjectProperty<>(endDateTime);
        this.complete = new SimpleObjectProperty<>(false);
        this.id = new SimpleObjectProperty<>(this.hashCode());
        this.peopleIndices = new SimpleObjectProperty<>();
        peopleIndices.set(new ArrayList<>());
    }

    public Task (String name, String description, String startDateTime, String endDateTime,
                 Set<Tag> tags, Boolean complete, List<Integer> peopleIndices) {
        this(tags);
        this.taskName = new SimpleObjectProperty<>(name);
        this.taskDescription = new SimpleObjectProperty<>(description);
        this.startDateTime = new SimpleObjectProperty<>(startDateTime);
        this.endDateTime = new SimpleObjectProperty<>(endDateTime);
        this.complete = new SimpleObjectProperty<>(false);
        this.id = new SimpleObjectProperty<>(this.hashCode());
        this.peopleIndices = new SimpleObjectProperty<>(new ArrayList<>(peopleIndices));
    }

    /**
     * Copy constructor for task class
     * @param task
     */
    public Task (ReadOnlyTask task) {
        this(task.getName(), task.getDescription(), task.getStartDateTime(),
                task.getEndDateTime(), task.getTags(), task.getComplete());
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

    @Override
    public ObjectProperty<Integer> idProperty() {
        return id;
    }

    @Override
    public Integer getId() {
        return id.get();
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

    public void setTags(Set<Tag> replacement) {
        tags.set(new UniqueTagList(replacement));
    }

    public void setComplete() {
        this.complete.set(requireNonNull(true));
    }

    public ObjectProperty<ArrayList<Integer>> peopleIndicesProperty() {
        return peopleIndices;
    }

    public ArrayList<Integer> getPeopleIndices() { return peopleIndices.get();}

    public void setRemark(ArrayList<Integer> peopleIndices) {this.peopleIndices.set(requireNonNull(peopleIndices));
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
