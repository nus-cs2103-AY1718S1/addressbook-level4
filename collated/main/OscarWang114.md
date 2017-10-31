# OscarWang114
###### \java\seedu\address\commons\events\ui\PersonNameClickedEvent.java
``` java
    public Optional<ReadOnlyPerson> getPerson() {
        return target.getOptionalPerson();
    }

    public StringProperty getPersonName() {
        return target.nameProperty();
    }
```
###### \java\seedu\address\commons\function\ThrowingConsumer.java
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
###### \java\seedu\address\logic\commands\AddCommand.java
``` java
    public static final String MESSAGE_USAGE = concatenateCommandWords(COMMAND_WORDS)
            + ": Adds a person to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_DOB + "DATE OF BIRTH] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_DOB + "20 01 1997 "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney";
```
###### \java\seedu\address\logic\commands\AddCommand.java
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

        public AddPersonOptionalFieldDescriptor() {
            this.phone = new Phone();
            this.email = new Email();
            this.address = new Address();
            this.dateofbirth = new DateOfBirth();
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
                    && getDateOfBirth().equals(a.getDateOfBirth());
        }
    }
}
```
###### \java\seedu\address\logic\commands\AddLifeInsuranceCommand.java
``` java
/**
 * Creates an insurance relationship in the address book.
 */
public class AddLifeInsuranceCommand extends UndoableCommand {
    public static final String[] COMMAND_WORDS = {"addli", "ali", "+"};
    public static final String COMMAND_WORD = "addli";

    public static final String MESSAGE_USAGE = concatenateCommandWords(COMMAND_WORDS)
            + ": Creates an insurance relationship. "
            + "Parameters: "
            + PREFIX_OWNER + "OWNER "
            + PREFIX_INSURED + "INSURED "
            + PREFIX_BENEFICIARY + "BENEFICIARY "
            + PREFIX_CONTRACT + "CONTRACT"
            + PREFIX_PREMIUM + "PREMIUM "
            + PREFIX_SIGNING_DATE + "SIGNING_DATE "
            + PREFIX_EXPIRY_DATE + "EXPIRY_DATE\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_OWNER + "OWNER "
            + PREFIX_INSURED + "INSURED "
            + PREFIX_BENEFICIARY + "BENEFICIARY "
            + PREFIX_CONTRACT + "CONTRACT"
            + PREFIX_PREMIUM + "PREMIUM "
            + PREFIX_SIGNING_DATE + "SIGNING_DATE "
            + PREFIX_EXPIRY_DATE + "EXPIRY_DATE";

    public static final String MESSAGE_SUCCESS = "New insurance added: %1$s";
    public static final String MESSAGE_DUPLICATE_INSURANCE = "This insurance already exists in the address book";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final String ownerName;
    private final String insuredName;
    private final String beneficiaryName;
    private final Double premium;
    private final String contractPath;
    private final String signingDate;
    private final String expiryDate;

    private ReadOnlyPerson personForOwner;
    private ReadOnlyPerson personForInsured;
    private ReadOnlyPerson personForBeneficiary;

    /**
     * Creates an AddLifeInsuranceCommand to add the specified {@code ReadOnlyInsurance}
     */
    public AddLifeInsuranceCommand(String ownerName, String insuredName, String beneficiaryName,
                                   Double premium, String contractPath, String signingDate, String expiryDate) {

        this.ownerName = ownerName;
        this.insuredName = insuredName;
        this.beneficiaryName = beneficiaryName;
        this.premium = premium;
        this.contractPath = contractPath;
        this.signingDate = signingDate;
        this.expiryDate = expiryDate;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (!arePersonsAllInList(lastShownList, ownerName, insuredName, beneficiaryName)) {
            throw new CommandException(Messages.MESSAGE_PERSON_NOT_FOUND);
        }

        try {
            LifeInsurance lifeInsuranceToAdd = new LifeInsurance(personForOwner, personForInsured, personForBeneficiary,
                premium, contractPath, signingDate, expiryDate);
            model.updatePerson(personForOwner, new Person(personForOwner, lifeInsuranceToAdd));
            model.updatePerson(personForInsured, new Person(personForInsured, lifeInsuranceToAdd));
            model.updatePerson(personForBeneficiary, new Person(personForBeneficiary, lifeInsuranceToAdd));
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

            return new CommandResult(String.format(MESSAGE_SUCCESS, lifeInsuranceToAdd));
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        } catch (IllegalValueException ive) {
            throw new CommandException(ive.getMessage());
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddLifeInsuranceCommand); // instanceof handles nulls
                //&& toAdd.equals(((AddLifeInsuranceCommand) other).toAdd));
        //TODO: need to compare every nonstatic class member.
    }

    /**
     * Check if all the Person parameters required to create an insurance are inside the list
     */
    public boolean arePersonsAllInList(List<ReadOnlyPerson> list, String owner, String insured, String beneficiary) {
        boolean ownerFlag = false;
        boolean insuredFlag = false;
        boolean beneficiaryFlag = false;

        for (ReadOnlyPerson person: list) {
            String personFullNameLowerCase = person.getName().toString().toLowerCase();
            if (personFullNameLowerCase.equals(owner.toLowerCase())) {
                ownerFlag = true;
                this.personForOwner = person;
            }
            if (personFullNameLowerCase.equals(insured.toLowerCase())) {
                insuredFlag = true;
                this.personForInsured = person;
            }
            if (personFullNameLowerCase.equals(beneficiary.toLowerCase())) {
                beneficiaryFlag = true;
                this.personForBeneficiary = person;
            }
            if (ownerFlag && beneficiaryFlag && insuredFlag) {
                return true;
            }
        }
        return false;
    }
}
```
###### \java\seedu\address\logic\parser\AddCommandParser.java
``` java
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap;
        argMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_DOB, PREFIX_TAG);

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

            Phone phone = addPersonOptionalFieldDescriptor.getPhone();
            Email email = addPersonOptionalFieldDescriptor.getEmail();
            Address address = addPersonOptionalFieldDescriptor.getAddress();
            DateOfBirth dob = addPersonOptionalFieldDescriptor.getDateOfBirth();

            ReadOnlyPerson person = new Person(name, phone, email, address, dob, tagList);

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
###### \java\seedu\address\logic\parser\AddLifeInsuranceCommandParser.java
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
                args, PREFIX_OWNER, PREFIX_INSURED, PREFIX_BENEFICIARY, PREFIX_PREMIUM,
                PREFIX_CONTRACT, PREFIX_SIGNING_DATE, PREFIX_EXPIRY_DATE);

        if (!arePrefixesPresent(argMultimap, PREFIX_OWNER, PREFIX_INSURED, PREFIX_BENEFICIARY,
                PREFIX_PREMIUM, PREFIX_CONTRACT, PREFIX_SIGNING_DATE, PREFIX_EXPIRY_DATE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddLifeInsuranceCommand.MESSAGE_USAGE));
        }

        try {
            String owner = ParserUtil.parseNameForInsurance(argMultimap.getValue(PREFIX_OWNER)).get();
            String insured = ParserUtil.parseNameForInsurance(argMultimap.getValue(PREFIX_INSURED)).get();
            String beneficiary = ParserUtil.parseNameForInsurance(argMultimap.getValue(PREFIX_BENEFICIARY)).get();
            Double premium = ParserUtil.parsePremium(argMultimap.getValue(PREFIX_PREMIUM)).get();
            String contract = ParserUtil.parseContract(argMultimap.getValue(PREFIX_CONTRACT)).get();
            String signingDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_SIGNING_DATE)).get();
            String expiryDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_EXPIRY_DATE)).get();

            return new AddLifeInsuranceCommand(owner, insured, beneficiary, premium, contract, signingDate, expiryDate);
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
###### \java\seedu\address\model\AddressBook.java
``` java
    private final UniqueLifeInsuranceMap lifeInsuranceMap;
```
###### \java\seedu\address\model\AddressBook.java
``` java
        lifeInsuranceMap = new UniqueLifeInsuranceMap();
```
###### \java\seedu\address\model\AddressBook.java
``` java
    public void setLifeInsurances(Map<UUID, ReadOnlyInsurance> insurances) throws DuplicateInsuranceException {
        this.lifeInsuranceMap.setInsurances(insurances);
    }
```
###### \java\seedu\address\model\AddressBook.java
``` java
        try {
            setLifeInsurances(newData.getLifeInsuranceMap());
        } catch (DuplicateInsuranceException e) {
            assert false : "AddressBooks should not have duplicate insurances";
        }
        syncMasterLifeInsuranceMapWith(persons);

        try {
            syncMasterPersonListWith(lifeInsuranceMap);
        } catch (InsuranceNotFoundException e) {
            assert false : "AddressBooks should not contain id that doesn't match to an insurance";
        }
```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Ensures that every insurance in the master map:
     *  - links to its owner, insured, and beneficiary {@code Person} if they exist in master person list respectively
     */
    public void syncMasterLifeInsuranceMapWith(UniquePersonList persons) {
        lifeInsuranceMap.forEach((id, insurance) -> {
            String owner = insurance.getOwner().getName();
            String insured = insurance.getInsured().getName();
            String beneficiary = insurance.getBeneficiary().getName();
            persons.forEach(person -> {
                if (person.getName().fullName.equals(owner)) {
                    insurance.setOwner(person);
                }
                if (person.getName().fullName.equals(insured)) {
                    insurance.setInsured(person);
                }
                if (person.getName().fullName.equals(beneficiary)) {
                    insurance.setBeneficiary(person);
                }
            });
        });
    }

    /**
     * Ensures that every person in the master list:
     *  - contains the correct life insurance corresponding to its id from the master map
     */
    public void syncMasterPersonListWith(UniqueLifeInsuranceMap lifeInsuranceMap) throws InsuranceNotFoundException {
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
###### \java\seedu\address\model\AddressBook.java
``` java
    @Override
    public Map<UUID, ReadOnlyInsurance> getLifeInsuranceMap() {
        return lifeInsuranceMap.asMap();
    }
```
###### \java\seedu\address\model\insurance\exceptions\DuplicateInsuranceException.java
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
###### \java\seedu\address\model\insurance\exceptions\InsuranceNotFoundException.java
``` java
/**
 * Signals that the operation is unable to find the specified insurance.
 */
public class InsuranceNotFoundException extends Exception {}
```
###### \java\seedu\address\model\insurance\InsurancePerson.java
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
###### \java\seedu\address\model\insurance\LifeInsurance.java
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
###### \java\seedu\address\model\insurance\ReadOnlyInsurance.java
``` java
/**
 * Represents an insurance.
 */
public interface ReadOnlyInsurance {

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
###### \java\seedu\address\model\insurance\UniqueLifeInsuranceList.java
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
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<ReadOnlyInsurance> asObservableList() {
        return FXCollections.unmodifiableObservableList(mappedList);
    }

    @Override
    public Iterator<LifeInsurance> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueLifeInsuranceList // instanceof handles nulls
                && this.internalList.equals(((UniqueLifeInsuranceList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
```
###### \java\seedu\address\model\insurance\UniqueLifeInsuranceMap.java
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

    private final HashMap<UUID, LifeInsurance> internalMap = new HashMap<>();

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
    }

    public void setInsurances(Map<UUID, ? extends ReadOnlyInsurance> insurances) throws DuplicateInsuranceException {
        final UniqueLifeInsuranceMap replacement = new UniqueLifeInsuranceMap();
        for (final Map.Entry<UUID, ? extends ReadOnlyInsurance> entry : insurances.entrySet()) {
            replacement.put(entry.getKey(), entry.getValue());
        }
        setInsurances(replacement);
    }

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
}
```
###### \java\seedu\address\model\person\Address.java
``` java
    /**
     * Initialize a Address object with value of empty String. This can ONLY be used in the default field of
     * {@code AddPersonOptionalFieldDescriptor}
     */
    public Address() {
        this.value = "";
    }
```
###### \java\seedu\address\model\person\Email.java
``` java
    /**
     * Initialize a Email object with value of empty String. This can ONLY be used in the default field of
     * {@code AddPersonOptionalFieldDescriptor}
     */
    public Email() {
        this.value = "";
    }
```
###### \java\seedu\address\model\person\Person.java
``` java
    private ObjectProperty<List<UUID>> lifeInsuranceIds;
    private ObjectProperty<UniqueLifeInsuranceList> lifeInsurances;
```
###### \java\seedu\address\model\person\Person.java
``` java
        this.lifeInsuranceIds = new SimpleObjectProperty<>(lifeInsuranceIds);
        this.lifeInsurances = new SimpleObjectProperty<>(new UniqueLifeInsuranceList());
```
###### \java\seedu\address\model\person\Person.java
``` java
        this.lifeInsuranceIds = new SimpleObjectProperty<>(new ArrayList<UUID>());
        this.lifeInsurances = new SimpleObjectProperty<>(new UniqueLifeInsuranceList());
```
###### \java\seedu\address\model\person\Person.java
``` java
        if (source.getLifeInsuranceIds() != null) {
            this.lifeInsuranceIds = new SimpleObjectProperty<>(source.getLifeInsuranceIds());
        }
        if (source.getLifeInsurances() != null) {
            this.lifeInsurances = new SimpleObjectProperty<>(source.getLifeInsurances());
        }
```
###### \java\seedu\address\model\person\Person.java
``` java
        if (source.getLifeInsuranceIds() != null) {
            this.lifeInsuranceIds = new SimpleObjectProperty<>(source.getLifeInsuranceIds());
        }
        addLifeInsurances(lifeInsurance);
```
###### \java\seedu\address\model\person\Person.java
``` java
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
###### \java\seedu\address\model\person\Phone.java
``` java
    /**
     * Initialize a Phone object with value of empty String. This can ONLY be used in the default field of
     * {@code AddPersonOptionalFieldDescriptor}
     */
    public Phone() {
        this.value = "";
    }
```
###### \java\seedu\address\model\person\ReadOnlyPerson.java
``` java
    ObjectProperty<List<UUID>> lifeInsuranceIdProperty();
    List<UUID> getLifeInsuranceIds();
    ObjectProperty<UniqueLifeInsuranceList> lifeInsuranceProperty();
    UniqueLifeInsuranceList getLifeInsurances();
```
###### \java\seedu\address\model\ReadOnlyAddressBook.java
``` java
    /**
     * Returns an unmodifiable view of the life insurances map.
     * This map will not contain any duplicate insurances.
     */
    Map<UUID, ReadOnlyInsurance> getLifeInsuranceMap();
```
###### \java\seedu\address\storage\XmlAdaptedLifeInsurance.java
``` java
/**
 * JAXB-friendly version of the LifeInsurance.
 */
public class XmlAdaptedLifeInsurance {
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
        return new LifeInsurance(this.owner, this.insured, this.beneficiary, this.premium,
                this.contractPath, this.signingDate, this.expiryDate);
    }
}
```
###### \java\seedu\address\storage\XmlAdaptedPerson.java
``` java
    @XmlElement(name = "lifeInsuranceId")
    private List<String> lifeInsuranceIds = new ArrayList<>();
```
###### \java\seedu\address\storage\XmlAdaptedPerson.java
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
        final Set<Tag> tags = new HashSet<>(personTags);
        return new Person(name, phone, email, address, dob, tags, personLifeInsuranceIds);
    }
```
###### \java\seedu\address\storage\XmlSerializableAddressBook.java
``` java
    @XmlElement(name = "lifeInsuranceMap")
    private Map<String, XmlAdaptedLifeInsurance> lifeInsuranceMap;
```
###### \java\seedu\address\storage\XmlSerializableAddressBook.java
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
###### \java\seedu\address\ui\InsuranceListPanel.java
``` java
/**
 * The Insurance Panel of the App.
 */
public class InsuranceListPanel extends UiPart<Region> {

    private static final String FXML = "InsuranceListPanel.fxml";

    @FXML
    private ListView<InsuranceProfile> insuranceListView;

    public InsuranceListPanel() {
        super(FXML);
        loadDefaultPage();
        registerAsAnEventHandler(this);
    }

    public InsuranceListPanel(ReadOnlyPerson person) {
        super(FXML);
        setConnections(person.getLifeInsurances().asObservableList());
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
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code InsuranceProfile}.
     */
    class InsuranceListViewCell extends ListCell<InsuranceProfile> {
        @Override
        protected void updateItem(InsuranceProfile insurance, boolean empty) {
            super.updateItem(insurance, empty);

            if (empty || insurance == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(insurance.getRoot());
            }
        }
    }
}
```
###### \java\seedu\address\ui\InsuranceProfile.java
``` java
/**
 * The Profile Panel of the App.
 */
public class InsuranceProfile extends UiPart<Region> {

    private static final String FXML = "InsuranceProfile.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

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
    private Label contractPath;
    @FXML
    private Label signingDate;
    @FXML
    private Label expiryDate;

    public InsuranceProfile(ReadOnlyInsurance insurance, int displayIndex) {
        super(FXML);
        this.insurance = insurance;
        index.setText(displayIndex + ".");
        owner.setOnMouseClicked(e -> raise(new PersonNameClickedEvent(insurance.getOwner())));
        insured.setOnMouseClicked(e -> raise(new PersonNameClickedEvent(insurance.getInsured())));
        beneficiary.setOnMouseClicked(e ->
                raise(new PersonNameClickedEvent(insurance.getBeneficiary())));

        bindListeners(insurance);
        registerAsAnEventHandler(this);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code ReadOnlyInsurance} properties
     * so that they will be notified of any changes.
     * @param insurance
     */
    private void bindListeners(ReadOnlyInsurance insurance) {
        owner.textProperty().bind(Bindings.convert(insurance.getOwner().nameProperty()));
        insured.textProperty().bind(Bindings.convert(insurance.getInsured().nameProperty()));
        beneficiary.textProperty().bind(Bindings.convert(insurance.getBeneficiary().nameProperty()));
        premium.textProperty().bind(Bindings.convert(insurance.premiumStringProperty()));
        contractPath.textProperty().bind(Bindings.convert(insurance.contractPathProperty()));
        signingDate.textProperty().bind(Bindings.convert(insurance.signingDateStringProperty()));
        expiryDate.textProperty().bind(Bindings.convert(insurance.expiryDateStringProperty()));
    }
}
```
###### \java\seedu\address\ui\ProfilePanel.java
``` java
    private void loadPersonPage(StringProperty name) {
        this.name.textProperty().bind(Bindings.convert(name));
        this.address.textProperty().unbind();
        this.address.setText(PERSON_DOES_NOT_EXIST_MESSAGE);
    }
```
###### \java\seedu\address\ui\ProfilePanel.java
``` java
    @Subscribe
    private void handlePersonNameClickedEvent(PersonNameClickedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        ReadOnlyPerson person = event.getPerson().orElse(null);
        if (person == null) {
            loadPersonPage(event.getPersonName());
        } else {
            loadPersonPage(event.getPerson().get());
        }
        raise(new SwitchPanelRequestEvent());
    }
```
###### \resources\view\InsuranceListPanel.fxml
``` fxml
<VBox xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
    <ListView fx:id="insuranceListView" VBox.vgrow="ALWAYS" />
</VBox>
```
