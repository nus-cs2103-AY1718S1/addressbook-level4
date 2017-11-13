package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.NusmodsCommandParser.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.NusmodsCommandParser.PREFIX_TUTORIAL;
import static seedu.address.logic.parser.NusmodsCommandParser.PREFIX_TYPE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.BrowserPanelChangeActiveTabEvent;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthdate;
import seedu.address.model.person.EmailAddress;
import seedu.address.model.person.Name;
import seedu.address.model.person.NusModules;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Photo;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UserId;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

//@@author ritchielq
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
