//@@author ShaocongDong
package seedu.address.model.task;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
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

    private ObjectProperty<Name> taskName;
    private ObjectProperty<Description> taskDescription;
    private ObjectProperty<DateTime> startDateTime;
    private ObjectProperty<DateTime> endDateTime;
    private ObjectProperty<Integer> taskPriority;
    private ObjectProperty<UniqueTagList> tags;
    private ObjectProperty<Boolean> complete;
    private ObjectProperty<Integer> id;
    private ObjectProperty<ArrayList<Integer>> peopleIds;

    /**
     * Default constructor may not be used
     */
    public Task () {
        this.tags = new SimpleObjectProperty<>(new UniqueTagList());
        this.complete = new SimpleObjectProperty<>(false);
        this.taskPriority = new SimpleObjectProperty<>(3);
        this.id = new SimpleObjectProperty<>(this.hashCode());
        this.peopleIds = new SimpleObjectProperty<>(new ArrayList<Integer>());
    }

    /**
     * Constructor with also a time only to be passed in (third type, start and end)
     * @param name, the name of this task
     * @param description, the description of this task
     * @param startDateTime, the start date and time of this task
     * @param endDateTime, the end date and time of this task
     */
    public Task (Name name, Description description, DateTime startDateTime, DateTime endDateTime) {
        this();
        this.taskName = new SimpleObjectProperty<>(name);
        this.taskDescription = new SimpleObjectProperty<>(description);
        this.startDateTime = new SimpleObjectProperty<>(startDateTime);
        this.endDateTime = new SimpleObjectProperty<>(endDateTime);
        this.complete = new SimpleObjectProperty<>(false);
        //this.taskPriority = new SimpleObjectProperty<>(1);
    }

    /**
     * Constructor for taking also a priority set by the user
     * @param name
     * @param description
     * @param startDateTime
     * @param endDateTime
     * @param priority
     */
    public Task (Name name, Description description, DateTime startDateTime, DateTime endDateTime,
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
    public Task (Name name, Description description, DateTime startDateTime, DateTime endDateTime,
                 Set<Tag> tags, Boolean state) {
        this();
        this.tags = new SimpleObjectProperty<>(new UniqueTagList(tags));
        this.taskName = new SimpleObjectProperty<>(name);
        this.taskDescription = new SimpleObjectProperty<>(description);
        this.startDateTime = new SimpleObjectProperty<>(startDateTime);
        this.endDateTime = new SimpleObjectProperty<>(endDateTime);

        this.complete = new SimpleObjectProperty<>(false);
        this.id = new SimpleObjectProperty<>();
    }

    /**
     * Constructor
     * with also a time only to be passed in (third type, start and end) and the complete state
     * @param name, the name of this task
     * @param description, the description of this task
     * @param startDateTime, the start date and time of this task
     * @param endDateTime, the end date and time of this task
     * @param tags, the tag set
     * @param priority, the priority value
     */
    public Task (Name name, Description description, DateTime startDateTime, DateTime endDateTime,
                 Set<Tag> tags, Boolean state, Integer priority) {
        this();
        this.tags = new SimpleObjectProperty<>(new UniqueTagList(tags));
        this.taskName = new SimpleObjectProperty<>(name);
        this.taskDescription = new SimpleObjectProperty<>(description);
        this.startDateTime = new SimpleObjectProperty<>(startDateTime);
        this.endDateTime = new SimpleObjectProperty<>(endDateTime);
        this.complete = new SimpleObjectProperty<>(state);
        setPriority(priority);
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
    public Task (Name name, Description description, DateTime startDateTime, DateTime endDateTime,
                 Set<Tag> tags, Boolean state, Integer priority, Integer id, ArrayList<Integer> peopleIds) {
        this.tags = new SimpleObjectProperty<>(new UniqueTagList(tags));
        this.taskName = new SimpleObjectProperty<>(name);
        this.taskDescription = new SimpleObjectProperty<>(description);
        this.startDateTime = new SimpleObjectProperty<>(startDateTime);
        this.endDateTime = new SimpleObjectProperty<>(endDateTime);
        this.complete = new SimpleObjectProperty<>(false);
        this.id = new SimpleObjectProperty<>(id);
        this.peopleIds = new SimpleObjectProperty<>(peopleIds);
        this.complete = new SimpleObjectProperty<>(state);
        this.taskPriority = new SimpleObjectProperty<>();
        setPriority(priority);
    }

    /**
     * Copy constructor for task class
     * @param task
     */
    public Task (ReadOnlyTask task) {
        this(task.getName(), task.getDescription(), task.getStartDateTime(),
                task.getEndDateTime(), task.getTags(), task.getComplete(), task.getPriority(), task.getId(),
                task.getPeopleIds());
    }

    /**
     * get name from this task
     * @return name
     */
    public Name getName () {
        return taskName.get();
    }

    /**
     * get description from this task
     * @return description
     */
    public Description getDescription () {
        return taskDescription.get();
    }

    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags.get().toSet());
    }

    public DateTime getStartDateTime () {
        return startDateTime.get();
    }

    public DateTime getEndDateTime () {
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
    public Integer getPriority () {
        return taskPriority.get();
    }

    public ObjectProperty<Name> nameProperty() {
        return taskName;
    }

    public ObjectProperty<Description> descriptionProperty() {
        return taskDescription;
    }

    public ObjectProperty<DateTime> startTimeProperty() {
        return startDateTime;
    }

    public ObjectProperty<DateTime> endTimeProperty() {
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

    public void setName(Name name) {
        this.taskName.set(requireNonNull(name));
    }

    public void setDescription(Description description) {
        this.taskDescription.set(requireNonNull(description));
    }

    public void setStartDateTime(DateTime startDateTime) {
        this.startDateTime.set(requireNonNull(startDateTime));
    }

    public void setEndDateTime(DateTime endDateTime) {
        this.endDateTime.set(requireNonNull(endDateTime));
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

    public ObjectProperty<ArrayList<Integer>> peopleIdsProperty() {
        return peopleIds;
    }

    public ArrayList<Integer> getPeopleIds() {
        return peopleIds.get();
    }

    public void setPeopleIds(ArrayList<Integer> peopleIds) {
        this.peopleIds.set(requireNonNull(peopleIds));
    }

    public void setRemark(ArrayList<Integer> peopleIndices) {
        this.peopleIds.set(requireNonNull(peopleIndices));
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
