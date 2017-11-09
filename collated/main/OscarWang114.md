# OscarWang114
###### /java/seedu/address/commons/events/ui/PersonNameClickedEvent.java
``` java
    public Optional<ReadOnlyPerson> getPerson() {
        return target.getOptionalPerson();
    }

    public StringProperty getPersonName() {
        return target.nameProperty();
    }
```
###### /java/seedu/address/commons/function/ThrowingConsumer.java
``` java
/**
 * A Consumer that allows lambda expressions to throw exception.
 * @param <T>
 */
@FunctionalInterface
public interface ThrowingConsumer<T> extends Consumer<T> {

    @Override
    default void accept(final T elem) {
        try {
            acceptThrows(elem);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    void acceptThrows(T elem) throws Exception;

}
```
###### /java/seedu/address/logic/commands/AddCommand.java
``` java
    public static final String MESSAGE_USAGE = concatenateCommandWords(COMMAND_WORDS)
            + ": Adds a person to Lisa. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_DOB + "DATE OF BIRTH] "
            + "[" + PREFIX_GENDER + "GENDER] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_DOB + "20 01 1997 "
            + PREFIX_GENDER + "Male "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney";
```
###### /java/seedu/address/logic/commands/AddCommand.java
``` java
    /**
     * Stores the optional details to add the person with. By default each field is an object
     * with value of empty String.
     */
    public static class AddPersonOptionalFieldDescriptor {
        private Phone phone;
        private Email email;
        private Address address;
        private DateOfBirth dateofbirth;
        private Gender gender;

        public AddPersonOptionalFieldDescriptor() {
            this.phone = new Phone();
            this.email = new Email();
            this.address = new Address();
            this.dateofbirth = new DateOfBirth();
            this.gender = new Gender();
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Phone getPhone() {
            return phone;
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Email getEmail() {
            return email;
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Address getAddress() {
            return address;
        }

        public void setDateOfBirth(DateOfBirth dateofbirth) {
            this.dateofbirth = dateofbirth;
        }

        public DateOfBirth getDateOfBirth() {
            return dateofbirth;
        }

        public void setGender(Gender gender) {
            this.gender = gender;
        }

        public Gender getGender() {
            return gender;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof AddCommand.AddPersonOptionalFieldDescriptor)) {
                return false;
            }

            // state check
            AddCommand.AddPersonOptionalFieldDescriptor a =
                    (AddCommand.AddPersonOptionalFieldDescriptor) other;

            return getPhone().equals(a.getPhone())
                    && getEmail().equals(a.getEmail())
                    && getAddress().equals(a.getAddress())
                    && getDateOfBirth().equals(a.getDateOfBirth())
                    && getGender().equals(a.getGender());
        }
    }
}
```
###### /java/seedu/address/logic/commands/AddLifeInsuranceCommand.java
``` java
/**
 * Creates an insurance in Lisa
 */
public class AddLifeInsuranceCommand extends UndoableCommand {
    public static final String[] COMMAND_WORDS = {"addli", "ali", "+li"};
    public static final String COMMAND_WORD = "addli";

    public static final String MESSAGE_USAGE = concatenateCommandWords(COMMAND_WORDS)
            + ": Adds an insurance to Lisa. "
            + "Parameters: "
            + PREFIX_NAME + "INSURANCE_NAME "
            + PREFIX_OWNER + "OWNER "
            + PREFIX_INSURED + "INSURED "
            + PREFIX_BENEFICIARY + "BENEFICIARY "
            + PREFIX_CONTRACT + "CONTRACT "
            + PREFIX_PREMIUM + "PREMIUM "
            + PREFIX_SIGNING_DATE + "SIGNING_DATE "
            + PREFIX_EXPIRY_DATE + "EXPIRY_DATE\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Life Insurance "
            + PREFIX_OWNER + "Alex Yeoh "
            + PREFIX_INSURED + "John Doe "
            + PREFIX_BENEFICIARY + "Mary Jane "
            + PREFIX_CONTRACT + "normal_plan.pdf "
            + PREFIX_PREMIUM + "500 "
            + PREFIX_SIGNING_DATE + "01 11 2017 "
            + PREFIX_EXPIRY_DATE + "01 11 2018 ";

    public static final String MESSAGE_SUCCESS = "New insurance added: %1$s";

    private final LifeInsurance lifeInsurance;

    /**
     * Creates an AddLifeInsuranceCommand to add the specified Insurance
     */
    public AddLifeInsuranceCommand(ReadOnlyInsurance toAdd) {
        lifeInsurance = new LifeInsurance(toAdd);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        List<ReadOnlyPerson> personList = model.getFilteredPersonList();
        isAnyPersonInList(personList, lifeInsurance);
        model.addInsurance(lifeInsurance);

        return new CommandResult(String.format(MESSAGE_SUCCESS, lifeInsurance));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddLifeInsuranceCommand); // instanceof handles nulls
                //&& toAdd.equals(((AddLifeInsuranceCommand) other).toAdd));
        //TODO: need to compare every nonstatic class member.
    }

```
###### /java/seedu/address/logic/Logic.java
``` java
    /** Returns an unmodifiable view of the list of insurances */
    ObservableList<ReadOnlyInsurance> getFilteredInsuranceList();
```
###### /java/seedu/address/logic/LogicManager.java
``` java
    @Override
    public ObservableList<ReadOnlyInsurance> getFilteredInsuranceList() {
        return model.getFilteredInsuranceList();
    }
    //

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }
}
```
###### /java/seedu/address/logic/parser/AddCommandParser.java
``` java
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap;
        argMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_DOB, PREFIX_GENDER, PREFIX_TAG);

        if (!isNamePrefixPresent(argMultimap, PREFIX_NAME)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        AddPersonOptionalFieldDescriptor addPersonOptionalFieldDescriptor =
                new AddPersonOptionalFieldDescriptor();

        try {
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).get();
            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

            ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE))
                .ifPresent(addPersonOptionalFieldDescriptor::setPhone);
            ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL))
                .ifPresent(addPersonOptionalFieldDescriptor::setEmail);
            ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS))
                .ifPresent(addPersonOptionalFieldDescriptor::setAddress);
            ParserUtil.parseDateOfBirth(argMultimap.getValue(PREFIX_DOB))
                    .ifPresent(addPersonOptionalFieldDescriptor::setDateOfBirth);
            ParserUtil.parseGender(argMultimap.getValue(PREFIX_GENDER))
                    .ifPresent(addPersonOptionalFieldDescriptor::setGender);

            Phone phone = addPersonOptionalFieldDescriptor.getPhone();
            Email email = addPersonOptionalFieldDescriptor.getEmail();
            Address address = addPersonOptionalFieldDescriptor.getAddress();
            DateOfBirth dob = addPersonOptionalFieldDescriptor.getDateOfBirth();
            Gender gender = addPersonOptionalFieldDescriptor.getGender();

            ReadOnlyPerson person = new Person(name, phone, email, address, dob, gender, tagList);

            return new AddCommand(person);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if the name prefixes does not contain empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean isNamePrefixPresent(ArgumentMultimap argumentMultimap, Prefix namePrefix) {
        return argumentMultimap.getValue(namePrefix).isPresent();
    }
```
###### /java/seedu/address/logic/parser/AddLifeInsuranceCommandParser.java
``` java
/**
 * Parses input arguments and creates a new AddLifeInsuranceCommand object
 */
public class AddLifeInsuranceCommandParser implements Parser<AddLifeInsuranceCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddLifeInsuranceCommand
     * and returns an AddLifeInsuranceCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddLifeInsuranceCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap;
        argMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_NAME, PREFIX_OWNER, PREFIX_INSURED, PREFIX_BENEFICIARY, PREFIX_PREMIUM,
                PREFIX_CONTRACT, PREFIX_SIGNING_DATE, PREFIX_EXPIRY_DATE);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_OWNER, PREFIX_INSURED, PREFIX_BENEFICIARY,
                PREFIX_PREMIUM, PREFIX_CONTRACT, PREFIX_SIGNING_DATE, PREFIX_EXPIRY_DATE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddLifeInsuranceCommand.MESSAGE_USAGE));
        }

        try {
            String insuranceName = ParserUtil.parseNameForInsurance(argMultimap.getValue(PREFIX_NAME)).get();
            String owner = ParserUtil.parseNameForInsurance(argMultimap.getValue(PREFIX_OWNER)).get();
            String insured = ParserUtil.parseNameForInsurance(argMultimap.getValue(PREFIX_INSURED)).get();
            String beneficiary = ParserUtil.parseNameForInsurance(argMultimap.getValue(PREFIX_BENEFICIARY)).get();
            Double premium = ParserUtil.parsePremium(argMultimap.getValue(PREFIX_PREMIUM)).get();
            String contract = ParserUtil.parseContract(argMultimap.getValue(PREFIX_CONTRACT)).get();
            LocalDate signingDate = new DateParser().parse(
                    ParserUtil.parseContract(argMultimap.getValue(PREFIX_SIGNING_DATE)).get()
            );
            LocalDate expiryDate = new DateParser().parse(
                    ParserUtil.parseContract(argMultimap.getValue(PREFIX_EXPIRY_DATE)).get()
            );

            ReadOnlyInsurance lifeInsurance = new LifeInsurance(insuranceName, owner, insured, beneficiary, premium,
                    contract, signingDate, expiryDate);

            return new AddLifeInsuranceCommand(lifeInsurance);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if the name prefixes does not contain empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
```
###### /java/seedu/address/logic/parser/ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> owner} into an {@code Optional<String>} if {@code owner} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<String> parseNameForInsurance(Optional<String> name) throws IllegalValueException {
        requireNonNull(name);
        return name.isPresent() ? Optional.of(name.get()) : Optional.empty();
    }
    /**
     * Parses a {@code Optional<String> premium} into an {@code Optional<Double>} if {@code premium} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Double> parsePremium(Optional<String> premium) throws IllegalValueException {
        requireNonNull(premium);
        return premium.isPresent() ? Optional.of(Double.parseDouble(premium.get())) : Optional.empty();
    }
    /**
     * Parses a {@code Optional<String> contract} into an {@code Optional<String>} if {@code contract} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<String> parseContract(Optional<String> contract) throws IllegalValueException {
        requireNonNull(contract);
        return contract.isPresent() ? Optional.of(contract.get()) : Optional.empty();
    }

```
###### /java/seedu/address/model/AddressBook.java
``` java
    private final UniqueLifeInsuranceMap lifeInsuranceMap;
```
###### /java/seedu/address/model/AddressBook.java
``` java
        lifeInsuranceMap = new UniqueLifeInsuranceMap();
```
###### /java/seedu/address/model/AddressBook.java
``` java
    public void setLifeInsurances(Map<UUID, ReadOnlyInsurance> insurances) throws DuplicateInsuranceException {
        this.lifeInsuranceMap.setInsurances(insurances);
    }
```
###### /java/seedu/address/model/AddressBook.java
``` java
    /**
     *Adds an insurance to the address book.
     */
    public void addInsurance(ReadOnlyInsurance i) {
        UUID id = UUID.randomUUID();
        LifeInsurance lifeInsurance = new LifeInsurance(i);
        try {
            lifeInsuranceMap.put(id, lifeInsurance);
        } catch (DuplicateInsuranceException e) {
            assert false : "AddressBooks should not have duplicate insurances";
        }
        syncWithUpdate();
    }
```
###### /java/seedu/address/model/AddressBook.java
``` java
    /**
     * Ensures that every insurance in the master map:
     *  - links to its owner, insured, and beneficiary {@code Person} if they exist in master person list respectively
     */
    public void syncMasterLifeInsuranceMap() {
        lifeInsuranceMap.forEach((id, insurance) -> {
            insurance.resetAllInsurancePerson();
            String owner = insurance.getOwner().getName();
            String insured = insurance.getInsured().getName();
            String beneficiary = insurance.getBeneficiary().getName();
            persons.forEach(person -> {
                if (person.getName().fullName.equals(owner)) {
                    insurance.setOwner(person);
                    person.addLifeInsuranceIds(id);
                }
                if (person.getName().fullName.equals(insured)) {
                    insurance.setInsured(person);
                    person.addLifeInsuranceIds(id);
                }
                if (person.getName().fullName.equals(beneficiary)) {
                    insurance.setBeneficiary(person);
                    person.addLifeInsuranceIds(id);
                }
            });
        });
        lifeInsuranceMap.syncMappedListWithInternalMap();
    }

    /**
     * Ensures that every person in the master list:
     *  - contains the correct life insurance corresponding to its id from the master map
     */
    public void syncMasterPersonList() throws InsuranceNotFoundException {
        persons.forEach((ThrowingConsumer<Person>) person -> {
            List<UUID> idList = person.getLifeInsuranceIds();
            if (!idList.isEmpty()) {
                person.clearLifeInsurances();
                idList.forEach((ThrowingConsumer<UUID>) id -> {
                    LifeInsurance lf = lifeInsuranceMap.get(id);
                    person.addLifeInsurances(lf);
                });
            }
        });
    }
```
###### /java/seedu/address/model/AddressBook.java
``` java
    @Override
    public Map<UUID, ReadOnlyInsurance> getLifeInsuranceMap() {
        return lifeInsuranceMap.asMap();
    }
```
###### /java/seedu/address/model/insurance/exceptions/DuplicateInsuranceException.java
``` java
/**
 * Signals that the operation will result in duplicate Insurance objects.
 */
public class DuplicateInsuranceException extends DuplicateDataException {
    public DuplicateInsuranceException() {
        super("Operation would result in duplicate insurances");
    }
}
```
###### /java/seedu/address/model/insurance/exceptions/InsuranceNotFoundException.java
``` java
/**
 * Signals that the operation is unable to find the specified insurance.
 */
public class InsuranceNotFoundException extends Exception {}
```
###### /java/seedu/address/model/insurance/InsurancePerson.java
``` java
/**
 * Represents a person and his/her name in an insurance in LISA.
 */
public class InsurancePerson {

    //TODO: Change from String to Name
    private StringProperty name;
    private Optional<ReadOnlyPerson> person;

    public InsurancePerson(String name) {
        this.person = Optional.empty();
        this.name = new SimpleStringProperty(name);
    }

    public InsurancePerson(ReadOnlyPerson person) {
        this.person = Optional.of(person);
        this.name = new SimpleStringProperty(person.getName().fullName);
    }

    public void setName(String name) {
        this.name = new SimpleStringProperty(name);
    }
    public String getName() {
        return name.getValue();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setPerson(ReadOnlyPerson person) {
        this.person = Optional.of(person);
    }
    //TODO: Fix
    public Optional<ReadOnlyPerson> getOptionalPerson() {
        return person;
    }
}
```
###### /java/seedu/address/model/insurance/LifeInsurance.java
``` java
/**
 * Represents a life insurance in LISA.
 * Guarantees: details are present and not null.
 */
public class LifeInsurance implements ReadOnlyInsurance {

    /**
     * Represents the three person-roles inside an insurance in LISA.
     */
    enum Roles { OWNER, INSURED, BENEFICIARY }
    private ObjectProperty<UUID> id;
    private EnumMap<Roles, String> roleToPersonNameMap;
    private ObjectProperty<InsurancePerson> owner;
    private ObjectProperty<InsurancePerson> insured;
    private ObjectProperty<InsurancePerson> beneficiary;
    private DoubleProperty premium;
    private StringProperty premiumString;
    private StringProperty contractPath;
    private StringProperty signingDateString;
    private StringProperty expiryDateString;

```
###### /java/seedu/address/model/insurance/ReadOnlyInsurance.java
``` java
/**
 * Represents an insurance.
 */
public interface ReadOnlyInsurance {

    String getInsuranceName();
    StringProperty insuranceNameProperty();
    ObjectProperty<UUID> idProperty();
    String getId();
    EnumMap getRoleToPersonNameMap();
    ObjectProperty<InsurancePerson> ownerProperty();
    InsurancePerson getOwner();
    ObjectProperty<InsurancePerson> insuredProperty();
    InsurancePerson getInsured();
    ObjectProperty<InsurancePerson> beneficiaryProperty();
    InsurancePerson getBeneficiary();
    DoubleProperty premiumProperty();
    Double getPremium();
    StringProperty premiumStringProperty();
    String getPremiumString();
    StringProperty contractPathProperty();
    String getContractPath();
    StringProperty signingDateStringProperty();
    String getSigningDateString();
    StringProperty expiryDateStringProperty();
    String getExpiryDateString();
}
```
###### /java/seedu/address/model/insurance/UniqueLifeInsuranceList.java
``` java
/**
 * A list of insurances that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see LifeInsurance#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueLifeInsuranceList implements Iterable<LifeInsurance> {

    private final ObservableList<LifeInsurance> internalList = FXCollections.observableArrayList();
    // used by asObservableList()
    private final ObservableList<ReadOnlyInsurance> mappedList = EasyBind.map(internalList, (insurance) -> insurance);

    /**
     * Adds an insurance to the list.
     *
     */
    public void add(ReadOnlyInsurance toAdd) {
        requireNonNull(toAdd);
        internalList.add(new LifeInsurance(toAdd));
        sortInsurances();
    }

```
###### /java/seedu/address/model/insurance/UniqueLifeInsuranceMap.java
``` java
/**
 * A map of life insurances that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of map operations.
 *
 * @see LifeInsurance#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueLifeInsuranceMap {

    private final ObservableMap<UUID, LifeInsurance> internalMap = FXCollections.observableHashMap();
    private final ObservableList<ReadOnlyInsurance> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the map contains an equivalent UUID as the given argument.
     */
    public boolean containsKey(UUID toCheck) {
        requireNonNull(toCheck);
        return internalMap.containsKey(toCheck);
    }

    /**
     * Returns true if the map contains an equivalent insurance as the given argument.
     */
    public boolean containsValue(ReadOnlyInsurance toCheck) {
        requireNonNull(toCheck);
        return internalMap.containsValue(toCheck);
    }

    /**
     * Adds an life insurance to the map.
     *
     * @throws DuplicateInsuranceException if the life insurance to add is a duplicate of an
     * existing life insurance in the list.
     */
    public void put(UUID key, ReadOnlyInsurance toPut) throws DuplicateInsuranceException {
        requireNonNull(toPut);
        if (containsValue(toPut)) {
            throw new DuplicateInsuranceException();
        }
        internalMap.put(key, new LifeInsurance(toPut));
    }

    /**
     * Retrieves an life insurance from the map.
     *
     * @throws InsuranceNotFoundException if the life insurance to add is a duplicate of an
     * existing life insurance in the list.
     */
    public LifeInsurance get(UUID key) throws InsuranceNotFoundException {
        requireNonNull(key);
        if (!containsKey(key)) {
            throw new InsuranceNotFoundException();
        }
        return internalMap.get(key);
    }

    public void forEach(BiConsumer<UUID, LifeInsurance> action) {
        internalMap.forEach(action);
    }

    public void setInsurances(UniqueLifeInsuranceMap replacement) {
        this.internalMap.clear();
        this.internalMap.putAll(replacement.internalMap);
        syncMappedListWithInternalMap();
    }

    public void setInsurances(Map<UUID, ? extends ReadOnlyInsurance> insurances) throws DuplicateInsuranceException {
        final UniqueLifeInsuranceMap replacement = new UniqueLifeInsuranceMap();
        for (final Map.Entry<UUID, ? extends ReadOnlyInsurance> entry : insurances.entrySet()) {
            replacement.put(entry.getKey(), entry.getValue());
        }
        setInsurances(replacement);
    }

    /**
     * Ensures that every insurance in the internalList:
     * contains the same exact same collections of insurances as that of the insuranceMap.
     */
    public void syncMappedListWithInternalMap() {
        this.internalList.clear();
        this.internalList.setAll(this.internalMap.values());
    }
```
###### /java/seedu/address/model/insurance/UniqueLifeInsuranceMap.java
``` java
    /**
     * Returns the backing map as an unmodifiable {@code ObservableList}.
     */
    public Map<UUID, ReadOnlyInsurance> asMap() {
        assert CollectionUtil.elementsAreUnique(internalMap.values());
        return Collections.unmodifiableMap(internalMap);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueLifeInsuranceMap// instanceof handles nulls
                && this.internalMap.equals(((UniqueLifeInsuranceMap) other).internalMap));
    }

    @Override
    public int hashCode() {
        return internalMap.hashCode();
    }
```
###### /java/seedu/address/model/Model.java
``` java
    /** Returns an unmodifiable view of the filtered insurance list */
    ObservableList<ReadOnlyInsurance> getFilteredInsuranceList();
```
###### /java/seedu/address/model/person/Address.java
``` java
    /**
     * Initialize a Address object with value of empty String. This can ONLY be used in the default field of
     * {@code AddPersonOptionalFieldDescriptor}
     */
    public Address() {
        this.value = "";
    }
```
###### /java/seedu/address/model/person/Email.java
``` java
    /**
     * Initialize a Email object with value of empty String. This can ONLY be used in the default field of
     * {@code AddPersonOptionalFieldDescriptor}
     */
    public Email() {
        this.value = "";
    }
```
###### /java/seedu/address/model/person/Person.java
``` java
    private ObjectProperty<List<UUID>> lifeInsuranceIds;
    private ObjectProperty<UniqueLifeInsuranceList> lifeInsurances;
```
###### /java/seedu/address/model/person/Person.java
``` java
        this.lifeInsuranceIds = new SimpleObjectProperty<>(lifeInsuranceIds);
        this.lifeInsurances = new SimpleObjectProperty<>(new UniqueLifeInsuranceList());
```
###### /java/seedu/address/model/person/Person.java
``` java
    /**
     * Creates a copy of the given ReadOnlyPerson.
     */
    public Person(ReadOnlyPerson source) {
        this(source.getName(), source.getPhone(), source.getEmail(), source.getAddress(),
                source.getDateOfBirth(), source.getGender(), source.getTags());
        if (source.getLifeInsuranceIds() != null) {
            this.lifeInsuranceIds = new SimpleObjectProperty<>(source.getLifeInsuranceIds());
        }
        if (source.getLifeInsurances() != null) {
            this.lifeInsurances = new SimpleObjectProperty<>(source.getLifeInsurances());
        }

    }
```
###### /java/seedu/address/model/person/Person.java
``` java
    public Person(ReadOnlyPerson source, LifeInsurance lifeInsurance) {
        this(source.getName(), source.getPhone(), source.getEmail(), source.getAddress(),
                source.getDateOfBirth(), source.getGender(), source.getTags());
        if (source.getLifeInsuranceIds() != null) {
            this.lifeInsuranceIds = new SimpleObjectProperty<>(source.getLifeInsuranceIds());
        }
        addLifeInsurances(lifeInsurance);

    }
```
###### /java/seedu/address/model/person/Person.java
``` java

    /**
     * adds an Id
     * @param idToAdd
     */
    public void addLifeInsuranceIds(UUID idToAdd) {
        for (UUID id : lifeInsuranceIds.get()) {
            if (id.equals(idToAdd)) {
                return;
            }
        }
        lifeInsuranceIds.get().add(idToAdd);
    }

    public void clearLifeInsuranceIds() {
        lifeInsuranceIds = new SimpleObjectProperty<>(new ArrayList<UUID>());
    }

    @Override
    public ObjectProperty<List<UUID> > lifeInsuranceIdProperty() {
        return this.lifeInsuranceIds;
    }

    @Override
    public List<UUID> getLifeInsuranceIds() {
        return this.lifeInsuranceIds.get();
    }

    public void addLifeInsurances(ReadOnlyInsurance lifeInsurance) {
        this.lifeInsurances.get().add(lifeInsurance);
    }

    public void clearLifeInsurances() {
        this.lifeInsurances = new SimpleObjectProperty<>(new UniqueLifeInsuranceList());
    }

    @Override
    public ObjectProperty<UniqueLifeInsuranceList> lifeInsuranceProperty() {
        return this.lifeInsurances;
    }

    @Override
    public UniqueLifeInsuranceList getLifeInsurances() {
        return this.lifeInsurances.get();
    }
```
###### /java/seedu/address/model/person/Phone.java
``` java
    /**
     * Initialize a Phone object with value of empty String. This can ONLY be used in the default field of
     * {@code AddPersonOptionalFieldDescriptor}
     */
    public Phone() {
        this.value = "";
    }
```
###### /java/seedu/address/model/person/ReadOnlyPerson.java
``` java
    ObjectProperty<List<UUID>> lifeInsuranceIdProperty();
    List<UUID> getLifeInsuranceIds();
    ObjectProperty<UniqueLifeInsuranceList> lifeInsuranceProperty();
    UniqueLifeInsuranceList getLifeInsurances();
```
###### /java/seedu/address/model/ReadOnlyAddressBook.java
``` java
    /**
     * Returns an unmodifiable view of the life insurances map.
     * This map will not contain any duplicate insurances.
     */
    Map<UUID, ReadOnlyInsurance> getLifeInsuranceMap();
```
###### /java/seedu/address/storage/XmlAdaptedLifeInsurance.java
``` java
/**
 * JAXB-friendly version of the LifeInsurance.
 */
public class XmlAdaptedLifeInsurance {
    @XmlElement(required = true)
    private String insuranceName;
    @XmlElement(required = true)
    private String owner;
    @XmlElement(required = true)
    private String insured;
    @XmlElement(required = true)
    private String beneficiary;
    @XmlElement(required = true)
    private Double premium;
    @XmlElement(required = true)
    private String contractPath;
    @XmlElement(required = true)
    private String signingDate;
    @XmlElement(required = true)
    private String expiryDate;

    /**
     * Constructs an XmlAdaptedLifeInsurance.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedLifeInsurance() {}


    /**
     * Converts a given LifeInsurance into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedLifeInsurance
     */
    public XmlAdaptedLifeInsurance(ReadOnlyInsurance source) {
        insuranceName = source.getInsuranceName();
        owner = source.getOwner().getName();
        insured = source.getInsured().getName();
        beneficiary = source.getBeneficiary().getName();
        premium = source.getPremium();
        contractPath = source.getContractPath();
        signingDate = source.getSigningDateString();
        expiryDate = source.getExpiryDateString();
    }

    /**
     * Converts this jaxb-friendly adapted life insurance object into the model's LifeInsurance object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted life insurance
     */

    public LifeInsurance toModelType() throws IllegalValueException {
        return new LifeInsurance(this.insuranceName, this.owner, this.insured, this.beneficiary, this.premium,
                this.contractPath, this.signingDate, this.expiryDate);
    }
}
```
###### /java/seedu/address/storage/XmlAdaptedPerson.java
``` java
    @XmlElement(name = "lifeInsuranceId")
    private List<String> lifeInsuranceIds = new ArrayList<>();
```
###### /java/seedu/address/storage/XmlAdaptedPerson.java
``` java
    /**
     * Converts this jaxb-friendly adapted person object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }
        final List<UUID> personLifeInsuranceIds = new ArrayList<>();
        for (String lifeInsuranceId: lifeInsuranceIds) {
            personLifeInsuranceIds.add(UUID.fromString(lifeInsuranceId));
        }
        final Name name = new Name(this.name);
        final Phone phone = this.phone.equals("") ? new Phone() : new Phone(this.phone);
        final Email email = this.email.equals("") ? new Email() : new Email(this.email);
        final Address address = this.address.equals("") ? new Address() : new Address(this.address);
        final DateOfBirth dob = this.dob.equals("") ? new DateOfBirth() : new DateOfBirth(this.dob);
        final Gender gender = this.gender.equals("") ? new Gender() : new Gender(this.gender);
        final Set<Tag> tags = new HashSet<>(personTags);
        return new Person(name, phone, email, address, dob, gender, tags, personLifeInsuranceIds);
    }
```
###### /java/seedu/address/storage/XmlSerializableAddressBook.java
``` java
    @XmlElement(name = "lifeInsuranceMap")
    private Map<String, XmlAdaptedLifeInsurance> lifeInsuranceMap;
```
###### /java/seedu/address/storage/XmlSerializableAddressBook.java
``` java
    @Override
    public Map<UUID, ReadOnlyInsurance> getLifeInsuranceMap() {
        final Map<UUID, ReadOnlyInsurance> lifeInsurances = this.lifeInsuranceMap.entrySet().stream()
            .collect(Collectors.<Map.Entry<String, XmlAdaptedLifeInsurance>, UUID, ReadOnlyInsurance>toMap(
                i -> UUID.fromString(i.getKey()), i -> {
                    try {
                        return i.getValue().toModelType();
                    } catch (IllegalValueException e) {
                        e.printStackTrace();
                        //TODO: better error handling
                        return null;
                    }
                }
            ));
        return lifeInsurances;
    }
```
###### /java/seedu/address/ui/InsuranceListPanel.java
``` java
    private static final String FXML = "InsuranceListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private ListView<InsuranceProfile> insuranceListView;

    public InsuranceListPanel() {
        super(FXML);
        loadDefaultPage();
        registerAsAnEventHandler(this);
    }

    public InsuranceListPanel(ObservableList<ReadOnlyInsurance> insuranceList) {
        super(FXML);
        setConnections(insuranceList);
        registerAsAnEventHandler(this);
    }
    /**
     * Load default page with nothing
     */
    private void loadDefaultPage() {

    }

    /**
     * To be called everytime a new person is selected and bind the list of insurance of that person
     * @param insuranceList
     */
    public void setConnections(ObservableList<ReadOnlyInsurance> insuranceList) {
        ObservableList<InsuranceProfile> mappedList = EasyBind.map(
                insuranceList, (insurance) -> new InsuranceProfile(insurance, insuranceList.indexOf(insurance) + 1));
        insuranceListView.setItems(mappedList);
        insuranceListView.setCellFactory(listView -> new InsuranceListPanel.InsuranceListViewCell());
        setEventHandlerForSelectionChangeEvent();

    }
```
###### /java/seedu/address/ui/InsuranceProfile.java
``` java
    private static final String FXML = "InsuranceProfile.fxml";
    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private File insuranceFile;
    private ReadOnlyInsurance insurance;

    @FXML
    private Label index;
    @FXML
    private Label owner;
    @FXML
    private Label insured;
    @FXML
    private Label beneficiary;
    @FXML
    private Label premium;
    @FXML
    private Label insuranceName;

    public InsuranceProfile() {
        super(FXML);
        enableNameToProfileLink(insurance);
        registerAsAnEventHandler(this);

    }
```
###### /java/seedu/address/ui/ProfilePanel.java
``` java
    /**
     * Load person page with only his/her name with person does not exist in Lisa message
     */
    private void loadPersonPage(StringProperty name) {
        unbindListenersAndClearText();
        this.name.textProperty().bind(Bindings.convert(name));
        this.address.setText(PERSON_DOES_NOT_EXIST_IN_LISA_MESSAGE);
    }
```
###### /java/seedu/address/ui/ProfilePanel.java
``` java
    /**
     * Unbind all listeners and reset the text values to {@code null}
     */
    private void unbindListenersAndClearText() {
        name.textProperty().unbind();
        phone.textProperty().unbind();
        address.textProperty().unbind();
        dob.textProperty().unbind();
        gender.textProperty().unbind();
        email.textProperty().unbind();
        name.setText(null);
        phone.setText(null);
        address.setText(null);
        dob.setText(null);
        gender.setText(null);
        email.setText(null);
        insuranceHeader.setText(null);
    }
```
###### /java/seedu/address/ui/ProfilePanel.java
``` java
    @Subscribe
    private void handlePersonNameClickedEvent(PersonNameClickedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        insurance.clear();
        ReadOnlyPerson person = event.getPerson().orElse(null);
        if (person == null) {
            loadPersonPage(event.getPersonName());
        } else {
            loadPersonPage(event.getPerson().get());
        }
        raise(new SwitchToProfilePanelRequestEvent());
    }
```
###### /resources/view/DarkTheme.css
``` css
.list-cell:filled:selected #insuranceCardPane {
    -fx-border-color: #3e7b91;
    -fx-border-width: 1;
}
```
###### /resources/view/DarkTheme.css
``` css
#personListView {
    -fx-background-color: transparent #383838 transparent #383838;
}
```
###### /resources/view/InsuranceListPanel.fxml
``` fxml
<VBox xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
    <ListView fx:id="insuranceListView" VBox.vgrow="ALWAYS" />
</VBox>
```
###### /resources/view/InsuranceProfile.fxml
``` fxml
         <VBox alignment="CENTER_LEFT" GridPane.columnIndex="0">
            <padding>
               <Insets left="15.0" />
            </padding>
            <HBox>
               <children>
                  <Label styleClass="static-insurance-labels" text="Owner: " />
               </children>
            </HBox>
            <HBox>
               <children>
                  <Label styleClass="static-insurance-labels" text="Insured: " />
               </children>
            </HBox>
            <HBox>
               <children>
                  <Label styleClass="static-insurance-labels" text="Beneficiary: " />
               </children>
            </HBox>
            <HBox>
               <children>
                  <Label styleClass="static-insurance-labels" text="Premium: " />
               </children>
            </HBox>
         </VBox>
```
