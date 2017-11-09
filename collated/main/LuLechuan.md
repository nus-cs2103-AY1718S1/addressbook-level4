# LuLechuan
###### \java\seedu\address\logic\commands\CustomCommand.java
``` java
/**
 * Adds or updates a custom field of a person identified using it's last displayed index from the address book.
 */
public class CustomCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "custom";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Updates one person's custom field identified by the index number used in the last person listing.\n"
            + "Parameters: "
            + "INDEX (must be a positive integer)\n"
            + "CUSTOM_FIELD_NAME \n"
            + "CUSTOM_FIELD_VALUE"
            + "Example: " + COMMAND_WORD + " 1" + " nickname" + " Ah Chuang";

    public static final String MESSAGE_UPDATE_PERSON_CUSTOM_FIELD_SUCCESS = "Updated Person: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index targetIndex;

    private final CustomField customField;

    public CustomCommand(Index targetIndex, CustomField customField) {
        this.targetIndex = targetIndex;
        this.customField = customField;
    }

    /**
     * Adds or Updates a Person's customField
     */
    private Person updatePersonCustomField(ReadOnlyPerson personToUpdateCustomField, CustomField customField) {
        Name name = personToUpdateCustomField.getName();
        Phone phone = personToUpdateCustomField.getPhone();
        Email email = personToUpdateCustomField.getEmail();
        Address address = personToUpdateCustomField.getAddress();
        Photo photo = personToUpdateCustomField.getPhoto();
        UniquePhoneList uniquePhoneList = personToUpdateCustomField.getPhoneList();
        Set<Tag> tags = personToUpdateCustomField.getTags();
        UniqueCustomFieldList customFields = personToUpdateCustomField.getCustomFieldList();

        customFields.add(customField);

        Person personUpdated = new Person(name, phone, email, address,
                photo, uniquePhoneList, tags, customFields.toSet());

        return personUpdated;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToUpdateCustomField = lastShownList.get(targetIndex.getZeroBased());
        Person personUpdated = updatePersonCustomField(personToUpdateCustomField, customField);

        try {
            model.updatePerson(personToUpdateCustomField, personUpdated);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_UPDATE_PERSON_CUSTOM_FIELD_SUCCESS, personUpdated));
    }
}
```
###### \java\seedu\address\logic\commands\DeleteByNameCommand.java
``` java
/**
 * Deletes a person identified using the person's name from the address book.
 */
public class DeleteByNameCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "deleteByName";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the person's name\n"
            + "Parameters: Full Name of a person (String)\n"
            + "Example: " + COMMAND_WORD + " John Doe";

    public static final String MESSAGE_DELETE_PERSON_BY_NAME_SUCCESS = "Deleted Person: %1$s";

    private final String targetName;

    public DeleteByNameCommand(String targetName) {
        this.targetName = targetName;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        ReadOnlyPerson personToDelete = null;

        for (int i = 0; i < lastShownList.size(); i++) {
            if (lastShownList.get(i).getName().toString().equals(targetName)) {
                personToDelete = lastShownList.get(i);
                break;
            }
        }

        if (personToDelete == null) {
            throw new CommandException(Messages.MESSAGE_UNFOUND_PERSON_NAME);
        }

        try {
            model.deletePerson(personToDelete);
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_BY_NAME_SUCCESS, personToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteByNameCommand // instanceof handles nulls
                && this.targetName.equals(((DeleteByNameCommand) other).targetName)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\UploadPhotoCommand.java
``` java
/**
 * Adds or updates the photo of a person identified using it's last displayed index from the address book.
 */
public class UploadPhotoCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "upload";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Updates one person's photo identified by the index number used in the last person listing.\n"
            + "Parameters: "
            + "INDEX (must be a positive integer)\n"
            + "photoPath"
            + "Example: " + COMMAND_WORD + " 1" + "/img.png";

    public static final String MESSAGE_UPDATE_PERSON_PHOTO_SUCCESS = "Updated Person: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index targetIndex;

    private final Photo photo;

    public UploadPhotoCommand(Index targetIndex, Photo photo) {
        this.targetIndex = targetIndex;
        this.photo = photo;
    }

    /**
     * Updates a Person's photo
     */
    private Person updatePersonPhoto(ReadOnlyPerson personToUpdatePhoto, Photo photo) {
        Name name = personToUpdatePhoto.getName();
        Phone phone = personToUpdatePhoto.getPhone();
        Email email = personToUpdatePhoto.getEmail();
        Address address = personToUpdatePhoto.getAddress();
        UniquePhoneList uniquePhoneList = personToUpdatePhoto.getPhoneList();
        Set<Tag> tags = personToUpdatePhoto.getTags();
        Set<CustomField> customFields = personToUpdatePhoto.getCustomFields();

        Person personUpdated = new Person(name, phone, email, address,
                photo, uniquePhoneList, tags, customFields);
        return personUpdated;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToUpdatePhoto = lastShownList.get(targetIndex.getZeroBased());

        Person personUpdated = updatePersonPhoto(personToUpdatePhoto, photo);

        try {
            model.updatePerson(personToUpdatePhoto, personUpdated);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_UPDATE_PERSON_PHOTO_SUCCESS, personUpdated));
    }
}
```
###### \java\seedu\address\logic\parser\CustomCommandParser.java
``` java
/**
 * Parses input arguments and creates a new CustomCommand object
 */
public class CustomCommandParser implements Parser<CustomCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CustomCommand
     * and returns a CustomCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CustomCommand parse(String args) throws ParseException {
        try {
            StringTokenizer st = new StringTokenizer(args);
            Index index = ParserUtil.parseIndex(st.nextToken());
            String customFieldName = st.nextToken();
            String customFieldValue;

            if (st.hasMoreTokens()) {
                customFieldValue = st.nextToken();
            } else {
                customFieldValue = "";
            }

            CustomField customField = new CustomField(customFieldName, customFieldValue);
            return new CustomCommand(index, customField);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CustomCommand.MESSAGE_USAGE));
        }
    }

}
```
###### \java\seedu\address\logic\parser\DeleteByNameCommandParser.java
``` java
/**
 * Parses input arguments and creates a new DeleteByNameCommand object
 */
public class DeleteByNameCommandParser implements Parser<DeleteByNameCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteByNameCommand
     * and returns an DeleteByNameCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteByNameCommand parse(String args) throws ParseException {
        try {
            String name = ParserUtil.parseNameString(args);
            return new DeleteByNameCommand(name);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteByNameCommand.MESSAGE_USAGE));
        }
    }

}
```
###### \java\seedu\address\logic\parser\UploadPhotoCommandParser.java
``` java
/**
 * Parses input arguments and creates a new UploadPhotoCommand object
 */
public class UploadPhotoCommandParser implements Parser<UploadPhotoCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UploadPhotoCommand
     * and returns a UploadPhotoCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UploadPhotoCommand parse(String args) throws ParseException {
        try {
            StringTokenizer st = new StringTokenizer(args);
            Index index = ParserUtil.parseIndex(st.nextToken());

            String photoPath;
            if (st.hasMoreTokens()) {
                photoPath = st.nextToken();
            } else {
                photoPath = "..\\addressbook4\\docs\\images\\default_photo.png";
            }

            while (st.hasMoreTokens()) {
                photoPath += " ";
                photoPath += st.nextToken();
            }

            Photo photoToUpload = new Photo(photoPath);
            return new UploadPhotoCommand(index, photoToUpload);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UploadPhotoCommand.MESSAGE_USAGE));
        }
    }

}
```
###### \java\seedu\address\model\customField\CustomField.java
``` java
/**
 * Represents a CustomField in the address book.
 * Guarantees: immutable.
 */
public class CustomField {

    public static final String MESSAGE_CUSTOM_FIELD_CONSTRAINTS = "CustomFields names should be alphanumeric";

    public final String customFieldName;
    private String customFieldValue;

    /**
     * Validates given customFieldName and customFieldValue.
     *
     * @throws IllegalValueException if the given customFieldName or customFieldValue string is invalid.
     */
    public CustomField(String customFieldName, String customFieldValue) throws IllegalValueException {
        requireNonNull(customFieldName);
        requireNonNull(customFieldValue);

        this.customFieldName = customFieldName;
        this.customFieldValue = customFieldValue;
    }

    /**
     *  Returns custom field value of this CustomField
     *
     * @return customFieldValue
     */
    public String getCustomFieldValue() {
        return customFieldValue;
    }

    /**
     *  Sets a new custom field value for this CustomField
     *
     * @param newCustomFieldValue
     */
    public void setCustomFieldValue(String newCustomFieldValue) {
        this.customFieldValue = newCustomFieldValue;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CustomField // instanceof handles nulls
                && this.customFieldName.equals(((CustomField) other).customFieldName))
                && this.customFieldValue.equals(((CustomField) other).customFieldValue); // state check
    }

    @Override
    public int hashCode() {
        return customFieldName.hashCode() + customFieldValue.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return customFieldValue;
    }

}
```
###### \java\seedu\address\model\customField\UniqueCustomFieldList.java
``` java
/**
 * A list of customField that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see CustomField#equals(Object)
 */
public class UniqueCustomFieldList implements Iterable<CustomField> {

    public final ObservableList<CustomField> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty CustomFieldList.
     */
    public UniqueCustomFieldList() {}

    /**
     * Creates a UniqueCustomFieldList using given customFields.
     * Enforces no nulls.
     */
    public UniqueCustomFieldList(Set<CustomField> customFields) {
        requireAllNonNull(customFields);
        internalList.addAll(customFields);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns all customFields in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<CustomField> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Replaces the CustomFields in this list with those in the argument customField list.
     */
    public void setCustomFields(Set<CustomField> customFields) {
        requireAllNonNull(customFields);
        internalList.setAll(customFields);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Ensures every customField in the argument list exists in this object.
     */
    public void mergeFrom(UniqueCustomFieldList from) {
        final Set<CustomField> alreadyInside = this.toSet();
        from.internalList.stream()
                .filter(customField -> !alreadyInside.contains(customField))
                .forEach(internalList::add);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns true if the list contains an equivalent CustomField as the given argument.
     */
    public boolean contains(CustomField toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a CustomField to the list.
     */
    public void add(CustomField toAdd) {
        requireNonNull(toAdd);

        for (CustomField cf : internalList) {
            if (cf.customFieldName.equals(toAdd.customFieldName)) {
                if (toAdd.getCustomFieldValue().equals("")) {
                    remove(toAdd.customFieldName);
                    return;
                }
                cf.setCustomFieldValue(toAdd.getCustomFieldValue());
                return;
            }
        }

        if (toAdd.getCustomFieldValue().equals("")) {
            return;
        }

        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     *  Remove a CustomField from the list according to the custom field name
     *
     * @param customFieldName
     */
    public void remove(String customFieldName) {
        for (CustomField cf : internalList) {
            if (cf.customFieldName.equals(customFieldName)) {
                internalList.remove(cf);
                break;
            }
        }
    }

    @Override
    public Iterator<CustomField> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<CustomField> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof UniqueCustomFieldList // instanceof handles nulls
                && this.internalList.equals(((UniqueCustomFieldList) other).internalList));
    }

    /**
     * Returns true if the element in this list is equal to the elements in {@code other}.
     * The elements do not have to be in the same order.
     */
    public boolean equalsOrderInsensitive(UniqueCustomFieldList other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        assert CollectionUtil.elementsAreUnique(other.internalList);
        return this == other || new HashSet<>(this.internalList).equals(new HashSet<>(other.internalList));
    }

    @Override
    public int hashCode() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (CustomField customField : internalList) {
            sb.append(customField.customFieldName);
            sb.append(": ");
            sb.append(customField.getCustomFieldValue());
            sb.append("\r\n");
        }
        return sb.toString();
    }

}
```
###### \java\seedu\address\model\person\Person.java
``` java
    public void setPhoto(Photo photo) {
        this.photo.set(requireNonNull(photo));
    }

    @Override
    public ObjectProperty<Photo> photoProperty() {
        return photo;
    }

    @Override
    public Photo getPhoto() {
        return photo.get();
    }
```
###### \java\seedu\address\model\person\Person.java
``` java
    /**
     * Returns an immutable custom field set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    @Override
    public Set<CustomField> getCustomFields() {
        return Collections.unmodifiableSet(customFields.get().toSet());
    }

    /**
     * Returns the list of custom fields of the person
     *
     * @return customFields.get()
     */
    @Override
    public UniqueCustomFieldList getCustomFieldList() {
        return customFields.get();
    }

    public ObjectProperty<UniqueCustomFieldList> customFieldProperty() {
        return customFields;
    }

    /**
     * Replaces this person's custom fields with the custom fields in the argument custom fields set.
     */
    public void setCustomFields(Set<CustomField> replacement) {
        customFields.set(new UniqueCustomFieldList(replacement));
    }
```
###### \java\seedu\address\model\person\Photo.java
``` java
/**
 * Represents a Person's photo in the address book.
 */
public class Photo {

    public final String pathName;

    /**
     *  Constructs a default photo.
     */
    public Photo() {
        pathName = "..\\addressbook4\\docs\\images\\default_photo.png";
    }

    /**
     * Constructs with a given pathName.
     */
    public Photo(String pathName) throws IllegalValueException {
        //requireNonNull(pathName);

        this.pathName = pathName;
    }

    /**
     * Returns true if a given string is empty, which means an unknown path
     */
    private static boolean isUnknownPath(String test) {
        return test.equals("");
    }

    /**
     *
     * @return true if a given pathName has unknown value
     */
    public boolean isUnknownPathName() {
        return pathName.equals("");
    }

    public String getPathName() {
        return pathName;
    }

    /**
     *  Displace the photo
     */
    public void showPhoto() {
        JFrame frame = new JFrame("Icon Photo");
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(pathName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ImageIcon imgIcon = new ImageIcon(img);
        JLabel lbl = new JLabel();
        lbl.setIcon(imgIcon);
        frame.getContentPane().add(lbl, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public String toString() {
        return pathName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Photo // instanceof handles nulls
                && this.pathName.equals(((Photo) other).pathName)); // state check
    }

    @Override
    public int hashCode() {
        return pathName.hashCode();
    }

}
```
###### \java\seedu\address\storage\XmlAdaptedCustomField.java
``` java
/**
 * JAXB-friendly adapted version of the Custom Field.
 */
public class XmlAdaptedCustomField {

    @XmlValue
    private String customField;

    /**
     * Constructs an XmlAdaptedCustomField.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedCustomField() {}

    /**
     * Converts a given Custom Field into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedCustomField(CustomField source) {
        customField = source.customFieldName + " " + source.getCustomFieldValue();
    }

    /**
     * Converts this jaxb-friendly adapted custom field object into the model's Custom Field object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public CustomField toModelType() throws IllegalValueException {
        StringTokenizer st = new StringTokenizer(customField);
        String customFieldName = st.nextToken();
        String customFieldValue;
        if (st.hasMoreTokens()) {
            customFieldValue = st.nextToken();
        } else {
            customFieldValue = "";
        }
        return new CustomField(customFieldName, customFieldValue);
    }

}
```
###### \java\seedu\address\storage\XmlAdaptedPhone.java
``` java
/**
 * JAXB-friendly adapted version of the Phone.
 */
public class XmlAdaptedPhone {

    @XmlValue
    private String phoneNum;

    /**
     * Constructs an XmlAdaptedPhone.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPhone() {}

    /**
     * Converts a given Phone into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedPhone(Phone source) {
        phoneNum = source.value;
    }

    /**
     * Converts this jaxb-friendly adapted phone object into the model's Phone object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Phone toModelType() throws IllegalValueException {
        return new Phone(phoneNum);
    }

}
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    /**
     *  Sets a background image for a stack pane
     */
    private void setBackground(StackPane pane, String pathname, int width, int height) {
        File file = new File(pathname);
        try {
            BackgroundImage backgroundImage = new BackgroundImage(
                    new Image(file.toURI().toURL().toString(), width, height, false, true),
                    BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                    BackgroundSize.DEFAULT);
            pane.setBackground(new Background(backgroundImage));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    /**
     *  Initialises icon photo
     */
    private void initPhoto(ReadOnlyPerson person) {
        String pathName = person.getPhoto().pathName;

        File photoImage = new File(pathName);
        Image photo = null;
        try {
            photo = new Image(photoImage.toURI().toURL().toString(), 80, 80, false, false);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        photoContainer.setImage(photo);

        Circle clip = new Circle(60, 60, 50);
        photoContainer.setClip(clip);
    }
```
