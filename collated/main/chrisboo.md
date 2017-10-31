# chrisboo
###### \java\seedu\address\commons\events\ui\OpenAddressBookRequestEvent.java
``` java
package seedu.address.commons.events.ui;

import java.io.File;

import org.apache.commons.io.FilenameUtils;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request to open a new AddressBook
 */
public class OpenAddressBookRequestEvent extends BaseEvent {

    private String fileName;
    private String filePath;

    /**
     *
     * @param file that contains the addressbook xml
     */
    public OpenAddressBookRequestEvent(File file) {
        this.fileName = FilenameUtils.removeExtension(file.getName());
        this.filePath = file.getPath();
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFileName() {
        return fileName;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\logic\commands\FindCommand.java
``` java
    private FindPersonDescriptor findPersonDescriptor;

    public FindCommand(FindPersonDescriptor findPersonDescriptor) {
        requireNonNull(findPersonDescriptor);

        this.findPersonDescriptor = new FindPersonDescriptor(findPersonDescriptor);
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(person -> findPersonDescriptor.match(person));
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof FindCommand // instanceof handles nulls
            && this.findPersonDescriptor.equals(((FindCommand) other).findPersonDescriptor)); // state check
    }

    /**
     * Stores the details to find of the person.
     */
    public static class FindPersonDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Birthday birthday;
        private Website website;
        private Set<Tag> tags;

        public FindPersonDescriptor() {
        }

        public FindPersonDescriptor(FindPersonDescriptor toCopy) {
            this.name = toCopy.name;
            this.phone = toCopy.phone;
            this.email = toCopy.email;
            this.address = toCopy.address;
            this.birthday = toCopy.birthday;
            this.website = toCopy.website;
            this.tags = toCopy.tags;
        }

        /**
         * @return false if no fields are provided
         */
        public boolean allNull() {
            return this.name == null
                && this.phone == null
                && this.email == null
                && this.address == null
                && this.birthday == null
                && this.website == null
                && this.tags == null;
        }

        /**
         * @param other to check
         * @return true if other matches all fields
         */
        public boolean match(Object other) {
            if (!(other instanceof Person)) {
                return false;
            }
            if (this.name != null && !match(this.name, ((Person) other).getName())) {
                return false;
            }
            if (this.phone != null && !match(this.phone, ((Person) other).getPhone())) {
                return false;
            }
            if (this.email != null && !this.email.equals(((Person) other).getEmail())) {
                return false;
            }
            if (this.address != null && !this.address.equals(((Person) other).getAddress())) {
                return false;
            }
            if (this.website != null && !this.website.equals(((Person) other).getWebsite())) {
                return false;
            }
            if (this.birthday != null && !this.birthday.equals(((Person) other).getBirthday())) {
                return false;
            }
            if (this.tags != null && !match(this.tags, ((Person) other).getTags())) {
                return false;
            }

            return true;
        }

        /**
         * @param predicate
         * @param test
         * @return true if test contains predicate
         */
        private boolean match(Name predicate, Name test) {
            if (predicate == test) {
                return true;
            }
            if (predicate == null || test == null) {
                return false;
            }

            String[] splitPredicate = predicate.toString().toUpperCase().split("\\s+");

            for (String keyword : splitPredicate) {
                if (keyword.equals("")) {
                    continue;
                }
                if (test.toString().toUpperCase().contains(keyword)) {
                    return true;
                }
            }

            return false;
        }

        /**
         *
         * @param predicate
         * @param test
         * @return true if predicate is a substring of test
         */
        private boolean match(Phone predicate, Phone test) {
            if (predicate == test) {
                return true;
            }
            if (predicate == null || test == null) {
                return false;
            }

            return test.toString().contains(predicate.toString());
        }

        /**
         * @param predicate
         * @param test
         * @return true if test contains a tag that is among the predicate
         */
        private boolean match(Set<Tag> predicate, Set<Tag> test) {
            for (Tag predicateTag : predicate) {
                for (Tag testTag : test) {
                    if (predicateTag.equals(testTag)) {
                        return true;
                    }
                }
            }
            return false;
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            if (phone.value != null) {
                this.phone = phone;
            }
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            if (email.value != null) {
                this.email = email;
            }
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            if (address.value != null) {
                this.address = address;
            }
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setBirthday(Birthday birthday) {
            if (birthday.value != null) {
                this.birthday = birthday;
            }
        }

        public Optional<Birthday> getBirthday() {
            return Optional.ofNullable(birthday);
        }

        public void setWebsite(Website website) {
            if (website.value != null) {
                this.website = website;
            }
        }

        public Optional<Website> getWebsite() {
            return Optional.ofNullable(website);
        }

        public void setTags(Set<Tag> tags) {
            this.tags = tags;
        }

        public Optional<Set<Tag>> getTags() {
            return Optional.ofNullable(tags);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof FindPersonDescriptor)) {
                return false;
            }

            // state check
            FindPersonDescriptor e = (FindPersonDescriptor) other;

            return getName().equals(e.getName())
                && getPhone().equals(e.getPhone())
                && getEmail().equals(e.getEmail())
                && getAddress().equals(e.getAddress())
                && getBirthday().equals(e.getBirthday())
                && getWebsite().equals(e.getWebsite())
                && getTags().equals(e.getTags());
        }
    }
```
###### \java\seedu\address\MainApp.java
``` java
    @Subscribe
    public void handleOpenAddressBookRequestEvent(OpenAddressBookRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));

        try {
            // change addressbook file path
            setAddressBookFilePath(event.getFilePath());
            setAddressBookAppName(event.getFileName());

            init();
            start(this.primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setAddressBookFilePath(String addressBookFilePath) {
        try {
            userPrefs = JsonUtil.readJsonFile("preferences.json", UserPrefs.class).get();
            userPrefs.setAddressBookFilePath(addressBookFilePath);
            JsonUtil.saveJsonFile(userPrefs, "preferences.json");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setAddressBookAppName(String addressBookFileName) {
        try {
            config = JsonUtil.readJsonFile("config.json", Config.class).get();
            config.setAppTitle(addressBookFileName);
            JsonUtil.saveJsonFile(config, "config.json");

            userPrefs = JsonUtil.readJsonFile("preferences.json", UserPrefs.class).get();
            userPrefs.setAddressBookName(addressBookFileName);
            JsonUtil.saveJsonFile(userPrefs, "preferences.json");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
```
###### \java\seedu\address\model\person\Address.java
``` java
    @Override
    public boolean equals(Object other) {
        if (other == this) { // short circuit if same object
            return true;
        } else if (!(other instanceof Address)) { // instanceof handle nulls
            return false;
        } else if (this.value == ((Address) other).value) {
            return true;
        } else if (this.value != null && this.value.equals(((Address) other).value)) { // state check
            return true;
        }

        return false;
    }
```
###### \java\seedu\address\model\person\Birthday.java
``` java
    @Override
    public boolean equals(Object other) {
        if (other == this) { // short circuit if same object
            return true;
        } else if (!(other instanceof Birthday)) { // instanceof handle nulls
            return false;
        } else if (this.value == ((Birthday) other).value) {
            return true;
        } else if (this.value != null && this.value.equals(((Birthday) other).value)) { // state check
            return true;
        }

        return false;
    }
```
###### \java\seedu\address\model\person\Email.java
``` java
    @Override
    public boolean equals(Object other) {
        if (other == this) { // short circuit if same object
            return true;
        } else if (!(other instanceof Email)) { // instanceof handle nulls
            return false;
        } else if (this.value == ((Email) other).value) {
            return true;
        } else if (this.value != null && this.value.equals(((Email) other).value)) { // state check
            return true;
        }

        return false;
    }
```
###### \java\seedu\address\model\person\Phone.java
``` java
    @Override
    public boolean equals(Object other) {
        if (other == this) { // short circuit if same object
            return true;
        } else if (!(other instanceof Phone)) { // instanceof handle nulls
            return false;
        } else if (this.value == ((Phone) other).value) {
            return true;
        } else if (this.value != null && this.value.equals(((Phone) other).value)) { // state check
            return true;
        }

        return false;
    }
```
###### \java\seedu\address\model\person\Website.java
``` java
    @Override
    public boolean equals(Object other) {
        if (other == this) { // short circuit if same object
            return true;
        } else if (!(other instanceof Website)) { // instanceof handle nulls
            return false;
        } else if (this.value == ((Website) other).value) {
            return true;
        } else if (this.value != null && this.value.equals(((Website) other).value)) { // state check
            return true;
        }

        return false;
    }
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    /**
     * Opens a FileChooser to let the user select an address book to load.
     */
    @FXML
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
            "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show open file dialog
        File file = fileChooser.showOpenDialog(new Stage());

        raise(new OpenAddressBookRequestEvent(file));
    }
```
