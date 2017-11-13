# ritchielq
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
    /**
     * Creates new list of tags by taking tags in personToEdit,
     * removing duplicated tags in editPersonDescriptor,
     * and adding in all other tags from editPersonDescriptor
     *
     * @param personToEdit
     * @param editPersonDescriptor
     */
    private static Set<Tag> processTags(ReadOnlyPerson personToEdit,
                                    EditPersonDescriptor editPersonDescriptor) {
        Set<Tag> updatedTags = personToEdit.getTags();

        if (editPersonDescriptor.getTags().isPresent()) {
            Set<Tag> tagsToToggle = new HashSet<Tag>(editPersonDescriptor.getTags().get());

            if (tagsToToggle.isEmpty()) {
                return tagsToToggle;
            } else {
                for (Tag tag : updatedTags) {
                    if (!tagsToToggle.remove(tag)) {
                        tagsToToggle.add(tag);
                    }
                }
            }

            return tagsToToggle;
        } else {
            return updatedTags;
        }

    }

```
###### \java\seedu\address\logic\commands\NusmodsCommand.java
``` java
/**
 * Edits the details of an existing person in the address book.
 */
public class NusmodsCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "nusmods";
    public static final String COMMAND_ALIAS = "nm";
    public static final int NUSMODS_TAB = 1;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edit nusmods details of person identified "
            + "by the index number used in the last person listing.\n"
            + PREFIX_TYPE + "is followed by either add, delete or url.\n"
            + PREFIX_TYPE + " and " + PREFIX_MODULE_CODE + " must be filled."
            + "It is then followed any number of lessonType/lessonSlot\n"
            + "Format: " + COMMAND_WORD + " INDEX "
            + "[" + PREFIX_TYPE + "<ADD|DELETE|URL>] "
            + "[" + PREFIX_MODULE_CODE + "MODULE_CODE/URL] "
            + "[LESSONTYPE/LESSONSLOT]..\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_TYPE + "add "
            + PREFIX_MODULE_CODE + "CS2103T "
            + PREFIX_TUTORIAL + "T5";

    public static final String MESSAGE_NUSMODS_SUCCESS = "Changed modules: %1$s (%2$s)";
    public static final String MESSAGE_INVALID_TYPE = "t/ needs to be 'add', 'url',"
            + " or 'delete'. m/ needs to be filled\n + "
            + "E.g. nusmods 1 t/add m/CS2103T tut/T5";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String MESSAGE_INVALID_MODULE_DETAILS = "Module details invalid";

    private static final Logger logger = LogsCenter.getLogger(NusmodsCommand.class);

    private final Index index;
    private final NusmodsDescriptor nusmodsDescriptor;

    /**
     * @param index of the person in the filtered person list to modify
     * @param nusmodsDescriptor details to edit the person with
     */
    public NusmodsCommand(Index index, NusmodsDescriptor nusmodsDescriptor) {
        requireNonNull(index);
        requireNonNull(nusmodsDescriptor);

        this.index = index;
        this.nusmodsDescriptor = new NusmodsDescriptor(nusmodsDescriptor);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException, IOException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createEditedPerson(personToEdit, nusmodsDescriptor);

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        } catch (IOException ioe) {
            throw new AssertionError("The file must exist");
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        EventsCenter.getInstance().post(new BrowserPanelChangeActiveTabEvent(NUSMODS_TAB));
        EventsCenter.getInstance().post(new JumpToListRequestEvent(index));
        return new CommandResult(String.format(MESSAGE_NUSMODS_SUCCESS,
                lastShownList.get(index.getZeroBased()).getName().fullName, editedPerson.getNusModules().toString()));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code nusmodsDescriptor}.
     */
    private static Person createEditedPerson(
            ReadOnlyPerson personToEdit,
            NusmodsDescriptor nusmodsDescriptor) throws CommandException, MalformedURLException {
        assert personToEdit != null;

        Name updatedName = personToEdit.getName();
        Phone updatedPhone = personToEdit.getPhone();
        EmailAddress updatedEmail = personToEdit.getEmailAddress();
        Address updatedAddress = personToEdit.getAddress();
        Photo updatedPhoto = personToEdit.getPhoto();
        Set<Tag> updatedTags = personToEdit.getTags();
        Birthdate updatedBirthdate = personToEdit.getBirthdate();
        NusModules updatedNusModules = null;
        UserId updatedUserId = personToEdit.getUserId();

        if (nusmodsDescriptor.getType().toUpperCase().equals("ADD")
                || nusmodsDescriptor.getType().toUpperCase().equals("A")) {
            updatedNusModules = processNusmodsDescriptorForAdd(personToEdit, nusmodsDescriptor);
        }
        if (nusmodsDescriptor.getType().toUpperCase().equals("DELETE")
                || nusmodsDescriptor.getType().toUpperCase().equals("D")) {
            updatedNusModules = processNusmodsDescriptorForDelete(personToEdit, nusmodsDescriptor);

        }
        if (nusmodsDescriptor.getType().toUpperCase().equals("URL")
                || nusmodsDescriptor.getType().toUpperCase().equals("U")) {
            updatedNusModules = processNusmodsDescriptorForUrl(nusmodsDescriptor);
        }
        logger.info("Change person Nusmodules object to: " + updatedNusModules.toString());

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedPhoto, updatedTags,
                updatedBirthdate, updatedNusModules, updatedUserId);

    }

    /**
     * Creates a NusModules from url in nusmodsDescriptor and returns it
     *
     * @param nusmodsDescriptor
     * @return NusModules from url in nusmodsDescriptor
     */
    private static NusModules processNusmodsDescriptorForUrl(NusmodsDescriptor nusmodsDescriptor)
            throws CommandException, MalformedURLException {
        NusModules newNusModules = new NusModules();

        URL url = new URL(nusmodsDescriptor.getModuleCode().get());
        String query = url.getQuery();
        String[] lessons = query.split("&");
        for (String lesson : lessons) {
            int index1 = lesson.indexOf("[");
            int index2 = lesson.indexOf("]");
            int index3 = lesson.indexOf("=");
            String moduleCode = lesson.substring(0, index1);
            String lessonType = lesson.substring(index1 + 1, index2);
            String lessonSlot = lesson.substring(index3 + 1);

            HashMap<String, String> lessonHm = new HashMap<>();
            lessonHm.put(lessonType, lessonSlot);
            try {
                newNusModules = newNusModules.addModule(moduleCode, lessonHm);
            } catch (IllegalValueException e) {
                throw new CommandException(MESSAGE_INVALID_MODULE_DETAILS);
            }
        }

        return newNusModules;
    }

    /**
     * Removes module in nusmodsDescriptor from  personToEdit's Nusmodules
     *
     * @param personToEdit
     * @param nusmodsDescriptor
     * @return personToEdit's NusModules with removed module from nusmodsDescriptor
     */
    private static NusModules processNusmodsDescriptorForDelete(ReadOnlyPerson personToEdit,
                                                                NusmodsDescriptor nusmodsDescriptor)
            throws CommandException {

        NusModules nusModules = personToEdit.getNusModules();

        try {
            return nusModules.removeModule(nusmodsDescriptor.getModuleCode().get());
        } catch (IllegalValueException e) {
            throw new CommandException(MESSAGE_INVALID_MODULE_DETAILS);
        }
    }

    /**
     * Adds module in nusmodsDescriptor to personToEdit's Nusmodules
     *
     * @param personToEdit
     * @param nusmodsDescriptor
     * @return personToEdit's NusModules with added module from nusmodsDescriptor
     */
    private static NusModules processNusmodsDescriptorForAdd(
            ReadOnlyPerson personToEdit, NusmodsDescriptor nusmodsDescriptor) throws CommandException {
        NusModules nusModules = personToEdit.getNusModules();

        HashMap<String, String> lessons = new HashMap<>();

        if (nusmodsDescriptor.getDesignLecture().isPresent()) {
            lessons.put("DLEC", nusmodsDescriptor.getDesignLecture().get());
        }
        if (nusmodsDescriptor.getLaboratory().isPresent()) {
            lessons.put("LAB", nusmodsDescriptor.getLaboratory().get());
        }
        if (nusmodsDescriptor.getLecture().isPresent()) {
            lessons.put("LEC", nusmodsDescriptor.getLecture().get());
        }
        if (nusmodsDescriptor.getPackagedLecture().isPresent()) {
            lessons.put("PLEC", nusmodsDescriptor.getPackagedLecture().get());
        }
        if (nusmodsDescriptor.getPackagedTutorial().isPresent()) {
            lessons.put("PTUT", nusmodsDescriptor.getPackagedTutorial().get());
        }
        if (nusmodsDescriptor.getRecitation().isPresent()) {
            lessons.put("REC", nusmodsDescriptor.getRecitation().get());
        }
        if (nusmodsDescriptor.getSectionalTeaching().isPresent()) {
            lessons.put("SEC", nusmodsDescriptor.getSectionalTeaching().get());
        }
        if (nusmodsDescriptor.getSeminar().isPresent()) {
            lessons.put("SEM", nusmodsDescriptor.getSeminar().get());
        }
        if (nusmodsDescriptor.getTutorial().isPresent()) {
            lessons.put("TUT", nusmodsDescriptor.getTutorial().get());
        }
        if (nusmodsDescriptor.getTutorial2().isPresent()) {
            lessons.put("TUT2", nusmodsDescriptor.getTutorial2().get());
        }
        if (nusmodsDescriptor.getTutorial3().isPresent()) {
            lessons.put("TUT3", nusmodsDescriptor.getTutorial3().get());
        }

        try {
            return nusModules.addModule(nusmodsDescriptor.getModuleCode().get(), lessons);
        } catch (IllegalValueException e) {
            throw new CommandException(MESSAGE_INVALID_MODULE_DETAILS);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NusmodsCommand)) {
            return false;
        }

        // state check
        NusmodsCommand e = (NusmodsCommand) other;
        return index.equals(e.index)
                && nusmodsDescriptor.equals(e.nusmodsDescriptor);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class NusmodsDescriptor {
        private String type;
        private String moduleCode;
        private String designLecture;
        private String laboratory;
        private String lecture;
        private String packagedLecture;
        private String packagedTutorial;
        private String recitation;
        private String sectionalTeaching;
        private String seminar;
        private String tutorial;
        private String tutorial2;
        private String tutorial3;

        public NusmodsDescriptor() {}

        public NusmodsDescriptor(NusmodsDescriptor toCopy) {
            this.type = toCopy.type;
            this.moduleCode = toCopy.moduleCode;
            this.designLecture = toCopy.designLecture;
            this.laboratory = toCopy.laboratory;
            this.lecture = toCopy.lecture;
            this.packagedLecture = toCopy.packagedLecture;
            this.packagedTutorial = toCopy.packagedTutorial;
            this.recitation = toCopy.recitation;
            this.sectionalTeaching = toCopy.sectionalTeaching;
            this.seminar = toCopy.seminar;
            this.tutorial = toCopy.tutorial;
            this.tutorial2 = toCopy.tutorial2;
            this.tutorial3 = toCopy.tutorial3;
        }

        /**
         * Returns true if type if valid
         */
        public boolean isValidType() {
            if (this.type == null || this.moduleCode == null) {
                return false;
            }
            if (this.type.toUpperCase().equals("ADD") || this.type.toUpperCase().equals("A")
                    || this.type.toUpperCase().equals("DELETE") || this.type.toUpperCase().equals("D")
                    || this.type.toUpperCase().equals("URL") || this.type.toUpperCase().equals("U")) {
                return true;
            }
            return false;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof NusmodsCommand.NusmodsDescriptor)) {
                return false;
            }

            // state check
            NusmodsDescriptor e = (NusmodsDescriptor) other;

            return getType().equals(e.getType())
                    && getModuleCode().equals(e.getModuleCode())
                    && getDesignLecture().equals(e.getDesignLecture())
                    && getLaboratory().equals(e.getLaboratory())
                    && getLecture().equals(e.getLecture())
                    && getPackagedLecture().equals(e.getPackagedLecture())
                    && getPackagedTutorial().equals(e.getPackagedTutorial())
                    && getRecitation().equals(e.getRecitation())
                    && getSectionalTeaching().equals(e.getSectionalTeaching())
                    && getSeminar().equals(e.getSeminar())
                    && getTutorial().equals(e.getTutorial())
                    && getTutorial2().equals(e.getTutorial2())
                    && getTutorial3().equals(e.getTutorial3());
        }

        public Optional<String> getTutorial3() {
            return Optional.ofNullable(tutorial3);
        }

        public void setTutorial3(String tutorial3) {
            this.tutorial3 = tutorial3.toUpperCase();
        }

        public Optional<String> getTutorial2() {
            return Optional.ofNullable(tutorial2);
        }

        public void setTutorial2(String tutorial2) {
            this.tutorial2 = tutorial2.toUpperCase();
        }

        public Optional<String> getTutorial() {
            return Optional.ofNullable(tutorial);
        }

        public void setTutorial(String tutorial) {
            this.tutorial = tutorial.toUpperCase();
        }

        public Optional<String> getSeminar() {
            return Optional.ofNullable(seminar);
        }

        public void setSeminar(String seminar) {
            this.seminar = seminar.toUpperCase();
        }

        public Optional<String> getSectionalTeaching() {
            return Optional.ofNullable(sectionalTeaching);
        }

        public void setSectionalTeaching(String sectionalTeaching) {
            this.sectionalTeaching = sectionalTeaching.toUpperCase();
        }

        public Optional<String> getRecitation() {
            return Optional.ofNullable(recitation);
        }

        public void setRecitation(String recitation) {
            this.recitation = recitation.toUpperCase();
        }

        public Optional<String> getPackagedTutorial() {
            return Optional.ofNullable(packagedTutorial);
        }

        public void setPackagedTutorial(String packagedTutorial) {
            this.packagedTutorial = packagedTutorial.toUpperCase();
        }

        public Optional<String> getPackagedLecture() {
            return Optional.ofNullable(packagedLecture);
        }

        public void setPackagedLecture(String packagedLecture) {
            this.packagedLecture = packagedLecture.toUpperCase();
        }

        public Optional<String> getLecture() {
            return Optional.ofNullable(lecture);
        }

        public void setLecture(String lecture) {
            this.lecture = lecture.toUpperCase();
        }

        public Optional<String> getLaboratory() {
            return Optional.ofNullable(laboratory);
        }

        public void setLaboratory(String laboratory) {
            this.laboratory = laboratory.toUpperCase();
        }

        public Optional<String> getDesignLecture() {
            return Optional.ofNullable(designLecture);
        }

        public void setDesignLecture(String designLecture) {
            this.designLecture = designLecture.toUpperCase();
        }

        public Optional<String> getModuleCode() {
            return Optional.ofNullable(moduleCode);
        }

        public void setModuleCode(String moduleCode) {
            this.moduleCode = moduleCode.toUpperCase();
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type.toUpperCase();
        }
    }
}
```
###### \java\seedu\address\logic\parser\NusmodsCommandParser.java
``` java
/**
 * Parses input arguments and creates a new NusmodsCommand object
 */
public class NusmodsCommandParser implements Parser<NusmodsCommand> {
    public static final Prefix PREFIX_TYPE = new Prefix("t/");
    public static final Prefix PREFIX_MODULE_CODE = new Prefix("m/");
    public static final Prefix PREFIX_DESIGN_LECTURE = new Prefix("dlec/");
    public static final Prefix PREFIX_LABORATORY = new Prefix("lab/");
    public static final Prefix PREFIX_LECTURE = new Prefix("lec/");
    public static final Prefix PREFIX_PACKAGED_LECTURE = new Prefix("plec/");
    public static final Prefix PREFIX_PACKAGED_TUTORIAL = new Prefix("ptut/");
    public static final Prefix PREFIX_RECITATION = new Prefix("rec/");
    public static final Prefix PREFIX_SECTIONAL_TEACHING = new Prefix("sec/");
    public static final Prefix PREFIX_SEMINAR = new Prefix("sem/");
    public static final Prefix PREFIX_TUTORIAL = new Prefix("tut/");
    public static final Prefix PREFIX_TUTORIAL2 = new Prefix("tut2/");
    public static final Prefix PREFIX_TUTORIAL3 = new Prefix("tut3/");

    /**
     * Parses the given {@code String} of arguments in the context of the NusmodsCommand
     * and returns an NusmodsCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public NusmodsCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TYPE, PREFIX_MODULE_CODE, PREFIX_DESIGN_LECTURE,
                        PREFIX_LABORATORY, PREFIX_LECTURE, PREFIX_PACKAGED_LECTURE, PREFIX_PACKAGED_TUTORIAL,
                        PREFIX_RECITATION, PREFIX_SECTIONAL_TEACHING, PREFIX_SEMINAR, PREFIX_TUTORIAL,
                        PREFIX_TUTORIAL2, PREFIX_TUTORIAL3);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, NusmodsCommand.MESSAGE_USAGE));
        }

        NusmodsDescriptor nusmodsDescriptor = new NusmodsDescriptor();
        argMultimap.getValue(PREFIX_TYPE).ifPresent(nusmodsDescriptor::setType);
        argMultimap.getValue(PREFIX_MODULE_CODE).ifPresent(nusmodsDescriptor::setModuleCode);
        argMultimap.getValue(PREFIX_DESIGN_LECTURE).ifPresent(nusmodsDescriptor::setDesignLecture);
        argMultimap.getValue(PREFIX_LABORATORY).ifPresent(nusmodsDescriptor::setLaboratory);
        argMultimap.getValue(PREFIX_LECTURE).ifPresent(nusmodsDescriptor::setLecture);
        argMultimap.getValue(PREFIX_PACKAGED_LECTURE).ifPresent(nusmodsDescriptor::setPackagedLecture);
        argMultimap.getValue(PREFIX_PACKAGED_TUTORIAL).ifPresent(nusmodsDescriptor::setPackagedTutorial);
        argMultimap.getValue(PREFIX_RECITATION).ifPresent(nusmodsDescriptor::setRecitation);
        argMultimap.getValue(PREFIX_SECTIONAL_TEACHING).ifPresent(nusmodsDescriptor::setSectionalTeaching);
        argMultimap.getValue(PREFIX_SEMINAR).ifPresent(nusmodsDescriptor::setSeminar);
        argMultimap.getValue(PREFIX_TUTORIAL).ifPresent(nusmodsDescriptor::setTutorial);
        argMultimap.getValue(PREFIX_TUTORIAL2).ifPresent(nusmodsDescriptor::setTutorial2);
        argMultimap.getValue(PREFIX_TUTORIAL3).ifPresent(nusmodsDescriptor::setTutorial3);

        if (!nusmodsDescriptor.isValidType()) {
            throw new ParseException(NusmodsCommand.MESSAGE_INVALID_TYPE);
        }

        return new NusmodsCommand(index, nusmodsDescriptor);
    }

}
```
###### \java\seedu\address\model\person\NusModules.java
``` java
/**
 * Represents a Person's NusModules in the address book.
 * Guarantees: immutable; is valid as declared in isValidNusModules
 */
public class NusModules {

    public static final String MESSAGE_NUS_MODULE_CONSTRAINTS =
            "Person NUS modules must start with two or three letters followed by four digits. "
                    + "It may be followed by a uppercase alphabet";

    public static final String MESSAGE_LESSON_TYPE_CONSTRAINTS =
            "Lesson type must be 4 characters";

    /*
     * The first two or three characters must be uppercase alphabets, the following four characters must be digits.
     * It may be followed by a uppercase alphabet.
     */
    public static final String NUS_MODULE_VALIDATION_REGEX = "[A-Z]{2,3}[0-9]{4}[A-Z]?";

    /*
     * Lesson type must be 3 uppercase alphabets. Followed by a number of alphabet
     */
    public static final String LESSON_TYPE_VALIDATION_REGEX = "[A-Z]{3}[A-Z0-9]?";

    public static final int VALID_NUS_MODULE = 0;
    public static final int FAILED_NUS_MODULE_VALIDATION_REGEX = 1;
    public static final int FALIED_LESSON_TYPE_VALIDATION_REGEX = 2;

    public final HashMap<String, HashMap<String, String>> value;

    public NusModules() {
        this.value = new HashMap<>();
    }

    /**
     * Validates given nusModules.
     *
     * @throws IllegalValueException if given nusModules string is invalid.
     */
    public NusModules(HashMap<String, HashMap<String, String>> nusModules) throws IllegalValueException {
        requireNonNull(nusModules);
        switch (isValidNusModules(nusModules)) {
        case FAILED_NUS_MODULE_VALIDATION_REGEX:
            throw new IllegalValueException(MESSAGE_NUS_MODULE_CONSTRAINTS);
        case FALIED_LESSON_TYPE_VALIDATION_REGEX:
            throw new IllegalValueException(MESSAGE_LESSON_TYPE_CONSTRAINTS);
        default:
            break;
        }

        this.value = nusModules;
    }

    /**
     * Returns VALID_NUS_MODULE if a given HashMap is a valid person nusModules.
     * Returns FAILED_NUS_MODULE_VALIDATION_REGEX if it fails NUS_MODULE_VALIDATION_REGEX
     * Returns FALIED_LESSON_TYPE_VALIDATION_REGEX if it fails LESSON_TYPE_VALIDATION_REGEX
     */
    public static int isValidNusModules(HashMap<String, HashMap<String, String>> test) {
        for (Map.Entry<String, HashMap<String, String>> module : test.entrySet()) {
            String moduleCode = module.getKey();
            // If fail either, return 1 and 2 respectively
            if (!moduleCode.matches(NUS_MODULE_VALIDATION_REGEX)) {
                return FAILED_NUS_MODULE_VALIDATION_REGEX;
            }
            for (Map.Entry<String, String> lessons : module.getValue().entrySet()) {
                String lessonType = lessons.getKey();
                if (!lessonType.matches(LESSON_TYPE_VALIDATION_REGEX)) {
                    return FALIED_LESSON_TYPE_VALIDATION_REGEX;
                }
            }
        }

        return VALID_NUS_MODULE;
    }

    /**
     * Copies value to create and return modified NusModules by:
     * If NusModule does not have the module, add it
     * If it has, add or overwrite it's lessons with newLessons
     */
    public NusModules addModule(String moduleCode, HashMap<String, String> newLessons) throws IllegalValueException {

        // Copy oldNusModules value into new HashMap
        HashMap<String, HashMap<String, String>> newNusModulesHashMap = new HashMap<>();
        for (Map.Entry<String, HashMap<String, String>> oldNusModule : value.entrySet()) {
            newNusModulesHashMap.put(oldNusModule.getKey(), oldNusModule.getValue());
        }

        // Adding in new module or overwriting them.
        if (newNusModulesHashMap.containsKey(moduleCode)) {
            HashMap<String, String> currLessons = newNusModulesHashMap.get(moduleCode);
            for (Map.Entry<String, String> newLesson : newLessons.entrySet()) {
                currLessons.put(newLesson.getKey(), newLesson.getValue());
            }
        } else {
            newNusModulesHashMap.put(moduleCode, newLessons);
        }

        NusModules newNusModules = new NusModules(newNusModulesHashMap);
        return newNusModules;
    }

    /**
     * Copies value to create and return modified NusModules with
     * given module removed (if it exist)
     */
    public NusModules removeModule(String moduleCode) throws IllegalValueException {
        // Copy oldNusModules value into new HashMap
        HashMap<String, HashMap<String, String>> newNusModulesHashMap = new HashMap<>();
        for (Map.Entry<String, HashMap<String, String>> oldNusModule : value.entrySet()) {
            newNusModulesHashMap.put(oldNusModule.getKey(), oldNusModule.getValue());
        }

        newNusModulesHashMap.remove(moduleCode);
        NusModules newNusModules = new NusModules(newNusModulesHashMap);
        return newNusModules;
    }

    @Override
    public String toString() {
        String nusModuleString = "";
        for (Map.Entry<String, HashMap<String, String>> module : value.entrySet()) {
            String moduleCode = module.getKey();
            for (Map.Entry<String, String> lessons : module.getValue().entrySet()) {
                String lessonType = lessons.getKey();
                String lessonSlot = lessons.getValue();

                nusModuleString += moduleCode + "[" + lessonType + "]=" + lessonSlot + "&";
            }
            if (module.getValue().isEmpty()) {
                nusModuleString += moduleCode + "&";
            }
        }
        return nusModuleString;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NusModules // instanceof handles nulls
                && this.value.toString().equals(((NusModules) other).value.toString())); // Compare toString()
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\model\person\Person.java
``` java
    @Override
    public ObjectProperty<NusModules> nusModulesProperty() {
        return nusModules;
    }

    @Override
    public NusModules getNusModules() {
        if (nusModules == null) {
            return new NusModules();
        }
        return nusModules.get();
    }

    public void setNusModules(NusModules nusModules) {
        if (this.nusModules == null) {
            this.nusModules = new SimpleObjectProperty<>(nusModules);
        } else {
            this.nusModules.set(nusModules);
        }
    }

```
###### \java\seedu\address\storage\XmlAdaptedModuleLessons.java
``` java
/**
 * JAXB-friendly adapted version of the NusModule.value Map.Entry
 */
public class XmlAdaptedModuleLessons {

    @XmlAttribute
    private String lessonType;
    @XmlValue
    private String lessonSlot;

    /**
     * Constructs an XmlAdaptedModuleLessons.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedModuleLessons() {}

    /**
     * Converts a given Map.Entry into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedModuleLessons(Map.Entry<String, String> source) {
        lessonType = source.getKey();
        lessonSlot = source.getValue();
    }

    /**
     * Converts this jaxb-friendly adapted NusModuleLesson object into the model's NusModule.value Map.Entry
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public ArrayList<String> toNusLesson() throws IllegalValueException {
        ArrayList<String> nusLesson = new ArrayList<>();
        nusLesson.add(lessonType);
        nusLesson.add(lessonSlot);
        return nusLesson;
    }

}
```
###### \java\seedu\address\storage\XmlAdaptedNusModule.java
``` java
/**
 * JAXB-friendly adapted version of the NusModule.
 */
public class XmlAdaptedNusModule {

    @XmlAttribute
    private String moduleCode;
    @XmlElement(name = "lesson")
    private List<XmlAdaptedModuleLessons> nusLessons = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedNusModule.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedNusModule() {}

    /**
     * Converts a given NusModule Map.Entry into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedNusModule(Map.Entry<String, HashMap<String, String>> source) {
        moduleCode = source.getKey();
        for (Map.Entry<String, String> moduleLesson : source.getValue().entrySet()) {
            nusLessons.add(new XmlAdaptedModuleLessons(moduleLesson));
        }
    }

    /**
     * Converts this jaxb-friendly adapted NusModule object into the model's NusModule Entry
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public HashMap<String, HashMap<String, String>> toNusModulesModelEntry() throws IllegalValueException {
        HashMap<String, HashMap<String, String>> nusModuleEntry = new HashMap<>();
        HashMap<String, String> nusLessonEntry = new HashMap<>();
        for (XmlAdaptedModuleLessons nusLesson : nusLessons) {
            ArrayList<String> tempNusLessonEntry = nusLesson.toNusLesson();
            nusLessonEntry.put(tempNusLessonEntry.get(0), tempNusLessonEntry.get(1));
        }
        nusModuleEntry.put(moduleCode, nusLessonEntry);

        return nusModuleEntry;
    }

}
```
###### \java\seedu\address\storage\XmlAdaptedPerson.java
``` java
    /**
     * Processes ArrayList<XmlAdaptedNusModule/> nusModules and returns NusModule object
     *
     * @return personNusModules
     * @throws IllegalValueException
     */
    private NusModules getPersonNusModules() throws IllegalValueException {
        NusModules personNusModules = new NusModules();
        if (!nusModules.isEmpty()) {
            for (XmlAdaptedNusModule nusModule : nusModules) {
                for (Map.Entry<String, HashMap<String, String>> mod : nusModule.toNusModulesModelEntry().entrySet()) {
                    personNusModules = personNusModules.addModule(mod.getKey(), mod.getValue());
                }
            }
        }
        return personNusModules;
    }

    /**
     * Processes person's NusModule and return it in ArrayList<XmlAdaptedNusModule/> format
     *
     * @param source
     * @return xmlAdaptedNusModulesArrayList
     */
    private ArrayList<XmlAdaptedNusModule> getArrayListOfXmlAdaptedNusModule(ReadOnlyPerson source) {
        ArrayList<XmlAdaptedNusModule> xmlAdaptedNusModulesArrayList = new ArrayList<>();
        if (source.getNusModules() != null) {
            for (Map.Entry<String, HashMap<String, String>> module : source.getNusModules().value.entrySet()) {
                xmlAdaptedNusModulesArrayList.add(new XmlAdaptedNusModule(module));
            }
        }
        return xmlAdaptedNusModulesArrayList;
    }
}
```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
    /**
     * Loads nusmods website base on person object
     * @param person
     */
    private void loadPersonPage(ReadOnlyPerson person) {
        // Loads website if nusModule is not null or empty
        if (person.getNusModules() != null && !person.getNusModules().value.isEmpty()) {
            loadPage(NUSMODS_SEARCH_URL_PREFIX + academicYear + "/sem" + semester + "?"
                    + person.getNusModules().toString());
        } else {
            loadNoTimetablePage();
        }
    }

    public String getSemester() {
        return semester;
    }

    public String getAcademicYear() {
        return academicYear;
    }

```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
    private void setAcademicYearSemester(Config config) {
        academicYear = config.getAcademicYear();
        semester = config.getSemester();
    }

```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
    /**
     * Sets active tab to {@code activeTab}
     */
    private void setActiveTab() {
        switch (activeTab) {
        case INSTA_TAB:
            browserPanel.getSelectionModel().select(instaTab);
            break;
        case NUSMODS_TAB:
            browserPanel.getSelectionModel().select(nusModsTab);
            break;
        default:
            break;
        }
    }

```
###### \java\seedu\address\ui\MainWindow.java
``` java
    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
        setAccelerator(shortcutMenuUndo, KeyCombination.valueOf("Ctrl+z"));
        setAccelerator(shortcutMenuRedo, KeyCombination.valueOf("Ctrl+y"));
        setAccelerator(shortcutMenuScrollUp, KeyCombination.valueOf("Page Up"));
        setAccelerator(shortcutMenuScrollDown, KeyCombination.valueOf("Page Down"));
        setAccelerator(shortcutMenuToggleTab, KeyCombination.valueOf("Ctrl+t"));
    }

```
###### \java\seedu\address\ui\MainWindow.java
``` java
    /**
     * Calls PersonListPanel to scrolls up personListView
     */
    @FXML
    private void handleScrollDown() {
        leftDisplayPanel.scrollDown();
    }

    /**
     * Calls PersonListPanel to scroll up personListView
     */
    @FXML
    private void handleScrollUp() {
        leftDisplayPanel.scrollUp();
    }

```
###### \java\seedu\address\ui\MainWindow.java
``` java
    /**
     * Calls Logic to execute "redo"
     */
    @FXML
    private void handleRedo() {
        try {
            CommandResult commandResult = logic.execute("redo");

            // process result of the command
            logger.info("Result: " + commandResult.feedbackToUser);
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser, false));
        } catch (CommandException | ParseException | IOException e) {
            raise(new NewResultAvailableEvent(e.getMessage(), true));
        }
    }

    /**
     * Calls Logic to execute "undo"
     */
    @FXML
    private void handleUndo() {
        try {
            CommandResult commandResult = logic.execute("undo");

            // process result of the command
            logger.info("Result: " + commandResult.feedbackToUser);
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser, false));
        } catch (CommandException | ParseException | IOException e) {
            raise(new NewResultAvailableEvent(e.getMessage(), true));
        }
    }

```
###### \java\seedu\address\ui\PersonCard.java
``` java
    private static String getColorForTag(String tagName) {
        if (!tagColors.containsKey(tagName)) {
            tagColors.put(tagName, getRandomDarkColor());
        }

        return tagColors.get(tagName);
    }

    private static String getRandomDarkColor() {
        Random random = new Random();

        int red;
        int green;
        int blue;

        do {
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);
        } while ((red * 0.299) + (green * 0.587) + (blue * 0.114) > 186);

        return "rgb(" + red + "," + green + "," + blue + ")";
    }

```
###### \java\seedu\address\ui\PersonCardBirthday.java
``` java
    private static String getColorForTag(String tagName) {
        if (!tagColors.containsKey(tagName)) {
            tagColors.put(tagName, getRandomDarkColor());
        }

        return tagColors.get(tagName);
    }

    private static String getRandomDarkColor() {
        Random random = new Random();

        int red;
        int green;
        int blue;

        // Do while too luminous
        do {
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);
        } while ((red * 0.299) + (green * 0.587) + (blue * 0.114) > 186);

        return "rgb(" + red + "," + green + "," + blue + ")";
    }

```
###### \java\seedu\address\ui\PersonListPanel.java
``` java
    /**
     * Scrolls one page down
     */
    public void scrollDown() {
        Platform.runLater(() -> {
            if (personListViewScrollBar == null) {
                setPersonListViewScrollBar();
            }

            /*
             * Changing unit increment with setUnitIncrement() does not effect amount scrolled with increment()
             * Using loop as a workaround.
             */
            for (int i = 0; i < SCROLL_INCREMENT; i++) {
                personListViewScrollBar.increment();
            }
        });
    }

    /**
     * Scrolls one page up
     */
    public void scrollUp() {
        Platform.runLater(() -> {
            if (personListViewScrollBar == null) {
                setPersonListViewScrollBar();
            }

            /*
             * Changing unit increment with setUnitIncrement() does not effect amount scrolled with increment()
             * Using loop as a workaround.
             */
            for (int i = 0; i < SCROLL_INCREMENT; i++) {
                personListViewScrollBar.decrement();
            }
        });
    }

```
###### \java\seedu\address\ui\PersonListPanel.java
``` java
    @Subscribe
    private void handleDeselectEvent(DeselectEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        personListView.getSelectionModel().clearSelection();
    }

```
###### \resources\view\MainWindow.fxml
``` fxml
      <MenuItem fx:id="shortcutMenuUndo" mnemonicParsing="false" onAction="#handleUndo" text="Undo" />
      <MenuItem fx:id="shortcutMenuRedo" mnemonicParsing="false" onAction="#handleRedo" text="Redo" />
      <MenuItem fx:id="shortcutMenuScrollUp" mnemonicParsing="false" onAction="#handleScrollUp" text="Scroll Up" />
      <MenuItem fx:id="shortcutMenuScrollDown" mnemonicParsing="false" onAction="#handleScrollDown" text="Scroll Down" />
```
