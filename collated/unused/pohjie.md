# pohjie
###### \AddAttendanceCommand.java
``` java
/**
 * Reason why this was unused: Tried implementing this feature too late into the iterative cycle,
 * couldn't get it to work. Decided to take it out to ensure quality of code for working parts
 * and leave enough time for writing sufficient and rigorous test cases.
  */
/**
 * Increment attendance count of an existing person in the address book.
 */
public class AddAttendanceCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addAttendance";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Increment the attendance person identified"
            + "by the index number used in the last person listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) ";

    public static final String MESSAGE_ADD_ATTENDANCE_SUCCESS = "Edited Person's Attendance: %1$s";
    public static final String MESSAGE_ATTENDANCE_NOT_ADDED =
            "Either invalid index or the attended sessions is already 8";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final EditAttendancePersonDescriptor editAttendancePersonDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public AddAttendanceCommand(Index index, EditAttendancePersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editAttendancePersonDescriptor = new EditAttendancePersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createEditedPerson(personToEdit, editAttendancePersonDescriptor);

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (PersonMaxAttendanceException pmae) {
            throw new CommandException(MESSAGE_ATTENDANCE_NOT_ADDED);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_ADD_ATTENDANCE_SUCCESS, editedPerson));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(ReadOnlyPerson personToEdit,
                                             EditAttendancePersonDescriptor editAttendancePersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editAttendancePersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editAttendancePersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editAttendancePersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = editAttendancePersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        Set<Tag> updatedTags = editAttendancePersonDescriptor.getTags().orElse(personToEdit.getTags());
```
###### \AddAttendanceCommand.java
``` java
        Attendance attendance = editAttendancePersonDescriptor.getAttendance();
        ProfilePicture updatedProfilePicture = editAttendancePersonDescriptor.getProfilePicture();
        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, date, updatedTags);
```
###### \AddAttendanceCommand.java
``` java
        private Attendance attendance;
        private ProfilePicture profilePicture;

```
###### \AddAttendanceCommand.java
``` java
            this.attendance = toCopy.attendance;
            try {
                this.attendance.addAttendance();
            } catch (personMaxAttendanceException e) {
                System.out.println("Max attendance already!");
            }
            this.profilePicture = toCopy.profilePicture;
```
###### \AddAttendanceCommand.java
``` java
        public void setAttendance(Attendance attendance) {
            this.attendance = attendance;
        }

        public Attendance getAttendance() {
            return attendance;
        }

        public void setProfilePicture(ProfilePicture profilePicture) {
            this.profilePicture = profilePicture;
        }

        public ProfilePicture getProfilePicture() {
            return profilePicture;
        }
```
###### \PersonInfoHandle.java
``` java
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
```
