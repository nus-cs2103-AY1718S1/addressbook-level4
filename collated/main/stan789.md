# stan789
###### \java\seedu\address\logic\commands\EmailCommand.java
``` java
    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        ReadOnlyPerson personToEmail = lastShownList.get(targetIndex.getZeroBased());
        if (personToEmail.getEmail().toString().equals("~")) {
            throw new CommandException(MESSAGE_EMPTY_EMAIL);
        }

        try {
            desktopEmail(personToEmail.getEmail().toString());
        } catch (URISyntaxException e) {
            throw new CommandException(MESSAGE_INVALID_EMAIL);
        } catch (IOException | SecurityException e) {
            throw new CommandException(MESSAGE_EMAIL_FAIL);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }

```
###### \java\seedu\address\logic\commands\EmailCommand.java
``` java
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailCommand // instanceof handles nulls
                && this.targetIndex.equals(((EmailCommand) other).targetIndex)); // state check
    }

```
###### \java\seedu\address\logic\commands\EmailCommand.java
``` java
    /**
     * redirects user to their desktop's default email application.
     *
     * @param email email of the recipient.
     * @throws CommandException if desktop not supported
     * @throws IOException if desktop mail application cannot be opened or not found
     */

    protected void desktopEmail(String email) throws IOException, URISyntaxException, CommandException {
        if (!Desktop.isDesktopSupported()) {
            throw new CommandException(MESSAGE_NOT_SUPPORTED);
        }
        Desktop desktop = getDesktop();
        desktop.mail(new URI("mailto:" + email));
    }

}
```
###### \java\seedu\address\logic\commands\ExportCommand.java
``` java
    @Override
    public CommandResult execute() throws CommandException {

        try {
            model.exportFile(fileLocation, extension);

        } catch (IOException e) {
            throw new CommandException(MESSAGE_WRITE_ERROR);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, fileName));
    }

```
###### \java\seedu\address\logic\commands\ExportCommand.java
``` java
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExportCommand // instanceof handles nulls
                && this.fileLocation.equals(((ExportCommand) other).fileLocation)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\ImportCommand.java
``` java
    @Override
    public CommandResult execute() throws CommandException {
        ImportAnalysis importAnalysis = new ImportAnalysis();

        try {
            model.importFile(fileLocation, importAnalysis);

        } catch (EmptyFileException e) {
            throw new CommandException(MESSAGE_EMPTY_FILE);

        } catch (IOException e) {
            throw new CommandException(MESSAGE_FILE_INVALID);
        }
        String feedbackToUser = String.format(MESSAGE_SUCCESS, importAnalysis.getNumContacts());

        if (importAnalysis.isDuplicateContacts()) {
            feedbackToUser = feedbackToUser.concat(MESSAGE_DUPLICATE);
        }
        if (importAnalysis.isIllegalValue()) {
            feedbackToUser = feedbackToUser.concat(MESSAGE_ILLEGAL_VALUE);
        }
        return new CommandResult(feedbackToUser);
    }

```
###### \java\seedu\address\logic\commands\ImportCommand.java
``` java
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ImportCommand // instanceof handles nulls
                && this.fileLocation.equals(((ImportCommand) other).fileLocation)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\SortCommand.java
``` java
    @Override
    public CommandResult execute() throws CommandException {
        try {
            if (this.binMode) {
                model.executeBinSort(sortType);
            } else {
                model.executeSort(sortType);
            }
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (EmptyAddressBookException e) {
            throw new CommandException(MESSAGE_NO_PERSON_TO_SORT);
        }

    }

```
###### \java\seedu\address\logic\commands\SortCommand.java
``` java
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortCommand // instanceof handles nulls
                && this.sortType.equals(((SortCommand) other).sortType)); // state check
    }
}
```
###### \java\seedu\address\logic\parser\EmailCommandParser.java
``` java
    /**
     * Parses the given {@code String} of arguments in the context of the EmailCommand
     * and returns an EmailCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EmailCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new EmailCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\logic\parser\ExportCommandParser.java
``` java
    /**
     * Parses the given {@code String} of arguments in the context of the ExportCommand
     * and returns an ExportCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    public ExportCommand parse(String args) throws ParseException {

        String trimmedArgs = args.trim();

        trimmedArgs = trimmedArgs.replace("\\", "/");

        if (args.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
        }
        String directory = trimmedArgs.substring(0, trimmedArgs.lastIndexOf("/") + 1);
        File dir = new File(directory);
        if (!dir.isDirectory()) {
            throw new ParseException(INVALID_DIRECTORY);
        }

        String fileName = trimmedArgs.substring(trimmedArgs.lastIndexOf("/") + 1, trimmedArgs.lastIndexOf("."));
        if (!fileName.matches("[A-Za-z0-9.-_]+")) {
            throw new ParseException(INVALID_FILE_NAME);
        }

        String extension = trimmedArgs.substring(trimmedArgs.lastIndexOf(".") + 1, trimmedArgs.length());
        if (!extension.equals(VCF_EXTENSION) && !extension.equals(CSV_EXTENSION)) {
            throw new ParseException(INVALID_EXTENSION);
        }

        return new ExportCommand(trimmedArgs, fileName, extension);
    }
}

```
###### \java\seedu\address\logic\parser\ImportCommandParser.java
``` java
    /**
     * Parses the given {@code String} of arguments in the context of the ImportCommand
     * and returns an ImportCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    public ImportCommand parse(String args) throws ParseException {

        String trimmedArgs = args.trim();

        trimmedArgs = trimmedArgs.replace("\\", "/");

        if (args.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));
        }
        fileLocation = Paths.get(trimmedArgs);
        File file = new File(trimmedArgs);
        if (!file.isFile()) {
            throw new ParseException(NO_FILE_FOUND);
        }
        String filename = file.getName();
        String extension = filename.substring(filename.lastIndexOf(".") + 1, filename.length());
        if (!extension.equals(VCF_EXTENSION)) {
            throw new ParseException(FILE_WRONG_FORMAT);
        }
        return new ImportCommand(fileLocation);
    }
}
```
###### \java\seedu\address\logic\parser\SortCommandParser.java
``` java
    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns an SortCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim().toLowerCase();
        if (trimmedArgs.isEmpty() || (!trimmedArgs.equals(SORT_NAME) && !trimmedArgs.equals(SORT_EMAIL))) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
        return new SortCommand(trimmedArgs);
    }
}
```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Sorts the list by type(name or email) in alphabetical order.
     */

    public void executeSort(String sortType) throws EmptyAddressBookException {
        persons.sort(sortType);
    }

    //// person-level operations

    /**
     * Adds a person to the address book.
     * Also checks the new person's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the person to point to those in {@link #tags}.
     *
     * @throws DuplicatePersonException if an equivalent person already exists.
     */
    public void addPerson(ReadOnlyPerson p) throws DuplicatePersonException {
        Person newPerson = new Person(p);
        syncMasterTagListWith(newPerson);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        persons.add(newPerson);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedReadOnlyPerson}.
     * {@code AddressBook}'s tag list will be updated with the tags of {@code editedReadOnlyPerson}.
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *      another existing person in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     *
     * @see #syncMasterTagListWith(Person)
     */
    public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedReadOnlyPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireNonNull(editedReadOnlyPerson);

        Person editedPerson = new Person(editedReadOnlyPerson);
        syncMasterTagListWith(editedPerson);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        persons.setPerson(target, editedPerson);
    }

    /**
     * Ensures that every tag in this person:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     */
    private void syncMasterTagListWith(Person person) {
        final UniqueTagList personTags = new UniqueTagList(person.getTags());
        tags.mergeFrom(personTags);

        // Create map with values = tag object references in the master list
        // used for checking person tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        tags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of person tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        personTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));
        person.setTags(correctTagReferences);
    }

    /**
     * Ensures that every tag in these persons:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     *  @see #syncMasterTagListWith(Person)
     */
    protected void syncMasterTagListWith(UniquePersonList persons) {
        persons.forEach(this::syncMasterTagListWith);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * @throws PersonNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public ReadOnlyPerson removePerson(ReadOnlyPerson key) throws PersonNotFoundException {
        ReadOnlyPerson deletedPerson = persons.remove(key);
        if (deletedPerson != null) {
            return deletedPerson;
        } else {
            throw new PersonNotFoundException();
        }
    }

    //// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asObservableList().size() + " persons, " + tags.asObservableList().size() +  " tags";
        // TODO: refine later
    }

    @Override
    public ObservableList<ReadOnlyPerson> getPersonList() {
        return persons.asObservableList();
    }

    @Override
    public ObservableList<Tag> getTagList() {
        return tags.asObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && this.persons.equals(((AddressBook) other).persons)
                && this.tags.equalsOrderInsensitive(((AddressBook) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(persons, tags);
    }
}
```
###### \java\seedu\address\model\Model.java
``` java
    /**
     * sorts the list in the addressbook by alphabetical order.
     * @throws EmptyAddressBookException if addressbook is empty
     */
    void executeSort(String sortType) throws EmptyAddressBookException;

```
###### \java\seedu\address\model\Model.java
``` java
    /** imports a VCard file to OneBook.
     * @throws IOException if file cannot be read.
     */
    void importFile(Path fileLocation, ImportAnalysis importAnalysis) throws IOException;

```
###### \java\seedu\address\model\Model.java
``` java
    /** Exports the current contacts in OneBook to VCard or Csv file*/
    void exportFile(String fileLocation, String extension) throws IOException;
}
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void executeSort(String sortType) throws EmptyAddressBookException {
        addressBook.executeSort(sortType);
        indicateAddressBookChanged();
    }

```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void importFile(Path fileLocation, ImportAnalysis importAnalysis) throws IOException {
        Integer count = 0;
        ImportVCardFile importFile = new ImportVCardFile(fileLocation, importAnalysis);
        ArrayList<Person> person = importFile.getPersonFromFile();
        for (Person p : person) {
            try {
                addPerson(p);
                count++;
            } catch (DuplicatePersonException e) {
                importAnalysis.setDuplicateContacts(true);
            }
        }
        importAnalysis.setNumContacts(count);
    }

```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void exportFile(String fileLocation, String extension) throws IOException {
        ObservableList<ReadOnlyPerson> person = getAddressBook().getPersonList();
        if (extension.equals("vcf")) {
            ExportVCardFile exportVCardFile = new ExportVCardFile(fileLocation);
            exportVCardFile.createVCardFile(person);
        } else {
            ExportCsvFile exportCsvFile = new ExportCsvFile(fileLocation);
            exportCsvFile.createCsvFile(person);
        }

    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public ReadOnlyAddressBook getRecycleBin() {
        return recycleBin;
    }

```
###### \java\seedu\address\model\person\exceptions\EmptyAddressBookException.java
``` java
/**
 * Signals that the operation will result in executing an empty address.
 */

public class EmptyAddressBookException extends Exception {
    public EmptyAddressBookException() {
    }
}
```
###### \java\seedu\address\model\person\UniquePersonList.java
``` java
    /**
     * Sorts the list by type(name or email) in alphabetical order.
     */
    public void sort(String sortType) throws EmptyAddressBookException {
        if (internalList.isEmpty()) {
            throw new EmptyAddressBookException();
        }

        switch(sortType) {

        case SORT_NAME:
            internalList.sort(Comparator.comparing(p -> p.getName().toString().toLowerCase()));
            break;

        case SORT_EMAIL:
            internalList.sort(Comparator.comparing(p -> p.getEmail().toString().toLowerCase()));
            break;

        default:
        }

    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     *
     * @throws DuplicatePersonException if the replacement is equivalent to another existing person in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     */
    public void setPerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireNonNull(editedPerson);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        if (!target.equals(editedPerson) && internalList.contains(editedPerson)) {
            throw new DuplicatePersonException();
        }

        internalList.set(index, new Person(editedPerson));
    }

    /**
     * Removes the equivalent person from the list.
     *
     * @throws PersonNotFoundException if no such person could be found in the list.
     */
    public ReadOnlyPerson remove(ReadOnlyPerson toRemove) throws PersonNotFoundException {
        requireNonNull(toRemove);
        final boolean personFoundAndDeleted = internalList.remove(toRemove);
        if (!personFoundAndDeleted) {
            throw new PersonNotFoundException();
        }
        return toRemove;
    }

    public void setPersons(UniquePersonList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setPersons(List<? extends ReadOnlyPerson> persons) throws DuplicatePersonException {
        final UniquePersonList replacement = new UniquePersonList();
        for (final ReadOnlyPerson person : persons) {
            replacement.add(new Person(person));
        }
        setPersons(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<ReadOnlyPerson> asObservableList() {
        return FXCollections.unmodifiableObservableList(mappedList);
    }


    @Override
    public Iterator<Person> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniquePersonList // instanceof handles nulls
                        && this.internalList.equals(((UniquePersonList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
```
###### \java\seedu\address\storage\exceptions\EmptyFileException.java
``` java
/**
 * Signals that the file read is an empty file.
 */

public class EmptyFileException extends IOException {
    public EmptyFileException() {

    }
}
```
###### \java\seedu\address\storage\exceptions\WrongFormatInFileException.java
``` java
/**
 * Signals that the format in the file is wrong.
 */

public class WrongFormatInFileException extends IOException {
    public WrongFormatInFileException() {

    }
}
```
###### \java\seedu\address\storage\ExportCsvFile.java
``` java
    /**
     * Create a csv file to the directory.
     */
    public void createCsvFile(ObservableList<ReadOnlyPerson> person) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(new File(fileLocation));
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

        bufferedWriter.write(heading);
        bufferedWriter.newLine();

        for (ReadOnlyPerson p : person) {

            writeCsvName(bufferedWriter, p);
            writeCsvOrganisation(bufferedWriter, p);
            writeCsvAddress(bufferedWriter, p);
            writeCsvPhone(bufferedWriter, p);
            writeCsvBirthday(bufferedWriter, p);
            writeCsvEmail(bufferedWriter, p);
            writeCsvRemark(bufferedWriter, p);
            bufferedWriter.newLine();
        }

        bufferedWriter.close();
    }

```
###### \java\seedu\address\storage\ExportCsvFile.java
``` java
    /**
     * Writes a person's name to csv file
     */
    private void writeCsvName(BufferedWriter bufferedWriter, ReadOnlyPerson person) throws IOException {
        String[] name = person.getName().toString().split(" ", 2);
        bufferedWriter.write(name[0]);
        bufferedWriter.write(",");

        if (name.length == 2) {
            bufferedWriter.write(name[1]);
        }
        bufferedWriter.write(",");
    }

```
###### \java\seedu\address\storage\ExportCsvFile.java
``` java
    /**
     * Writes a person's organisation to csv file
     */
    private void writeCsvOrganisation(BufferedWriter bufferedWriter, ReadOnlyPerson person) throws  IOException {
        if (!person.getOrganisation().toString().equals("~")) {
            bufferedWriter.write(person.getOrganisation().toString());
        }
        bufferedWriter.write(",");
    }

```
###### \java\seedu\address\storage\ExportCsvFile.java
``` java
    /**
     * Writes a person's address to csv file
     */
    private void writeCsvAddress(BufferedWriter bufferedWriter, ReadOnlyPerson person) throws  IOException {
        if (!person.getAddress().toString().equals("~")) {
            String address = person.getAddress().toString();
            address = address.replaceAll(",", "");
            bufferedWriter.write(address);
        }
        bufferedWriter.write(",");
    }

```
###### \java\seedu\address\storage\ExportCsvFile.java
``` java
    /**
     * Writes a person's phone to csv file
     */
    private void writeCsvPhone(BufferedWriter bufferedWriter, ReadOnlyPerson person) throws  IOException {
        if (!person.getPhone().toString().equals("~")) {
            bufferedWriter.write(person.getPhone().toString());
        }
        bufferedWriter.write(",");
    }

```
###### \java\seedu\address\storage\ExportCsvFile.java
``` java
    /**
     * Writes a person's birthday to csv file
     */
    private void writeCsvBirthday(BufferedWriter bufferedWriter, ReadOnlyPerson person) throws  IOException {
        if (!person.getBirthday().toString().equals("~")) {
            String birthday = person.getBirthday().toString();
            birthday = birthday.replaceAll("-", "/");
            bufferedWriter.write(birthday);
        }
        bufferedWriter.write(",");
    }

```
###### \java\seedu\address\storage\ExportCsvFile.java
``` java
    /**
     * Writes a person's email to csv file
     */
    private void writeCsvEmail(BufferedWriter bufferedWriter, ReadOnlyPerson person) throws  IOException {
        if (!person.getEmail().toString().equals("~")) {
            bufferedWriter.write(person.getEmail().toString());
        }
        bufferedWriter.write(",");
    }

```
###### \java\seedu\address\storage\ExportCsvFile.java
``` java
    /**
     * Writes a person's remarks to csv file
     */
    private void writeCsvRemark(BufferedWriter bufferedWriter, ReadOnlyPerson person) throws  IOException {
        if (!person.getRemark().toString().equals("~")) {
            bufferedWriter.write(person.getRemark().toString());
        }
    }

}
```
###### \java\seedu\address\storage\ExportVCardFile.java
``` java
    /**
     * write vCard file from the directory. supports up to VCard Version 3.0
     */
    public void createVCardFile(ObservableList<ReadOnlyPerson> person) throws IOException {

        FileOutputStream outputStream = new FileOutputStream(new File(fileLocation));
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

        for (ReadOnlyPerson p : person) {
            bufferedWriter.write(vcf.getBegin());
            bufferedWriter.newLine();

            bufferedWriter.write(vcf.getVersion());
            bufferedWriter.newLine();

            writeVCardName(bufferedWriter, p);
            writeVCardEmail(bufferedWriter, p);
            writeVCardPhone(bufferedWriter, p);
            writeVCardTag(bufferedWriter, p);
            writeVCardBirthday(bufferedWriter, p);
            writeVCardAddress(bufferedWriter, p);
            writeVCardOrganisation(bufferedWriter, p);
            writeVCardRemark(bufferedWriter, p);

            bufferedWriter.write(vcf.getEnd());
            bufferedWriter.newLine();
        }
        bufferedWriter.close();
    }

```
###### \java\seedu\address\storage\ExportVCardFile.java
``` java
    /**
     * write a person's name and full name to VCard file.
     */
    private void writeVCardName(BufferedWriter bufferedWriter, ReadOnlyPerson person) throws IOException {

        bufferedWriter.write(vcf.getFullName() + ":" + person.getName());
        bufferedWriter.newLine();

        String[] arrayName = person.getName().toString().split(" ", 2);

        bufferedWriter.write(vcf.getName() + ":" + arrayName[INDEX_ZERO] + ";");
        if (arrayName.length == INDEX_TWO) {
            bufferedWriter.write(arrayName[INDEX_ONE]);
        }
        bufferedWriter.newLine();
    }

```
###### \java\seedu\address\storage\ExportVCardFile.java
``` java
    /**
     * write a person's email to VCard file.
     */
    private void writeVCardEmail(BufferedWriter bufferedWriter, ReadOnlyPerson person) throws IOException {
        if (!person.getEmail().toString().equals("~")) {
            bufferedWriter.write(vcf.getEmail() + ":" + person.getEmail());
            bufferedWriter.newLine();
        }
    }

```
###### \java\seedu\address\storage\ExportVCardFile.java
``` java
    /**
     * write a person's phone to VCard file.
     */
    private void writeVCardPhone(BufferedWriter bufferedWriter, ReadOnlyPerson person) throws IOException {
        if (!person.getPhone().toString().equals("~")) {
            bufferedWriter.write(vcf.getPhoneFormat() + ":" + person.getPhone());
            bufferedWriter.newLine();
        }
    }

```
###### \java\seedu\address\storage\ExportVCardFile.java
``` java
    /**
     * write a person's tag/tags to VCard file.
     */
    private void writeVCardTag(BufferedWriter bufferedWriter, ReadOnlyPerson person) throws IOException {
        if (!person.getTags().isEmpty()) {
            String tag = person.getTags().toString();
            if (tag.contains("[")) {
                tag = tag.replaceAll("[\\[\\]]", "");
            }
            bufferedWriter.write(vcf.getLabel() + ":" + tag);
            bufferedWriter.newLine();
        }
    }

```
###### \java\seedu\address\storage\ExportVCardFile.java
``` java
    /**
     * write a person's birthday to VCard file.
     */
    private void writeVCardBirthday(BufferedWriter bufferedWriter, ReadOnlyPerson person) throws IOException {
        if (!person.getBirthday().toString().equals("~")) {
            String birthday = person.getBirthday().toString();
            String[] birthdayArray = birthday.split("-");
            birthday = birthdayArray[INDEX_TWO] + "-" + birthdayArray[INDEX_ONE] + "-" + birthdayArray[INDEX_ZERO];
            bufferedWriter.write(vcf.getBirthday() + ":" + birthday);
            bufferedWriter.newLine();
        }
    }

```
###### \java\seedu\address\storage\ExportVCardFile.java
``` java
    /**
     * write a person's address to VCard file.
     */
    private void writeVCardAddress(BufferedWriter bufferedWriter, ReadOnlyPerson person) throws IOException {
        if (!person.getAddress().toString().equals("~")) {
            String address = person.getAddress().toString();
            for (int count = 0; count < 4; count++) {
                if (address.contains(",")) {
                    address = address.replace(",", ";");
                } else {
                    address = address.concat(";");
                }
            }
            bufferedWriter.write(vcf.getAddressFormat1() + ":;;" + address);
            bufferedWriter.newLine();
        }
    }

```
###### \java\seedu\address\storage\ExportVCardFile.java
``` java
    /**
     * write a person's organisation to VCard file.
     */
    private void writeVCardOrganisation(BufferedWriter bufferedWriter, ReadOnlyPerson person) throws IOException {
        if (!person.getOrganisation().toString().equals("~")) {
            String org = person.getOrganisation().toString();
            bufferedWriter.write(vcf.getOrganization() + ":" + org);
            bufferedWriter.newLine();
        }
    }

```
###### \java\seedu\address\storage\ExportVCardFile.java
``` java
    /**
     * write a person's reamark to VCard file.
     */
    private void writeVCardRemark(BufferedWriter bufferedWriter, ReadOnlyPerson person) throws IOException {
        if (!person.getRemark().toString().equals("~")) {
            String remark = person.getRemark().toString();
            bufferedWriter.write(vcf.getNotes() + ":" + remark);
            bufferedWriter.newLine();
        }

    }
}
```
###### \java\seedu\address\storage\ImportVCardFile.java
``` java
    /**
     * Read vCard file from the directory. Check format in file.
     * @throws IOException if not able to read from file
     */

    public ArrayList<Person> getPersonFromFile() throws IOException {

        File file = new File(fileLocation.toString());
        if (file.length() == EMPTY_SIZE) {
            throw new EmptyFileException();
        }
        BufferedReader br = new BufferedReader(new FileReader(fileLocation.toString()));
        String string = br.readLine();
        if (!string.equals(vcf.getBegin())) {
            throw new IOException();
        }

        for (String str : Files.readAllLines(fileLocation, Charset.defaultCharset())) {
            try {
                sendRequest(str);
            } catch (WrongFormatInFileException e) {
                throw new IOException();
            }
        }
        if (!checkEnd) {
            throw new IOException();
        }
        return person;
    }

```
###### \java\seedu\address\storage\ImportVCardFile.java
``` java
    /**
     * Check format in file for each line.
     * return a person
     */
    private void sendRequest(String line) throws WrongFormatInFileException {

        if (line.contains(vcf.getBegin())) {

            if (!checkEnd) {
                throw new WrongFormatInFileException();
            } else {
                vCard = new VCard();
                name = "";
            }
            checkEnd = false;
            checkBegin = true;
        } else if (line.contains(vcf.getEnd())) {
            endSection();
        } else {
            vCardFilePart(line);
        }
    }

```
###### \java\seedu\address\storage\ImportVCardFile.java
``` java
    /**
     * check the format of the different attributes of VCard object.
     */
    private void vCardFilePart(String line) {
        String[] contactArray = line.split(":");
        if (contactArray.length == INDEX_TWO) {
            if ((line.startsWith(vcf.getPhoneFormat2()) || line.startsWith(vcf.getPhoneFormat()))) {
                phoneSection(contactArray[INDEX_ONE].trim());
            }
            if (line.startsWith(vcf.getEmail())) {
                vCard.setEmail(contactArray[INDEX_ONE].trim());
            }
            if (line.startsWith(vcf.getFullName())) {
                vCard.setName(contactArray[INDEX_ONE].trim());
            }
            if (line.startsWith(vcf.getName())) {
                nameSection(contactArray[INDEX_ONE].trim());
            }
            if (line.startsWith(vcf.getAddressFormat1()) || line.startsWith(vcf.getAddressFormat2())) {
                addressSection(contactArray[INDEX_ONE].trim());
            }
            if (line.startsWith(vcf.getBirthday())) {
                birthdaySection(contactArray[INDEX_ONE].trim());
            }
            if (line.startsWith(vcf.getLabel())) {
                tagSection(contactArray[INDEX_ONE].trim());
            }
            if (line.startsWith(vcf.getOrganization())) {
                vCard.setOrganisation(contactArray[INDEX_ONE].trim());
            }
            if (line.startsWith(vcf.getNotes())) {
                vCard.setRemark(contactArray[INDEX_ONE].trim());
            }
        }
    }

```
###### \java\seedu\address\storage\ImportVCardFile.java
``` java
    /**
     * change format of VCard name to OneBook phone format
     */
    private void nameSection(String contactArray) {
        String[] nameArray = contactArray.split(";");
        for (int i = nameArray.length - 1; i >= 0; i--) {
            if (!nameArray[i].equals("")) {
                name = name.concat(nameArray[i] + " ");
            }
        }
    }


```
###### \java\seedu\address\storage\ImportVCardFile.java
``` java
    /**
     * change format of VCard phone to OneBook phone format
     */
    private void phoneSection(String phone) {
        phone = phone.replaceAll("[^\\p{Graph}]", " ");
        if (vCard.getPhone() == null) {
            vCard.setPhone(phone);
        }
    }

```
###### \java\seedu\address\storage\ImportVCardFile.java
``` java
    /**
     * change format of VCard birthday to OneBook birthday format
     */
    private void birthdaySection(String birthday) {
        String[] array = birthday.split("-");
        if (array.length == BIRTHDAY_SIZE) {
            birthday = array[INDEX_TWO] + "-" + array[INDEX_ONE] + "-" + array[INDEX_ZERO];
        }
        vCard.setBirthday(birthday);
    }

```
###### \java\seedu\address\storage\ImportVCardFile.java
``` java
    /**
     * change format of VCard address to OneBook address format
     */
    private void addressSection(String spiltAddress) {
        String[] array = spiltAddress.split(";");
        String address = "";
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals("")) {
                continue;
            }
            address = address.concat(array[i]);
            if (i != array.length - 1) {
                address = address.concat(",");
            }

        }
        vCard.setAddress(address);
    }

```
###### \java\seedu\address\storage\ImportVCardFile.java
``` java
    /**
     * create a list of tags from VCard file tags.
     */
    private void tagSection (String label) {
        List<String> tagList = new ArrayList<>();
        if (label.contains(",")) {
            tagList.addAll(Arrays.asList(label.split(",")));
        } else {
            tagList.add(label);
        }
        vCard.setTag(tagList);
    }

```
###### \java\seedu\address\storage\ImportVCardFile.java
``` java
    /**
     * add a new person to Arraylist if a person's format is correct and start with begin VCard and
     * end VCard statement.
     */
    private void endSection() throws WrongFormatInFileException {
        try {
            if (!checkBegin) {
                throw new WrongFormatInFileException();
            } else {
                checkEnd = true;
                checkBegin = false;
                if (vCard.getName().equals("")) {
                    vCard.setName(name.trim());
                }
                Set<Tag> tag = new HashSet<>();
                for (String string : vCard.getTag()) {
                    tag.add(new Tag(string));
                }
                person.add(new Person(new Name(vCard.getName()), new Phone(vCard.getPhone()),
                        new Birthday(vCard.getBirthday()), new Email(vCard.getEmail()),
                        new Address(vCard.getAddress()), new Organisation(vCard.getOrganisation()),
                        new Remark((vCard.getRemark())), tag));
            }
        } catch (IllegalValueException e) {
            importAnalysis.setIllegalValue(true);
        }
    }

}
```
