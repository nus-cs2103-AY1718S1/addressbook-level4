# OscarWang114
###### /java/seedu/address/commons/events/ui/PersonNameClickedEvent.java
``` java
    public Optional<ReadOnlyPerson> getPerson() {
        return target.getOptionalPerson();
    }

    public ObjectProperty<Name> getPersonName() {
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
        private DateOfBirth dateOfBirth;
        private Gender gender;

        public AddPersonOptionalFieldDescriptor() {
            this.phone = new Phone();
            this.email = new Email();
            this.address = new Address();
            this.dateOfBirth = new DateOfBirth();
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
```
###### /java/seedu/address/logic/commands/AddLifeInsuranceCommand.java
``` java
/**
 * Creates a life insurance in Lisa
 */
public class AddLifeInsuranceCommand extends UndoableCommand {
    public static final String[] COMMAND_WORDS = {"addli", "ali", "+li"};
    public static final String COMMAND_WORD = "addli";

    public static final String MESSAGE_USAGE = concatenateCommandWords(COMMAND_WORDS)
            + ": Adds a life insurance to Lisa.\n"
            + "Parameters: "
            + PREFIX_NAME + "INSURANCE_NAME "
            + PREFIX_OWNER + "OWNER "
            + PREFIX_INSURED + "INSURED "
            + PREFIX_BENEFICIARY + "BENEFICIARY "
            + PREFIX_PREMIUM + "PREMIUM "
            + PREFIX_SIGNING_DATE + "SIGNING_DATE "
            + PREFIX_EXPIRY_DATE + "EXPIRY_DATE "
            + PREFIX_CONTRACT_FILE_NAME + "CONTRACT_NAME\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Term Life "
            + PREFIX_OWNER + "Alex Yeoh "
            + PREFIX_INSURED + "John Doe "
            + PREFIX_BENEFICIARY + "Mary Jane "
            + PREFIX_PREMIUM + "600 "
            + PREFIX_SIGNING_DATE + "17 11 2017 "
            + PREFIX_EXPIRY_DATE + "17 11 2037 "
            + PREFIX_CONTRACT_FILE_NAME + "AlexYeoh-TermLife";

    public static final String MESSAGE_SUCCESS = "New insurance added: %1$s";
    public static final String MESSAGE_DUPLICATE_INSURANCE = "AddressBooks should not have duplicate insurances";

    public static final String MESSAGE_DUPLICATE_CONTRACT_FILE_NAME =
            "This insurance contract file already exists in LISA";

    private final LifeInsurance toAdd;

    /**
     * Creates an {@code AddLifeInsuranceCommand} to add the specified {@code LifeInsurance}
     */
    public AddLifeInsuranceCommand(ReadOnlyInsurance toAdd) {
        this.toAdd = new LifeInsurance(toAdd);
    }

    /**
     * Check if any {@code ReadOnlyPerson} arguments (owner, insured, and beneficiary) required to create a
     * life insurance are inside the person list by matching their names case-insensitively. If matches,
     * set the person as the owner, insured, or beneficiary accordingly.
     */
    private void isAnyPersonInList(List<ReadOnlyPerson> list, LifeInsurance lifeInsurance) {
        String ownerName = lifeInsurance.getOwner().getName();
        String insuredName = lifeInsurance.getInsured().getName();
        String beneficiaryName = lifeInsurance.getBeneficiary().getName();
        for (ReadOnlyPerson person: list) {
            String lowerCaseName = person.getName().toString().toLowerCase();
            if (lowerCaseName.equals(ownerName)) {
                lifeInsurance.setOwner(new InsurancePerson(person));
            }
            if (lowerCaseName.equals(insuredName)) {
                lifeInsurance.setInsured(new InsurancePerson(person));
            }
            if (lowerCaseName.equals(beneficiaryName)) {
                lifeInsurance.setBeneficiary(new InsurancePerson(person));
            }
        }
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        List<ReadOnlyPerson> personList = model.getFilteredPersonList();
        isAnyPersonInList(personList, toAdd);
        try {
            model.addLifeInsurance(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateInsuranceException die) {
            throw new AssertionError(MESSAGE_DUPLICATE_INSURANCE);
        } catch (DuplicateContractFileNameException dicne) {
            throw new CommandException(MESSAGE_DUPLICATE_CONTRACT_FILE_NAME);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddLifeInsuranceCommand // instanceof handles nulls
                && toAdd.equals(((AddLifeInsuranceCommand) other).toAdd));
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
            final Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).get();
            final Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

            ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE))
                .ifPresent(addPersonOptionalFieldDescriptor::setPhone);
            ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL))
                .ifPresent(addPersonOptionalFieldDescriptor::setEmail);
            ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS))
                .ifPresent(addPersonOptionalFieldDescriptor::setAddress);
```
###### /java/seedu/address/logic/parser/AddCommandParser.java
``` java
            final Phone phone = addPersonOptionalFieldDescriptor.getPhone();
            final Email email = addPersonOptionalFieldDescriptor.getEmail();
            final Address address = addPersonOptionalFieldDescriptor.getAddress();
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
 * Parses input arguments and creates a new {@code AddLifeInsuranceCommand} object
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
                PREFIX_CONTRACT_FILE_NAME, PREFIX_SIGNING_DATE, PREFIX_EXPIRY_DATE);

        if (!arePrefixesPresentAndFilled(argMultimap, PREFIX_NAME, PREFIX_OWNER, PREFIX_INSURED, PREFIX_BENEFICIARY,
                PREFIX_PREMIUM, PREFIX_CONTRACT_FILE_NAME, PREFIX_SIGNING_DATE, PREFIX_EXPIRY_DATE)) {
            throw new MissingPrefixException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddLifeInsuranceCommand.MESSAGE_USAGE));
        }

        try {
            final InsuranceName insuranceName = ParserUtil.parseInsuranceName(argMultimap.getValue(PREFIX_NAME)).get();
            final InsurancePerson owner = ParserUtil.parseInsurancePerson(argMultimap.getValue(PREFIX_OWNER)).get();
            final InsurancePerson insured = ParserUtil.parseInsurancePerson(argMultimap.getValue(PREFIX_INSURED)).get();
            final InsurancePerson beneficiary =
                    ParserUtil.parseInsurancePerson(argMultimap.getValue(PREFIX_BENEFICIARY)).get();
            final Premium premium = ParserUtil.parsePremium(argMultimap.getValue(PREFIX_PREMIUM)).get();
            final ContractFileName contractFileName =
                    ParserUtil.parseContractFileName(argMultimap.getValue(PREFIX_CONTRACT_FILE_NAME)).get();
            final LocalDate signingDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_SIGNING_DATE)).get();
            final LocalDate expiryDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_EXPIRY_DATE)).get();

            ReadOnlyInsurance lifeInsurance = new LifeInsurance(insuranceName, owner, insured, beneficiary, premium,
                    contractFileName, signingDate, expiryDate);

            return new AddLifeInsuranceCommand(lifeInsurance);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if the name prefixes does not contain empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresentAndFilled(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Arrays.stream(prefixes).allMatch(prefix -> {
            if (argumentMultimap.getValue(prefix).isPresent() && !argumentMultimap.getValue(prefix).get().isEmpty()) {
                return true;
            } else {
                return false;
            }
        });
    }
}
```
###### /java/seedu/address/logic/parser/ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> name} into an {@code Optional<InsuranceName>} if {@code owner} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<InsuranceName> parseInsuranceName(Optional<String> name) throws IllegalValueException {
        requireNonNull(name);
        return name.isPresent() ? Optional.of(new InsuranceName(name.get())) : Optional.empty();
    }
    /**
     * Parses a {@code Optional<String> insurancePerson} into an {@code Optional<InsurancePerson>} if {@code premium}
     * is present. See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<InsurancePerson> parseInsurancePerson(Optional<String> person) throws IllegalValueException {
        requireNonNull(person);
        return person.isPresent() ? Optional.of(new InsurancePerson(person.get())) : Optional.empty();
    }
    /**
     * Parses a {@code Optional<String> premium} into an {@code Optional<Premium>} if {@code premium} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Premium> parsePremium(Optional<String> premium) throws IllegalValueException {
        requireNonNull(premium);
        return premium.isPresent() ? Optional.of(new Premium(premium.get())) : Optional.empty();
    }
    /**
     * Parses a {@code Optional<String> contract} into an {@code Optional<ContractFileName>} if {@code contract}
     * is present. See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<ContractFileName> parseContractFileName(Optional<String> contract)
            throws IllegalValueException {
        requireNonNull(contract);
        return contract.isPresent() ? Optional.of(new ContractFileName(contract.get())) : Optional.empty();
    }
    /**
     * Parses a {@code Optional<String> date} into an {@code Optional<LocalDate>} if {@code date} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<LocalDate> parseDate(Optional<String> date) throws IllegalValueException {
        requireNonNull(date);
        DateParser dateParser = new DateParser();
        return date.isPresent() ? Optional.of(dateParser.parse(date.get())) : Optional.empty();
    }

```
###### /java/seedu/address/model/AddressBook.java
``` java
    public void setLifeInsurances(Map<UUID, ReadOnlyInsurance> insurances)
            throws DuplicateInsuranceException, DuplicateContractFileNameException {
        this.lifeInsuranceMap.setInsurances(insurances);
    }

    /**
     * Replaces the given insurance {@code target} in the map with {@code editedReadOnlyInsurance}.
     *
     * @throws InsuranceNotFoundException if the id of {@code target} cannot be found in the map.
     */
    public void updateLifeInsurance(ReadOnlyInsurance target, ReadOnlyInsurance editedReadOnlyInsurance)
            throws InsuranceNotFoundException {
        UUID id = target.getId();
        LifeInsurance lifeInsurance = new LifeInsurance(editedReadOnlyInsurance);
        this.lifeInsuranceMap.replace(id, lifeInsurance);
    }
```
###### /java/seedu/address/model/AddressBook.java
``` java
    /**
     *Adds an insurance to the address book.
     */
    public void addInsurance(ReadOnlyInsurance i)
            throws DuplicateInsuranceException, DuplicateContractFileNameException {
        LifeInsurance lifeInsurance = new LifeInsurance(i);
        lifeInsuranceMap.put(lifeInsurance.getId(), lifeInsurance);
        syncWithUpdate();
    }
```
###### /java/seedu/address/model/AddressBook.java
``` java
    /**
     * Ensures that every insurance in the master map:
     *  - links to its owner, insured, and beneficiary {@code Person} if they exist in master person list respectively
     */
    public void syncMasterLifeInsuranceMapWith(UniquePersonList persons) {
        clearAllPersonsInsuranceIds();
        lifeInsuranceMap.forEach((id, insurance) -> {
            insurance.resetAllInsurancePersons();
            String owner = insurance.getOwnerName();
            String insured = insurance.getInsuredName();
            String beneficiary = insurance.getBeneficiaryName();
            persons.forEach(person -> {
                if (person.getName().toString().equals(owner)) {
                    insurance.setOwner(new InsurancePerson(person));
                    person.addLifeInsuranceIds(id);
                }
                if (person.getName().toString().equals(insured)) {
                    insurance.setInsured(new InsurancePerson(person));
                    person.addLifeInsuranceIds(id);
                }
                if (person.getName().toString().equals(beneficiary)) {
                    insurance.setBeneficiary(new InsurancePerson(person));
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
###### /java/seedu/address/model/AddressBook.java
``` java
    @Override
    public Map<UUID, ReadOnlyInsurance> getLifeInsuranceMap() {
        return lifeInsuranceMap.asMap();
    }
```
###### /java/seedu/address/model/insurance/ContractFileName.java
``` java
/**
 * Represents a contract file name of an insurance in LISA.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class ContractFileName {

    public static final String MESSAGE_NAME_CONSTRAINTS = "Contract file names should start with "
            + "alphanumeric characters. It can only contain underscores, hyphens, spaces, "
            + "an optional file extension, and should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum}_\\-]*(\\.[\\p{Alnum}]+)?";

    public final String fullName;

    /**
     * Validates given name.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
    public ContractFileName(String name) throws IllegalValueException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (trimmedName.isEmpty()) {
            throw new EmptyFieldException(PREFIX_CONTRACT_FILE_NAME);
        }
        if (!isValidName(trimmedName)) {
            throw new IllegalValueException(MESSAGE_NAME_CONSTRAINTS);
        }
        this.fullName = trimmedName;
    }

    /**
     * Returns true if a given string is a valid contract file name.
     */
    public static boolean isValidName(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ContractFileName // instanceof handles nulls
                && this.fullName.equals(((ContractFileName) other).fullName)); // state check
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
```
###### /java/seedu/address/model/insurance/exceptions/DuplicateContractFileNameException.java
``` java
/**
 * Signals that the operation will result in duplicate Insurance objects.
 */
public class DuplicateContractFileNameException extends DuplicateDataException {
    public DuplicateContractFileNameException() {
        super("Operation would result in duplicate insurance contract file names");
    }
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
 * Represents a person and his/her name in an insurance inside LISA.
 */
public class InsurancePerson {

    private ObjectProperty<Name> name;
    private Optional<ReadOnlyPerson> person;

    public InsurancePerson(String nameString) throws IllegalValueException {
        final Name name = new Name(nameString);
        this.name = new SimpleObjectProperty(name);
        this.person = Optional.empty();
    }

    public InsurancePerson(ReadOnlyPerson person) {
        this.name = new SimpleObjectProperty<>(person.getName());
        this.person = Optional.of(person);
    }

    public InsurancePerson(Name name) {
        this.name = new SimpleObjectProperty<>(name);
        this.person = Optional.empty();
    }

    public String getName() {
        return name.get().toString();
    }

    public ObjectProperty<Name> nameProperty() {
        return name;
    }

    public void setPerson(ReadOnlyPerson person) {
        this.person = Optional.of(person);
    }

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
    private ObjectProperty<InsuranceName> insuranceName;
    private EnumMap<Roles, String> roleToPersonNameMap;
    private ObjectProperty<InsurancePerson> owner;
    private ObjectProperty<InsurancePerson> insured;
    private ObjectProperty<InsurancePerson> beneficiary;
    private ObjectProperty<Premium> premium;
    private ObjectProperty<ContractFileName> contractFileName;

```
###### /java/seedu/address/model/insurance/Premium.java
``` java
/**
 * Represents a insurance's premium in LISA.
 * Guarantees: immutable; is valid as declared in {@link #isValidPremium(String)}
 */
public class Premium {


    public static final String MESSAGE_PREMIUM_CONSTRAINTS =
            "Premium can only contain numbers with one optional decimal point";
    public static final String PREMIUM_VALIDATION_REGEX = "\\d+(\\.\\d+)?";
    public final Double value;


    /**
     * Validates given premium.
     *
     * @throws IllegalValueException if given phone string is invalid.
     */
    public Premium(String premium) throws IllegalValueException {
        requireNonNull(premium);
        String trimmedPremium = premium.trim();
        if (trimmedPremium.isEmpty()) {
            throw new EmptyFieldException(PREFIX_PREMIUM);
        }
        if (!isValidPremium(trimmedPremium)) {
            throw new IllegalValueException(MESSAGE_PREMIUM_CONSTRAINTS);
        }
        Double premiumValue =  Double.parseDouble(trimmedPremium);
        this.value = premiumValue;
    }

    /**
     * Returns true if a given string is a valid insurance premium.
     */
    public static boolean isValidPremium(String test) {
        return test.matches(PREMIUM_VALIDATION_REGEX);
    }

    public Double toDouble() {
        return this.value;
    }

```
###### /java/seedu/address/model/insurance/Premium.java
``` java
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Premium// instanceof handles nulls
                && this.value.equals(((Premium) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
    //
}
```
###### /java/seedu/address/model/insurance/ReadOnlyInsurance.java
``` java
/**
 * Represents an insurance.
 */
public interface ReadOnlyInsurance {

    InsuranceName getInsuranceName();
    ObjectProperty<InsuranceName> insuranceNameProperty();
    ObjectProperty<UUID> idProperty();
    UUID getId();
    EnumMap getRoleToPersonNameMap();
    ObjectProperty<InsurancePerson> ownerProperty();
    InsurancePerson getOwner();
    String getOwnerName();
    ObjectProperty<InsurancePerson> insuredProperty();
    InsurancePerson getInsured();
    String getInsuredName();
    ObjectProperty<InsurancePerson> beneficiaryProperty();
    InsurancePerson getBeneficiary();
    String getBeneficiaryName();
    ObjectProperty<Premium> premiumProperty();
    Premium getPremium();
    ObjectProperty<ContractFileName> contractFileNameProperty();
    ContractFileName getContractFileName();
    LocalDate getSigningDate();
    StringProperty signingDateStringProperty();
    String getSigningDateString();
    LocalDate getExpiryDate();
    StringProperty expiryDateStringProperty();
    String getExpiryDateString();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyInsurance other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getId().equals(this.getId()) // state checks here onwards
                && other.getInsuranceName().equals(this.getInsuranceName())
                && other.getRoleToPersonNameMap().equals(this.getRoleToPersonNameMap())
                && other.getOwner().equals(this.getOwner())
                && other.getInsured().equals(this.getInsured())
                && other.getBeneficiaryName().equals(this.getBeneficiaryName())
                && other.getPremium().equals(this.getPremium())
                && other.getSigningDate().equals(this.getSigningDate())
                && other.getSigningDateString().equals(this.getSigningDateString())
                && other.getExpiryDate().equals(this.getExpiryDate()))
                && other.getExpiryDateString().equals(this.getExpiryDateString())
                && other.getContractFileName().equals(this.getContractFileName());
    }

    /**
     * Formats the insurance as text, showing all the details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getInsuranceName())
                .append("  Owner: ")
                .append(getOwnerName())
                .append("  Insured: ")
                .append(getInsuredName())
                .append("  Beneficiary: ")
                .append(getBeneficiaryName())
                .append(" \nPremium: ")
                .append(getPremium())
                .append("  Contract File: ")
                .append(getContractFileName())
                .append("  Signing Date: ")
                .append(getSigningDateString())
                .append("  Expiry Date: ")
                .append(getExpiryDateString());
        return builder.toString();
    }
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
###### /java/seedu/address/model/insurance/UniqueLifeInsuranceList.java
``` java
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
     * Returns true if the map contains an equivalent key {@code UUID} as the given argument.
     */
    public boolean containsKey(UUID toCheck) {
        requireNonNull(toCheck);
        return internalMap.containsKey(toCheck);
    }

    /**
     * Returns true if the map contains an equivalent value {@code ReadOnlyInsurance} as the given argument.
     */
    public boolean containsValue(ReadOnlyInsurance toCheck) {
        requireNonNull(toCheck);
        return internalMap.containsValue(toCheck);
    }

    /**
     * Returns true if an insurance inside the map contains an equivalent {@code contractName} as the given argument.
     */
    public boolean containsContractFileName(ContractFileName toCheck) {
        requireNonNull(toCheck);
        return internalMap.values().stream().anyMatch(li ->
            li.getContractFileName().equals(toCheck)
        );
    }

    /**
     * Adds an life insurance to the map.
     *
     * @throws DuplicateInsuranceException if the life insurance to add is a duplicate of an
     * existing life insurance in the map.
     */
    public void put(UUID key, ReadOnlyInsurance toPut)
            throws DuplicateInsuranceException, DuplicateContractFileNameException {
        requireNonNull(toPut);
        if (containsValue(toPut)) {
            throw new DuplicateInsuranceException();
        }
        if (containsContractFileName(toPut.getContractFileName())) {
            throw new DuplicateContractFileNameException();
        }
        internalMap.put(key, new LifeInsurance(toPut));
    }

    /**
     * Replaces an life insurance in the map.
     *
     * @throws InsuranceNotFoundException if the id of the life insurance {@code toSet} can not be found
     * in the map.
     */
    public void replace(UUID key, ReadOnlyInsurance toSet) throws InsuranceNotFoundException {
        requireNonNull(toSet);
        if (!containsKey(key)) {
            throw new InsuranceNotFoundException();
        }
        internalMap.replace(key, new LifeInsurance(toSet));
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

    public void setInsurances(Map<UUID, ? extends ReadOnlyInsurance> insurances)
            throws DuplicateInsuranceException, DuplicateContractFileNameException {
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

    /**
     * Updates the filter of the filtered insurance list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredInsuranceList(Predicate<ReadOnlyInsurance> predicate);
```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public synchronized void addLifeInsurance(ReadOnlyInsurance insurance)
            throws DuplicateInsuranceException, DuplicateContractFileNameException {
        addressBook.addInsurance(insurance);
        updateFilteredInsuranceList(PREDICATE_SHOW_ALL_INSURANCES);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void deleteInsurance(ReadOnlyInsurance target) throws InsuranceNotFoundException {
        addressBook.deleteInsurance(target);
        updateFilteredInsuranceList(PREDICATE_SHOW_ALL_INSURANCES);
        indicateAddressBookChanged();
    }

    @Override
    public void updateLifeInsurance(ReadOnlyInsurance target, ReadOnlyInsurance editedInsurance)
        throws InsuranceNotFoundException {
        requireAllNonNull(target, editedInsurance);
        addressBook.updateLifeInsurance(target, editedInsurance);
        indicateAddressBookChanged();
    }
    //=========== Insurance List Accessors ==================================================================
    /**
     * Returns an unmodifiable view of the list of {@code ReadOnlyPerson} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<ReadOnlyInsurance> getFilteredInsuranceList() {
        return FXCollections.unmodifiableObservableList(filteredInsurances);
    }


    @Override
    public void updateFilteredInsuranceList(Predicate<ReadOnlyInsurance> predicate) {
        requireNonNull(predicate);
        filteredInsurances.setPredicate(predicate);
    }
```
###### /java/seedu/address/model/person/Address.java
``` java
    /**
     * Initialize a Address object with value of empty String. This can only be used in the default field of
     * {@code AddPersonOptionalFieldDescriptor}
     */
    public Address() {
        this.value = "";
    }
```
###### /java/seedu/address/model/person/Email.java
``` java
    /**
     * Initialize a Email object with value of empty String. This can only be used in the default field of
     * {@code AddPersonOptionalFieldDescriptor}
     */
    public Email() {
        this.value = "";
    }
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

    public void setName(Name name) {
        this.name.set(requireNonNull(name));
    }

    @Override
    public ObjectProperty<Name> nameProperty() {
        return name;
    }

    @Override
    public Name getName() {
        return name.get();
    }

    public void setPhone(Phone phone) {
        this.phone.set(requireNonNull(phone));
    }

    @Override
    public ObjectProperty<Phone> phoneProperty() {
        return phone;
    }

    @Override
    public Phone getPhone() {
        return phone.get();
    }

    public void setEmail(Email email) {
        this.email.set(requireNonNull(email));
    }

    @Override
    public ObjectProperty<Email> emailProperty() {
        return email;
    }

    @Override
    public Email getEmail() {
        return email.get();
    }

    public void setAddress(Address address) {
        this.address.set(requireNonNull(address));
    }

    @Override
    public ObjectProperty<Address> addressProperty() {
        return address;
    }

    @Override
    public Address getAddress() {
        return address.get();
    }

```
###### /java/seedu/address/model/person/Person.java
``` java
    /**
     * Adds a life insurance id to {@code lifeInsuranceIds} of this person.
     * Returns if a duplicate of the id to add already exists in the list.
     */
    public void addLifeInsuranceIds(UUID toAdd) {
        for (UUID id : lifeInsuranceIds.get()) {
            if (id.equals(toAdd)) {
                return;
            }
        }
        lifeInsuranceIds.get().add(toAdd);
    }

    /**
     * Clears the list of life insurance ids in this person.
     */
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

    /**
     * Adds a life insurance to {@code UniqueLifeInsuranceList} of this person.
     */
    public void addLifeInsurances(ReadOnlyInsurance lifeInsurance) {
        this.lifeInsurances.get().add(lifeInsurance);
    }

    /**
     * Clears the list of life insurances in this person.
     */
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
     * Initialize a Phone object with value of empty String. This can only be used in the default field of
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
    private String id;
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
    private String contractFileName;
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
        id = source.getId().toString();
        insuranceName = source.getInsuranceName().toString();
        owner = source.getOwner().getName();
        insured = source.getInsured().getName();
        beneficiary = source.getBeneficiary().getName();
        premium = source.getPremium().toDouble();
        contractFileName = source.getContractFileName().toString();
        signingDate = source.getSigningDateString();
        expiryDate = source.getExpiryDateString();
    }

    /**
     * Converts this jaxb-friendly adapted life insurance object into the model's LifeInsurance object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted life insurance
     */

    public LifeInsurance toModelType() throws IllegalValueException {
        final UUID id = UUID.fromString(this.id);
        final InsuranceName insuranceName = new InsuranceName(this.insuranceName);
        final InsurancePerson owner = new InsurancePerson(this.owner);
        final InsurancePerson insured = new InsurancePerson(this.insured);
        final InsurancePerson beneficiary = new InsurancePerson(this.beneficiary);
        final Premium premium = new Premium(this.premium.toString());
        final ContractFileName contractName = new ContractFileName(this.contractFileName);
        final DateParser dateParser = new DateParser();
        final LocalDate signingDate = dateParser.parse(this.signingDate);
        final LocalDate expiryDate = dateParser.parse(this.expiryDate);
        return new LifeInsurance(id, insuranceName, owner, insured, beneficiary, premium,
                contractName, signingDate, expiryDate);
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
```
###### /java/seedu/address/storage/XmlAdaptedPerson.java
``` java
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
                        return null;
                    }
                }
            ));
        return lifeInsurances;
    }
```
###### /java/seedu/address/ui/InsuranceCard.java
``` java
/**
 * An UI component that displays information of a {@code LifeInsurance}.
 */
public class InsuranceCard extends UiPart<Region> {


    private static final String FXML = "InsuranceCard.fxml";
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

    public InsuranceCard() {
        super(FXML);
        enableNameToProfileLink(insurance);
        registerAsAnEventHandler(this);

    }
```
###### /java/seedu/address/ui/InsuranceListPanel.java
``` java
    private static final String FXML = "InsuranceListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private ListView<InsuranceCard> insuranceListView;

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
        ObservableList<InsuranceCard> mappedList = EasyBind.map(
                insuranceList, (insurance) -> new InsuranceCard(insurance, insuranceList.indexOf(insurance) + 1));
        insuranceListView.setItems(mappedList);
        insuranceListView.setCellFactory(listView -> new InsuranceListPanel.InsuranceListViewCell());
        setEventHandlerForSelectionChangeEvent();

    }
```
###### /java/seedu/address/ui/ProfilePanel.java
``` java
    /**
     * Load person page with only his/her name with person does not exist in Lisa message
     */
    private void loadPersonPage(ObjectProperty<Name> name) {
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
###### /resources/view/InsuranceCard.fxml
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
###### /resources/view/InsuranceListPanel.fxml
``` fxml
<VBox xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
    <ListView fx:id="insuranceListView" VBox.vgrow="ALWAYS" />
</VBox>
```
