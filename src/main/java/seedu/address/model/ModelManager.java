package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javax.mail.AuthenticationFailedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.BackupAddressBookEvent;
import seedu.address.commons.events.ui.EmailDraftChangedEvent;
import seedu.address.email.Email;
import seedu.address.email.EmailManager;
import seedu.address.email.exceptions.EmailLoginInvalidException;
import seedu.address.email.exceptions.EmailMessageEmptyException;
import seedu.address.email.exceptions.EmailRecipientsEmptyException;
import seedu.address.email.message.MessageDraft;
import seedu.address.model.person.EmailAddress;
import seedu.address.model.person.Person;
import seedu.address.model.person.Photo;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final Email email;
    private final AddressBook addressBook;
    private final FilteredList<ReadOnlyPerson> filteredPersons;
    private final SortedList<ReadOnlyPerson> sortedPersonsList;
    private final SortedList<ReadOnlyPerson> sortedPersonsListBirthdate;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs, Email email) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);
        this.addressBook = new AddressBook(addressBook);
        this.email = email;
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        sortedPersonsList = new SortedList<ReadOnlyPerson>(filteredPersons);
        sortedPersonsListBirthdate = new SortedList<ReadOnlyPerson>(filteredPersons);
        sortFilteredPersons(0);
        sortBirthdate();

    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs(), new EmailManager());
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        addressBook.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    //@@author awarenessxz
    @Override
    public Email getEmailManager() {
        return email;
    }
    //@@author

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(addressBook));
    }

    @Override
    public synchronized void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException, IOException {
        addressBook.removePerson(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addPerson(ReadOnlyPerson person) throws DuplicatePersonException, IOException {
        addressBook.addPerson(person);
        sortFilteredPersons(0);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    //@@author wenzongteo
    @Override
    public synchronized String addImage(EmailAddress email, Photo photo) throws IOException {
        String folder = "data/images/";
        String fileExt = ".jpg";

        File imageFolder = new File(folder);

        if (!imageFolder.exists()) {
            imageFolder.mkdir();
        } else {

        }

        String destination = folder + email.toString() + fileExt;
        Path sourcePath = Paths.get(photo.toString());
        Path destPath = Paths.get(destination);

        Files.copy(sourcePath, destPath, StandardCopyOption.REPLACE_EXISTING);

        return folder + email.toString() + fileExt;
    }
    //@@author

    @Override
    public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
            throws DuplicatePersonException, PersonNotFoundException, IOException {
        requireAllNonNull(target, editedPerson);
        addressBook.updatePerson(target, editedPerson);
        indicateAddressBookChanged();
    }

    @Override
    public void deleteTag(Tag tag) throws DuplicatePersonException, PersonNotFoundException, IOException {
        for (int i = 0; i < addressBook.getPersonList().size(); i++) {
            ReadOnlyPerson orginalPerson = addressBook.getPersonList().get(i);

            Person person = new Person(orginalPerson);
            Set<Tag> tags = person.getTags();

            tags.remove(tag);
            person.setTags(tags);

            addressBook.updatePerson(orginalPerson, person);

        }
    }


    //=========== Filtered Person List Accessors =============================================================

    //@@author awarenessxz
    /**
     * Returns an unmodifiable view of the list of {@code ReadOnlyPerson} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
        return FXCollections.unmodifiableObservableList(sortedPersonsList);
    }
    //@@author

    //@@author hengyu95
    /**
     * Returns an unmodifiable view of the list of {@code ReadOnlyPerson} backed by the internal list of
     * {@code addressBook} sorted by upcoming birthday
     */
    @Override
    public ObservableList<ReadOnlyPerson> getFilteredPersonListBirthdate() {
        return FXCollections.unmodifiableObservableList(sortedPersonsListBirthdate);
    }
    //@@author


    //@@author awarenessxz
    @Override
    public void sortFilteredPersons(int sortOrder) {

        //sort by name by default
        Comparator<ReadOnlyPerson> sort = new Comparator<ReadOnlyPerson>() {
            @Override
            public int compare(ReadOnlyPerson o1, ReadOnlyPerson o2) {
                return o1.getName().fullName.toUpperCase().compareTo(o2.getName().fullName.toUpperCase());
            }
        };

        if (sortOrder == 1) {
            //sort by tags
            sort = new Comparator<ReadOnlyPerson>() {
                @Override
                public int compare(ReadOnlyPerson o1, ReadOnlyPerson o2) {
                    TreeSet<Tag> o1SortedTags = new TreeSet<Tag>(o1.getTags());
                    TreeSet<Tag> o2SortedTags = new TreeSet<Tag>(o2.getTags());

                    if (o1SortedTags.size() == 0) {
                        return 1;
                    } else if (o2SortedTags.size() == 0) {
                        return -1;
                    } else {
                        return o1SortedTags.first().tagName.compareTo(o2SortedTags.first().tagName);
                    }
                }
            };
        } else if (sortOrder == 2) {
            //sort by emails
            sort = new Comparator<ReadOnlyPerson>() {
                @Override
                public int compare(ReadOnlyPerson o1, ReadOnlyPerson o2) {
                    return o1.getEmailAddress().value.toUpperCase().compareTo(o2.getEmailAddress().value.toUpperCase());
                }
            };
        } else if (sortOrder == 3) {
            //sort by address
            sort = new Comparator<ReadOnlyPerson>() {
                @Override
                public int compare(ReadOnlyPerson o1, ReadOnlyPerson o2) {
                    return o1.getAddress().value.toUpperCase().compareTo(o2.getAddress().value.toUpperCase());
                }
            };
        }

        sortedPersonsList.setComparator(sort);
    }

    //@@author hengyu95
    /**
     * Returns a sorted unmodifiable view of the list {@code ReadOnlyPerson} backed by the internal list of
     * {@code addressBook} sorted by upcoming birthdays
     */
    public void sortBirthdate() {

        Comparator<ReadOnlyPerson> sort = new Comparator<ReadOnlyPerson>() {

            public int compare(ReadOnlyPerson o1, ReadOnlyPerson o2) {
                String birthdate1 = o1.getBirthdate().value;

                String birthdate2 = o2.getBirthdate().value;
                DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                LocalDate today = LocalDate.now();
                LocalDate date1;
                LocalDate date2;

                try {
                    date1 = LocalDate.parse(birthdate1, format).withYear(today.getYear());
                } catch (DateTimeParseException e) {
                    date1 = LocalDate.of(9999, 12, 30);
                }

                try {
                    date2 = LocalDate.parse(birthdate2, format).withYear(today.getYear());
                } catch (DateTimeParseException e) {
                    date2 = LocalDate.of(9999, 12, 30);
                }

                if (date1.isBefore(today)) {
                    date1 = date1.withYear(date1.getYear() + 1);
                }

                if (date2.isBefore(today)) {
                    date2 = date2.withYear(date2.getYear() + 1);
                }

                return date1.compareTo(date2);
            }
        };

        sortedPersonsListBirthdate.setComparator(sort);
    }
    //@@author

    @Override
    public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    //@@author awarenessxz
    @Override
    public void loginEmail(String [] loginDetails) throws EmailLoginInvalidException {
        email.loginEmail(loginDetails);
    }

    @Override
    public void sendEmail(MessageDraft message) throws EmailLoginInvalidException, EmailMessageEmptyException,
            EmailRecipientsEmptyException, AuthenticationFailedException {
        email.composeEmail(message);
        email.sendEmail();

        raise(new EmailDraftChangedEvent(email.getEmailDraft()));
    }

    @Override
    public String getEmailStatus() {
        return email.getEmailStatus();
    }

    @Override
    public void clearEmailDraft() {
        email.clearEmailDraft();

        raise(new EmailDraftChangedEvent(email.getEmailDraft()));
    }

    @Override
    public void draftEmail(MessageDraft message) {
        email.composeEmail(message);

        raise(new EmailDraftChangedEvent(email.getEmailDraft()));
    }
    //@@author

    @Override
    public boolean equals(Object obj) {

        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return addressBook.equals(other.addressBook)
                && sortedPersonsList.equals(other.sortedPersonsList)
                && email.equals(other.email);
    }

    //@author hengyu95
    @Override
    public void backupAddressBook() {
        raise(new BackupAddressBookEvent(addressBook));
    }

}
